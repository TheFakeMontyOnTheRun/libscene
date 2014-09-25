package br.odb.libscene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.odb.libstrip.Mesh;
import br.odb.utils.Direction;

public class GroupSector extends SpaceRegion {
	
	public GroupSector( String id ) {
		super( id );
		mesh = new Mesh( id );
	}
	
	public GroupSector(SpaceRegion region ) {
		super( region );
		mesh = new Mesh( id );
	}
	
	public void removeChild( SpaceRegion child ) {
		sons.remove( child );
		child.parent = null;
	}
	
	public void addChild( SpaceRegion region ) {
		
		if ( region.parent instanceof GroupSector ) {
			( ( GroupSector) region.parent ).sons.remove( region );
		}
		
		sons.add( region );
		region.parent = this;
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
	
	public List< SpaceRegion > getSons() {
		return sons;
	}
	
	public final Mesh mesh;
	public final HashMap< Direction, String > materials = new HashMap< Direction, String >();
	protected final ArrayList< SpaceRegion > sons = new ArrayList< SpaceRegion >();
}
