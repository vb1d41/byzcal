/*
 * Copyright 2023 vb1d41
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vb1d41.byzcal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * @version 1.1.1
 */
public class ByzDateTest {

    static final ByzDate APR_3_7531 = ByzDate.of(7531, ByzMonth.APRIL, 3);
    static final ByzDate APR_4_7531 = ByzDate.of(7531, ByzMonth.APRIL, 4);
    static final ByzDate MAY_3_7531 = ByzDate.of(7531, ByzMonth.MAY,   3);
    static final ByzDate APR_3_7532 = ByzDate.of(7532, ByzMonth.APRIL, 3);

    @DataProvider(name = "ofYearMonthDay")
    public Object[][] ofYearMonthDay() {
        return new Object[][] {
            { 7531, ByzMonth.SEPTEMBER, 1 },
            { 7531, ByzMonth.OCTOBER,   1 },
            { 7531, ByzMonth.NOVEMBER,  1 },
            { 7531, ByzMonth.DECEMBER,  1 },
            { 7531, ByzMonth.JANUARY,   1 },
            { 7531, ByzMonth.FEBRUARY,  1 },
            { 7531, ByzMonth.MARCH,     1 },
            { 7531, ByzMonth.APRIL,     1 },
            { 7531, ByzMonth.MAY,       1 },
            { 7531, ByzMonth.JUNE,      1 },
            { 7531, ByzMonth.JULY,      1 },
            { 7531, ByzMonth.AUGUST,    1 }
        };
    }

    @Test(dataProvider = "ofYearMonthDay")
    public void testOfYearMonthDay(int year, ByzMonth month, int dayOfMonth) {

        var d = ByzDate.of(year, month, dayOfMonth);

        assertEquals(year, d.year());
        assertEquals(month, d.month());
        assertEquals(dayOfMonth, d.dayOfMonth());
    }

    @DataProvider(name = "fromGregorian")
    public Object[][] fromGregorian() {
        return new Object[][] {
            { 2023,  1, 14,  7531, ByzMonth.JANUARY,   1 },
            { 2023,  2, 14,  7531, ByzMonth.FEBRUARY,  1 },
            { 2023,  3, 14,  7531, ByzMonth.MARCH,     1 },
            { 2023,  4, 14,  7531, ByzMonth.APRIL,     1 },
            { 2023,  5, 14,  7531, ByzMonth.MAY,       1 },
            { 2023,  6, 14,  7531, ByzMonth.JUNE,      1 },
            { 2023,  7, 14,  7531, ByzMonth.JULY,      1 },
            { 2023,  8, 14,  7531, ByzMonth.AUGUST,    1 },
            { 2023,  9, 14,  7532, ByzMonth.SEPTEMBER, 1 },
            { 2023, 10, 14,  7532, ByzMonth.OCTOBER,   1 },
            { 2023, 11, 14,  7532, ByzMonth.NOVEMBER,  1 },
            { 2023, 12, 14,  7532, ByzMonth.DECEMBER,  1 }
        };
    }

    @Test(dataProvider = "fromGregorian")
    public void testFromGregorian(int greYear, int greMonth, int greDayOfMonth,
            int byzYear, ByzMonth byzMonth, int byzDayOfMonth) {

        var d = ByzDate.fromGregorian(greYear, greMonth, greDayOfMonth);

        assertEquals(byzYear, d.year());
        assertEquals(byzMonth, d.month());
        assertEquals(byzDayOfMonth, d.dayOfMonth());
    }

    @Test
    public void testOfDate() {
        ByzDate d = APR_3_7531;

        assertEquals(d, ByzDate.of(d));
    }

    @DataProvider(name = "dayOfWeek")
    public Object[][] dayOfWeek() {
        return new Object[][] {
            { 7531, ByzMonth.APRIL, 3, ByzDayOfWeek.LORDSDAY },
            { 7531, ByzMonth.APRIL, 4, ByzDayOfWeek.SECOND },
            { 7531, ByzMonth.APRIL, 5, ByzDayOfWeek.THIRD },
            { 7531, ByzMonth.APRIL, 6, ByzDayOfWeek.FOURTH },
            { 7531, ByzMonth.APRIL, 7, ByzDayOfWeek.FIFTH },
            { 7531, ByzMonth.APRIL, 8, ByzDayOfWeek.PREPARATION },
            { 7531, ByzMonth.APRIL, 9, ByzDayOfWeek.SABBATH }
        };
    }

    @Test(dataProvider = "dayOfWeek")
    public void testDayOfWeek(int year, ByzMonth month, int dayOfMonth,
            ByzDayOfWeek dayOfWeek) {

        var d = ByzDate.of(year, month, dayOfMonth);

        assertEquals(dayOfWeek, d.dayOfWeek());
    }

    @Test
    public void testAddYears() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_3_7532;

        assertEquals(d2, d1.addYears(1));
        assertEquals(d1, d2.addYears(-1));
    }

    @Test
    public void testAddMonths() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = MAY_3_7531;

        assertEquals(d2, d1.addMonths(1));
        assertEquals(d1, d2.addMonths(-1));
    }

    @Test
    public void testAddDays() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_4_7531;

        assertEquals(d2, d1.addDays(1));
        assertEquals(d1, d2.addDays(-1));
    }

    @Test
    public void testIsEqual() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_3_7531;
        ByzDate d3 = APR_4_7531;

        assertTrue(d1.isEqual(d2));

        assertFalse(d1.isEqual(d3));
        assertFalse(d3.isEqual(d1));
    }

    @Test
    public void testIsBefore() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_4_7531;
        ByzDate d3 = APR_3_7531;

        assertTrue(d1.isBefore(d2));

        assertFalse(d1.isBefore(d3));
        assertFalse(d2.isBefore(d1));
    }

    @Test
    public void testIsAfter() {
        ByzDate d1 = APR_4_7531;
        ByzDate d2 = APR_3_7531;
        ByzDate d3 = APR_4_7531;

        assertTrue(d1.isAfter(d2));

        assertFalse(d1.isAfter(d3));
        assertFalse(d2.isAfter(d1));
    }

    @Test
    public void testCompareTo() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_4_7531;
        ByzDate d3 = APR_3_7531;

        assertTrue(d1.compareTo(d2) < 0);
        assertTrue(d2.compareTo(d1) > 0);
        assertTrue(d1.compareTo(d3) == 0);
    }

    @Test
    public void testEquals() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_3_7531;
        ByzDate d3 = APR_4_7531;

        assertTrue(d1.equals(d2));

        assertFalse(d1.equals(d3));
        assertFalse(d3.equals(d1));

        assertFalse(d1.equals(null));
        assertFalse(d1.equals(""));
    }

    @Test
    public void testHashCode() {
        ByzDate d1 = APR_3_7531;
        ByzDate d2 = APR_3_7531;
        ByzDate d3 = APR_4_7531;

        assertEquals(d2.hashCode(), d1.hashCode());

        assertNotEquals(d3.hashCode(), d1.hashCode());
    }

    @Test
    public void testToString() {
        ByzDate d = APR_3_7531;

        assertEquals("APRIL 3, 7531", d.toString());
    }
}

