-- Agregar la columna faltante photo_url (Ignora el error si ya existe)
ALTER TABLE professionals ADD COLUMN IF NOT EXISTS photo_url VARCHAR(255);

-- Limpiar la base de datos y REINICIAR los contadores (SERIAL) a 1
TRUNCATE TABLE booking_service, professional_coverage, professional_service, reviews, bookings, services, coverage_areas, professionals, clients, brands, categorias RESTART IDENTITY CASCADE;

-- 1. Insertar Categorías
INSERT INTO categorias (nombre) VALUES 
('Cabello'), 
('Uñas'), 
('Maquillaje'), 
('Masajes'),
('Cuidado Facial'),
('Barbería');

-- 2. Insertar Marcas
INSERT INTO brands (brand_name, logo_url, description) VALUES 
('BeautyAtHome Pro', 'https://images.unsplash.com/photo-1560066984-138dadb4c035?auto=format&fit=crop&w=150&q=80', 'Nuestra marca oficial de profesionales premium.'),
('Independiente', 'https://images.unsplash.com/photo-1522337660859-02fbefca4702?auto=format&fit=crop&w=150&q=80', 'Talento independiente verificado y de alta calidad.'),
('Glamour Studio', 'https://images.unsplash.com/photo-1596462502278-27bfdc403348?auto=format&fit=crop&w=150&q=80', 'Estudio exclusivo de belleza a domicilio.');

-- 3. Insertar Clientes (Con contraseñas de prueba)
INSERT INTO clients (first_name, last_name, email, phone, password_hash, address) VALUES 
('Ana', 'García', 'ana@example.com', '3001234567', 'hash_temporal_123', 'Calle 123 #45-67, Bogotá'),
('Carlos', 'López', 'carlos@example.com', '3109876543', 'hash_temporal_123', 'Carrera 14 #89-00, Medellín'),
('María', 'Rodríguez', 'maria@example.com', '3205554433', 'hash_temporal_123', 'Avenida 19 #100-20, Cali'),
('Elena', 'Martínez', 'elena@example.com', '3009998877', 'hash_temporal_123', 'Calle 50 #10-20, Bogotá'),
('Diego', 'Fernández', 'diego@example.com', '3157776655', 'hash_temporal_123', 'Diagonal 45 #8-12, Bogotá');

-- 4. Insertar Profesionales (Con photo_url!)
-- status: 'activo', 'inactivo'
INSERT INTO professionals (id_brand, user_name, bio_experience, speciality, photo_url, phone, rating, status) VALUES 
(1, 'laura_stylist', 'Estilista experta con 5 años de experiencia en colorimetría y cortes modernos.', 'Colorista', 'https://randomuser.me/api/portraits/women/44.jpg', '3001112233', 4.9, 'activo'),
(2, 'jorge_nails', 'Especialista en acrílicas y nail art detallado. Creatividad garantizada.', 'Manicurista', 'https://randomuser.me/api/portraits/men/32.jpg', '3112223344', 4.7, 'activo'),
(1, 'sofia_makeup', 'Maquilladora profesional para eventos sociales y bodas.', 'Maquilladora', 'https://randomuser.me/api/portraits/women/68.jpg', '3223334455', 5.0, 'activo'),
(3, 'camila_skin', 'Cosmetóloga especialista en limpieza facial profunda y tratamientos anti-edad.', 'Cosmetóloga', 'https://randomuser.me/api/portraits/women/12.jpg', '3104445566', 4.8, 'activo'),
(2, 'andres_barber', 'Barbero clásico. Cortes fade y perfilado de barba con toalla caliente.', 'Barbero', 'https://randomuser.me/api/portraits/men/11.jpg', '3156667788', 4.9, 'activo'),
(1, 'valentina_spa', 'Masoterapeuta enfocada en la relajación holística.', 'Masajista', 'https://randomuser.me/api/portraits/women/9.jpg', '3189990011', 4.6, 'activo'),
(3, 'lucia_nails', 'Especialista en uñas esculpidas en gel y técnicas rusas.', 'Manicurista', 'https://randomuser.me/api/portraits/women/47.jpg', '3201119988', 4.8, 'activo'),
(2, 'mateo_hair', 'Experto en keratinas y tratamientos capilares reestructurantes.', 'Estilista Capilar', 'https://randomuser.me/api/portraits/men/33.jpg', '3124448899', 4.7, 'activo'),
(1, 'isabella_makeup', 'Maquillaje editorial y social avanzado. Incluye contorno 3D.', 'Maquilladora', 'https://randomuser.me/api/portraits/women/24.jpg', '3005556677', 4.9, 'activo');

