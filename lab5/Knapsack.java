package lab5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.print("M = ");
        Scanner scan = new Scanner(System.in);
        int M = scan.nextInt();
        System.out.print("Number of items = ");
        int n = scan.nextInt();
        ArrayList<knap> knaps = new ArrayList<knap>();
        System.out.println("Enter " + n + " space seprated values for profit: ");
        for (int i = 0; i < n; i++) {
            knap temp = new knap();
            temp.profit = scan.nextInt();
            knaps.add(temp);
        }
        System.out.println("Enter " + n + " space separated values for weight: ");
        for (int i = 0; i < n; i++) {
            knap temp = knaps.get(i);
            temp.weight = scan.nextInt();
            temp.computeX();
            knaps.set(i, temp);
        }
        scan.close();

        System.out.printf("\n\n--------------------\n\n");
        knaps.sort(new Comparator<knap>() {
            @Override
            public int compare(knap a, knap b) {
                if (a.x == b.x) {
                    return a.profit - b.profit;
                }
                return a.x > b.x ? 1 : -1;
            }
        });


        float store = 0.f;
        float totalProfit = 0.f;
        int i = knaps.size() - 1;
        while (store < (float) M && i >= 0) {
            knap temp = knaps.get(i);
            float remainingCap = (float)M - store;
            System.out.println("Iteration: "+(n-i));
            System.out.println("Remaining Capacity: "+remainingCap);
            if (remainingCap >= temp.weight) {
                totalProfit += temp.profit;
                store += temp.weight;
                System.out.println("Current weight: "+store);
                System.out.println("Current profit: "+totalProfit);
                knaps.remove(i);
            }
            else {
                float frac = remainingCap / (float)temp.weight;
                float fracp = (float) temp.profit * frac;
                totalProfit += fracp;
                System.out.println("Breaking weight "+temp.weight+" in fraction "+frac);
                System.out.println("Fraction profit of "+temp.profit+": "+fracp);
                break;
            }
            i--;
        }

        System.out.printf("\n\n------------------------------\n\n");

        System.out.println("Total profit: "+totalProfit);
    }

}

class knap implements Comparable<knap> {
    int profit;
    int weight;
    float x;

    public void computeX() {
        this.x = (float) profit / (float) weight;
    }

    @Override
    public int compareTo(knap b) {
        if (this.x == b.x) {
            return this.profit - b.profit;
        }
        return this.x > b.x ? 1 : -1;
    }

    @Override
    public String toString() {
        String k = "[Profit: " + this.profit + " Weight: " + this.weight + " Ratio: " + this.x + "]";
        return k;
    }
}
