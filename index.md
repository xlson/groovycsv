---
title: xlson/groovycsv @ GitHub
layout: default
---

GroovyCSV is a library to make csv processing just a little bit Groovier. The library uses [opencsv](http://opencsv.sourceforge.net/) behind the scenes and merely tries to add a thin layer of "Groovy-ness" to the mix.

## Dependencies

[opencsv 2.1](http://opencsv.sourceforge.net/)

## Documentation and more information

* [Javadoc](docs/0.2/javadoc/)
* [GroovyCSV](http://xlson.com/2010/11/08/groovycsv-0.2-released.html) 0.2 Released

## Features

* Value-access by header name or position
* Iteration using the ordinary collection methods (`findAll`, `collect`
  and so on)
* Full support for OpenCSV's configurability
* Support for guessing separator and/or quote character
* Support for reading csv without headers

## Usage example


#### Basic usage

    @Grab('com.xlson.groovycsv:groovycsv:1.0')
    import com.xlson.groovycsv.CsvParse
    def csv = '''Name-Lastname
    Mark-'Anderson-Nielsen'
    Pete-Hansen'''
    
    def data = new CsvParser().parse(csv, separator: '-', quoteChar: "'")
    for(line in data) {
        println "$line.Name $line.Lastname"
    }

#### Parsing a headerless file

    @Grab('com.xlson.groovycsv:groovycsv:1.0')
    import com.xlson.groovycsv.CsvParser
    def csv = '''Apple,2
    Pear,5'''
 
    def data = new CsvParser().parse(csv, readFirstLine:true,
                                     columnNames:['fruit', 'qty'])
    for(line in data) {
        println "$line.fruit ${line[1]}"
    }

## License

Apache V2


## Authors
Leonard Axelsson (leo@xlson.com)

## Download

You can download this project in either [zip](http://github.com/xlson/groovycsv/zipball/master) or [tar](http://github.com/xlson/groovycsv/tarball/master) formats.

You can also clone the project with [Git](http://git-scm.com) by running:
`$ git clone git://github.com/xlson/groovycsv`
