@GrabResolver(name='Nexus OSS Repo', root='https://oss.sonatype.org/content/groups/public/', m2Compatible='true')
@Grab('com.xlson.groovycsv:groovycsv:0.2-SNAPSHOT')
import com.xlson.groovycsv.CsvParser

def csv = '''Name,Lastname
Mark,Andersson
Pete,Hansen'''

def data = new CsvParser().parse(csv)
for(line in data) {
    println "$line.Name $line.Lastname"
}
