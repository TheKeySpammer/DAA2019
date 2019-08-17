import java.util.Scanner;
class SearchRun {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter length of array: ");
        int len = scan.nextInt();
        Integer arr[] = new Integer[len];
        System.out.println("Enter "+len+" numbers");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scan.nextInt();
        }
        System.out.println("Enter a number to search: ");
        int num = scan.nextInt();
        scan.close();
        SearchAlgo<Integer> searchAlgo = new SearchAlgo<Integer>(arr);
        if (searchAlgo.binarySearchRec(num, 0, arr.length-1)) {
            System.out.println("Found");
        }else{
            System.out.println("Not found");
        }
    }
}