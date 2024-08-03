package com.tbb.taamcollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class AddItemTest {
    @Test
    public void testIsNumberTrue() {
        assertTrue(AddItemFragment.isNumber("123"));
    }

    @Test
    public void testIsNumberFalse() {
        assertFalse(AddItemFragment.isNumber("abc"));
    }

    @Test
    public void testIsNumberMixedNumFront() {
        assertFalse(AddItemFragment.isNumber("123abc"));
    }

    @Test
    public void testIsNumberMixedNumBack() {
        assertFalse(AddItemFragment.isNumber("oadf19823"));
    }

    @Test
    public void testIsNumberMixed() {
        assertFalse(AddItemFragment.isNumber("s328vn29xm"));
    }

    @Test
    public void testIsNumberNegative() {
        assertFalse(AddItemFragment.isNumber("-1029363"));
    }
}