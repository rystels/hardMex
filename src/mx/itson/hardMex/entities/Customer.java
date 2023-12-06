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
public class Customer {
    
    private int id;
    private String name;
    private String lastName;
    private String email;

    public static List<Customer> getAll(String filtro) {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_clientes WHERE name LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resulSet = statement.executeQuery();

            while (resulSet.next()) {
                Customer c = new Customer();
                c.setId(resulSet.getInt(1));
                c.setName(resulSet.getString(2));
                c.setLastName(resulSet.getString(3));
                c.setEmail(resulSet.getString(4));
                customers.add(c);
            }
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return customers;
    }
    
    public boolean save(String name, int lastName, String email) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT  INTO LV_clientes (nombre, apellido, email) VALUES(?,?,?)";
            PreparedStatement statement= conexion.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, lastName);
            statement.setString(3, email);
            statement.execute();
            
            result = statement.getUpdateCount()==1;
            
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error "+ex.getMessage());
        }
        return result;
    }
    
    public boolean update(int id, String name, String lastName, String email) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_clientes SET nombre=?, apellido=?, email=? WHERE id=?";
            PreparedStatement statement= conexion.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setInt(4, id);
            statement.execute();
            
            result = statement.getUpdateCount()==1;
            
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error "+ex.getMessage());
        }
        return result;
    }
    
    public boolean delete(int id) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "DELETE FROM LV_clientes WHERE id= ?";
            PreparedStatement statement= conexion.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
            
            result = statement.getUpdateCount()==1;
            
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error "+ex.getMessage());
        }
        return result;
    }
    
    public static Customer getById(int id) {
        Customer customers = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_peliculas WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                customers = new Customer();
                customers.setId(resultSet.getInt("id"));
                customers.setName(resultSet.getString("nombre"));
                customers.setLastName(resultSet.getString("apellido"));
                customers.setEmail(resultSet.getString("email"));
            }

            conexion.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return customers;
    }
    
    public Customer() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
