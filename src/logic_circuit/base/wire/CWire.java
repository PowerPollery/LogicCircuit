package logic_circuit.base.wire;

import logic_circuit.base.port.Port;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;

/**
 * Cycle-safe
 * 循环安全的导线 - 即只有在作用后B端状态与之前不同的情况下才传输，避免死循环
 */
public class CWire {
    private Port pA, pB;//导线的两端 - 按电流流向
    public CWire(Port pA, Port pB) {
        this.pA = pA;
        this.pB = pB;
        addListener();
    }
    //注册监听器
    private void addListener() {
        pA.addPortListener(new PortListener() {
            @Override
            public void portAffected(PortEvent e) {
                if(e.getType().equals(PortEvent.SETV) && pB.getV() != pA.getV()) {//B端口不等于A端口时
                    pB.setV(pA.getV());//传递
                }
            }
        });
    }
}
