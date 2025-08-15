# Employee Database Management System

A comprehensive Java JDBC application demonstrating full CRUD operations for employee management with SQLite database.

## Features

### Core JDBC Components Used
- **Connection**: Database connectivity management
- **PreparedStatement**: Secure SQL execution with parameter binding
- **ResultSet**: Data retrieval and iteration
- **Statement**: DDL operations and simple queries

### CRUD Operations
- **Create**: Add new employees with auto-generated IDs
- **Read**: View all employees, search by ID, filter by department
- **Update**: Modify existing employee information
- **Delete**: Remove employees with confirmation

### Additional Features
- Database initialization with sample data
- Input validation and error handling
- Statistics and reporting
- Professional console interface
- Connection pooling and resource management

## Project Structure

```
Employee Database App/
├── Employee.java              # Employee entity class
├── DatabaseConnection.java    # Database connection management
├── EmployeeDAO.java          # Data Access Object with CRUD operations
├── EmployeeDatabaseApp.java  # Main application with console interface
└── employee_database.db     # SQLite database (auto-created)
```

## Database Schema

```sql
CREATE TABLE employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    department TEXT NOT NULL,
    salary REAL NOT NULL,
    hire_date TEXT NOT NULL
);
```

## Setup Instructions

### Prerequisites
- Java 8 or higher
- SQLite JDBC driver (sqlite-jdbc-3.x.x.jar)

### Installation
1. Download SQLite JDBC driver:
   ```bash
   # Download from: https://github.com/xerial/sqlite-jdbc/releases
   # Or use Maven/Gradle dependency
   ```

2. Compile the application:
   ```bash
   javac -cp ".:sqlite-jdbc-3.x.x.jar" *.java
   ```

3. Run the application:
   ```bash
   java -cp ".:sqlite-jdbc-3.x.x.jar" EmployeeDatabaseApp
   ```

## Usage Guide

### Menu Options
1. **Add New Employee**: Enter employee details (name, email, department, salary, hire date)
2. **View All Employees**: Display formatted table of all employees
3. **View Employee by ID**: Search and display specific employee
4. **Update Employee**: Modify existing employee information
5. **Delete Employee**: Remove employee with confirmation
6. **Search by Department**: Filter employees by department
7. **Show Statistics**: Display database analytics
8. **Exit**: Close application and database connection

### Sample Data
The application includes sample employees:
- John Doe (Engineering, $75,000)
- Jane Smith (Marketing, $65,000)
- Mike Johnson (HR, $60,000)

## JDBC Implementation Details

### Connection Management
```java
// Singleton pattern for database connection
Connection conn = DatabaseConnection.getConnection();
```

### PreparedStatement Usage
```java
String sql = "INSERT INTO employees (first_name, last_name, email, department, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
```

### ResultSet Processing
```java
while (rs.next()) {
    Employee employee = new Employee(
        rs.getInt("id"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email"),
        rs.getString("department"),
        rs.getDouble("salary"),
        rs.getString("hire_date")
    );
}
```

## Key Learning Objectives

### JDBC Concepts Demonstrated
- Database connection establishment
- SQL injection prevention with PreparedStatement
- Transaction handling and resource management
- Auto-generated key retrieval
- Batch operations and error handling

### Design Patterns Used
- **DAO Pattern**: Separates data access logic
- **Singleton Pattern**: Database connection management
- **MVC Pattern**: Separation of concerns

## Error Handling

- SQL exception handling with detailed error messages
- Input validation for all user inputs
- Resource cleanup with try-with-resources
- Connection state verification

## Extensibility

The application can be extended with:
- GUI interface using Swing/JavaFX
- Advanced search and filtering
- Employee photo management
- Salary history tracking
- Department management
- Export/Import functionality
- User authentication and authorization

## Dependencies

Add to your project:
```xml
<!-- Maven -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.42.0.0</version>
</dependency>
```

```gradle
// Gradle
implementation 'org.xerial:sqlite-jdbc:3.42.0.0'
```

## Testing

Test all CRUD operations:
1. Add multiple employees
2. View and search functionality
3. Update employee information
4. Delete operations with confirmation
5. Error scenarios (invalid input, duplicate emails)

## Performance Considerations

- Connection reuse and proper closing
- PreparedStatement for repeated operations
- Efficient SQL queries with proper indexing
- Resource management with try-with-resources

---

**Author**: Java JDBC Learning Project  
**Version**: 1.0  
**Last Updated**: August 2025

This project demonstrates professional Java JDBC development practices with comprehensive CRUD operations, proper error handling, and clean architecture design.
