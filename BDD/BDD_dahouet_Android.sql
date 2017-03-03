-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 03 Mars 2017 à 08:39
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `dahouet`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `DistanceMoyenneSaison` (IN `p_saison` INT)  BEGIN
SELECT AVG(distance_regate) AS DistanceMoyenne, c.saison AS Saison FROM Regate r
INNER JOIN Challenge c ON r.id_challenge=c.id_challenge
WHERE c.id_challenge = p_saison;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InterventionCommissaire` (IN `p_challenge` INT, IN `p_date_debut` DATE, IN `p_date_fin` DATE)  BEGIN
SELECT nom_personne AS NomCommissaire, prenom_personne AS PrenomCommissaire, comite AS Comité, date_regate AS DateRégate FROM Regate r
INNER JOIN Challenge cha ON r.id_challenge=cha.id_challenge
INNER JOIN Commissaire com ON r.id_commissaire=com.id_commissaire
INNER JOIN Personne per ON com.id_personne=per.id_personne
WHERE p_challenge = cha.id_challenge AND p_date_debut < date_regate AND p_date_fin > date_regate;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListeEquipageVoilierParRegate` (IN `p_voilier` INT, IN `p_regate` INT)  BEGIN
SELECT nom_personne AS Nom, prenom_personne AS Prénom, v.nom_voilier AS Voilier, r.nom_regate AS Regate FROM Personne per
INNER JOIN Proprietaire pro ON per.id_personne=pro.id_personne
INNER JOIN Voilier v ON pro.id_proprietaire=v.id_proprietaire
INNER JOIN PARTICIPER par ON v.id_voilier=par.id_voilier
INNER JOIN Regate r ON par.id_regate=r.id_regate
WHERE r.num_regate = p_regate AND v.num_voile = p_voilier;
END$$

--
-- Fonctions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `CodeGen` (`v_id_regate` INT) RETURNS VARCHAR(11) CHARSET latin1 BEGIN

DECLARE v_code_challenge VARCHAR(20);
DECLARE v_mois_date INT;
DECLARE v_ajout INT;
DECLARE CodeGen VARCHAR(20);

SELECT code_challenge INTO v_code_challenge FROM Challenge cha INNER JOIN Regate r ON cha.id_challenge=r.id_challenge WHERE r.id_regate=v_id_regate;
SELECT MONTH(date_regate) INTO v_mois_date FROM Regate r INNER JOIN Challenge cha ON r.id_challenge=cha.id_challenge WHERE r.id_regate=v_id_regate;
SELECT COUNT(id_regate) INTO v_ajout FROM Regate;

SET CodeGen = CONCAT(v_code_challenge,"-",v_mois_date,"-", v_ajout+1);

RETURN CodeGen;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `challenge`
--

