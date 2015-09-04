/*
 *		OPTET Factory
 *
 *	Class CertificatInterface 1.0 20 nov. 2013
 *
 *	Copyright (c) 2013 Thales Communications & Security SAS
 *	4, Avenue des Louvresses - 92230 Gennevilliers 
 *	All rights reserved
 *
 */

package com.thalesgroup.optet.common.data;

/**
 * @author F. Motte
 *
 */
public interface CertificatInterface {

	public void addComponent(String componentName, String parentName, boolean InTOE);
	
	public void addAsset(String name, String type);
	
	public void addStakeholder(String name, String Type);
	
	public void addThread(String name, String type);
	
	public void addProblemDefinition(String name, String Threat, String affectedAsset);
	
	public void addThread();
	
	public void addPropertySpecification(String twProperty, String control);
	
	public void addTWProperty(String name, String asset, String twAttribute, String metric);
	
	public void addTWAttribute();
	
	public void addMetricCalculationMethod(String name);
	
	public void addMetric(String name, String type, String calculationMethod);
	
	public void addControl(String name, String type);
	
	public void addCertifiedEvidence(String name, String metric, String value);
	
	public void addRuntimeEvidence(String name, String metric, String value);
	
	
}
