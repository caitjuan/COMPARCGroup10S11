package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInserter {
    private DBConnector database;
    
    public DataInserter(){
        database = new DBConnector();
    }
    
    public void close(){
        database.closeDB();
    }
    
    public void insertCode(String instruction, String opcode, String hex, String address){
        String query = "INSERT INTO minimips.code(instruction, opcode, hex, address) "
                + "VALUES (?,?,?,?);";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, instruction);
            ps.setString(2,  opcode);
            ps.setString(3, hex);
            ps.setString(4, address);
        } catch (SQLException ex) {
            Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.executeQueryNoReturn(ps);
    }
    
    public void insertReg(String value, int id){
        String query = "INSERT INTO minimips.regs(id, value) "
                + "VALUES (?,?);";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setInt(1, id);
            ps.setString(2, value);
        } catch (SQLException ex) {
            Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.executeQueryNoReturn(ps);
    }
    
    public void insertMemory(String address, String value){
        String query = "INSERT INTO minimips.memory(address, value) "
                + "VALUES (?,?);";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, address);
            ps.setString(2, value);
        } catch (SQLException ex) {
            Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.executeQueryNoReturn(ps);
    }
    
    public void insertCycle(String IF_IR, String IF_NPC, String IF_PC, String ID_IR, String ID_NPC, String ID_A, String ID_B, 
                            String ID_IMM, String EX_IR, String EX_B, String EX_ALU, String EX_COND, String MEM_IR,
                            String MEM_ALU, String MEM_RANGE, String MEM_LMD, String WB_RN) {
        
        String query = "INSERT INTO minimips.code(IF_IR, IF_NPC, IF_PC, ID_IR, ID_NPC, ID_A, ID_B, \n" +
"                            ID_IMM, EX_IR, EX_B, EX_ALU, EX_COND, MEM_IR,\n" +
"                            MEM_ALU, MEM_RANGE, MEM_LMD, WB_RN) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = database.createStatement(query);
        try {
            ps.setString(1, IF_IR);
            ps.setString(2,  IF_NPC);
            ps.setString(3, IF_PC);
            ps.setString(4, ID_IR);
            ps.setString(5, ID_NPC);
            ps.setString(6,  ID_A);
            ps.setString(7, ID_B);
            ps.setString(8, ID_IMM);
            ps.setString(9, EX_IR);
            ps.setString(10,  EX_B);
            ps.setString(11, EX_ALU);
            ps.setString(12, EX_COND);
            ps.setString(13, MEM_IR);
            ps.setString(14,  MEM_ALU);
            ps.setString(15, MEM_RANGE);
            ps.setString(16, MEM_LMD);
            ps.setString(17, WB_RN);
        } catch (SQLException ex) {
            Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.executeQueryNoReturn(ps);
        
    }
    
}
