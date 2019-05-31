package com.cheroliv.fiber.domain

import groovy.transform.ToString

import javax.validation.constraints.*
import java.time.LocalDate

import static com.cheroliv.fiber.domain.Mois.*
import static java.lang.Integer.parseInt
import static java.time.format.DateTimeFormatter.ofPattern

@ToString
class Inter {
    static String NOT_NULL_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.NotNull.message}"
    static String SIZE_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Size.message}"
    static String MIN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Min.message}"
    static String PATTERN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Pattern.message}"
    static String MAX_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Max.message}"

    static Integer INTER_ID_MIN_VALUE = 1
    static Long HEURE_MIN_VALUE = 8
    static Long HEURE_MAX_VALUE = 19
    static Integer PRENOM_SIZE_VALUE = 100
    static Integer NOM_SIZE_VALUE = 100

    @Min(value = 1L)
    Integer id
    @NotNull
    @Size(min = 10, max = 10)
    String nd
    @NotNull
    @Pattern(regexp = "BAAP|BAOC|BAFA|BAST|PLP")
    String type
    @NotNull
    @Pattern(regexp = "LM|IQ|Passage de cable")
    String contrat
    @NotNull
    @Min(value = 8l)
    @Max(value = 19l)
    Integer heure
    @NotNull
    LocalDate date
    @Size(max = 100)
    String nom
    @Size(max = 100)
    String prenom


    static Integer parseStringHeureToInteger(String strHeure) {
        parseInt "${strHeure.charAt(0)}${strHeure.charAt(1)}"
    }

    String[] toArrayString() {
        [nd, type, contrat,
         heure < 10 ? "0${heure.toString()}" : heure.toString(),
         date.format(ofPattern("dd/MM/yyyy")),
         nom.toLowerCase() == "null" || nom == null ? "" : nom,
         prenom.toLowerCase() == "null" || prenom == null ? "" : prenom]
    }

    static Integer timeStringToInteger(String strHeure) {
        parseInt "${strHeure.charAt(0)}${strHeure.charAt(1)}"
    }


    static String convertNombreEnMois(Integer mois) throws NumberFormatException {
//        assert mois > 0 && mois < 13: "mois doit etre entre 1 et 12"
        switch (mois) {
            case 1: return Janvier.toString()
            case 2: return Février.toString()
            case 3: return Mars.toString()
            case 4: return Avril.toString()
            case 5: return Mai.toString()
            case 6: return Juin.toString()
            case 7: return Juillet.toString()
            case 8: return Aout.toString()
            case 9: return Septembre.toString()
            case 10: return Octobre.toString()
            case 11: return Novembre.toString()
            case 12: return Décembre.toString()
            default: throw new IllegalArgumentException("mauvais mois dans la base")
        }
    }
}