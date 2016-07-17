import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
task is to compute the number of inversions in the file given, 
where the ith row of the file indicates the ith entry of an array.
*/

public class InversionCount {
	

	public static void main(String[] args) {
		InversionCount ic = new InversionCount();
		
		int [][] A = {
				{1, 3, 5, 2, 4, 6},
				{1, 20, 6, 4, 5},
				{8, 4, 2, 1}
				};
		for (int i=0; i<A.length; i++) {
			System.out.println("inversion count: " + ic.countInversions(A[i]));
		}
		ic.readInput();
		
	}
	
	public long countInversions(int [] A) {
		return countInversions(A, 0, A.length-1);
	}
	
	private long countInversions(int [] A, int low, int high) {
		long totalCount = 0;
		if (low < high) {
			int mid = low + (high-low)/2;
			totalCount += countInversions(A, low, mid);
			totalCount += countInversions(A, mid+1, high);
			totalCount += countSplitInversion(A, low, mid, high);
		}
		return totalCount;
	}
	
	private long countSplitInversion(int [] A, int low, int mid, int high) {
		long inversionCount = 0;
		int leftSize = mid-low+1;
		int rightSize = high-mid;
		int [] left = new int[leftSize];
		int [] right = new int[rightSize];
		
		for (int i=low, j=0; i<=mid; i++, j++) {
			left[j] = A[i];
		}
		
		for (int i=mid+1, j=0; i<=high; i++, j++) {
			right[j] = A[i];
		}
		
		int k=low;
		int i=0, j=0;
		while (i<left.length && j <right.length) {
			if (left[i] < right[j]) {
				A[k++] = left[i];
				++i;
			} else {
				A[k++] = right[j];
				++j;
				inversionCount += (left.length - i);
			}
		}
		while(i < left.length) {
			A[k++] = left[i++];
		}
		while (j < right.length) {
			A[k++] = right[j++];
		}
		return inversionCount;
	}
	
	public void readInput() {
		BufferedReader br = null;
		int [] A = new int[100000];
		try {
			String in;
			int index = 0;
			int count = 0;
			br = new BufferedReader(new FileReader("data/inversion_input.txt"));
			while ((in = br.readLine()) != null) {
				A[index++] = Integer.parseInt(in.trim());
				//System.out.println(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i=0; i<10; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
		System.out.println("A size: " + A.length);
		System.out.println("inversion in input: " + countInversions(A));
	}
}
