package lab3;
import java.util.Arrays;

public class RandQuickSort {
    public void sort (int arr[], int l, int h) {
	if (h <= l) return;

	int p = partition(arr, l, h);

	sort (arr, l, p-1);
	sort (arr, p+1, h);
    }

    private int partition(int arr[], int low, int high) {
	int pIndex = (int) (Math.random()*(high - low) + low);
	swap (arr, pIndex, high);
	int pivot = arr[high];
	int i = -1;
	for (int j = 0; j < high; j++) {
	    if (arr[j] < pivot) {
		i++;
		swap(arr, i, j);
	    }
	}

	swap (arr, i+1, high);
	return i+1;
    }

    private void swap (int arr[], int i, int j){
	int temp = arr[j];
	arr[j] = arr[i];
	arr[i] = temp;
    }

    
    public static void main(String args[]) {
	int a[] = {7, 2, 6, 5, 4, 2};
	println("Initial array: ");
	println(Arrays.toString(a));
	RandQuickSort qs = new RandQuickSort();
	qs.sort(a, 0, a.length-1);
	println("Sorted Array: ");
	println(Arrays.toString(a));
    }

    public static void println(String a) {
	System.out.println(a);
    }
}
