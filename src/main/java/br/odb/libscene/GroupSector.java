package br.odb.libscene;

import java.util.ArrayList;
import java.util.HashMap;

import br.odb.utils.Direction;

public class GroupSector extends SpaceRegion {
	
	public GroupSector() {
	}
	
	public GroupSector(SpaceRegion region) {
		super( region );
	}
	
	
	public final HashMap< Direction, String > materials = new HashMap< Direction, String >();
	public final ArrayList< SpaceRegion > sons = new ArrayList< SpaceRegion >();
	String id;
}