-- 5. Insertar Servicios
INSERT INTO services (id_categoria, name, description, base_price, estimated_duration) VALUES 
(1, 'Corte de Cabello Mujer', 'Corte moderno incluye lavado y secado rápido.', 45000.0, 45),
(1, 'Balayage Premium', 'Decoloración técnica balayage con matizante.', 180000.0, 180),
(2, 'Manicura Semipermanente', 'Manicura completa con esmalte semipermanente de larga duración.', 35000.0, 60),
(2, 'Pedicura Spa', 'Limpieza profunda, exfoliación e hidratación.', 40000.0, 60),
(3, 'Maquillaje Social', 'Maquillaje de noche para eventos con pestañas postizas.', 120000.0, 90),
(4, 'Masaje Relajante', 'Masaje de cuerpo completo con aceites esenciales.', 90000.0, 60),
(5, 'Limpieza Facial Profunda', 'Extracción de impurezas, mascarilla hidratante y luz LED.', 80000.0, 90),
(6, 'Corte Clásico + Barba', 'Corte con tijera o máquina y arreglo de barba.', 40000.0, 45),
(1, 'Aplicación de Keratina', 'Tratamiento alisador intensivo.', 150000.0, 120),
(2, 'Uñas Acrílicas (Set Nuevo)', 'Aplicación de set nuevo en técnica acrílica.', 70000.0, 120);

-- 6. Asociar Profesionales con Servicios (professional_service)
INSERT INTO professional_service (id_profesional, id_service) VALUES 
(1, 1), (1, 2), -- Laura: Cabello
(2, 3), (2, 4), (2, 10), -- Jorge: Uñas
(3, 5), -- Sofia: Maquillaje
(4, 7), -- Camila: Facial
(5, 8), -- Andres: Barbería
(6, 6), -- Valentina: Masajes
(7, 3), (7, 10), -- Lucia: Uñas
(8, 1), (8, 9), -- Mateo: Cabello / Keratina
(9, 5); -- Isabella: Maquillaje

-- 7. Insertar Zonas de Cobertura
INSERT INTO coverage_areas (zip_code, neighborhood_name) VALUES 
('110111', 'Chapinero'),
('110221', 'Usaquén'),
('110311', 'Teusaquillo'),
('110411', 'Chicó'),
('110511', 'Rosales');

-- 8. Asociar Profesionales con Zonas de Cobertura (professional_coverage)
INSERT INTO professional_coverage (id_profesional, id_coverage) VALUES 
(1, 1), (1, 2), 
(2, 1), (2, 3), 
(3, 2), (3, 3), (3, 4),
(4, 4), (4, 5),
(5, 1), (5, 3),
(6, 2), (6, 4), (6, 5),
(7, 1), (7, 4),
(8, 3), (8, 5),
(9, 2), (9, 4);

-- 9. Insertar Reservas de Prueba
-- status: 'pendiente', 'completado', 'cancelado'
INSERT INTO bookings (id_cliente, id_profesional, datetime_start, datetime_end, total_price, status) VALUES 
(1, 1, '2026-05-28 10:00:00', '2026-05-28 11:30:00', 45000.0, 'pendiente'),
(2, 2, '2026-05-29 14:00:00', '2026-05-29 15:00:00', 35000.0, 'pendiente'),
(3, 3, '2026-05-30 09:00:00', '2026-05-30 10:30:00', 120000.0, 'completado'),
(4, 4, '2026-06-01 16:00:00', '2026-06-01 17:30:00', 80000.0, 'pendiente'),
(5, 5, '2026-06-02 11:00:00', '2026-06-02 11:45:00', 40000.0, 'completado');

-- 10. Asociar Servicios a Reservas (booking_service)
INSERT INTO booking_service (id_booking, id_service) VALUES 
(1, 1), 
(2, 3), 
(3, 5),
(4, 7),
(5, 8);

-- 11. Insertar Reseñas
INSERT INTO reviews (id_booking, rating, comment, created_date) VALUES 
(3, 5, '¡Sofía es espectacular! El maquillaje me duró toda la noche intacto. ¡100% recomendada!', '2026-05-20'),
(5, 5, 'Excelente servicio de barbería. Andrés fue muy puntual y el corte quedó perfecto.', '2026-05-22');
