package com.cheroliv.fiber.dao


import com.cheroliv.fiber.domain.Inter

interface InterDao {

    Inter find(String nd, String type)

    List<Inter> findAllInter()

    Integer count()

    void create(Inter inter)

    void update(Inter inter)

    void delete(String nd, String type)


    /**
     * tous les mois par année
     * @return
     */
    List<Map<String, Integer>> findAllMoisFormatFrParAnnee()

    List<Map<String, Integer>> findAllMoisParAnnee()


    /**
     * chercher les inters par mois d'une année
     * @param mois
     * @param annee
     * @return
     */
    List<Inter> findAllInterDeMoisDansAnnee(Integer mois, Integer annee)


    /**
     * recuperer le nombre de mois nbMois donne nombre de feuille
     * @return
     */
    Integer countMois()

    /**
     * recuperer le nombre dinter par mois
     * @param mois
     * @param annee
     * @return
     */
    Integer countInterParMoisDansAnnee(Integer mois, Integer annee)

    Integer countPlpParMoisDansAnnee(Integer mois, Integer annee)

    /**
     * BAAP BAOC BAFA BAST
     * @param mois
     * @param annee
     * @return
     */
    Integer countRacParMoisDansAnnee(Integer mois, Integer annee)

    Integer countBaapParMoisDansAnnee(Integer mois, Integer annee)

    Integer countBaocParMoisDansAnnee(Integer mois, Integer annee)

    Integer countBafaParMoisDansAnnee(Integer mois, Integer annee)

    Integer countBastParMoisDansAnnee(Integer mois, Integer annee)

    Integer countSavParMoisDansAnnee(Integer mois, Integer annee)

    Integer countPdcParMoisDansAnnee(Integer mois, Integer annee)

    /**
     * rac simple BAAP BAOC
     * @param mois
     * @param annee
     * @return
     */
    Integer countRacSimpleParMoisDansAnnee(Integer mois, Integer annee)

    /**
     * compter passage de cable en BAFA
     * @param mois
     * @param annee
     * @return
     */
    Integer countPdcBafaParMoisDansAnnee(Integer mois, Integer annee)

    /**
     * compter passage de cable BAAP et BAOC
     * @param mois
     * @param annee
     * @return
     */
    Integer countPdcBaocBaapParMoisDansAnnee(Integer mois, Integer annee)

    /**
     * compter passage de cable en BAST
     * @param mois
     * @param annee
     * @return
     */
    Integer countPdcBastParMoisDansAnnee(Integer mois, Integer annee)


}