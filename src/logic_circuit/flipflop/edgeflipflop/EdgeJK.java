package logic_circuit.flipflop.edgeflipflop;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.CWire;
import logic_circuit.base.wire.Wire;

/**
 * 边沿JK触发器
 * 下降沿
 * 实际设计要求两个与非门的传输延迟时间比基本RS触发器的反转时间要长，
 * 所以，加上了延迟时间，可以正常运行了
 */
public class EdgeJK {
    private static final int DELAY = 10;//与非门延迟时间

    private Port Q = new Port("Q"),
                 _Q = new Port("_Q"),
                 _Sd = new Port("_Sd"),
                 _Rd = new Port("_Rd"),
                 J = new Port("J"),
                 K = new Port("K"),
                 CP = new Port("CP");
    private OrGate orNot1 = new OrGate(false),
                   orNot2 = new OrGate(false);
    private AndGate and1 = new AndGate(3,true),
                    and2 = new AndGate(3, true),
                    and3 = new AndGate(3, true),
                    and4 = new AndGate(3, true),
                    andNot1 = new AndGate(4, false, DELAY),
                    andNot2 = new AndGate(4, false, DELAY);

    public EdgeJK(){
        new Wire(_Rd, and1.inPort());
        new Wire(_Rd, and2.inPort());
        new Wire(_Rd, andNot2.inPort());
        new Wire(_Sd, and4.inPort());
        new Wire(_Sd, and3.inPort());
        new Wire(_Sd, andNot1.inPort());

        new Wire(K, andNot1.inPort());
        new Wire(J, andNot2.inPort());
        new Wire(CP, and1.inPort());
        new Wire(CP, andNot1.inPort());
        new Wire(CP, andNot2.inPort());
        new Wire(CP, and4.inPort());

        new Wire(andNot1.outPort, and2.inPort());
        new Wire(andNot2.outPort, and3.inPort());
        new Wire(and1.outPort, orNot1.inPort());
        new Wire(and2.outPort, orNot1.inPort());
        new Wire(and3.outPort, orNot2.inPort());
        new Wire(and4.outPort, orNot2.inPort());

        new CWire(orNot1.outPort, and3.inPort());
        new CWire(orNot1.outPort, and4.inPort());
        new CWire(orNot1.outPort, andNot2.inPort());
        new CWire(orNot2.outPort, and1.inPort());
        new CWire(orNot2.outPort, and2.inPort());
        new CWire(orNot2.outPort, andNot1.inPort());

        new Wire(orNot1.outPort, _Q);
        new Wire(orNot2.outPort, Q);

    }

    public void input(int _rd, int _sd, int j, int k){
        _Rd.setV(_rd);
        _Sd.setV(_sd);
        J.setV(j);
        K.setV(k);
    }

    public Port getQ() {
        return Q;
    }

    public Port get_Q() {
        return _Q;
    }

    public Port get_Sd() {
        return _Sd;
    }

    public Port get_Rd() {
        return _Rd;
    }

    public Port getJ() {
        return J;
    }

    public Port getK() {
        return K;
    }

    public Port getCP() {
        return CP;
    }
}
