package com.cheroliv.fiber.service

import com.cheroliv.fiber.dao.InterDao
import com.cheroliv.fiber.data.ClasseurRecap
import com.cheroliv.fiber.data.Recap
import com.cheroliv.fiber.domain.Inter
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import java.nio.file.LinkOption
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.LocalDate

import static com.cheroliv.fiber.domain.Inter.convertNombreEnMois
import static com.cheroliv.fiber.domain.Inter.timeStringToInteger
import static java.nio.file.Files.exists
import static java.nio.file.Files.isDirectory
import static java.nio.file.Paths.get
import static java.time.format.DateTimeFormatter.ofPattern
import static org.apache.commons.io.FilenameUtils.removeExtension

@Slf4j
@Service
class InterServiceImpl implements InterService {

    @Autowired
    InterDao interDao
    @Value('${classeurPathName}')
    String classeurPathName
    ClasseurRecap classeur
    @Value('${fiberUserDataFolderName}')
    String fiberUserDataFolderName
    @Value('${jsonBackUpFileName}')
    String jsonBackUpFileName


    String getFiberJsonFilePath() {
        String separator = get(System.getProperty("user.home"))
                .fileSystem.separator
        Path path = get "${System.getProperty("user.home")}$separator$fiberUserDataFolderName$separator$jsonBackUpFileName"
        path.toString()
    }


    /**
     * créer le dossier $fiberUserDataFolderName, à la racine du l'utilisateur de l'application
     */
    @Override
    void setUp() {
        String separator = get(System.getProperty("user.home"))
                .fileSystem.separator
        Path path = get "${System.getProperty("user.home")}$separator" +
                "$fiberUserDataFolderName"
        exists(path, LinkOption.NOFOLLOW_LINKS) &&
                isDirectory(path, LinkOption.NOFOLLOW_LINKS) ?:
                path.toFile().mkdir()
        assert path.toFile().exists()
        assert path.toFile().directory
    }

    /**
     *
     * @param countMois
     * @return
     */
    private List<String> nomFeuilles(Integer countMois) {
        List<Map<String, Integer>> list = interDao.findAllMoisFormatFrParAnnee()
        List<String> finalList = new ArrayList(countMois)
        list.eachWithIndex { item, idx ->
            String key = (item as Map<String, Integer>).keySet().first()
            String value = (item as Map<String, Integer>).get(key)
            String monthYearLabel = "$key $value"
            finalList.add idx, monthYearLabel
        }
        finalList
    }

    @Override
    void processClasseurFeuilles() {
        /**
         * Passer au constructeur toutes les données necessaire
         * pour construire le classeur
         */
        Integer nbMois = interDao.countMois()
        //todo ranger le classeur dans le dossier "user.home"/.fiber/output/
        classeur = new ClasseurRecap(
                classeurPathName: classeurPathName,
                nbFeuille: nbMois,
                nomFeuilles: nomFeuilles(nbMois),
                moisParAnnee: interDao.findAllMoisParAnnee())
        processFeuille()
        classeur.createExcelWorkBook()
    }

    @Override
    void saveToJsonFile(String path) throws IOException {
        List<String> jsonList = new ArrayList<String>()
        JsonBuilder builder = new JsonBuilder()
        //building json
        interDao.findAllInter().each { it ->
            Inter inter = it
            String jsonString = builder {
                '"id_inter"' "\"${inter.id}\""
                '"ND"' "\"${inter.nd}\""
                '"nom"' "\"${inter.nom}\""
                '"prenom"' "\"${inter.prenom}\""
                '"heure"' inter.heure > 9 ? "\"${inter.heure}:00:00\"" : "\"0${inter.heure}:00:00\""
                '"date"' "\"${inter.date.format ofPattern("yyyy-MM-dd")}\""
                '"contrat"' "\"${inter.contrat}\""
                '"type"' "\"${inter.type}\""
            }.toString()
            jsonString = "{${jsonString.substring(1, jsonString.size() - 1)}}"
            jsonList.add "${jsonString}\n"
        }

        //saving to file
        String ajout = new SimpleDateFormat("yyyyMMddHHmmss").format new Date()
        new File(path).renameTo "${removeExtension path}${ajout}.json"
        File jsonBackUpFile = new File(path)
        jsonBackUpFile.createNewFile()
        jsonBackUpFile.text = jsonList.toString()
    }

    @Override
    void importFromJsonFile(String path) throws IOException {
        Object jsonInters = new JsonSlurper().parse new File(path)
        jsonInters.each {
            interDao.find(it.ND, it.type) ?:
                    interDao.create(new Inter(
                            nd: it.ND,
                            nom: it.nom,
                            prenom: it.prenom,
                            heure: timeStringToInteger(it.heure as String),
                            date: LocalDate.parse(
                                    it.date,
                                    ofPattern("yyyy-MM-dd")),
                            contrat: it.contrat,
                            type: it.type))
        }
    }

    private void processFeuille() {
        classeur.recaps = new ArrayList<Recap>(classeur.nbFeuille)
        assert classeur.nbFeuille == classeur.moisParAnnee.size()
        if (classeur.nbFeuille == null || classeur.nbFeuille <= 0) {
            classeur.recaps = new ArrayList<Recap>()
            classeur.nbFeuille++
        } else {
            classeur.moisParAnnee.eachWithIndex { map, idx ->
                String moisStrKey = map.keySet().first()
                Integer anneeIntValue = map.get(moisStrKey)
                Integer moisInt = moisStrKey.toInteger()
                classeur.recaps.add(idx,
                        new Recap(
                                sheetName: classeur.nomFeuilles.get(idx),
                                inters: interDao
                                        .findAllInterDeMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                annee: anneeIntValue,
                                mois: moisStrKey.toInteger(),
                                nbInterTotal: interDao
                                        .countInterParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbBaocBaap: interDao
                                        .countRacParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbBafa: interDao
                                        .countBafaParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbBast: interDao
                                        .countBastParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbPlp: interDao
                                        .countPlpParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbSav: interDao
                                        .countSavParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbPdcTotal: interDao
                                        .countPdcParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbPdcBafa: interDao
                                        .countPdcBafaParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbPdcBast: interDao
                                        .countPdcBastParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                nbPdcBaocBaap: interDao
                                        .countPdcBaocBaapParMoisDansAnnee(
                                                moisInt, anneeIntValue),
                                labelTitreRecap:
                                        "${new Recap().labelTitreRecap}" +
                                                "${convertNombreEnMois(moisInt)}" +
                                                " $anneeIntValue",
                                labelCurrentMonthYearFormattedFr:
                                        convertNombreEnMois(moisInt)))

            }
        }
    }
}
