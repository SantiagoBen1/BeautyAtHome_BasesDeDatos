-- =========================================================
-- PROYECTO DE BASES DE DATOS - BeautyAtHome
-- Autor: Santiago Andrés Benavides Coral
-- Asignatura: Bases de Datos
-- Semestre: Sexto
-- ---------------------------------------------------------
-- Este script realiza:
-- 1. Ajustes estructurales de tablas
-- 2. Limpieza y reinicio de datos
-- 3. Inserción de datos de prueba
-- 4. Creación de vistas
-- 5. Configuración de roles y permisos
-- =========================================================

-- Agregar la columna faltante photo_url (Ignora el error si ya existe)
ALTER TABLE professionals ADD COLUMN IF NOT EXISTS photo_url VARCHAR(255);

-- =========================================================
-- LIMPIEZA DE LA BASE DE DATOS
-- =========================================================

TRUNCATE TABLE booking_service, professional_coverage, professional_service, photo_reference, reviews, bookings, services, coverage_areas, professionals, clients, brands, categorias RESTART IDENTITY CASCADE;

-- =========================================================
-- 1. REGISTRO DE CATEGORÍAS
-- =========================================================

-- Inserta las categorías principales disponibles
-- dentro de la plataforma BeautyAtHome.

INSERT INTO categorias (nombre) VALUES 
('Cuidado Capilar'),
('Cuidado de Uñas'),
('Maquillaje'),
('Masajes y Spa'),
('Cuidado Facial'),
('Barbería'),
('Depilación'),
('Tratamientos Corporales');

-- =========================================================
-- 2. REGISTRO DE MARCAS
-- =========================================================

-- Inserta las marcas y estudios asociados
-- a los profesionales de la plataforma.

INSERT INTO brands (brand_name, logo_url, description) VALUES 
('BeautyAtHome Pro', 'https://images.unsplash.com/photo-1560066984-138dadb4c035?auto=format&fit=crop&w=150&q=80', 'Marca premium de profesionales exclusivos.'),
('Independiente', 'https://images.unsplash.com/photo-1522337660859-02fbefca4702?auto=format&fit=crop&w=150&q=80', 'Talento independiente verificado.'),
('Glamour Studio', 'https://images.unsplash.com/photo-1596462502278-27bfdc403348?auto=format&fit=crop&w=150&q=80', 'Especialistas en maquillaje y estilismo de alto nivel.'),
('Zen Wellness', 'https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=150&q=80', 'Spa y relajación holística en la comodidad de tu hogar.'),
('Urban Barber Co.', 'https://images.unsplash.com/photo-1585747860715-2ba37e788b70?auto=format&fit=crop&w=150&q=80', 'Barbería clásica y moderna a domicilio.'),
('SkinCare Clinic', 'https://images.unsplash.com/photo-1556228578-0d85b1a4d571?auto=format&fit=crop&w=150&q=80', 'Expertos en dermatología estética y limpieza facial.'),
('Lash & Brow Experts', 'https://images.unsplash.com/photo-1588513706465-d421d0a51b5c?auto=format&fit=crop&w=150&q=80', 'Diseño de miradas, pestañas y perfilado de cejas.');
-- =========================================================
-- 3. REGISTRO DE CLIENTES
-- =========================================================

-- Inserta clientes de prueba con información básica
-- para realizar reservas dentro del sistema.

INSERT INTO clients (first_name, last_name, email, phone, password_hash, address) VALUES 
('Valentina', 'Ríos', 'vale.rios@email.com', '3101234567', 'hash_abc123', 'Cra 7 # 45-10, Chapinero'),
('Santiago', 'Morales', 'santi.m@email.com', '3209876543', 'hash_def456', 'Av 19 # 118-30, Usaquén'),
('Camila', 'Herrera', 'cami.h@email.com', '3154567890', 'hash_ghi789', 'Cl 53 # 24-12, Teusaquillo'),
('Mateo', 'Vargas', 'mateo.v@email.com', '3001112233', 'hash_jkl012', 'Cra 58 # 128-45, Suba'),
('Isabella', 'Castillo', 'isa.castillo@email.com', '3187778899', 'hash_mno345', 'Cl 26 # 68-10, Fontibón'),
('Daniel', 'Ramírez', 'daniel.r@email.com', '3105556677', 'hash_temp123', 'Calle 100 # 15-20, Chicó'),
('Mariana', 'Gómez', 'mariana.g@email.com', '3204445566', 'hash_temp456', 'Carrera 11 # 82-40, Rosales');

