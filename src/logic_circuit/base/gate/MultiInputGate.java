package logic_circuit.base.gate;
import java.util.ArrayList;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.port.Port;
/**
 * 多输入门抽象类
 * 可以刻意设置传输延迟时间
 */
public abstract class MultiInputGate{
	protected ArrayList<Port> inPorts;	//输入端口
	private int nextIndex = 0;//自动记录下一个端口下标，调用者只管接，不管下标
	public Port outPort;	//输出端口
	private boolean isNot = false;		//是否转非
	private int delay = 0;//传输延迟时间
	public MultiInputGate(boolean transformTo) {//转换类型，true为X门，false为X非门
		init(2, transformTo);//默认为两个端口
		addListener();
	}
	public MultiInputGate(int n, boolean transformTo) {
		init(n, transformTo);
		addListener();
	}
	public MultiInputGate(boolean transformTo, int delay) {//设置传输延迟
		this.delay = delay;
		init(2, transformTo);//默认为两个端口
		addListener();
	}
	public MultiInputGate(int n, boolean transformTo, int delay) {
		this.delay = delay;
		init(n, transformTo);
		addListener();
	}

	/**
	 * 初始化
	 * @param n 端口数
	 * @param transformTo 是否转非
	 */
	private void init(int n, boolean transformTo) {
		reset();
		isNot = !transformTo;
		//输入端口数
		inPorts = new ArrayList<Port>(n);
		for(int i = 0; i < n; i++) {
			inPorts.add(new Port());
		}
	}


	/**
	 * 注册监听器
	 * 监听输入端，一旦有端口变化，则立刻反应
	 * 与导线配合，实现所有端口的监听，从而实现联动
	 */
	protected void addListener() {
		for(Port p : inPorts) {
			p.addPortListener(new PortListener() {
				@Override
				public void portAffected(PortEvent e) {
					if(e.getType().equals(PortEvent.SETV))
						react();
				}
			});
		}
	}
	/**
	 * 需要重写的方法 - 具体逻辑
	 * 实现类需要根据自己的端口情况返回输出结果： 0或1(不考虑非)
	 * @return 0/1
	 */
	protected abstract int logic();
	/**
	 * 	对变化的反应
	 */
	protected void react() {
		int result = logic();
		//传输延迟
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) { e.printStackTrace(); }
		//输出
		if(!isNot) {
			outPort.setV(result);
		}else {
			outPort.setV(result==1 ? 0 : 1);
		}
	}
	/**
	 * 输入多个数据
	 * @param ins 任意个数据，但不要超过端口数
	 */
	public void input(int ...ins) {
		if(this.inPorts == null)
			return;
		for(int i = 0; i < ins.length && i < inPorts.size(); i++) {
			inPorts.get(i).setVOnly(ins[i]);//这里全部设置完了再反应，模拟同时输入
		}
		react();
	}
	/**
	 * 输入一个数据
	 * @param index
	 * @param value
	 */
	public void set(int index, int value) {
		if(this.inPorts == null)
			return;
		this.inPorts.get(index).setV(value);
	}
	
	/**
	 * 得到输入端口
	 * @param index 端口下标
	 * @return 对应端口
	 */
	public Port inPort(int index) {
		nextIndex = index+1;
		return this.inPorts.get(index);
	}
	/**
	 * 为了简化代码，这个方法将对nextIndex进行自加一。之前的类可以不用换方法
	 * @return 没有连接过的端口
	 */
	public Port inPort(){
		if(nextIndex >= inPorts.size()){
            try {
                throw new Exception("多输入门 - 试图得到不存在的端口：index:"+nextIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
		}
		return this.inPorts.get(nextIndex++);
	}
	
	/**
	 * 重置
	 */
	public void reset() {
		this.inPorts = null;
		outPort = new Port();
	}

}
