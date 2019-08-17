import java.util.*;
class question1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String strArr = scan.next();
        int q = scan.nextInt();
        int ls[] = new int[q];
        int arr[] = toIntArray(strArr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = count1s(arr[i]);
        }
        for (int i = 0; i < q; i++) {
            int k = scan.nextInt();
            ls[i] = calculateL_n2(arr, k);
        }
        scan.close();
        System.out.println(Arrays.toString(ls));    
    }

    public static int calculateL_n2(int arr[], int k) {
        int l = arr.length+1;
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            int j = i;
            boolean found = false;
            for (; j < arr.length; j++) {
                sum += arr[j];
                if (sum >= k) {
                    found = true;
                    break;
                }
            }
            if (j-i+1 < l && found) {
                l = j-i+1;
            }
        }
        if (l == arr.length-1) {
            return -1;
        }
        return l;
    }

    public static int count1s(int ele) {
        int ones = 0;
        while (ele > 0) {
            ones++;
            ele &= (ele - 1);
        }
        return ones;
    }
    public static int[] toIntArray(String str) {
        int len = 0;
        StringTokenizer strT = new StringTokenizer(str, " ,-");
        len = strT.countTokens();
        int arr[] = new int[len];
        int i = 0;
        while(strT.hasMoreTokens()) {
            arr[i++] = Integer.parseInt(strT.nextToken());
        }
        return arr;
    }
}