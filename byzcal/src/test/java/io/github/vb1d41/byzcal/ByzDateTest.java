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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @version 1.0.0
 */
class ByzDateTest {
    @ParameterizedTest
    @CsvSource({
        "7531, SEPTEMBER, 1",
        "7531, OCTOBER, 1",
        "7531, NOVEMBER, 1",
        "7531, DECEMBER, 1",
        "7531, JANUARY, 1",
        "7531, FEBRUARY, 1",
        "7531, MARCH, 1",
        "7531, APRIL, 1",
        "7531, MAY, 1",
        "7531, JUNE, 1",
        "7531, JULY, 1",
        "7531, AUGUST, 1"
    })
    void testOf(int year, ByzMonth month, int dayOfMonth) {
        var d = ByzDate.of(year, month, dayOfMonth);

        assertEquals(year, d.year());
        assertEquals(month, d.month());
        assertEquals(dayOfMonth, d.dayOfMonth());
    }

    @ParameterizedTest
    @CsvSource({
        "2023,  1, 14, 7531, JANUARY, 1",
        "2023,  2, 14, 7531, FEBRUARY, 1",
        "2023,  3, 14, 7531, MARCH, 1",
        "2023,  4, 14, 7531, APRIL, 1",
        "2023,  5, 14, 7531, MAY, 1",
        "2023,  6, 14, 7531, JUNE, 1",
        "2023,  7, 14, 7531, JULY, 1",
        "2023,  8, 14, 7531, AUGUST, 1",
        "2023,  9, 14, 7532, SEPTEMBER, 1",
        "2023, 10, 14, 7532, OCTOBER, 1",
        "2023, 11, 14, 7532, NOVEMBER, 1",
        "2023, 12, 14, 7532, DECEMBER, 1"
    })
    void testFromGregorian(int greYear, int greMonth, int greDayOfMonth,
            int byzYear, ByzMonth byzMonth, int byzDayOfMonth) {

        var d = ByzDate.fromGregorian(greYear, greMonth, greDayOfMonth);

        assertEquals(byzYear, d.year());
        assertEquals(byzMonth, d.month());
        assertEquals(byzDayOfMonth, d.dayOfMonth());
    }

    @ParameterizedTest
    @CsvSource({
        "2023, 4, 16, LORDSDAY",
        "2023, 4, 17, SECOND",
        "2023, 4, 18, THIRD",
        "2023, 4, 19, FOURTH",
        "2023, 4, 20, FIFTH",
        "2023, 4, 21, PREPARATION",
        "2023, 4, 22, SABBATH"
    })
    void testDayOfWeek(int greYear, int greMonth, int greDayOfMonth,
            ByzDayOfWeek byzDayOfWeek) {

        var d = ByzDate.fromGregorian(greYear, greMonth, greDayOfMonth);

        assertEquals(byzDayOfWeek, d.dayOfWeek());
    }

    @Test
    void testAddYears() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2024, 6, 14);

        assertEquals(d2, d1.addYears(1));
        assertEquals(d1, d2.addYears(-1));
    }

    @Test
    void testAddMonths() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 7, 14);

        assertEquals(d2, d1.addMonths(1));
        assertEquals(d1, d2.addMonths(-1));
    }

    @Test
    void testAddDays() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 15);

        assertEquals(d2, d1.addDays(1));
        assertEquals(d1, d2.addDays(-1));
    }

    @Test
    void testIsEqual() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 14);
        var d3 = ByzDate.fromGregorian(2023, 6, 15);

        assertTrue(d1.isEqual(d2));
        assertTrue(d2.isEqual(d1));

        assertFalse(d1.isEqual(d3));
        assertFalse(d3.isEqual(d1));
    }

    @Test
    void testIsBefore() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 15);
        var d3 = ByzDate.fromGregorian(2023, 6, 14);

        assertTrue(d1.isBefore(d2));

        assertFalse(d1.isBefore(d3));
        assertFalse(d2.isBefore(d1));
    }

    @Test
    void testIsAfter() {
        var d1 = ByzDate.fromGregorian(2023, 6, 15);
        var d2 = ByzDate.fromGregorian(2023, 6, 14);
        var d3 = ByzDate.fromGregorian(2023, 6, 15);

        assertTrue(d1.isAfter(d2));

        assertFalse(d1.isAfter(d3));
        assertFalse(d2.isAfter(d1));
    }

    @Test
    void testCompareTo() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 15);
        var d3 = ByzDate.fromGregorian(2023, 6, 14);

        assertTrue(d1.compareTo(d2) < 0);
        assertTrue(d2.compareTo(d1) > 0);
        assertTrue(d1.compareTo(d3) == 0);
    }

    @Test
    void testEquals() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 14);
        var d3 = ByzDate.fromGregorian(2023, 6, 15);

        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        assertFalse(d1.equals(d3));
        assertFalse(d3.equals(d1));
    }

    @Test
    void testHashCode() {
        var d1 = ByzDate.fromGregorian(2023, 6, 14);
        var d2 = ByzDate.fromGregorian(2023, 6, 14);
        var d3 = ByzDate.fromGregorian(2023, 6, 15);

        assertEquals(d2.hashCode(), d1.hashCode());

        assertNotEquals(d3.hashCode(), d1.hashCode());
    }

    @Test
    void testToString() {
        var d = ByzDate.of(7531, ByzMonth.APRIL, 3);

        assertEquals("APRIL 3, 7531", d.toString());
    }
}

