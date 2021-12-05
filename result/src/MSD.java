import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.*;
import java.util.ArrayList;

public class MSD {

    private static final int R = 128;
    private static String[] aux;
    public static void radixSort(String[] arr){
        aux = new String[arr.length];
        radixSort(arr, 0, arr.length - 1, 0);
    }

    private static void radixSort(String[] arr,int lo, int hi, int d) {
        if(hi <= lo){
            return;
        }
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            count[getCharIndex(arr[i], d) + 2]++;
        }

        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[getCharIndex(arr[i], d) + 1]++] = arr[i];
        }

        for (int i = lo; i <= hi; i++) {
            arr[i] = aux[i - lo];
        }

        for (int r = 0; r < R; r++) {
            radixSort(arr, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }

    }
    //获取字符串第k位字符所对应的ascii码序号
    private static int getCharIndex(String s, int d){

        return d < s.length()
                ? s.charAt(d)
                : -1;
    }
    public static String[] radixpy(String[] arr) throws IOException {
        int length = arr.length;
        String[] eachline = new String[length];
        double xx = 0;

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

        return eachline;
    }

    private static String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;
        }
        return pinyins[0];
    }


    public static void main(String[] args) throws IOException {
        File file = new File("result/resources/shuffledChinese.txt");
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
