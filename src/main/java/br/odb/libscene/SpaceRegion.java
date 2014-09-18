package br.odb.libscene;

import java.util.HashMap;

import br.odb.libstrip.Mesh;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;


public class SpaceRegion extends SceneNode {

	public final HashMap<Direction,Color> colorForDirection = new HashMap<Direction,Color>(); 
	public final Vec3 size = new Vec3();
	public String description;

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

//	public boolean pick(Vec3 vec) {
//		float x = vec.x;
//		float y = vec.y;
//		float z = vec.z;
//
//		boolean inside = false;
//
//		inside = Float.isNaN(x)
//				|| ((getX0() <= x || Utils.eqFloat(getX0(), x)) && x <= getX1() || Utils
//						.eqFloat(getX1(), x));
//
//		inside = inside
//				&& (Float.isNaN(y) || ((getY0() <= y || Utils.eqFloat(getY0(),
//						y)) && y <= getY1() || Utils.eqFloat(getY1(), y)));
//
//		inside = inside
//				&& (Float.isNaN(z) || ((getZ0() <= z || Utils.eqFloat(getZ0(),
//						z)) && z <= getZ1() || Utils.eqFloat(getZ1(), z)));
//
//		return inside;
//	}
	

	public boolean isDegenerate() {		
		return ( size.x <= 0.0f ) || ( size.y <= 0.0f ) || ( size.z <= 0.0f );
	}
	
	public boolean coincidant( SpaceRegion sector) {

		if (sector == null) {
			return false;
		}

		return position.equals( sector.position ) && size.equals( sector.size );
	}

	
//	public boolean contains(Vec3 vec) {
//		float x = vec.x;
//		float y = vec.y;
//		float z = vec.z;
//
//		boolean inside = false;
//
//		inside = ((getX0() <= x || Utils.eqFloat(getX0(), x)) && x <= getX1() || Utils
//				.eqFloat(getX1(), x))
//				&& ((getY0() <= y || Utils.eqFloat(getY0(), y)) && y <= getY1() || Utils
//						.eqFloat(getY1(), y))
//				&& ((getZ0() <= z || Utils.eqFloat(getZ0(), z)) && z <= getZ1() || Utils
//						.eqFloat(getZ1(), z));
//
//		return inside && !blockedByDoor(vec);
//	}
	
}