-- =========================================================
-- 4. REGISTRO DE PROFESIONALES
-- =========================================================

-- Inserta los profesionales disponibles junto
-- con su especialidad y calificación.

INSERT INTO professionals (id_brand, user_name, bio_experience, speciality, photo_url, phone, rating, status) VALUES 
(1, 'laura_estetica', '5 años en colorimetría y tratamientos capilares modernos.', 'Estilista Capilar', 'https://randomuser.me/api/portraits/women/44.jpg', '3111234567', 4.80, 'activo'),
(2, 'jorge_nails', 'Especialista en uñas acrílicas, polygel y nail art avanzado.', 'Manicurista', 'https://randomuser.me/api/portraits/men/32.jpg', '3222345678', 4.70, 'activo'),
(3, 'sofia_makeup', 'Maquilladora editorial y social. Master en contorno 3D.', 'Maquilladora', 'https://randomuser.me/api/portraits/women/68.jpg', '3333456789', 4.95, 'activo'),
(6, 'camila_skin', 'Cosmetóloga especialista en limpieza facial profunda y peeling.', 'Cosmetóloga', 'https://randomuser.me/api/portraits/women/12.jpg', '3444567890', 4.90, 'activo'),
(5, 'andres_barber', 'Barbero clásico. Cortes fade y perfilado de barba con toalla caliente.', 'Barbero', 'https://randomuser.me/api/portraits/men/11.jpg', '3555678901', 4.85, 'activo'),
(4, 'valentina_spa', 'Masoterapeuta enfocada en relajación holística y maderoterapia.', 'Masajista', 'https://randomuser.me/api/portraits/women/9.jpg', '3005556677', 4.90, 'activo'),
(7, 'lucia_lashes', 'Experta en extensiones de pestañas volumen ruso y lifting.', 'Lash Artist', 'https://randomuser.me/api/portraits/women/47.jpg', '3109998877', 4.85, 'activo'),
(1, 'mateo_hair', 'Experto en keratinas, balayage y tratamientos reestructurantes.', 'Colorista', 'https://randomuser.me/api/portraits/men/33.jpg', '3201112233', 4.75, 'activo'),
(2, 'isabella_wax', 'Especialista en depilación con cera española y técnica sin dolor.', 'Depiladora', 'https://randomuser.me/api/portraits/women/24.jpg', '3114445566', 4.60, 'activo'),
(4, 'roberto_masajes', 'Masajista deportivo y descontracturante avanzado.', 'Terapeuta Corporal', 'https://randomuser.me/api/portraits/men/66.jpg', '3157778899', 4.80, 'activo');

-- =========================================================
-- 5. REGISTRO DE SERVICIOS
-- =========================================================

-- Inserta los servicios ofrecidos por la plataforma,
-- incluyendo precio base y duración estimada.

INSERT INTO services (id_categoria, name, description, base_price, estimated_duration) VALUES 
(1, 'Corte de Cabello Mujer', 'Corte moderno incluye lavado y secado rápido.', 45000.0, 45),
(1, 'Balayage Premium', 'Decoloración técnica balayage con matizante.', 180000.0, 180),
(1, 'Aplicación de Keratina', 'Tratamiento alisador intensivo sin formol.', 150000.0, 120),
(2, 'Manicura Semipermanente', 'Manicura completa con esmalte semipermanente.', 35000.0, 60),
(2, 'Uñas Acrílicas (Set Nuevo)', 'Aplicación de set nuevo en técnica acrílica y diseño.', 80000.0, 120),
(3, 'Maquillaje Social', 'Maquillaje de noche para eventos con pestañas postizas.', 120000.0, 90),
(3, 'Maquillaje de Novia', 'Maquillaje HD a prueba de agua e hidratación previa.', 200000.0, 120),
(4, 'Masaje Relajante 60 min', 'Masaje de cuerpo completo con aceites esenciales.', 90000.0, 60),
(4, 'Masaje Descontracturante', 'Masaje profundo para liberar tensiones musculares.', 110000.0, 60),
(5, 'Limpieza Facial Profunda', 'Extracción, exfoliación y luz LED.', 85000.0, 90),
(6, 'Corte Clásico + Barba', 'Corte con tijera/máquina y arreglo de barba.', 40000.0, 45),
(7, 'Depilación Cera (Piernas completas)', 'Depilación con cera hipoalergénica.', 50000.0, 45),
(8, 'Maderoterapia Corporal', 'Sesión de masajes reductores con maderas.', 70000.0, 45),
(5, 'Lifting de Pestañas', 'Curvado natural de pestañas con keratina.', 60000.0, 60),
(2, 'Pedicura Spa', 'Limpieza profunda, exfoliación e hidratación.', 45000.0, 60);

