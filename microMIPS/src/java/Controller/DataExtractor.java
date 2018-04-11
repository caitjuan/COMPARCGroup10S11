package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataExtractor {
    private DBConnector database;
    
    public DataExtractor(){
        database = new DBConnector();
    }
    
    public void close(){
        database.closeDB();
    }
    
    public ResultSet getCode(String address){
        String query = "	SELECT * FROM minimips.code\n" +
"	WHERE address = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, address);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
        return rs;
    }
    
    public ResultSet getCodeWithHex(String hex){
        String query = "	SELECT * FROM minimips.code\n" +
"	WHERE hex = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, hex);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
        return rs;
    }
    
    public ResultSet getReg(int id){
        String query = "	SELECT * FROM minimips.regs\n" +
"	WHERE id = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
        return rs;
    }
    
    public ResultSet getMem(String address){
        String query = "	SELECT * FROM minimips.memory\n" +
"	WHERE address = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, address);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
        return rs;
    }
    
    public ResultSet getCycle(int cyclenum){
        String query = "	SELECT * FROM minimips.cycle\n" +
"	WHERE cyclenum = ?";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setInt(1, cyclenum);
        } catch (SQLException ex) {
            Logger.getLogger(DataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = database.executeQueryWithReturn(ps);
        return rs;
    }
}
