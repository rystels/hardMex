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
import mx.itson.hardMmex.persistence.MySQLConnection;

/**
 *
 */
public class Movie {
    
    private int id;
    private String title;
    private int duration;
    private String synopsis;
    private String genre;
    
    public static List<Movie> getAll(String filtro) {
        List<Movie> movies = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_peliculas WHERE titulo LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resulSet = statement.executeQuery();

            while (resulSet.next()) {
                Movie m = new Movie();
                m.setId(resulSet.getInt(1));
                m.setTitle(resulSet.getString(2));
                m.setDuration(resulSet.getInt(3));
                m.setSynopsis(resulSet.getString(4));
                m.setGenre(resulSet.getString(5));
                movies.add(m);
            }
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return movies;
    }
    
    public boolean save(String title, int duration, String synopsis, String genre) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT  INTO LV_peliculas (titulo, duracion, sinopsis, genero) VALUES(?,?,?,?)";
            PreparedStatement statement= conexion.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, duration);
            statement.setString(3, synopsis);
            statement.setString(4, genre);
            statement.execute();
            
            result = statement.getUpdateCount()==1;
            
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error "+ex.getMessage());
        }
        return result;
    }
    
    public boolean update(int id, String title, int duration, String synopsis, String genre) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_peliculas SET titulo=?, duracion=?, sinopsis=?, genero=? WHERE id=?";
            PreparedStatement statement= conexion.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, duration);
            statement.setString(3, synopsis);
            statement.setString(4, genre);
            statement.setInt(5, id);
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
            String query = "DELETE FROM LV_peliculas WHERE id= ?";
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
    
    public static Movie getById(int id) {
        Movie movies = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_peliculas WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movies = new Movie();
                movies.setId(resultSet.getInt("id"));
                movies.setTitle(resultSet.getString("titulo"));
                movies.setDuration(resultSet.getInt("duracion"));
                movies.setSynopsis(resultSet.getString("sinopsis"));
                movies.setGenre(resultSet.getString("genero"));
            }

            conexion.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return movies;
    }
    
    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
}
