package com.xlson.groovycsv

import spock.lang.Specification

class PropertyMapperSpec extends Specification {

    def getTestDataWithDuplicatedLines() {
        '''a,b
1,2
1,2
3,4'''
    }

    def "PropertyMapper has a toMap() that returns a map representation."() {
        setup:
        def pm = new PropertyMapper(values: values, columns: columns)

        expect:
        pm.toMap() == toMapRepresentation

        where:
        columns                  | values          | toMapRepresentation
        ['a': 0, 'b': 1, 'c': 2] | ['1', '2', '3'] | ['a': '1', 'b': '2', 'c': '3']
        ['Name': 0, 'Age': 1]    | ['Mark', '56']  | ['Name': 'Mark', 'Age': '56']
    }

    def "PropertyMapper has a toString() that returns all the data in it's columns."() {
        setup:
        def pm = new PropertyMapper(values: values, columns: columns)

        expect:
        pm.toString() == toStringRepresentation

        where:
        columns                  | values          | toStringRepresentation
        ['a': 0, 'b': 1, 'c': 2] | ['1', '2', '3'] | "a: 1, b: 2, c: 3"
        ['Name': 0, 'Age': 1]    | ['Mark', '56']  | "Name: Mark, Age: 56"
    }

}
