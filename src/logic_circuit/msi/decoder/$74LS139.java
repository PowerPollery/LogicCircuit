package logic_circuit.msi.decoder;
/**
 * 2线-4线译码器
 * 输出低有效，意思是输出1时逻辑上无效
 * 使能低有效
 */

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.NotGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;

public class $74LS139 {
	private Port ST = new Port("ST"),
				 A1 = new Port("A1"),
				 A0 = new Port("A0"),
				 Y3 = new Port("Y3"),
				 Y2 = new Port("Y2"),
				 Y1 = new Port("Y1"),
				 Y0 = new Port("Y0");
	private NotGate not1 = new NotGate(),
					not2 = new NotGate(),
					not3 = new NotGate(),
					not4 = new NotGate(),
					not5 = new NotGate();
	private AndGate andNot1 = new AndGate(3, false),
					andNot2 = new AndGate(3, false),
					andNot3 = new AndGate(3, false),
					andNot4 = new AndGate(3, false);
	
	public $74LS139() {
		new Wire(ST, not1.inPort());
		new Wire(A1, not2.inPort());
		new Wire(A0, not3.inPort());
		
		new Wire(not1.outPort, andNot1.inPort(0));
		new Wire(not1.outPort, andNot2.inPort(0));
		new Wire(not1.outPort, andNot3.inPort(0));
		new Wire(not1.outPort, andNot4.inPort(0));
		new Wire(not2.outPort, andNot1.inPort(1));
		new Wire(not2.outPort, andNot2.inPort(1));
		new Wire(not2.outPort, not4.inPort());
		new Wire(not3.outPort, andNot1.inPort(2));
		new Wire(not3.outPort, andNot3.inPort(1));
		new Wire(not3.outPort, not5.inPort());
		new Wire(not4.outPort, andNot3.inPort(2));
		new Wire(not4.outPort, andNot4.inPort(1));
		new Wire(not5.outPort, andNot2.inPort(2));
		new Wire(not5.outPort, andNot4.inPort(2));
		
		new Wire(andNot1.outPort, Y0);
		new Wire(andNot2.outPort, Y1);
		new Wire(andNot3.outPort, Y2);
		new Wire(andNot4.outPort, Y3);
	}
	
	public void input(int st, int a1, int a0) {
		ST.setV(st);
		A1.setV(a1);
		A0.setV(a0);
	}

	public Port getST() {
		return ST;
	}

	public Port getA1() {
		return A1;
	}

	public Port getA0() {
		return A0;
	}

	public Port getY3() {
		return Y3;
	}

	public Port getY2() {
		return Y2;
	}

	public Port getY1() {
		return Y1;
	}

	public Port getY0() {
		return Y0;
	}
	
}
