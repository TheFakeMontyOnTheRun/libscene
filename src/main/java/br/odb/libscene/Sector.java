package br.odb.libscene;

import java.util.HashMap;

import br.odb.utils.Direction;

public class Sector extends SpaceRegion {
	
	public Sector( String id ) {
		super( id );
	}
	
	public Sector(SpaceRegion region ) {
		super( region, region.id );
	}

	public final HashMap< Direction, Sector > connection = new HashMap< Direction, Sector >();
}
