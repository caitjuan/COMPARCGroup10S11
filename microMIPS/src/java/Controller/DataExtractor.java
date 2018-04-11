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
