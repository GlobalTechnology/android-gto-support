package org.ccci.gto.android.common.jsonapi.model;

import org.ccci.gto.android.common.jsonapi.annotation.JsonApiId;
import org.ccci.gto.android.common.jsonapi.annotation.JsonApiIgnore;
import org.ccci.gto.android.common.jsonapi.annotation.JsonApiPostCreate;

import androidx.annotation.Nullable;

public abstract class ModelBase {
    @Nullable
    @JsonApiId
    public Integer mId;

    @JsonApiIgnore
    public boolean mPostCreateCalled = false;

    @JsonApiPostCreate
    final void finalPostCreate() {
        if (mPostCreateCalled) {
            throw new IllegalStateException("post create already called!!!");
        }
        mPostCreateCalled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ModelBase modelBase = (ModelBase) o;

        return mId != null ? mId.equals(modelBase.mId) : modelBase.mId == null;
    }

    @Override
    public int hashCode() {
        return mId != null ? mId : Integer.MAX_VALUE;
    }
}
