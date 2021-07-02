package org.ccci.gto.android.common.util.os

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

private const val KEY1 = "key1"
private const val KEY2 = "key2"
private const val KEY3 = "key3"

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class BundleUtilsTest {
    enum class TestEnum { VALUE1, VALUE2, DEFVALUE }

    @Test
    fun verifyPutEnum() {
        val bundle = Bundle().apply {
            putEnum(KEY1, TestEnum.VALUE1)
            putEnum(KEY2, TestEnum.VALUE2)
            putEnum(KEY3, null)
        }

        assertEquals(3, bundle.size())
        assertEquals(TestEnum.VALUE1, bundle.getEnum<TestEnum>(KEY1))
        assertEquals(TestEnum.VALUE2, bundle.getEnum<TestEnum>(KEY2))
        assertNull(bundle.getEnum<TestEnum>(KEY3))
    }

    @Test
    fun verifyGetEnum() {
        val bundle = Bundle().apply {
            putEnum(KEY1, TestEnum.VALUE1)
            putEnum(KEY2, TestEnum.VALUE2)
            putString(KEY3, "VALUE1-3")
        }

        assertEquals(TestEnum.VALUE1, bundle.getEnum<TestEnum>(KEY1))
        assertEquals(TestEnum.VALUE2, bundle.getEnum<TestEnum>(KEY2))
        assertNull(bundle.getEnum<TestEnum>(KEY3))
    }

    @Test
    fun verifyGetEnumDefault() {
        val bundle = Bundle().apply {
            putEnum(KEY1, TestEnum.VALUE1)
            putString(KEY2, "VALUE2-3")
        }

        assertEquals(TestEnum.VALUE1, bundle.getEnum(KEY1, TestEnum.DEFVALUE))
        assertEquals(TestEnum.DEFVALUE, bundle.getEnum(KEY2, TestEnum.DEFVALUE))
        assertEquals(TestEnum.DEFVALUE, bundle.getEnum(KEY3, TestEnum.DEFVALUE))
    }

    @Test
    fun verifyPutLocaleAndGetLocale() {
        val bundle = Bundle().apply {
            putLocale(KEY1, Locale.ENGLISH)
            putLocale(KEY2, Locale.FRENCH)
            putLocale(KEY3, null)
        }

        assertEquals(3, bundle.size())
        assertEquals(Locale.ENGLISH, bundle.getLocale(KEY1))
        assertEquals(Locale.FRENCH, bundle.getLocale(KEY2))
        assertNull(bundle.getLocale(KEY3))
    }
}
