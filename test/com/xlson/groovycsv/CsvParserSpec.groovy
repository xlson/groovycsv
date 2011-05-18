package com.xlson.groovycsv

import spock.lang.*
import au.com.bytecode.opencsv.CSVReader

class CsvParserSpec extends Specification {
    def getTestDataWithColumnNamesAnd3Rows() {
        '''Name,Lastname,Age,Email
Mark,Hamilton,45,mark@hamilton.com
Bosse,Bildoktorn,50,bildoktorn@tv4.se
Peps,Persson,65,peps.persson@hotmail.com'''
    }

    def getTestDataWithColumnNamesAnd2Rows() {
        '''Letter,Word,Number
a,paris,5
h,drink,60'''
    }

    def getCsvWithColonAsSeparator() {
        '''Fruit:Count
Apple:5
Pear:10
Kiwi:200'''        
    }

    def "Iterating over the parsed csv values are available by column name."() {
        setup:
        def data = new CsvParser().parse(getTestDataWithColumnNamesAnd3Rows())

        expect:
        data*."$columnName" == values

        where:
        columnName  |   values
        "Name"      |   ['Mark', 'Bosse', "Peps"]
        "Lastname"  |   ['Hamilton', 'Bildoktorn', 'Persson']
        'Age'       |   ['45', '50', '65']
        "Email"     |   ['mark@hamilton.com', 'bildoktorn@tv4.se', 'peps.persson@hotmail.com']
    }

    def "Functional collection methods are available on parsed object."() {
        setup:
        def data = new CsvParser().parse(getTestDataWithColumnNamesAnd3Rows())

        expect:
        data.findAll { (it.Age as int) > 46 }.size() == 2 
    }

    def "PropertyMapper has a toString() that returns all the data in it's columns."() {
        setup:
        def pm = new PropertyMapper(values: values, columns: columns)

        expect:
        pm.toString() == toStringRepresentation

        where:
        columns                     |   values          |   toStringRepresentation
        ['a': 0, 'b': 1, 'c': 2]    |   ['1', '2', '3'] |   "a: 1, b: 2, c: 3"
        ['Name': 0, 'Age': 1]       |   ['Mark', '56']  |   "Name: Mark, Age: 56"   
    }

    def "readAll should never be called on the CSVReader instance used to parse the csv."() {
        setup:
        CSVReader csvReader = Mock(CSVReader)
        def partiallyMockedCsvParser = new CsvParser()
        partiallyMockedCsvParser.metaClass.createCSVReader = { Reader reader ->
            csvReader
        }

        when: "csv is parsed and looped through"
        def data = partiallyMockedCsvParser.parse(getTestDataWithColumnNamesAnd2Rows())
        for(d in data) {}

        then: "readAll() should not be called."
        0 * csvReader.readAll()
    }

    def "Parse supports a custom separator."() {
        setup:
        def data = new CsvParser().parse(csvWithColonAsSeparator, separator: ':')

        expect:
        data*."$columnName" == values

        where:
        columnName      |   values
        "Fruit"         |   ['Apple', 'Pear', 'Kiwi']
        "Count"         |   ["5", "10", "200"]
    }

    def getCsvUsingDoubleQuoteAsQuoteChar() {
        '''Typo,Desc
123,"text ,and more"'''
    }

    def getCsvUsingPercentageAsQuoteChar() {
        '''Typo,Desc
1123,%bla, ha%'''

    }

    def "Parse supports custom quote character."() {
        when:
        def csv = new CsvParser().parse(csvData, quoteChar: quoteChar)

        then:
        csv*."$columnName" == values

        where:
        csvData                         |   quoteChar   |   values              |   columnName
        csvUsingDoubleQuoteAsQuoteChar  |   '"'         |   ['text ,and more']   |   "Desc"
        csvUsingPercentageAsQuoteChar   |   "%"         |   ['bla, ha']          |   "Desc"
    }

    def "Parse supports custom escape char."() {
        setup:
        def csvData = '''Test,It
1,"this is \"a quote\""'''
        def csv = new CsvParser().parse(csvData, escapeChar: "\\")

        expect:
        csv*.It == ['this is "a quote"']

    }

    def "Parse supports java.io.Reader as input."() {
        when:
        def csv = new CsvParser().parse(new StringReader(testDataWithColumnNamesAnd2Rows))

        then:
        csv*.Number == ['5', '60']
    }

    def "CsvParser should auto detect separator and quote character"() {
        when: "a CSV file is parsed with auto detection"
        def csv = new CsvParser().parse(autoDetect: true, csvData)

        then: "it should return the correct columns"
        csv*."$property" == values
        
        where:
        csvData                         | property | values
        testDataWithColumnNamesAnd3Rows | "Age"    | ["45", "50", "65"]
        csvWithColonAsSeparator         | "Count"  | ["5", "10", "200"]
        csvUsingDoubleQuoteAsQuoteChar  | "Desc"   | ["text ,and more"]
        csvUsingDoubleQuoteAsQuoteChar  | "Typo"   | ["123"]
        testDataWithColumnNamesAnd2Rows | "Word"   | ["paris", "drink"]
        testDataWithColumnNamesAnd3Rows | "Email"  | ["mark@hamilton.com", "bildoktorn@tv4.se", "peps.persson@hotmail.com"]
    }
    
    def "should allow to override auto detection"() {
        when: "autoDetect is active and a separator is provided"
        def csv = new CsvParser().parse(autoDetect: true, separator: ',', csvWithColonAsSeparator)
        
        then: "the separator provided is used"
        csv*."Fruit:Count" == ["Apple:5", "Pear:10", "Kiwi:200"]
    }
    
}
