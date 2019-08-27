package lab2;


public class InsertionSort<T extends Comparable <T>> {
    public T[] sort(T a[], boolean rev) {
        for (int i = 1; i < a.length; i++) {
            T key = a[i];
            
            int j = i-1;
            
            while (j >= 0 && compare(a[j], key, rev)) {
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = key;
        }
        return a;

    }

    private boolean compare(T a, T b, boolean rev) {
        boolean comp = false;
        if (rev) {
            comp = a.compareTo(b) < 0;
        }else {
            comp = a.compareTo(b) > 0;
        }
        return comp;
    }

    public T[] sort (T a[]) {
        return sort(a, false);
    }
}
