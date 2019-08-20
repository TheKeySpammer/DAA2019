package lab1;

public class SearchAlgo <T extends Comparable<T>> {
    private T arr[];

    SearchAlgo(T arr[]) {
        this.arr = arr;
    }

    public boolean linearSearch(T ele) {
        for (T a: arr) {
            if (a.compareTo(ele) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean binarySearch(T ele) {
        int high = arr.length-1;
        int low = 0;
        int middle;
        while (high >= low) {
            middle = (low + high) / 2;
            if (arr[middle].compareTo(ele) == 0) {
                return true;
            }else if (arr[middle].compareTo(ele) < 0) {
                low = middle + 1;
            }else {
                high = middle - 1;
            }
        }
        return false;
    }

    public boolean binarySearchRec(T ele, int low, int high) {
        int middle = (high + low) / 2;
        if (high < low) {
            return false;
        }
        if (arr[middle].compareTo(ele) == 0) return true;
        else if (arr[middle].compareTo(ele) < 0) return binarySearchRec(ele, middle + 1, high);
        else return binarySearchRec(ele, low, middle - 1);
    }

}