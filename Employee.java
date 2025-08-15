/**
 * Employee class representing an employee entity
 * Contains employee information and basic operations
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private double salary;
    private String hireDate;

    // Default constructor
    public Employee() {}

    // Constructor with all fields
    public Employee(int id, String firstName, String lastName, String email, 
                   String department, double salary, String hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Constructor without ID (for new employees)
    public Employee(String firstName, String lastName, String email, 
                   String department, double salary, String hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    // toString method for display
    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s %s', email='%s', department='%s', salary=%.2f, hireDate='%s'}",
                id, firstName, lastName, email, department, salary, hireDate);
    }

    // Method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
