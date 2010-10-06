package com.xlson.groovycsv

import spock.lang.Specification
import au.com.bytecode.opencsv.CSVReader

class CsvIteratorSpec extends Specification {

    def getCsvData() {
        def csv = """a,b,c
1,2,3
4,5,6"""
        def csvReader = new CSVReader(new StringReader(csv))
        def columnNames = csvReader.readNext()
        return [columnNames, csvReader]
    }

    def "CsvIterator iterates correctly over the CSVReader"() {
        setup:
        def (colNames, csvReader) = csvData
        def iter = new CsvIterator(colNames, csvReader)

        expect:
        iter.hasNext()
        iter.next().a == '1'
        iter.hasNext()
        iter.next().c == '6'
        !iter.hasNext()
    }

    def "CsvIterator should close the underlying CSVReader instance when reaching the end of the file."() {
        setup:
        CSVReader csvReader = Mock(CSVReader)
        def iter = new CsvIterator(["a", "b"], csvReader)
        csvReader.readNext() >>> [["1","2"],["3","4"], null]

        when:
        iter.next()

        then:
        iter.hasNext()
        !iter.isClosed()
        0 * csvReader.close()

        when:
        iter.next()
        iter.hasNext()
        //println iter.hasNext()
        //println iter.isClosed()

        then:
        iter.isClosed()
        1 * csvReader.close()
    }

    def "close can be called on the CsvIterator to close the connection to the reader."() {
        setup:
        def (colNames, csvReader) = csvData
        def iter = new CsvIterator(colNames, csvReader)

        when:
        iter.next()
        iter.close()

        then:
        iter.isClosed()

        when:
        iter.next()

        then:
        thrown(IllegalStateException)

    }

}
