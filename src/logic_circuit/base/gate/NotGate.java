package logic_circuit.base.gate;

import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.port.Port;

/**
 * 非门
 */
public class NotGate {
	private Port inPort = new Port();
	public Port outPort = new Port();
	
	public NotGate() {
		//注册监听器
		inPort.addPortListener(new PortListener() {
			@Override
			public void portAffected(PortEvent e) {
				if(e.getType().equals(PortEvent.SETV))
					react();
			}
		});
	}
	
	//反应 - 非逻辑
	private void react() {
		outPort.setV(inPort.getV()==1 ? 0 : 1);
	}
	
	public void input(int value) {
		inPort.setV(value);
	}
	
	public Port inPort() {
		return inPort;
	}
}
