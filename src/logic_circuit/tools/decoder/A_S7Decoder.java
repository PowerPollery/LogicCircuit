package logic_circuit.tools.decoder;

import logic_circuit.base.event.PortEvent;
import logic_circuit.base.event.PortListener;
import logic_circuit.base.port.Port;

/**
 * 模拟7段译码器
 * 由于连电路图不成功，所以只能模拟功能
 */
public class A_S7Decoder {
    private Port A0 = new Port("A0"),
                A1 = new Port("A1"),
                A2 = new Port("A2"),
                A3 = new Port("A3"),
                Ya = new Port("Ya"),
                Yb = new Port("Yb"),
                Yc = new Port("Yc"),
                Yd = new Port("Yd"),
                Ye = new Port("Ye"),
                Yf = new Port("Yf"),
                Yg = new Port("Yg");

    public A_S7Decoder(){
        addListener(A0);
        addListener(A1);
        addListener(A2);
        addListener(A3);
    }

    public void input(int a3, int a2, int a1, int a0){
        A0.setV(a0);
        A1.setV(a1);
        A2.setV(a2);
        A3.setV(a3);
    }

    private void addListener(Port p){
        p.addPortListener(new PortListener() {
            @Override
            public void portAffected(PortEvent e) {
                if(e.getType().equals(PortEvent.SETV_ONLY))
                    return;
                String input = getInput();
                String output = "";
                //译码
                switch (input){
                    case "0000":
                        output = "1111110";
                        break;
                    case "0001":
                        output = "0110000";
                        break;
                    case "0010":
                        output = "1101101";
                        break;
                    case "0011":
                        output = "1111001";
                        break;
                    case "0100":
                        output = "0110011";
                        break;
                    case "0101":
                        output = "1011011";
                        break;
                    case "0110":
                        output = "1011111";
                        break;
                    case "0111":
                        output = "1110000";
                        break;
                    case "1000":
                        output = "1111111";
                        break;
                    case "1001":
                        output = "1111011";
                        break;
                }
                setOutput(output);
            }
        });
    }
    private String getInput(){
        String input = "";
        input += A3.getV()==1?1:0;
        input += A2.getV()==1?1:0;
        input += A1.getV()==1?1:0;
        input += A0.getV()==1?1:0;
        return input;
    }
    private void setOutput(String input){
        for(int i = 0; i < input.length(); i++){
            char ch = input.charAt(i);
            switch (i){
                case 0:
                    Ya.setV(ch=='1'?1:0);
                    break;
                case 1:
                    Yb.setV(ch=='1'?1:0);
                    break;
                case 2:
                    Yc.setV(ch=='1'?1:0);
                    break;
                case 3:
                    Yd.setV(ch=='1'?1:0);
                    break;
                case 4:
                    Ye.setV(ch=='1'?1:0);
                    break;
                case 5:
                    Yf.setV(ch=='1'?1:0);
                    break;
                case 6:
                    Yg.setV(ch=='1'?1:0);
                    break;
            }
        }
    }

    public Port getA0() {
        return A0;
    }

    public Port getA1() {
        return A1;
    }

    public Port getA2() {
        return A2;
    }

    public Port getA3() {
        return A3;
    }

    public Port getYa() {
        return Ya;
    }

    public Port getYb() {
        return Yb;
    }

    public Port getYc() {
        return Yc;
    }

    public Port getYd() {
        return Yd;
    }

    public Port getYe() {
        return Ye;
    }

    public Port getYf() {
        return Yf;
    }

    public Port getYg() {
        return Yg;
    }
}
