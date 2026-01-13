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
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @version 1.2.1
 */
final class CalendarAdapter {

    private final GregorianCalendar calendar;

    public CalendarAdapter() {
        var timeZone = TimeZone.getTimeZone("GMT0");
        this.calendar = new GregorianCalendar(timeZone, Locale.US);
        this.calendar.clear();
    }

    public void set(int year, Month month, int dayOfMonth) {
        set(year, month.getValue(), dayOfMonth);
    }

    // month [1,12]
    public void set(int year, int month, int dayOfMonth) {
        this.calendar.set(year, month - 1, dayOfMonth);
    }

    public void set(long milliseconds) {
        this.calendar.setTimeInMillis(milliseconds);
    }

    public void switchToPureJulian() {
        var maxDate = new Date(Long.MAX_VALUE);
        this.calendar.setGregorianChange(maxDate);
    }

    public int year() {
        return this.calendar.get(Calendar.YEAR);
    }

    public Month month() {
        int calendarMonth = this.calendar.get(Calendar.MONTH); // [0,11]
        return Month.of(calendarMonth + 1);
    }

    public int dayOfMonth() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DayOfWeek dayOfWeek() {
        int calendarDayOfWeek = this.calendar.get(Calendar.DAY_OF_WEEK);

        DayOfWeek dayOfWeek = switch (calendarDayOfWeek) {
            case Calendar.SUNDAY -> DayOfWeek.SUNDAY;
            case Calendar.MONDAY -> DayOfWeek.MONDAY;
            case Calendar.TUESDAY -> DayOfWeek.TUESDAY;
            case Calendar.WEDNESDAY -> DayOfWeek.WEDNESDAY;
            case Calendar.THURSDAY -> DayOfWeek.THURSDAY;
            case Calendar.FRIDAY -> DayOfWeek.FRIDAY;
            case Calendar.SATURDAY -> DayOfWeek.SATURDAY;
            default -> null;
        };

        assert dayOfWeek != null;
        return dayOfWeek;
    }

    public long milliseconds() {
        return this.calendar.getTimeInMillis();
    }

    public CalendarAdapter addYears(int years) {
        this.calendar.add(Calendar.YEAR, years);
        return this;
    }

    public CalendarAdapter addMonths(int months) {
        this.calendar.add(Calendar.MONTH, months);
        return this;
    }

    public CalendarAdapter addDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, days);
        return this;
    }
}
