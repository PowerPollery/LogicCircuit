package logic_circuit.msi.multiplexer;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.NotGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;

/**
 * 4选1数据选择器
 */
public class Mux41 {
	private Port A1 = new Port("A1"),
			     A0 = new Port("A0"),
			     ST = new Port("ST"),
			     D0 = new Port("D0"),
			     D1 = new Port("D1"),
			     D2 = new Port("D2"),
			     D3 = new Port("D3"),
			     Y = new Port("Y");
	private NotGate not1 = new NotGate(),
					not2 = new NotGate(),
					not3 = new NotGate(),
					not4 = new NotGate(),
					not5 = new NotGate();
	private AndGate and1 = new AndGate(4, true),
					and2 = new AndGate(4, true),
					and3 = new AndGate(4, true),
					and4 = new AndGate(4, true);
	private OrGate or = new OrGate(4, true);
	
	public Mux41() {
		//使能端
		new Wire(ST, not5.inPort());
		new Wire(not5.outPort, and1.inPort(0));
		new Wire(not5.outPort, and2.inPort(0));
		new Wire(not5.outPort, and3.inPort(0));
		new Wire(not5.outPort, and4.inPort(0));
		//数据端
		new Wire(D0, and1.inPort(1));
		new Wire(D1, and2.inPort(1));
		new Wire(D2, and3.inPort(1));
		new Wire(D3, and4.inPort(1));
		//输入端
		new Wire(A1, not1.inPort());
		new Wire(A0, not2.inPort());
		new Wire(not1.outPort, and1.inPort(2));
		new Wire(not1.outPort, and2.inPort(2));
		new Wire(not1.outPort, not3.inPort());
		new Wire(not2.outPort, and1.inPort(3));
		new Wire(not2.outPort, and3.inPort(2));
		new Wire(not2.outPort, not4.inPort());
		new Wire(not3.outPort, and3.inPort(3));
		new Wire(not3.outPort, and4.inPort(2));
		new Wire(not4.outPort, and2.inPort(3));
		new Wire(not4.outPort, and4.inPort(3));
		//输出端
		new Wire(and1.outPort, or.inPort(0));
		new Wire(and2.outPort, or.inPort(1));
		new Wire(and3.outPort, or.inPort(2));
		new Wire(and4.outPort, or.inPort(3));
		new Wire(or.outPort, Y);
	}
	
	public void input(int st, int a1, int a0, int d0, int d1, int d2, int d3) {
		ST.setV(st);
		A1.setV(a1);
		A0.setV(a0);
		D0.setV(d0);
		D1.setV(d1);
		D2.setV(d2);
		D3.setV(d3);
	}

	public Port getA1() {
		return A1;
	}

	public Port getA0() {
		return A0;
	}

	public Port getST() {
		return ST;
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

	public Port getY() {
		return Y;
	}
	
}
