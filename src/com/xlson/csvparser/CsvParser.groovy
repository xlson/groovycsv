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

import au.com.bytecode.opencsv.CSVReader

/**
 * Helper class used to parse information from csv files using the column names
 * in the first line. Currently it only supports csv files where the first line
 * contains the column names.
 * <p>
 * Usage:
 * <pre>
 * def csv = '''Name,Lastname
 * Mark,Andersson
 * Pete,Hansen'''
 *
 * def data = new CsvParser().parse(csv)
 * for(line in data) {
 *   println "$line.Name $line.Lastname"
 * }</pre>
 *
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvParser {

    /**
     * Parses the supplied csv into a CsvData instance that can be used
     * to access the data. The first line of the csv will be used as
     * column-headers.
     *
     *
     * @param csv the csv to parse
     * @return an instance of <code>CsvData</code>
     */
    def parse(String csv) {
        def reader = new CSVReader(new StringReader(csv))
        def lines = reader.readAll()

        new CsvIterator(rowIterator: lines[1..-1].iterator(),
                columns: buildColumnMap(lines[0])) as Iterator
    }

    private def buildColumnMap(headers) {
        def columns = [:]
        headers.eachWithIndex { columnName, index ->
            columns.put(columnName, index)
        }
        columns
    }

}
