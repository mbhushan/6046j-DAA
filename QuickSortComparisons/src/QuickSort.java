import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/*
 task is to compute the total number of comparisons used to sort the given input file by QuickSort. 
 As you know, the number of comparisons depends on which elements are chosen as pivots, 
 so we'll ask you to explore three different pivoting rules.
 you should always use the first element of the array as the pivot element.
 */

public class QuickSort {

	public static void main(String[] args) {
		QuickSort qs = new QuickSort();

		int[] A = { 8, 3, 25, 6, 10, 17, 1, 2, 18, 5 };
		
		long comp = qs.quickSort(A);
		System.out.println("after running quick sort algo: ");
		System.out.println(Arrays.toString(A));
		System.out.println("number of comparisons: " + comp);
		
		qs.readInput();
	}
	
	public void readInput() {
		BufferedReader br = null;
		int [] A = new int[10000 ];
		try {
			String in;
			int index = 0;
			br = new BufferedReader(new FileReader("data/quicksort_input.txt"));
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
		int [] B = Arrays.copyOf(A, A.length);
		System.out.println("number of comparisons, first element as pivot: " + quickSort(B));
		System.out.println(Arrays.toString(B));
		int [] C = Arrays.copyOf(A, A.length);
		System.out.println("number of comparisons, last element as pivot: " + quickSort2(C));
		System.out.println(Arrays.toString(C));
		int [] D = Arrays.copyOf(A, A.length);
		System.out.println("number of comparisons, median of 3 as pivot: " + quickSort3(D));
		System.out.println(Arrays.toString(D));
		
	}

	public long quickSort(int[] A) {
		long [] accumulator = new long[1];
		int low = 0;
		int high = A.length - 1;
		quickSortComparisons(A, low, high, accumulator);
		return accumulator[0];
	}

	private void quickSortComparisons(int[] A, int low, int high,
			long [] accumulator) {
		if (low < high) {
			int pivot = partition(A, low, high);
			accumulator[0] += (pivot - low-1);
			accumulator[0] += (high-pivot+1);
			quickSortComparisons(A, low, pivot - 1, accumulator);
			quickSortComparisons(A, pivot + 1, high, accumulator);
		}
	}
	
	public long quickSort2(int[] A) {
		long [] accumulator = new long[1];
		int low = 0;
		int high = A.length - 1;
		quickSortComparisons2(A, low, high, accumulator);
		return accumulator[0];
	}

	private void quickSortComparisons2(int[] A, int low, int high,
			long [] accumulator) {
		if (low < high) {
			int temp = A[low];
			A[low] = A[high];
			A[high] = temp;
			int pivot = partition(A, low, high);
			accumulator[0] += pivot - low-1;
			accumulator[0] += high-pivot+1;
			quickSortComparisons2(A, low, pivot - 1, accumulator);
			quickSortComparisons2(A, pivot + 1, high, accumulator);
		}
	}
	
	public long quickSort3(int[] A) {
		long [] accumulator = new long[1];
		int low = 0;
		int high = A.length - 1;
		quickSortComparisons3(A, low, high, accumulator);
		return accumulator[0];
	}

	private void quickSortComparisons3(int[] A, int low, int high,
			long [] accumulator) {
		if (low < high) {
			int index = median(A, low, high);
			int temp = A[low];
			A[low] = A[index];
			A[index] = temp;
			int pivot = partition(A, low, high);
			accumulator[0] += pivot - low-1;
			accumulator[0] += high-pivot+1;
			quickSortComparisons3(A, low, pivot - 1, accumulator);
			quickSortComparisons3(A, pivot + 1, high, accumulator);
		}
	}
	
	private int median(int [] A, int low, int high) {
		int mid = low + (high-low)/2;
		
		if (A[low] > A[mid] && A[low] < A[high]) {
			return low;
		} else if (A[low] > A[high] && A[low] < A[mid]) {
			return low;
		} else if (A[mid] > A[low] && A[mid] < A[high]){
			return mid;
		} else if (A[mid] > A[high] && A[mid] < A[low]) {
			return mid;
		} else {
			return high;
		}
	}
	
	
	private int partition(int [] A, int low, int high) {
		int pivot = A[low];
		int i = low+1;
		
		for (int j=low+1; j<=high; j++) {
			if (A[j] < pivot) {
				int temp = A[j];
				A[j] = A[i];
				A[i] = temp;
				++i;
			}
		}
		A[low] = A[i-1];
		A[i-1] = pivot;
		return i-1;
	}
}
