import java.sql.*;

/**
 * DatabaseConnection class to manage database connections
 * Handles connection setup and database initialization
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:employee_database.db";
    private static Connection connection = null;

    /**
     * Get database connection
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connected to SQLite database successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to database!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Initialize database and create tables
     */
    public static void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS employees (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                department TEXT NOT NULL,
                salary REAL NOT NULL,
                hire_date TEXT NOT NULL
            )
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Employee table created successfully!");
            
            // Insert sample data if table is empty
            insertSampleData();
            
        } catch (SQLException e) {
            System.err.println("Error creating table!");
            e.printStackTrace();
        }
    }

    /**
     * Insert sample data for testing
     */
    private static void insertSampleData() {
        String checkDataSQL = "SELECT COUNT(*) FROM employees";
        String insertSQL = """
            INSERT INTO employees (first_name, last_name, email, department, salary, hire_date) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = getConnection();
             Statement checkStmt = conn.createStatement();
             ResultSet rs = checkStmt.executeQuery(checkDataSQL)) {
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert sample employees
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    // Employee 1
                    pstmt.setString(1, "John");
                    pstmt.setString(2, "Doe");
                    pstmt.setString(3, "john.doe@company.com");
                    pstmt.setString(4, "Engineering");
                    pstmt.setDouble(5, 75000.00);
                    pstmt.setString(6, "2023-01-15");
                    pstmt.executeUpdate();

                    // Employee 2
                    pstmt.setString(1, "Jane");
                    pstmt.setString(2, "Smith");
                    pstmt.setString(3, "jane.smith@company.com");
                    pstmt.setString(4, "Marketing");
                    pstmt.setDouble(5, 65000.00);
                    pstmt.setString(6, "2023-02-20");
                    pstmt.executeUpdate();

                    // Employee 3
                    pstmt.setString(1, "Mike");
                    pstmt.setString(2, "Johnson");
                    pstmt.setString(3, "mike.johnson@company.com");
                    pstmt.setString(4, "HR");
                    pstmt.setDouble(5, 60000.00);
                    pstmt.setString(6, "2023-03-10");
                    pstmt.executeUpdate();

                    System.out.println("Sample data inserted successfully!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting sample data!");
            e.printStackTrace();
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
}