-- =========================================================
-- 6. RELACIÓN PROFESIONAL - SERVICIO
-- =========================================================

-- Relaciona cada profesional con los servicios
-- que puede ofrecer dentro de la aplicación.

INSERT INTO professional_service (id_profesional, id_service) VALUES 
(1, 1), (1, 2), (1, 3),    -- laura: Cabello
(2, 4), (2, 5), (2, 15),   -- jorge: Uñas y pedicure
(3, 6), (3, 7),            -- sofia: Maquillaje social y novias
(4, 10),                   -- camila: Facial
(5, 11),                   -- andres: Barbería
(6, 8), (6, 13),           -- valentina: Masaje relajante, Maderoterapia
(7, 14),                   -- lucia: Lifting de pestañas
(8, 2), (8, 3),            -- mateo: Balayage, Keratina
(9, 12),                   -- isabella: Depilación
(10, 9);                   -- roberto: Masaje descontracturante
-- =========================================================
-- 7. REGISTRO DE ZONAS DE COBERTURA
-- =========================================================

-- Inserta los barrios y códigos postales
-- donde opera la plataforma.

INSERT INTO coverage_areas (zip_code, neighborhood_name) VALUES 
('110111', 'Chapinero'),
('110221', 'Usaquén'),
('110411', 'Suba'),
('110231', 'Teusaquillo'),
('110311', 'Fontibón'),
('110511', 'Chicó'),
('110611', 'Rosales'),
('110711', 'Cedritos');

-- =========================================================
-- 8. RELACIÓN PROFESIONAL - COBERTURA
-- =========================================================

-- Asocia los profesionales con las zonas
-- donde prestan sus servicios.

INSERT INTO professional_coverage (id_profesional, id_coverage) VALUES 
(1, 1), (1, 2), (1, 6),                     
(2, 1), (2, 3), (2, 5),                      
(3, 2), (3, 4), (3, 7),                             
(4, 4), (4, 2), (4, 8),                             
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5),      
(6, 6), (6, 7),
(7, 1), (7, 2), (7, 8),
(8, 1), (8, 6), (8, 7),
(9, 2), (9, 3), (9, 8),
(10, 1), (10, 2), (10, 4), (10, 6);

-- =========================================================
-- 9. REGISTRO DE RESERVAS
-- =========================================================

-- Inserta reservas de ejemplo realizadas
-- por clientes a diferentes profesionales.

INSERT INTO bookings (id_cliente, id_profesional, datetime_start, datetime_end, total_price, status) VALUES 
(1, 1, '2026-05-20 10:00:00', '2026-05-20 12:00:00', 120000.0, 'completado'), -- Valentina con Laura (Balayage)
(2, 2, '2026-05-21 14:00:00', '2026-05-21 15:30:00', 80000.0, 'completado'),  -- Santiago con Jorge (Uñas Acrílicas)
(3, 3, '2026-05-22 09:00:00', '2026-05-22 10:30:00', 120000.0, 'completado'), -- Camila con Sofia (Maquillaje Social)
(4, 4, '2026-05-23 16:00:00', '2026-05-23 17:30:00', 85000.0, 'completado'),  -- Mateo con Camila (Limpieza Facial)
(5, 5, '2026-05-24 11:00:00', '2026-05-24 11:45:00', 40000.0, 'completado'),  -- Isabella con Andres (Barbería)
(6, 6, '2026-05-24 15:00:00', '2026-05-24 16:00:00', 90000.0, 'completado'),  -- Daniel con Valentina (Masaje)
(7, 7, '2026-05-25 10:00:00', '2026-05-25 11:00:00', 60000.0, 'completado'),  -- Mariana con Lucia (Lifting Pestañas)
(1, 8, '2026-05-28 10:00:00', '2026-05-28 12:00:00', 150000.0, 'pendiente'),  -- Keratina pendiente
(2, 9, '2026-05-29 14:00:00', '2026-05-29 14:45:00', 50000.0, 'pendiente'),   -- Depilación pendiente
(3, 10, '2026-05-30 09:00:00', '2026-05-30 10:00:00', 110000.0, 'pendiente'), -- Masaje descontracturante pendiente
(4, 1, '2026-06-01 16:00:00', '2026-06-01 16:45:00', 45000.0, 'confirmado'),  -- Corte cabello confirmado
(5, 2, '2026-06-02 11:00:00', '2026-06-02 12:00:00', 35000.0, 'cancelado');   -- Manicura cancelada

