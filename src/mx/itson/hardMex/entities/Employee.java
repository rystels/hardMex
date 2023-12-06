/*
 * 
 */
package mx.itson.hardMex.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.itson.hardMex.persistence.MySQLConnection;

/**
 *
 */
public class Employee {
    
    private int id;
    private String name;
    private String phone;
    private String position;
    private double salary;

    public static List<Employee> getAll(String filtro) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_empleados WHERE name LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee e = new Employee();
                e.setId(resultSet.getInt("1"));
                e.setName(resultSet.getString("2"));
                e.setPhone(resultSet.getString("3"));
                e.setPosition(resultSet.getString("4"));
                e.setSalary(resultSet.getDouble("5"));
                employees.add(e);
            }
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return employees;
    }

    public boolean save(Employee employee) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT INTO LV_empleados (nombre, telefono, posicion, salario) VALUES(?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPhone());
            statement.setString(3, employee.getPosition());
            statement.setDouble(4, employee.getSalary());
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public boolean update(Employee employee) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_empleados SET nombre=?, telefono=?, posicion=?, salario=? WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPhone());
            statement.setString(3, employee.getPosition());
            statement.setDouble(4, employee.getSalary());
            statement.setInt(5, employee.getId());
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "DELETE FROM LV_empleados WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public static Employee getById(int id) {
        Employee employee = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_empleados WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("nombre"));
                employee.setPhone(resultSet.getString("apellido"));
                employee.setPosition(resultSet.getString("posicion"));
                employee.setSalary(resultSet.getDouble("salario"));
            }

            conexion.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return employee;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
}
