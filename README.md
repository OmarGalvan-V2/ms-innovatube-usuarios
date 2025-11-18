## Running with Docker

This project includes a multi-stage Docker setup using Eclipse Temurin JDK 17 for both build and runtime. The application is packaged as a Spring Boot JAR and runs on port **8080** by default.

### Requirements
- Docker and Docker Compose installed
- No additional environment variables are required by default (uncomment `env_file` in `docker-compose.yml` if you add a `.env` file)

### Build and Run
To build and start the application using Docker Compose:

```sh
docker compose up --build
```

This will:
- Build the application using Maven Wrapper inside the container
- Run the app as a non-root user for improved security
- Expose port **8080** (Spring Boot default)

### Configuration
- The application runs with JVM options optimized for containers (`JAVA_OPTS` is set in the Dockerfile)
- If you need to override configuration, mount your own `application.properties` or use environment variables as needed

### Ports
- `8080:8080` – Main application HTTP port

### Networking
- The service is attached to a custom Docker network `appnet` for easy extension with other services

---

For further customization, edit the `docker-compose.yml` or `Dockerfile` as needed for your environment.