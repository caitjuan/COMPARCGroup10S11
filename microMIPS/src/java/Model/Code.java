package Model;

public class Code {
    private final String instruction;
    private final String opcode;
    private final String address;
    
    public Code(String instruction, String address, String opcode) {
        this.instruction = instruction;
        this.address = address;
        this.opcode = opcode;
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
}
