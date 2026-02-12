# Domain Model

## 1) Contexte métier

Application de gestion de tâches pour particuliers et professionnels, organisée par **groupes** (Foyer, Travail, Projet, etc.) avec collaboration simple (membres, assignation, statuts, priorité, échéance).

---

## 2) Acteurs

- **Visiteur** : s’inscrire / se connecter
- **Utilisateur** : gérer ses groupes et tâches
- **Propriétaire de groupe** : administrer groupe (renommer, supprimer, inviter, retirer membres)
- **Membre de groupe** : consulter et gérer les tâches du groupe
- **Admin (optionnel)** : supervision globale

---

## 3) Cas d’usage principaux (issus du backlog)

1. Inscription
2. Connexion / Déconnexion
3. Créer un groupe
4. Voir mes groupes
5. Renommer / supprimer un groupe
6. Inviter / retirer un membre
7. Créer une tâche dans un groupe
8. Voir les tâches d’un groupe
9. Modifier / supprimer une tâche
10. Changer le statut d’une tâche
11. Assigner une tâche
12. Définir échéance et priorité
13. Filtrer / rechercher des tâches

---

## 4) Entités du domaine

## User
- `id: Long`
- `email: String`
- `passwordHash: String`
- `displayName: String`
- `role: UserRole` (optionnel si admin)
- `active: boolean` (optionnel)

## Group
- `id: Long`
- `name: String`
- `ownerId: Long`
- `createdAt: Instant`

## GroupMember
*(entité d’association User <-> Group pour gérer les membres et rôles)*
- `id: Long`
- `groupId: Long`
- `userId: Long`
- `role: GroupRole` (`OWNER`, `MEMBER`)
- `joinedAt: Instant`

## Task
- `id: Long`
- `groupId: Long`
- `title: String`
- `description: String`
- `status: TaskStatus`
- `priority: TaskPriority`
- `dueDate: Instant` (nullable)
- `assigneeId: Long` (nullable)
- `createdBy: Long`
- `createdAt: Instant`
- `updatedAt: Instant`

## Comment (optionnel selon avancement)
- `id: Long`
- `taskId: Long`
- `authorId: Long`
- `content: String`
- `createdAt: Instant`

---

## 5) Énumérations

## TaskStatus
- `TODO`
- `IN_PROGRESS`
- `DONE`

## TaskPriority
- `LOW`
- `MEDIUM`
- `HIGH`

## GroupRole
- `OWNER`
- `MEMBER`

## UserRole (optionnel)
- `USER`
- `ADMIN`

---

## 6) Relations et cardinalités

- Un **User** peut posséder `0..*` **Group**
- Un **Group** a exactement `1` propriétaire (User)
- Un **Group** a `1..*` **GroupMember**
- Un **User** peut appartenir à `0..*` groupes (via GroupMember)
- Un **Group** contient `0..*` **Task**
- Une **Task** appartient à `1` groupe
- Une **Task** peut être assignée à `0..1` user
- Une **Task** peut avoir `0..*` commentaires (si Comment implémenté)
- Un **Comment** appartient à `1` tâche et `1` auteur

---

## 7) Règles métier (invariants)

1. Le nom d’un groupe est obligatoire.
2. Le propriétaire d’un groupe doit aussi être membre du groupe (role `OWNER`).
3. Seul le propriétaire peut renommer/supprimer le groupe.
4. Seul le propriétaire peut inviter/retirer des membres.
5. On ne peut pas retirer le propriétaire du groupe.
6. Le titre d’une tâche est obligatoire.
7. Une tâche appartient obligatoirement à un groupe.
8. On ne peut assigner une tâche qu’à un membre du même groupe.
9. Le statut d’une tâche doit être dans `TaskStatus`.
10. La priorité d’une tâche doit être dans `TaskPriority`.
11. Une date d’échéance, si présente, doit être valide.
12. Un non-membre ne peut pas consulter les tâches d’un groupe.

---

## 8) Diagramme UML (Mermaid)

```mermaid
classDiagram
direction LR

class User {
  Long id
  String email
  String passwordHash
  String displayName
  UserRole role
  boolean active
}

class Group {
  Long id
  String name
  Long ownerId
  Instant createdAt
}

class GroupMember {
  Long id
  Long groupId
  Long userId
  GroupRole role
  Instant joinedAt
}

class Task {
  Long id
  Long groupId
  String title
  String description
  TaskStatus status
  TaskPriority priority
  Instant dueDate
  Long assigneeId
  Long createdBy
  Instant createdAt
  Instant updatedAt
}

class Comment {
  Long id
  Long taskId
  Long authorId
  String content
  Instant createdAt
}

class TaskStatus {
  <<enumeration>>
  TODO
  IN_PROGRESS
  DONE
}

class TaskPriority {
  <<enumeration>>
  LOW
  MEDIUM
  HIGH
}

class GroupRole {
  <<enumeration>>
  OWNER
  MEMBER
}

class UserRole {
  <<enumeration>>
  USER
  ADMIN
}

User "1" --> "0..*" Group : owns
Group "1" --> "1..*" GroupMember : has
User "1" --> "0..*" GroupMember : belongsTo
Group "1" --> "0..*" Task : contains
Task "0..1" --> "1" User : assignedTo
Task "1" --> "0..*" Comment : has
Comment "1" --> "1" User : author
Task --> TaskStatus
Task --> TaskPriority
GroupMember --> GroupRole
User --> UserRole