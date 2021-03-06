package br.odb.libscene;

import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.Material;
import br.odb.libstrip.TriangleMesh;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

import java.io.Serializable;
import java.util.*;

public class GroupSector extends SpaceRegion implements Serializable {

	public final List< SceneNode > sons = new ArrayList<>();
	public final Map< Direction, Material > shades = new HashMap<>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4269836844728197301L;
	
	GroupSector() {
		super();
	}
	
	public GroupSector( String id ) {
		super( id );
	}
	
	public GroupSector(SpaceRegion region ) {
		super( region );		
	}
	
	public void removeChild( SceneNode child ) {
		
		child.localPosition.set( child.getAbsolutePosition() );		
		child.parent = null;
		
		sons.remove( child );		
	}
	

	public void addChild( SceneNode region ) {
		
		if ( sons.contains( region ) ) {
			return;
		}		
		
		if ( region.parent instanceof GroupSector ) {
			( ( GroupSector) region.parent ).removeChild( region );
		}

		region.localPosition.set( region.getAbsolutePosition().sub( getAbsolutePosition() ) );
		region.parent = this;

        sons.add( region );
	}
	
	public SpaceRegion pick( Vec3 v ) {
		
		SpaceRegion contained;
		
		if ( isInside( v ) ) {
			
			for ( SceneNode sr : sons ) {
				
				if ( sr instanceof SpaceRegion ) {
					
					if ( ((SpaceRegion) sr ).isInside( v ) ) {
						
						if ( sr instanceof GroupSector  ) {
							
							contained = ((GroupSector)sr).pick( v );		
							
							if ( contained != null ) {
								
								return contained;
							}
						}
					
						return (SpaceRegion)sr;
					}
				}
			}	 			
			
			return this;
		}
		
		return null;
	}
	
	private static void maxVec( Vec3 v, float x, float y, float z ) {
		
		if ( v.x < x ) {
			v.x = x;
		}
		
		if ( v.y < y ) {
			v.y = y;
		}
		
		if ( v.z < z ) {
			v.z = z;
		}
	}
	
	private static void minVec( Vec3 v, float x, float y, float z ) {
		
		if ( v.x > x ) {
			v.x = x;
		}
		
		if ( v.y > y ) {
			v.y = y;
		}
		
		if ( v.z > z ) {
			v.z = z;
		}
	}
	
	
	public static GroupSector getConvexHull( TriangleMesh mesh ) {
		
		if ( mesh == null ) {
			return new GroupSector( "null" );
		}

		GroupSector sector = new GroupSector(mesh.name );
		MeshNode hull = new MeshNode( "hull", new TriangleMesh( "_mesh" ) );
		
		// find the center point;
		Vec3 center = mesh.getCenter();

		// make the box stay at the center;
		Vec3 p0 = new Vec3( center );
		Vec3 p1 = new Vec3( center );
		
		for ( GeneralTriangle f : mesh.faces) {

			minVec( p0, f.x0, f.y0, f.z0 );
			minVec( p0, f.x1, f.y1, f.z1 );
			minVec( p0, f.x2, f.y2, f.z2 );
			
			maxVec( p1, f.x0, f.y0, f.z0 );
			maxVec( p1, f.x1, f.y1, f.z1 );
			maxVec( p1, f.x2, f.y2, f.z2 );
			
			hull.materials.add( f.material );
		}

		for ( Material m : hull.materials ) {
			sector.shades.clear();
			
			for ( Direction d : Direction.values() ) {
				sector.shades.put( d, m );
			}
		}
		
		sector.localPosition.set( p0 );
		sector.size.set( p1.sub( p0 ) );

		return sector;
	}	
	
	public SceneNode getChild( String query ) {
		
		SceneNode descendant;
		
		for ( SceneNode child : sons ) {
			
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((shades == null) ? 0 : shades.hashCode());
		result = prime * result + ((sons == null) ? 0 : sons.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof GroupSector)) {
			return false;
		}
		GroupSector other = (GroupSector) obj;
		if (shades == null) {
			if (other.shades != null) {
				return false;
			}
		} else if (!shades.equals(other.shades)) {
			return false;
		}
		if (sons == null) {
			if (other.sons != null) {
				return false;
			}
		} else if (!sons.equals(other.sons)) {
			return false;
		}
		return true;
	}

	public Collection<SceneNode> getSons() {
		return sons;
	}
}
