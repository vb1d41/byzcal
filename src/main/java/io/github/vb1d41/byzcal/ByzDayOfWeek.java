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

/**
 * A day of the week in the Byzantine calendar.
 *
 * The days of the week are Lordsday, Second, Third, Fourth, Fifth,
 * Preparation and Sabbath.
 *
 * @version 1.1.0
 */
public enum ByzDayOfWeek {
    /**
     * The first day of the week (Sunday).
     */
    LORDSDAY,
    /**
     * The second day of the week (Monday).
     */
    SECOND,
    /**
     * The third day of the week (Tuesday).
     */
    THIRD,
    /**
     * The fourth day of the week (Wednesday).
     */
    FOURTH,
    /**
     * The fifth day of the week (Thursday).
     */
    FIFTH,
    /**
     * The sixth day of the week (Friday).
     */
    PREPARATION,
    /**
     * The seventh day of the week (Saturday).
     */
    SABBATH;
}

