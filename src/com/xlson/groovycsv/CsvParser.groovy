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
 * <p><b>Parser configuration</b></p>
 *
 * <p>
 * There parser is configurable using named arguments. These arguments are available.
 *
 * <ul>
 * <li>separator : Sets a custom separator like tab
 * <li>quoteChar : Sets a custom quote character
 * <li>escapeChar : Sets a custom escape character for the separator and quoteChar
 * </ul>
 * </p>
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
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvParser {

    def autoDetectedSeparators = [",", ";", ":", "|"]
    
    def autoDetectedQuoteChars = ['"', "'"]
    
    def linesForAutodetection = 5
    
    /**
     * Parses the csv supplied using the reader. See parse(Reader reader) for
     * more information about usage.
     *
     * @param args configurable parameters
     * @param csv the csv to parse
     * @return an instance of <code>com.xlson.groovycsv.CsvIterator</code>
     */
    def parse(Map args = [:], String csv) {
        parse(args, new StringReader(csv))
    }

    /**
     * Parses the supplied csv  and returns a CsvIterator that can be
     * use to access the data. The first line of the csv will be used
     * as column-headers. Named paramenters can be used to configure the
     * parsing, see the class documentation for more more information on
     * usage.
     * <p>
     * Arguments for configuration:
     * <li>separator : Sets a custom separator like tab
     * <li>quoteChar : Sets a custom quote character
     * <li>escapeChar : Sets a custom escape character for the separator and quoteChar
     *
     * @param reader the csv to parse
     * @param args the configuration arguments
     * @return an instance of <code>com.xlson.groovycsv.CsvIterator</code>
     */
    def parse(Map args = [:], Reader reader) {
        def csvReader = createCSVReader(args, reader)
        def columnNames = csvReader.readNext()

        new CsvIterator(columnNames, csvReader)
    }

    private CSVReader createCSVReader(Map args = [:], Reader reader) {
        def text = reader.text
        reader = new StringReader(text)
        args = doAutoDetection(args, text)
        
        char separator = args.separator ?: ','
        char quoteChar = args.quoteChar ?: '"'
        char escapeChar = args.escapeChar

        if(escapeChar) {
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
    private Map doAutoDetection(Map args, String text) {
        if (args.separator == 'auto') {
            def detectedSeparator = autoDetectSeparator(text)
            if (detectedSeparator) args.separator = detectedSeparator
            else args.remove("separator")
        }
        if(args.quoteChar == 'auto') {
            def detectedQuoteChar = autoDetectQuoteChar(text)
            if (detectedQuoteChar) args.quoteChar = detectedQuoteChar
            else args.remove("quoteChar")
        }
        return args
    }
    
    /**
     * Run autodetection for separator.
     * @param text  The full CSV as a String.
     * @return the detected character.
     */
    private autoDetectSeparator(String text) {
        def firstLines = getFirstLines(text)
        return mostFrequentChar(firstLines, autoDetectedSeparators)
    }

    /**
    * Run autodetection for quote character.
    * @param text The full CSV as a String.
    * @return the detected character.
    */
    private autoDetectQuoteChar(String text) {
        def firstLines = getFirstLines(text)
        return mostFrequentChar(firstLines, autoDetectedQuoteChars)
    }
    
    /**
     * Find the most frequent character in a string among a list of characters.
     * 
     * @param sequence      The string to search.
     * @param characters    The list of characters to search.
     * @return  The most frequent character.
     */
    private mostFrequentChar(String sequence, List<String> characters) {
        def maxOccurences = 0
        char mostFrequentChar
        characters.each {
            def charOccurences = sequence.count(it)
            if ( charOccurences > maxOccurences) {
                mostFrequentChar = it
                maxOccurences = charOccurences
            }
        }
        return mostFrequentChar
    }
    
    /**
     * Extracts the first lines of a given text. The number of lines is
     * determined by the linesforAutodection attribute.
     * 
     */
    private String getFirstLines(String text) {
        def lines = text.readLines()
        def firstLines = ""
        for (int i = 0; i < linesForAutodetection - 1; i++) {
            firstLines += lines[i]
        }
        return firstLines
    }
}
