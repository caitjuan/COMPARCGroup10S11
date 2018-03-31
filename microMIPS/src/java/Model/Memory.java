package Model;

public class Memory {
    private String address;
    private String value;
    
    public Memory(String address, String value){
        this.address = address;
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    public String getAddress(){
        return value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
}
