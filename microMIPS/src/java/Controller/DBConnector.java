package Controller;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
    
    private static final String SCHEMA_NAME = "minimips";
    private static final String SCHEMA_USER = "root";
    private static final String SCHEMA_PASS = "SADIEstone151631";
    
    private Connection connect = null;

    public DBConnector(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/"+SCHEMA_NAME,SCHEMA_USER,SCHEMA_PASS);
            System.out.println("CONNECTION ESTABLISHED");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public PreparedStatement createStatement(String query){
        PreparedStatement pStatement;
        try {
            pStatement = connect.prepareStatement(query);
            return pStatement;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ResultSet executeQueryWithReturn(PreparedStatement pStatement){
        try {
            ResultSet resultSet = pStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void executeQueryNoReturn(PreparedStatement pStatement){
        try {
            pStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeDB(){
        try {
            connect.close();
            System.out.println("CONNECTION CLOSED");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
