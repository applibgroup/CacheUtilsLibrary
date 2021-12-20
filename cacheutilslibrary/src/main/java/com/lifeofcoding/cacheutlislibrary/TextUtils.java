/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lifeofcoding.cacheutlislibrary;

/**
 * Utility class that contains helper methods for {@code String} manipulation.
 */
public class TextUtils {
    private TextUtils() {
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param string - the string to be examined
     * @return true if the string is null or 0-length
     */
    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        return string.isEmpty();
    }
}