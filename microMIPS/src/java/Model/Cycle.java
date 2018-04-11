package Model;

public class Cycle {
    private String IF_IR;
    private String IF_NPC;
    private String IF_PC;
    
    private String ID_IR;
    private String ID_NPC;
    private String ID_A;
    private String ID_B;
    private String ID_IMM;
    
    private String EX_IR;
    private String EX_B;
    private String EX_ALU;
    private String EX_COND;
    
    private String MEM_IR;
    private String MEM_ALU;
    private String MEM_RANGE;
    private String MEM_LMD;
    
    private String WB_RN;
    
    private boolean mayBranch;
    private boolean done;
    
    public Cycle(){
        
    }
    
    
}
