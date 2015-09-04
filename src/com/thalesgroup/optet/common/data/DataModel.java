/*
 *		OPTET Factory
 *
 *	Class DataModel 1.0 12 nov. 2013
 *
 *	Copyright (c) 2013 Thales Communications & Security SAS
 *	4, Avenue des Louvresses - 92230 Gennevilliers 
 *	All rights reserved
 *
 */

package com.thalesgroup.optet.common.data;

import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.eclipse.core.resources.IFile;

import com.thalesgroup.optet.common.jaxb.optet.OptetType.Metrics.Metric;

/**
 * @author F. Motte
 *
 */
public interface DataModel {

	public abstract void addSumTestResult(String globalTestName, int nbRun,
			int nbFailed, int nbIgnored, int nbError, int nbOK, int nbUndefined);

	public abstract void addTestResult(String testName, String result);

	/**
	 * addClassesTestCoverage set the metric around class coverage
	 * @param total
	 * @param covered
	 * @param pourcentage
	 */
	public abstract void addClassesTestCoverage(int total, int covered,
			double pourcentage);

	/**
	 * addMethodsTestCoverage set the metric around method coverage
	 * @param total
	 * @param covered
	 * @param pourcentage
	 */
	public abstract void addMethodsTestCoverage(int total, int covered,
			double pourcentage);

	/**
	 * addLinesTestCoverage set the metric around line coverage
	 * @param total
	 * @param covered
	 * @param pourcentage
	 */
	public abstract void addLinesTestCoverage(int total, int covered,
			double pourcentage);

	/**
	 * addBlocksTestCoverage set the metric around block coverage
	 * @param total
	 * @param covered
	 * @param pourcentage
	 */
	public abstract void addBlocksTestCoverage(int total, int covered,
			double pourcentage);

	/**
	 * addInstructionsTestCoverage set the metric around instruction coverage
	 * @param total
	 * @param covered
	 * @param pourcentage
	 */
	public abstract void addInstructionsTestCoverage(int total, int covered,
			double pourcentage);

	/**
	 * addMetrics add new metric into the report
	 * @param metric name of the metric
	 * @param value value of the metric
	 */
	public abstract void addMetrics(String metric, String value, String type, String category);

	public abstract void addExpectedMetric(String metric, String value, String twAttribute);
	
	public abstract String getExpectedMetric(String metric, String twAttribute);
	
	public abstract String getMetric(String metric);
	
	public abstract void addExpectedTWAttribute(String twAttribute, String value);
	
	public abstract Set<String> getEvidenceForTWAttribute(String twAttribute);
	
	public abstract String getExpectedTWAttribute(String twAttribute);
	
	public abstract List<Metric> getMetrics();
	
	/**
	 * addError add new error into the report
	 * @param file
	 * @param tool
	 * @param line
	 * @param severity
	 * @param ruleset
	 * @param message
	 * @param recommandation
	 */
	public abstract void addError(IFile file, String tool, int line,
			Severity severity, String ruleset, String message,
			String recommandation, String source);

	/**
	 * generateXML generate the XML report of the audit
	 * @throws JAXBException
	 */
	public abstract void generateXML() throws JAXBException;

	/**
	 * cleanAudit clean the audit part
	 */
	public abstract void cleanAudit();

	/**
	 * cleanMetric clean the metric part
	 */
	public abstract void cleanMetric();
	public abstract void cleanExpectedMetric();

	/**
	 * cleanTestCoverage clean the test coverage part
	 */
	public abstract void cleanTestCoverage();

	/**
	 * 
	 */
	public abstract void computeRulesMetric();
	
	public abstract void configureRulesMetric(String metric, int nbrules);

	public abstract void computeMetric();
	public abstract String computeTWAttribute(String twAttribute);
	
	public abstract String getTWAttributeID(String name);
	
	public abstract String getEvidenceID(String name);
	public abstract String getTWAttributeName(String name);
	
	public abstract String getEvidenceName(String name);
	
	
	public abstract Set<String> getAllTWAttributeName();
	
	
	
	public abstract void setJMLRACResult(String res);
	public abstract void setJMLCompResult(String res);
	public abstract void setJMLESCResult(String res);
	
	public abstract String getJMLRACResult();
	public abstract String getJMLCompResult();
	public abstract String getJMLESCResult();
	
	public abstract List<String> getJMLProgress();
	public abstract void resetJMLProgress();
	public abstract void addJMLProgress(String progress);
	
	
}