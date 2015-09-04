/*
 *		OPTET Factory
 *
 *	Class OptetTwAttribute 1.0 29 juil. 2014
 *
 *	Copyright (c) 2013 Thales Communications & Security SAS
 *	4, Avenue des Louvresses - 92230 Gennevilliers 
 *	All rights reserved
 *
 */

package com.thalesgroup.optet.common.views;

import java.util.List;

/**
 * @author F. Motte
 *
 */
public class OptetTwAttribute {
	// name of the metric
	private String name;
	// value of the metric 
	private List<OptetTwProperty> twProperty;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<OptetTwProperty> getTwProperty() {
		return twProperty;
	}
	public void setTwProperty(List<OptetTwProperty> twProperty) {
		this.twProperty = twProperty;
	}
	public OptetTwAttribute(String name, List<OptetTwProperty> twProperty) {
		super();
		this.name = name;
		this.twProperty = twProperty;
	}
	
	
}
