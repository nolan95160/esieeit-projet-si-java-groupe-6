# Package Structure

## Structure actuelle

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

## Rôle de chaque package

api/controller : endpoints REST
api/dto : objets Request/Response
domain/model : entités métier
domain/enums : énumérations métier
service : logique métier
repository : accès base de données
exception : exceptions personnalisées

## Règles de dépendance

controller -> service
controller <-> dto
service -> domain + repository
repository -> domain
domain ne dépend pas de api/repository

## Dépendances interdites

controller -> repository (interdit)
domain -> api (interdit)
domain -> repository (interdit)