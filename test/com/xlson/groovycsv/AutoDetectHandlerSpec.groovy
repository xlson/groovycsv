package com.xlson.groovycsv

import spock.lang.Specification;

class AutoDetectHandlerSpec extends Specification {
    
    
    def "should auto detect quote character"() {
        setup:
        def adh = new AutoDetectHandler(linesToInspect: csvData)
        
        expect:
        adh.autoDetectQuoteChar() == quoteChar
        
        where:
        csvData                         | quoteChar
        testDataWithColumnNamesAnd3Rows | null
        testDataWithColumnNamesAnd2Rows | null
        csvUsingDoubleQuoteAsQuoteChar  | '"'
        csvUsingPercentageAsQuoteChar   | "%"
        
    }
    
    def "should auto detect separator"() {
        setup:
        def adh = new AutoDetectHandler(linesToInspect: csvData)
        
        expect:
        adh.autoDetectSeparator() == separator
        
        where:
        csvData                         | separator
        testDataWithColumnNamesAnd3Rows | ","
        testDataWithColumnNamesAnd2Rows | ","
        csvUsingDoubleQuoteAsQuoteChar  | ','
        csvWithColonAsSeparator         | ":"
    }

    
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
    
    def getCsvUsingDoubleQuoteAsQuoteChar() {
        '''Typo,Desc
123,"text ,and more"'''
    }

    def getCsvUsingPercentageAsQuoteChar() {
        '''Typo,Desc
1123,%bla, ha%'''

    }

}
