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
public class Hall {
    
    private int id;
    private String name;
    private String capacity;

     public static List<Hall> getAll(String filtro) {
        List<Hall> halls = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_salas WHERE name LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Hall h = new Hall();
                h.setId(resultSet.getInt("1"));
                h.setName(resultSet.getString("2"));
                h.setCapacity(resultSet.getString("3"));
                halls.add(h);
            }
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return halls;
    }

    public boolean save(Hall hall) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT INTO LV_salas (nombre, capacidad) VALUES(?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, hall.getName());
            statement.setString(2, hall.getCapacity());
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public boolean update(Hall hall) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_salas SET nombre=?, capacidad=? WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, hall.getName());
            statement.setString(2, hall.getCapacity());
            statement.setInt(3, hall.getId());
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
            String query = "DELETE FROM LV_salas WHERE id=?";
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

    public static Hall getById(int id) {
        Hall hall = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_salas WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                hall = new Hall();
                hall.setId(resultSet.getInt("id"));
                hall.setName(resultSet.getString("nombre"));
                hall.setCapacity(resultSet.getString("capacidad"));
            }

            conexion.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return hall;
    }
    
    public Hall() {
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    
}
