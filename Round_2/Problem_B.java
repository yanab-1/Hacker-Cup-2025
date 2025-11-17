package Round_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Problem_B {
    static long[] preB, preSB;
    static int[] cntB;

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
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int[] b = new int[m];
        int maxB = 0;
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
            if (b[i] > maxB) maxB = b[i];
        }
        Arrays.sort(a);
        ArrayList<Integer> groupsList = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int v = a[i], c = 0;
            while (i < n && a[i] == v) { c++; i++; }
            groupsList.add(c);
        }
        int U = groupsList.size();
        int[] groups = new int[U];
        for (i = 0; i < U; i++) groups[i] = groupsList.get(i);

        cntB = new int[maxB + 1];
        long totalBsum = 0;
        for (int x : b) { cntB[x]++; totalBsum += x; }

        preB = new long[maxB + 1];
        preSB = new long[maxB + 1];
        long cAcc = 0, sAcc = 0;
        for (int x = 0; x <= maxB; x++) {
            cAcc += cntB[x];
            sAcc += (long) x * cntB[x];
            preB[x] = cAcc;
            preSB[x] = sAcc;
        }

        int nonZero = m - cntB[0];

        int low = 0, high = U;
        while (low < high) {
            int mid = (low + high + 1) >>> 1;
            if (ok(groups, U, mid, m, maxB, totalBsum, nonZero))
                low = mid;
            else
                high = mid - 1;
        }
        long ans = 0;
        for (int x = U - low; x < U; x++) ans += groups[x];
        System.out.println(ans);
    }
    private static boolean ok(int[] groups, int U, int g, int m, int maxB, long totalBsum, int nonZero) {
        if (g == 0) return true;
        if (g > m) return false;
        if (g > nonZero) return false;

        int start = U - g;
        int r = 0;
        for (int i = start; i < U; i++) r += groups[i];
        if (r == 0) return true;

        long[] LHS = new long[r + 1];
        long cur = 0;
        int pos = 1;

        for (int d = g; d >= 1; d--) {
            int cnt = groups[start + (d - 1)];
            for (int t = 0; t < cnt; t++) {
                cur += d;
                LHS[pos++] = cur;
            }
        }

        for (int k = 1; k <= r; k++) {
            long rhs;
            if (k <= maxB) {
                rhs = preSB[k] + (long) (preB[maxB] - preB[k]) * k;
            } else {
                rhs = totalBsum;
            }
            if (LHS[k] > rhs) return false;
        }
        return true;
    }
}
