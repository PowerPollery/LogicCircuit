package logic_circuit.tools.wavedisplay;
import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.port.Port;
import java.util.ArrayList;
import java.util.LinkedHashMap;
/**
 * 信号捕获器
 * 侦听传进来的端口，记录从start到stop时段内各个端口的信号初始值和翻转时间点
 * 生成LinkedHashMap - waveDatas，并在调用stop时返回
 */
public class SignalCapturer {
    private ArrayList<Port> ports = new ArrayList<>();
    private LinkedHashMap<String, ArrayList<Integer>> waveDatas;
    private long startTime;
    private boolean started = false;//是否开始捕获
    public SignalCapturer(ArrayList ...portsLists){
        for(ArrayList list:portsLists){
            this.ports.addAll(list);
        }
        addListener();
    }
    private void addListener(){
        for(Port p:ports){
            p.addPortListener(new PortListener() {
                @Override
                public void portAffected(PortEvent e) {
                    if(started){
                        if(e.isStateChage()) {//状态改变了
                            long time = System.currentTimeMillis();//获取当前时间
                            waveDatas.get(p.getName()).add((int) (time - startTime));
                        }
                    }
                }
            });
        }
    }
    /**
     * 创建所有端口的时序列表并将第一个设为初始状态
     */
    private void prepare(){
        waveDatas = new LinkedHashMap<>();
        for(Port p : ports){
            ArrayList<Integer> list = new ArrayList<>();
            list.add(p.getV());//第一个是初始值
            waveDatas.put(p.getName(), list);
        }
    }
    /**
     * 开始捕获
     * 要停止捕获请调用stop方法
     * @param testTime 停滞时长
     */
    public void start(int testTime){
        prepare();//start之前要确保输入端口都被赋了初值
        startTime = System.currentTimeMillis();
        started = true;
        try {
            Thread.sleep(testTime);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
    /**
     * 停止捕获
     * 必须确保add方法已经执行完了，目前可以用延迟的办法，但并不好
     * @return 返回捕获结果
     */
    public LinkedHashMap stop(){
        started = false;
        return waveDatas;
    }
}
