import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDAO (Data Access Object) class
 * Handles all database operations for Employee entities
 * Implements CRUD operations using JDBC
 */
public class EmployeeDAO {

    /**
     * Add a new employee to the database
     * @param employee Employee object to add
     * @return true if successful, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        String sql = """
            INSERT INTO employees (first_name, last_name, email, department, salary, hire_date) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setString(6, employee.getHireDate());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Employee added successfully with ID: " + employee.getId());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieve all employees from the database
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
                employees.add(employee);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Retrieve an employee by ID
     * @param id Employee ID
     * @return Employee object or null if not found
     */
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("hire_date")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employee by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update an existing employee
     * @param employee Employee object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateEmployee(Employee employee) {
        String sql = """
            UPDATE employees 
            SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ?, hire_date = ? 
            WHERE id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setString(6, employee.getHireDate());
            pstmt.setInt(7, employee.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully!");
                return true;
            } else {
                System.out.println("No employee found with ID: " + employee.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete an employee by ID
     * @param id Employee ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
                return true;
            } else {
                System.out.println("No employee found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Search employees by department
     * @param department Department name
     * @return List of employees in the specified department
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ? ORDER BY last_name, first_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
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
                    employees.add(employee);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching employees by department: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Get total number of employees
     * @return Total count of employees
     */
    public int getTotalEmployeeCount() {
        String sql = "SELECT COUNT(*) FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error getting employee count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
