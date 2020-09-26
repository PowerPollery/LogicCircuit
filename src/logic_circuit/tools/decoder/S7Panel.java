package logic_circuit.tools.decoder;

import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.port.Port;
import logic_circuit.tools.util.CompFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 7段显示区域
 */
public class S7Panel extends JPanel{
    public static final int S = 15;
    public static final int L = 100;
    public static final int P = 15;
    public static final Color logic0 = new Color(224, 233, 218);
    public static final Color logic1 = new Color(244, 68, 51);
    private A_S7Decoder s7 = new A_S7Decoder();

    private JButton a = CompFactory.createButton1(""),
                     b = CompFactory.createButton1(""),
                     c = CompFactory.createButton1(""),
                     d = CompFactory.createButton1(""),
                     e = CompFactory.createButton1(""),
                     f = CompFactory.createButton1(""),
                     g = CompFactory.createButton1("");

    public S7Panel(){
        setLayout(null);
        setPreferredSize(new Dimension(2*S+L, 3*S+2*L));
        setBackground(Color.white);
        a.setBounds(S, 0, L,S);
        b.setBounds(S+L, S, S,L);
        c.setBounds(S+L, P+S+L, S,L);
        d.setBounds(S, P+S+L*2, L,S);
        e.setBounds(0, P+S+L, S,L);
        f.setBounds(0, S, S,L);
        g.setBounds(S, L+P, L,S);

        add(a);
        add(b);
        add(c);
        add(d);
        add(e);
        add(f);
        add(g);
        a.setBackground(logic0);
        b.setBackground(logic0);
        c.setBackground(logic0);
        d.setBackground(logic0);
        e.setBackground(logic0);
        f.setBackground(logic0);
        g.setBackground(logic0);

        addListener(s7.getYa(), a);
        addListener(s7.getYb(), b);
        addListener(s7.getYc(), c);
        addListener(s7.getYd(), d);
        addListener(s7.getYe(), e);
        addListener(s7.getYf(), f);
        addListener(s7.getYg(), g);
    }

    private void addListener(Port p, JButton show){
        p.addPortListener(new PortListener() {
            @Override
            public void portAffected(PortEvent e) {
                if(p.getV() == 1){
                    show.setBackground(logic1);
                }else{
                    show.setBackground(logic0);
                }
            }
        });
    }

    public Port getA0(){
        return s7.getA0();
    }
    public Port getA1(){
        return s7.getA1();
    }
    public Port getA2(){
        return s7.getA2();
    }
    public Port getA3(){
        return s7.getA3();
    }
}
