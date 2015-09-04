package com.thalesgroup.optet.common.views;

import org.eclipse.core.resources.IFile;

/**
 * @author F. Motte
 *
 */

public class OptetAuditModel {

	// the tool which find the problem
	private String tool;
	// the file where the problem is
	private IFile file;
	// the line where the problem is
	private int line;
	// the severity of the problem
	private String severity;
	// the ruleset of the problem (category)
	private String ruleset;
	// the message of the problem
	private String message;
	// the recommendation to solve the problem
	private String recommandation;
	// the source which found the problem
	private String source;
	
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	/**
	 * @return the file where the problem is
	 */
	public IFile getFile() {
		return file;
	}

	
	/**
	 * @param file the file where the problem is
	 */
	public void setFile(IFile file) {
		this.file = file;
	}


	/**
	 * @return the tool which find the problem
	 */
	public String getTool() {
		return tool;
	}


	/**
	 * @param tool the tool which find the problem
	 */
	public void setTool(String tool) {
		this.tool = tool;
	}


	/**
	 * @return the line where the problem is
	 */
	public int getLine() {
		return line;
	}

	
	/**
	 * @param line the line where the problem is
	 */
	public void setLine(int line) {
		this.line = line;
	}

	
	/**
	 * @return the severity of the problem
	 */
	public String getSeverity() {
		return severity;
	}

	
	/**
	 * @param severity the severity of the problem
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	
	/**
	 * @return the message of the problem
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * @param message the message of the problem
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * @return the ruleset of the problem (category)
	 */
	public String getRuleset() {
		return ruleset;
	}


	/**
	 * @param ruleset the ruleset of the problem (category)
	 */
	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}


	/**
	 * @return the recommendation to solve the problem
	 */
	public String getRecommandation() {
		return recommandation;
	}


	/**
	 * @param recommandation the recommendation to solve the problem
	 */
	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}


	/**
	 * Constructor
	 * @param tool the tool which find the problem
	 * @param file the file where the problem is
	 * @param line the line where the problem is
	 * @param severity the severity of the problem
	 * @param ruleset the ruleset of the problem (category)
	 * @param message the message of the problem
	 * @param recommandation the recommendation to solve the problem
	 * @param source 
	 */
	public OptetAuditModel(String tool, IFile file, int line, String severity,
			String ruleset, String message, String recommandation, String source) {
		super();
		this.tool = tool;
		this.file = file;
		this.line = line;
		this.severity = severity;
		this.ruleset = ruleset;
		this.message = message;
		this.recommandation = recommandation;
		this.source = source;
	}




}
