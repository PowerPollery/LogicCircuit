package logic_circuit.tools.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 组件工厂
 */
public class CompFactory extends JLabel {
    private static Font font = new Font("微软雅黑", 0, 18);
    private static Font font2 = new Font("微软雅黑", 0, 15);
    private static Color color1 = new Color(87, 126, 185);
    private static Color color2 = new Color(96, 96, 96);
    private static Color color3 = new Color(197, 206, 192);

    public static JButton createButton(String name){
        JButton button = new JButton(name);
        button.setFont(font);
        button.setBackground(color3);
        return button;
    }
    public static JButton createButton1(String name){
        JButton button = new JButton(name);
        button.setFont(font);
        button.setBorder(null);
        return button;
    }

    public static JLabel createLable(String name){
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setForeground(color2);
        return label;
    }

    public static JTextField createTextField(int cols){
        JTextField field = new JTextField(cols);
        field.setFont(font);
        field.setForeground(color1);
        field.addFocusListener(new FocusListener() {//自动全选
            @Override
            public void focusGained(FocusEvent e) {
                field.selectAll();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        return field;
    }

    public static JComboBox<Integer> createComboBox(int ...states){
        JComboBox<Integer> box = new JComboBox<>();
        for(int s : states){
            box.addItem(s);
        }
        box.setFont(font);

        return box;
    }

    public static JMenu createMenu(String name){
        JMenu menu = new JMenu(name);
        menu.setFont(font2);
        return menu;
    }

    public static JMenuItem createMenuItem(String name){
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setFont(font2);
        return menuItem;
    }
}
