--CREATE SCHEMA `fiber` DEFAULT CHARACTER SET utf8 ;
drop table if exists `Inter`;
CREATE TABLE `fiber`.`Inter` (
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