package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataUpdater {
    private DBConnector database;
    
    public DataUpdater(){
        database = new DBConnector();
    }
    
    public void close(){
        database.closeDB();
    }
    
    public void updateReg(String value, int cyclenum){
        String query = "UPDATE minimips.regs"
                + "SET value = ?"
                + "WHERE cyclenum = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, "'" + value + "'");
            ps.setInt(2, cyclenum);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
    }
    
    public void updateMem(String value, String address){
        String query = "UPDATE minimips.memory"
                + "SET value = ?"
                + "WHERE address = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, "'" + value + "'");
            ps.setString(2, address);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
    }
    
    public void reset(){
        resetReg();
        resetMem();
        resetCode();
        resetCycle();
    }
    
    public void resetReg(){
        String query = "TRUNCATE minimips.regs;";
        PreparedStatement ps = database.createStatement(query); 
        
        database.executeQueryNoReturn(ps);
    }
    
    public void resetMem(){
        String query = "TRUNCATE minimips.memory;";
        
        PreparedStatement ps = database.createStatement(query);
        
        database.executeQueryNoReturn(ps);
    }
    
    public void resetCode(){
        String query = "TRUNCATE minimips.code;";
        
        PreparedStatement ps = database.createStatement(query);
        
        database.executeQueryNoReturn(ps);
    }
    
    public void resetCycle(){
        String query = "TRUNCATE minimips.cycle;";
        
        PreparedStatement ps = database.createStatement(query);
        
        database.executeQueryNoReturn(ps);
    }
}
