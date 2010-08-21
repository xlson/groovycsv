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
     * Parses the csv supplied using the reader. See parse(Reader reader) for
     * more information about usage.
     *
     * @param args configurable parameters
     * @param csv the csv to parse
     * @return an instance of <code>com.xlson.csvparser.CsvIterator</code>
     */
    def parse(Map args = [:], String csv) {
        parse(args, new StringReader(csv))
    }

    /**
     * Parses the supplied csv  and returns a CsvIterator that can be
     * use to access the data. The first line of the csv will be used
     * as column-headers.
     * <p>
     * Arguments for configuration:
     * <li>separator : Sets a custom separator like tab
     * <li>quoteChar : Sets a custom quote character
     * <li>escapeChar : Sets a custom escape character for the separator and quoteChar
     *
     * @param reader the csv to parse
     * @param args the configuration arguments
     * @return an instance of <code>com.xlson.csvparser.CsvIterator</code>
     */
    def parse(Map args = [:], Reader reader) {
        def csvReader = createCSVReader(args, reader)
        def columnNames = csvReader.readNext()

        new CsvIterator(columnNames, csvReader)
    }

    private CSVReader createCSVReader(Map args = [:], Reader reader) {
        char separator = args.separator ?: ','
        char quoteChar = args.quoteChar ?: '"'
        char escapeChar = args.escapeChar

        if(escapeChar) {
            return new CSVReader(reader, separator, quoteChar, escapeChar)             
        } else {
            return new CSVReader(reader, separator, quoteChar)
        }
    }

}
