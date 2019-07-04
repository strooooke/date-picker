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
package com.afollestad.date.data

import androidx.annotation.CheckResult
import androidx.annotation.VisibleForTesting
import com.afollestad.date.dayOfMonth
import com.afollestad.date.dayOfWeek
import com.afollestad.date.data.MonthItem.DayOfMonth
import com.afollestad.date.data.MonthItem.WeekHeader
import com.afollestad.date.month
import com.afollestad.date.data.snapshot.DateSnapshot
import com.afollestad.date.data.snapshot.snapshotMonth
import com.afollestad.date.totalDaysInMonth
import com.afollestad.date.year
import java.util.Calendar
import kotlin.properties.Delegates

/** @author Aidan Follestad (@afollestad) */
internal class MonthGraph(
  @VisibleForTesting val calendar: Calendar
) {
  @VisibleForTesting var daysInMonth: Int by Delegates.notNull()
  @VisibleForTesting var firstWeekDayInMonth: DayOfWeek
  var orderedWeekDays: List<DayOfWeek>

  init {
    calendar.dayOfMonth = 1
    daysInMonth = calendar.totalDaysInMonth
    firstWeekDayInMonth = calendar.dayOfWeek
    orderedWeekDays = calendar.firstDayOfWeek
        .asDayOfWeek()
        .andTheRest()
  }

  @CheckResult fun getMonthItems(selectedDate: DateSnapshot): List<MonthItem> {
    val daysOfMonth = mutableListOf<MonthItem>()
    val month = calendar.snapshotMonth()

    // Add weekday headers
    daysOfMonth.addAll(
        orderedWeekDays
            .map { WeekHeader(it) }
    )

    // Add prefix days first, days the lead up from last month to the first day of this
    daysOfMonth.addAll(
        orderedWeekDays
            .takeWhile { it != firstWeekDayInMonth }
            .map { DayOfMonth(it, month) }
    )

    for (date in 1..daysInMonth) {
      calendar.dayOfMonth = date
      daysOfMonth.add(
          DayOfMonth(
              dayOfWeek = calendar.dayOfWeek,
              month = month,
              date = date,
              isSelected = selectedDate == DateSnapshot(calendar.month, date, calendar.year)
          )
      )
    }

    if (daysOfMonth.size < EXPECTED_SIZE) {
      // Fill in remaining days of week
      val loopTarget = orderedWeekDays.last()
          .nextDayOfWeek()
      daysOfMonth.addAll(
          (daysOfMonth.last() as DayOfMonth)
              .dayOfWeek
              .nextDayOfWeek()
              .andTheRest()
              .takeWhile { it != loopTarget }
              .map { DayOfMonth(it, month) }
      )
    }
    // Make sure we fill up 6 weeks worth of dates
    while (daysOfMonth.size < EXPECTED_SIZE) {
      daysOfMonth.addAll(orderedWeekDays.map { DayOfMonth(it, month,
          NO_DATE
      ) })
    }

    check(daysOfMonth.size == EXPECTED_SIZE) {
      "${daysOfMonth.size} must equal $EXPECTED_SIZE"
    }
    return daysOfMonth
  }

  private companion object {
    const val EXPECTED_SIZE: Int = 49
  }
}