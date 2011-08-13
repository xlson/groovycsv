@Grab('com.xlson.groovycsv:groovycsv:0.2')
import com.xlson.groovycsv.CsvParser

def csv = '''Name,Lastname
Mark,Andersson
Pete,Hansen'''

def data = new CsvParser().parse(csv)
for(line in data) {
    println "$line.Name $line.Lastname"
}
