# Frontend - BeautyAtHome (Capa de Presentación Web)

**Creador:** Santiago Andrés Benavides Coral - 20232020036
**Creador:** Miguel Andres Contreras Rodriguez - 20232020020
**Creador:** Sergio Nicolas Osorio Guevara - 20241020073
**Creador:** Adiel Valentin Hernandez  - 20201020144
**Asignatura:** Bases de Datos (Sexto Semestre)

## Relación con la Base de Datos

Aunque este directorio contiene código orientado al lado del cliente (HTML, CSS), es la interfaz principal a través de la cual los usuarios interactúan y alteran el estado de la base de datos PostgreSQL. Todo el frontend es renderizado directamente por el servidor backend utilizando **Thymeleaf**, lo cual crea un vínculo estrecho entre los datos relacionales y la interfaz visual.

### Recolección de Datos (Formularios)

Las plantillas (`.html`) están diseñadas estratégicamente para mapearse exactamente con las Entidades (y eventualmente tablas) de la base de datos.

- Formularios como los de `clients.html` o `professionals.html` validan tipos de campos nativos (ej. `type="email"`, `type="tel"`) que garantizan que el input del usuario cumpla restricciones DDL y de dominio antes de convertirse en consultas `INSERT` o `UPDATE`.
- La información sobre múltiples servicios seleccionados y zonas de cobertura se procesan de texto plano y se insertan a tablas dependientes (`One-to-Many`) en el backend.

### Lectura de Datos (`SELECT` queries)

Gracias al motor de renderizado del lado del servidor, cuando el usuario navega a las pestañas de Profesionales o Reservas:

1. El backend ejecuta múltiples consultas y uniones (JOINs) SQL automatizadas a través de JPA para extraer información (ej. "Obtener un Profesional + su Marca + sus Servicios + sus Reseñas").
2. Thymeleaf (en el HTML) usa la sintaxis iterativa (`th:each`) para dibujar tablas y tarjetas (Studio Capsules, Embajadores) pobladas dinámicamente con estas relaciones SQL extraídas sin exponer ninguna lógica SQL al cliente por seguridad.
