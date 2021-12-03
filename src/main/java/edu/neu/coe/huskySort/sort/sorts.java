package edu.neu.coe.huskySort.sort;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class sorts {


    public static void test(String filename, int n) throws IOException{
        File file = new File(filename);
        String adn;

        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        ArrayList<String> readList = new ArrayList<>();
        BufferedReader reader=new BufferedReader(read);
        while(n>0&&(adn = reader.readLine())!=null){
            readList.add(System.lineSeparator()+adn);
            n--;
        }
        reader.close();
        Comparator<String> c = new PinyinComparator();
        String[]strArr = readList.toArray(new String[0]);
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        double startTime;
        double endTime1;
        double endTime2;
        double endTime3;
        for(int i =0; i<5;i++) {
            startTime = System.currentTimeMillis();
            LSDRadix.radixpy(strArr);
            endTime1 = System.currentTimeMillis();
            Arrays.sort(strArr.clone(), c);//Timsort
            endTime2 = System.currentTimeMillis();
            MSDRadix.radixpy(strArr);
            endTime3 = System.currentTimeMillis();
            x1 += endTime1 - startTime;
            x2 += endTime2 - endTime1;
            x3 += endTime3 - endTime2;
        }




        System.out.println(x2/5);
        System.out.println(x1/5);
        System.out.println(x3/5);

    }




    public static void main(String[] args) throws IOException {

        System.out.println("**********************");
        test("E:\\Git\\The-repository-formerly-known-as\\src\\main\\resources\\shuffledChinese.txt",64000);
        System.out.println("**********************");
        test("E:\\Git\\The-repository-formerly-known-as\\src\\main\\resources\\shuffledChinese2m.txt",64000);
    }
}
