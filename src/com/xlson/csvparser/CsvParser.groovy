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
     * Parses the supplied csv  and returns a CsvIterator that can be
     * use to access the data. The first line of the csv will be used
     * as column-headers.
     *         *
     * @param csv the csv to parse
     * @return an instance of <code>com.xlson.csvparser.CsvIterator</code>
     */
    def parse(Map args = [:], String csv) {
        def reader = createCSVReader(args, csv)
        def columnNames = reader.readNext()

        new CsvIterator(columnNames, reader)
    }

    private CSVReader createCSVReader(Map args = [:], String csv) {
        return createCSVReader(args, new StringReader(csv))
    }

    private CSVReader createCSVReader(Map args = [:], Reader reader) {
        char separator = args.separator ?: ','

        //public CSVReader(java.io.Reader reader, char separator, char quotechar, char escape, int line)
        return new CSVReader(reader, separator)
    }

}
