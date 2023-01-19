package org.yeastrc.limelight.xml.conga.utils;

import org.yeastrc.limelight.xml.conga.objects.CongaPSM;
import org.yeastrc.limelight.xml.conga.objects.CongaReportedPeptide;

public class ReportedPeptideUtils {

	public static CongaReportedPeptide getReportedPeptideForPSM(CongaPSM psm ) throws Exception {
		
		CongaReportedPeptide rp = new CongaReportedPeptide();
		
		rp.setNakedPeptide( psm.getPeptideSequence() );
		rp.setMods( psm.getMods() );
		rp.setReportedPeptideString( ModParsingUtils.getRoundedReportedPeptideString( psm.getPeptideSequence(), psm.getMods() ));

		return rp;
	}

}
