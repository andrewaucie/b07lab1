import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class Polynomial {
	double[] array;
	int[] exp;
	public Polynomial() {
		array = new double[1];
		exp = new int[1];
		array[0] = 0;
		exp[0] = 0;
	}
	public Polynomial (double[] new_arr, int[] new_exp) {
		array = new_arr;
		exp = new_exp;
	}
	public Polynomial(File x) {
		try {
			Scanner file = new Scanner(x);
			String str = file.nextLine();
			String[] p = str.split("(?=\\+)|(?=-)");
			array = new double[p.length];
			exp = new int[p.length];
			int index=0;
			for (String num: p) {
				if (num.indexOf("x") == -1) {
					array[index] = Double.parseDouble(num);
					exp[index] = 0;
				}
				else {
					array[index] = Double.parseDouble(num.substring(0, num.indexOf("x")));
					exp[index] = Integer.parseInt(num.substring(num.indexOf("x")+1));
				}
				index++;
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	void saveToFile(String name) {
		try {
			FileWriter file = new FileWriter(name, false);
			for (int i=0; i<array.length; i++) {
				String coefficient = String.valueOf(this.array[i]);
				String exponent = String.valueOf(this.exp[i]);
				if (i==0 || this.array[i] < 0) file.write(coefficient);
				else if (this.array[i] > 0) file.write("+" + coefficient);
				if (this.exp[i] > 0) file.write("x" + exponent);
			}
			file.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	Polynomial add(Polynomial p) {
		int length_curr = this.array.length;
		int length_p = p.array.length;
		double[] new_arr = new double[length_curr + length_p];
		int[] new_exp = new int[length_curr + length_p];
		int index=0;
		for (int i=0; i<length_curr; i++) {
			for (int j=0; j<length_p; j++) {
				if (this.exp[i] == p.exp[j] && this.array[i] != -p.array[j]) {
					//System.out.println(index +"; "+String.valueOf(length_curr+length_p));
					new_arr[index] = this.array[i]+p.array[j];
					new_exp[index] = this.exp[i];
					index++;
					break;
				}
			}
		}
		double[] upd_arr = new double[index];
		int[] upd_exp = new int[index];
		for (int i=0; i<index; i++) {
			upd_arr[i] = new_arr[i];
			upd_exp[i] = new_exp[i];
		}
		Polynomial upd_p = new Polynomial(upd_arr, upd_exp);
		return upd_p;
	}
	double evaluate(double x) {
		double sum = 0;
		int length = this.array.length;
		for (int i=0; i<length; i++) {
			sum += this.array[i]*Math.pow(x, this.exp[i]);
		}
		return sum;
	}
	boolean hasRoot(double x) {
		if (evaluate(x)==0) {
			return true;
		}
		return false;
	}
	Polynomial multiply(Polynomial p) {
		double[] new_arr = new double[this.array.length * p.array.length];
		int[] new_exp = new int[this.array.length * p.array.length];
		int index=0;
		for (int i=0; i<this.array.length; i++) {
			for (int j=0; j<p.array.length; j++) {
				new_arr[index] = this.array[i]*p.array[j];
				new_exp[index] = this.exp[i] + p.exp[j];
				index++;
			}
		}
		//System.out.println(Arrays.toString(new_arr) + "exp: " + Arrays.toString(new_exp));
		int upd_index=index;
		for (int i=0; i<index; i++) {
			for (int j=0; j<index; j++) {
				if (new_exp[i]>=0 && new_exp[i] == new_exp[j] && i!=j) {
					new_arr[i] += new_arr[j];
					new_arr[j] = 0;
					new_exp[j] = -1;
					if (new_arr[i] == 0) {
						upd_index--;
						new_exp[i] = -1;
					}
					upd_index--;
				}
			}
		}
		double[] upd_arr = new double[upd_index];
		int[] upd_exp = new int[upd_index];
		int n=0;
		for (int i=0; i<index; i++) {
			if (new_arr[i] != 0) {
				upd_arr[n] = new_arr[i];
				upd_exp[n] = new_exp[i];
				n++;
			}
		}
		Polynomial new_p = new Polynomial(upd_arr, upd_exp);
		//System.out.println(Arrays.toString(upd_arr) + "exp: " + Arrays.toString(upd_exp));
		return new_p;
	}
}