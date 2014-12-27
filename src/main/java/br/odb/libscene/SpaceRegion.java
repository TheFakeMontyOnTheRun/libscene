package br.odb.libscene;

import java.util.HashMap;

import br.odb.libstrip.Mesh;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;


public class SpaceRegion extends SceneNode {

	public final HashMap<Direction,Color> colorForDirection = new HashMap<Direction,Color>(); 
	public final Vec3 size = new Vec3( 1.0f, 1.0f, 1.0f );
	public String description;
	public SpaceRegion parent;
	
	public Vec3 getAbsolutePosition() {
		if ( parent != null ) {
			return parent.getAbsolutePosition().add( localPosition );
		} else {
			return localPosition;
		}
	}
	
	Vec3 getAbsolutePosition(SpaceRegion child) {
		
		if ( parent != null ) {
			return parent.getAbsolutePosition( this ).add( child.localPosition );
		} else {
			return child.localPosition;
		}
	}


	public void setPositionFromGlobal( Vec3 pos ) {
		localPosition.set( pos.sub( parent.getAbsolutePosition() ) );
	}

	
	public SpaceRegion( String id ) {
		super( id );
	}
	
	public SpaceRegion(SpaceRegion region ) {
		super( region );
		
		size.set( region.size );
		description = region.description;
	}
	


	public static SpaceRegion getConvexHull( int snapLevel, Mesh mesh ) {

		GroupSector sector = new GroupSector( mesh.name );

		if (mesh.material != null) {
			
			for ( Direction d : Direction.values() ) {
				sector.colorForDirection.put( d, mesh.material.mainColor );
				System.out.println("d: " + d + " m: " + mesh.material.mainColor );
			}			
		} else {
			System.out.println( "Sector has no material for it's mesh" );
		}


		if ( mesh.points.size() < 1) {
			
			return sector;
		}

		// find the center point;
		Vec3 center = mesh.getCenter();

		// make the box stay at the center;

		sector.localPosition.x = (center.x);
		sector.localPosition.y = (center.y);
		sector.localPosition.z = (center.z);
		sector.size.x = (center.x);
		sector.size.y = (center.y);
		sector.size.z = (center.z);
		
		float x0 = center.x;
		float y0 = center.y;
		float z0 = center.z;
		float x1 = center.x;
		float y1 = center.y;
		float z1 = center.z;
		
		
		for ( Vec3 p : mesh.points ) {

			if ( p.x < x0 ) {
				x0 = p.x;
			}

			if ( p.y < y0 ) {
				y0 = p.y;
			}

			if ( p.z < z0 ) {
				z0 = p.z;
			}			

			if ( p.x > x1 ) {
				x1 = p.x;
			}

			if ( p.y > y1 ) {
				y1 = p.y;
			}
			
			if ( p.z > z1 ) {
				z1 = p.z;
			}			
		}	
		
		
		sector.localPosition.x = x0;
		sector.localPosition.y = y0;
		sector.localPosition.z = z0;
		
		sector.size.x = x1 - x0;
		sector.size.y = y1 - y0;
		sector.size.z = z1 - z0;
		
		if ( snapLevel > 0) {
			sector.localPosition.x = Math.round( sector.localPosition.x );
			sector.localPosition.y = Math.round( sector.localPosition.y );
			sector.localPosition.z = Math.round( sector.localPosition.z );
			sector.size.x = Math.round( sector.size.x );
			sector.size.y = Math.round( sector.size.y );
			sector.size.z = Math.round( sector.size.z );
		}
		
		return sector;
	}


	public boolean isDegenerate() {		
		return ( size.x <= 0.0f ) || ( size.y <= 0.0f ) || ( size.z <= 0.0f );
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((colorForDirection == null) ? 0 : colorForDirection
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpaceRegion other = (SpaceRegion) obj;
		if (colorForDirection == null) {
			if (other.colorForDirection != null)
				return false;
		} else if (!colorForDirection.equals(other.colorForDirection))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		
		if (localPosition == null) {
			if (other.localPosition != null)
				return false;
		} else if (!localPosition.equals(other.localPosition))
			return false;
		
		
		return true;
	}
	
	public boolean intersects( SpaceRegion another ) {
		
		if ( another == null ) {
			return false;
		}
		
		Vec3 pos = getAbsolutePosition();
		Vec3 otherPos = another.getAbsolutePosition();
		
		if ( this == another || this.equals( another ) ) {
			return true;
		}
		
		Vec3 p0 = pos;
		Vec3 p1 = pos.add( size );
		Vec3 anotherP0 = otherPos;
		Vec3 anotherP1 = otherPos.add( size );

		if ( contains( anotherP0.x, anotherP0.y, anotherP0.z ) ) { 
			return true;
		}		
		
		if ( contains( anotherP0.x, anotherP0.y, anotherP1.z ) ) { 
			return true;
		}
		
		if ( contains( anotherP0.x, anotherP1.y, anotherP0.z ) ) {
			return true;
		}
		
		
		if ( contains( anotherP0.x, anotherP1.y, anotherP1.z ) ) {
			return true;
		}
		
		
		if (contains( anotherP1.x, anotherP0.y, anotherP0.z ) ) {
			return true;
		}
		
		if (contains( anotherP1.x, anotherP0.y, anotherP1.z ) ) {
			return true;
		}	
	
		if ( contains( anotherP1.x, anotherP1.y, anotherP0.z ) ) {
			return true;
		}
		
		if ( contains( anotherP1.x, anotherP1.y, anotherP1.z ) ) {
			return true;
		}
		

		
		if (	anotherP0.x >= p0.x && 
				anotherP1.x <= p1.x && 
				anotherP0.y <= p0.y && 
				anotherP1.y >= p1.y  && 
				( 
						( p0.z <= anotherP0.z && anotherP0.z <= p1.z ) || 
						( p0.z <= anotherP1.z && anotherP1.z <= p1.z )  
				) 
		) {
			return true;
		}
		
		return false;
	}

	public boolean contains( float x, float y, float z ) {
		return contains( new Vec3( x, y, z ) );
	}
	
	public boolean contains( Vec3 v ) {

		Vec3 position = getAbsolutePosition();
		
		return ( ( position.x <= v.x ) && ( v.x <= ( position.x + size.x ) ) )
				&& ( ( position.y <= v.y ) && ( v.y <= ( position.y + size.y ) ) )
				&& ( ( position.z <= v.z ) && ( v.z <= ( position.z + size.z ) ) );
	}
	
}
