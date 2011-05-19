import com.xlson.groovycsv.CsvParser

def csv = '''Name:Lastname
Mark:Andersson
Pete:Hansen'''

def data = new CsvParser().parse(csv, autoDetect:true)
for(line in data) {
    println "$line.Name $line.Lastname"
}
