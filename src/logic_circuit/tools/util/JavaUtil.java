package logic_circuit.tools.util;

import logic_circuit.base.port.Port;

import java.io.*;
import java.util.ArrayList;

/**
 * 工具类
 */
public class JavaUtil {
    public static ArrayList arrToList(Object[] arr){
        ArrayList list = new ArrayList();
        for(Object o:arr){
            list.add(o);
        }
        return list;
    }

    public static Port[] toPortArr(ArrayList<Port> list){
        Port[] ports = new Port[list.size()];
        for(int i = 0; i < list.size(); i++){
            ports[i] = list.get(i);
        }
        return ports;
    }

    public static String readAll(File file){
        BufferedReader reader;
        String line, buff = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while((line=reader.readLine()) != null){
                buff += line;
                buff += "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff;
    }

    public static void write(File file, String buff){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(buff);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到有用的数据
     * 如果是()就返回testTime和unitTime
     * 如果是{}就返回状态和列表
     * @param str 大括号
     * @return length为2的数组
     */
    public static String[] getUseful(String str){
        String[] data = new String[2];
        try {

            if(str.contains("(")){
                str = str.replaceAll("^(\\()|(\\))$", "");
                String[] arr = str.split(",");
                String[] arr1 = arr[0].split(":");
                String[] arr2 = arr[1].split(":");

                if(arr1[0].toLowerCase().equals("test_time")){
                    data[0] = arr1[1];
                    data[1] = arr2[1];
                }else{
                    data[1] = arr1[1];
                    data[0] = arr2[1];
                }

            }else if(str.contains("{")) {
                str = str.replaceAll("^(\\{)|(\\})$", "");
                String[] arr = str.split(",");
                String[] arr1 = arr[0].split(":");
                String[] arr2 = arr[1].split(":");

                if(arr1[0].toLowerCase().equals("state")){
                    data[0] = arr1[1];
                    data[1] = arr2.length==2?arr2[1]:"";

                }else{
                    data[1] = arr1[1];
                    data[0] = arr2.length==2?arr2[1]:"";
                }
            }
        }catch (Exception e){
            return null;
        }
        return data;
    }
}
