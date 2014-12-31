package br.odb.libscene;

import java.util.HashMap;

import br.odb.utils.Direction;

public class Sector extends SpaceRegion {
	
	public Sector( String id ) {
		super( id );
	}
	
	public Sector(SpaceRegion region ) {
		super( region );
	}
	
	public Sector(SpaceRegion region, String newId ) {
		super( region, newId );
	}

	public final HashMap< Direction, Sector > connection = new HashMap< Direction, Sector >();
}
