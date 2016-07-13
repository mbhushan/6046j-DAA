
public class KaratsubaMultiplication {

	public static void main(String[] args) {
		KaratsubaMultiplication km = new KaratsubaMultiplication();
		
		//char [] X = {'1', '2','3', '4'};
		//char [] Y = {'5', '6', '7', '8'};
		long y = 1234;
		long x = 5678;
		long result = km.karatsubaAlgo(x, y);
		System.out.println("result: " + result);
	}
	
	public long karatsubaAlgo(long x, long y) {
		int xlen = getNumSize(x);
		int ylen = getNumSize(y);
		
		int n = Math.max(xlen, ylen);
		if (n < 10) {
			return x*y;
		}
		n = (n/2) + (n%2);
		long m = (long)Math.pow(10, n);
		
		long b = x / m;
        long a = x - (b * m);
        long d = y / m;
        long c = y - (d * n);
        
        /** compute sub expressions **/
		long ac = karatsubaAlgo(a, c);
		long adbc = karatsubaAlgo(a+b, c+d);
		long bd = karatsubaAlgo(b, d);
		
		return (long)Math.pow(10, 2*n)*ac + (long)Math.pow(10, m)*(adbc - ac - bd) + bd;
	}
	
	private int getNumSize(long num) {
		return String.valueOf(num).length();
	}
}
