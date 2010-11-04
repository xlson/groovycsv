# GroovyCSV

GroovyCSV is a library for Groovy which aims to make csv data
easier to work with. The library was inspired by @[goeh's](http://twitter.com/goeh)
[ExcelBuilder](http://www.technipelago.se/blog/?p=44) that lets you
iterate over rows in the excel file using `eachLine` and access values
using the column names.

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

    @GrabResolver(name='Nexus OSS Repo',
        root='https://oss.sonatype.org/content/groups/public/',
        m2Compatible='true')
    @Grab('com.xlson.groovycsv:groovycsv:0.2')
    import com.xlson.groovycsv.CsvParser
    
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

## Getting GroovyCSV

GroovyCSV is currently available in Sonatypes public repository and
soon in Maven Central. It is also available directly from GitHub
(links below). You will have to add a custom maven
repository to your build as long as it hasn't been added to maven
central.

### Maven & Ivy configuration

* *Repo:* [https://oss.sonatype.org/content/groups/public/](https://oss.sonatype.org/content/groups/public/)
* *GroupId:* com.xlson.groovycsv
* *ArtifactId:* groovycsv
* *Version:* 0.2

### Downloads

*GroovyCSV 0.2*

* [groovycsv-0.2.jar](https://github.com/downloads/xlson/groovycsv/groovycsv-0.2.jar)
* [groovycsv-0.2-javadoc.jar](https://github.com/downloads/xlson/groovycsv/groovycsv-0.2-javadoc.jar)

## Dependencies

* [Groovy 1.7.x](http://groovy.codehaus.org)
* [OpenCSV 2.x](http://opencsv.sourceforge.net/)

Many thanks to Glen Smith and the other's in the OpenCSV team for
doing all the heavy lifting.

## Building

GroovyCSV uses Gradle for building. Gradle handles the dependencies
for you so all you need to do is install gradle and then build the 
code. 

**Build instruction**

1. Download and install [Gradle 0.9-rc-2](http://www.gradle.org/downloads.html)
2. Fetch the latest code: `git clone git://github.com/xlson/groovycsv.git`
3. (Optional) Run the tests using `gradle test`
4. Go to the project directory and run: `gradle jar`

You will find the built jar in `./build/libs`. If you need any
dependencies you can download them using `gradle downloadDeps`, they
end up in the `lib` folder.
