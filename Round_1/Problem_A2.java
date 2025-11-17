package Round_1;

import java.util.Scanner;

public class Problem_A2 {
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
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }
        long hi = 0;
        for(int i = 0; i < n; i++){
            hi = Math.max(hi, a[i]);
        }
        long lo = 0;
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            if(isPossible(a, mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        System.out.println(lo);
    }

    private static boolean isPossible(long[] a, long mid) {
        int n = a.length;
        long c = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            c = Math.min(c, a[i]);
            boolean valid = (i == n - 1) || (Math.abs(a[i] - a[i + 1]) > mid);
            if(valid){
                if(c > mid) {
                    return false;
                }
                c = Long.MAX_VALUE;
            }
        }
        return true;
    }
}
