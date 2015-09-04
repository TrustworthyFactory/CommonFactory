package com.thalesgroup.optet.common.views;

/**
 * @author F. Motte
 *
 */
public class OptetTestCoverageModel {
	// name of the metric
	private String name;
	// total number of the metric
	private int total;
	// metric covered by the unit test
	private int covered;
	// Percentage for this metric
	private double pourcentage;
	

	/**
	 * getName
	 * @return return the name of the metric
	 */
	public String getName() {
		return name;
	}


	/**
	 * setName
	 * @param name the name of the metric
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * getTotal
	 * @return the total number of the metric
	 */
	public int getTotal() {
		return total;
	}


	/**
	 * setTotal
	 * @param total total number of the metric
	 */
	public void setTotal(int total) {
		this.total = total;
	}


	/**
	 * getCovered
	 * @return metric covered by the unit test
	 */
	public int getCovered() {
		return covered;
	}


	/**
	 * setCovered
	 * @param covered metric covered by the unit test
	 */
	public void setCovered(int covered) {
		this.covered = covered;
	}


	/**
	 * getPourcentage
	 * @return the percentage for this metric
	 */
	public double getPourcentage() {
		return pourcentage;
	}


	/**
	 * setPourcentage
	 * @param pourcentage the percentage for this metric
	 */
	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}


	public OptetTestCoverageModel(String name, int total, int covered, double pourcentage) {
		super();
		this.name = name;
		this.total = total;
		this.covered = covered;
		this.pourcentage = pourcentage;
	}


}
