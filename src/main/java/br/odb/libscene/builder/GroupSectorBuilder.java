package br.odb.libscene.builder;

import java.io.IOException;
import java.io.InputStream;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;

public class GroupSectorBuilder extends SpaceRegionBuilder {
	
	public SpaceRegion build( InputStream is ) throws NumberFormatException, IOException {
		
		SpaceRegion region = super.build( is );
		GroupSector toReturn = new GroupSector( region );
		
		return toReturn;
	}
}
