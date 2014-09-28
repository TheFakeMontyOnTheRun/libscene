package br.odb.libscene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		mesh = new Mesh( id );
	}
	
	public void removeChild( SpaceRegion child ) {
		child.position.set( getAbsolutePosition( child ) );
		sons.remove( child );
		child.parent = null;
	}
	

	public void addChild( SpaceRegion region ) {
		
		if ( region.parent instanceof GroupSector ) {
			( ( GroupSector) region.parent ).removeChild( region );
		}
		
		sons.add( region );
		
		Vec3 absolute = new Vec3();
		
		if ( parent != null ) {
			absolute.set( parent.getAbsolutePosition( this ) );
		}
		
		absolute.set( absolute.sub( region.position ) );
		
		region.position.set( absolute );
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
	
	public List< SpaceRegion > getSons() {
		return sons;
	}
	
	public final Mesh mesh;
	public final HashMap< Direction, String > materials = new HashMap< Direction, String >();
	private final ArrayList< SpaceRegion > sons = new ArrayList< SpaceRegion >();
}
