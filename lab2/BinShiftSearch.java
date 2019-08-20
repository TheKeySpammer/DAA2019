package lab2;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;


public class BinShiftSearch {
    public int search(int arr[], int shift, int val) {
        int index;
        index = binarySearch(arr, 0, ((shift % arr.length) - 1), val);
        if (index == -1) {
            index = binarySearch(arr, (shift % arr.length), arr.length-1, val);
        }
        return index;
    }

    public int binarySearch(int arr[], int low, int high, int val) {
        int middle = (low + high) / 2;
        if (high < low) return -1;
        if (arr[middle] == val) return middle;
        else if (arr[middle] < val) return binarySearch(arr, middle+1, high, val);
        else return binarySearch(arr, low, middle-1, val);
    }


    public static void main(String[] args) throws IOException {
        File inFile = new File("./lab2/shiftArray");
        BinShiftSearch bss = new BinShiftSearch();
        Scanner scan = new Scanner(inFile);
        String inString = "";
        int shift = Integer.parseInt(scan.nextLine());
        while (scan.hasNextLine()) {
            inString = inString+scan.nextLine()+" ";
        }
        int arr[] = bss.processArrayString(inString);
        scan.close();
        Scanner scanSys = new Scanner(System.in);
        System.out.println("Enter a number to be searched: ");
        int ele = scanSys.nextInt();
        scanSys.close();
        System.out.println(bss.search(arr, shift, ele));
    }

    private int[] processArrayString(String aSt) {
        StringTokenizer st = new StringTokenizer(aSt);
        int ar[] = new int[st.countTokens()];
        int i = 0;
        while(st.hasMoreTokens()) {
            ar[i++] = Integer.parseInt(st.nextToken());
        }
        return ar;
    }
}
