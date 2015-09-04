package com.thalesgroup.optet.common.views;

/**
 * The OptetMetricModel is the model used for the metric audit
 * 
 * @author F. Motte
 *
 */
public class OptetMetricModel {
	// name of the metric
	private String metric;
	// value of the metric 
	private String value;
	// type of the metric
	private String type;
	
	
	/**
	 * getMetric
	 * @return the metric
	 */
	public String getMetric() {
		return metric;
	}
	
	
	/**
	 * setMetric
	 * @param metric the metric to set
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}
	
	
	/**
	 * getValue
	 * @return the value of the metric
	 */
	public String getValue() {
		return value;
	}
	
	
	/**
	 * setValue
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	/**
	 * Constructor
	 * @param metric the name of the metric
	 * @param value the value of the metric
	 */
	public OptetMetricModel(String metric, String value, String type) {
		super();
		this.metric = metric;
		this.value = value;
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
