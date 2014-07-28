package br.odb.libscene.builder;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;

public class SectorBuilder {
	public static Sector build( String[] token ) {
		
		SpaceRegion region = SpaceRegionBuilder.build( token );

		Sector toReturn = new Sector(); 
		
		toReturn.p0.set( region.p0 );
		toReturn.p1.set( region.p1 );
		
		return toReturn;
	}
}
