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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * A date in the Byzantine calendar.
 *
 * This class is immutable and thread-safe.
 *
 * @version 1.2.0
 */
public final class ByzDate implements Comparable<ByzDate> {

    private final long milliseconds; // an offset from the Epoch
    private final int year;
    private final ByzMonth month;
    private final int dayOfMonth;
    private final ByzDayOfWeek dayOfWeek;

    /**
     * Creates an instance of ByzDate.
     *
     * The minimum supported Byzantine date is March 1, 5512.
     *
     * @param year the year in the Byzantine calendar
     * @param month the Byzantine month, not null
     * @param dayOfMonth the day of the month
     * @return the Byzantine date, not null
     */
    public static ByzDate of(int year, ByzMonth month, int dayOfMonth) {
        assert month != null;

        var calendar = new JulGreCalendar();
        calendar.switchToPureJulian();
        calendar.set(toCalYear(year, month), toCalMonth(month), dayOfMonth);

        return new ByzDate(calendar);
    }

    /**
     * Returns a copy of the source date.
     *
     * @param source the Byzantine date, not null
     * @return the Byzantine date, not null
     * @since 1.1.0
     */
    public static ByzDate of(ByzDate source) {
        assert source != null;

        return new ByzDate(source.calendar());
    }

    /**
     * Creates an instance of ByzDate from the Gregorian date.
     *
     * The minimum supported Gregorian date is October 15, 1582.
     *
     * @param year the year in the Gregorian calendar
     * @param month the month, from 1 (Jan) to 12 (Dec)
     * @param dayOfMonth the day of the month
     * @return the Byzantine date, not null
     */
    public static ByzDate fromGregorian(int year, int month, int dayOfMonth) {
        var calendar = new JulGreCalendar();
        calendar.set(year, month, dayOfMonth);
        calendar.switchToPureJulian();

        return new ByzDate(calendar);
    }

    /**
     * Creates an instance of ByzDate from the Gregorian date.
     *
     * The minimum supported Gregorian date is October 15, 1582.
     *
     * @param date the date in the Gregorian calendar, not null
     * @return the Byzantine date, not null
     * @since 1.2.0
     */
    public static ByzDate fromGregorian(LocalDate date) {
        assert date != null;

        int year = date.getYear();
        int month = date.getMonth().getValue();
        int dayOfMonth = date.getDayOfMonth();

        return fromGregorian(year, month, dayOfMonth);
    }

    /**
     * Returns the year of this date.
     *
     * @return the year in the Byzantine calendar
     */
    public int year() {
        return this.year;
    }

    /**
     * Returns the month of this date.
     *
     * @return the Byzantine month, not null
     */
    public ByzMonth month() {
        return this.month;
    }

    /**
     * Returns the day of the month of this date.
     *
     * @return the day of the month
     */
    public int dayOfMonth() {
        return this.dayOfMonth;
    }

    /**
     * Returns the day of the week of this date.
     *
     * @return the Byzantine day of the week, not null
     */
    public ByzDayOfWeek dayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Returns a copy of this date with the number of years added.
     *
     * @param years the number of years
     * @return the Byzantine date, not null
     */
    public ByzDate addYears(int years) {
        return new ByzDate(calendar().addYears(years));
    }

    /**
     * Returns a copy of this date with the number of months added.
     *
     * @param months the number of months
     * @return the Byzantine date, not null
     */
    public ByzDate addMonths(int months) {
        return new ByzDate(calendar().addMonths(months));
    }

    /**
     * Returns a copy of this date with the number of days added.
     *
     * @param days the number of days
     * @return the Byzantine date, not null
     */
    public ByzDate addDays(int days) {
        return new ByzDate(calendar().addDays(days));
    }

    /**
     * Checks if this date is equal to the other date.
     *
     * @param other the Byzantine date, not null
     * @return true if this date is equal to the other
     */
    public boolean isEqual(ByzDate other) {
        assert other != null;

        return this.milliseconds == other.milliseconds;
    }

    /**
     * Checks if this date is before the other date.
     *
     * @param other the Byzantine date, not null
     * @return true if this date is before the other
     */
    public boolean isBefore(ByzDate other) {
        assert other != null;

        return this.milliseconds < other.milliseconds;
    }

    /**
     * Checks if this date is after the other date.
     *
     * @param other the Byzantine date, not null
     * @return true if this date is after the other
     */
    public boolean isAfter(ByzDate other) {
        assert other != null;

        return this.milliseconds > other.milliseconds;
    }

    /**
     * Compares this date to the other date.
     *
     * @param other the Byzantine date, not null
     * @return negative if less, zero if equal, positive if greater
     */
    @Override
    public int compareTo(ByzDate other) {
        assert other != null;

        if (isBefore(other))
            return -1;
        if (isAfter(other))
            return 1;
        return 0;
    }

    /**
     * Checks if this object is equal to the other object.
     *
     * @param other the object to check
     * @return true if the other is an instance of ByzDate equal to this date
     */
    @Override
    public boolean equals(Object other) {
        return other != null
            && getClass() == other.getClass()
            && isEqual((ByzDate) other);
    }

    /**
     * Returns a hash code for this date.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Long.hashCode(this.milliseconds);
    }

    /**
     * Returns a string representation of this date.
     *
     * For example, APRIL 3, 7531.
     *
     * @return the string, not null
     */
    @Override
    public String toString() {
        return "%s %d, %d".formatted(month(), dayOfMonth(), year());
    }

    private static int toByzYear(int calYear, Month calMonth) {
        return calYear + switch (calMonth) {
            case JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST -> 5508;
            case SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 5509;
        };
    }

    private static int toCalYear(int byzYear, ByzMonth byzMonth) {
        return byzYear - switch (byzMonth) {
            case SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 5509;
            case JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST -> 5508;
        };
    }

    private static ByzMonth toByzMonth(Month calMonth) {
        var monthName = calMonth.toString();
        return ByzMonth.valueOf(monthName);
    }

    private static Month toCalMonth(ByzMonth byzMonth) {
        var monthName = byzMonth.toString();
        return Month.valueOf(monthName);
    }

    private static ByzDayOfWeek toByzDayOfWeek(DayOfWeek calDayOfWeek) {
        return switch (calDayOfWeek) {
            case SUNDAY -> ByzDayOfWeek.LORDSDAY;
            case MONDAY -> ByzDayOfWeek.SECOND;
            case TUESDAY -> ByzDayOfWeek.THIRD;
            case WEDNESDAY -> ByzDayOfWeek.FOURTH;
            case THURSDAY -> ByzDayOfWeek.FIFTH;
            case FRIDAY -> ByzDayOfWeek.PREPARATION;
            case SATURDAY -> ByzDayOfWeek.SABBATH;
        };
    }

    private ByzDate(JulGreCalendar calendar) {
        this.milliseconds = calendar.milliseconds();
        this.year = toByzYear(calendar.year(), calendar.month());
        this.month = toByzMonth(calendar.month());
        this.dayOfMonth = calendar.dayOfMonth();
        this.dayOfWeek = toByzDayOfWeek(calendar.dayOfWeek());
    }

    private JulGreCalendar calendar() {
        var calendar = new JulGreCalendar();
        calendar.switchToPureJulian();
        calendar.set(this.milliseconds);
        return calendar;
    }
}

