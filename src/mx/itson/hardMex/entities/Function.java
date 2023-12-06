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
public class Function {
    
    private int id;
    private Movie movie;
    private Employee employee;
    private Hall hall;
    private Schedules schedules;
    private Customer customer;

    public static List<Function> getAll(String filtro) {
        List<Function> functions = new ArrayList<>();
        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM LV_funciones WHERE pelicula_titulo LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Function function = new Function();
                function.setId(resultSet.getInt("id"));

                Movie movie = new Movie();
                movie.setId(resultSet.getInt("pelicula_id"));
                // cargar los otros atributos de peliculas de ser necesario
                function.setMovie(movie);

                Employee employee = new Employee();
                employee.setId(resultSet.getInt("empleado_id"));
                // cargar los otros atributos de empleados de ser necesario
                function.setEmployee(employee);

                Hall hall = new Hall();
                hall.setId(resultSet.getInt("sala_id"));
                // cargar los otros atributos de salas de ser necesario
                function.setHall(hall);

                Schedules schedules = new Schedules();
                schedules.setId(resultSet.getInt("horario_id"));
                // cargar los otros atributos de horarios de ser necesario
                function.setSchedules(schedules);

                Customer customer = new Customer();
                customer.setId(resultSet.getInt("cliente_id"));
                // cargar los otros atributos de clientes de ser necesario
                function.setCustomer(customer);

                functions.add(function);
            }
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return functions;
    }

    public boolean save(Function function) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT INTO LV_funciones (pelicula_id, empleado_id, sala_id, horario_id, cliente_id) VALUES(?,?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, function.getMovie().getId());
            statement.setInt(2, function.getEmployee().getId());
            statement.setInt(3, function.getHall().getId());
            statement.setInt(4, function.getSchedules().getId());
            statement.setInt(5, function.getCustomer().getId());
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();
        } catch (Exception ex) {
            System.err.println("Error " + ex.getMessage());
        }
        return result;
    }

    public boolean update(Function function) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "UPDATE LV_funciones SET pelicula_id=?, empleado_id=?, sala_id=?, horario_id=?, cliente_id=? WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, function.getMovie().getId());
            statement.setInt(2, function.getEmployee().getId());
            statement.setInt(3, function.getHall().getId());
            statement.setInt(4, function.getSchedules().getId());
            statement.setInt(5, function.getCustomer().getId());
            statement.setInt(6, function.getId());
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
            String query = "DELETE FROM LV_funciones WHERE id=?";
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

    public static Function getById(int id) {
        Function function = null;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "SELECT * FROM LV_funciones WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                function = new Function();
                function.setId(resultSet.getInt("id"));

                Movie movie = new Movie();
                movie.setId(resultSet.getInt("pelicula_id"));
                // cargar los otros atributos de peliculas de ser necesario
                function.setMovie(movie);

                Employee employee = new Employee();
                employee.setId(resultSet.getInt("empleado_id"));
                // cargar los otros atributos de empleados de ser necesario
                function.setEmployee(employee);

                Hall hall = new Hall();
                hall.setId(resultSet.getInt("sala_id"));
                // cargar los otros atributos de salas de ser necesario
                function.setHall(hall);

                Schedules schedules = new Schedules();
                schedules.setId(resultSet.getInt("horario_id"));
                // cargar los otros atributos de horarios de ser necesario
                function.setSchedules(schedules);

                Customer customer = new Customer();
                customer.setId(resultSet.getInt("cliente_id"));
                // cargar los otros atributos de clientes de ser necesario
                function.setCustomer(customer);
            }

            conexion.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex);
        }
        return function;
    }
    
    public Function() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Schedules getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedules schedules) {
        this.schedules = schedules;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
