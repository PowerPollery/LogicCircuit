package logic_circuit.base.gate;

import logic_circuit.base.port.Port;

/**
 * 或门
 */
public class OrGate extends MultiInputGate{
	
	public OrGate(boolean transformTo) {
		super(transformTo);
	}
	public OrGate(int n, boolean transformTo) {
		super(n, transformTo);
	}
	//设置传输延迟
	public OrGate(boolean transformTo, int delay) {
		super(transformTo, delay);
	}
	public OrGate(int n, boolean transformTo, int delay) {
		super(n, transformTo, delay);
	}
	//或逻辑
	@Override
	protected int logic() {
		int result = 0;
		for(Port port : inPorts) {
			if(port.getV() == 1) {
				result = 1;
				break;
			}
		}
		return result;
	}
	
}
