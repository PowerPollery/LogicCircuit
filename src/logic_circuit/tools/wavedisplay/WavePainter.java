package logic_circuit.tools.wavedisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 波形绘制器 - JPanel
 * 从外部传入一个HashMap，键为端口名称，值为一个时序列表(记录着初始值和一系列翻转时间点)
 * 时间段除以时间总长度(一般为5个半CP周期)，得到该时间段所占总数的比
 * panel中指定宽度 X 上述结果 = 端口维持某一状态的波形线段长
 */
class WavePainter extends JPanel {
    public static final int INITIAL_X = 20;//画笔初始位置
    public static final int INITIAL_Y = 50;
    public static final int INTERVAL_LEFT = 50;//名称与波形间距
    public static final int WAVE_WIDTH = 50;//波形的宽度
    public static final int INTERVAL = 20;//波形的间隔
    public static final int PANEL_WIDTH = 650;//面板的宽度
    //LinkedHashMap会按照加入的顺序遍历
    private LinkedHashMap<String, ArrayList<Integer>> waveDatas;//时序列表，记录初始状态和翻转点
    private LinkedHashMap<String, ArrayList<Integer>> waveforms;//波形列表，记录波形初始状态和每个时段的长度
    private int posX = INITIAL_X;//画笔位置(不停变化)
    private int posY = INITIAL_Y;
    private int testTime;//测试总用时
    private int portCnt;//端口数量
    private String name;//波形图名称

    public WavePainter(String name, int portCnt){
        this.name = name;
        this.portCnt = portCnt;
        setPreferredSize(new Dimension(PANEL_WIDTH, getActlHeight()));
        setBorder(BorderFactory.createTitledBorder(name+"波形图"));

    }

    //得到实际高度
    public int getActlHeight(){
        return 2*INITIAL_Y+portCnt*WAVE_WIDTH+(portCnt-1)*INTERVAL;
    }
    /**
     * 开始画
     */
    public void draw(int testTime, LinkedHashMap waveDatas){
        this.testTime = testTime;
        this.waveDatas = waveDatas;

        repaint();
    }

    /**
     * 画图前的准备工作
     * 初始化了posX和poxY
     * 将waveDatas处理后创建waveforms供painter使用
     */
    private void prepare(){
        posX = INITIAL_X;
        posY = INITIAL_Y;
        waveforms = new LinkedHashMap<>();

        //没传进来波形数据
        if(waveDatas == null)
            return;

        //总宽度
        int totalWidth = this.getWidth()- INTERVAL_LEFT *2;

        for(Map.Entry<String, ArrayList<Integer>> e : waveDatas.entrySet()){
            String portName = e.getKey();
            ArrayList<Integer> waveData = e.getValue();
            ArrayList<Integer> waveform = new ArrayList<>();

            waveform.add(waveData.get(0));//初始状态
            if(waveData.size() > 1)
                waveform.add((int)((1.0 * waveData.get(1))/testTime * totalWidth));//相当于第一个 - 0
            int size = waveData.size();
            for(int i = 1; i < size; i++){
                if(i+1 < size) {
                    //一段时间占总时之比 * 宽度
                    waveform.add((int) (1.0 * (waveData.get(i + 1) - waveData.get(i)) / testTime * totalWidth));
                }
            }
            //最后剩下的
            waveform.add((int)(1.0*(testTime-waveData.get(size-1))/testTime * totalWidth));
            waveforms.put(portName, waveform);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //背景图片
        ImageIcon img = new ImageIcon(WavePainter.class.getResource("/logic_circuit/tools/imgs/back.jpg"));
        g.drawImage(img.getImage(), 0, 0, getWidth(), getActlHeight(), img.getImageObserver());
        //准备工作
        prepare();
        //开始画
        for(Map.Entry<String, ArrayList<Integer>> e : waveforms.entrySet()){
            String portName = e.getKey();
            ArrayList<Integer> waveform = e.getValue();

            //画名称
            g.setFont(new Font("Arial", 0, 18));
            g.drawString(portName, posX, posY+30);
            //画波形
            int state = waveform.get(0);
            posX = INITIAL_X + INTERVAL_LEFT;
            for(int i = 1; i < waveform.size(); i++){
                if(state == 0){
                    state = 1;
                    posY += WAVE_WIDTH;
                    g.drawLine(posX,posY,posX+waveform.get(i),posY);
                    posX += waveform.get(i);
                    if(i != waveform.size()-1){
                        //这个地方(包括下面)是为了消除短暂尖峰的现象：如果此段或者下一段长度是0，则不画竖线
                        if(!(i+1<waveform.size() && (waveform.get(i+1) == 0 || waveform.get(i)==0))) {
                            g.drawLine(posX, posY, posX, posY - WAVE_WIDTH);
                        }
                    }
                }else if(state == 1){
                    state = 0;
                    if(i != 1)//初始的时候posY就是1的位置
                        posY -= WAVE_WIDTH;
                    g.drawLine(posX,posY,posX+waveform.get(i),posY);
                    posX += waveform.get(i);
                    if(i != waveform.size()-1){
                        if(!(i+1<waveform.size() && (waveform.get(i+1) == 0 || waveform.get(i)==0))){
                            g.drawLine(posX, posY, posX, posY + WAVE_WIDTH);
                        }
                    }
                }
            }
            if(state == 0) {//该画0了，说明最后状态在1
                //保证画笔结束后的位置在 0 上
                posY += WAVE_WIDTH;
            }

            posX = INITIAL_X;//X方向归位
            posY += INTERVAL;//Y方向间隔
        }
    }


    /**
     * 保存波形图
     * @param x 窗口x
     * @param y 窗口y
     * @param h 窗口高
     */
    public void saveImg(int x, int y, int h){
        try {
            //截屏
            BufferedImage screenshot = new Robot().createScreenCapture(
                    new Rectangle(x+8, y+h-this.getHeight()-8, this.getWidth(), this.getHeight())
            );

            //选择路径
            JFileChooser chooser = new JFileChooser("C:\\Users\\windows\\Desktop");//默认在桌面
            chooser.setSelectedFile(new File(name+"波形图.png"));
            int option = chooser.showDialog(null, "保存波形图");
            if(option == JFileChooser.APPROVE_OPTION){

                File file = chooser.getSelectedFile();
                //写入文件
                ImageIO.write(screenshot, "png", file);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}