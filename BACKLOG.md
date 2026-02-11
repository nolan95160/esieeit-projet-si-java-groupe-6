# BACKLOG — To-Do List (Particuliers & Pros) avec Groupes

## 1) Pitch produit (30 secondes)
- Problème : On gère des tâches dans plusieurs contextes (foyer, travail, projets perso), on s’y perd et on oublie.
- Solution : Une application simple pour créer, organiser et suivre des tâches, rangées par groupes (Foyer, Travail, etc.).
- Pour qui : Particuliers (perso/foyer) et professionnels (équipe/projet).
- Valeur : Clarifier “quoi faire” et “où”, prioriser, suivre l’avancement.
- Contraintes / hypothèses :
  - MVP : gestion de groupes + tâches + affectation simple.
  - Collaboration simple (inviter des membres) sans temps réel avancé.
  - Données persistées (base de données ou fichier selon le cours).

## 2) Acteurs (personas)
| Acteur | Objectifs | Permissions / actions |
|---|---|---|
| Visiteur | Découvrir l’app | S’inscrire, se connecter |
| Particulier | Gérer tâches perso/foyer | Créer groupes (Foyer, Perso…), gérer tâches, inviter proches |
| Professionnel | Gérer tâches de travail | Créer groupes (Projet, Équipe…), assigner tâches, suivre statuts |
| Propriétaire de groupe | Administrer un groupe | Renommer/supprimer groupe, inviter/retirer membres, gérer rôles |
| Membre de groupe | Participer | Voir tâches du groupe, créer/éditer tâches (selon droits) |
| Admin (optionnel) | Supervision | Voir utilisateurs, désactiver compte |

## 3) Modules / Features
- M1 — Authentification & Compte
- M2 — Gestion des groupes (CRUD + membres)
- M3 — Gestion des tâches (CRUD)
- M4 — Statuts, priorité, échéance, assignation
- M5 — Recherche, filtres, tri
- M6 — Qualité (validations, messages d’erreur, UX)
- (Bonus) M7 — Administration

## 4) User Stories (15)

### US-01 — Inscription
**Story** : En tant que *Visiteur*, je veux créer un compte afin d’enregistrer mes groupes et mes tâches.  
**Priorité** : Must  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis sur la page d’inscription  
  When je saisis un email valide et un mot de passe conforme  
  Then mon compte est créé et je suis connecté
- Given un compte existe déjà avec cet email  
  When je tente de m’inscrire avec le même email  
  Then un message d’erreur s’affiche et aucun compte n’est créé

### US-02 — Connexion
**Story** : En tant que *Visiteur*, je veux me connecter afin d’accéder à mes groupes et tâches.  
**Priorité** : Must  
**Estimation** : S  
**Critères d’acceptation** :
- Given je suis sur la page de connexion  
  When je saisis des identifiants valides  
  Then j’accède à mon espace
- Given mes identifiants sont invalides  
  When je tente de me connecter  
  Then un message d’erreur s’affiche

### US-03 — Déconnexion
**Story** : En tant que *Utilisateur*, je veux me déconnecter afin de sécuriser mon compte.  
**Priorité** : Must  
**Estimation** : S  
**Critères d’acceptation** :
- Given je suis connecté  
  When je clique sur “Déconnexion”  
  Then ma session est terminée et je reviens à l’écran de connexion

---

## Groupes (Foyer/Travail/etc.)

### US-04 — Créer un groupe
**Story** : En tant que *Utilisateur*, je veux créer un groupe (ex : Foyer, Travail) afin d’organiser mes tâches par contexte.  
**Priorité** : Must  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis connecté  
  When je crée un groupe avec un nom valide  
  Then le groupe apparaît dans ma liste de groupes
- Given le nom du groupe est vide  
  When je valide  
  Then un message indique que le nom est obligatoire

### US-05 — Voir mes groupes
**Story** : En tant que *Utilisateur*, je veux voir la liste de mes groupes afin de naviguer facilement.  
**Priorité** : Must  
**Estimation** : S  
**Critères d’acceptation** :
- Given je suis connecté  
  When j’ouvre l’écran des groupes  
  Then je vois uniquement les groupes dont je suis membre
- Given je n’ai aucun groupe  
  When j’ouvre l’écran des groupes  
  Then je vois un message “Aucun groupe”

### US-06 — Renommer / supprimer un groupe
**Story** : En tant que *Propriétaire de groupe*, je veux renommer ou supprimer un groupe afin de garder un espace propre.  
**Priorité** : Should  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis propriétaire du groupe  
  When je renomme le groupe avec un nom valide  
  Then le nouveau nom est affiché partout