-- =========================================================
-- 10. RELACIÓN RESERVA - SERVICIO
-- =========================================================

-- Relaciona las reservas con el servicio
-- solicitado por el cliente.

INSERT INTO booking_service (id_booking, id_service) VALUES 
(1, 2),   -- Balayage Premium
(2, 5),   -- Uñas Acrílicas
(3, 6),   -- Maquillaje Social
(4, 10),  -- Limpieza Facial Profunda
(5, 11),  -- Corte Clásico + Barba
(6, 8),   -- Masaje Relajante
(7, 14),  -- Lifting Pestañas
(8, 3),   -- Keratina
(9, 12),  -- Depilación
(10, 9),  -- Descontracturante
(11, 1),  -- Corte mujer
(12, 4);  -- Manicura Semipermanente

-- =========================================================
-- 12. REGISTRO DE FOTOS DE RESEÑAS
-- =========================================================

-- Guarda imágenes relacionadas con las
-- reseñas publicadas por los clientes.

INSERT INTO reviews (id_booking, rating, comment, created_date) VALUES 
(1, 5.00, 'El balayage quedó espectacular, Laura cuida muchísimo el cabello.', '2026-05-20'),
(2, 4.80, 'Mis uñas acrílicas quedaron hermosas, Jorge tiene mucho talento para el nail art.', '2026-05-21'),
(3, 5.00, 'El maquillaje me duró intacto toda la boda. ¡Gracias Sofía!', '2026-05-22'),
(4, 4.70, 'Mi piel quedó súper iluminada, Camila es muy profesional.', '2026-05-23'),
(5, 4.90, 'Excelente corte de barba, toalla caliente muy relajante.', '2026-05-24'),
(6, 5.00, 'Increíble masaje, se me quitó todo el estrés de la semana.', '2026-05-24'),
(7, 4.80, 'Las pestañas quedaron muy naturales, no ardió nada.', '2026-05-25');

-- =========================================================
-- 12. Insertar Referencias de Fotos a las Reseñas
-- =========================================================
INSERT INTO photo_reference (id_review, photo, s3_bucket_url) VALUES 
(1, 'resultado_balayage.jpg', 'https://images.unsplash.com/photo-1595476108010-b4d1f10d5e43?auto=format&fit=crop&w=300&q=80'),
(2, 'unas_acrilicas.jpg', 'https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?auto=format&fit=crop&w=300&q=80'),
(3, 'maquillaje_social.jpg', 'https://images.unsplash.com/photo-1512496115841-3450283a20d7?auto=format&fit=crop&w=300&q=80'),
(6, 'cuarto_masajes.jpg', 'https://images.unsplash.com/photo-1544161515-4ab6ce6db874?auto=format&fit=crop&w=300&q=80');

-- =========================================================
-- PARTE 2: VISTAS Y ROLES
-- =========================================================

-- Vista 1.2: Simplificación
DROP VIEW IF EXISTS ServiciosDisponibles;
CREATE VIEW ServiciosDisponibles AS
SELECT 
    ca.neighborhood_name AS vecindario,
    ca.zip_code AS codigo_postal,
    pr.user_name AS nombre,
    pr.phone AS telefono,
    pr.rating AS rating,
    se.name AS nombre_del_servicio,
    se.base_price AS precio_base,
    se.estimated_duration AS duracion_estimada,
    cat.nombre AS nombre_de_la_categoria
FROM coverage_areas ca
JOIN professional_coverage pc ON ca.id_coverage = pc.id_coverage
JOIN professionals pr ON pc.id_profesional = pr.id_profesional
JOIN professional_service ps ON ps.id_profesional = pr.id_profesional
JOIN services se ON se.id_service = ps.id_service
JOIN categorias cat ON cat.id=se.id_categoria
WHERE pr.status = 'activo';

SELECT * FROM ServiciosDisponibles;

-- Vista 1.3: Reporte
DROP VIEW IF EXISTS CategoriasReservadas;
CREATE VIEW CategoriasReservadas AS
SELECT 
    cat.nombre AS categoria,
    COUNT(b.id_booking) AS total_reservas,
    SUM(b.total_price) AS ingresos_totales,
    AVG(b.total_price) AS promedio_valor_reserva
FROM categorias cat
JOIN services se ON cat.id = se.id_categoria
JOIN booking_service bs ON se.id_service = bs.id_service
JOIN bookings b ON bs.id_booking = b.id_booking
GROUP BY cat.nombre
ORDER BY total_reservas DESC;

