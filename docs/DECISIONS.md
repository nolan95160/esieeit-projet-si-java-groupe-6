# Technical Decisions

## D-001 — Architecture en couches

**Décision :** Structure `api / domain / service / repository / exception`.  
**Pourquoi :** Séparer les responsabilités et faciliter les tests/maintenance.

## D-002 — Entité d’association GroupMember

**Décision :** Modéliser l’adhésion aux groupes avec une entité dédiée `GroupMember` au lieu d’une relation directe.  
**Pourquoi :** Porter des informations métier (rôle OWNER/MEMBER, date d’adhésion) et gérer proprement les permissions.

## D-003 — DTO côté API

**Décision :** Utiliser des DTO Request/Response dans `api/dto`.  
**Pourquoi :** Ne pas exposer directement les entités domaine et mieux contrôler les données entrantes/sortantes.

## D-004 — Enums métier

**Décision :** Utiliser des enums (`TaskStatus`, `TaskPriority`, `GroupRole`).  
**Pourquoi :** Éviter les valeurs invalides et sécuriser la logique métier.

## D-005 — Règles d’accès centralisées dans service

**Décision :** Vérifier les permissions (propriétaire, membre, assignation) dans la couche `service`.  
**Pourquoi :** Éviter la duplication dans les controllers et garantir une logique cohérente.

## D-006 — Persistance isolée

**Décision :** Limiter `repository` à l’accès aux données, sans logique métier complexe.  
**Pourquoi :** Respect des responsabilités et meilleure évolutivité.

## D-007 — MVP sans collaboration temps réel avancée

**Décision :** Pas de WebSocket/notifications temps réel dans le MVP.  
**Pourquoi :** Respect du périmètre du TP et livraison fiable des fonctionnalités prioritaires.

## D-008 — Suppression de groupe

**Décision :** Suppression en cascade des tâches (et commentaires associés) lors de la suppression d’un groupe.  
**Pourquoi :** Cohérence fonctionnelle et intégrité des données.