- Given je suis propriétaire du groupe  
  When je supprime le groupe et je confirme  
  Then le groupe et ses tâches ne sont plus accessibles
- Given je ne suis pas propriétaire  
  When je tente de renommer/supprimer  
  Then l’action est refusée

### US-07 — Inviter un membre dans un groupe
**Story** : En tant que *Propriétaire de groupe*, je veux inviter une personne afin de partager la gestion des tâches.  
**Priorité** : Should  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis propriétaire  
  When j’invite un utilisateur existant par email  
  Then il devient membre du groupe
- Given l’email n’existe pas  
  When je tente d’inviter  
  Then un message indique “Utilisateur introuvable”
- Given la personne est déjà membre  
  When je tente de l’inviter  
  Then un message indique qu’elle est déjà dans le groupe

### US-08 — Retirer un membre d’un groupe
**Story** : En tant que *Propriétaire de groupe*, je veux retirer un membre afin de gérer l’accès.  
**Priorité** : Nice  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis propriétaire  
  When je retire un membre  
  Then il n’a plus accès au groupe ni à ses tâches
- Given je tente de retirer le propriétaire  
  When je valide  
  Then l’action est refusée

---

## Tâches (dans un groupe)

### US-09 — Créer une tâche dans un groupe
**Story** : En tant que *Membre de groupe*, je veux créer une tâche dans un groupe afin de planifier ce qui doit être fait.  
**Priorité** : Must  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis membre d’un groupe  
  When je crée une tâche avec un titre  
  Then la tâche apparaît dans le groupe avec le statut “À faire”
- Given le titre est vide  
  When je valide  
  Then une erreur s’affiche

### US-10 — Voir les tâches d’un groupe
**Story** : En tant que *Membre de groupe*, je veux voir les tâches du groupe afin de savoir quoi faire.  
**Priorité** : Must  
**Estimation** : S  
**Critères d’acceptation** :
- Given je suis dans un groupe  
  When j’ouvre l’onglet tâches  
  Then je vois toutes les tâches de ce groupe
- Given je ne suis pas membre  
  When je tente d’accéder au groupe  
  Then l’accès est refusé

### US-11 — Modifier / supprimer une tâche
**Story** : En tant que *Membre de groupe*, je veux modifier ou supprimer une tâche afin de maintenir la liste à jour.  
**Priorité** : Must  
**Estimation** : M  
**Critères d’acceptation** :
- Given une tâche existe dans mon groupe  
  When je modifie titre/description et j’enregistre  
  Then les changements sont visibles
- Given je supprime une tâche et je confirme  
  When je valide  
  Then la tâche disparaît de la liste

### US-12 — Changer le statut d’une tâche
**Story** : En tant que *Membre de groupe*, je veux changer le statut (À faire/En cours/Terminée) afin de suivre l’avancement.  
**Priorité** : Must  
**Estimation** : S  
**Critères d’acceptation** :
- Given une tâche est “À faire”  
  When je la passe en “En cours”  
  Then le statut est mis à jour et persisté
- Given une tâche est “En cours”  
  When je la passe en “Terminée”  
  Then elle apparaît comme terminée

### US-13 — Assigner une tâche à un membre
**Story** : En tant que *Professionnel ou Particulier*, je veux assigner une tâche à un membre du groupe afin de clarifier qui fait quoi.  
**Priorité** : Should  
**Estimation** : M  
**Critères d’acceptation** :
- Given je suis membre du groupe  
  When j’assigne la tâche à un membre du groupe  
  Then le responsable s’affiche sur la tâche
- Given l’utilisateur n’est pas membre  
  When je tente de l’assigner  
  Then l’action est refusée

### US-14 — Ajouter une échéance et une priorité
**Story** : En tant que *Utilisateur*, je veux mettre une échéance et une priorité afin de mieux planifier.  
**Priorité** : Should  
**Estimation** : M  
**Critères d’acceptation** :
- Given je crée/modifie une tâche  
  When je choisis une date d’échéance valide et une priorité  
  Then ces informations s’affichent sur la tâche
- Given la date est invalide  
  When je valide  
  Then une erreur s’affiche

### US-15 — Filtrer / rechercher dans un groupe
**Story** : En tant que *Utilisateur*, je veux filtrer et rechercher des tâches dans un groupe afin de retrouver rapidement une info.  
**Priorité** : Should  
**Estimation** : M  
**Critères d’acceptation** :
- Given des tâches existent  
  When je filtre par statut ou par assigné  
  Then seules les tâches correspondantes sont affichées
- Given je tape un mot-clé  
  When je recherche  
  Then les tâches dont le titre/description contient le mot-clé sont affichées
- Given aucun résultat  
  When je recherche  
  Then un message “Aucun résultat” apparaît
