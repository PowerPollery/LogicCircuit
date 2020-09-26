package logic_circuit.tools.wavedisplay;

import logic_circuit.base.port.Port;
import logic_circuit.tools.util.CompFactory;
import logic_circuit.tools.util.JavaUtil;
import logic_circuit.tools.util.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 波形图显示器
 * 此类与SignalCreater、SignalCapturer和WavePainter配合使用
 * 此类是一个窗口，负责让用户键入输入端口的波形，整理成LinkedHashMap(存储了输入端口、时段列表)
 * 初步考虑让用户输入时段，时段用单位时间的倍数表示
 * 将map传给creater，将ports传给capturer，将波形名称和端口数传给painter
 * creater调用init和create方法，开始产生信号;
 * capturer调用start和stop方法捕获测试时间内的信号;
 * painter调用draw方法接收测试时长、捕获结果，并显示出最后的波形
 */
public class WaveDisplayer extends JFrame{
    Port[] inPorts;
    Port[] filtedInPorts;//过滤过的只给painter
    Port[] filtedOutPorts;
    String waveName;//波形图名称
    public static final String sUnit = "500";
    public static final String sTime = "5500";
    private JPanel pCenter, pUnit, pTime, pTU;
    private JButton bGenerate;//生成键
    private JTextField tUnit, tTime;//时间
    private JLabel lUnit, lTime;
    private JPopupMenu pop;
    private JMenuItem iSaveImg, iExport;
    private WaveDisplayer self = this;

    private ArrayList<PortSetting> portSettings = new ArrayList<>();//输入端口
    private SignalCreater creater;//信号生成器
    private SignalCapturer capturer;//信号捕获器
    private WavePainter painter;//波形绘制器

