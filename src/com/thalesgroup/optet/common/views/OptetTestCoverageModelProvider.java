package com.thalesgroup.optet.common.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton of the OptetTestCoverageModelProvider
 * @author F. Motte
 *
 */
public enum OptetTestCoverageModelProvider {
INSTANCE;
	
	// list of audit entry	
	private List<OptetTestCoverageModel> audit;
	
	
	/**
	 * constructor
	 */
	private OptetTestCoverageModelProvider() {
		audit = new ArrayList<OptetTestCoverageModel>();
	}
	
	/**
	 * getTestCoverage return the list of test coverage entry
	 * @return list of the test coverage entry
	 */
	public List<OptetTestCoverageModel> getTestCoverage(){
		return audit;
	}
	
	
	/**
	 * addTestCoverageEntry add a new test coverage entry in to internal list
	 * @param entry new entry
	 */
	public void addTestCoverageEntry(OptetTestCoverageModel entry){
		audit.add(entry);
	}
	
	
	/**
	 * cleanEntries clean the test coverage entry map
	 */
	public void cleanEntries(){
		audit.clear();
	}
}
