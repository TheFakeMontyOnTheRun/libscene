package br.odb.libscene.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.CameraNode;
import br.odb.libscene.SceneNode;
import br.odb.libscene.SpaceRegion;
import br.odb.gameutils.math.Vec3;

public class SpaceRegionTest {
	
	@Test
	public final void testIsDegenerate() {
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		Assert.assertFalse( sr1.isDegenerate() );
		Assert.assertEquals( sr1.size, new Vec3( 1.0f, 1.0f, 1.0f ) );
		
		sr1.size.set( 0.0f, 0.0f, 0.0f );
		Assert.assertTrue( sr1.isDegenerate() );
		
		sr1.size.set( -1.0f, 0.0f, 0.0f );
		Assert.assertTrue( sr1.isDegenerate() );

		sr1.size.set( 1.0f, -1.0f, 0.0f );
		Assert.assertTrue( sr1.isDegenerate() );

		sr1.size.set( 1.0f, 1.0f, -1.0f );
		Assert.assertTrue( sr1.isDegenerate() );

		
		sr1.size.set( Float.NaN, 0.0f, 0.0f );
		Assert.assertTrue( sr1.isDegenerate() );

		sr1.size.set( 1.0f, Float.NEGATIVE_INFINITY, 0.0f );
		Assert.assertTrue( sr1.isDegenerate() );

		sr1.size.set( 1.0f, 1.0f, Float.POSITIVE_INFINITY );
		Assert.assertTrue( sr1.isDegenerate() );

		sr1.size.set( Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE );
		Assert.assertFalse( sr1.isDegenerate() );
		
		
		sr1.size.set( 1.0f, 0.1f, 0.5f );
		Assert.assertFalse( sr1.isDegenerate() );
		
		sr1.size.set( Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE );
		Assert.assertFalse( sr1.isDegenerate() );
		
	}
	
	@Test
	public final void testCoincidant() {
		
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		SpaceRegion sr2 = new SpaceRegion( "test1" );
		SpaceRegion sr3 = new SpaceRegion( "parent" );
		
		sr3.localPosition.set( 1.0f, 1.0f, 1.0f );
		sr1.localPosition.set( 1.0f, 2.0f, 3.0f );
		sr2.localPosition.set( sr1.localPosition );
		sr1.size.set( 4.0f, 5.0f, 6.0f );
		sr2.size.set( sr1.size );
		
		sr1.localPosition.set( 0.0f, 1.0f, 2.0f );
		
		Assert.assertFalse( sr1.coincidant( sr2 ) );
		
		sr1.parent = sr3;
		
		Assert.assertTrue( sr1.coincidant( sr2 ) );
		
		sr2.size.set( 6.0f, 5.0f, 4.0f );
		Assert.assertFalse( sr1.coincidant( sr2 ) );
	}
	
	@Test
	public final void testEquals() {
		
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		SpaceRegion sr2 = new SpaceRegion( "test1" );
		
		Assert.assertEquals( sr1, sr2 );
		sr1.localPosition.set( 1.0f, 2.0f, 3.0f );
		sr2.localPosition.set( sr1.localPosition );
		
		sr1.size.set( 4.0f, 5.0f, 6.0f );
		sr2.size.set( sr1.size );
		Assert.assertTrue( sr1.hashCode() == sr2.hashCode() );
		Assert.assertEquals( sr1, sr2 );
		Assert.assertEquals( sr1, sr1 );
		Assert.assertFalse( sr1.equals( null ) );
		Assert.assertFalse( sr1.equals( new SceneNode( sr1 )  ) );
		Assert.assertFalse( sr1.equals( new CameraNode( "test1" )  ) );
		Assert.assertFalse( sr1.equals( "Not even a Space Region!" ) );
		
		
		sr2.size.set( 6.0f, 5.0f, 4.0f );
		Assert.assertFalse( sr1.equals( sr2 ) );
		Assert.assertFalse( sr1.hashCode() == sr2.hashCode() );
	}
	
