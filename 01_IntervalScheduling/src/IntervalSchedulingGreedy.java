import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class IntervalSchedulingGreedy {

	public static void main(String[] args) {
		IntervalSchedulingGreedy isg = new IntervalSchedulingGreedy();
		
		int start[]  =  {0, 1, 3, 5, 8, 5};
		int finish[] =  {6, 2, 4, 7, 9, 9};
		int len = start.length;
		Interval [] intervals = new Interval[len];
		for (int i=0; i<len; i++) {
			intervals[i] = new Interval(start[i], finish[i]);
		}
		isg.printIntervals(intervals);
		
		isg.maxIntervals(intervals);
		
	}
	
	public void maxIntervals(Interval [] intervals) {
		Arrays.sort(intervals, new IntervalComparator());
		System.out.println("sorting intervals on finish time - increasing order ");
		printIntervals(intervals);
		ArrayList<Interval> result = new ArrayList<Interval>();	
		Interval aux = intervals[0];
		result.add(aux);
		for (int i=1; i<intervals.length; i++) {
			if (overlap(aux, intervals[i])) {
				continue;
			}
			aux = intervals[i];
			result.add(aux);
		}
		System.out.println("number of non-overlapping intervals scheduled:");
		for (Interval x: result) {
			System.out.println(x.toString());
		}
	}
	
	private boolean overlap(Interval a, Interval b) {
		int max = Math.max(a.start, b.start);
		int min = Math.min(a.finish, b.finish);
		
		return max < min;
	}
	
	private void printIntervals(Interval [] intervals) {
		for (Interval x: intervals) {
			System.out.println(x.toString());
		}
	}
	
	
}

class IntervalComparator implements Comparator<Interval> {

	@Override
	public int compare(Interval o1, Interval o2) {
		return o1.finish - o2.finish;
	}
	
}

class Interval {
	int start;
	int finish;
	
	public Interval(int start, int end) {
		this.start = start;
		this.finish = end;
	}
	
	public String toString() {
		String str = "[" + start + ", " + finish + "]";
		return str;
	}
}

