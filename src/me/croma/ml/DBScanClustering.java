package me.croma.ml;

import java.util.ArrayList;
import java.util.Arrays;

//TODO make a list based constructor also.
/**
 * Provide implementation of DBScan clustering algorithm
 * @see #startClustering()
 * @see #getAssignedCluster(int)
 */
public class DBScanClustering {
	private double input[][];
	private double e;
	private int minPoints;
	private int closed[];
	private int clusterNo = 0;
	private boolean isTrained = false;
	private double memoDistance[][];
	private boolean isNoise[];
	/**
	 Build a DBScan Trainer using
	 <a href = 'http://en.wikipedia.org/wiki/DBSCAN'> DBScan algorithm</a>
	 * @param input input data
	 * @param e Value of ε.
	 * @param minPoints min Points for valid cluster.
	 * @throws IllegalArgumentException if input is invalid.
	 * @throws java.lang.NullPointerException if input is null.
	 * @see #startClustering()
	 * @see #isNoise(int)
	 */
	public DBScanClustering(double input[][], double e, int minPoints) {
		if(input == null) throw new NullPointerException("Input array is null");
		for(int i = 0;i < input.length;i++) {
			if(input[i] == null) throw new NullPointerException("Input array is null");
			if(input[i].length != input[0].length) {
				throw new IllegalArgumentException("All elements of input array are not equal");
			}
		}
		this.input = input;
		this.minPoints = minPoints;
		this.e = e;
		this.memoDistance = new double[input.length][input.length];
		for(int i = 0;i < memoDistance.length;i++) Arrays.fill(memoDistance[i] , -1);
		closed = new int[input.length];
		isNoise = new boolean[input.length];
		Arrays.fill(closed, -1);


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
	 * Euclidean distance
	 * @param input1 index1
	 * @param input2 index2
	 * @return distance b/w two input vectors
	 */
	private double distance(int input1, int input2) {
		if(memoDistance[input1][input2] == -1) {
			memoDistance[input1][input2] = distance(input[input1], input[input2]);
		}
		return memoDistance[input1][input2];
	}


	/**
	 *
	 * @param st start from index st and recursively find connected component in graph.
	 */
	private boolean cluster(int st, int clusterNo) {

		//TODO for boundary point choose min AV distance based cluster.

		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i = 0;i < input.length;i++) {
			if(distance(st, i) <= e && st != i) {
				al.add(i);
			}
		}

		if(al.size() < minPoints) {
			////recursion
			int c = clusterNo;
			closed[st] = clusterNo;
			for (int i = 0;i < al.size();i++) {
				if (closed[al.get(i)] == -1) {
					boolean b = cluster(al.get(i), c);
					if(b) {
						closed[st] = c;
					}
					c++;
				}
			}
			this.clusterNo = c == clusterNo ? c : c - 1;
			return false;//this is not a dense point.
		}
		else {
			//point st is a dense point.
			closed[st] = clusterNo;
			for (int i = 0;i < al.size();i++) {
				if(closed[al.get(i)] == -1) {
					cluster(al.get(i), clusterNo);
				}
			}
			return true;//this point is a dense point.
		}
	}

	/**
	 * @return assigned values of clusters.
	 */
	public int[] startClustering() {
		int count[];
		if(!isTrained) {
			for(int i = 0;i < input.length;i++) {
				if(closed[i] == -1) {
					cluster(i, clusterNo);
					clusterNo++;
				}
			}

			count = new int[clusterNo + 1];
			for(int i = 0;i < closed.length;i++) {
				count[closed[i]]++;
			}
			for(int i = 0;i < input.length;i++) {
				isNoise[i] = count[closed[i]] < minPoints;
			}
			isTrained = true;
		}
		return closed;
	}
	/**
	 * @return Input Array.
	 */
	public double[][] getInput() {
		return input;
	}

	/**
	 * @param index Index if input vector.
	 * @return assigned cluster No for given index.
	 * @throws java.lang.ArrayIndexOutOfBoundsException if Index is more than input length and less then 0.
	 */
	public int getAssignedCluster(int index) {
		if(!isTrained) startClustering();
		return closed[index];
	}

	/**

	 * @return Assigned value of cluster as an array.
	 */
	public int[] getClustersArray() {
		if(!isTrained) startClustering();
		return closed;
	}

	/**
	 *
	 * @param index index of input array.
	 * @return true if index is noise Point else false.
	 *@throws java.lang.ArrayIndexOutOfBoundsException if value of index is less then 0 or greater then equal to input.length.
	 */
	public boolean isNoise(int index) {
		if(!isTrained) startClustering();
		return isNoise[index];
	}

	/**
	 *
	 * @return isNoise array.
	 */
	public boolean[] getNoiseArray() {
		if(!isTrained) startClustering();
		return this.isNoise;
	}



}