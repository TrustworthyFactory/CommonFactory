/*
 *		OPTET Factory
 *
 *	Class OptetTwProperty 1.0 29 juil. 2014
 *
 *	Copyright (c) 2013 Thales Communications & Security SAS
 *	4, Avenue des Louvresses - 92230 Gennevilliers 
 *	All rights reserved
 *
 */

package com.thalesgroup.optet.common.views;

/**
 * @author F. Motte
 *
 */
public class OptetTwProperty {

	// name of the metric
	private String metric;
	// value of the metric 
	private String value;
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public OptetTwProperty(String metric, String value) {
		super();
		this.metric = metric;
		this.value = value;
	}
	
}
