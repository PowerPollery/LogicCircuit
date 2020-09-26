package logic_circuit.flipflop.register;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.NotGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;
import logic_circuit.flipflop.edgeflipflop.EdgeJK;

/**
 * 4位双向移位寄存器
 * 上升沿
 * M1M0 1在哪向哪移
 */
public class $74LS194 {
    private Port D0 = new Port("D0"),
                 D1 = new Port("D1"),
                 D2 = new Port("D2"),
                 D3 = new Port("D3"),
                 M1 = new Port("M1"),
                 M0 = new Port("M0"),
                 Dsr = new Port("Dsr"),
                 Dsl = new Port("Dsl"),
                 CP = new Port("CP"),
                 _CR = new Port("_CR"),
                 Q0 = new Port("Q0"),
                 Q1 = new Port("Q1"),
                 Q2 = new Port("Q2"),
                 Q3 = new Port("Q3");
    private EdgeJK ff0 = new EdgeJK(),
                   ff1 = new EdgeJK(),
                   ff2 = new EdgeJK(),
                   ff3 = new EdgeJK();
    private NotGate n1 = new NotGate(),
                    n2 = new NotGate(),
                    n3 = new NotGate(),
                    n4 = new NotGate(),
                    n5 = new NotGate(),
                    n01 = new NotGate(),
                    n02 = new NotGate(),
                    n11 = new NotGate(),
                    n12 = new NotGate();
    private AndGate a01 = new AndGate(3,true),
                    a02 = new AndGate(3,true),
                    a03 = new AndGate(3,true),
                    a04 = new AndGate(3,true),
                    a11 = new AndGate(3,true),
                    a12 = new AndGate(3,true),
                    a13 = new AndGate(3,true),
                    a14 = new AndGate(3,true),
                    a21 = new AndGate(3,true),
                    a22 = new AndGate(3,true),
                    a23 = new AndGate(3,true),
                    a24 = new AndGate(3,true),
                    a31 = new AndGate(3,true),
                    a32 = new AndGate(3,true),
                    a33 = new AndGate(3,true),
                    a34 = new AndGate(3,true);
    private OrGate o0 = new OrGate(4,false),
                   o1 = new OrGate(4,false),
                   o2 = new OrGate(4,false),
                   o3 = new OrGate(4,false);

