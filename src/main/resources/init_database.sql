-- ==========================================
-- SISTEMA DE GESTIÓN DE VEHÍCULOS RESIDENCIAL
-- Base de Datos SQLite - Esquema Inicial
-- ==========================================

-- Tabla de Torres
CREATE TABLE IF NOT EXISTS torres (
                                      id_torre INTEGER PRIMARY KEY AUTOINCREMENT,
                                      nombre_torre TEXT NOT NULL UNIQUE,
                                      fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Inmuebles (Apartamentos)
CREATE TABLE IF NOT EXISTS inmuebles (
                                         id_inmueble INTEGER PRIMARY KEY AUTOINCREMENT,
                                         numero_apartamento TEXT NOT NULL,
                                         id_torre INTEGER NOT NULL,
                                         fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (id_torre) REFERENCES torres(id_torre) ON DELETE CASCADE,
    UNIQUE(numero_apartamento, id_torre)
    );

-- Tabla de Residentes
CREATE TABLE IF NOT EXISTS residentes (
                                          id_residente INTEGER PRIMARY KEY AUTOINCREMENT,
                                          nombres_completos TEXT NOT NULL,
                                          telefono TEXT,
                                          correo_electronico TEXT,
                                          tipo_vinculo TEXT NOT NULL CHECK(tipo_vinculo IN ('Propietario', 'Arrendatario', 'Familiar', 'Otro')),
    id_inmueble INTEGER NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_inmueble) REFERENCES inmuebles(id_inmueble) ON DELETE CASCADE
    );

-- Tabla de Vehículos
CREATE TABLE IF NOT EXISTS vehiculos (
                                         placa TEXT PRIMARY KEY,
                                         marca TEXT NOT NULL,
                                         modelo TEXT NOT NULL,
                                         color TEXT,
                                         id_inmueble_destino INTEGER NOT NULL,
                                         fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (id_inmueble_destino) REFERENCES inmuebles(id_inmueble) ON DELETE CASCADE
    );

-- Tabla de Roles
CREATE TABLE IF NOT EXISTS roles (
                                     id_rol INTEGER PRIMARY KEY AUTOINCREMENT,
                                     nombre_rol TEXT NOT NULL UNIQUE,
                                     descripcion TEXT,
                                     fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Usuarios (Porteros/Administradores)
CREATE TABLE IF NOT EXISTS usuarios (
                                        id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                                        nombre_usuario TEXT NOT NULL UNIQUE,
                                        password TEXT NOT NULL,
                                        id_rol INTEGER NOT NULL,
                                        activo BOOLEAN DEFAULT 1,
                                        fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE RESTRICT
    );

-- Tabla de Registros de Acceso
CREATE TABLE IF NOT EXISTS registros_acceso (
                                                id_registro INTEGER PRIMARY KEY AUTOINCREMENT,
                                                fecha_hora_ingreso DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                placa_vehiculo TEXT NOT NULL,
                                                id_usuario_porteria INTEGER NOT NULL,
                                                observaciones TEXT,
                                                FOREIGN KEY (placa_vehiculo) REFERENCES vehiculos(placa) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario_porteria) REFERENCES usuarios(id_usuario) ON DELETE RESTRICT
    );

-- ==========================================
-- ÍNDICES PARA OPTIMIZACIÓN DE CONSULTAS
-- ==========================================

CREATE INDEX IF NOT EXISTS idx_inmuebles_torre ON inmuebles(id_torre);
CREATE INDEX IF NOT EXISTS idx_residentes_inmueble ON residentes(id_inmueble);
CREATE INDEX IF NOT EXISTS idx_vehiculos_inmueble ON vehiculos(id_inmueble_destino);
CREATE INDEX IF NOT EXISTS idx_usuarios_rol ON usuarios(id_rol);
CREATE INDEX IF NOT EXISTS idx_registros_placa ON registros_acceso(placa_vehiculo);
CREATE INDEX IF NOT EXISTS idx_registros_usuario ON registros_acceso(id_usuario_porteria);
CREATE INDEX IF NOT EXISTS idx_registros_fecha ON registros_acceso(fecha_hora_ingreso);

-- ==========================================
-- DATOS INICIALES
-- ==========================================

-- Insertar roles predeterminados
INSERT OR IGNORE INTO roles (id_rol, nombre_rol, descripcion) VALUES
(1, 'Administrador', 'Acceso total al sistema'),
(2, 'Portero', 'Registro de accesos de vehículos'),
(3, 'Residente', 'Consulta de información personal');

-- Insertar usuario administrador por defecto
INSERT OR IGNORE INTO usuarios (id_usuario, nombre_usuario, password, id_rol, activo) VALUES
(1, 'admin', 'admin123', 1, 1);

-- Insertar usuario portero de ejemplo
INSERT OR IGNORE INTO usuarios (id_usuario, nombre_usuario, password, id_rol, activo) VALUES
(2, 'portero1', 'portero123', 2, 1);