#!/usr/bin/env groovy
import com.cheroliv.fiber.domain.Inter
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

//executer ce script depuis de src/test/groovy

String projectBaseDirPath = new File(System.getProperty("user.dir"))
        .parentFile.parentFile.parentFile.canonicalPath
String jsonDataFilePath = projectBaseDirPath +
        "/data/inter.json"
String xmlDataSetFilePath = projectBaseDirPath +
        "/src/test/resources/interXmlDataset.xml"

File jsonDataFile = new File(jsonDataFilePath)
assert jsonDataFile.exists()
File interXmlDatasetFile = new File(xmlDataSetFilePath)


interXmlDatasetFile.exists() ?: interXmlDatasetFile.createNewFile()

//println "user.home: ${System.getProperty("user.home")}\n" +
//        "user.dir: ${System.getProperty("user.dir")}\n" +
//        "projectBaseDirPath: $projectBaseDirPath\n" +
//        "jsonDataFilePath: $jsonDataFilePath\n" +
//        "xmlDataSetFilePath: $xmlDataSetFilePath"

//jsonDataFile.eachWithIndex { it, idx ->
//    idx > 10 ?: println(it)
//}

//println inter.toString()
String xmlDeclaration = """<?xml version="1.0" encoding="UTF-8"?>\n"""


//from json to xml to get a proper dbunit dataset
String xmlDataset = new JsonSlurper().parseText(jsonDataFile.text).with { j ->
    new StringWriter().append(xmlDeclaration).with { sw ->
        Set keySet = ((j as List).first() as Map).keySet()
        MarkupBuilder builder = new MarkupBuilder(sw)
        builder.'dataset' {
            j.each { k ->
                "${Inter.simpleName}"(
                        "${keySet.getAt(0)}": (k as Map).get(keySet.getAt(0)),
                        "${keySet.getAt(1)}": (k as Map).get(keySet.getAt(1)),
                        "${keySet.getAt(2)}": (k as Map).get(keySet.getAt(2)),
                        "${keySet.getAt(3)}": (k as Map).get(keySet.getAt(3)),
                        "${keySet.getAt(4)}": (k as Map).get(keySet.getAt(4)),
                        "${keySet.getAt(5)}": (k as Map).get(keySet.getAt(5)),
                        "${keySet.getAt(6)}": (k as Map).get(keySet.getAt(6)))
            }
        }
        sw.toString()
    }
}
interXmlDatasetFile.withWriter('UTF-8') {
    w -> w.write xmlDataset
}