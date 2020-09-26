package logic_circuit.base.wire;

import logic_circuit.base.port.Port;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;

/**
 * 导线 - 连接端口
 * 导线的主要作用是为A端口添加监听器，此后只要A端口变化，则B端口立刻变化
 * 目前与CWire功能相同，适时可取代CWire，或者让CWire为循环的标识
 */
/**
 * Cycle-safe
 * 循环安全的导线 - 即只有在作用后B端状态与之前不同的情况下才传输，避免死循环
 */
public class Wire {
    private Port pA, pB;//导线的两端 - 按电流流向
    public Wire(Port pA, Port pB) {
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