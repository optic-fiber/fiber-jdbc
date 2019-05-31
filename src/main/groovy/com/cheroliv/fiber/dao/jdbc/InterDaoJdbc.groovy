package com.cheroliv.fiber.dao.jdbc

import com.cheroliv.fiber.dao.InterDao
import com.cheroliv.fiber.domain.Inter
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Repository
class InterDaoJdbc implements InterDao {
    @Autowired
    JdbcTemplate jdbcTemplate

    @Transactional
    @Override
    void create(Inter inter) {
        jdbcTemplate.update(
                "insert into fiber.Inter (ND, nom, prenom, heure, `date`, contrat, type) " +
                        "values('${inter.nd}', " +
                        "'${inter.nom}', " +
                        "'${inter.prenom}', " +
                        "MAKETIME('${inter.heure}',0,0), " +
                        "'${inter.date}', " +
                        "'${inter.contrat}', " +
                        "'${inter.type}')"
        )
    }

    @Transactional
    @Override
    void update(Inter inter) {
        jdbcTemplate.update(
                "update fiber.Inter set " +
                        "ND = '${inter.nd}', " +
                        "nom = '${inter.nom}', " +
                        "prenom = '${inter.prenom}', " +
                        "heure = MAKETIME('${inter.heure}',0,0)," +
                        "date = '${inter.date}', " +
                        "contrat = '${inter.contrat}' " +
                        "where nd = '${inter.nd}' and" +
                        "type = '${inter.type}'"

        )


    }

    @Transactional
    @Override
    void delete(String nd, String type) {
        jdbcTemplate.update "delete from Inter where nd='$nd' and type='$type'"
    }

    @Override
    Inter find(String nd, String typed) {
        List<Inter> result = jdbcTemplate.query(
                "select * from fiber.Inter where nd='$nd' and type='$typed'",
                new InterRowMapper())
        result ? result.first() : null
    }

    @Override
    List<Inter> findAllInter() {
        jdbcTemplate.query(
                "select * from fiber.Inter",
                new InterRowMapper())
    }


    @Override
    Integer count() {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter",
                Integer.class)
    }


    //recuperer le nombre de mois nbMois donne nombre de feuille
    //select count(distinct MONTH(date), YEAR(date)) from fiber.Inter;
    @Override
    Integer countMois() {
        jdbcTemplate.queryForObject(
                "select count(distinct MONTH(date), YEAR(date)) from fiber.Inter",
                Integer.class)
    }

    //tous les mois par année
    //select distinct MONTH(date), YEAR(date) from fiber.Inter;
    @Override
    List<Map<String, Integer>> findAllMoisFormatFrParAnnee() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(
                "select distinct MONTH(date), YEAR(date) from fiber.Inter")
        List<Map<String, Integer>> finalResult =
                new ArrayList<HashMap<String, Integer>>(result.size())
        result.eachWithIndex { item, idx ->
            //todo:handle if date is null on db
            String mois = item.get("MONTH(date)") as String
            Integer annee = (item.get("YEAR(date)") as String).toInteger()
            Map<String, Integer> map = new HashMap<String, Integer>(1)
            map.put(Inter.convertNombreEnMois(mois.toInteger()), annee)
            finalResult.add(idx, map)
        }
        finalResult
    }


    //tous les mois par année
    //select distinct MONTH(date), YEAR(date) from fiber.Inter;
    @Override
    List<Map<String, Integer>> findAllMoisParAnnee() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(
                "select distinct MONTH(date), YEAR(date) from fiber.Inter")
        List<Map<String, Integer>> finalResult =
                new ArrayList<HashMap<String, Integer>>(result.size())
        result.eachWithIndex { item, idx ->
            String mois = item.get("MONTH(date)") as String
            Integer annee = (item.get("YEAR(date)") as String).toInteger()
            Map<String, Integer> tree = new HashMap<String, Integer>(1)
            tree.put mois, annee
            finalResult.add(idx, tree)
        }
        finalResult
    }

    //chercher les inters par mois d'une année
    //select * from fiber.Inter where MONTH(date)=$mois and YEAR(date)=$annee;
    @Override
    List<Inter> findAllInterDeMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.query(
                "select * from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee'",
                new InterRowMapper())
    }

    //recuperer le nombre dinter par mois
    //select count(*) from fiber.Inter where MONTH(date)=$mois and YEAR(date)=$annee;
    @Override
    Integer countInterParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee'",
                Integer.class)
    }

    //select count(*) from fiber.Inter where MONTH(date)=$mois
    // and YEAR(date)=$annee and type='PLP';
    @Override
    Integer countPlpParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and type='PLP'",
                Integer.class)
    }
    //BAAP BAOC BAFA BAST
    //select count(*) from fiber.Inter where MONTH(date)=$mois
    // and YEAR(date)=$annee and type='PLP'
    @Override
    Integer countRacParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and (type='BAAP' or type='BAOC' " +
                        "or type='BAFA' or type='BAST')" +
                        "and contrat!='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countBaapParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and type='BAAP' and contrat!='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countBaocParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and type='BAOC' and contrat!='Passage de cable'",
                Integer.class)

    }

    @Override
    Integer countBafaParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and type='BAFA' and contrat!='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countBastParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and type='BAST' and contrat!='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countSavParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and type='SAV'",
                Integer.class)
    }

    @Override
    Integer countPdcParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and contrat='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countRacSimpleParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and (type='BAAP' or type='BAOC')" +
                        "and contrat!='Passage de cable'",
                Integer.class)
    }

    @Override
    Integer countPdcBafaParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and contrat='Passage de cable' and type='BAFA'",
                Integer.class)
    }

    @Override
    Integer countPdcBaocBaapParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)='$mois' and YEAR(date)='$annee' " +
                        "and contrat='Passage de cable' " +
                        "and (type='BAOC' or type='BAAP')",
                Integer.class)
    }

    @Override
    Integer countPdcBastParMoisDansAnnee(Integer mois, Integer annee) {
        jdbcTemplate.queryForObject(
                "select count(*) from fiber.Inter " +
                        "where MONTH(date)=$mois and YEAR(date)='$annee' " +
                        "and contrat='Passage de cable'" +
                        " and type='BAST'",
                Integer.class)
    }
}
