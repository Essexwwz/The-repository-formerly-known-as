import edu.neu.coe.huskySort.sort.PinyinComparator;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
@SuppressWarnings("ALL")
public class sortstest {


    public ArrayList<String> prepare() throws Exception{
        File file = new File("E:\\Git\\The-repository-formerly-known-as\\result\\resources\\shuffledChinese.txt");
        String adn;

        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        ArrayList<String> readList = new ArrayList<>();
        BufferedReader reader=new BufferedReader(read);
        while((adn = reader.readLine())!=null){
            readList.add(System.lineSeparator()+adn);
        }
        reader.close();
        return readList;
    }
    @Test
    public void testLSD() throws Exception{
        ArrayList<String> readList = prepare();
        ArrayList<String> readListcopy = new ArrayList<>(readList);
        Comparator<String> c = new PinyinComparator();
        String[]strArr = readList.toArray(new String[0]);
        readListcopy.sort(c);
        String[] py = LSD.radixpy(strArr);
        assertEquals(py[5], LSD.pinyinstr(readListcopy.get(5)));
        assertEquals(py[100], LSD.pinyinstr(readListcopy.get(100)));
        assertEquals(py[1042], LSD.pinyinstr(readListcopy.get(1042)));
        assertEquals(py[24760], LSD.pinyinstr(readListcopy.get(24760)));
        assertEquals(py[509748], LSD.pinyinstr(readListcopy.get(509748)));
    }
    @Test
    public void testMSD() throws Exception{
        ArrayList<String> readList = prepare();
        ArrayList<String> readListcopy = new ArrayList<>(readList);
        Comparator<String> c = new PinyinComparator();
        String[]strArr = readList.toArray(new String[0]);
        readListcopy.sort(c);
        String[] py = MSD.radixpy(strArr);
        assertEquals(py[5], LSD.pinyinstr(readListcopy.get(5)));
        assertEquals(py[100], LSD.pinyinstr(readListcopy.get(100)));
        assertEquals(py[1042], LSD.pinyinstr(readListcopy.get(1042)));
        assertEquals(py[24760], LSD.pinyinstr(readListcopy.get(24760)));
        assertEquals(py[509748], LSD.pinyinstr(readListcopy.get(509748)));
    }


}
