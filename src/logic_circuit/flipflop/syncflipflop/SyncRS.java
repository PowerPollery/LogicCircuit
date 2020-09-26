package logic_circuit.flipflop.syncflipflop;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.CWire;
import logic_circuit.base.wire.Wire;

/**
 * 同步RS触发器
 * 两输入端不允许同时为1
 */
public class SyncRS {
    private Port S = new Port("S"),
                 R = new Port("R"),
                 CP = new Port("CP"),
                 Q = new Port("Q"),
                 _Q = new Port("_Q");
    private AndGate andNot1 = new AndGate(false),
                    andNot2 = new AndGate(false),
                    andNot3 = new AndGate(false),
                    andNot4 = new AndGate(false);

    public SyncRS(){
        new Wire(S, andNot3.inPort(0));
        new Wire(R, andNot4.inPort(0));
        new Wire(CP, andNot3.inPort(1));
        new Wire(CP, andNot4.inPort(1));

        new Wire(andNot3.outPort, andNot1.inPort(0));
        new Wire(andNot4.outPort, andNot2.inPort(0));
        new CWire(andNot1.outPort, andNot2.inPort(1));
        new CWire(andNot2.outPort, andNot1.inPort(1));

        new Wire(andNot1.outPort, Q);
        new Wire(andNot2.outPort, _Q);
    }

    public void input(int s, int r){
        S.setV(s);
        R.setV(r);
    }

    public Port getS() {
        return S;
    }

    public Port getR() {
        return R;
    }

    public Port getCP() {
        return CP;
    }

    public Port getQ() {
        return Q;
    }

    public Port get_Q() {
        return _Q;
    }
}
