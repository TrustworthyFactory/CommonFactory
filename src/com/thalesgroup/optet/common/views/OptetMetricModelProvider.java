package com.thalesgroup.optet.common.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton of the OptetMetricModelProvider
 * @author F. Motte
 *
 */
public enum OptetMetricModelProvider {
INSTANCE;
	
	// list of audit entry
	private Map<String, OptetMetricModel> audit;
	
	/**
	 * constructor
	 */
	private OptetMetricModelProvider() {
		audit = new HashMap<String, OptetMetricModel>();
	}
	
	/**
	 * getAudit return the list of audit entry
	 * @return list of audit entry
	 */
	public List<OptetMetricModel> getAudit(){
		return new ArrayList(audit.values());
	}
	
	/**
	 * addAuditEntry add a new audit enty in to internal list
	 * @param metric 
	 * @param entry new entry
	 */
	public void addAuditEntry(String metric, OptetMetricModel entry){
		if (audit.containsKey(metric))
			audit.remove(metric);
		audit.put(metric, entry);
	}
	
	/**
	 * cleanEntries clean the audit entry map
	 */
	public void cleanEntries(){
		audit.clear();
	}
}
