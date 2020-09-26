package logic_circuit.msi.multiplexer;
/**
 * 双四选一选择器
 */

import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;

public class $74LS153 {
	private Port A0 = new Port("A0"),
				 A1 = new Port("A1"),
				 _1ST = new Port("1ST"),
				 _1D0 = new Port("1D0"),
				 _1D1 = new Port("1D1"),
				 _1D2 = new Port("1D2"),
				 _1D3 = new Port("1D3"),
				 _2ST = new Port("2ST"),
				 _2D0 = new Port("2D0"),
				 _2D1 = new Port("2D1"),
				 _2D2 = new Port("2D2"),
				 _2D3 = new Port("2D3"),
				 _1Y = new Port("1Y"),
				 _2Y = new Port("2Y");
	private Mux41 mux41A = new Mux41(),
				  mux41B = new Mux41();
	
	public $74LS153() {
		//输入/使能端
		new Wire(A0, mux41A.getA0());
		new Wire(A0, mux41B.getA0());
		new Wire(A1, mux41A.getA1());
		new Wire(A1, mux41B.getA1());
		new Wire(_1ST, mux41A.getST());
		new Wire(_2ST, mux41B.getST());
		new Wire(_1D0, mux41A.getD0());
		new Wire(_1D1, mux41A.getD1());
		new Wire(_1D2, mux41A.getD2());
		new Wire(_1D3, mux41A.getD3());		
		new Wire(_2D0, mux41B.getD0());
		new Wire(_2D1, mux41B.getD1());
		new Wire(_2D2, mux41B.getD2());
		new Wire(_2D3, mux41B.getD3());
		//输出端
		new Wire(mux41A.getY(), _1Y);
		new Wire(mux41B.getY(), _2Y);
		
	}
	
	public void input(int a1, int a0, int _1st, int _1d0, int _1d1, int _1d2, int _1d3,
						int _2st, int _2d0, int _2d1, int _2d2, int _2d3) {
		A1.setV(a1);
		A0.setV(a0);
		_1ST.setV(_1st);
		_1D0.setV(_1d0);
		_1D1.setV(_1d1);
		_1D2.setV(_1d2);
		_1D3.setV(_1d3);
		_2D0.setV(_2d0);
		_2D1.setV(_2d1);
		_2D2.setV(_2d2);
		_2D3.setV(_2d3);
	}

	public Port getA0() {
		return A0;
	}

	public Port getA1() {
		return A1;
	}

	public Port get_1ST() {
		return _1ST;
	}

	public Port get_1D0() {
		return _1D0;
	}

	public Port get_1D1() {
		return _1D1;
	}

	public Port get_1D2() {
		return _1D2;
	}

	public Port get_1D3() {
		return _1D3;
	}

	public Port get_2ST() {
		return _2ST;
	}

	public Port get_2D0() {
		return _2D0;
	}

	public Port get_2D1() {
		return _2D1;
	}

	public Port get_2D2() {
		return _2D2;
	}

	public Port get_2D3() {
		return _2D3;
	}

	public Port get_1Y() {
		return _1Y;
	}

	public Port get_2Y() {
		return _2Y;
	}
	
}
