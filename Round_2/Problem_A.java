package Round_2;

import java.util.Scanner;

public class Problem_A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            System.out.print("Case #" + (i + 1) + ": ");
            solve(sc);
        }
    }

    private static void solve(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n < m) {
            System.out.println("NO");
            return;
        }                      
        if (n <= 2 * m - 2) {
        System.out.println("YES");
        return;
        }         
        if (n == 2 * m - 1) {
        System.out.println("NO");
        return;
        }             
        if(n % 2 == 0){
            System.out.println("YES");                                
        }
        else{
            System.out.println("NO");                                 
        }
    }
}
