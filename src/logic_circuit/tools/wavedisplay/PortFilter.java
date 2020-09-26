package logic_circuit.tools.wavedisplay;

import logic_circuit.base.port.Port;
import logic_circuit.tools.util.CompFactory;
import logic_circuit.tools.util.JavaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * 端口过滤器
 * 让用户选择需要查看的端口
 */
public class PortFilter extends JDialog{
    Port[] inPorts, outPorts;
    private JPanel pIn, pOut;
    private JButton bGo;
    private ArrayList<JCheckBox> boxs = new ArrayList<>();
    private ArrayList<Port> filtedInPorts = new ArrayList<>();
    private ArrayList<Port> filtedOutPorts = new ArrayList<>();
    private boolean go = false;//是否继续

    public PortFilter(Port[] inPorts, Port[] outPorts){
        this.inPorts = inPorts;
        this.outPorts = outPorts;
        setModal(true);
        setTitle("选择要查看的端口");

        pIn = new JPanel();
        pOut = new JPanel();
        pIn.setBorder(BorderFactory.createTitledBorder("输入端口"));
        pOut.setBorder(BorderFactory.createTitledBorder("输出端口"));
        bGo = CompFactory.createButton("继续");

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(pOut, BorderLayout.NORTH);
        this.add(pIn, BorderLayout.CENTER);
        this.add(bGo, BorderLayout.SOUTH);

        for(int i = 0; i < outPorts.length; i++){
            JCheckBox box = new JCheckBox(outPorts[i].getName());
            box.setSelected(true);
            boxs.add(box);
            pOut.add(box);
        }
        for(int i = 0; i < inPorts.length; i++){
            JCheckBox box = new JCheckBox(inPorts[i].getName());
            box.setSelected(true);
            boxs.add(box);
            pIn.add(box);
        }

        addListener();

    }

    public boolean showDialog(){
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        return go;
    }

    private void addListener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                go = false;
            }
        });
        bGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < boxs.size(); i++){
                    if(i < outPorts.length){
                        if(boxs.get(i).isSelected()){
                            filtedOutPorts.add(outPorts[i]);
                        }
                    }else{
                        if(boxs.get(i).isSelected()){
                            filtedInPorts.add(inPorts[i-outPorts.length]);
                        }
                    }
                }
                go = true;
                dispose();
            }
        });
    }

    public Port[] getFiltedInPorts(){
        return JavaUtil.toPortArr(filtedInPorts);
    }

    public Port[] getFiltedOutPorts(){
        return JavaUtil.toPortArr(filtedOutPorts);
    }
}
