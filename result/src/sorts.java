import edu.neu.coe.huskySort.sort.LSDRadix;
import edu.neu.coe.huskySort.sort.MSDRadix;
import edu.neu.coe.huskySort.sort.PinyinComparator;

import java.io.*;
import java.util.*;

public class sorts {




    public static void test(String filename, int n) throws IOException{
        File file = new File(filename);
        int m = n;
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
        DualPivotQuickSort dp = new DualPivotQuickSort();
        String[]strArr = readList.toArray(new String[0]);
        ArrayList<String> readListcp = new ArrayList<>(readList);
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        double x4 = 0;
        double startTime;
        double endTime1;
        double endTime2;
        double endTime3;
        double endTime4;
        for(int i =0; i<5;i++) {
            startTime = System.currentTimeMillis();
            LSD.radixpy(strArr);
            endTime1 = System.currentTimeMillis();
            Arrays.sort(strArr.clone(), c);//Timsort
            endTime2 = System.currentTimeMillis();
            MSD.radixpy(strArr);
            endTime3 = System.currentTimeMillis();
            dp.DPQS(strArr.clone(),0,strArr.length-1);//DPQS
            endTime4 = System.currentTimeMillis();
            x1 += endTime1 - startTime;
            x2 += endTime2 - endTime1;
            x3 += endTime3 - endTime2;
            x4 += endTime4 - endTime3;
            readList.clear();
            readList.addAll(readListcp);
        }




        System.out.println("When n is "+ m+", the average time taken for five Timsorts (mSec): "+x2/5);
        System.out.println("When n is "+ m+", the average time taken for five LSD Radix Sorts (mSec): "+x1/5);
        System.out.println("When n is "+ m+", the average time taken for five Dual-pivot quicksorts (mSec): "+x4/5);
        System.out.println("When n is "+ m+", the average time taken for five MSD Radix Sorts (mSec): "+x3/5);

    }



    public static void main(String[] args) throws IOException {

        System.out.println("**********************");
        test("result/resources/shuffledChinese.txt",  250000);
        System.out.println("**********************");
        test("result/resources/shuffledChinese.txt",  500000);
        System.out.println("**********************");
        test("result/resources/shuffledChinese.txt",  1000000);
        System.out.println("**********************");
        test("result/resources/shuffledChinese2m.txt",2000000);
        System.out.println("**********************");
        test("result/resources/shuffledChinese4m.txt",4000000);
    }
}

