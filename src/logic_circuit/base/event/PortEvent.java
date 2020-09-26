package logic_circuit.base.event;

/**
 * 端口事件
 */
public class PortEvent {
    public static final String SETV = "set V with transmission";//传输
    public static final String SETV_ONLY = "set V without transmission";//不传输
    private String type = "";
    private boolean stateChage = false;

    public PortEvent(String type, boolean stateChage){
        this.type = type;
        this.stateChage = stateChage;
    }

    public boolean isStateChage() {
        return stateChage;
    }

    public String getType(){
        return type;
    }
}