	@Test
	public final void testInside() {
		
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		SpaceRegion sr2 = new SpaceRegion( "test2" );
		
		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
		sr1.size.set( 12, 12, 12 );
		sr2.localPosition.set( 31.0f, 21.0f, 3.0f );
		sr2.size.set( 12, 12, 12 );
		
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );
		
	}
	
	
	@Test
	public final void testIntersectCorners() {
		
		SpaceRegion sr1 = new SpaceRegion("sr1");
		SpaceRegion sr2 = new SpaceRegion( sr1, "sr1");
		
		Assert.assertTrue( sr1.intersects( sr1 ) );
		Assert.assertTrue( sr1.intersects( sr2 ) );
		
		for ( float x = -1.0f; x <= 1.0f; x += 0.1f ) {
			
			sr2.localPosition.x = x;
			
			for ( float y = -1.0f; y <= 1.0f; y += 0.1f ) {
				
				sr2.localPosition.y = y;
				
				for ( float z = -1.0f; z <= 1.0f; z += 0.1f ) {
					
					sr2.localPosition.z = z;

					for ( float dx = 1.0f; dx <= 2.0f; dx += 0.1f ) {
						
						sr2.size.x = dx;
						
						for ( float dy = 1.0f; dy <= 2.0f; dy += 0.1f ) {
							
							sr2.size.y = dy;
							
							for ( float dz = 1.0f; dz <= 2.0f; dz += 0.1f ) {
								
								sr2.size.z = dz;
								
								Assert.assertTrue( sr1.intersects( sr2 ) );		
							}
						}
					}
				}
			}
		}
		
		sr1.localPosition.set( 0.0f, 0.0f, 0.0f );
		sr2.localPosition.set( 0.0f, 0.0f, 0.0f );
		

		
		
		sr2.localPosition.set( 0.3f, -1.0f, -1.0f );
		sr2.size.set( 0.3f, 2.0f, 2.0f );
		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		sr2.localPosition.set( -1.0f, 0.3f, -1.0f );		
		sr2.size.set( 2.0f, 0.3f, 2.0f );

		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		sr2.localPosition.set( -1.0f, -1.0f, 0.3f );		
		sr2.size.set( 2.0f, 2.0f, 0.3f );

		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		

		sr2.localPosition.set( 0.3f, 0.3f, 0.3f );		
		sr2.size.set( 0.3f, 0.3f, 0.3f );

		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );

		
		sr2.localPosition.set( 0.3f, -2.0f, 0.3f );		
		sr2.size.set( 0.3f, 2.0f, 0.3f );

		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr1.intersects( null ) );
	}
	
	//http://stackoverflow.com/questions/19699634/coverage-for-private-constructor-junit-emma
	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<SpaceRegion> constructor = SpaceRegion.class.getDeclaredConstructor();
      Assert.assertFalse(Modifier.isPublic(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }
	
	@Test
	public final void testGetLocalCenter() {
		SpaceRegion sr1 = new SpaceRegion("test", new Vec3( 2.0f, 2.0f, 2.0f ) );
		SpaceRegion sr2 = new SpaceRegion( sr1  );
		sr2.id = "parent";
		sr1.localPosition.set( 1.0f, 1.0f, 1.0f );
		sr2.localPosition.set( 1.0f, 1.0f, 1.0f );
		sr1.parent = sr2;
		
		Assert.assertEquals( new Vec3( 2.0f, 2.0f, 2.0f ), sr1.getLocalCenter() );
	}

	@Test
	public final void testGetGlobalCenter() {
		SpaceRegion sr1 = new SpaceRegion("test");
		SpaceRegion sr2 = new SpaceRegion("test2");
		sr1.localPosition.set( 1.0f, 1.0f, 1.0f );
		sr2.localPosition.set( 1.0f, 1.0f, 1.0f );
		sr1.parent = sr2;
		sr1.size.set( 2.0f, 2.0f, 2.0f );
		
		Assert.assertEquals( new Vec3( 3.0f, 3.0f, 3.0f ), sr1.getAbsoluteCenter() );
	}
	
	
	@Test
	public final void testPointsInside() {
		
		SpaceRegion sr1 = new SpaceRegion("test");
		sr1.localPosition.set( 0.0f, 0.0f, 0.0f );
		sr1.size.set( 2.0f, 2.0f, 2.0f );
		
		Vec3 point = new Vec3();

		point.set( -1.0f, 0.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );

		point.set( 3.0f, 0.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );

		point.set( 0.0f, -1.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );

		point.set( 0.0f, 3.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );

		point.set( 0.0f, 0.0f, -1.0f );		
		Assert.assertFalse( sr1.isInside(point) );

		point.set( 0.0f, 0.0f, 3.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		
		
		point.set( 0.0f, 0.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 0.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) ); 
		point.set( 0.0f, 2.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 2.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 1.0f, 0.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 1.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 1.0f, 2.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 0.0f, 1.0f, 1.0f );		
		Assert.assertTrue( sr1.isInside(point) );

		sr1.localPosition.set( 0.5f, 0.5f, 0.5f );
		sr1.size.set( 1.0f, 1.0f, 1.0f );
		
		point.set( 0.0f, 0.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 2.0f, 0.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 0.0f, 2.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 2.0f, 2.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 1.0f, 0.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 2.0f, 1.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 1.0f, 2.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );
		point.set( 0.0f, 1.0f, 1.0f );		
		Assert.assertFalse( sr1.isInside(point) );


		sr1.localPosition.set( -10.0f, -10.f, -10.0f );
		sr1.size.set( 20.0f, 20.0f, 20.0f );

		point.set( 0.0f, 0.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 0.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 0.0f, 2.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 2.0f, 2.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 1.0f, 0.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 2.0f, 1.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 1.0f, 2.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		point.set( 0.0f, 1.0f, 0.0f );		
		Assert.assertTrue( sr1.isInside(point) );
		
	}
	
	@Test
	public final void testDisjointRectsIntersection() {
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		sr1.localPosition.set( 0.0f, 0.0f, 0.0f );
		sr1.size.set( 2.0f, 2.0f, 2.0f );
		
		SpaceRegion sr2 = new SpaceRegion( "test2" );
		sr2.localPosition.set( 3.0f, 0.0f, 0.0f );
		sr2.size.set( 2.0f, 2.0f, 2.0f );
		
		sr2.localPosition.set( 0.0f, -3.0f, 0.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );
		
		sr2.localPosition.set( 3.0f, -3.0f, -3.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( 3.0f, 0.0f, 0.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( 3.0f, 3.0f, 3.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( 0.0f, -3.0f, 0.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( -3.0f, 3.0f, 3.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( -3.0f, 0.0f, 0.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );

		sr2.localPosition.set( -3.0f, -3.0f, -3.0f );
		Assert.assertFalse( sr1.intersects( sr2 ) );
		Assert.assertFalse( sr2.intersects( sr1 ) );
	}
}
