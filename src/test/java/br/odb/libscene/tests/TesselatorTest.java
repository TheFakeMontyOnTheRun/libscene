package br.odb.libscene.tests;

import org.junit.Assert;
import org.junit.Test;

import br.odb.utils.Direction;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.Sector;
import br.odb.libscene.SceneTesselator;
import br.odb.utils.math.Vec3;

public class TesselatorTest {

    public static final int TRIANGLES_PER_WALL = 2;
	
	@Test 
	public final void testExtractPolysOnlyFromParentInHierarchy() {
		GroupSector sr1 = new GroupSector( "test1" );
		Sector s1 = new Sector( sr1 );
		sr1.addChild( s1 );

		for ( Direction d : Direction.values() ) {
		    Assert.assertEquals( 0, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		}

		SceneTesselator.generateSubSectorMeshForSector( sr1 );
		Assert.assertEquals( 6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size() );
	}

	@Test 
	public final void testExtractPolysParentAndSubSectorsInHierarchy() {
		GroupSector sr1 = new GroupSector( "test1" );
		sr1.size.set( 3.0f, 3.0f, 3.0f );
		Sector s1 = new Sector( sr1 );
		Sector s2 = new Sector( sr1 );
		sr1.addChild( s1 );
		sr1.addChild( s2 );

		s2.localPosition.z = sr1.size.z / 2.0f;
		s2.size.z /= 2.0f;
		s1.size.z /= 2.0f;

		s1.connection.put( Direction.S, s2 );
		s2.connection.put( Direction.N, s1 );

		for ( Direction d : Direction.values() ) {
		    Assert.assertEquals( 0, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		}

		SceneTesselator.generateSubSectorMeshForSector( sr1 );
		Assert.assertEquals( 6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size() );
	}

	@Test 
	public final void testExtractPolysParentAndSubSectorsWithForeignLinksInHierarchy() {
		GroupSector sr1 = new GroupSector( "test1" );
		sr1.size.set( 3.0f, 3.0f, 3.0f );
		Sector s1 = new Sector( sr1 );
		Sector s2 = new Sector( sr1 );
		sr1.addChild( s1 );
		sr1.addChild( s2 );

		s2.localPosition.z = sr1.size.z / 2.0f;
		s2.size.z /= 2.0f;
		s1.size.z /= 2.0f;

		s1.connection.put( Direction.S, s2 );
		s2.connection.put( Direction.N, s1 );
		s2.connection.put( Direction.FLOOR, s2 );


		for ( Direction d : Direction.values() ) {

		    if ( d == Direction.FLOOR ) {
			Assert.assertEquals( 1, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		    } else {
			Assert.assertEquals( 0, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		    }
		}

		SceneTesselator.generateSubSectorMeshForSector( sr1 );
		Assert.assertEquals( 6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size() );
	}


	@Test 
	public final void testExtractPolysParentAndSubSectorsWithSmallForeignLinksInHierarchy() {
		GroupSector sr1 = new GroupSector( "test1" );
		sr1.size.set( 3.0f, 3.0f, 3.0f );
		Sector s1 = new Sector( sr1 );
		Sector s2 = new Sector( sr1 );
		Sector s3 = new Sector( sr1 );
		Sector s4 = new Sector( sr1 );
		sr1.addChild( s1 );
		sr1.addChild( s2 );
		sr1.addChild( s3 );
		sr1.addChild( s4 );

		s2.localPosition.z = sr1.size.z / 2.0f;
		s4.localPosition.z = sr1.size.z / 2.0f;
		s2.size.z /= 2.0f;
		s1.size.z /= 2.0f;
		s3.size.z /= 2.0f;
		s4.size.z /= 2.0f;

		s3.localPosition.x = sr1.size.x / 2.0f;
		s4.localPosition.x = sr1.size.x / 2.0f;

		s2.connection.put( Direction.FLOOR, s2 );


		for ( Direction d : Direction.values() ) {

		    if ( d == Direction.FLOOR ) {
			Assert.assertEquals( 1, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		    } else {
			Assert.assertEquals( 0, SceneTesselator.foreignLinksInDirection( d, sr1 ) );
		    }
		}

		SceneTesselator.generateSubSectorMeshForSector( sr1 );
		Assert.assertEquals( 6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size() );
	}

}

