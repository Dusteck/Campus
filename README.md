# CampusOps

Campus equipment management application built with Next.js, TypeScript, Java 21, and Spring Boot.

The current implementation provides a frontend page that checks the backend health API. Database-backed features will be added next.

## Requirements

- Node.js and npm compatible with the frontend dependencies.
- JDK 21, with `JAVA_HOME` pointing to the JDK folder and its `bin` directory on `PATH`.
- VS Code with Extension Pack for Java for running the backend from the editor.
- Docker Desktop for the upcoming MySQL setup; the current health-check workflow does not require a database.

## Project layout

```text
frontend/                           Next.js + TypeScript
backend/                            Spring Boot + Maven Wrapper
  src/main/java/com/campusops/backend/
    controller/                     REST endpoints
    service/                        Business logic
    repository/                     Database access
    entity/                         Persistence entities
    dto/                            Request and response objects
    config/                         Application configuration
    security/                       Authentication and authorization
    exception/                      Error handling
```

## Start the frontend

In VS Code, right-click `frontend` and select **Open in Integrated Terminal**. Install dependencies on the first run, then start the development server:

```powershell
npm.cmd ci
npm.cmd run dev
```

Open [http://localhost:3000](http://localhost:3000). Keep the terminal running.

## Start the backend

In VS Code, open `backend/src/main/java/com/campusops/backend/BackendApplication.java` and click **Run** above `main`. Make sure the Java project runtime is JDK 21.

Alternatively, open an integrated terminal in `backend` and run:

```powershell
.\mvnw.cmd spring-boot:run
```

The first run downloads Maven and the project dependencies. The backend uses port 8080. Use either the VS Code Run action or the terminal command to start it; running both will cause a port conflict.

## Check the connection

With both servers running:

1. Open [http://localhost:8080/api/health](http://localhost:8080/api/health) to check Spring Boot directly.
2. Open [http://localhost:3000/api/health](http://localhost:3000/api/health) to check the Next.js API proxy.
3. Open [http://localhost:3000](http://localhost:3000) and click **Check backend**.

The expected response is:

```json
{
  "status": "UP",
  "service": "CampusOps backend"
}
```

JSON field order may differ. Next.js forwards `/api/*` requests to `http://127.0.0.1:8080/api/*`. Restart the frontend after changing `frontend/next.config.ts`.

## Verify the project

From an integrated terminal in `backend`:

```powershell
.\mvnw.cmd -v
.\mvnw.cmd clean verify
```

Maven should report Java 21 and finish with `BUILD SUCCESS`. Build verification has not yet been confirmed for this setup.

From an integrated terminal in `frontend`:

```powershell
npm.cmd run lint
npm.cmd run build
```

Stop a terminal-launched development server with **Ctrl+C**. For a backend launched through VS Code, use its **Stop** control.

## Local files

Local environment files, uploaded files, build output, and the three personal planning guides are ignored by Git. `.env.example` files may be tracked as templates; use placeholder values rather than credentials.
