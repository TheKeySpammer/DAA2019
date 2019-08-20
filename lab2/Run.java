package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

class Run {
    public static void main(String[] args) throws IOException {
        File arFile = new File("./lab2/array");
        try {
            Scanner scan = new Scanner(arFile);
            String rawArr = "";
            while (scan.hasNextLine()) {
                rawArr = rawArr + scan.nextLine() + " ";
            }
            Integer arr[] = processArr(rawArr);
            Integer arr2[] = new Integer[arr.length];
            arr2 = Arrays.copyOf(arr, arr.length);
            selectionSortRun(arr);
            insertionSortRun(arr2);
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public static void insertionSortRun(Integer arr[]) throws IOException {
        InsertionSort<Integer> is = new InsertionSort<>();
        arr = is.sort(arr);
        File outFile = new File("./lab2/outFile2");
        FileWriter fw = new FileWriter(outFile);
        fw.write(arrToString(arr));
        fw.close();
    }

    public static void selectionSortRun(Integer arr[]) throws IOException {
        SelectionSort<Integer> ss = new SelectionSort<>();
        arr = ss.sort(arr);
        File outFile = new File("./lab2/outFile1");
        FileWriter fw = new FileWriter(outFile);
        fw.write(arrToString(arr));
        fw.close();
    }

    public static Integer[] processArr(String arr) {
        StringTokenizer st = new StringTokenizer(arr);
        Integer a[] = new Integer[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            a[i++] = Integer.parseInt(st.nextToken());
        }
        return a;
    }

    public static String arrToString(Integer arr[]) {
        String str = "";
        for (int i : arr) {
            str = str + i + "\n";
        }
        return str;
    }
}