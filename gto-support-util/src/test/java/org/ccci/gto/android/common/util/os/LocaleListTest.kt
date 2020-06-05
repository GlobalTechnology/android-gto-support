package org.ccci.gto.android.common.util.os

import android.os.LocaleList
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class LocaleListTest {
    @Test
    fun testListIterator() {
        val list = LocaleList(Locale.ENGLISH, Locale.FRENCH)
        val iterator = list.listIterator()

        assertTrue(iterator.hasNext())
        assertFalse(iterator.hasPrevious())
        assertEquals(0, iterator.nextIndex())
        assertEquals(Locale.ENGLISH, iterator.next())
        assertTrue(iterator.hasNext())
        assertTrue(iterator.hasPrevious())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals(Locale.FRENCH, iterator.next())
        assertFalse(iterator.hasNext())
        assertTrue(iterator.hasPrevious())
        assertEquals(1, iterator.previousIndex())
        assertEquals(Locale.FRENCH, iterator.previous())
        assertTrue(iterator.hasNext())
        assertTrue(iterator.hasPrevious())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals(Locale.ENGLISH, iterator.previous())
    }
}
