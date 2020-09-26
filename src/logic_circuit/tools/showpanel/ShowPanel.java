package logic_circuit.tools.showpanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import logic_circuit.base.port.Port;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.tools.wavedisplay.PortFilter;
import logic_circuit.tools.wavedisplay.WaveDisplayer;
import logic_circuit.tools.util.CompFactory;
import logic_circuit.tools.util.ThreadPool;
/**
 * 显示面板
 * 通过addInPorts和addOutPorts来添加输入输出端口
 * 通过showPanel方法来显示面板
 * set按钮按下后才可以输入
 */
public class ShowPanel extends JFrame{
	private JPanel pMain, pIn, pOut, pComponent, pBottom, pCenter;
	private JButton power, bSet;//电源,同时输入
    private JMenuBar bar;
    private JMenu mFunc;//功能菜单
    private JMenuItem iDispWave;//查看波形
	private SignalArea s1Hz, s2Hz, s4Hz, s8Hz;//交流信号区域
	private Port[] inPorts;
	private Port[] outPorts;
	private ArrayList<JButton> inShows = new ArrayList<>();
	private ArrayList<JButton> outShows = new ArrayList<>();
	private String name = "";
	private static final Color logic0 = new Color(217, 217, 217);
	private static final Color logic1 = new Color(198, 253, 156);
	private boolean on = false;//通电
	private boolean setable = false;//是否可以设置输入
	public ShowPanel() {
		initPanel();
	}
	public ShowPanel(String name) {
	    this.name = name;
		initPanel();
		setTitle("显示面板 - "+name);
	}
	public void addInPorts(Port ...ports) {
		this.inPorts = ports;
		s1Hz.addPorts(ports);
		s2Hz.addPorts(ports);
		s4Hz.addPorts(ports);
		s8Hz.addPorts(ports);
		//传进来一个端口，用图形化的方式显示其逻辑状态
		for(Port port : ports) {
			JButton show = new JButton(port.getName());
			show.setSize(20, 20);
			show.setBackground(logic0);
			pIn.add(show);
			inShows.add(show);//保存按钮
			port.addPortListener(new PortListener() {
				@Override
				public void portAffected(PortEvent e) {
					if (!on)
						return;
					if (port.getV() == 1) {
						show.setBackground(logic1);
					} else {
						show.setBackground(logic0);
					}
				}
			});
			show.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!on)
						return;
					if(!setable){//set按钮没按下时，不传输
						port.setVOnly(port.getV()==1 ? 0 : 1);
						if(port.getV() == 1) {
							show.setBackground(logic1);
						}else {
							show.setBackground(logic0);
						}
						return;
					}
					port.setV(port.getV()==1 ? 0 : 1);
				}
			});
		}
	}
	public void addOutPorts(Port ...ports) {
		this.outPorts = ports;
		//传进来一个端口，用图形化的方式显示其逻辑状态
		for(Port port : ports) {
			JButton show = new JButton(port.getName());
			show.setSize(20, 20);
			show.setBackground(logic0);
			pOut.add(show);
			outShows.add(show);
			port.addPortListener(new PortListener() {
				@Override
				public void portAffected(PortEvent e) {
					if(!on)
						return;
					if(port.getV() == 1) {
						show.setBackground(logic1);
					}else {
						show.setBackground(logic0);
					}
				}
			});
		}
	}
	public void showPanel() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
        //onSet();//set键一开始时按下的
    }
	private void initPanel() {
		setTitle("显示面板");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pMain = new JPanel();
		pIn = new JPanel();
		pOut = new JPanel();
		pComponent = new JPanel();
		pBottom = new JPanel();
		power = new JButton("电 源");
		bSet = new JButton("set");
        bSet.setBackground(Color.lightGray);
        bSet.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		pIn.add(bSet);
		bar = new JMenuBar();
		mFunc = CompFactory.createMenu("面板功能 F");
		iDispWave = CompFactory.createMenuItem("查看波形");
		bar.setBackground(new Color(207, 236, 234));
		bar.add(mFunc);
		mFunc.add(iDispWave);
        this.setJMenuBar(bar);
		pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(2, 2));
		s1Hz = new SignalArea(500, "1Hz");
		s2Hz = new SignalArea(250, "2Hz");
		s4Hz = new SignalArea(125, "4Hz");
		s8Hz = new SignalArea(63, "8Hz");
		pCenter.add(s1Hz);
		pCenter.add(s2Hz);
		pCenter.add(s4Hz);
		pCenter.add(s8Hz);
		
		power.setBorder(BorderFactory.createRaisedBevelBorder());
		pBottom.add(power);
		power.setBackground(Color.lightGray);
		power.setPreferredSize(new Dimension(90, 35));
		pComponent.setLayout(new BorderLayout());
		pComponent.add(pOut, BorderLayout.NORTH);
		pComponent.add(pIn, BorderLayout.SOUTH);
		pMain.setLayout(new BorderLayout());
		pIn.setLayout(new FlowLayout());
		pOut.setLayout(new FlowLayout());
		pIn.setBorder(BorderFactory.createTitledBorder("输入"));
		pOut.setBorder(BorderFactory.createTitledBorder("输出"));
		pMain.add(pComponent, BorderLayout.NORTH);
		pMain.add(pBottom, BorderLayout.SOUTH);
		pMain.add(pCenter, BorderLayout.CENTER);
		this.add(pMain);
		addListener();
	}
	private void initShows() {
		if(setable) {
			//输入全部设零以初始化
			for (Port port : inPorts) {
				port.setV(0);
			}
		}
		s1Hz.ON();
		s2Hz.ON();
		s4Hz.ON();
		s8Hz.ON();
	}
	private void offShows() {
		//断电
        if(inPorts != null)
            for(Port port:inPorts) {
                port.setVOnly(0);
            }
		if(outPorts != null)
            for(Port port:outPorts) {
                port.setVOnly(0);
            }
        if(inShows != null)
            for(JButton show:inShows) {
                show.setBackground(logic0);
            }
        if(outShows != null)
            for(JButton show:outShows) {
                show.setBackground(logic0);
            }
		s1Hz.OFF();
		s2Hz.OFF();
		s4Hz.OFF();
		s8Hz.OFF();
	}
    /**
     * 取消全部信号
     */
    public void cancelSignals(){
        s1Hz.cancelAll();
        s2Hz.cancelAll();
        s4Hz.cancelAll();
        s8Hz.cancelAll();
    }
    /**
     * 按了电源
     */
	public void onPower(){
        if(on) {
            on = false;
            power.setBorder(BorderFactory.createRaisedBevelBorder());
            power.setBackground(Color.lightGray);
            offShows();
        }else {
            on = true;
            power.setBorder(BorderFactory.createLoweredBevelBorder());
            power.setBackground(Color.pink);
            initShows();
        }
    }
    /**
     * 按了set
     */
    public void onSet(){
        if(setable){
            setable = false;
            bSet.setBackground(Color.lightGray);
            bSet.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            s1Hz.doInput(false);
            s2Hz.doInput(false);
            s4Hz.doInput(false);
            s8Hz.doInput(false);
        }else{
            setable = true;
            bSet.setBackground(Color.white);
            bSet.setBorder(BorderFactory.createLoweredSoftBevelBorder());
            //同时输入
            for(Port port:inPorts) {
                if(port.getV()==-1)//还未初始化的要初始化，相当于通电
                    port.setV(0);
                else
                    port.setV(port.getV());
            }
            s1Hz.doInput(true);
            s2Hz.doInput(true);
            s4Hz.doInput(true);
            s8Hz.doInput(true);
        }
    }
	/**
	 * 监听器
	 */
	private void addListener() {
		power.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
                onPower();
			}
		});
		power.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					power.setBorder(BorderFactory.createLoweredBevelBorder());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(!on)
					power.setBorder(BorderFactory.createRaisedBevelBorder());
				}
			}
		});
		bSet.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                onSet();
            }
		});
		iDispWave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadPool.newThread(new Runnable() {
                    @Override
                    public void run() {
                        PortFilter filter = new PortFilter(inPorts, outPorts);
                        if(filter.showDialog()) {
                            new WaveDisplayer(name, inPorts, filter.getFiltedInPorts(), filter.getFiltedOutPorts());
                        }
                    }
                });
            }
        });
	}
}
