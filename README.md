# TODO List API

API REST per gestire una lista di task: creazione, modifica, eliminazione, elenco con filtri,
più un job che ogni 15 minuti elimina i task completati.


## Avvio

Requisito: JDK 25 o superiore.

```bash
./mvnw spring-boot:run
```

Su Windows: `mvnw.cmd spring-boot:run`.

Dopo aver avviato l'applicazione le API si possono provare da Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoint CRUD

```
POST   /api/tasks                          crea un task
GET    /api/tasks?status=TODO&priority=HIGH elenco, filtri facoltativi
GET    /api/tasks/{id}                     dettaglio
PUT    /api/tasks/{id}                     modifica
DELETE /api/tasks/{id}                     elimina
```

Esempio di body per la creazione:

```json
{ "title": "titolo di esempio 1", "description": "desc 1", "priority": "HIGH" }
```

## Scelte tecniche

- Niente frontend: per provare le API c'è Swagger UI.
- Principio di singola responsabilità: controller, service e repository (architettura a Layer).
- DTO separati dall'entità.
- H2 in memoria: così il progetto parte senza installare niente. Lo schema lo gestisce **Flyway**
  (`ddl-auto=validate`): passare a PostgreSQL vuol dire cambiare solo la configurazione.
- Filtri con una query JPQL in cui un parametro null vuol dire "nessun filtro". Con molti filtri combinabili passerei alle JPA Specification.
- Job con `@Scheduled` e cron configurabile, al posto di Spring Batch che serve per elaborazioni grandi a blocchi. Il job fa una sola `DELETE` dei task `DONE`, senza caricarli in memoria.

## TODO

Cose a cui ho pensato ma che ho lasciato fuori da questa versione (sono segnate anche nel codice con `TODO`):

- eccezioni specifiche (es. `TaskNotFoundException`) gestite in un `@RestControllerAdvice`
- paginazione e altri filtri (ricerca nel testo, data di scadenza)
- `completedAt`, per eliminare i task completati solo dopo un certo tempo
- autenticazione e task per utente
- test
