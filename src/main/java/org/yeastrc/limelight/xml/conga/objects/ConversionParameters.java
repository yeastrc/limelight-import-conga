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

package org.yeastrc.limelight.xml.conga.objects;

import java.io.File;

public class ConversionParameters {

	public File getFastaFile() {
		return fastaFile;
	}

	public void setFastaFile(File fastaFile) {
		this.fastaFile = fastaFile;
	}

	public File getLimelightXMLOutputFile() {
		return limelightXMLOutputFile;
	}

	public void setLimelightXMLOutputFile(File limelightXMLOutputFile) {
		this.limelightXMLOutputFile = limelightXMLOutputFile;
	}

	public ConversionProgramInfo getConversionProgramInfo() {
		return conversionProgramInfo;
	}

	public void setConversionProgramInfo(ConversionProgramInfo conversionProgramInfo) {
		this.conversionProgramInfo = conversionProgramInfo;
	}

	public File getTargetsFile() {
		return targetsFile;
	}

	public void setTargetsFile(File targetsFile) {
		this.targetsFile = targetsFile;
	}

	private File fastaFile;
	private File limelightXMLOutputFile;
	private ConversionProgramInfo conversionProgramInfo;
	private File targetsFile;
}
