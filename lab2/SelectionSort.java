package lab2;

public class SelectionSort<T extends Comparable<T>> {

    public T[] sort(T arr[], boolean rev) {
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                boolean comp = false;
                if (rev) {
                    comp = arr[j].compareTo(arr[i]) < 0;
                }else {
                    comp = arr[i].compareTo(arr[j]) > 0;
                }
                if (comp) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }            
        }
        return arr;
    }

    public T[] sort(T arr[]) {
        return sort(arr, false);
    }

}