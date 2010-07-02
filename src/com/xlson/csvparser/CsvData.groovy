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

package com.xlson.csvparser

/**
 * Contains the information of the csv and how to work with it.
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvData {

    /**
     * The columns of the csv.
     */
    def columns = [:]



    /**
     * The lines of the csv. 
     */
    def rows = []

    /**
     * Counts the lines of the csv.
     *
     * @return the number of lines in the csv
     */
    def size() {
        rows.size()
    }

    /**
     * Creates an iterator over the lines in the csv.
     *
     * @return an instance of <code>CsvIterator</code>
     */
    def Iterator iterator() {
        new CsvIterator(rowIterator: rows.iterator(),
                columns: columns) as Iterator
    }
}
