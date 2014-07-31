package br.odb.libscene.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.odb.libscene.SpaceRegion;
import br.odb.utils.Utils;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {
	
	public static final String CMD_NEWREGION = "s";
	public static final String CMD_NEWMASTERREGION = "m";
		
	public SpaceRegion build( InputStream is ) throws NumberFormatException, IOException {
		
		
		BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
		SpaceRegion toReturn = new SpaceRegion();
		String line;
		String[] token;
		
		while ( ( line = br.readLine() ) != null ) {
			System.out.println( "got the lile: " + line + " at the SpaceRegionBuilder" );
			token = line.split(" ");
			
			if ( !token[ 0 ].equals( CMD_NEWREGION ) || token[ 0 ].equals( CMD_NEWMASTERREGION ) ) {
				return toReturn;
			} else {
				
				toReturn.p0.x = Float.parseFloat( token[ 1 ] );
				toReturn.p1.x = Float.parseFloat( token[ 2 ] );
				toReturn.p0.y = Float.parseFloat( token[ 3 ] );
				toReturn.p1.y = Float.parseFloat( token[ 4 ] );
				toReturn.p0.z = Float.parseFloat( token[ 5 ] );
				toReturn.p1.z = Float.parseFloat( token[ 6 ] );
			}
		}
		
		return toReturn;
	}
}
