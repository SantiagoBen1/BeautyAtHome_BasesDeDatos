# Backend - BeautyAtHome (Capa de Base de Datos y Lógica)

**Creador:** Santiago Andrés Benavides Coral - 20232020036
**Asignatura:** Bases de Datos (Sexto Semestre)

## Arquitectura de Datos (ORM y JPA)

Este directorio alberga el Backend del proyecto desarrollado en **Spring Boot (Java)**. El principal objetivo de esta capa, desde la perspectiva de bases de datos, es gestionar la conexión, las consultas y la persistencia hacia PostgreSQL mediante **Hibernate (Spring Data JPA)**.

### Mapeo de Entidades (`com/beautyathome/entities/`)

Las clases dentro de la carpeta de entidades son la representación viva de nuestras tablas SQL en código Java.

- **Anotaciones Clave:** Se usan `@Entity` y `@Table` para atar clases a tablas. `@Id` y `@GeneratedValue` automatizan la asignación de IDs primarios (`SERIAL` en SQL).
- **Relaciones:**
  - `@ManyToOne`: Por ejemplo, múltiples profesionales pertenecen a una marca (`BrandEntity`).
  - `@ManyToMany`: Configurado con `@JoinTable` para modelar de forma transparente las tablas intermedias complejas, como la asignación de servicios a una reserva (`booking_service`) o las zonas de cobertura de un profesional (`professional_coverage`).
- **Restricciones DDL:** Se utilizaron anotaciones como `nullable = false` o `unique = true` en `@Column` para garantizar la integridad a nivel de código antes de llegar a la base de datos.

### Repositorios (`com/beautyathome/repositories/`)

Las interfaces que extienden de `JpaRepository` actúan como la capa DAO (Data Access Object).

- **Consultas Automáticas:** Hibernate genera automáticamente las consultas SQL subyacentes con solo declarar firmas de métodos como `findByProfessionalId(Integer id)`.
- **Consultas Nativas Personalizadas:** En escenarios de uniones complejas, como `JpaServiceRepository`, se utilizó la anotación `@Query(nativeQuery = true)` para escribir e inyectar sentencias SQL nativas que optimizan inserciones en tablas Many-to-Many (`INSERT INTO professional_service...`).

### Adaptadores de Persistencia (`com/beautyathome/services/`)

Esta capa es crucial para aplicar el patrón de _Arquitectura Hexagonal_. Los adaptadores (ej. `BookingPersistenceAdapter`) traducen los datos del dominio interno de la aplicación a las `Entities` transaccionales de JPA antes de llamar al repositorio, previniendo así la fuga de abstracciones SQL al resto del sistema.

### Controladores Web y APIs (`com/beautyathome/controllers/`)

(Endpoints) Gestionan las peticiones HTTP (`GET`, `POST`) de la interfaz, orquestando las interacciones entre los formularios web y las transacciones de bases de datos de forma segura, garantizando propiedades ACID.
