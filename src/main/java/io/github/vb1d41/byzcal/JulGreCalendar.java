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
 * @version 1.1.0
 */
final class JulGreCalendar {

    private final GregorianCalendar calendar;

    public JulGreCalendar() {
        this.calendar = makeCalendar();
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
        return Month.of(this.calendar.get(Calendar.MONTH) + 1);
    }

    public int dayOfMonth() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DayOfWeek dayOfWeek() {
        DayOfWeek dayOfWeek = switch(this.calendar.get(Calendar.DAY_OF_WEEK)) {
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

    public JulGreCalendar addYears(int years) {
        this.calendar.add(Calendar.YEAR, years);
        return this;
    }

    public JulGreCalendar addMonths(int months) {
        this.calendar.add(Calendar.MONTH, months);
        return this;
    }

    public JulGreCalendar addDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, days);
        return this;
    }

    private static GregorianCalendar makeCalendar() {
        var timeZone = TimeZone.getTimeZone("GMT0");
        return new GregorianCalendar(timeZone, Locale.US);
    }
}

