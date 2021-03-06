package org.ccci.gto.android.common.db

import android.os.Parcelable
import androidx.annotation.RestrictTo
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Join<S : Any, T : Any> private constructor(
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP) val target: Table<T>,
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP) val base: Join<S, *>? = null,
    private val type: String? = null,
    private val on: Expression? = null
) : Parcelable {
    companion object {
        @JvmField
        val NO_JOINS = emptyArray<Join<*, *>>()

        @JvmStatic
        @Deprecated("Since v3.3.0, use source.join() instead", ReplaceWith("source.join(target)"))
        fun <S : Any, T : Any> create(source: Table<S>, target: Table<T>) = source.join(target)

        @JvmStatic
        fun <S : Any, T : Any> create(target: Table<T>) = Join<S, T>(target = target)
    }

    fun type(type: String?) = copy(type = type)
    fun on(on: Expression?) = copy(on = on)
    fun andOn(on: Expression) = copy(on = this.on?.and(on) ?: on)

    fun <T2 : Any> join(target: Class<T2>) = join(Table.forClass(target))
    fun <T2 : Any> join(target: Table<T2>) = Join(target = target, base = this)
    inline fun <reified T2 : Any> join() = join(Table.forClass<T2>())

    @Transient
    @IgnoredOnParcel
    private var sqlJoin: QueryComponent? = null

    internal fun getSql(dao: AbstractDao): QueryComponent = sqlJoin ?: buildSql(dao).also { sqlJoin = it }

    private fun buildSql(dao: AbstractDao) = base?.getSql(dao) + buildString {
        if (type != null) append(' ').append(type)
        append(" JOIN ").append(target.sqlTable(dao))
    } + on?.buildSql(dao)?.prepend(" ON ")
}
