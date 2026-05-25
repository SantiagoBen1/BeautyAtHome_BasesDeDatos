# BeautyAtHome — Frontend

## Descripción
Este directorio contiene los archivos del frontend de BeautyAtHome.

Actualmente incluye las plantillas Thymeleaf y estilos CSS del MVP original como referencia para la futura implementación de un frontend separado (React, Vue, Angular, etc.).

## Estructura

```
frontend/
├── templates/          # Plantillas HTML (Thymeleaf original)
│   ├── index.html
│   ├── bookings.html
│   ├── clients.html
│   ├── professionals.html
│   └── reviews.html
├── static/
│   └── css/
│       └── app.css     # Estilos globales
└── README.md
```

## Futura Implementación

Para implementar un frontend moderno separado:

1. Inicializar un proyecto con el framework elegido (ej: `npx create-vite@latest ./ --template react`)
2. Configurar las llamadas a la API REST del backend (`http://localhost:8080/api/...`)
3. El backend ya tiene configuración CORS habilitada para desarrollo local.

## Endpoints API disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/clients` | Registrar cliente |
| POST | `/api/professionals` | Registrar profesional |
| GET | `/api/professionals` | Buscar profesionales (params: zone, category) |
| GET | `/api/professionals/{id}/services` | Listar servicios de un profesional |
| GET | `/api/professionals/{id}/history` | Ver historial de servicios |
| POST | `/api/bookings` | Crear reserva |
| DELETE | `/api/bookings/{id}` | Cancelar reserva |
| POST | `/api/services` | Crear servicio |
| POST | `/api/reviews` | Agregar reseña |
| GET | `/api/reviews/professional/{id}/average` | Rating promedio |
| POST | `/api/photos` | Subir foto |
| POST | `/api/photos/consent` | Otorgar consentimiento |
