package logic_circuit.tools.wavedisplay;

import logic_circuit.base.port.Port;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 信号产生器
 * 负责为传入的端口产生信号
 * 根据的是传入的时序列表(第一个为初值，其他的为时间段而不是时间点)
 */
public class SignalCreater {
    LinkedHashMap<Port, ArrayList<Integer>> timeLists = new LinkedHashMap<>();
    private boolean started = false;

    public SignalCreater(LinkedHashMap timeLists){
        this.timeLists = timeLists;
    }

    /**
     * 初始化 - 为端口设置初值
     * 如果第一遍波形不对，可能是delay值太小了
     * @param delay 延迟值，确保init完全
     */
    public void init(int delay){
        for(Map.Entry<Port, ArrayList<Integer>> e : timeLists.entrySet()){
            Port p = e.getKey();
            ArrayList<Integer> list = e.getValue();
            p.setV(list.get(0));
        }
        try {
            Thread.sleep(delay);//确保init完了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始产生信号
     */
    public void create(){
        started = true;
        for(Map.Entry<Port, ArrayList<Integer>> e : timeLists.entrySet()){
            Port p = e.getKey();
            ArrayList<Integer> list = e.getValue();
            new Thread(){
                @Override
                public void run() {
                    for(int i = 1; i < list.size(); i++){
                        try {
                            if(!started)
                                break;
                            Thread.sleep(list.get(i));
                        } catch (InterruptedException e1) { e1.printStackTrace(); }
                        p.setV(p.getV()==1?0:1);//翻转
                    }
                }
            }.start();
        }
    }
    /**
     * 中断产生信号
     */
    public void interrupt(){
        started = false;
    }
}