CREATE TABLE `challenge` (
  `id_challenge` int(11) NOT NULL,
  `code_challenge` varchar(10) NOT NULL,
  `saison` varchar(25) DEFAULT NULL,
  `debut_challenge` date DEFAULT NULL,
  `fin_challenge` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `challenge`
--

INSERT INTO `challenge` (`id_challenge`, `code_challenge`, `saison`, `debut_challenge`, `fin_challenge`) VALUES
(1, 'CHAHIV', 'Hiver', '2016-11-01', '2017-03-31'),
(2, 'CHAETE', 'Ete', '2017-05-01', '2017-09-30');

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

CREATE TABLE `classe` (
  `id_classe` int(11) NOT NULL,
  `nom_classe` varchar(25) NOT NULL,
  `coef` int(11) NOT NULL,
  `id_serie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `classe`
--

INSERT INTO `classe` (`id_classe`, `nom_classe`, `coef`, `id_serie`) VALUES
(1, 'Corsaire', 5, 1),
(2, 'Surprise', 6, 1),
(3, '8 metres', 8, 1),
(4, 'Maraudeur', 3, 1),
(5, 'Figaro', 9, 1),
(6, 'Flying Fifteen', 7, 2),
(7, 'Soling', 4, 2),
(8, 'Star', 3, 2),
(9, 'Tempest', 2, 2),
(10, 'Yngling', 6, 2),
(11, '5.5', 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `commissaire`
--

CREATE TABLE `commissaire` (
  `id_commissaire` int(11) NOT NULL,
  `comite` varchar(25) DEFAULT NULL,
  `id_personne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commissaire`
--

INSERT INTO `commissaire` (`id_commissaire`, `comite`, `id_personne`) VALUES
(1, 'Idf', 6),
(2, 'Est', 9),
(3, 'Ouest', 11),
(4, 'Sud', 19);

-- --------------------------------------------------------

--
-- Structure de la table `concurrent`
--

CREATE TABLE `concurrent` (
  `id_concurrent` int(11) NOT NULL,
  `id_personne` int(11) DEFAULT NULL,
  `id_voilier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `concurrent`
--

INSERT INTO `concurrent` (`id_concurrent`, `id_personne`, `id_voilier`) VALUES
(1, 7, 1),
(2, 8, 1),
(3, 5, 1),
(4, 1, 2),
(5, 3, 2),
(6, 4, 2),
(7, 10, 3),
(8, 11, 3),
(9, 12, 4),
(10, 13, 5),
(11, 16, 6),
(12, 17, 7),
(13, 17, 8);

-- --------------------------------------------------------

--
-- Structure de la table `participer`
--

CREATE TABLE `participer` (
  `tps_reel` int(11) DEFAULT NULL,
  `tps_compense` int(11) DEFAULT NULL,
  `place` int(10) DEFAULT NULL,
  `id_voilier` int(11) NOT NULL,
  `id_regate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `participer`
--

INSERT INTO `participer` (`tps_reel`, `tps_compense`, `place`, `id_voilier`, `id_regate`) VALUES
(5236, 5236, 5, 1, 49),
(5648, 1322, 1, 2, 55),
(4878, 4878, 4, 3, 49),
(3587, 3587, 3, 5, 49),
(2992, 2992, 2, 6, 49),
(2547, 2547, 1, 7, 49),
(7254, 686, 2, 8, 55);

--
-- Déclencheurs `participer`
--
DELIMITER $$
CREATE TRIGGER `verif_place` BEFORE INSERT ON `participer` FOR EACH ROW BEGIN

DECLARE v_place INT;
DECLARE v_nb_participant INT;

SELECT COUNT(place) INTO v_place FROM PARTICIPER PAR INNER JOIN Regate r ON PAR.id_regate=r.id_regate;
SELECT COUNT(PAR.id_regate) INTO v_nb_participant FROM PARTICIPER PAR INNER JOIN Regate r ON PAR.id_regate=r.id_regate;

IF v_place < v_nb_participant THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erreur !';
END IF;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id_personne` int(11) NOT NULL,
  `nom_personne` varchar(25) DEFAULT NULL,
  `prenom_personne` varchar(25) DEFAULT NULL,
  `email_personne` varchar(25) DEFAULT NULL,
  `num_licence` int(11) DEFAULT NULL,
  `annee_licence` int(11) DEFAULT NULL,
  `nom_club` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`id_personne`, `nom_personne`, `prenom_personne`, `email_personne`, `num_licence`, `annee_licence`, `nom_club`) VALUES
(1, 'Callec', 'Yoann', 'pouet@pouet.fr', 34856, 2017, 'Club Mx'),
(2, 'Balcon', 'Yoann', 'prout@prout.prout', 45687, 2014, 'Club Brest'),
(3, 'LeGrall', 'Jérome', 'jeje@jeje.je', 84314, 1998, 'Club Santec'),
(4, 'Fournier', 'Lucas', 'lulu@lulu.lu', 98459, 2011, 'Club Lorient'),
(5, 'Lopez', 'Richard', 'zer@ezr.ezr', 84645, 2015, 'Club St Pol'),
(6, 'Duval', 'Jean-Luc', 'uuu@uuu.uu', 98754, 2016, 'Club Santec'),
(7, 'Kertruc', 'Martine', 'dd@ddd.dd', 36985, 2010, 'Club Plouenan'),
(8, 'Lotus', 'Louis', 'eee@ee.ee', 95874, 2007, 'Club Mespaul'),
(9, 'Kiev', 'Daniel', 'df@df.df', 98547, 2015, 'Club Moscou'),
(10, 'Le Henaff', 'Gwenolé', 'sdf@sdf.sdf', 65487, 2017, 'Club Mx'),
(11, 'Langaine', 'Nicolas', 'dd@dd.dd', 98754, 2016, 'Club Landivisiau'),
(12, 'Simon', 'Christophe', 'zz@zz.zz', 50654, 1991, 'Club Landivisiau'),
(13, 'Chenier', 'Tanguy', 'dfg@sdf.er', 10984, 2010, 'Club AFPA'),
(14, 'Vanel', 'Rémi', 'hh@hh.hh', 25654, 2012, 'Club Lesneven'),
(15, 'Lefèbvre', 'Christiane', 'cleb@sdf.sdf', 68546, 2015, 'Club Paris'),
(16, 'Laframboise', 'Amedee', 'sdf@dfg.ee', 12654, 2000, 'Club Rennes'),
(17, 'Faubert', 'Lea', 'lf@lf.lf', 32165, 2001, 'Club Marseille'),
(18, 'Bisson', 'Thierry', 'bt@bt.bt', 65487, 2016, 'Club Lille'),
(19, 'Bisaillon', 'Noël', 'rgdfg@dfgdfg.df', 54562, 2017, 'Club Rennes'),
(20, 'Dupuis', 'Marcel', 'sdfsdf@ggg.gg', 98451, 2014, 'Club Lille');

-- --------------------------------------------------------

--
-- Structure de la table `proprietaire`
--

CREATE TABLE `proprietaire` (
  `id_proprietaire` int(11) NOT NULL,
  `id_personne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `proprietaire`
--

INSERT INTO `proprietaire` (`id_proprietaire`, `id_personne`) VALUES
(1, 2),
(2, 3),
(3, 10),
(4, 11),
(5, 14),
(6, 15),
(7, 18),
(8, 20);

-- --------------------------------------------------------

--
-- Structure de la table `regate`
--

CREATE TABLE `regate` (
  `id_regate` int(11) NOT NULL,
  `nom_regate` varchar(25) DEFAULT NULL,
  `num_regate` int(11) DEFAULT NULL,
  `date_regate` date DEFAULT NULL,
  `distance_regate` int(11) DEFAULT NULL,
  `id_challenge` int(11) DEFAULT NULL,
  `id_commissaire` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `regate`
--

INSERT INTO `regate` (`id_regate`, `nom_regate`, `num_regate`, `date_regate`, `distance_regate`, `id_challenge`, `id_commissaire`) VALUES
(36, 'Coupe de l\'America', 8754, '2017-01-16', 3256, 1, 3),
(48, 'Course Croisière EDHEC', 3215, '2017-03-12', 2847, 1, 1),
(49, 'Fastnet Race', 1254, '2016-12-13', 3057, 1, 2),
(50, 'Route du Rhum', 6875, '2016-12-01', 2076, 1, 4),
(51, 'Tall Ships\' Races', 6874, '2017-02-22', 356, 1, 1),
(52, 'Transat 6.50\nMini Transat', 3332, '2017-01-13', 7741, 1, 2),
(53, 'Route du Café', 8845, '2017-03-08', 3211, 1, 3),
(54, 'Québec-Saint-Malo', 6754, '2017-02-06', 9475, 1, 2),
(55, 'Twostar', 8953, '2017-02-19', 3621, 1, 4),
(56, 'Lorient-St-Barth’', 1124, '2017-02-09', 2147, 1, 1),
(57, 'Route de la Perdition', 6685, '2016-12-07', 3331, 1, 1),
(58, 'Solidaire du Chocolat', 3547, '2016-11-18', 5847, 1, 2),
(59, 'Sydney-Hobart', 4441, '2017-05-18', 3220, 2, 4),
(60, 'Global Challenge', 9958, '2017-06-14', 4488, 2, 2),
(61, 'Around Alone', 9965, '2017-06-05', 5566, 2, 3),
(62, 'The Chase', 5546, '2017-05-24', 3321, 2, 1),
(63, 'Oryx Quest', 2222, '2017-09-13', 8521, 2, 1),
(64, 'Route de la découverte', 5487, '2017-08-31', 984, 2, 3),
(65, 'Apprentissage', 3579, '2017-06-03', 741, 2, 4),
(66, 'La Veine', 3541, '2017-08-01', 9514, 2, 2),
(67, 'Trophée Jules-Verne', 9996, '2017-09-13', 4242, 2, 1),
(68, 'La Satisfaction', 9993, '2017-07-19', 1478, 2, 4);

--
-- Déclencheurs `regate`
--
DELIMITER $$
CREATE TRIGGER `verif_date_challenge` BEFORE INSERT ON `regate` FOR EACH ROW BEGIN
DECLARE date_debut_challenge DATE;
DECLARE date_fin_challenge DATE;


SELECT debut_challenge INTO date_debut_challenge FROM Challenge WHERE id_challenge=NEW.id_challenge;
SELECT fin_challenge INTO date_fin_challenge FROM Challenge WHERE id_challenge=NEW.id_challenge;

IF (NEW.date_regate < date_debut_challenge OR NEW.date_regate > date_fin_challenge)
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erreur ! La date est incorrecte !';
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `verif_regate_challenge` BEFORE DELETE ON `regate` FOR EACH ROW BEGIN

DECLARE v_datefin_challenge DATE;
SELECT fin_challenge INTO v_datefin_challenge FROM Challenge WHERE id_challenge=OLD.id_challenge;

IF CURDATE() < v_datefin_challenge
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Impossible de supprimer la régate ! La saison nest pas terminé !';
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `serie`
--

CREATE TABLE `serie` (
  `id_serie` int(11) NOT NULL,
  `nom_serie` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `serie`
--

INSERT INTO `serie` (`id_serie`, `nom_serie`) VALUES
(1, 'Habitables'),
(2, 'Quillards de sport');

-- --------------------------------------------------------

--
-- Structure de la table `skipper`
--

CREATE TABLE `skipper` (
  `id_skipper` int(11) NOT NULL,
  `num_skipper` int(11) DEFAULT NULL,
  `id_voilier` int(11) DEFAULT NULL,
  `id_personne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `skipper`
--

INSERT INTO `skipper` (`id_skipper`, `num_skipper`, `id_voilier`, `id_personne`) VALUES
(1, 1836, 1, 1),
(2, 666, 2, 4),
(3, 547, 3, 10);

-- --------------------------------------------------------

--
-- Structure de la table `voilier`
--

CREATE TABLE `voilier` (
  `id_voilier` int(11) NOT NULL,
  `nom_voilier` varchar(25) DEFAULT NULL,
  `num_voile` int(11) DEFAULT NULL,
  `id_proprietaire` int(11) DEFAULT NULL,
  `id_classe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `voilier`
--

INSERT INTO `voilier` (`id_voilier`, `nom_voilier`, `num_voile`, `id_proprietaire`, `id_classe`) VALUES
(1, 'Python', 159, 1, 5),
(2, 'LeCPlusPlus', 201, 2, 2),
(3, 'LeSharp', 654, 3, 9),
(4, 'Perl', 148, 4, 11),
(5, 'Bootstrap', 808, 5, 7),
(6, 'Symfony', 987, 6, 6),
(7, 'Github', 211, 7, 10),
(8, 'Ruby', 778, 8, 3),
(9, 'PHP', 207, 1, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `challenge`
--
ALTER TABLE `challenge`
  ADD PRIMARY KEY (`id_challenge`);

--
-- Index pour la table `classe`
--
ALTER TABLE `classe`
  ADD PRIMARY KEY (`id_classe`),
  ADD KEY `id_serie` (`id_serie`);

--
-- Index pour la table `commissaire`
--
ALTER TABLE `commissaire`
  ADD PRIMARY KEY (`id_commissaire`),
  ADD KEY `FK_Commissaire_id_personne` (`id_personne`);

--
-- Index pour la table `concurrent`
--
ALTER TABLE `concurrent`
  ADD PRIMARY KEY (`id_concurrent`),
  ADD KEY `FK_Concurrent_id_personne` (`id_personne`),
  ADD KEY `FK_Concurrent_id_voilier` (`id_voilier`);

--
-- Index pour la table `participer`
--
ALTER TABLE `participer`
  ADD PRIMARY KEY (`id_voilier`,`id_regate`),
  ADD KEY `FK_PARTICIPER_id_regate` (`id_regate`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id_personne`);

--
-- Index pour la table `proprietaire`
--
ALTER TABLE `proprietaire`
  ADD PRIMARY KEY (`id_proprietaire`),
  ADD KEY `FK_Proprietaire_id_personne` (`id_personne`);

--
-- Index pour la table `regate`
--
ALTER TABLE `regate`
  ADD PRIMARY KEY (`id_regate`),
  ADD KEY `FK_Regate_id_challenge` (`id_challenge`),
  ADD KEY `FK_Regate_id_commissaire` (`id_commissaire`);

--
-- Index pour la table `serie`
--
ALTER TABLE `serie`
  ADD PRIMARY KEY (`id_serie`);

--
-- Index pour la table `skipper`
--
ALTER TABLE `skipper`
  ADD PRIMARY KEY (`id_skipper`),
  ADD KEY `FK_Skipper_id_voilier` (`id_voilier`),
  ADD KEY `FK_Skipper_id_personne` (`id_personne`);

--
-- Index pour la table `voilier`
--
ALTER TABLE `voilier`
  ADD PRIMARY KEY (`id_voilier`),
  ADD KEY `FK_Voilier_id_proprietaire` (`id_proprietaire`),
  ADD KEY `FK_Voilier_id_serie` (`id_classe`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `challenge`
--
ALTER TABLE `challenge`
  MODIFY `id_challenge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `classe`
--
ALTER TABLE `classe`
  MODIFY `id_classe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `commissaire`
--
ALTER TABLE `commissaire`
  MODIFY `id_commissaire` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `concurrent`
--
ALTER TABLE `concurrent`
  MODIFY `id_concurrent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id_personne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT pour la table `proprietaire`
--
ALTER TABLE `proprietaire`
  MODIFY `id_proprietaire` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `regate`
--
ALTER TABLE `regate`
  MODIFY `id_regate` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;
--
-- AUTO_INCREMENT pour la table `serie`
--
ALTER TABLE `serie`
  MODIFY `id_serie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `skipper`
--
ALTER TABLE `skipper`
  MODIFY `id_skipper` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `voilier`
--
ALTER TABLE `voilier`
  MODIFY `id_voilier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `classe`
--
ALTER TABLE `classe`
  ADD CONSTRAINT `Classe_ibfk_1` FOREIGN KEY (`id_serie`) REFERENCES `serie` (`id_serie`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commissaire`
--
ALTER TABLE `commissaire`
  ADD CONSTRAINT `FK_Commissaire_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `concurrent`
--
ALTER TABLE `concurrent`
  ADD CONSTRAINT `FK_Concurrent_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `personne` (`id_personne`),
  ADD CONSTRAINT `FK_Concurrent_id_voilier` FOREIGN KEY (`id_voilier`) REFERENCES `voilier` (`id_voilier`);

--
-- Contraintes pour la table `participer`
--
ALTER TABLE `participer`
  ADD CONSTRAINT `FK_PARTICIPER_id_regate` FOREIGN KEY (`id_regate`) REFERENCES `regate` (`id_regate`),
  ADD CONSTRAINT `FK_PARTICIPER_id_voilier` FOREIGN KEY (`id_voilier`) REFERENCES `voilier` (`id_voilier`);

--
-- Contraintes pour la table `proprietaire`
--
ALTER TABLE `proprietaire`
  ADD CONSTRAINT `FK_Proprietaire_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `regate`
--
ALTER TABLE `regate`
  ADD CONSTRAINT `FK_Regate_id_challenge` FOREIGN KEY (`id_challenge`) REFERENCES `challenge` (`id_challenge`),
  ADD CONSTRAINT `FK_Regate_id_commissaire` FOREIGN KEY (`id_commissaire`) REFERENCES `commissaire` (`id_commissaire`);

--
-- Contraintes pour la table `skipper`
--
ALTER TABLE `skipper`
  ADD CONSTRAINT `FK_Skipper_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `personne` (`id_personne`),
  ADD CONSTRAINT `FK_Skipper_id_voilier` FOREIGN KEY (`id_voilier`) REFERENCES `voilier` (`id_voilier`);

--
-- Contraintes pour la table `voilier`
--
ALTER TABLE `voilier`
  ADD CONSTRAINT `FK_Voilier_id_proprietaire` FOREIGN KEY (`id_proprietaire`) REFERENCES `proprietaire` (`id_proprietaire`),
  ADD CONSTRAINT `Voilier_ibfk_1` FOREIGN KEY (`id_classe`) REFERENCES `classe` (`id_classe`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
