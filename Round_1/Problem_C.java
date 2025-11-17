package Round_1;

import java.util.HashMap;
import java.util.Scanner;

public class Problem_C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            System.out.print("Case #" + (i + 1) + ": ");
            solve(sc);
        }
    }

    private static void solve(Scanner sc) {
        int N = sc.nextInt();
        HashMap<Integer, Long> freq = new HashMap<>();
        int prefix = 0;
        freq.put(0, 1L);
        for (int i = 0; i < N; i++) {
            int a = sc.nextInt();
            prefix ^= a;
            freq.put(prefix, freq.getOrDefault(prefix, 0L) + 1L);
        }
        long n = N;
        long total = n * (n + 1) * (n + 2) / 6;
        long subtract = 0L;
        for (long m : freq.values()) {
            subtract += m * (m - 1) * (m + 1) / 6;
        }
        long ans = total - subtract;
        System.out.println(ans);
    }
}
