package logic_circuit.flipflop.register;

import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;
import logic_circuit.flipflop.edgeflipflop.EdgeD;

/**
 * 四边沿D触发器 - 寄存器
 * 上升沿置数
 */
public class $74LS175 {
    private EdgeD ff0 = new EdgeD(),
                  ff1 = new EdgeD(),
                  ff2 = new EdgeD(),
                  ff3 = new EdgeD();
    private Port CP = new Port("CP"),
                 _CR = new Port("_CR"),
                 D0 = new Port("D0"),
                 D1 = new Port("D1"),
                 D2 = new Port("D2"),
                 D3 = new Port("D3"),
                 Q0 = new Port("Q0"),
                 _Q0 = new Port("_Q0"),
                 Q1 = new Port("Q1"),
                 _Q1 = new Port("_Q1"),
                 Q2 = new Port("Q2"),
                 _Q2 = new Port("_Q2"),
                 Q3 = new Port("Q3"),
                 _Q3 = new Port("_Q3");

    public $74LS175(){
        new Wire(CP, ff0.getCP());
        new Wire(CP, ff1.getCP());
        new Wire(CP, ff2.getCP());
        new Wire(CP, ff3.getCP());

        new Wire(_CR, ff0.getRd());
        new Wire(_CR, ff1.getRd());
        new Wire(_CR, ff2.getRd());
        new Wire(_CR, ff3.getRd());

        new Wire(D0, ff0.getD());
        new Wire(D1, ff1.getD());
        new Wire(D2, ff2.getD());
        new Wire(D3, ff3.getD());
        new Wire(ff0.getQ(), Q0);
        new Wire(ff0.get_Q(), _Q0);
        new Wire(ff1.getQ(), Q1);
        new Wire(ff1.get_Q(), _Q1);
        new Wire(ff2.getQ(), Q2);
        new Wire(ff2.get_Q(), _Q2);
        new Wire(ff3.getQ(), Q3);
        new Wire(ff3.get_Q(), _Q3);

        //Sd端都接1
        ff0.getSd().setV(1);
        ff1.getSd().setV(1);
        ff2.getSd().setV(1);
        ff3.getSd().setV(1);
    }

    public void input(int d3, int d2, int d1, int d0){
        D0.setV(d0);
        D1.setV(d1);
        D2.setV(d1);
        D3.setV(d3);
    }

    public Port getCP() {
        return CP;
    }

    public Port get_CR() {
        return _CR;
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

    public Port getQ0() {
        return Q0;
    }

    public Port get_Q0() {
        return _Q0;
    }

    public Port getQ1() {
        return Q1;
    }

    public Port get_Q1() {
        return _Q1;
    }

    public Port getQ2() {
        return Q2;
    }

    public Port get_Q2() {
        return _Q2;
    }

    public Port getQ3() {
        return Q3;
    }

    public Port get_Q3() {
        return _Q3;
    }
}
