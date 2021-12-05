import edu.neu.coe.huskySort.sort.PinyinComparator;

import java.util.Comparator;

public class DualPivotQuickSort {
    public static void DPQS(String[] a,int l, int r){
        Comparator<String> c = new PinyinComparator();
        if(r -l>=1){
            String p1;
            String p2;
            if(c.compare(a[r],a[l])>=0){p1 = a[l]; p2=a[r];}
            else {p1 = a[r];p2 = a[l];}
            int st = l +1;
            int end= r-1;
            int temp = st;
            while(temp <= end){
                if(c.compare(a[temp],p1)<0){
                    swap(a,temp,st);
                    st++;
                }else if(c.compare(a[temp],p2)>=0){
                    while(c.compare(a[end],p2)>=0 && temp <end){
                        end --;
                    }
                    swap(a,temp,end);
                    end --;
                    if(c.compare(a[temp],p1)<0){
                        swap(a,temp,st);
                        st++;
                    }
                }
                temp++;
            }
            st--;
            end++;
            a[l] = a[st];
            a[st] = p1;
            a[r] = a[end];
            a[end] = p2;
            DPQS(a,l,st-1);
            DPQS(a,st+1,end-1);
            DPQS(a,end+1,r);
        }
    }
    private static void swap(String[] a, int i, int j){
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
