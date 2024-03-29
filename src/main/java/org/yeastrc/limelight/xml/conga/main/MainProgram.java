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


import org.yeastrc.limelight.xml.conga.constants.Constants;
import org.yeastrc.limelight.xml.conga.objects.ConversionParameters;
import org.yeastrc.limelight.xml.conga.objects.ConversionProgramInfo;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@CommandLine.Command(name = "java -jar " + Constants.CONVERSION_PROGRAM_NAME,
		mixinStandardHelpOptions = true,
		version = Constants.CONVERSION_PROGRAM_NAME + " " + Constants.CONVERSION_PROGRAM_VERSION,
		sortOptions = false,
		synopsisHeading = "%n",
		descriptionHeading = "%n@|bold,underline Description:|@%n%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		description = "Convert the results of a CONGA analysis to a Limelight XML file suitable for import into Limelight.\n\n" +
				"More info at: " + Constants.CONVERSION_PROGRAM_URI
)

/**
 * @author Michael Riffle
 * @date Jan 18, 2023
 *
 */
public class MainProgram implements Runnable {

	@CommandLine.Option(names = { "-f", "--fasta-file" }, required = true, description = "Full path to FASTA file used in the experiment. E.g., /data/yeast.fa")
	private File fastaFile;

	@CommandLine.Option(names = { "-t", "--conga-target_mods" }, required = true, description = "Full path to the CONGA \".target_mods.txt\" file (results) E.g., /data/results/conga.target_mods.txt.")
	private File targetsFile;

	@CommandLine.Option(names = { "-l", "--conga-log" }, required = true, description = "Full path to the CONGA log file E.g., /data/results/conga.log.txt")
	private File logFile;

	@CommandLine.Option(names = { "-o", "--out-file" }, required = true, description = "Full path to use for the Limelight XML output file. E.g., /data/my_analysis/crux.limelight.xml")
	private File outFile;

	@CommandLine.Option(names = { "-v", "--verbose" }, required = false, description = "If this parameter is present, error messages will include a full stacktrace. Helpful for debugging.")
	private boolean verboseRequested = false;

	private String[] args;

	public void run() {

		printRuntimeInfo();

		if( !logFile.exists() ) {
			System.err.println( "Could not find log file: " + logFile.getAbsolutePath() );
			System.exit( 1 );
		}

		if( !targetsFile.exists() ) {
			System.err.println( "Could not find targets file: " + targetsFile.getAbsolutePath() );
			System.exit( 1 );
		}

		if( !fastaFile.exists() ) {
			System.err.println( "Could not find Fasta file: " + fastaFile.getAbsolutePath() );
			System.exit( 1 );
		}

		ConversionProgramInfo cpi = ConversionProgramInfo.createInstance( String.join( " ",  args ) );        

		ConversionParameters cp = new ConversionParameters();
		cp.setConversionProgramInfo( cpi );
		cp.setFastaFile( fastaFile );
		cp.setTargetsFile( targetsFile );
		cp.setLogFile(logFile);
		cp.setLimelightXMLOutputFile( outFile );

		try {
			ConverterRunner.createInstance().convertCongaTSVToLimelightXML(cp);
		} catch(Throwable t) {

			System.err.println("Error running conversion: " + t.getMessage());

			if(verboseRequested) {
				t.printStackTrace();
			}

			System.exit(1);
		}


		System.exit( 0 );
	}

	public static void main( String[] args ) {

		MainProgram mp = new MainProgram();
		mp.args = args;

		CommandLine.run(mp, args);
	}


	/**
	 * Print runtime info to STD ERR
	 * @throws Exception 
	 */
	public static void printRuntimeInfo() {

		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "run.txt" ) ) ) ) {

			String line = null;
			while ( ( line = br.readLine() ) != null ) {

				line = line.replace( "{{URL}}", Constants.CONVERSION_PROGRAM_URI );
				line = line.replace( "{{VERSION}}", Constants.CONVERSION_PROGRAM_VERSION );

				System.err.println( line );
				
			}
			
			System.err.println( "" );

		} catch ( Exception e ) {
			System.out.println( "Error printing runtime information." );
		}
	}
}
