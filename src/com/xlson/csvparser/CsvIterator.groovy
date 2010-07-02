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
 * CsvIterator acts as a proxy around the <code>rowIterator</code> sent to it
 * and delegates all calls but <code>next()</code> to it. 
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvIterator {

    /**
     * The underlying iterator.
     */
    @Delegate
    Iterator rowIterator



    /**
     * The columns of the csv data.
     */
    def columns

    /**
     * Gets the next row in the csv file.
     *
     * @return an instance of <code>PropertyMapper</code>
     */
    def next() { new PropertyMapper(columns: columns, values: rowIterator.next()) }
}
