/*
 * 
 */
package mx.itson.hardMex.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public class MySQLConnection {
    public static Connection get(){
        Connection connection = null;
        try{
            //connection = DriverManager.getConnection("ndbc:mysql://localhost/gansitodb/?user=root&password=080901");
            connection = DriverManager.getConnection("jdbc:mysql://arturog58.sg-host.com:3306/dbaswox04bsrqh","u4iwcrzxmsk8g","camaronesITSON");
        }catch(Exception ex){
            System.err.print("Error: "+ex.getMessage());
        }
        return connection;
    }
}
