package com.cheroliv.fiber.data

import com.jameskleeh.excel.ExcelBuilder
import groovy.util.logging.Slf4j
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Slf4j
class ClasseurRecap {
    XSSFWorkbook classeur
    String classeurPathName
    Integer nbFeuille
    List<String> nomFeuilles
    List<Recap> recaps
    List<Map<String, Integer>> moisParAnnee

    void createExcelWorkBook() {
        classeurPathName
        classeur
        try {
            File classeurFile
            classeurFile = new File(classeurPathName)
            try {
                FileOutputStream fos
                fos = new FileOutputStream(classeurFile)
                classeur = ExcelBuilder.build {
                    recaps.eachWithIndex { recap, idx ->
                        sheet(nomFeuilles.get(idx)) {
                            row {
                                merge {
                                    String titre = recap.labelTitreRecap
                                    cell(titre)
                                    skipCells(4)
                                }
                            }
                            row()
                            row {
                                cell(recap.labelNbInterTotal)
                                cell(recap.nbInterTotal)
                                cell()
                                merge {
                                    cell(recap.labelDefPdc)
                                    skipCells(2)
                                }
                            }
                            row {
                                cell(recap.labelNbBaocBaap)
                                cell(recap.nbBaocBaap)
                            }
                            row {
                                cell(recap.labelNbBafa)
                                cell(recap.nbBafa)
                            }
                            row {
                                cell(recap.labelNbPdcTotal)
                                cell(recap.nbPdcTotal)
                            }
                            row {
                                cell(recap.labelNbBast)
                                cell(recap.nbBast)
                            }
                            row {
                                cell(recap.labelNbPdcBafa)
                                cell(recap.nbPdcBafa)
                            }
                            row {
                                cell(recap.labelNbPlp)
                                cell(recap.nbPlp)
                            }
                            row {
                                cell(recap.labelNbPdcBast)
                                cell(recap.nbPdcBast)
                            }
                            row {
                                cell(recap.labelNbSav)
                                cell(recap.nbSav)
                            }
                            row {
                                cell(recap.labelNbPdcBaocBaap)
                                cell(recap.nbPdcBaocBaap)
                            }
                            row()
                            row(recap.labelListInter)
                            row {
                                cell(recap.labelNd)
                                cell(recap.labelType)
                                cell(recap.labelContrat)
                                cell(recap.labelHeure)
                                cell(recap.labelDate)
                                cell recap.labelNom
                                cell recap.labelPrenom
                            }
                            recap.inters.each { i ->
                                row {
                                    cell i.nd
                                    cell i.type
                                    cell i.contrat
                                    cell i.heure
                                    cell i.date
                                    cell i.nom
                                    cell i.prenom
                                }
                            }
                        }
                        it.getSheetAt(idx).autoSizeColumn 0
                        it.getSheetAt(idx).autoSizeColumn 3
                        it.getSheetAt(idx).autoSizeColumn 5
                        it.getSheetAt(idx).autoSizeColumn 6
                    }

                }
                classeur.write fos
                fos.close()
            } catch (Exception e) {
                e.printStackTrace()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
