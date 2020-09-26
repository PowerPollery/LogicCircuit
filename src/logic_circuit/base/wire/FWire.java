package logic_circuit.base.wire;

import logic_circuit.base.port.Port;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;

/**
 * Flip-Wire
 * 翻转导线，类似于非门功能，但仅用于测试，禁止连接于器件内部
 * 非循环安全的导线
 */
public class FWire {
    private Port pA, pB;//导线的两端 - 按电流流向
    public FWire(Port pA, Port pB) {
        this.pA = pA;
        this.pB = pB;
        addListener();
    }
    //注册监听器
    private void addListener() {
        pA.addPortListener(new PortListener() {
            @Override
            public void portAffected(PortEvent e) {
                if(e.getType().equals(PortEvent.SETV)) {
                    pB.setV(pA.getV() == 1 ? 0 : 1);//传递
                }
            }
        });
    }
}
