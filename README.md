# Application Spring - Stage.io

Cette application permet au professeur de l'EFREI de suivre leurs stagiaires plus simplement et rapidement.

# Fonctionnalité implémentée :
###Page de login
- Authentification : Spring Security

###Page d'acceuil
- Accès : directement apères l'authentification
- Modification des éléments de suivi de Stage principaux directement via le tableaux de stagiaires (utilisation d'ajax) puis en cliquant sur le bouton "Valider" en bas de page
###Page d'ajout d'élève 
- Accès : en cliquant sur le bouton "Ajouter" sur la page d'acceuil
- Il suffit de séléctionner un élève et cliquer sur le bouton "Ajouter"
- Les étudiant qui sont déjà associé a un prof ou les alternant ne peuvent pas être associés
- Les message d'erreur ou de succès sont affiché sur la page dans un toast grâce a ajax (il n'y a pas de rechargement de page)
###Page de Détails d'un élève
- Accès : en selectionnant une ligne du tableau de la page d'acceuil grace aux radio boutons puis cliquant sur le bouton "Details" en bas de page
- Il est possible de modifier tout les élèments de suivi de stage de l'étudiant a partir de cette page en modifiant une valeur et cliquant sur le bouton "sauvegarder"
- Il est possible d'ajouter une nouvelle entreprise au référentiel d'entreprise
- En cliquant sur le bouton "renseigner la fiche de visite", il est possible de télécharger le pdf de la fiche de visite (brouillon ou signé) la signature sera dans ce cas celle importer depuis la page profil du professeur
###Page profil
- Accès : En cliquant sur le nom de l'utilisateur connecté dans la navbar
- Possibilité de changer la signature associé au professeur
#Architecture de l'application
- Models : représentent les classes sauvegardées en base de données
- Repositories : Repository associés au models et héritant des interface JPARepository, permet d'accèder aux différentes opérations de base sur les entités de base de donnée, comme findAll(), findById()...
- Services : Les services contiennent toutes la couche traitement de l'application
- Controllers : Les controllers mettents les url a disposition pour toute actions liée a l'application et contienent les controle sur les données.
- Templates : templates twig, la couche vue de l'application

Tout les services sont injecté par injection de dépendence dans l'application grace à l'annotation @Autowired, cela permet de mettre en pratique la fameuse IoC de Spring.
# Guide d'installation

- faire un maven install sur le projet
- créer une base de donnée
- modifier le fichier ``src/main/resources/application.properties`` afin de rensigner les informations de la base de donnée
- lancer le script ``init.sql`` qui créera et remplira votre base avec des données fake
- lancer le projet en tant qu'application Tomcat ou Glassfish
- vous pouvez vous connecté grace à l'identifiant suivant : ```{ username: 'jaugustin', password: 'password' }```