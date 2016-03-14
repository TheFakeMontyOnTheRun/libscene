package br.odb.libscene.tests;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.RayTracer;
import br.odb.libscene.SpaceRegion;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

public class RayTracerTest {

	@Test
	public void testHitPlane() {
		SpaceRegion region = new SpaceRegion( "test" );
		region.localPosition.set( 1.0f, 1.0f, 1.0f );
		region.size.set( 1.0f, 1.0f, 1.0f );
		
		//from inside:
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( -1.0f, 0.0f, -1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( 0.0f, 0.0f, -1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( 1.0f, 0.0f, -1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( 0.0f, 0.0f, -0.5f ) ) );
		
		//The point of intersection would go outside the boundaries of the N plane
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( -1.0f, 0.0f, -0.5f ) ) );
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( -1.0f, 0.0f, -0.5f ) ) );
		
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( 0.0f, 0.0f, 0.0f ) ) );
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3() ) );
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 1.5f ), new Vec3( Float.NaN, 1.0f, 1.0f ) ) );
		
		
		//from outside:
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 4.5f ), new Vec3( 0.0f, 0.0f, 1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 4.5f ), new Vec3( 0.0f, 0.0f, -1.0f ) ) );	
		
		//from z = 0, shooting towards the front of the plane
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 0.0f ), new Vec3( -0.5f, 0.0f, 1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 0.0f ), new Vec3( -0.25f, 0.0f, 1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 0.0f ), new Vec3( 0.0f, 0.0f, 1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 0.0f ), new Vec3( 0.25f, 0.0f, 1.0f ) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.N, new Vec3( 1.5f, 1.5f, 0.0f ), new Vec3( 0.5f, 0.0f, 1.0f ) ) );

		//from the side
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f,  -0.75f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f,  -0.5f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f, -0.25f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f, 0.0f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f, 0.25f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f, 0.5f ) ) );
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.E, new Vec3( 2.5f, 1.5f, 1.5f ), new Vec3( -0.5f, 0.0f,  0.75f) ) );		

		
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( -0.75f, -0.5f,  -0.75f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( -0.5f, -0.5f,  -0.5f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( -0.25f, -0.5f, -0.25f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( 0.0f, -0.5f, 0.0f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( 0.25f, -0.5f, 0.25f) ) );
		Assert.assertTrue( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( 0.5f, -0.5f, 0.5f ) ) );
		Assert.assertFalse( RayTracer.hitPlane( region, Direction.CEILING, new Vec3( 1.5f, 2.5f, 1.5f ), new Vec3( 0.75f, -0.5f,  0.75f) ) );		
	}
}
