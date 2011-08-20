package com.xlson.groovycsv

import spock.lang.Specification

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

}
