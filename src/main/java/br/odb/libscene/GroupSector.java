package br.odb.libscene;

import java.util.ArrayList;
import java.util.HashMap;

import br.odb.utils.Direction;

public class GroupSector extends SpaceRegion {
	
	public GroupSector( String id ) {
		super( id );
	}
	
	public GroupSector(SpaceRegion region ) {
		super( region, region.id );
	}
	
	public void addChild( SpaceRegion region ) {
		sons.add( region );
	}
	
	public SpaceRegion getChild( String id ) {
		
		SpaceRegion descendant;
		
		for ( SpaceRegion child : sons ) {
			
			if ( child.id.equals( id ) ) {
				return child;
			}
			
			if ( child instanceof GroupSector ) {
				descendant = (( GroupSector) child ).getChild(id);
				
				if ( descendant != null ) {
					return descendant;
				}
			}
		}
		
		return null;
	}
	
	
	public final HashMap< Direction, String > materials = new HashMap< Direction, String >();
	public final ArrayList< SpaceRegion > sons = new ArrayList< SpaceRegion >();
	String id;
}
