# BeautyAtHome - Sistema de Gestión de Belleza a Domicilio

**Creador:** Santiago Andrés Benavides Coral - 20232020036
**Asignatura:** Bases de Datos

## Descripción General del Proyecto

**BeautyAtHome** es una plataforma integral diseñada para conectar a profesionales de la belleza (estilistas, maquilladores, masajistas, etc.) con clientes que buscan servicios a domicilio. La aplicación permite gestionar usuarios (clientes y profesionales), reservar servicios, registrar catálogos, asignar coberturas por zonas, y calificar las experiencias recibidas.

El proyecto está diseñado con un fuerte enfoque en el **diseño y gestión de Bases de Datos Relacionales**, garantizando la integridad referencial, el manejo de relaciones (One-to-Many, Many-to-Many), y la persistencia de datos mediante un ORM (Object-Relational Mapping).

## Arquitectura de la Base de Datos

La aplicación utiliza **PostgreSQL** como motor de base de datos principal, interactuando a través de **Spring Data JPA (Hibernate)**.

### Características principales de la BD:

1. **Normalización:** La base de datos sigue las reglas de normalización para evitar redundancia (ej: la separación de `brands`, `categories` y `coverage_areas`).
2. **Integridad Referencial:** Uso de Llaves Foráneas (`FOREIGN KEY`) con políticas de cascada (`ON DELETE CASCADE`) donde sea apropiado para mantener consistencia.
3. **Mapeo Objeto-Relacional (ORM):** Las tablas están representadas en Java usando anotaciones JPA (`@Entity`, `@Table`, `@Column`, `@OneToMany`, `@ManyToMany`), facilitando las operaciones CRUD.
4. **Relaciones Complejas (Many-to-Many):**
   - **Profesionales y Servicios:** Un profesional ofrece múltiples servicios y un servicio puede ser prestado por múltiples profesionales (`professional_service`).
   - **Profesionales y Zonas de Cobertura:** Cada profesional puede atender en distintas zonas (`professional_coverage`).
   - **Reservas y Servicios:** Una reserva puede incluir múltiples servicios adquiridos (`booking_service`).

## Estructura de Directorios

- `/backend`: Contiene la lógica del servidor (Spring Boot), la configuración de conexión a la base de datos, las Entidades (Entities) que mapean las tablas, y los Repositorios que ejecutan las consultas SQL.
- `/frontend`: Contiene las plantillas web (`HTML`/`Thymeleaf`) y estilos (`CSS`), donde los datos extraídos de la base de datos se presentan al usuario final.
- `inserts_test_data.sql`: Script SQL que inicializa la base de datos con información de prueba (10 profesionales, clientes, reservas, y reseñas) para validar la integridad y las consultas del sistema.

## Instrucciones de Ejecución

1. Asegúrate de tener **PostgreSQL** corriendo localmente (puerto `5432`).
2. Crea una base de datos vacía llamada `beautyathome_db`.
3. Navega al directorio `/backend/beautyathome` en tu terminal.
4. Ejecuta el comando:
   ```bash
   mvn spring-boot:run
   ```
5. Accede a la aplicación desde tu navegador en `http://localhost:8080`.
6. Opcionalmente, ejecuta el script `inserts_test_data.sql` en tu gestor de base de datos para cargar los datos de demostración y visualizar el funcionamiento completo.

---
