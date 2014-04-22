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
     * Number of characters used to provide to autodetection (in case auto
     * detection is used.
     */
    Integer autoDetectCharNumber = 1000

    /**
     * Parses a string as csv in the same way as CsvParser.parse(...).
     *
     * @param args
     * @param csv
     * @return
     */
    static Iterator parseCsv(Map args = [:], String csv) {
        new CsvParser().parse(args, csv)
    }

    /**
     * Parses a reader as csv in the same way as CsvParser.parse(...).
     *
     * @param args
     * @param csv
     * @return
     */
    static Iterator parseCsv(Map args = [:], Reader reader) {
        new CsvParser().parse(args, reader)
    }

    /**
     * Parses the csv supplied using the reader. See parse(Reader reader) for
     * more information about usage.
     *
     * @param args configurable parameters
     * @param csv the csv to parse
     * @return an instance of <code>com.xlson.groovycsv.CsvIterator</code>
     */
    Iterator parse(Map args = [:], String csv) {
        parse(args, new StringReader(csv))
    }

    /**
     * Parses the supplied csv  and returns a CsvIterator that can be
     * use to access the data. The first line of the csv will be used
     * as column-headers. Named paramenters can be used to configure the
     * parsing, see the class documentation for more more information on
     * usage. There's also support for autodetecting the quote and separator
     * characters.
     * <p>
     * Arguments for configuration:
     * <li>separator: configures the separator character to use (default: ,)
     * <li>quoteChar: configures the quote character to use (default: ")
     * <li>escapeChar: configures the escape character for the separator and quoteChar (default:\
     * <li>autoDetect: sets up autodetect that will honor other configurations you've done (default: false)
     * <li>columnNames: set custom column names instead of using the first line
     * <li>readFirstLine: reads the first line as csv instead of using it as headers
     *
     * <p>
     * Usage:
     * <pre>
     * def csv = '''Fruit-Quantity
     * Apple-2
     * Pear-5'''
     *
     * def data = new CsvParser().parse(csv, separator: '-')
     *
     * // Print all fruits that have a quantity higher than 3
     * data.findAll{ (it.Quantity as int) > 3 }.each{ println it }
     * </pre>
     * </p>
     * @param reader the csv to parse
     * @param args the configuration arguments
     * @return an instance of <code>com.xlson.groovycsv.CsvIterator</code>
     */
    Iterator parse(Map args = [:], Reader reader) {
        def csvReader = createCSVReader(args, reader)
        def columnNames = parseColumnNames(args, csvReader)
        new CsvIterator(columnNames, csvReader)
    }

    private def parseColumnNames(Map args, CSVReader csvReader) {
        def columnNames

        if (!args.readFirstLine) {
            columnNames = csvReader.readNext()
        }

        if (args.columnNames) {
            columnNames = args.columnNames
        }
        return columnNames
    }

    private CSVReader createCSVReader(Map args = [:], Reader reader) {
        Character separator
        Character quoteChar
        Character escapeChar = args.escapeChar
        
        if (args.autoDetect == true) {
            reader = new PushbackReader(reader, autoDetectCharNumber)
            doAutoDetection(args, reader)
            separator = args.separator
            quoteChar = args.quoteChar
        } else {
            separator = args.separator ?: ','
            quoteChar = args.quoteChar ?: '"'
        }

        if(escapeChar != null) {
            return new CSVReader(reader, separator, quoteChar, escapeChar)    
        } else {
            return new CSVReader(reader, separator, quoteChar)
        }
    }

    /**
     * Performs automatic detection of separator and quote character.
     * 
     * It will search arguments for values 'auto' in separator and quoteChar. It
     * will return a new version of the arguments modified with the values that
     * were found.
     * 
     * If nothing is detected, the values are removed from the args.
     * 
     * Note that 
     * 
     * @param args the configuration arguments.
     * @param text the CSV as a String.
     * @return modified args with detected. 
     */
    private Map doAutoDetection(Map args, PushbackReader reader) {
        def buf = new char[autoDetectCharNumber]
        def charsRead = reader.read(buf)
        def linesToInspect = new String(buf)
        reader.unread(buf, 0, charsRead)
        
        
        def autoDetector = new AutoDetectHandler(linesToInspect: linesToInspect)
        if (args.autoDetectQuoteChars) {
            autoDetector.autoDetectQuoteChars = args.autoDetectQuoteChars
        }
        if (args.autoDetectSeparators) {
            autoDetector.autoDetectSeparators = args.autoDetectSeparators
        }
        if (!args.separator) {
            def detectedSeparator = autoDetector.autoDetectSeparator()
            if (detectedSeparator) args.separator = detectedSeparator
            else args.remove("separator")
        }
        if(!args.quoteChar) {
            def detectedQuoteChar = autoDetector.autoDetectQuoteChar()
            if (detectedQuoteChar) args.quoteChar = detectedQuoteChar
            else args.remove("quoteChar")
        }
        return args
    }
    
}
