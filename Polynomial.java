public class Polynomial {
	double[] array;
	public Polynomial() {
		array = new double[1];
		array[0] = 0;
	}
	public Polynomial (double[] new_arr) {
		array = new_arr;
	}
	Polynomial add(Polynomial p) {
		int length_curr= this.array.length;
		int length_p = p.array.length;
		int max = Math.max(length_curr, length_p);
		int min = Math.min(length_curr, length_p);
		double[] new_arr = new double[max];
		for (int i=0; i<min; i++) {
			new_arr[i] = this.array[i]+p.array[i];
		}
		for (int i=min; i<max; i++) {
			if (length_curr > length_p) new_arr[i] = this.array[i];
			else if (length_curr < length_p) new_arr[i] = p.array[i];
		}
		Polynomial new_p = new Polynomial(new_arr);
		return new_p;
	}
	double evaluate(double x) {
		double sum = this.array[0];
		int length = this.array.length;
		for (int i=1; i<length; i++) {
			sum += this.array[i]*Math.pow(x, i);
		}
		return sum;
	}
	boolean hasRoot(double x) {
		if (evaluate(x)==0) {
			return true;
		}
		return false;
	}
}