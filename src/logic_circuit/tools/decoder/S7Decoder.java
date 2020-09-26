package logic_circuit.tools.decoder;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.HubGate;
import logic_circuit.base.gate.NotGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;


/**
 * 7段显示译码器 - 失败！
 * _BIRBO具有输入和输出双重功能
 */
public class S7Decoder {
    private Port A0 = new Port("A0"),
                 A1 = new Port("A1"),
                 A2 = new Port("A2"),
                 A3 = new Port("A3"),
                 _LT = new Port("_LT"),
                 _BIRBO = new Port("_BIRBO"),
                 _RBI = new Port("_RBI"),
                 Ya = new Port("Ya"),
                 Yb = new Port("Yb"),
                 Yc = new Port("Yc"),
                 Yd = new Port("Yd"),
                 Ye = new Port("Ye"),
                 Yf = new Port("Yf"),
                 Yg = new Port("Yg");

    private NotGate g1 = new NotGate(),
                    g8 = new NotGate();

    private AndGate g3 = new AndGate(6, false),
                    g5 = new AndGate(false),
                    g6 = new AndGate(false),
                    g7 = new AndGate(false),
                    g9 = new AndGate(false),
                    g10 = new AndGate(false),
                    g11 = new AndGate(false),
                    g12 = new AndGate(false),
                    a1 = new AndGate(true),
                    a2 = new AndGate(true),
                    a3 = new AndGate(4,true),
                    b1 = new AndGate(true),
                    b2 = new AndGate(3,true),
                    b3 = new AndGate(3,true),
                    c1 = new AndGate(true),
                    c2 = new AndGate(3,true),
                    d1 = new AndGate(3,true),
                    d2 = new AndGate(3,true),
                    d3 = new AndGate(3,true),
                    e = new AndGate(true),
                    f1 = new AndGate(true),
                    f2 = new AndGate(true),
                    f3 = new AndGate(3,true),
                    G1 = new AndGate(3,true),
                    G2 = new AndGate(4,true);

    private OrGate g13 = new OrGate(3,false),
                    g14 = new OrGate(3,false),
                    g15 = new OrGate(false),
                    g16 = new OrGate(3,false),
                    g17 = new OrGate(false),
                    g18 = new OrGate(3,false),
                    g19 = new OrGate(false);

    private HubGate h = new HubGate(false);

    public S7Decoder(){
        new Wire(A0, g5.inPort());
        new Wire(A1, g6.inPort());
        new Wire(A2, g7.inPort());
        new Wire(A3, g8.inPort());
        new Wire(_BIRBO, h.inPort());
        new Wire(g3.outPort, h.inPort());
        new Wire(h.outPort, g9.inPort());
        new Wire(h.outPort, g10.inPort());
        new Wire(h.outPort, g11.inPort());
        new Wire(h.outPort, g12.inPort());
        new Wire(_LT, g3.inPort());
        new Wire(_LT, g5.inPort());
        new Wire(_LT, g6.inPort());
        new Wire(_LT, g7.inPort());
        new Wire(_LT, G2.inPort());
        new Wire(_RBI, g1.inPort());
        new Wire(g1.outPort, g3.inPort());
        new Wire(g5.outPort, g9.inPort());
        new Wire(g6.outPort, g10.inPort());
        new Wire(g7.outPort, g11.inPort());
        new Wire(g8.outPort, g12.inPort());

        new Wire(g5.outPort, a2.inPort());
        new Wire(g5.outPort, b3.inPort());
        new Wire(g5.outPort, c2.inPort());
        new Wire(g5.outPort, d2.inPort());
        new Wire(g5.outPort, g3.inPort());

        new Wire(g6.outPort, a3.inPort());
        new Wire(g6.outPort, b2.inPort());
        new Wire(g6.outPort, d1.inPort());
        new Wire(g6.outPort, d2.inPort());
        new Wire(g6.outPort, e.inPort());
        new Wire(g6.outPort, g3.inPort());
        new Wire(g6.outPort, G2.inPort());

        new Wire(g7.outPort, a3.inPort());
        new Wire(g7.outPort, c2.inPort());
        new Wire(g7.outPort, d1.inPort());
        new Wire(g7.outPort, g3.inPort());
        new Wire(g7.outPort, f2.inPort());
        new Wire(g7.outPort, f3.inPort());
        new Wire(g7.outPort, G2.inPort());

        new Wire(g8.outPort, a3.inPort());
        new Wire(g8.outPort, g3.inPort());
        new Wire(g8.outPort, f3.inPort());
        new Wire(g8.outPort, G2.inPort());

        new Wire(g9.outPort, a3.inPort());
        new Wire(g9.outPort, b2.inPort());
        new Wire(g9.outPort, d1.inPort());
        new Wire(g9.outPort, d3.inPort());
        new Wire(g9.outPort, g17.inPort());//
        new Wire(g9.outPort, f1.inPort());
        new Wire(g9.outPort, f3.inPort());
        new Wire(g9.outPort, G1.inPort());

        new Wire(g10.outPort, a1.inPort());
        new Wire(g10.outPort, b1.inPort());
        new Wire(g10.outPort, b3.inPort());
        new Wire(g10.outPort, c2.inPort());
        new Wire(g10.outPort, d3.inPort());
        new Wire(g10.outPort, f1.inPort());
        new Wire(g10.outPort, f2.inPort());
        new Wire(g10.outPort, G1.inPort());

        new Wire(g11.outPort, a2.inPort());
        new Wire(g11.outPort, b2.inPort());
        new Wire(g11.outPort, b3.inPort());
        new Wire(g11.outPort, c1.inPort());
        new Wire(g11.outPort, d2.inPort());
        new Wire(g11.outPort, d3.inPort());
        new Wire(g11.outPort, e.inPort());
        new Wire(g11.outPort, G1.inPort());

        new Wire(g12.outPort, a1.inPort());
        new Wire(g12.outPort, b1.inPort());
        new Wire(g12.outPort, c1.inPort());

        //输出
        new Wire(g13.outPort, Ya);
        new Wire(g14.outPort, Yb);
        new Wire(g15.outPort, Yc);
        new Wire(g16.outPort, Yd);
        new Wire(g17.outPort, Ye);
        new Wire(g18.outPort, Yf);
        new Wire(g19.outPort, Yg);
    }

    public void input(int a3, int a2, int a1, int a0, int _lt, int _rbi, int _birbo){
        A0.setV(a0);
        A1.setV(a1);
        A2.setV(a2);
        A3.setV(a3);
        _BIRBO.setV(_birbo);
        _LT.setV(_lt);
        _RBI.setV(_rbi);
    }

    public Port getA0() {
        return A0;
    }

    public Port getA1() {
        return A1;
    }

    public Port getA2() {
        return A2;
    }

    public Port getA3() {
        return A3;
    }

    public Port get_LT() {
        return _LT;
    }

    public Port get_BIRBO() {
        return _BIRBO;
    }

    public Port get_RBI() {
        return _RBI;
    }

    public Port getYa() {
        return Ya;
    }

    public Port getYb() {
        return Yb;
    }

    public Port getYc() {
        return Yc;
    }

    public Port getYd() {
        return Yd;
    }

    public Port getYe() {
        return Ye;
    }

    public Port getYf() {
        return Yf;
    }

    public Port getYg() {
        return Yg;
    }
}
