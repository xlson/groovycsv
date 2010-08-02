package com.xlson.csvparser

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

}
