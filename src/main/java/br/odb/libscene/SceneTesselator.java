package br.odb.libscene;

import br.odb.libstrip.GeneralPolygon;
import br.odb.libstrip.GeneralQuad;
import br.odb.libstrip.GeneralTriangle;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SceneTesselator {

	public static World generateQuadsForWorld(World world) {
	
		generateMeshForSector( world.masterSector );
		
		return world;
	}

	private static void generateMeshForSector(GroupSector sector) {
//		GeneralQuad quad;
		sector.mesh.clear();
		
//		if ( sector.colorForDirection.get( Direction.CEILING ) != null ) {
//			quad = new GeneralQuad();
//			quad.vertex[ 0 ].set( sector.position );
//			quad.vertex[ 2 ].set( sector.position.add( sector.size ) );
//			quad.color.set( sector.colorForDirection.get( Direction.CEILING ) );
//			sector.mesh.addFace( quad );
//		}

//		if ( sector.colorForDirection.get( Direction.FLOOR ) != null ) {
//			quad = new GeneralQuad();
//			quad.vertex[ 0 ].set( sector.position );
//			quad.vertex[ 2 ].set( sector.position.add( sector.size ) );
//			quad.color.set( sector.colorForDirection.get( Direction.FLOOR ) );
//			quad.color.set( new Color( 255, 0, 0 ) );
//			sector.mesh.addFace( quad );
//		}
		Vec3 v0;
		Vec3 v1;
		Vec3 v2;
		GeneralPolygon	trig;
		
		v0 = new Vec3();
		v1 = new Vec3();
		v2 = new Vec3();
		
		v0.set( sector.position );
		v2.set( sector.position.add( sector.size ) );
		v2.y = v0.y;
		v1.set( v2.x, v0.y, v0.z );
		
		trig = new GeneralPolygon();
		trig.addVertex( v0 );
		trig.addVertex( v1 );
		trig.addVertex( v2 );
		
		if ( sector.colorForDirection.get( Direction.FLOOR ) != null ) {
			trig.color.set( sector.colorForDirection.get( Direction.FLOOR ) );
		} else {
			
			trig.color.set( new Color( 255, 0, 0 ) );
		}
		
		sector.mesh.addFace( trig );		
		
		
		v0 = new Vec3();
		v1 = new Vec3();
		v2 = new Vec3();
		
		v0.set( sector.position );
		v2.set( sector.position.add( sector.size ) );
		v2.y = v0.y;
		v1.set( v0.x, v0.y, v2.z );
		
		trig = new GeneralPolygon();
		trig.addVertex( v0 );
		trig.addVertex( v1 );
		trig.addVertex( v2 );
		
		if ( sector.colorForDirection.get( Direction.FLOOR ) != null ) {
			trig.color.set( sector.colorForDirection.get( Direction.FLOOR ) );
		} else {
			
			trig.color.set( new Color( 255, 0, 0 ) );
		}
		
		sector.mesh.addFace( trig );
		
		for ( SpaceRegion sr : sector.getSons() ) {
			if ( sr instanceof GroupSector ) {
				generateMeshForSector( (GroupSector) sr );
			}
		}
		
	}
}
