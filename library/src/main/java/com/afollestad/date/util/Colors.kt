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
package com.afollestad.date.util

import android.graphics.Color
import androidx.annotation.ColorInt

/** @author Aidan Follestad (@afollestad) */
internal fun Int.isColorDark(threshold: Double = 0.5): Boolean {
  if (this == Color.TRANSPARENT) {
    return false
  }
  val darkness =
    1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
  return darkness >= threshold
}

/** @author Aidan Follestad (@afollestad) */
@ColorInt internal fun Int.withAlpha(alpha: Float): Int {
  return Color.argb((255 * alpha).toInt(), Color.red(this), Color.green(this), Color.blue(this))
}
