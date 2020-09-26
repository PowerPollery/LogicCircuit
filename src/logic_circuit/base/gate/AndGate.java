package logic_circuit.base.gate;
import logic_circuit.base.port.Port;
/**
 * 与门
 */
public class AndGate extends MultiInputGate{
	public AndGate(boolean transformTo) {
		super(transformTo);
	}
	public AndGate(int n, boolean transformTo) {
		super(n, transformTo);
	}
	//设置传输延迟
	public AndGate(boolean transformTo, int delay) {
		super(transformTo, delay);
	}
	public AndGate(int n, boolean transformTo, int delay) {
		super(n, transformTo, delay);
	}
	//与逻辑
	@Override
	protected int logic() {
        int result = 1;
        for(Port port : inPorts) {
            if(port.getV() != 1) {
                result = 0;
                break;
            }
        }
        return result;
	}
	
}
