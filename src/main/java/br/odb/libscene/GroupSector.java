package br.odb.libscene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import br.odb.libstrip.Material;
import br.odb.libstrip.Mesh;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class GroupSector extends SpaceRegion {
	
	public GroupSector( String id ) {
		super( id );
		mesh = new Mesh( id );
	}
	
	public GroupSector(SpaceRegion region ) {
		super( region );
		
		for ( Direction d : Direction.values() ) {
			if ( region.colorForDirection.get( d ) != null ) {
				colorForDirection.put( d, region.colorForDirection.get( d ) );
			}
		}
		
		mesh = new Mesh( id );
	}
	
	public void removeChild( SpaceRegion child ) {
		child.localPosition.set( getAbsolutePosition( child ) );
		sons.remove( child );
		child.parent = null;
	}
	

	public void addChild( SpaceRegion region ) {
		
		if ( region.parent instanceof GroupSector ) {
			( ( GroupSector) region.parent ).removeChild( region );
		}
		
		sons.add( region );
		
		region.localPosition.set( region.localPosition.sub( localPosition ) );
		region.parent = this;
	}
	
	public SpaceRegion pick( Vec3 v ) {
		
		SpaceRegion contained;
		
		if ( contains( v ) ) {
			
			for ( SpaceRegion sr : sons ) {
				
				if ( sr.contains(v) && sr instanceof GroupSector ) {
					
					contained = ((GroupSector)sr).pick( v );		
					
					if ( contained != null ) {
						
						return contained;
					}
				}
			}	 			
			
			return this;
		}
		
		return null;
	}
	
	
	public SpaceRegion getChild( String query ) {
		
		SpaceRegion descendant;
		
		for ( SpaceRegion child : sons ) {
			
			if ( child.id.equals( query ) ) {
				return child;
			}
			
			if ( child instanceof GroupSector ) {
				descendant = (( GroupSector) child ).getChild( query );
				
				if ( descendant != null ) {
					return descendant;
				}
			}
		}
		
		if ( id.equals( query  ) ) {
			return this;
		}
		
		return null;
	}
	
	public Set< SpaceRegion > getSons() {
		return sons;
	}
	
	public final Mesh mesh;
	public final HashMap< Direction, Material > materials = new HashMap< Direction, Material >();
	private final Set< SpaceRegion > sons = new HashSet< SpaceRegion >();
	
}
