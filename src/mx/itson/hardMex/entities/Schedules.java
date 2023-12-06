/*
 * 
 */
package mx.itson.hardMex.entities;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import mx.itson.hardMex.persistence.MySQLConnection;
/**
 *
 */
public class Schedules {
    
    private int id;
    private Hall hall;
    private String startTime;
    private String endTime;
    private Date date;

    public static List<Schedules> getAll(String filtro) throws ParseException {
        List<Schedules> schedulesList = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_horarios WHERE hora_inicio LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Schedules schedules = new Schedules();
                schedules.setId(resultSet.getInt("id"));
                
                Hall hall = new Hall();
                hall.setId(resultSet.getInt("sala_id"));
                // Puedes cargar el resto de los atributos del salón si es necesario
                schedules.setHall(hall);

                schedules.setStartTime(resultSet.getString("hora_inicio"));
                schedules.setEndTime(resultSet.getString("hora_final"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(resultSet.getString("fecha"));
                schedules.setDate(date);

                schedulesList.add(schedules);
            }
        } catch (SQLException | ParseException ex) {
            System.err.print("Error: " + ex);
        }
        return schedulesList;
    }

    public boolean save(Schedules schedules) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT INTO LV_horarios (sala_id, hora_inicio, hora_final, fecha) VALUES(?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, schedules.getHall().getId());
            statement.setString(2, schedules.getStartTime());
            statement.setString(3, schedules.getEndTime());
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(4, dateFormat.format(schedules.getDate()));

            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public boolean update(Schedules schedules) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_horarios SET sala_id=?, hora_inicio=?, hora_final=?, fecha=? WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, schedules.getHall().getId());
            statement.setString(2, schedules.getStartTime());
            statement.setString(3, schedules.getEndTime());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(4, dateFormat.format(schedules.getDate()));

            statement.setInt(5, schedules.getId());
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
            String query = "DELETE FROM LV_horarios WHERE id=?";
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

    public static Schedules getById(int id) {
        Schedules schedules = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_horarios WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                schedules = new Schedules();
                schedules.setId(resultSet.getInt("id"));

                Hall hall = new Hall();
                hall.setId(resultSet.getInt("sala_id"));
                // cargar los otros atributos de salas de ser necesario
                schedules.setHall(hall);

                schedules.setStartTime(resultSet.getString("hora_inicio"));
                schedules.setEndTime(resultSet.getString("hora_final"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(resultSet.getString("fecha"));
                schedules.setDate(date);
            }

            conexion.close();
        } catch (SQLException | ParseException ex) {
            System.err.print("Error: " + ex);
        }
        return schedules;
    }
    
    public Schedules() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