SELECT * FROM CategoriasReservadas;

-- Vista Seguridad / Simplificación
DROP VIEW IF EXISTS ReservasActivas;
CREATE VIEW ReservasActivas AS
SELECT 
    b.id_booking,
    c.first_name || ' ' || c.last_name AS cliente,
    c.email AS correo_cliente,
    c.phone AS telefono_cliente,
    pr.user_name AS profesional,
    b.datetime_start AS fecha_inicio,
    b.datetime_end AS fecha_fin,
    b.total_price AS valor_total,
    b.status AS estado_reserva
FROM bookings b
JOIN clients c ON b.id_cliente = c.id_cliente
JOIN professionals pr ON b.id_profesional = pr.id_profesional
WHERE b.status IN ('pendiente', 'confirmado');

SELECT * FROM ReservasActivas;

-- =========================================================
-- PARTE 3: USUARIOS Y PERMISOS DEL PROYECTO
-- =========================================================

DROP USER IF EXISTS usuario_consulta;
DROP USER IF EXISTS usuario_operador;
DROP USER IF EXISTS usuario_admin;

DROP ROLE IF EXISTS rol_consulta_barberia;
DROP ROLE IF EXISTS rol_operador_barberia;
DROP ROLE IF EXISTS rol_admin_barberia;

CREATE ROLE rol_consulta_barberia;
CREATE ROLE rol_operador_barberia;
CREATE ROLE rol_admin_barberia;

CREATE USER usuario_consulta WITH LOGIN PASSWORD 'Consulta123';
CREATE USER usuario_operador WITH LOGIN PASSWORD 'Operador123';
CREATE USER usuario_admin    WITH LOGIN PASSWORD 'Admin123';

GRANT rol_consulta_barberia TO usuario_consulta;
GRANT rol_operador_barberia TO usuario_operador;
GRANT rol_admin_barberia    TO usuario_admin;

-- Permiso para que postgres pueda usar SET ROLE y probar
GRANT rol_consulta_barberia TO CURRENT_USER;
GRANT rol_operador_barberia TO CURRENT_USER;
GRANT rol_admin_barberia    TO CURRENT_USER;

-- PERMITIR USO DEL ESQUEMA PUBLIC A LOS ROLES
GRANT USAGE ON SCHEMA public TO rol_consulta_barberia;
GRANT USAGE ON SCHEMA public TO rol_operador_barberia;
GRANT USAGE ON SCHEMA public TO rol_admin_barberia;

-- PERMISOS PARA ROL CONSULTA
GRANT SELECT ON ServiciosDisponibles TO rol_consulta_barberia;

-- PERMISOS PARA ROL OPERADOR
GRANT SELECT ON ServiciosDisponibles TO rol_operador_barberia;
GRANT SELECT ON ReservasActivas TO rol_operador_barberia;
GRANT SELECT ON CategoriasReservadas TO rol_operador_barberia;

GRANT SELECT, INSERT, UPDATE ON clients TO rol_operador_barberia;
GRANT SELECT, INSERT, UPDATE ON bookings TO rol_operador_barberia;
GRANT SELECT, INSERT, UPDATE ON booking_service TO rol_operador_barberia;
GRANT SELECT, INSERT, UPDATE ON reviews TO rol_operador_barberia;

GRANT SELECT ON services TO rol_operador_barberia;
GRANT SELECT ON professionals TO rol_operador_barberia;
GRANT SELECT ON professional_service TO rol_operador_barberia;
GRANT SELECT ON professional_coverage TO rol_operador_barberia;
GRANT SELECT ON coverage_areas TO rol_operador_barberia;
GRANT SELECT ON categorias TO rol_operador_barberia;
GRANT SELECT ON photo_reference TO rol_operador_barberia;
GRANT SELECT ON brands TO rol_operador_barberia;

-- PERMISOS DE SECUENCIAS PARA EL ROL OPERADOR
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO rol_operador_barberia;

-- PERMISOS PARA ROL ADMINISTRADOR
GRANT SELECT ON ServiciosDisponibles TO rol_admin_barberia;
GRANT SELECT ON ReservasActivas TO rol_admin_barberia;
GRANT SELECT ON CategoriasReservadas TO rol_admin_barberia;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO rol_admin_barberia;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO rol_admin_barberia;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO usuario_operador;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO usuario_operador;



-- PRUEBAS DE BD PARA ACTUALIZACIONES EN PRODUCCIÓN
SELECT * FROM clients;
SELECT * FROM professionals;
SELECT * FROM bookings;
SELECT * FROM reviews;