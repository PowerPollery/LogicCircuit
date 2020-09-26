package logic_circuit.flipflop.syncflipflop;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.CWire;
import logic_circuit.base.wire.Wire;

/**
 * 同步JK触发器
 * J K 同时为1时会不停地翻转，产生振荡，看不出效果
 */
public class SyncJK {
    private Port Q = new Port("Q"),
                 _Q = new Port("_Q"),
                 CP = new Port("CP"),
                 J = new Port("J"),
                 K = new Port("K");
    private AndGate andNot1 = new AndGate(2, false),
                    andNot2 = new AndGate(2, false),
                    andNot3 = new AndGate(3, false),
                    andNot4 = new AndGate(3, false);

    public SyncJK(){
        new Wire(J, andNot3.inPort(0));
        new Wire(K, andNot4.inPort(0));
        new Wire(CP, andNot3.inPort(1));
        new Wire(CP, andNot4.inPort(1));

        new Wire(andNot3.outPort, andNot1.inPort(0));
        new Wire(andNot4.outPort, andNot2.inPort(0));
        new CWire(andNot1.outPort, andNot2.inPort(1));
        new CWire(andNot1.outPort, andNot4.inPort(2));
        new CWire(andNot2.outPort, andNot1.inPort(1));
        new CWire(andNot2.outPort, andNot3.inPort(2));

        new Wire(andNot1.outPort, Q);
        new Wire(andNot2.outPort, _Q);
    }

    public void input(int j, int k){
        J.setV(j);
        K.setV(k);
    }

    public Port getQ() {
        return Q;
    }

    public Port get_Q() {
        return _Q;
    }

    public Port getCP() {
        return CP;
    }

    public Port getJ() {
        return J;
    }

    public Port getK() {
        return K;
    }
}
