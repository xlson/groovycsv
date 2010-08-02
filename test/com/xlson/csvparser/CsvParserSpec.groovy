import spock.lang.*
import com.xlson.csvparser.CsvParser
import com.xlson.csvparser.PropertyMapper
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
    
}
