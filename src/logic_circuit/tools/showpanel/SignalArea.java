package logic_circuit.tools.showpanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logic_circuit.base.port.Port;

/**
 * 模拟交流信号区域
 */
class SignalArea extends JScrollPane{
	private int sleepTime;
	private HashMap<JCheckBox, Thread> signalThreads = new HashMap<>();
	private HashMap<JCheckBox, Port> ports = new HashMap<>();
	private JPanel main;
	private boolean on = false;//通电
	private boolean input = true;//是否真的输入
	int hight = 110;
	
	public SignalArea(int sleepTime, String name) {
		this.sleepTime = sleepTime;
		this.setBorder(BorderFactory.createTitledBorder(name));
		
		main = new JPanel();
		main.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setPreferredSize(new Dimension(135, hight));
	
        this.setViewportView(main);
        this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	public void addPorts(Port ...ports) {
		for(Port port:ports) {
			JCheckBox check = new JCheckBox(port.getName());
			this.main.add(check);
			this.ports.put(check, port);//加入到端口表里
			check.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chosen(check);//哪一个被点了
				}
			});
		}
		main.setPreferredSize(new Dimension(40*ports.length/2, hight));//实时变化
	}
	//选择之后
	private void chosen(JCheckBox check) {
		if(!on) {// - 电源关时
			if(check.isSelected())
				signalThreads.put(check, null);//信号端口与线程对应
			else
				signalThreads.remove(check);//通过点的方式就移除取消的键值对
			return;
		}
		// - 电源开时
		if(check.isSelected()){
			//要开
			Thread signal = new Thread() {
				@Override
				public void run() {
					Port temp = ports.get(check);
					while(true) {
						if(input)
							temp.setV(temp.getV()==1?0:1);//交替
						else{
							//只显示灯，不输入
							temp.setVOnly(temp.getV()==1?0:1);
						}
						try {Thread.sleep(sleepTime);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
				}
			};
			signalThreads.put(check, signal);//信号端口与线程对应
			signal.start();
		}else {
			//要关
			shutdown(check);
			signalThreads.remove(check);//通过点的方式就移除取消的键值对
		}
	}
	//关闭信号
	private void shutdown(JCheckBox check){
		Thread signal = signalThreads.get(check);//得到正在运行的线程
		signal.stop();
		if(input)
			ports.get(check).setV(0);//设为0
		else
			ports.get(check).setVOnly(0);
		//不取消键值对
	}

	//输入与否
	public void doInput(boolean input){
		this.input = input;
	}
	
	//开关 - 但不取消
	public void ON() {
		this.on = true;

		for(Map.Entry<JCheckBox, Thread> e : signalThreads.entrySet()) {
			chosen(e.getKey());
		}
	}
	public void OFF() {
		this.on = false;

		for(Map.Entry<JCheckBox, Thread> e : signalThreads.entrySet()) {
			shutdown(e.getKey());
		}
	}
	//全部取消
    public void cancelAll(){
        for(Map.Entry<JCheckBox, Port> e : ports.entrySet()) {
            if(e.getKey().isSelected()){
                chosen(e.getKey());
                e.getKey().setSelected(false);
            }
        }
    }
}




