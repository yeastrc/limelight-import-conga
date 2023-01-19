package org.yeastrc.limelight.xml.conga.objects;

import java.util.Map;

public class CongaResults {

	private Map<CongaReportedPeptide, Map<Integer, CongaPSM>> peptidePSMMap;

	/**
	 * @return the peptidePSMMap
	 */
	public Map<CongaReportedPeptide, Map<Integer, CongaPSM>> getPeptidePSMMap() {
		return peptidePSMMap;
	}
	/**
	 * @param peptidePSMMap the peptidePSMMap to set
	 */
	public void setPeptidePSMMap(Map<CongaReportedPeptide, Map<Integer, CongaPSM>> peptidePSMMap) {
		this.peptidePSMMap = peptidePSMMap;
	}

}
