# Projet SI Java — Groupe 6

Projet réalisé dans le cadre du module **Systèmes d’Information** à **ESIEE-IT**.  
Application de gestion de tâches pour particuliers et professionnels, avec organisation par **groupes** (Foyer, Travail, Projet, etc.).

---

## Objectif du projet

L’application permet de :
- créer et gérer des groupes,
- créer, modifier et suivre des tâches,
- assigner des tâches à des membres,
- gérer statuts, priorité et échéance,
- préparer une base de collaboration simple (invitation/retrait de membres).

---

## P 2.1 — Modélisation & Architecture

Cette itération couvre la partie **analyse métier** et **structure logicielle** du projet.

### Livrables demandés (TP 2.1)

- `docs/DOMAIN_MODEL.md` 
  Contient :
  - acteurs et cas d’usage,
  - entités métier,
  - attributs et types,
  - relations et cardinalités,
  - règles métier,
  - diagramme UML (Mermaid).

- `docs/PACKAGE_STRUCTURE.md`
  Contient :
  - structure des packages,
  - rôle de chaque couche,
  - règles de dépendances,
  - dépendances interdites.

- `docs/DECISIONS.md`
  Contient :
  - décisions techniques clés (architecture, DTO, enums, permissions, persistance).

### Structure choisie

src/main/java/com/esieeit/projetsi/
  api/
    controller/
    dto/
  domain/
    model/
    enums/
  service/
  repository/
  exception/
  
## Auteurs — Groupe 6

Nolan Le Faou
Lucas Dos Santos
Othmane Hamoudi
Nuno Teixeira Ferreira
