package test;

import logic_circuit.base.gate.AndGate;
import logic_circuit.base.gate.NotGate;
import logic_circuit.base.gate.OrGate;
import logic_circuit.base.port.Port;
import logic_circuit.base.wire.Wire;
import logic_circuit.msi.decoder.$74LS138;
import logic_circuit.tools.showpanel.ShowPanel;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		/*首次实验*/
		AndGate and1 = new AndGate(true);
		AndGate and2 = new AndGate(true);
		OrGate or = new OrGate(true);
		NotGate not = new NotGate();
		//连接
		new Wire(and1.outPort, or.inPort(0));
		new Wire(and2.outPort, or.inPort(1));
		new Wire(or.outPort, not.inPort());
		//输入
		and1.input(1, 0);
		and2.input(1, 0);
		//输出
		Thread.sleep(50);//反应时间要有
		System.out.println(not.outPort);
		
		/*同或逻辑功能*/
//		AndGate andNot1 = new AndGate(false),
//				andNot2 = new AndGate(false),
//				andNot3 = new AndGate(false),
//				andNot4 = new AndGate(false),
//				andNot5 = new AndGate(false);
//		Port A = new Port(),
//			 B = new Port(),
//			 F = new Port();
//
//		new Wire(A, andNot1.inPort(0));
//		new Wire(B, andNot1.inPort(1));
//		new Wire(A, andNot2.inPort(0));
//		new Wire(B, andNot3.inPort(1));
//		new Wire(andNot1.outPort, andNot2.inPort(1));
//		new Wire(andNot1.outPort, andNot3.inPort(0));
//		new Wire(andNot2.outPort, andNot4.inPort(0));
//		new Wire(andNot3.outPort, andNot4.inPort(1));
//		new Wire(andNot4.outPort, andNot5.inPort(0));
//		new Wire(andNot4.outPort, andNot5.inPort(1));
//		new Wire(andNot5.outPort, F);
//
//		ShowPanel sp = new ShowPanel("同或逻辑");
//		sp.addInPorts(A,B);
//		sp.addOutPorts(F);
//		sp.showPanel();

		//2-4译码器
//		$74LS139 line24 = new $74LS139();
//		ShowPanel sp = new ShowPanel();
//		sp.addInPorts(line24.getST(), line24.getA1(), line24.getA0());
//		sp.addOutPorts(line24.getY3(), line24.getY2(), line24.getY1(), line24.getY0());
//		sp.showPanel();

		
		//3-8译码器
//		$74LS138 line38 = new $74LS138();
//		ShowPanel s = new ShowPanel("3-8译码器");
//		s.addInPorts(line38.getSTA(), line38.getSTB(), line38.getSTC(),
//				line38.getA2(), line38.getA1(), line38.getA0());
//		s.addOutPorts(line38.getY0(), line38.getY1(), line38.getY2(), line38.getY3(),
//				line38.getY4(), line38.getY5(), line38.getY6(), line38.getY7());
//		s.showPanel();

		//4选1
//		Mux41 mux41 = new Mux41();
//		ShowPanel s = new ShowPanel("四选一选择器");
//		s.addInPorts(mux41.getST(), mux41.getA1(), mux41.getA0(), mux41.getD0(),
//				mux41.getD1(), mux41.getD2(), mux41.getD3());
//		s.addOutPorts(mux41.getY());
//		s.showPanel();
//
//
//		//双四选一
//		$74LS153 dmux41 = new $74LS153();
//		ShowPanel s1 = new ShowPanel("双四选一选择器");
//		s1.addInPorts(dmux41.getA1(), dmux41.getA0(), dmux41.get_1ST(), dmux41.get_1D0(),
//				dmux41.get_1D1(), dmux41.get_1D2(), dmux41.get_1D3(), dmux41.get_2ST(),
//				dmux41.get_2D0(), dmux41.get_2D1(), dmux41.get_2D2(), dmux41.get_2D3());
//		s1.addOutPorts(dmux41.get_1Y(), dmux41.get_2Y());
//		s1.showPanel();
		
		//基本RS触发器
//		RS rs = new RS();
//        ShowPanel sp = new ShowPanel("基本RS触发器");
//        sp.addInPorts(rs.get_Sd(), rs.get_Rd());
//        sp.addOutPorts(rs.getQ(), rs.get_Q());
//        sp.showPanel();

		//同步RS触发器
//		SyncRS srs = new SyncRS();
//		ShowPanel sp = new ShowPanel("同步RS触发器");
//		sp.addInPorts(srs.getCP(), srs.getR(), srs.getS());
//		sp.addOutPorts(srs.getQ(), srs.get_Q());
//		sp.showPanel();

		//同步D触发器
//		SyncD sd = new SyncD();
//		ShowPanel sp = new ShowPanel("同步D触发器");
//		sp.addInPorts(sd.getCP(), sd.getD());
//		sp.addOutPorts(sd.getQ(), sd.get_Q());
//		sp.showPanel();

        //同步JK触发器
//        SyncJK jk = new SyncJK();
//        ShowPanel sp = new ShowPanel("同步JK触发器");
//        sp.addInPorts(jk.getCP(), jk.getJ(), jk.getK());
//        sp.addOutPorts(jk.getQ(), jk.get_Q());
//        sp.showPanel();

        //边沿JK触发器
//        EdgeJK jk = new EdgeJK();
//        ShowPanel sp = new ShowPanel("边沿JK触发器");
//        sp.addInPorts(jk.get_Rd(), jk.get_Sd(), jk.getCP(), jk.getJ(), jk.getK());
//        sp.addOutPorts(jk.getQ(), jk.get_Q());
//        sp.showPanel();

        //边沿D触发器
//        EdgeD d = new EdgeD();
//        ShowPanel sp = new ShowPanel("边沿D触发器");
//        sp.addInPorts(d.getRd(), d.getSd(), d.getCP(), d.getD());
//        sp.addOutPorts(d.getQ(), d.get_Q());
//        sp.showPanel();

        //4位流水灯
//        EdgeD d1 = new EdgeD(),
//                d2 = new EdgeD();
//        $74LS139 dec = new $74LS139();
//        new Wire(d1.get_Q(), d1.getD());
//        new Wire(d1.getQ(), dec.getA0());
//        new Wire(d1.getQ(), d2.getCP());
//        new Wire(d2.get_Q(), d2.getD());
//        new Wire(d2.getQ(), dec.getA1());
//
//        Port L1 = new Port("L1");
//        Port L2 = new Port("L2");
//        Port L3 = new Port("L3");
//        Port L4 = new Port("L4");
//        new FWire(dec.getY0(), L1);
//        new FWire(dec.getY1(), L2);
//        new FWire(dec.getY2(), L3);
//        new FWire(dec.getY3(), L4);
//        ShowPanel sp = new ShowPanel("4位流水灯");
//        sp.addInPorts(dec.getST(), d1.getCP());
//        sp.addOutPorts(L1, L2, L3, L4);
//        sp.showPanel();
	}
}
