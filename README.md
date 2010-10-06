# GroovyCSV

GroovyCSV is a library for Groovy which aims to make csv data
easier to work with. The library was inspired by @[goeh's](http://twitter.com/goeh)
[ExcelBuilder](http://www.technipelago.se/blog/?p=44) that lets you
iterate over rows in the excel file using `eachLine` and access values
using the column names.

At the moment there's no official release so you will have to download
and build it yourself using the [Gradle](http://www.gradle.org/) build
script.

*Important*

Package structure was changed from `com.xlson.csvparser` to
`com.xlson.groovycsv` between release 0.1 and 0.2.

## Features

* Value-access by header name
* Iteration using the ordinary collection methods (`findAll`, `collect`
  and so on)
* Full support for OpenCSV's configurability

## Example

The parse method returns an iterator over the rows in the csv. This
means we can use any of the default groovy ways to iterate, in this
example we see the for each loop in use.

    def csv = '''Name,Lastname
    Mark,Andersson
    Pete,Hansen'''

    def data = new CsvParser().parse(csv)
    for(line in data) {
        println "$line.Name $line.Lastname"
    }

**Output:**

    Mark Andersson
    Pete Hansen

## Building

csv-parser uses Gradle for building. Gradle handles the dependencies
for you so all you need to do is install gradle and then build the 
code. 

**Build instruction**

1. Download and install [Gradle 0.9-preview-3](http://www.gradle.org/downloads.html)
2. Fetch the latest code: `git clone git://github.com/xlson/csv-parser.git`
3. (Optional) Run the tests using `gradle test`
4. Go to the project directory and run: `gradle jar`

You will find the built jar in `./build/libs`. If you need any
dependencies you can download them using `gradle downloadDeps`, they
end up in the `lib` folder.

## Dependencies

* [Groovy 1.7.x](http://groovy.codehaus.org)
* [OpenCSV 2.0](http://opencsv.sourceforge.net/)

Many thanks to Glen Smith and the other's in the OpenCSV team for
doing all the heavy lifting.

## Plans

* Validation
* Conversion support for primitives
