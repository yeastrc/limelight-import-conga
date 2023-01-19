package org.yeastrc.limelight.xml.conga.annotation;

import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.limelight.xml.conga.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class PSMAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPSMAnnotationTypeSortOrder() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();

		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.CONGA_SCORE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_CONGA );
			annotations.add( annotation );
		}

		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.CONGA_PEPTIDE_RANK );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_CONGA );
			annotations.add( annotation );
		}
		
		return annotations;
	}


}
