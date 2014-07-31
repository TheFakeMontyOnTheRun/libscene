package br.odb.libscene.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;

public class SectorBuilder extends SpaceRegionBuilder {

	private static final String CMD_LINK = "p";
	private static final String CMD_PARENT = "n";

	public SpaceRegion build( InputStream is ) throws NumberFormatException, IOException {
		//Como transformar uma region em um sector?!
		SpaceRegion region = super.build( is );
		
		Sector toReturn = new Sector( region );
		
		BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
		String line;
		String[] token;
		br.mark( 255 );
		while ( ( line = br.readLine() ) != null ) {

			token = line.split(" ");
			
			if ( token[ 0 ].equals( CMD_PARENT ) ) {
				
			} else if ( token.equals( CMD_LINK ) ) {
				
			} else {
				br.reset();
				//Mas aí eu já vou ter consumido o símbolo..
				return toReturn;
			}
		}
		
		return toReturn;
	}
}
