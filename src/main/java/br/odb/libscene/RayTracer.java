package br.odb.libscene;

import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class RayTracer {

	public static SceneNode trace(SpaceRegion region, Vec3 position,
			Vec3 direction) {

		SceneNode returned;
		
		if ( region instanceof SpaceRegion && collidesWithRegion( region, position, direction ) ) {
			if ( region instanceof GroupSector ) {
				for ( SceneNode sn :  ((GroupSector)region).getSons()) {
					if ( sn instanceof SpaceRegion ) {
						
						//please notice we're bringing the position vector into the region's subspace.
						returned = trace( ((SpaceRegion)sn), position.sub( region.localPosition ), direction );
						if ( returned != null ) {
							return returned;
						}
					}
				}
			}
			
			return region;
		}
		
		return null;
	}

	public static boolean collidesWithRegion(SpaceRegion region, Vec3 position, Vec3 direction) {
		// This is where the shit hits the fan
		
		if ( region.isInside( position ) && direction.isValid() && !direction.isZero() ) {
			//ok, that was easy. Even if very small, having a non-zero, valid,
			//direction and being inside, means that at one point, that line 
			//gotta touch the inside walls of the region.
			return true;
		} else {
			//here, I have a few options...I could go smart-ass and cheat
			//or I could go with math and decompose into planes and check for intersections.
			//I decided to go with math, for once.
			
			for ( Direction d : Direction.values() ) {
				
				if ( region instanceof Sector && ((Sector)region).links[ d.ordinal() ] == null ) {
					continue;
				}
				
				if ( hitPlane( region, d, position, direction ) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	public static  Vec3 getParametricVecFromRay( Vec3 origin, Vec3 direction, float t ) {
		Vec3 scaled = new Vec3( direction );
		scaled.scale( t );
		return origin.add( scaled );
	}
	
	
	public static boolean hitPlane(SpaceRegion region, Direction plane,
			Vec3 position, Vec3 direction) {
		
		Vec3 normal = null;
		Vec3 p0;
		Vec3 p1;
		float D; //because math.
		Vec3 pointInPlane;
		//Extracting each plane can, for once, be done with some trickery.
		//also, notice how N and S have the same plane, with only the normal switched around.
		switch ( plane ) {
		
		case N:
			normal = new Vec3( 0.0f, 0.0f, -1.0f );
			pointInPlane = region.localPosition;
			break;
		case S:
			normal = new Vec3( 0.0f, 0.0f, 1.0f );
			pointInPlane = region.localPosition.add( region.size );
			break;
			
		case W:
			normal = new Vec3( 1.0f, 0.0f, 0.0f );
			pointInPlane = region.localPosition;
			break;
		case E:
			normal = new Vec3( -1.0f, 0.0f, 0.0f );
			pointInPlane = region.localPosition.add( region.size );
			break;
			
		case FLOOR:
			normal = new Vec3( 0.0f, 1.0f, 0.0f );
			pointInPlane = region.localPosition;
			break;
		case CEILING:
			default:
			normal = new Vec3( 0.0f, -1.0f, 0.0f );
			pointInPlane = region.localPosition.add( region.size );
			break;
		}
		
		D = normal.scaled( -1.0f ).dotProduct( pointInPlane );

		float t = Math.max( 0.0f,  ( - ( normal.dotProduct( position ) + D )) / ( normal.dotProduct( direction ) ) );
		
		Vec3 projection = getParametricVecFromRay(position, direction, t);
		
		return region.isInside( projection );
	}
}
