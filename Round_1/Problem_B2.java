package Round_1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Problem_B2 {
        static final long MOD = 1_000_000_007L;
    static final int MAXE = 60;
    static long[] fact = new long[MAXE + 1];
    static long[] invFact = new long[MAXE + 1];
    static Random rnd = new Random();
    public static void main(String[] args) {
        pre();
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {
            long N = sc.nextLong();
            long A = sc.nextLong();
            long B = sc.nextLong();
            Map<Long, Integer> primeExp = factor(B);
            List<Long> primes = new ArrayList<>();
            List<Integer> exps = new ArrayList<>();
            for (Map.Entry<Long, Integer> e : primeExp.entrySet()) {
                primes.add(e.getKey());
                exps.add(e.getValue());
            }
            long ans = 0;
            ans = dfsEnumerate(0, 1L, primes, exps, A, N);
            ans %= MOD;
            sb.append("Case #").append(tc).append(": ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        sc.close();
    }
    static long dfsEnumerateEntry(List<Long> primes, List<Integer> exps, long A, long N) {
        int m = primes.size();
        int[] chosen = new int[m];
        return dfsExponents(0, chosen, primes, exps, 1L, A, N);
    }
    static long dfsExponents(int idx, int[] chosen, List<Long> primes, List<Integer> exps, long curDiv, long A, long N) {
        if (idx == primes.size()) {
            if (curDiv > A) return 0L;
            long ways = 1L;
            for (int i = 0; i < exps.size(); i++) {
                int eL = chosen[i];
                int eR = exps.get(i) - eL;
                long c1 = combWithN(N, eL);
                long c2 = combWithN(N, eR);
                ways = (ways * ((c1 * c2) % MOD)) % MOD;
            }
            return ways % MOD;
        }
        long res = 0L;
        long p = primes.get(idx);
        int maxE = exps.get(idx);
        long mul = 1L;
        for (int e = 0; e <= maxE; e++) {
            if (curDiv > A) break; // pruning
            chosen[idx] = e;
            res += dfsExponents(idx + 1, chosen, primes, exps, curDiv, A, N);
            if (res >= MOD) res -= MOD;
            if (e < maxE) {
                curDiv = curDiv * p;
            }
        }
        return res % MOD;
    }
    static long dfsEnumerate(int dummy, long cur, List<Long> primes, List<Integer> exps, long A, long N) {
        return dfsEnumerateEntry(primes, exps, A, N);
    }
    static long combWithN(long N, int e) {
        if (e == 0) return 1L;
        long base = ((N - 1) % MOD + MOD) % MOD;
        long num = 1L;
        for (int i = 1; i <= e; i++) {
            num = (num * ((base + i) % MOD)) % MOD;
        }
        num = (num * invFact[e]) % MOD;
        return num;
    }
    static void pre() {
        fact[0] = 1;
        for (int i = 1; i <= MAXE; i++) fact[i] = (fact[i - 1] * i) % MOD;
        invFact[MAXE] = modInverse(fact[MAXE], MOD);
        for (int i = MAXE - 1; i >= 0; i--) invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
    }
    static long modPow(long a, long e, long mod) {
        long res = 1 % mod;
        long base = a % mod;
        while (e > 0) {
            if ((e & 1) == 1) res = mulMod(res, base, mod);
            base = mulMod(base, base, mod);
            e >>= 1;
        }
        return res;
    }
    static long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }
    static long mulMod(long a, long b, long mod) {
        return (a * b) % mod;
    }
    static Map<Long, Integer> factor(long n) {
        Map<Long, Integer> map = new HashMap<>();
        if (n <= 1) return map;
        factorRec(BigInteger.valueOf(n), map);
        return map;
    }
    static void factorRec(BigInteger nBig, Map<Long, Integer> map) {
        long n;
        try {
            n = nBig.longValueExact();
        } catch (ArithmeticException ex) {
            n = nBig.longValue();
        }
        if (n == 1) return;
        if (isPrime(nBig)) {
            long nl = nBig.longValue();
            map.put(nl, map.getOrDefault(nl, 0) + 1);
            return;
        }
        BigInteger d = pollardsRho(nBig);
        factorRec(d, map);
        factorRec(nBig.divide(d), map);
    }

    static boolean isPrime(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        return n.isProbablePrime(30);
    }

    static BigInteger pollardsRho(BigInteger n) {
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            return BigInteger.TWO;
        BigInteger one = BigInteger.ONE;
        BigInteger nMinusOne = n.subtract(one);
        while (true) {
            BigInteger x = new BigInteger(n.bitLength(), rnd).mod(n);
            BigInteger y = x;
            BigInteger c = new BigInteger(n.bitLength(), rnd).mod(n);
            if (c.compareTo(BigInteger.ZERO) == 0) c = BigInteger.ONE;
            BigInteger d = BigInteger.ONE;
            while (d.equals(BigInteger.ONE)) {
                x = f(x, c, n);
                y = f(f(y, c, n), c, n);
                BigInteger diff = x.subtract(y).abs();
                d = diff.gcd(n);
                if (d.equals(n)) break;
            }
            if (!d.equals(n) && !d.equals(BigInteger.ONE)) return d;
        }
    }
    static BigInteger f(BigInteger x, BigInteger c, BigInteger mod) {
        return x.multiply(x).mod(mod).add(c).mod(mod);
    }
}
