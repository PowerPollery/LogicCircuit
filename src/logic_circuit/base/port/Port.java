package logic_circuit.base.port;

import java.util.ArrayList;

import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.tools.util.ThreadPool;

/**
 * 端口
 */
public class Port {
	private int v = -1;
	private String name = "";
	private ArrayList<PortListener> listeners = new ArrayList<>();
	private boolean stateChage = false;//状态是否变化

	public Port() {}
	public Port(String name) {
		this.name = name;
	}
	public Port(int v) {
		setV(v);
	}
	public Port(int v, String name) {
		this.name = name;
		setV(v);
	}

    /**
     * 为端口添加监听器
     * @param listener 端口监听器
     */
	public void addPortListener(PortListener listener) {
		this.listeners.add(listener);
	}

	//set方法用了同步
	public synchronized void setV(int v) {
		if(v == 1 || v == 0 || v == -1) {//-1代表断电
            stateChage = this.v == v ? false : true;
			this.v = v;
			//广播监听器
			for(PortListener listener : listeners) {
				//每次广播都是单独的线程
				ThreadPool.newThread(new Runnable() {
					public void run() {
						listener.portAffected(new PortEvent(PortEvent.SETV, stateChage));
					}
				});
			}
				
		}
	}
	//不传输的方法
	public synchronized void setVOnly(int v) {
		if(v == 1 || v == 0 || v == -1) {
            stateChage = this.v == v ? false : true;
			this.v = v;
			//广播监听器
			for(PortListener listener : listeners) {
				//每次广播都是单独的线程
				ThreadPool.newThread(new Runnable() {
					public void run() {
						listener.portAffected(new PortEvent(PortEvent.SETV_ONLY, stateChage));
					}
				});
			}
		}
	}
	public int getV() {
		return this.v;
	}

	@Override
	public String toString() {
		return name + ":" + getV();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

