package logic_circuit.tools.decoder;

import logic_circuit.base.port.Port;

import javax.swing.*;
import java.awt.*;

/**
 * 7段显示器
 */
public class S7Displayer extends JFrame{
    private S7Panel p = new S7Panel();

    public S7Displayer(){
        setSize(500, 500);
        setLayout(new FlowLayout());
        add(p);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public Port getA0(){
        return p.getA0();
    }
    public Port getA1(){
        return p.getA1();
    }
    public Port getA2(){
        return p.getA2();
    }
    public Port getA3(){
        return p.getA3();
    }
}
