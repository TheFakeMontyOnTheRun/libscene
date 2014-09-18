package br.odb.libscene;

import java.util.HashMap;

import br.odb.utils.Direction;

public class Sector extends SpaceRegion {
	
	public final HashMap<Direction,Sector> colorForDirection = new HashMap<Direction,Sector>();
	
	public Sector( String id ) {
		super( id );
	}
	
	public Sector(SpaceRegion region ) {
		super( region );
	}

	public final HashMap< Direction, Sector > connection = new HashMap< Direction, Sector >();
}
