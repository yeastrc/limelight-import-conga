/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.conga.reader;

import org.yeastrc.limelight.xml.conga.objects.CongaPSM;
import org.yeastrc.limelight.xml.conga.objects.CongaReportedPeptide;
import org.yeastrc.limelight.xml.conga.objects.CongaResults;
import org.yeastrc.limelight.xml.conga.objects.OpenModification;
import org.yeastrc.limelight.xml.conga.utils.ReportedPeptideUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Michael Riffle
 *
 */
public class ResultsParser {

	public static CongaResults getResults(File targetsFile) throws Throwable {

		CongaResults results = new CongaResults();
		Map<CongaReportedPeptide,Map<Integer,CongaPSM>> resultMap = new HashMap<>();
		results.setPeptidePSMMap(resultMap);

		try(BufferedReader br = new BufferedReader(new FileReader( targetsFile ))) {

			String headerLine = br.readLine();
			Map<String, Integer> columnMap = processHeaderLine(headerLine);

			for(String line = br.readLine(); line != null; line = br.readLine()) {
				CongaPSM psm = getPSMFromLine(line, columnMap);
				CongaReportedPeptide reportedPeptide = ReportedPeptideUtils.getReportedPeptideForPSM( psm );

				if( !results.getPeptidePSMMap().containsKey( reportedPeptide ) )
					results.getPeptidePSMMap().put( reportedPeptide, new HashMap<>() );

				results.getPeptidePSMMap().get( reportedPeptide ).put( psm.getScanNumber(), psm );
			}
		}

		return results;
	}

	/**
	 * Get a map of column headers to the index of that column on the line
	 *
	 * @param headerLine
	 * @return
	 */
	private static Map<String, Integer> processHeaderLine(String headerLine) {
		Map<String, Integer> columnMap = new HashMap<>();

		String[] fields = headerLine.split("\\t", -1);
		for(int i = 0; i < fields.length; i++) {
			columnMap.put(fields[i], i);
		}

		return columnMap;
	}

	/**
	 *
	 * @param line
	 * @param columnMap
	 * @return
	 * @throws Exception
	 */
	private static CongaPSM getPSMFromLine(String line, Map<String, Integer> columnMap) throws Exception {

		String[] fields = line.split("\\t", -1);

		final String[] requiredHeaders = new String[] {
				"scan",
				"charge",
				"score",
				"delta_mass",
				"rank",
				"search_file",
				"peptide",
				"spectrum_neutral_mass"
		};

		for(String requiredHeader : requiredHeaders) {
			if(!columnMap.containsKey(requiredHeader)) {
				throw new RuntimeException("Could not find column for \"" + requiredHeader + "\"");
			}
		}

		final int scanNumber = Integer.parseInt(fields[columnMap.get("scan")]);
		final byte charge = Byte.parseByte(fields[columnMap.get("charge")]);
		final BigDecimal score = new BigDecimal(fields[columnMap.get("score")]);
		final Integer rank = Math.round(Float.valueOf(fields[columnMap.get("rank")]));
		final String searchFile = fields[columnMap.get("search_file")];
		final String reportedPeptideString = fields[columnMap.get("peptide")];
		final BigDecimal deltaMass = new BigDecimal(fields[columnMap.get("delta_mass")]);
		final BigDecimal observedNeutralMass = new BigDecimal(fields[columnMap.get("spectrum_neutral_mass")]);

		CongaPSM psm = new CongaPSM();

		psm.setScanNumber(scanNumber);
		psm.setCharge(charge);
		psm.setScore(score);
		psm.setSearchFile(searchFile);
		psm.setPeptideRank(rank);
		psm.setObservedNeutralMass(observedNeutralMass);

		if(searchFile.equals("narrow")) {
			psm.setDeltaMass(deltaMass);
		} else if(searchFile.equals("open")) {
			psm.setOpenModification( new OpenModification(deltaMass, new HashSet<>()));
			psm.setDeltaMass(BigDecimal.ZERO);
		} else {
			throw new RuntimeException("\"search_file\" must be \"narrow\" or \"open\"");
		}

		// handle peptide string
		psm.setPeptideSequence(getPeptideSequenceFromReportedPeptideString(reportedPeptideString));

		// handle var mods
		psm.setMods(getVariableModsFromReportedPeptideString(reportedPeptideString));

		return psm;
	}

	/**
	 * Get peptide sequence with mod information removed
	 * @param reportedPeptideString
	 * @return
	 */
	private static String getPeptideSequenceFromReportedPeptideString(String reportedPeptideString) {
		return reportedPeptideString.toUpperCase().replaceAll("[^A-Z]", "");
	}

	private static Map<Integer, BigDecimal> getVariableModsFromReportedPeptideString(String reportedPeptideString) {
		Map<Integer, BigDecimal> mods = new HashMap<>();

		return mods;
	}



	}
