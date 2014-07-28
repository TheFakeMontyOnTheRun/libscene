package br.odb.libscene.builder;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;

public class GroupSectorBuilder {
	
	public static GroupSector build( String[] token ) {
		
		SpaceRegion region = SpaceRegionBuilder.build( token );

		GroupSector toReturn = new GroupSector(); 
		
		toReturn.p0.set( region.p0 );
		toReturn.p1.set( region.p1 );
		
		return toReturn;
	}
}
