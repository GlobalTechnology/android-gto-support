package org.ccci.gto.android.common.db.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import org.ccci.gto.android.common.db.AbstractDao;
import org.ccci.gto.android.common.db.Expression;
import org.ccci.gto.android.common.db.Expression.Field;
import org.ccci.gto.android.common.db.Join;
import org.ccci.gto.android.common.db.Query;
import org.ccci.gto.android.common.db.Table;
import org.ccci.gto.android.common.support.v4.content.SimpleCursorLoader;
import org.ccci.gto.android.common.util.os.BundleKt;

import static org.ccci.gto.android.common.db.AbstractDao.ARG_DISTINCT;
import static org.ccci.gto.android.common.db.AbstractDao.ARG_JOINS;
import static org.ccci.gto.android.common.db.AbstractDao.ARG_ORDER_BY;
import static org.ccci.gto.android.common.db.AbstractDao.ARG_PROJECTION;
import static org.ccci.gto.android.common.db.AbstractDao.ARG_WHERE;

public class DaoCursorLoader<T> extends SimpleCursorLoader {
    @NonNull
    protected final AbstractDao mDao;

    private boolean mDistinct = false;
    @NonNull
    private final Table<T> mFrom;
    @NonNull
    @SuppressWarnings("unchecked")
    private Join<T, ?>[] mJoins = (Join<T, ?>[]) Join.NO_JOINS;
    @Nullable
    private Expression mWhere;
    @Nullable
    private Field[] mGroupBy;
    @Nullable
    private Expression mHaving;

    public DaoCursorLoader(@NonNull final Context context, @NonNull final AbstractDao dao, @NonNull final Class<T> type,
                           @Nullable final Bundle args) {
        this(context, dao, Table.forClass(type), args);
    }

    @SuppressWarnings("unchecked")
    public DaoCursorLoader(@NonNull final Context context, @NonNull final AbstractDao dao, @NonNull final Table<T> from,
                           @Nullable final Bundle args) {
        super(context);
        mDao = dao;

        mFrom = from;
        if (args != null) {
            setDistinct(args.getBoolean(ARG_DISTINCT, false));
            setJoins(BundleKt.getParcelableArray(args, ARG_JOINS, Join.class));
            setProjection(args.getStringArray(ARG_PROJECTION));
            setWhere(args.getParcelable(ARG_WHERE));
            setSortOrder(args.getString(ARG_ORDER_BY));
        } else {
            setDistinct(false);
            setJoins((Join<T, ?>[]) null);
            setProjection(null);
            setWhere(null);
            setSortOrder(null);
        }
    }

    @Nullable
    @Override
    @WorkerThread
    protected final Cursor getCursor() {
        // build query
        return mDao.getCursor(Query.select(mFrom)
                                      .distinct(isDistinct())
                                      .joins(getJoins())
                                      .projection(getProjection())
                                      .where(getWhere())
                                      .groupBy(getGroupBy())
                                      .having(getHaving())
                                      .orderBy(getSortOrder()));
    }

    public void setDistinct(final boolean distinct) {
        mDistinct = distinct;
    }

    public boolean isDistinct() {
        return mDistinct;
    }

    @Override
    public void setProjection(@Nullable final String[] projection) {
        super.setProjection(projection != null ? projection : mDao.getFullProjection(mFrom));
    }

    @NonNull
    public String[] getProjection() {
        final String[] projection = super.getProjection();
        return projection != null ? projection : mDao.getFullProjection(mFrom);
    }

    @SuppressWarnings("unchecked")
    public void setJoins(@Nullable final Join<T, ?>... joins) {
        mJoins = joins != null ? joins : (Join<T, ?>[]) Join.NO_JOINS;
    }

    @NonNull
    public Join<T, ?>[] getJoins() {
        return mJoins;
    }

    public void setWhere(@Nullable final Expression where) {
        mWhere = where;
    }

    @Nullable
    public Expression getWhere() {
        return mWhere;
    }

    @Nullable
    public Field[] getGroupBy() {
        return mGroupBy;
    }

    public void setGroupBy(@Nullable final Field... groupBy) {
        mGroupBy = groupBy;
    }

    @Nullable
    public Expression getHaving() {
        return mHaving;
    }

    public void setHaving(@Nullable final Expression having) {
        mHaving = having;
    }
}
