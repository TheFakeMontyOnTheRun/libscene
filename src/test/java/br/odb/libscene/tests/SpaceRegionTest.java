package br.odb.libscene.tests;



import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class SpaceRegionTest {
	
	@Test
	public final void testEquals() {
		
		SpaceRegion sr1 = new SpaceRegion( "test1" );
		SpaceRegion sr2 = new SpaceRegion( "test1" );
		
		Assert.assertEquals( sr1, sr2 );
		
		sr1.localPosition.set( 1.0f, 2.0f, 3.0f );
		sr2.localPosition.set( sr1.localPosition );
		
		sr1.size.set( 4.0f, 5.0f, 6.0f );
		sr2.size.set( sr1.size );
		
		Assert.assertEquals( sr1, sr2 );
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
		sr1.localPosition.set( 0.0f, 0.0f, 0.0f );
		sr1.size.set( 2.0f, 2.0f, 2.0f );
		SpaceRegion sr2 = new SpaceRegion( "sr2" );
		sr2.localPosition.set( 1.0f, 1.0f, 0.0f );
		sr2.size.set( 2.0f, 2.0f, 2.0f );

		//   ___
		//  | 1_|_
		//  |_| 2 |
		//    |___|
		//
		sr2.localPosition.set( 1.0f, 1.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		//    ___
		//   | 1 |
		//   |___|___
		//    	 | 2 |
		//  	 |___|
		//	
		sr2.localPosition.set( 2.0f, 2.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		//  	  ___
		//     __| 1 |
		//    | 2 |__|
		//    |___|
		//
		sr2.localPosition.set( -1.0f, 1.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		//  	  ___
		//       | 1 |
		//     __| __|		
		//    | 2 |
		//    |___|
		//
		sr2.localPosition.set( -2.0f, 2.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );

		//       ____________
		//     	 |	  ___	|
		//    	 |2  | 1 |  |
		//   	 |   |___|  |
		//  	 |__________|
		//	
		sr2.localPosition.set( -5.0f, -5.0f, -5.0f );
		sr2.size.set( 10.0f, 10.0f, 10.0f );		
		Assert.assertTrue( sr2.intersects( sr1 ) );
		

		//            ___
		//       ____|   |___
		//     	 |	 |   |  |
		//    	 |1  | 2 |  |
		//   	 |   |   |  |
		//  	 |___|   |__|
		//           |___|	
//		sr2.localPosition.set( 1.0f, -1.0f, 0.0f );
//		sr2.size.set( 0.5f, 10.0f, 2.0f );		
//		Assert.assertTrue( sr1.intersects( sr2 ) );
//		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		
		//   ___
		//  | 2_|_
		//  |_| 1 |
		//    |___|
		//
		sr2.localPosition.set( -1.0f, -1.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		//    ___
		//   | 2 |
		//   |___|___
		//    	 | 1 |
		//  	 |___|
		//	
		sr2.localPosition.set( -2.0f, -2.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		//  	  ___
		//     __| 2 |
		//    | 1 |__|
		//    |___|
		//
		sr2.localPosition.set( 1.0f, -1.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		//  	  ___
		//       | 2 |
		//     __| __|		
		//    | 1 |
		//    |___|
		//
		sr2.localPosition.set( 2.0f, -2.0f, 0.0f );		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
	}
	
	@Test
	public final void testPointsInside() {
		
		SpaceRegion sr1 = new SpaceRegion("test");
		sr1.localPosition.set( 0.0f, 0.0f, 0.0f );
		sr1.size.set( 2.0f, 2.0f, 2.0f );
		
		Vec3 point = new Vec3();
		
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
