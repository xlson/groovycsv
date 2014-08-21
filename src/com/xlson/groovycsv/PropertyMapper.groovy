/*
 * Copyright 2010 Leonard Axelsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xlson.groovycsv

/**
 * Maps between column names and values in a list. Uses <code>propertyMissing</code>
 * to allow for named access.
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class PropertyMapper {

    /**
     * A list of values for one csv line.
     */
    def values



    /**
     * The columns of the csv. 
     */
    def columns = [:]

    /**
     * Maps properties to values.
     *
     * @param name the name of the property
     * @return the value as a String
     * @throws MissingPropertyException where the <code>values<code>-list doesn't contain enough data.
     */
    def propertyMissing(String name) {
        def index = columns[name]
        if (index != null) {
            values[index]
        } else {
            throw new MissingPropertyException(name)
        }
    }

    /**
     * Allows values to be obtained using their position
     *
     * @param index
     * @return the value at that position
     */
    def getAt(Integer index) {
        values[index]
    }

    String toString() {
        columns.collect { key, index -> "$key: ${values[index]}" }.join(', ')
    }

    Map toMap() {
        def sortedKeys = columns.keySet().sort { columns[it] }
        [sortedKeys, values].transpose().collectEntries()
    }
}
