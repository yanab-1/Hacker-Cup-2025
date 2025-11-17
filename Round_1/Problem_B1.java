package Round_1;
import java.util.Scanner;

public class Problem_B1 {
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
        int a = sc.nextInt();
        int b = sc.nextInt();
        for(int i = 0; i < 2 * n - 1; i++){
            System.out.print(1 + " ");
        }
        System.out.println(b);
    }
}
