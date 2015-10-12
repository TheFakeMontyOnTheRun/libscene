package br.odb.libscene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class World implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2015164462288643184L;

	public World(GroupSector built) {
		masterSector = built;
	}

	public World() {
		masterSector = new GroupSector("id");
	}

	private List<SceneNode> getAllRegionsAsList(GroupSector group) {

		List<SceneNode> toReturn = new ArrayList<>();

		toReturn.addAll(group.getSons());

		for (SceneNode sr : group.getSons()) {
			if (sr instanceof GroupSector) {
				toReturn.addAll(getAllRegionsAsList((GroupSector) sr));
			}
		}

		return toReturn;
	}

	/*
	 * Returns all children of master sector 
	 */
	public List<SceneNode> getAllRegionsAsList() {
		return getAllRegionsAsList(masterSector);
	}
	
	
//	public void checkForHardLinks_new() {
//		
//		GroupSector gs;
//		Sector s;
//		
//		List<SceneNode> sectors = getAllRegionsAsList(masterSector);
//		Map< Sector, List< Direction > > edgeSectors = new HashMap< Sector, List< Direction > >();
//		
//		Direction[] allDirections = Direction.values();
//		
//		for (SceneNode sr : sectors) {
//
//			if (sr instanceof GroupSector) {
//				
//				gs = ((GroupSector) sr );
//				
//				for ( SceneNode son : gs.getSons() ) {
//					
//					if ( son instanceof Sector ) {
//						s = ((Sector) son );
//						
//						for ( Direction d : allDirections ) {
//							if ( !s.isParentEdgeAt( d ) ) {
//								s.links[ d.ordinal() ] = s.id;
//							} else {
//								
//								if ( !edgeSectors.containsKey( s ) ) {
//									edgeSectors.put( s, new ArrayList< Direction >() );
//								}
//								
//								edgeSectors.get( s ).add( d );
//							}
//						}
//					}
//				}				
//			}
//		}
//		
//		for (SceneNode sr : sectors) {
//
//			if (sr instanceof GroupSector) {
//				
//				gs = ( (GroupSector ) sr );
//				
//				for ( Sector edge : edgeSectors.keySet() ) {
//					if ( sr instanceof SpaceRegion && edge.parent != sr && ((SpaceRegion)sr).intersects( edge ) ) {
//						for ( SceneNode son : ((GroupSector) sr).getSons() ) {
//							checkLinksForSectors((Sector) son, (Sector) edge );
//						}
//					}
//				}
//			}
//		}	
//	}

	public void checkForHardLinks() {
		
		List<SceneNode> sectors = getAllRegionsAsList(masterSector);
		int shouldBeSix = Direction.values().length;
		
		for (SceneNode s1 : sectors) {

			if ( !( s1 instanceof Sector ) ) {
				continue;
			}
			
			for ( int c = 0; c < shouldBeSix; ++c ) {
				((Sector)s1).links[ c ] = null;	
			}
				
			

			for (SceneNode s2 : sectors) {

				if (s2 == s1)
					continue;

				if ( !( s2 instanceof Sector )) {
					continue;
				}

				checkLinksForSectors((Sector) s1, (Sector) s2);
			}
		}
	}
	
	public SceneNode rayTrace( Vec3 position, Vec3 direction ) {
		return RayTracer.trace( masterSector, position, direction );
	}
	

	/**
	 * @param s1
	 * @param s2
	 */
	private void checkLinksForSectors(Sector s1, Sector s2) {

		Vec3 pos1 = s1.getAbsolutePosition();
		Vec3 pos2 = s2.getAbsolutePosition();

		float s1_x0 = (pos1.x);
		float s1_x1 = (pos1.x + s1.size.x);
		float s1_y0 = (pos1.y);
		float s1_y1 = (pos1.y + s1.size.y);
		float s1_z0 = (pos1.z);
		float s1_z1 = (pos1.z + s1.size.z);

		float s2_x0 = (pos2.x);
		float s2_x1 = (pos2.x + s2.size.x);
		float s2_y0 = (pos2.y);
		float s2_y1 = (pos2.y + s2.size.y);
		float s2_z0 = (pos2.z);
		float s2_z1 = (pos2.z + s2.size.z);

		if (	   br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
				
			if (br.odb.utils.Utils.eqFloat(s1_x1, s2_x0)) {
				
				s1.links[ Direction.E.ordinal() ] = s2.id;
				s2.links[ Direction.W.ordinal() ] = s1.id;
			}

			if (br.odb.utils.Utils.eqFloat(s1_x0, s2_x1)) {
				
				s1.links[ Direction.W.ordinal() ] = s2.id;
				s2.links[ Direction.E.ordinal() ] = s1.id;
			}
		}

		if (       br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)) {

			if (br.odb.utils.Utils.eqFloat(s1_y1, s2_y0)) {
				
				s2.links[ Direction.FLOOR.ordinal() ] = s1.id;
				s1.links[ Direction.CEILING.ordinal() ] = s2.id;
			}

			if (br.odb.utils.Utils.eqFloat(s1_y0, s2_y1)) {
				
				
				s2.links[ Direction.CEILING.ordinal() ] = s1.id;
				s1.links[ Direction.FLOOR.ordinal() ] = s2.id;
			}
		}

		if (	   br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {

			if (br.odb.utils.Utils.eqFloat(s1_z0, s2_z1)) {
				s2.links[ Direction.S.ordinal() ] = s1.id;
				s1.links[ Direction.N.ordinal() ] = s2.id;
			}

			if (br.odb.utils.Utils.eqFloat(s1_z1, s2_z0)) {
				
				s1.links[ Direction.S.ordinal() ] = s2.id;
				s2.links[ Direction.N.ordinal() ] = s1.id;
			}
		}
	}

	public final GroupSector masterSector;
}
