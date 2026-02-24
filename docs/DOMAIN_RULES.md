# DOMAIN_RULES — To-Do List (Project = Group)

## 1) Mapping
Dans ce TP, l’entité imposée **Project** correspond à un **Groupe** (ex : Foyer, Travail, Projet…).
Une Task appartient à un Project (= groupe).

---

## 2) Validations techniques (centralisées)

Les validations de base sont regroupées dans `domain/validation/Validators` :
- `notBlank(value, field)` : champ obligatoire (non null, non vide)
- `positive(id, field)` : identifiants strictement positifs
- `maxLength(value, max, field)` : taille maximale
- `email(value, field)` : format email simple + non vide

En cas d’erreur :
- `ValidationException` (hérite de `DomainException`)

---

## 3) Règles métier par entité

### 3.1 User
Classe : `domain/model/User`
- `id` doit être > 0
- `email` obligatoire + format email
- `passwordHash` obligatoire
- `displayName` obligatoire
- Valeurs par défaut :
  - `role = USER`
  - `active = true`
- Méthodes métier :
  - `changeDisplayName()` : refuse un nom vide
  - `changePassword()` : refuse un hash vide
  - `activate()` / `deactivate()`

> Règles de type “email unique” seront garanties plus tard par la couche persistence (DB) ou service.

---

### 3.2 Project (Group)
Classe : `domain/model/Project`
- `id` > 0
- `name` obligatoire
- `ownerId` > 0
- `createdAt` initialisé à la création
- Règle métier :
  - `rename(newName, actorUserId)` : seul le propriétaire (`ownerId`) peut renommer
  - Si non propriétaire : `BusinessRuleException`

---

### 3.3 Task
Classe : `domain/model/Task`
- `id` > 0
- `projectId` > 0 (une tâche appartient toujours à un groupe)
- `title` obligatoire
- `createdBy` > 0
- Valeurs par défaut :
  - `status = TODO`
  - `priority = MEDIUM`
  - `dueDate = null` (optionnel)
  - `assigneeId = null` (optionnel)
- Règles métier :
  - Workflow de statut :
    - `start()` : autorisé uniquement si status == TODO, sinon `BusinessRuleException`
    - `complete()` : autorisé uniquement si status == IN_PROGRESS, sinon `BusinessRuleException`
  - `assignTo(userId)` :
    - `null` autorisé pour “désassigner”
    - si non null : id doit être > 0
  - `updatedAt` est mis à jour à chaque modification (méthode `touch()`)

> La règle “on ne peut assigner qu’à un membre du même groupe” sera vérifiée dans la couche service,
car l’entité Task ne connaît pas la liste des membres.

---

### 3.4 Comment (optionnel)
Classe : `domain/model/Comment`
- `id` > 0
- `taskId` > 0
- `authorId` > 0
- `content` obligatoire, longueur max 300
- `createdAt` initialisé à la création

---

## 4) Exceptions du domaine
Package : `domain/exception`
- `DomainException` : base des exceptions métier
- `ValidationException` : erreurs de validation (champs invalides)
- `BusinessRuleException` : violation de règle métier (droits, workflow…)

---