    public WaveDisplayer(String waveName, Port[] inPorts, Port[] filtedInPorts, Port[] filtedOutPorts) {
        this.waveName = waveName;
        this.inPorts = inPorts;
        this.filtedInPorts = filtedInPorts;
        this.filtedOutPorts = filtedOutPorts;

        init();
        initTools();
        addListener();
        addHandler();

        this.setSize(630, (inPorts.length+1)*65+painter.getActlHeight());
        this.setMinimumSize(new Dimension(620, 300));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void init(){
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("波形查看器");

        pCenter = new JPanel();
        pCenter.setLayout(new GridLayout(2+inPorts.length, 1));

        //TU
        pUnit = new JPanel();
        pTime = new JPanel();
        pTU = new JPanel();
        tUnit = CompFactory.createTextField(8);
        tTime = CompFactory.createTextField(8);
        lUnit = CompFactory.createLable("单位时间 (ms)");
        lTime = CompFactory.createLable("测试用时 (ms)");
        pUnit.add(lUnit);
        pUnit.add(tUnit);
        pTime.add(lTime);
        pTime.add(tTime);
        pTU.add(pTime);
        pTU.add(pUnit);
        tTime.setText(sTime);
        tUnit.setText(sUnit);

        pCenter.add(pTU);

        //PORT
        for(Port p:inPorts){
            PortSetting ps = new PortSetting(p);
            portSettings.add(ps);
            pCenter.add(ps);
        }

        //BUTTON
        bGenerate = CompFactory.createButton("生成波形图");
        pCenter.add(bGenerate);

        //POPMENU
        pop = new JPopupMenu();
        iSaveImg = CompFactory.createMenuItem("保存波形图");
        iExport = CompFactory.createMenuItem("导出波形设置");
        pop.add(iSaveImg);
        pop.add(iExport);

        this.add(pCenter, BorderLayout.CENTER);
    }

    private void initTools(){
        capturer = new SignalCapturer(JavaUtil.arrToList(filtedInPorts), JavaUtil.arrToList(filtedOutPorts));
        painter = new WavePainter(waveName, filtedInPorts.length+ filtedOutPorts.length);
        this.add(painter, BorderLayout.SOUTH);
    }

    /**
     * 生成
     */
    private void generate() {
        //数组转换出错的话就不进行
        int testTime, unit;
        try {
            testTime = Integer.parseInt(tTime.getText());
            unit = Integer.parseInt(tUnit.getText());
        }catch (Exception e){
            return;
        }

        LinkedHashMap<Port, ArrayList<Integer>> timeLists = new LinkedHashMap<>();//时段列表

        //添加键值
        for(PortSetting ps:portSettings){
            timeLists.put(ps.getPort(), ps.getList(unit));
        }

        bGenerate.setEnabled(false);
        bGenerate.setText("正在生成波形图，请勿操作显示面板...");

        creater = new SignalCreater(timeLists);
        creater.init(100);
        creater.create();
        capturer.start(testTime);
        painter.draw(testTime, capturer.stop());
        creater.interrupt();

        bGenerate.setEnabled(true);
        bGenerate.setText("生成波形图");

    }

    /**
     * 保存波形图
     */
    private void saveImg(){
        painter.saveImg(this.getX(), this.getY(), this.getHeight());
    }

    /**
     * 添加监听器
     */
    private void addListener(){
        bGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadPool.newThread(new Runnable() {
                    @Override
                    public void run() {
                        generate();
                    }
                });
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3){
                    pop.show(self, e.getX(), e.getY());
                }
            }
        });
        iSaveImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadPool.newThread(new Runnable() {
                    @Override
                    public void run() {
                        saveImg();
                    }
                });
            }
        });
        iExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadPool.newThread(new Runnable() {
                    @Override
                    public void run() {
                        exportSetting();
                    }
                });
            }
        });
    }

    /**
     * 导出数据
     */
    private void exportSetting(){
        String buff = "";
        buff += "(test_time:"+tTime.getText()+", unit_time:"+tUnit.getText()+")\n";
        for(PortSetting ps:portSettings){
            buff +=  "{state:"+ps.getState()+", list:"+ps.getSList()+"}\n";
        }

        JFileChooser chooser = new JFileChooser("C:\\Users\\windows\\Desktop");//默认在桌面
        chooser.setSelectedFile(new File(waveName+"波形设置.wvs"));
        int option = chooser.showDialog(null, "导出");
        if(option == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            //写入文件
            JavaUtil.write(file, buff);
            JOptionPane.showMessageDialog(this, "导出成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * 导入数据
     * @param file
     */
    private void importSetting(File file){
        //文件类型不符
        if(!file.getName().endsWith(".wvs")){
            JOptionPane.showMessageDialog(this, "配置文件错误！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String testTime="", unitTime="";
        ArrayList<Object[]> datas = new ArrayList<>();
        String buff = JavaUtil.readAll(file);
        Matcher m = Pattern.compile("\\([\\s\\S]*?\\)").matcher(buff);
        if(m.find()){
            String[] sTimes = JavaUtil.getUseful(m.group());
            if(sTimes == null) {
                JOptionPane.showMessageDialog(this, "配置文件错误！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            testTime = sTimes[0];
            unitTime = sTimes[1];
        }
        Matcher m1 = Pattern.compile("\\{[\\s\\S]*?\\}").matcher(buff);
        while(m1.find()){
            String[] sData = JavaUtil.getUseful(m1.group());
            if(sData == null) {
                JOptionPane.showMessageDialog(this, "配置文件错误！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Object[] data = new Object[2];
            data[0] = Integer.parseInt(sData[0]);
            data[1] = sData[1];
            datas.add(data);
        }
        //输入端口数量不符
        if(datas.size() != inPorts.length){
            JOptionPane.showMessageDialog(this, "导入的文件配置与端口数量不符！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //设置
        tTime.setText(testTime);
        tUnit.setText(unitTime);
        for(int i = 0; i < datas.size(); i++){
            portSettings.get(i).reset((int)datas.get(i)[0], (String)datas.get(i)[1]);
        }
        JOptionPane.showMessageDialog(this, "导入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 添加拖拽句柄
     */
    private void addHandler(){
        painter.setTransferHandler(new TransferHandler(){
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    //这里加判断是因为实现拖拽功能后再进行粘贴等键盘操作时会出异常，而异常就是UnsupportedDataFlavor
                    if(!t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                        return false;
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
                    //注意list的类型
                    java.util.List list = (java.util.List) o;//文件列表
                    importSetting(new File(list.get(0).toString()));//只取第一个文件
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
                for (int i = 0; i < transferFlavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(transferFlavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

}


