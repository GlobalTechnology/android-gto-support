@file:JvmName("BundleUtils")

package org.ccci.gto.android.common.util.os

import android.os.Bundle
import java.util.Locale
import org.ccci.gto.android.common.compat.util.LocaleCompat
import org.jetbrains.annotations.Contract

// region Enums

fun Bundle.putEnum(key: String?, value: Enum<*>?) = putString(key, value?.name)

@JvmOverloads
@Contract("_, _, _, !null -> !null")
fun <T : Enum<T>> Bundle.getEnum(type: Class<T>, key: String?, defValue: T? = null): T? {
    return try {
        getString(key)?.let { java.lang.Enum.valueOf<T>(type, it) } ?: defValue
    } catch (e: IllegalArgumentException) {
        defValue
    }
}

@Contract("_, _, !null -> !null")
inline fun <reified T : Enum<T>> Bundle.getEnum(key: String?, defValue: T? = null) =
    getEnum(T::class.java, key, defValue)

// endregion Enums

// region Locales

fun Bundle.putLocale(key: String?, locale: Locale?) =
    putString(key, if (locale != null) LocaleCompat.toLanguageTag(locale) else null)

@JvmOverloads
@Contract("_, _, !null -> !null")
fun Bundle.getLocale(key: String?, defValue: Locale? = null) =
    getString(key)?.let { LocaleCompat.forLanguageTag(it) } ?: defValue

/**
 * Store an array of Locales in the provided Bundle
 *
 * @receiver The bundle to store the locale array in
 * @param key The key to store the locale array under
 * @param locales The locales being put in the bundle
 * @param singleString Flag indicating if the locale array should be stored as a single string
 */
@JvmOverloads
fun Bundle.putLocaleArray(key: String?, locales: Array<Locale?>?, singleString: Boolean = false) {
    val tags = locales?.map { it?.let { LocaleCompat.toLanguageTag(it) } }?.toTypedArray()

    if (singleString) {
        putString(key, tags?.joinToString(","))
    } else {
        putStringArray(key, tags)
    }
}

fun Bundle.getLocaleArray(key: String?) =
    (getStringArray(key) ?: getString(key)?.split(",")?.toTypedArray())
        ?.map { it?.let { LocaleCompat.forLanguageTag(it) } }?.toTypedArray()

// endregion Locales
