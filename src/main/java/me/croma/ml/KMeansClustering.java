package me.croma.ml;

import java.util.Random;

/**
 *Provide Implementation of K-means algorithm.
 * {@link #iterate()} is One Iteration of mean adjustment step.
 * {@link #iterate(int)} N Iteration of mean adjustment step.
 * {@link #cost()} can be used to get current cost of model.
 */
public class KMeansClustering {

	private final double input[][];
	private int c[];// each point is in cluster no.
	private double u[][];//k means...
	private int k;

	/**
	 * Build a KMeans Trainer
	 * @param k No of cluster
	 * @param input input data
	 * @throws IllegalArgumentException if input is invalid.
	 * @throws java.lang.NullPointerException if input is null.
	 *
	 */
	public KMeansClustering(int k, final double input[][]) {
		if(input == null) throw new NullPointerException("Input array is null");
		for(int i = 0;i < input.length;i++) {
			if(input[i] == null) throw new NullPointerException("Input array is null");
			if(input[i].length != input[0].length) {
				throw new IllegalArgumentException("All elements of input array are not equal");
			}
		}
		if(k > input.length || k < 1) throw new IllegalArgumentException("K is More then length of Input Or less then 1");
		if(input.length == 0) throw new IllegalArgumentException("Input array length must be greater then 0");
		this.input = input;
		u = new double[k][];
		c = new int[input.length];
		this.k = k;
		randomInit();
	}

	/**
	 * Using <a href='http://en.wikipedia.org/wiki/Reservoir_sampling'>Reservoir sampling</a>
	 */
	private void randomInit() {
		for(int i = 0;i < k;i++) {
			u[i] = input[i];
		}
        Random r = new Random((long)input[0][0]);
		for(int i = k;i < input.length;i++) {

			int j = (int)(r.nextDouble()*i);
			if(j < k) {
				u[j] = input[i];
			}
		}
	}

	/**
	 * Euclidean distance
	 * @param input1 input vector 1
	 * @param input2 input vector 2
	 * @return distance b/w two input vectors
	 */
	private double distance(double input1[], double input2[]) {
		double r = 0;
		for(int i = 0;i < input1.length;i++) {
			r += (input1[i] - input2[i]) * (input1[i] - input2[i]);
		}
		return r;
	}


	/**
	 * 1 Iteration of training.
	 * Assign the clusters
	 *  @return assigned class to input after training.
	 */
	private int[] iterate() {

		for (int i = 0;i < input.length;i++) {
			double min = Double.MAX_VALUE;
			for (int j = 0;j < k;j++) {
				double v = distance(input[i], u[j]);
				if (v < min) {
					min = v;
					c[i] = j;
				}
			}
		}
		updateMeans();
		return this.c;
	}

	private double[] add(double input1[] , double input2[]) {
		double r[] = new double[input1.length];
		for (int i = 0;i < input1.length;i++) {
			r[i] = input1[i] + input2[i];
		}
		return r;
	}

	/**
	 * Update Means using current assignment.
	 */
	private void updateMeans() {
		double av[][] = new double[k][input[0].length];

		int ct[] = new int[k];
		for (int i = 0;i < input.length;i++) {

			av[c[i]] = add(av[c[i]], input[i]);
			ct[c[i]]++;
		}
		//divide
		for(int i = 0;i < av.length;i++) {
			for(int j = 0;j < av[i].length;j++) {
				if(ct[i] != 0) av[i][j] /= ct[i];
			}
		}
		this.u = av;
	}

	/**
	 *Cost function for K-means
	 * @return cost based on current {@link #getMeans() Mean Points}
	 */
	public double cost() {
		double cost = 0;
		for (int i = 0;i < input.length;i++ ) {
			cost += distance(input[i], u[c[i]]);
		}
		return cost;
	}



	/**
	 * @param n Number of Iteration.
	 * @return assigned class to input after training.
	 */
	public int[] iterate(int n) {
		for (int i = 0;i < n;i++) {
			iterate();
		}
		return c;
	}

	/**
	 * @return Learned Means
	 */
	public double[][] getMeans() {
		return u;
	}

	/**
	 * @return Input Array.
	 */
	public double[][] getInput() {
		return input;
	}

	/**
	 * Get assigned cluster based on current values of means.
	 * @param index Index if input vector.
	 * @return assigned cluster No for given index.
	 * @throws java.lang.ArrayIndexOutOfBoundsException if Index is more than input length and less then 0.
	 */
	public int getAssignedCluster(int index) {
		return c[index];
	}



}