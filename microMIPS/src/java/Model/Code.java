package Model;

public class Code {
    private final String instruction;
    private final String opcode;
    private final String address;
    private final String hex;
    
    public Code(String instruction, String address, String opcode, String hex) {
        this.instruction = instruction;
        this.address = address;
        this.opcode = opcode;
        this.hex = hex;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getInstruction() {
        return instruction;
    }
    
    public String getOpcode(){
        return opcode;
    }
    
    public String getHex() {
        return hex;
    }
    
}
