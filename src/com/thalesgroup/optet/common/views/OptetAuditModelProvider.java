package com.thalesgroup.optet.common.views;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton of the OptetAuditModelProvider
 * @author F. Motte
 *
 */
public enum OptetAuditModelProvider {
INSTANCE;
	// list of audit entry
	private List<OptetAuditModel> audit;
	
	
	/**
	 * constructor
	 */
	private OptetAuditModelProvider() {
		audit = new ArrayList<OptetAuditModel>();
	}
	
	
	/**
	 * getAudit return the list of audit entry
	 * @return list of audit entry
	 */
	public List<OptetAuditModel> getAudit(){
		return audit;
	}
	
	/**
	 * addAuditEntry add a new audit enty in to internal list
	 * @param entry new entry
	 */
	public void addAuditEntry(OptetAuditModel entry){
		audit.add(entry);
	}
	
	/**
	 * cleanEntries clean the audit entry map
	 */
	public void cleanEntries(){
		audit.clear();
	}
}
