-- =====================================================
-- DATOS INICIALES PARA LAS BASES DE DATOS
-- =====================================================

-- =====================================================
-- gateway_db - Usuarios iniciales de prueba
-- =====================================================

-- Insert test users (password: password123)
-- BCrypt hash: $2a$10$slYQmyNdGzin7olVN3p5Be7DkH0gvRHcvWcgIvQvFXQK5vltoKKFm

INSERT INTO users (email, password, full_name, role, created_at) VALUES
('admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0gvRHcvWcgIvQvFXQK5vltoKKFm', 'Admin User', 'ADMIN', NOW());

INSERT INTO users (email, password, full_name, role, created_at) VALUES
('staff@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0gvRHcvWcgIvQvFXQK5vltoKKFm', 'Staff Member', 'STAFF', NOW());

INSERT INTO users (email, password, full_name, role, created_at) VALUES
('guest@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0gvRHcvWcgIvQvFXQK5vltoKKFm', 'Guest User', 'GUEST', NOW());

-- =====================================================
-- service_a_db - Categorías de servicio iniciales
-- =====================================================

INSERT INTO service_categories (name, description, icon) VALUES
('Room Service', 'Services related to room needs', '🛏️');

INSERT INTO service_categories (name, description, icon) VALUES
('Restaurant & Dining', 'Dining and food-related requests', '🍽️');

INSERT INTO service_categories (name, description, icon) VALUES
('Transportation', 'Transport and shuttle services', '🚕');

INSERT INTO service_categories (name, description, icon) VALUES
('Activities', 'Local activities and entertainment', '🎭');

INSERT INTO service_categories (name, description, icon) VALUES
('Housekeeping', 'Cleaning and maintenance services', '🧹');

INSERT INTO service_categories (name, description, icon) VALUES
('Front Desk', 'General front desk assistance', '🏨');

-- Sample requests (user_id should match actual users)
INSERT INTO concierge_requests (user_id, category_id, title, description, status, priority, created_at, updated_at) VALUES
(1, 1, 'Extra towels needed', 'Please bring 4 extra towels to room 301', 'IN_PROGRESS', 'HIGH', NOW(), NOW());

INSERT INTO concierge_requests (user_id, category_id, title, description, status, priority, created_at, updated_at) VALUES
(2, 2, 'Restaurant recommendation', 'Looking for Italian restaurant nearby', 'PENDING', 'MEDIUM', NOW(), NOW());

INSERT INTO concierge_requests (user_id, category_id, title, description, status, priority, created_at, updated_at) VALUES
(3, 3, 'Airport transfer', 'Need pickup to airport tomorrow 6 AM', 'PENDING', 'URGENT', NOW(), NOW());

-- =====================================================
-- service_b_db - Base de conocimiento para IA
-- =====================================================

INSERT INTO knowledge_base (category, question, answer, tags) VALUES
('restaurants', 'What are the best Italian restaurants?', 'We recommend Trattoria Mario and Da Luigi, both within walking distance', 'dining,italian,restaurant');

INSERT INTO knowledge_base (category, question, answer, tags) VALUES
('activities', 'What activities are available nearby?', 'Museum of Modern Art, Central Park, and local theater shows', 'entertainment,activities,tour');

INSERT INTO knowledge_base (category, question, answer, tags) VALUES
('transportation', 'How to get to the airport?', 'We offer shuttle service every hour. Request at front desk.', 'transport,airport,shuttle');

INSERT INTO knowledge_base (category, question, answer, tags) VALUES
('hotel', 'When is breakfast served?', 'Breakfast is served from 6:30 AM to 10:00 AM in the dining hall', 'breakfast,dining,hours');

INSERT INTO knowledge_base (category, question, answer, tags) VALUES
('hotel', 'What is the WiFi password?', 'WiFi: HotelGuest, Password: Welcome2024', 'wifi,internet,password');

-- =====================================================
-- NOTAS IMPORTANTES
-- =====================================================

-- 1. Las contraseñas están hasheadas con BCrypt
--    email: admin@example.com, staff@example.com, guest@example.com
--    password: password123
--
-- 2. Los user_id deben coincidir con los registros en gateway_db.users
--
-- 3. Para generar nuevos hashes de contraseña:
--    - Usar BCrypt con salt rounds = 10
--    - Ejemplo en Java:
--      new BCryptPasswordEncoder().encode("password123")
--
-- 4. Para ejecutar este script:
--    psql -U postgres -d gateway_db -f INIT_DATA.sql
--    psql -U postgres -d service_a_db -f INIT_DATA.sql
--    psql -U postgres -d service_b_db -f INIT_DATA.sql
--
-- 5. O en Docker Compose:
--    docker exec postgres-gateway psql -U postgres gateway_db -f /docker-entrypoint-initdb.d/init.sql
