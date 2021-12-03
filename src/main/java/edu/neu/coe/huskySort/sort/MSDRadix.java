package edu.neu.coe.huskySort.sort;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.*;
import java.util.ArrayList;

public class MSDRadix{
    //ascii码的取值范围
    private static final int ASCII_RANGE = 128;

    public static void radixSort(String[] arr) {
        //元素最长位数
        int maxLength = 21;
        /*
        for (String s : arr) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }

         */
        //排序结果数组
        String[] sortedArr = new String[arr.length];

        //从个位开始比较，一直比较到最高位
        for(int k = 0;k >= maxLength-1;k++) {
            int[] countArr = new int[ASCII_RANGE];
            for (String s : arr) {
                int index = getCharIndex(s, k);
                countArr[index]++;

            }
            //统计数组变换
            int sum = 0;
            for(int i = 0;i < countArr.length;i++) {
                sum += countArr[i];
                countArr[i] = sum;
            }
            //倒序遍历原始数列，进行排序
            for(int i = arr.length-1;i >= 0;i--) {
                int index = getCharIndex(arr[i],k);
                sortedArr[countArr[index]-1] = arr[i];
                countArr[index]--;

            }
            //把每一轮的结果复制给arr
            arr = sortedArr.clone();
        }
    }
    //获取字符串第k位字符所对应的ascii码序号
    private static int getCharIndex(String str, int k){
        //w位数不足的位置补0
        if(str.length() < k+1){
            return 0;
        }
        return str.charAt(k);
    }
    public static void radixpy(String[] arr) {
        int length = arr.length;
        String[] eachline = new String[length];



        //true = append file


        for (int i = 0; i < length; i++) {
            StringBuilder y = new StringBuilder();
            for(int k = 2; k<5; k++) {
                if (k>=arr[i].length()) break;
                String x = pinyin(arr[i].charAt(k));
                y.append(x);

            }
            eachline[i]  = y.toString();

        }

        radixSort(eachline);
    }

    private static String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;
        }
        return pinyins[0];
    }


    public static void main(String[] args) throws IOException {
        File file = new File("E:/Chrome/shuffledChinese.txt");
        String adn;

        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        ArrayList<String> readList = new ArrayList<>();
        BufferedReader reader=new BufferedReader(read);
        while((adn = reader.readLine())!=null){
            readList.add(System.lineSeparator()+adn);
        }
        reader.close();
        String[]strArr = readList.toArray(new String[0]);
        radixpy(strArr);

    }
}

