package logic_circuit.msi.decoder;

import logic_circuit.base.gate.NotGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;

/**
 * 3线-8线译码器
 * 输出低有效
 */
public class $74LS138 {
	private Port A0 = new Port("A0"),
				 A1 = new Port("A1"),
				 A2 = new Port("A2"),
				 STA = new Port("STA"),
				 STB = new Port("STB"),
				 STC = new Port("STC"),
				 Y0 = new Port("Y0"),
				 Y1 = new Port("Y1"),
				 Y2 = new Port("Y2"),
				 Y3 = new Port("Y3"),
				 Y4 = new Port("Y4"),
				 Y5 = new Port("Y5"),
				 Y6 = new Port("Y6"),
				 Y7 = new Port("Y7");
	private $74LS139 line24A = new $74LS139(),
					 line24B = new $74LS139();
	private NotGate not1 = new NotGate(),
					not2 = new NotGate();
	private OrGate or = new OrGate(3, true),	//用于三个使能端
				   or1 = new OrGate(true),		//用于使能端和输出端相或
				   or2 = new OrGate(true),
				   or3 = new OrGate(true),
				   or4 = new OrGate(true),
				   or5 = new OrGate(true),
				   or6 = new OrGate(true),
				   or7 = new OrGate(true),
				   or8 = new OrGate(true);
	
	public $74LS138() {
		//使能端 - STA为1，STB、STC为0时译码器工作
		//发现本使能端与其他端口同时操作一个子原件的使能端时，会发生冲突
		//所以更改思路，令每个输出都和使能逻辑相或再输出，解决问题。
		new Wire(STA, not1.inPort());
		new Wire(not1.outPort, or.inPort(0));
		new Wire(STB, or.inPort(1));
		new Wire(STC, or.inPort(2));
		new Wire(line24A.getY0(), or1.inPort(0));
		new Wire(line24A.getY1(), or2.inPort(0));
		new Wire(line24A.getY2(), or3.inPort(0));
		new Wire(line24A.getY3(), or4.inPort(0));
		new Wire(line24B.getY0(), or5.inPort(0));
		new Wire(line24B.getY1(), or6.inPort(0));
		new Wire(line24B.getY2(), or7.inPort(0));
		new Wire(line24B.getY3(), or8.inPort(0));
		new Wire(or.outPort, or1.inPort(1));
		new Wire(or.outPort, or2.inPort(1));
		new Wire(or.outPort, or3.inPort(1));
		new Wire(or.outPort, or4.inPort(1));
		new Wire(or.outPort, or5.inPort(1));
		new Wire(or.outPort, or6.inPort(1));
		new Wire(or.outPort, or7.inPort(1));
		new Wire(or.outPort, or8.inPort(1));
		
		//输入端
		new Wire(A2, line24A.getST());
		new Wire(A2, not2.inPort());
		new Wire(not2.outPort, line24B.getST());
		new Wire(A1, line24A.getA1());
		new Wire(A1, line24B.getA1());
		new Wire(A0, line24A.getA0());
		new Wire(A0, line24B.getA0());
		//输出端
		new Wire(or1.outPort, Y0);
		new Wire(or2.outPort, Y1);
		new Wire(or3.outPort, Y2);
		new Wire(or4.outPort, Y3);
		new Wire(or5.outPort, Y4);
		new Wire(or6.outPort, Y5);
		new Wire(or7.outPort, Y6);
		new Wire(or8.outPort, Y7);
	}
	
	public void input(int sta, int stb, int stc, int a2, int a1, int a0) {
		STA.setV(sta);
		STB.setV(stb);
		STC.setV(stc);
		A2.setV(a2);
		A1.setV(a1);
		A0.setV(a0);
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

	public Port getSTA() {
		return STA;
	}

	public Port getSTB() {
		return STB;
	}

	public Port getSTC() {
		return STC;
	}

	public Port getY0() {
		return Y0;
	}

	public Port getY1() {
		return Y1;
	}

	public Port getY2() {
		return Y2;
	}

	public Port getY3() {
		return Y3;
	}

	public Port getY4() {
		return Y4;
	}

	public Port getY5() {
		return Y5;
	}

	public Port getY6() {
		return Y6;
	}

	public Port getY7() {
		return Y7;
	}
	
}
