package logic_circuit.flipflop.edgeflipflop;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;

/**
 * 边沿D触发器 74LS74
 * 上升沿
 */
public class EdgeD {
    private Port _Q = new Port("_Q"),
                 Q = new Port("Q"),
                 D = new Port("D"),
                 CP = new Port("CP"),
                 Rd = new Port("Rd"),
                 Sd = new Port("Sd");
    private AndGate g1 = new AndGate(3,false),
                    g2 = new AndGate(3,false),
                    g3 = new AndGate(3, false),
                    g4 = new AndGate(3,false),
                    g5 = new AndGate(3,false),
                    g6 = new AndGate(3,false);

    public EdgeD(){
        new Wire(D, g5.inPort());
        new Wire(CP, g3.inPort());
        new Wire(CP, g4.inPort());

        new Wire(g5.outPort, g3.inPort());
        new Wire(g5.outPort, g6.inPort());
        new Wire(g6.outPort, g4.inPort());
        new Wire(g3.outPort, g5.inPort());
        new Wire(g3.outPort, g1.inPort());
        new Wire(g4.outPort, g3.inPort());
        new Wire(g4.outPort, g2.inPort());
        new Wire(g4.outPort, g6.inPort());

        new Wire(g1.outPort, g2.inPort());
        new Wire(g2.outPort, g1.inPort());
        new Wire(g1.outPort, _Q);
        new Wire(g2.outPort, Q);

        new Wire(Rd, g1.inPort());
        new Wire(Rd, g4.inPort());
        new Wire(Rd, g5.inPort());
        new Wire(Sd, g2.inPort());
        new Wire(Sd, g6.inPort());
    }

    public void input(int rd, int sd, int d){
        Rd.setV(rd);
        Sd.setV(sd);
        D.setV(d);
    }

    public Port getRd() {
        return Rd;
    }

    public Port getSd() {
        return Sd;
    }

    public Port get_Q() {
        return _Q;
    }

    public Port getQ() {
        return Q;
    }

    public Port getD() {
        return D;
    }

    public Port getCP() {
        return CP;
    }
}
