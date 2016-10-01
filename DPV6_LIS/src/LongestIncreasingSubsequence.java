import javax.swing.plaf.ListUI;


public class LongestIncreasingSubsequence {

	public static void main(String[] args) {
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
		
		int [] A = {5, 2, 8, 6, 3, 6, 9, 7};
		//int [] A = { 10, 22, 9, 33, 21, 50, 41, 60 };
		lis.longestIncSub(A);
		
	}
	
	public void longestIncSub(int [] A) {
		int [] max = new int[1];
		lisUtil(A, 0, 1, max);
		System.out.println("max: " + max[0]);
	}
	
	private void lisUtil(int [] A, int index, int count, int [] max) {
		if (index >= A.length) {
			max[0] = Math.max(count, max[0]);
			return ;
		}
		
		for (int i=index; i<A.length; i++) {
			if ((i+1 < A.length) && (A[i] < A[i+1])) {
				lisUtil(A, i+1, count+1, max);
			} else {
				lisUtil(A, i+1, count, max);
			}
		}
	}
}
