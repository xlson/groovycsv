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
 * Iterates over the csv data in a non-synchronized way.
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvIterator implements Iterator {

    private def columns

    private CSVReader csvReader

    private def readValue

    private Boolean closed = false

    def CsvIterator(def columnNames, CSVReader csvReader) {
        this.columns = [:]
        columnNames.eachWithIndex { name, i ->
            columns."$name" = i
        }

        this.csvReader = csvReader
    }

    /**
     * Closes the underlying reader object. Could be useful if one would
     * not like to read all of the csv into memory.
     *
     * @throws IllegalStateException if the underlying dataset is already closed.
     */
    void close() {
        throwsExceptionIfClosed()

        closed = true
        csvReader.close()
    }

    /**
     * Checks if there is more data available. Will close the underlying dataset
     * if there isn't any more data.
     *
     * @return true if there is more data in the iterator
     */
    boolean hasNext() {
        if(isClosed()) {
            return false
        }
        else if(nextValueIsRead()) {
            return true
        } else {
            readValue = csvReader.readNext()
            if(readValue == null) {
                close()
            }
            return readValue != null
        }

    }

    /**
     * Checks if the underlying reader is closed.
     *
     * @return true if the underlying reader is closed
     */
    boolean isClosed() {
        closed
    }

    private boolean nextValueIsRead() {
        readValue as boolean
    }

    private def getNextValue() {
        if(nextValueIsRead()) {
            def value = readValue
            readValue = null
            return value
        } else {
            return csvReader.readNext()
        }
    }


    /**
     * Gets the next row in the csv file.
     *
     * @return an instance of <code>PropertyMapper</code>
     */
    def next() {
        throwsExceptionIfClosed()

        new PropertyMapper(columns: columns, values: nextValue)
    }

    private def throwsExceptionIfClosed() {
        if (isClosed()) {
            throw new IllegalStateException("The connection the underlying dataset has already been closed.")
        }
    }

    /**
     * remove is not supported in CsvIterator.
     *
     * @throws UnsupportedOperationException when called
     */
    void remove() {
        throw new UnsupportedOperationException()
    }
}