    public $74LS194(){
        //CP
        new Wire(CP, n5.inPort());
        new Wire(n5.outPort, ff0.getCP());
        new Wire(n5.outPort, ff1.getCP());
        new Wire(n5.outPort, ff2.getCP());
        new Wire(n5.outPort, ff3.getCP());

        //置零
        new Wire(_CR, ff0.get_Rd());
        new Wire(_CR, ff1.get_Rd());
        new Wire(_CR, ff2.get_Rd());
        new Wire(_CR, ff3.get_Rd());

        //与或非输出
        new Wire(a01.outPort, o0.inPort());
        new Wire(a02.outPort, o0.inPort());
        new Wire(a03.outPort, o0.inPort());
        new Wire(a04.outPort, o0.inPort());
        new Wire(a11.outPort, o1.inPort());
        new Wire(a12.outPort, o1.inPort());
        new Wire(a13.outPort, o1.inPort());
        new Wire(a14.outPort, o1.inPort());
        new Wire(a21.outPort, o2.inPort());
        new Wire(a22.outPort, o2.inPort());
        new Wire(a23.outPort, o2.inPort());
        new Wire(a24.outPort, o2.inPort());
        new Wire(a31.outPort, o3.inPort());
        new Wire(a32.outPort, o3.inPort());
        new Wire(a33.outPort, o3.inPort());
        new Wire(a34.outPort, o3.inPort());
        new Wire(o0.outPort, ff0.getK());
        new Wire(o0.outPort, n1.inPort());
        new Wire(o1.outPort, ff1.getK());
        new Wire(o1.outPort, n2.inPort());
        new Wire(o2.outPort, ff2.getK());
        new Wire(o2.outPort, n3.inPort());
        new Wire(o3.outPort, ff3.getK());
        new Wire(o3.outPort, n4.inPort());
        //非门
        new Wire(n1.outPort, ff0.getJ());
        new Wire(n2.outPort, ff1.getJ());
        new Wire(n3.outPort, ff2.getJ());
        new Wire(n4.outPort, ff3.getJ());
        //JK输出
        new Wire(ff0.getQ(), a04.inPort());
        new Wire(ff0.getQ(), a11.inPort());
        new Wire(ff1.getQ(), a03.inPort());
        new Wire(ff1.getQ(), a14.inPort());
        new Wire(ff1.getQ(), a21.inPort());
        new Wire(ff2.getQ(), a13.inPort());
        new Wire(ff2.getQ(), a24.inPort());
        new Wire(ff2.getQ(), a31.inPort());
        new Wire(ff3.getQ(), a23.inPort());
        new Wire(ff3.getQ(), a34.inPort());
        new Wire(ff0.getQ(), Q0);
        new Wire(ff1.getQ(), Q1);
        new Wire(ff2.getQ(), Q2);
        new Wire(ff3.getQ(), Q3);
        //串行输入
        new Wire(Dsr, a01.inPort());
        new Wire(Dsl, a33.inPort());
        //左右移
        new Wire(M1, n11.inPort());
        new Wire(n11.outPort, n12.inPort());
        new Wire(M0, n01.inPort());
        new Wire(n01.outPort, n02.inPort());

        new Wire(n11.outPort, a01.inPort());
        new Wire(n11.outPort, a04.inPort());
        new Wire(n11.outPort, a11.inPort());
        new Wire(n11.outPort, a14.inPort());
        new Wire(n11.outPort, a21.inPort());
        new Wire(n11.outPort, a24.inPort());
        new Wire(n11.outPort, a31.inPort());
        new Wire(n11.outPort, a34.inPort());
        new Wire(n12.outPort, a02.inPort());
        new Wire(n12.outPort, a03.inPort());
        new Wire(n12.outPort, a12.inPort());
        new Wire(n12.outPort, a13.inPort());
        new Wire(n12.outPort, a22.inPort());
        new Wire(n12.outPort, a23.inPort());
        new Wire(n12.outPort, a32.inPort());
        new Wire(n12.outPort, a33.inPort());

        new Wire(n01.outPort, a03.inPort());
        new Wire(n01.outPort, a04.inPort());
        new Wire(n01.outPort, a13.inPort());
        new Wire(n01.outPort, a14.inPort());
        new Wire(n01.outPort, a23.inPort());
        new Wire(n01.outPort, a24.inPort());
        new Wire(n01.outPort, a33.inPort());
        new Wire(n01.outPort, a34.inPort());
        new Wire(n02.outPort, a01.inPort());
        new Wire(n02.outPort, a02.inPort());
        new Wire(n02.outPort, a11.inPort());
        new Wire(n02.outPort, a12.inPort());
        new Wire(n02.outPort, a21.inPort());
        new Wire(n02.outPort, a22.inPort());
        new Wire(n02.outPort, a31.inPort());
        new Wire(n02.outPort, a32.inPort());
        //数据输入
        new Wire(D0, a02.inPort());
        new Wire(D1, a12.inPort());
        new Wire(D2, a22.inPort());
        new Wire(D3, a32.inPort());

        //S端置1后，R端就相当于清零端，低有效，高保持
        ff0.get_Sd().setV(1);
        ff1.get_Sd().setV(1);
        ff2.get_Sd().setV(1);
        ff3.get_Sd().setV(1);
    }

    public void input(int _cr, int dsl, int dsr, int m1, int m0, int d3, int d2, int d1, int d0){
        _CR.setV(_cr);
        Dsr.setV(dsr);
        Dsl.setV(dsl);
        M1.setV(m1);
        M0.setV(m0);
        D3.setV(d3);
        D2.setV(d2);
        D1.setV(d1);
        D0.setV(d0);
    }

    public Port getD0() {
        return D0;
    }

    public Port getD1() {
        return D1;
    }

    public Port getD2() {
        return D2;
    }

    public Port getD3() {
        return D3;
    }

    public Port getM1() {
        return M1;
    }

    public Port getM0() {
        return M0;
    }

    public Port getDsr() {
        return Dsr;
    }

    public Port getDsl() {
        return Dsl;
    }

    public Port getCP() {
        return CP;
    }

    public Port get_CR() {
        return _CR;
    }

    public Port getQ0() {
        return Q0;
    }

    public Port getQ1() {
        return Q1;
    }

    public Port getQ2() {
        return Q2;
    }

    public Port getQ3() {
        return Q3;
    }
}