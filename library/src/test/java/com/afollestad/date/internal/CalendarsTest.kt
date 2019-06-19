/**
 * Designed and developed by Aidan Follestad (@afollestad)
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
package com.afollestad.date.internal

import com.afollestad.date.dayOfMonth
import com.afollestad.date.dayOfWeek
import com.afollestad.date.decrementMonth
import com.afollestad.date.incrementMonth
import com.afollestad.date.lastMonth
import com.afollestad.date.month
import com.afollestad.date.nextMonth
import com.afollestad.date.totalDaysInMonth
import com.afollestad.date.year
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.GregorianCalendar

class CalendarsTest {
  private lateinit var calendar: Calendar

  @Before fun setup() {
    calendar = GregorianCalendar(
        2019,
        Calendar.JULY,
        28
    )
  }

  @Test fun year() {
    assertThat(calendar.year).isEqualTo(2019)
  }

  @Test fun month() {
    assertThat(calendar.month).isEqualTo(Calendar.JULY)
  }

  @Test fun dayOfMonth() {
    assertThat(calendar.dayOfMonth).isEqualTo(28)
  }

  @Test fun totalDaysInMonth() {
    assertThat(calendar.totalDaysInMonth).isEqualTo(31)
  }

  @Test fun incrementMonth() {
    calendar.incrementMonth()
    assertThat(calendar.dayOfMonth).isEqualTo(1)
    assertThat(calendar.month).isEqualTo(Calendar.AUGUST)
    assertThat(calendar.year).isEqualTo(2019)
  }

  @Test fun decrementMonth() {
    calendar.decrementMonth()
    assertThat(calendar.dayOfMonth).isEqualTo(1)
    assertThat(calendar.month).isEqualTo(Calendar.JUNE)
    assertThat(calendar.year).isEqualTo(2019)
  }

  @Test fun nextMonth() {
    val nextMonth = calendar.nextMonth()
    assertThat(nextMonth.dayOfMonth).isEqualTo(1)
    assertThat(nextMonth.month).isEqualTo(Calendar.AUGUST)
    assertThat(nextMonth.year).isEqualTo(2019)
  }

  @Test fun lastMonth() {
    val lastMonth = calendar.lastMonth()
    assertThat(lastMonth.dayOfMonth).isEqualTo(30)
    assertThat(lastMonth.month).isEqualTo(Calendar.JUNE)
    assertThat(lastMonth.year).isEqualTo(2019)
  }

  @Test fun dayOfWeek() {
    assertThat(calendar.dayOfWeek).isEqualTo(DayOfWeek.SUNDAY)
  }
}