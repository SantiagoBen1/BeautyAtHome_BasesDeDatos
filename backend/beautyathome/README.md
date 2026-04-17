## BeautyAtHome Backend

Servicio REST en Spring Boot que orquesta reservas de belleza a domicilio, gestiona agendas sin solapes y expone reseñas verificadas. Este módulo corresponde al backend del monorepo académicamente orientado.

---

### 🧱 Stack y arquitectura
- Java 21 + Spring Boot 3.3.5 (web, validation) empaquetado con Maven.
- Capas internas:
  - `api.controller`: controladores REST y mapeo de DTOs.
  - `application.*`: servicios de caso de uso (`BookingService`, `BeautyAtHomeFacade`, validadores).
  - `domain.*`: entidades ricas y patrones (Builder para reservas, Strategy para precios, Singleton para agenda, Observer/State para el ciclo de booking).
  - `infrastructure.*`: adaptadores de persistencia, multimedia y proxys de consentimiento.
- Configuración externa centralizada en `src/main/resources/application.yml`.

---

### ✅ Requisitos previos
- JDK 21 (o superior compatible con Spring Boot 3.3.x).
- Maven 3.9+.
- Git y una terminal con soporte para scripts de Maven.

Verifica las versiones:
```powershell
java -version
mvn -version
```

---

### 🚀 Puesta en marcha local
1. Clona el repo y entra al módulo:
	```powershell
	git clone https://github.com/AlicePQ/BeautyAtHome.git
	cd BeautyAtHome/backend/beautyathome
	```
2. Compila y ejecuta pruebas:
	```powershell
	mvn clean verify
	```
3. Levanta la API (por defecto en `http://localhost:8080`):
	```powershell
	mvn spring-boot:run
	```

Para producir un JAR ejecutable:
```powershell
mvn clean package
java -jar target/beautyathome-0.0.1-SNAPSHOT.jar
```

---

### 📂 Estructura relevante
```
backend/beautyathome
├─ pom.xml                 # Gestión de dependencias y plugins
├─ src/main/java/com/beautyathome
│  ├─ api/controller       # Endpoints REST (p.ej. BookingController)
│  ├─ application          # Servicios, facades y validaciones
│  ├─ domain               # Modelo de dominio y patrones
│  └─ infrastructure       # Persistencia, media y proxies
└─ src/main/resources
	└─ application.yml      # Configuración (puertos, datasources, etc.)
```

---

### 🔐 Configuración y perfiles
- Las propiedades por defecto viven en `application.yml`.
- Para entornos alternos crea archivos `application-{perfil}.yml` y arranca con `--spring.profiles.active=perfil`.
- Variables sensibles (tokens, credenciales) deben inyectarse vía variables de entorno o un gestor de secretos; evita commitearlas.

---

### 🧪 Calidad y pruebas
- `mvn test`: ejecuta la suite unitaria.
- Agrega pruebas en `src/test/java` siguiendo la convención `*Test`.
- Usa `@SpringBootTest` para pruebas integrales y `@WebMvcTest` para controladores aislados.

---

### 🛣️ Roadmap técnico corto
- Persistencia real (actualmente in memory) con repositorios JPA o puertos/adapter.
- Hardening de validaciones y manejo de excepciones global.
- Documentación OpenAPI en `SwaggerConfig` expuesta en `/swagger-ui`.

---

Hecho con cariño para el curso de Modelos de Programación. Si algo se rompe, es tu culpa. ✨
