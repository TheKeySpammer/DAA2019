package lab4;
import java.io.PrintWriter;
import java.util.Arrays;

public class BinHeap {
	public static void main(String args[]) {
		PrintWriter pw = new PrintWriter(System.out, true);
		int heap[] = new int[11];
		for (int i = 0; i < 10; i++) {
			heap[i] = (int) (Math.random() * 99 + 1);
		}
		heap[heap.length - 1] = 0;
		pw.println("Array: " + Arrays.toString(heap));
		int firstLeaftIndex = (heap.length / 2);
		for (int i = firstLeaftIndex; i >= 0; i--) {
			heapify(heap, i);
		}
		pw.println("Heap: " + Arrays.toString(heap));
		insertHeap(heap, 50);
		pw.println("Heap: " + Arrays.toString(heap));
	}

	public static void heapify(int a[], int i) {
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int n = a.length;
		if (!isHeap(a, i)) {
			int large;
			if (l >= n)
				large = r;
			else if (r >= n)
				large = l;
			else
				large = a[r] > a[l] ? r : l;
			swap(a, large, i);
		}
		if (l < n && !isHeap(a, l)) {
			heapify(a, l);
		}
		if (r < n && !isHeap(a, r)) {
			heapify(a, r);
		}
	}

	public static void insertHeap(int a[], int n) {
		int last = a.length - 1;
		a[last] = n;
		int parent = last >> 1;
		while (parent >= 0 && a[parent] < a[last]) {
			swap (a, parent, last);
			last = parent;
			parent = last >> 1;
		}
	}

	public static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

    
	public static boolean isHeap(int a[], int i) {
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int leafIndexStart = (a.length / 2);
		int n = a.length;
		if (i >= leafIndexStart)
			return true;
		if (l >= n)
			return a[i] > a[r];
		else if (r >= n)
			return a[i] > a[l];
		else
			return a[i] > a[l] && a[i] > a[r];
	}
}
