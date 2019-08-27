package lab1;

class question1v2 {
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

    public static int calaculateL_n2(int arr[k], int k) {
	int i = 0, j = 0;
	int min = -1;
	while (i < arr.length) {
	    int sum = 0;
	    while (j<arr.length) {
		sum += arr[j++];
		if (sum >= k) break;
	    }
	    min = min == -1 ? sum : min;
	    min = min > sum ? sum : min;
	    while (i <= j){
		sum -= arr[i++];
		if (sum < k) {
		    sum = sum + arr[i-1];
		    break;
		    
		}
	    }
	}
    }
}
