package org.example.residentialvehiclemanager.database;

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Gestor centralizado de conexiones a SQLite
 * Implementa patrón Singleton para garantizar una única instancia
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:ResidentialVehicleManager.db";

    private DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DB_URL);
            this.connection.setAutoCommit(true);
            System.out.println("✓ Conexión establecida con SQLite");
            initializeDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("✗ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia única del DatabaseManager
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    /**
     * Inicializa la base de datos ejecutando el script SQL
     */
    private void initializeDatabase() {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "torres", null);

            if (!tables.next()) {
                System.out.println("Inicializando base de datos...");
                executeSQLScript("/init_database.sql");
                System.out.println("✓ Base de datos inicializada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar/inicializar la BD: " + e.getMessage());
        }
    }

    /**
     * Ejecuta un script SQL desde recursos
     */
    private void executeSQLScript(String resourcePath) throws SQLException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream(resourcePath),
                        StandardCharsets.UTF_8))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("--")) {
                    sql.append(line).append(" ");

                    if (line.trim().endsWith(";")) {
                        try (Statement stmt = connection.createStatement()) {
                            stmt.execute(sql.toString());
                            sql = new StringBuilder();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error al ejecutar script SQL: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una conexión activa a la base de datos
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Ejecuta una consulta SELECT
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Ejecuta una actualización (INSERT, UPDATE, DELETE)
     */
    public int executeUpdate(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    /**
     * Ejecuta una actualización con PreparedStatement (seguro contra SQL injection)
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        }
    }

    /**
     * Ejecuta una consulta con PreparedStatement
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        return pstmt.executeQuery();
    }

    /**
     * Inicia una transacción
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Confirma una transacción
     */
    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Revierte una transacción
     */
    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    /**
     * Cierra la conexión
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
