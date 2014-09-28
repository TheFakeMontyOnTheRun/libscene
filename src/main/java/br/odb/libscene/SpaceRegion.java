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
	
	Vec3 getAbsolutePosition(SpaceRegion child) {
		
		if ( parent != null ) {
			return parent.getAbsolutePosition( this ).add( child.position );
		} else {
			return child.position;
		}
	}


	public SpaceRegion( String id ) {
		super( id );
	}
	
	public SpaceRegion(SpaceRegion region ) {
		super( region );
		
		size.set( region.size );
		description = region.description;
	}
	


	public static GroupSector getConvexHull( int snapLevel, Mesh mesh ) {

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

		sector.position.x = (center.x);
		sector.position.y = (center.y);
		sector.position.z = (center.z);
		sector.size.x = (center.x);
		sector.size.y = (center.y);
		sector.size.z = (center.z);
		
		for ( Vec3 p : mesh.points ) {

			if ( p.x < sector.position.x ) {
				sector.position.x = p.x;
			}

			if ( p.y < sector.position.y ) {
				sector.position.z = p.y;
			}

			if ( p.z < sector.position.z ) {
				sector.position.z = p.z;
			}			
		}

		
		for ( Vec3 p : mesh.points ) {

			if ( p.x > ( sector.size.x + sector.position.x ) ) {
				sector.size.x = ( p.x - sector.position.x );
			}

			if ( p.y > ( sector.size.y + sector.position.y ) ) {
				sector.size.y = ( p.y - sector.position.y );
			}
			
			if ( p.z > ( sector.size.z + sector.position.z ) ) {
				sector.size.z = ( p.z - sector.position.z );
			}
			
		}		
		
		if ( snapLevel > 0) {
			sector.position.x = Math.round( sector.position.x );
			sector.position.y = Math.round( sector.position.y );
			sector.position.z = Math.round( sector.position.z );
			sector.size.x = Math.round( sector.size.x );
			sector.size.y = Math.round( sector.size.y );
			sector.size.z = Math.round( sector.size.z );
		}
		
		return sector;
	}


	public boolean isDegenerate() {		
		return ( size.x <= 0.0f ) || ( size.y <= 0.0f ) || ( size.z <= 0.0f );
	}
	
	public boolean coincidant( SpaceRegion sector) {

		if (sector == null) {
			return false;
		}

		return position.equals( sector.position ) && size.equals( sector.size );
	}

	
	public boolean contains( Vec3 v ) {

		return ( ( position.x <= v.x ) && ( v.x <= ( position.x + size.x ) ) )
				&& ( ( position.y <= v.y ) && ( v.y <= ( position.y + size.y ) ) )
				&& ( ( position.z <= v.z ) && ( v.z <= ( position.z + size.z ) ) );
	}
	
}
