package logic_circuit.tools.wavedisplay;

import logic_circuit.base.port.Port;
import logic_circuit.tools.util.CompFactory;

import javax.swing.*;
import java.util.ArrayList;

/**
 * 设置端口的波形时段
 */
public class PortSetting extends JPanel{
    private JLabel lName, lState, lTimes;
    private JComboBox<Integer> cState;//初始状态
    private JTextField tTimes;
    Port port;

    public PortSetting(Port port){
        this.port = port;

        lName = CompFactory.createLable(port.getName());
        lState = CompFactory.createLable("初始状态:");
        cState = CompFactory.createComboBox(0, 1);
        lTimes = CompFactory.createLable("时段列表:");
        tTimes = CompFactory.createTextField(20);

        lName.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        this.add(lName);
        this.add(lState);
        this.add(cState);
        this.add(lTimes);
        this.add(tTimes);
    }

    public int getState(){
        return cState.getSelectedIndex();//只有0、1，所以下标可以
    }
    public String getSList(){
        return tTimes.getText();
    }

    public ArrayList<Integer> getList(int unitTime){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(getState());//初始状态
        String[] arr = tTimes.getText().split("[,， ]");
        for(int i = 0; i < arr.length; i++){
            try {
                list.add((int) (Double.parseDouble(arr[i]) * unitTime));
            }catch (Exception e){
                break;//出错的话，后面的就失效了
            }
        }
        return list;
    }

    public Port getPort() {
        return port;
    }

    /**
     * 重置
     */
    public void reset(int state, String list){
        cState.setSelectedIndex(state);
        tTimes.setText(list);
    }
}
