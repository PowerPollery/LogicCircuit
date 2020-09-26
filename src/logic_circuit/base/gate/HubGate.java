package logic_circuit.base.gate;

import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.port.Port;

/**
 * 集线门
 * 作用是把多个输入合并为一个输入，哪个端口变动就传递哪个信号
 */
public class HubGate extends MultiInputGate{
    private int out;

    public HubGate(boolean transformTo) {
        super(transformTo);
    }
    public HubGate(int n, boolean transformTo) {
        super(n, transformTo);
    }
    public HubGate(boolean transformTo, int delay) {
        super(transformTo, delay);
    }
    public HubGate(int n, boolean transformTo, int delay) {
        super(n, transformTo, delay);
    }

    protected void addListener() {
        for(Port p : inPorts) {
            p.addPortListener(new PortListener() {
                @Override
                public void portAffected(PortEvent e) {
                    if(e.getType().equals(PortEvent.SETV)) {
                        out = p.getV();
                        react();
                    }
                }
            });
        }
    }

    @Override
    protected int logic() {
        return out;
    }
}
