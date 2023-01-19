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

package org.yeastrc.limelight.xml.conga.main;

import org.yeastrc.limelight.xml.conga.builder.XMLBuilder;
import org.yeastrc.limelight.xml.conga.objects.CongaResults;
import org.yeastrc.limelight.xml.conga.objects.ConversionParameters;
import org.yeastrc.limelight.xml.conga.reader.ResultsParser;

public class ConverterRunner {

	// quickly get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }
	
	
	public void convertCongaTSVToLimelightXML(ConversionParameters conversionParameters ) throws Throwable {

		System.err.print( "Reading search results (" + conversionParameters.getTargetsFile().getName() + ") into memory..." );
		CongaResults congaResults = ResultsParser.getResults(conversionParameters.getTargetsFile());
		System.err.println( " Done." );

		System.err.print( "Writing out XML..." );
		(new XMLBuilder()).buildAndSaveXML( conversionParameters, congaResults );
		System.err.println( " Done." );

		System.err.print( "Validating Limelight XML..." );
		LimelightXMLValidator.validateLimelightXML(conversionParameters.getLimelightXMLOutputFile());
		System.err.println( " Done." );
		
	}
}
