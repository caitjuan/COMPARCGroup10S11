package Model;

public class Memory {
    private String[] mem = new String[31];
    
    public Memory(String[] mem){
        this.mem = mem;
    }
    
    public String getReg(int i){
        return mem[i];
    }
}
