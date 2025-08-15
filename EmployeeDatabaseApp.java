import java.util.List;
import java.util.Scanner;

/**
 * EmployeeDatabaseApp - Main application class
 * Console-based Employee Management System using JDBC
 * Demonstrates CRUD operations with PreparedStatement and ResultSet
 */
public class EmployeeDatabaseApp {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Employee Database Management System ===");
        System.out.println("Initializing database...");
        
        // Initialize database and create tables
        DatabaseConnection.initializeDatabase();
        
        // Main application loop
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    viewEmployeeById();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    searchByDepartment();
                    break;
                case 7:
                    showStatistics();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        // Close database connection
        DatabaseConnection.closeConnection();
        scanner.close();
        System.out.println("Thank you for using Employee Database Management System!");
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. View Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Search by Department");
        System.out.println("7. Show Statistics");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
    }

    /**
     * Add a new employee
     */
    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Department: ");
        String department = scanner.nextLine().trim();
        
        double salary = getDoubleInput("Salary: $");
        
        System.out.print("Hire Date (YYYY-MM-DD): ");
        String hireDate = scanner.nextLine().trim();
        
        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
            department.isEmpty() || hireDate.isEmpty()) {
            System.out.println("Error: All fields are required!");
            return;
        }
        
        Employee employee = new Employee(firstName, lastName, email, department, salary, hireDate);
        
        if (employeeDAO.addEmployee(employee)) {
            System.out.println("Employee added successfully!");
            System.out.println("Generated ID: " + employee.getId());
        } else {
            System.out.println("Failed to add employee!");
        }
    }

    /**
     * View all employees
     */
    private static void viewAllEmployees() {
        System.out.println("\n--- All Employees ---");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }
        
        System.out.println(String.format("%-4s %-15s %-15s %-25s %-15s %-10s %-12s", 
                          "ID", "First Name", "Last Name", "Email", "Department", "Salary", "Hire Date"));
        System.out.println("-".repeat(100));
        
        for (Employee emp : employees) {
            System.out.println(String.format("%-4d %-15s %-15s %-25s %-15s $%-9.2f %-12s",
                              emp.getId(), emp.getFirstName(), emp.getLastName(), 
                              emp.getEmail(), emp.getDepartment(), emp.getSalary(), emp.getHireDate()));
        }
        
        System.out.println("\nTotal employees: " + employees.size());
    }

    /**
     * View employee by ID
     */
    private static void viewEmployeeById() {
        System.out.println("\n--- View Employee by ID ---");
        int id = getIntInput("Enter Employee ID: ");
        
        Employee employee = employeeDAO.getEmployeeById(id);
        if (employee != null) {
            System.out.println("\nEmployee Details:");
            System.out.println("-".repeat(40));
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getFullName());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Salary: $" + String.format("%.2f", employee.getSalary()));
            System.out.println("Hire Date: " + employee.getHireDate());
        } else {
            System.out.println("Employee not found with ID: " + id);
        }
    }

    /**
     * Update an existing employee
     */
    private static void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        int id = getIntInput("Enter Employee ID to update: ");
        
        Employee employee = employeeDAO.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }
        
        System.out.println("Current details: " + employee);
        System.out.println("Enter new details (press Enter to keep current value):");
        
        System.out.print("First Name [" + employee.getFirstName() + "]: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) employee.setFirstName(firstName);
        
        System.out.print("Last Name [" + employee.getLastName() + "]: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) employee.setLastName(lastName);
        
        System.out.print("Email [" + employee.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) employee.setEmail(email);
        
        System.out.print("Department [" + employee.getDepartment() + "]: ");
        String department = scanner.nextLine().trim();
        if (!department.isEmpty()) employee.setDepartment(department);
        
        System.out.print("Salary [" + employee.getSalary() + "]: $");
        String salaryStr = scanner.nextLine().trim();
        if (!salaryStr.isEmpty()) {
            try {
                employee.setSalary(Double.parseDouble(salaryStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format!");
                return;
            }
        }
        
        System.out.print("Hire Date [" + employee.getHireDate() + "]: ");
        String hireDate = scanner.nextLine().trim();
        if (!hireDate.isEmpty()) employee.setHireDate(hireDate);
        
        if (employeeDAO.updateEmployee(employee)) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Failed to update employee!");
        }
    }

    /**
     * Delete an employee
     */
    private static void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        int id = getIntInput("Enter Employee ID to delete: ");
        
        Employee employee = employeeDAO.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }
        
        System.out.println("Employee to delete: " + employee);
        System.out.print("Are you sure you want to delete this employee? (y/N): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            if (employeeDAO.deleteEmployee(id)) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Failed to delete employee!");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    /**
     * Search employees by department
     */
    private static void searchByDepartment() {
        System.out.println("\n--- Search by Department ---");
        System.out.print("Enter Department name: ");
        String department = scanner.nextLine().trim();
        
        if (department.isEmpty()) {
            System.out.println("Department name cannot be empty!");
            return;
        }
        
        List<Employee> employees = employeeDAO.getEmployeesByDepartment(department);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
            return;
        }
        
        System.out.println("\nEmployees in " + department + " department:");
        System.out.println(String.format("%-4s %-20s %-25s %-10s %-12s", 
                          "ID", "Name", "Email", "Salary", "Hire Date"));
        System.out.println("-".repeat(75));
        
        for (Employee emp : employees) {
            System.out.println(String.format("%-4d %-20s %-25s $%-9.2f %-12s",
                              emp.getId(), emp.getFullName(), emp.getEmail(), 
                              emp.getSalary(), emp.getHireDate()));
        }
        
        System.out.println("\nTotal employees in " + department + ": " + employees.size());
    }

    /**
     * Show database statistics
     */
    private static void showStatistics() {
        System.out.println("\n--- Database Statistics ---");
        int totalEmployees = employeeDAO.getTotalEmployeeCount();
        System.out.println("Total Employees: " + totalEmployees);
        
        if (totalEmployees > 0) {
            List<Employee> allEmployees = employeeDAO.getAllEmployees();
            
            // Calculate average salary
            double totalSalary = allEmployees.stream().mapToDouble(Employee::getSalary).sum();
            double avgSalary = totalSalary / totalEmployees;
            
            System.out.println("Average Salary: $" + String.format("%.2f", avgSalary));
            
            // Find highest and lowest salary
            Employee highestPaid = allEmployees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElse(null);
            Employee lowestPaid = allEmployees.stream()
                .min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElse(null);
            
            if (highestPaid != null) {
                System.out.println("Highest Paid: " + highestPaid.getFullName() + 
                                 " ($" + String.format("%.2f", highestPaid.getSalary()) + ")");
            }
            if (lowestPaid != null) {
                System.out.println("Lowest Paid: " + lowestPaid.getFullName() + 
                                 " ($" + String.format("%.2f", lowestPaid.getSalary()) + ")");
            }
        }
    }

    /**
     * Get integer input with validation
     */
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    /**
     * Get double input with validation
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Salary cannot be negative!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}
