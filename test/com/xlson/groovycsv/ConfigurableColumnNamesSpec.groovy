package com.xlson.groovycsv

import spock.lang.Specification
import au.com.bytecode.opencsv.CSVReader

class ConfigurableColumnNamesSpec extends Specification {

    def csvWithoutCoulmnNames = """field1,field2,field3,
Joe,Doe,18
Jane,Doe,24"""

    def "Replaces existing column names with new ones"() {
        setup:
        def persons = new CsvParser().parse(csvWithoutCoulmnNames, columnNames: ['name', 'lastname', 'age'])

        when:
        def joe = persons.next()

        then:
        joe.name == 'Joe'
        joe.lastname == 'Doe'
        joe.age == '18'
    }

    def "Read all lines of csv content using custom column as column names"() {
        setup:
        def persons = new CsvParser().parse(csvWithoutCoulmnNames, readAllLinesAsContent: true, columnNames: ['name', 'lastname', 'age'])

        when:
        def names = persons*.name

        then:
        names == ['field1', 'Joe', 'Jane']
    }

    def "Parses columns from the first line by default"() {
        setup:
        def reader = Mock(CSVReader)

        when:
        def columnNames = new CsvParser().parseColumnNames([:], reader)

        then:
        reader.readNext() >> ['a', 'b', 'c']
        columnNames == ['a', 'b', 'c']
    }

    def "Throws away the first line by default when using custom column names."() {
        setup:
        def reader = Mock(CSVReader)

        when:
        def customColumnNames = ['1', '2', '3']
        def columnNames = new CsvParser().parseColumnNames([columnNames:customColumnNames], reader)

        then:
        1 * reader.readNext()
        columnNames == customColumnNames
    }

    def "Does not read the first line as header when readAllLinesAsContent is specified."() {
        setup:
        def reader = Mock(CSVReader)

        when:
        def customColumnNames = ['1', '2', '3']
        def columnNames = new CsvParser().parseColumnNames([columnNames:customColumnNames,
                                                            readAllLinesAsContent: true],
                                                           reader)
        then:
        0 * reader.readNext()
        columnNames == customColumnNames
    }

    def "Throws CsvParseException when readAllLinesAsContent is specified without specifying columnNames."() {
        setup:
        def reader = Mock(Reader)

        when:
        new CsvParser().parse(readAllLinesAsContent: true, reader)
        then:
        thrown(CsvParseException)

    }

}
