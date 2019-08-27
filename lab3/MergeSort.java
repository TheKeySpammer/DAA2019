package lab3;
import java.util.Arrays;
public class MergeSort {

    public void sort(int arr[], int l, int h) {
	if (h <= l) return;
	int mid = (h+l)/2;
	sort(arr, l, mid);
	sort(arr, mid+1, h);
	merge(arr, l, mid, h); 
    }

    private void merge(int arr[], int l, int m, int n) {
	int n1 = (m-l) + 1;
	int n2 = n-(m+1) + 1;
	int L[] = new int[n1];
	int R[] = new int[n2];
	// Copy contents in new temporary array
	int j = 0;
	for (int i=l; i <= m; i++) {
	    L[j++] = arr[i];
	}
	j = 0;
	for (int i=m+1; i<=n; i++) {
	    R[j++] = arr[i];
	}

	// Merge the two subarray
	int i = 0; j = 0;
	int k = l;
	while (i < n1 && j < n2) {
	    if (L[i] < R[j]) arr[k++] = L[i++];
	    else arr[k++] = R[j++];
	}

	// Insert Remaining elements
	while (i < n1) {
	    arr[k++] = L[i++];
	}

	while (j < n2) {
	    arr[k++] = R[j++];
	}
    }


    public static void main(String args[]) {
	int a[] = {7, 2, 6, 5, 4, 2};
	println("Initial array: ");
	println(Arrays.toString(a));
	MergeSort ms = new MergeSort();
	ms.sort(a, 0, a.length-1);
	println("Sorted Array: ");
	println(Arrays.toString(a));
    }

    public static void println(String a) {
	System.out.println(a);
    }
}
