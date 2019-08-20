package lab2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

class ShiftAndStore {
    public static void main(String[] args) throws IOException {
        int arr[] = new int[1000];
        Random rnd = new Random();
        int low = 0, high = 50;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(high-low) + low;
            low = high;
            high += 50;
            
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of times to be shifted");
        int shift = scan.nextInt();
        scan.close();
        String arrString = arrToString(arr, shift);
        File file = new File("./lab2/shiftArray");
        FileWriter fw = new FileWriter(file);
        fw.write(shift+"\n"+arrString);
        fw.close();
    }

    private static String arrToString(int arr[], int shift) {
        String s = "";
        int j = (arr.length - shift) % arr.length;
        for (int i = 0; i < arr.length; i++) {
            s = s+arr[j]+"\n";
            j = (j+1)%arr.length;
        }
        return s;
    }
}

