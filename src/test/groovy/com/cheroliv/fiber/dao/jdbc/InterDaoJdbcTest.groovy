package com.cheroliv.fiber.dao.jdbc

import com.cheroliv.fiber.dao.InterDao
import com.cheroliv.fiber.domain.Inter
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

import java.time.LocalDate

import static com.cheroliv.fiber.domain.Inter.timeStringToInteger
import static java.time.format.DateTimeFormatter.ofPattern


@Slf4j
@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@JdbcTest
@ContextConfiguration(locations = ["classpath:applicationContext-test.xml"])
class InterDaoJdbcTest {

    @Autowired
    ApplicationContext applicationContext
    @Autowired
    JdbcTemplate jdbcTemplate
    @Autowired
    InterDao interDao
    @Value(value = '${spring.datasource.url}')
    String connectionUrl
    @Value(value = '${spring.datasource.driverClassName}')
    String driverClass
    @Value(value = '${spring.datasource.username}')
    String username
    @Value(value = '${spring.datasource.password}')
    String password

    final List<Map<String, String>> getJsonInters() {
        new JsonSlurper().parse(applicationContext
                .getResource("classpath:inter.json")
                .file) as List<Map<String, String>>
    }


    @BeforeEach
    void setUp() {
        log.info "@BeforeEach"
    }

    @AfterEach
    void tearDown() {
        log.info "@AfterEach"
    }

    @Test
    @DisplayName("InterDaoJdbcTest.count")
    void count() {
        List<Map<String, String>> datas = jsonInters
        Integer expectedCount = datas.size()
        datas.each {
            Inter inter = new Inter(
                    nd: it.ND,
                    nom: it.nom,
                    prenom: it.prenom,
                    heure: timeStringToInteger(it.heure as String),
                    date: LocalDate.parse(
                            it.date,
                            ofPattern("yyyy-MM-dd")),
                    contrat: it.contrat,
                    type: it.type)
            jdbcTemplate.execute("insert into Inter (ND, nom, prenom, heure, `date`, contrat, type) " +
                    "values('${inter.nd}', " +
                    "'${inter.nom}', " +
                    "'${inter.prenom}', " +
                    "MAKETIME('${inter.heure}',0,0), " +
                    "${inter.heure}:00:00" +
                    "'${inter.date}', " +
                    "'${inter.contrat}', " +
                    "'${inter.type}')")
        }
        Assertions.assertEquals expectedCount, interDao.count()
    }

//    @Test
//    void create() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void find() {
//    }
//
//    @Test
//    void findAllInter() {
//    }
//
//    @Test
//    void countMois() {
//    }
//
//    @Test
//    void findAllMoisFormatFrParAnnee() {
//    }
//
//    @Test
//    void findAllMoisParAnnee() {
//    }
//
//    @Test
//    void findAllInterDeMoisDansAnnee() {
//    }
//
//    @Test
//    void countInterParMoisDansAnnee() {
//    }
//
//    @Test
//    void countPlpParMoisDansAnnee() {
//    }
//
//    @Test
//    void countRacParMoisDansAnnee() {
//    }
//
//    @Test
//    void countBaapParMoisDansAnnee() {
//    }
//
//    @Test
//    void countBaocParMoisDansAnnee() {
//    }
//
//    @Test
//    void countBafaParMoisDansAnnee() {
//    }
//
//    @Test
//    void countBastParMoisDansAnnee() {
//    }
//
//    @Test
//    void countSavParMoisDansAnnee() {
//    }
//
//    @Test
//    void countPdcParMoisDansAnnee() {
//    }
//
//    @Test
//    void countRacSimpleParMoisDansAnnee() {
//    }
//
//    @Test
//    void countPdcBafaParMoisDansAnnee() {
//    }
//
//    @Test
//    void countPdcBaocBaapParMoisDansAnnee() {
//    }
//
//    @Test
//    void countPdcBastParMoisDansAnnee() {
//    }
}