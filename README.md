# fiber-jdbc

## but de l'application

Le but de l'application est créer un fichier excel  
avec le récapitulatif des interventions d'un technicien fibre optique  
Le fichier excel se compose avec autant de feuille qu'il y a de mois  
chaque feuille doit contenir les enregistrements en base d'une intervention,  
le nombre d'intervention, le nombre de chaque type d'intervention  
et cela avec une mise en page correct.

## les fichier de l'application  

le formulaire LibreOffice base pour la saisie en base  
le fichier sql de création de la base sur mysql  
les fichiers de données des interventions en json et csv pour peupler la base 

## table description : Inter

| Field  | Type  | Null  | Key  | Default  | Extra  |
|---|---|:-:|---|---|---|
| id_inter  | bigint(19)  | NO  | PRI  | NULL  | auto_increment  |
| ND  | varchar(10)  | NO  | MUL  | NULL  |   |
| nom  | varchar(100)  | YES  |   | NULL  |   |
| prenom  | varchar(100)  | YES  |   | NULL  |   |
| heure  | time  | YES  |   | NULL  |   |
| date  | date  | YES  |   | NULL  |   |
| contrat  | varchar(16)  | NO  |   | NULL  |   |
| type  | varchar(16)  | NO  |   | NULL  |   |  


index unique(ND,type)  

>mysql > show create table FO.Inter;  
CREATE TABLE `Inter` (  
  `id_inter` bigint(19) NOT NULL AUTO_INCREMENT,  
  `ND` varchar(10)  NOT NULL,  
  `nom` varchar(100) DEFAULT NULL,  
  `prenom` varchar(100) DEFAULT NULL,  
  `heure` time DEFAULT NULL,  
  `date` date DEFAULT NULL,  
  `contrat` varchar(16) NOT NULL,  
  `type` varchar(16) NOT NULL,  
  PRIMARY KEY (`id_inter`),  
  UNIQUE KEY `uniq_idx_nd_type` (`ND`,`type`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;  

## lancer l'application
telecharger le zip ou cloner
se rendre dans le dossier, ouvrir un terminal en se rendant a la racine du projet.
>./gradlew bootRun




