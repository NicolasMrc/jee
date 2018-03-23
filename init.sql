-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:8889
-- Généré le :  Ven 23 Mars 2018 à 09:32
-- Version du serveur :  5.6.35
-- Version de PHP :  7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `jee`
--

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

CREATE TABLE `enseignant` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `signature` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `enseignant`
--

INSERT INTO `enseignant` (`id`, `nom`, `prenom`, `signature`) VALUES
(1, 'Augustin', 'Jacques', NULL),
(2, 'Karim', 'Lahlou', NULL),
(3, 'Christian', 'Khoury', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `entreprise`
--

CREATE TABLE `entreprise` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `entreprise`
--

INSERT INTO `entreprise` (`id`, `nom`, `adresse`) VALUES
(1, 'CGI', '12 rue secret défense'),
(2, 'Sopra Steria', '5 place de l\'Iris'),
(9, 'Société Générale', 'Wall Street'),
(10, 'Comiteo', 'Srartup street'),
(11, 'L\'amour est dans le pré', '3 ruelle des champs'),
(12, 'PMU', '3 rue du bar');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `id` int(11) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `is_alternant` tinyint(1) NOT NULL,
  `id_enseignant` int(11) DEFAULT NULL,
  `id_stage` int(11) DEFAULT NULL,
  `id_promo` int(11) NOT NULL,
  `photo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `etudiant`
--

INSERT INTO `etudiant` (`id`, `prenom`, `nom`, `is_alternant`, `id_enseignant`, `id_stage`, `id_promo`, `photo`) VALUES
(1, 'Damien', 'Lachambre', 1, NULL, NULL, 1, 'user-picture1.jpg'),
(3, 'Nicolas', 'Mercier', 0, 1, 4, 5, 'user-picture2.jpg'),
(4, 'Brian', 'Boukary', 0, 1, 5, 4, 'user-picture12.jpg'),
(5, 'Victor', 'Michaux', 0, 1, 6, 4, 'user-picture4.jpg'),
(6, 'Benjamin', 'Sanvoisin', 0, 2, 9, 3, 'user-picture5.jpg'),
(7, 'Cyril', 'Marquet', 0, 2, 10, 4, 'user-picture6.jpg'),
(8, 'Laura', 'Bouchara', 0, 3, 14, 5, 'user-picture8.jpg'),
(9, 'Adrien', 'Le Roy', 0, 1, 7, 5, 'user-picture13.jpg'),
(10, 'Florian', 'Allermoz', 0, 2, 13, 4, 'user-picture9.jpg'),
(11, 'Clément', 'Simon', 0, 3, 15, 4, 'user-picture10.jpg'),
(12, 'Anais', 'Ha', 0, 1, 8, 5, 'user-picture3.jpg'),
(13, 'Vincent', 'Le', 0, 3, 16, 4, 'user-picture7.jpg'),
(14, 'Timothé', 'Pearce', 0, 2, 11, 4, 'user-picture14.jpg'),
(15, 'Hudo', 'Jourdain', 0, 3, 17, 4, 'user-picture15.jpg'),
(16, 'Renan', 'Gehan', 0, 2, 12, 4, 'user-picture16.jpg'),
(17, 'Kevin', 'Nunes', 0, 3, 18, 4, 'user-picture17.jpg'),
(18, 'Napoléon', 'Bonaparte', 0, 1, 20, 3, 'user-picture17.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Structure de la table `promo`
--

CREATE TABLE `promo` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `promo`
--

INSERT INTO `promo` (`id`, `nom`) VALUES
(1, 'L1'),
(2, 'L2'),
(3, 'L3'),
(4, 'M1'),
(5, 'M2');

-- --------------------------------------------------------

--
-- Structure de la table `stage`
--

CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
  `maitre_de_stage` varchar(255) DEFAULT NULL,
  `cahier_des_charges` tinyint(1) DEFAULT NULL,
  `fiche_visite` tinyint(1) DEFAULT NULL,
  `fiche_evaluation_entreprise` tinyint(1) DEFAULT NULL,
  `sondage_web` tinyint(1) DEFAULT NULL,
  `rendu_rapport` tinyint(1) DEFAULT NULL,
  `soutenance` tinyint(1) DEFAULT NULL,
  `visite_planifiee` tinyint(1) DEFAULT NULL,
  `visite_faite` tinyint(1) DEFAULT NULL,
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `note_com` int(11) DEFAULT NULL,
  `note_tech` int(11) DEFAULT NULL,
  `id_entreprise` int(11) DEFAULT NULL,
  `description_mission` varchar(255) DEFAULT NULL,
  `commentaire` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `stage`
--

INSERT INTO `stage` (`id`, `maitre_de_stage`, `cahier_des_charges`, `fiche_visite`, `fiche_evaluation_entreprise`, `sondage_web`, `rendu_rapport`, `soutenance`, `visite_planifiee`, `visite_faite`, `debut`, `fin`, `note_com`, `note_tech`, `id_entreprise`, `description_mission`, `commentaire`) VALUES
(1, 'Jean Bon', 1, 0, 1, 1, 0, 1, 1, 0, '2017-12-01', '2017-12-31', 12, 11, 1, NULL, NULL),
(2, 'Sophie', 1, 0, 0, 1, 0, 0, 1, 1, '2017-12-01', '2017-12-31', 0, 0, 2, 'Description de la mission de Nico', ''),
(3, 'Obiwan', 0, 1, 0, 0, 1, 0, 0, 1, '2017-12-06', '2017-12-23', 0, 0, 1, NULL, NULL),
(4, 'Sophie Michel', 0, 1, 1, 1, 0, 1, 0, 0, '2017-12-01', '2017-12-24', 10, 20, 12, NULL, ''),
(5, 'John Rachid', 0, 1, 0, 1, 0, 1, 1, 0, '2017-12-01', '2017-12-15', 12, 15, 9, NULL, NULL),
(6, 'Guillaume Martin', 1, 1, 0, 0, 1, 0, 1, 0, '2017-12-01', '2017-12-30', 12, 11, 10, NULL, NULL),
(7, 'Louis Dupond', 1, 1, 0, 1, 1, 1, 1, 1, '2017-12-07', '2017-12-29', 16, 14, 2, NULL, NULL),
(8, 'Robin Tcherbazky', 1, 0, 0, 0, 1, 0, 1, 0, '2017-12-14', '2017-12-29', 18, 12, 9, NULL, NULL),
(9, '', 1, 0, 0, 0, 1, 0, 1, 0, '2017-12-01', '2017-12-23', 0, 0, 11, NULL, NULL),
(10, '', 1, 0, 0, 0, 1, 1, 1, 1, '2017-12-14', '2017-12-31', 0, 0, 1, NULL, NULL),
(11, '', 1, 1, 0, 0, 1, 0, 1, 0, '2017-12-01', '2017-12-30', 0, 0, 2, NULL, NULL),
(12, '', 1, 0, 0, 1, 0, 0, 1, 0, '2017-12-22', '2017-12-31', 0, 0, 2, NULL, NULL),
(13, '', 0, 1, 0, 1, 0, 1, 0, 0, '2017-12-15', '2017-12-31', 0, 0, 9, NULL, NULL),
(14, '', 1, 0, 0, 0, 0, 0, 1, 0, '2018-01-11', '2018-01-31', 0, 0, 2, NULL, NULL),
(15, '', 0, 1, 0, 1, 1, 0, 1, 1, '2017-12-01', '2017-12-29', 0, 0, 2, NULL, NULL),
(16, '', 1, 0, 0, 1, 0, 1, 1, 1, '2017-12-01', '2017-12-30', 0, 0, 1, NULL, NULL),
(17, '', 1, 0, 0, 0, 1, 0, 0, 0, '2017-12-12', '2017-12-30', 0, 0, 2, NULL, NULL),
(18, '', 0, 1, 0, 1, 1, 0, 1, 0, '2017-12-01', '2017-12-31', 0, 0, 2, NULL, NULL),
(19, NULL, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, NULL, NULL, NULL),
(20, '', 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 10, NULL, '');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role` varchar(25) NOT NULL,
  `id_enseignant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `password`, `username`, `role`, `id_enseignant`) VALUES
(8, '$2a$10$J5hz4pnkjQt4PHk4GCRgUuEckkuu8vfT3m41fA9v1zhadUumOtIoe', 'jaugustin', 'enseignant', 1),
(9, '$2a$10$J5hz4pnkjQt4PHk4GCRgUuEckkuu8vfT3m41fA9v1zhadUumOtIoe', 'klahlou', 'enseignant', 2),
(10, '$2a$10$J5hz4pnkjQt4PHk4GCRgUuEckkuu8vfT3m41fA9v1zhadUumOtIoe', 'ckhoury', 'enseignant', 3);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `entreprise`
--
ALTER TABLE `entreprise`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `stage`
--
ALTER TABLE `stage`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `enseignant`
--
ALTER TABLE `enseignant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `entreprise`
--
ALTER TABLE `entreprise`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT pour la table `promo`
--
ALTER TABLE `promo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `stage`
--
ALTER TABLE `stage`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;