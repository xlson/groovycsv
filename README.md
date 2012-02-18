# GroovyCSV

GroovyCSV is a library for Groovy which aims to make csv data
easier (and more idiomatically Groovy) to work with. The library was inspired by @[goeh's](http://twitter.com/goeh)
[ExcelBuilder](http://www.technipelago.se/blog/?p=44) that lets you
iterate over rows in the excel file using `eachLine` and access values
using the column names.

*Important*

Package structure was changed from `com.xlson.csvparser` to
`com.xlson.groovycsv` between release 0.1 and 0.2.

## Features

* Value-access by header name or position
* Iteration using the ordinary collection methods (`findAll`, `collect`
  and so on)
* Full support for OpenCSV's configurability
* Support for guessing separator and/or quote character
* Support for reading csv without headers

## Example

The parse method returns an iterator over the rows in the csv. This
means we can use any of the default groovy ways to iterate, in this
example we see the for each loop in use.

    @Grab('com.xlson.groovycsv:groovycsv:0.2')
    import com.xlson.groovycsv.CsvParser
    
    def csv = '''Name,Lastname
    Mark,Andersson
    Pete,Hansen'''
    
    def data = new CsvParser().parse(csv)
    for(line in data) {
        println "$line.Name $line.Lastname"
    }

The parse method takes a String or a Reader as argument.

**Output:**

    Mark Andersson
    Pete Hansen

## Getting GroovyCSV

GroovyCSV is available in Maven Central. It is also available directly from GitHub
(links below). 

### Maven & Ivy configuration

#### Latest stable

* *GroupId:* com.xlson.groovycsv
* *ArtifactId:* groovycsv
* *Version:* 1.0

#### Latest snapshot

* *Version:* 1.0-SNAPSHOT
* *Repository:* https://oss.sonatype.org/content/groups/public/

### Downloads

*GroovyCSV 1.0*

* [groovycsv-1.0.jar](https://github.com/downloads/xlson/groovycsv/groovycsv-0.2.jar)
* [groovycsv-1.0-javadoc.jar](https://github.com/downloads/xlson/groovycsv/groovycsv-0.2-javadoc.jar)
* [Javadoc Online](http://xlson.github.com/groovycsv/docs/1.0/javadoc/)

## Dependencies

* [Groovy 1.7.x](http://groovy.codehaus.org)
* [OpenCSV 2.x](http://opencsv.sourceforge.net/)

Many thanks to Glen Smith and the other's in the OpenCSV team for
doing all the heavy lifting.

## Building

GroovyCSV uses Gradle for building as is packaged with the gradle wrapper which will download and install gradle for you behind the scenes the first time you run it.

**Build instruction**

1. Fetch the latest code: `git clone git://github.com/xlson/groovycsv.git`
2. (Optional) Run the tests using the gradle wrapper `./gradlew test`
4. Go to the project directory and run: `./gradlew jar`

You will find the built jar in `./build/libs`. If you need any
dependencies you can download them using `./gradlew downloadDeps`, they
end up in the `lib` folder.
