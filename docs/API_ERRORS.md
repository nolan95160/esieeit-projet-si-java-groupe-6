# API Errors - Documentation

## Format JSON standard

Toutes les erreurs de l'API sont retournées dans un format JSON uniforme :

```json
{
  "timestamp": "2026-03-24T10:00:00Z",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "La requête est invalide",
  "path": "/api/tasks",
  "details": [
    { "field": "title", "message": "title est obligatoire" }
  ]
}
```

## Catalogue des erreurs

### 400 Bad Request — VALIDATION_ERROR

**Déclencheur** : Un champ du DTO ne respecte pas les contraintes Bean Validation (`@NotBlank`, `@Size`, `@Pattern`).

**Endpoints concernés** : `POST /api/tasks`, `PUT /api/tasks/{id}`

**Exemple — title manquant (POST)** :
```json
{
  "timestamp": "2026-03-24T10:00:00Z",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "La requête est invalide",
  "path": "/api/tasks",
  "details": [
    { "field": "title", "message": "title est obligatoire" }
  ]
}
```

**Exemple — title trop court** :
```json
{
  "timestamp": "2026-03-24T10:01:00Z",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "La requête est invalide",
  "path": "/api/tasks",
  "details": [
    { "field": "title", "message": "title doit contenir entre 3 et 120 caractères" }
  ]
}
```

**Exemple — status invalide (PUT)** :
```json
{
  "timestamp": "2026-03-24T10:02:00Z",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "La requête est invalide",
  "path": "/api/tasks/1",
  "details": [
    { "field": "status", "message": "status doit être parmi TODO, IN_PROGRESS, DONE" }
  ]
}
```

---

### 400 Bad Request — INVALID_DATA

**Déclencheur** : Les données sont techniquement valides mais incohérentes (ex : valeur de statut inconnue après parsing).

**Endpoints concernés** : `PUT /api/tasks/{id}`

**Exemple** :
```json
{
  "timestamp": "2026-03-24T10:03:00Z",
  "status": 400,
  "error": "INVALID_DATA",
  "message": "Statut invalide: UNKNOWN",
  "path": "/api/tasks/1",
  "details": []
}
```

---

### 404 Not Found — NOT_FOUND

**Déclencheur** : La ressource demandée n'existe pas (id introuvable).

**Endpoints concernés** : `GET /api/tasks/{id}`, `PUT /api/tasks/{id}`, `DELETE /api/tasks/{id}`

**Exemple** :
```json
{
  "timestamp": "2026-03-24T10:04:00Z",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Task introuvable: id=9999",
  "path": "/api/tasks/9999",
  "details": []
}
```

---

### 409 Conflict — BUSINESS_RULE_VIOLATION

**Déclencheur** : Une règle métier est violée (ex : transition de statut interdite).

**Endpoints concernés** : `PUT /api/tasks/{id}`

**Exemple** :
```json
{
  "timestamp": "2026-03-24T10:05:00Z",
  "status": 409,
  "error": "BUSINESS_RULE_VIOLATION",
  "message": "Transition de statut interdite: TODO -> DONE",
  "path": "/api/tasks/1",
  "details": []
}
```

---

### 500 Internal Server Error — INTERNAL_ERROR

**Déclencheur** : Erreur inattendue côté serveur (bug, exception non prévue).

**Endpoints concernés** : Tous

**Exemple** :
```json
{
  "timestamp": "2026-03-24T10:06:00Z",
  "status": 500,
  "error": "INTERNAL_ERROR",
  "message": "Une erreur inattendue est survenue",
  "path": "/api/tasks",
  "details": []
}
```

## Récapitulatif

| Code HTTP | Error Code              | Cause                                  |
|-----------|-------------------------|----------------------------------------|
| 400       | VALIDATION_ERROR        | Champ DTO invalide (Bean Validation)   |
| 400       | INVALID_DATA            | Données incohérentes                   |
| 404       | NOT_FOUND               | Ressource introuvable                  |
| 409       | BUSINESS_RULE_VIOLATION | Règle métier violée                    |
| 500       | INTERNAL_ERROR          | Erreur serveur inattendue              |
