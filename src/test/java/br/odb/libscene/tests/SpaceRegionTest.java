package br.odb.libscene.tests;



import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class SpaceRegionTest {
	
	@Test 
	public final void testAddChild() {
		GroupSector sr1 = new GroupSector( "test1" );
		
		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
		sr1.size.set( 12, 12, 12 );
		
		SpaceRegion sr2 = new SpaceRegion( sr1 );
		
		Assert.assertEquals( sr1.localPosition, sr2.localPosition );
		Assert.assertEquals( sr2.localPosition, sr1.localPosition );
		
		sr1.addChild( sr2 );
		
		Assert.assertEquals( new Vec3( 0, 0, 0), sr2.localPosition );
		Assert.assertEquals( sr2.localPosition, new Vec3( 0, 0, 0) );

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
	
	
//	@Test
//	public final void testIntersectCorners() {
//		Rect rect1 = new Rect( 0.0f, 0.0f, 2.0f, 2.0f );
//		Rect rect2 = new Rect( 1.0f, 1.0f, 2.0f, 2.0f );
//
//		//   ___
//		//  | 1_|_
//		//  |_| 2 |
//		//    |___|
//		//
//		rect2.set( 1.0f, 1.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		//    ___
//		//   | 1 |
//		//   |___|___
//		//    	 | 2 |
//		//  	 |___|
//		//	
//		rect2.set( 2.0f, 2.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		
//		//  	  ___
//		//     __| 1 |
//		//    | 2 |__|
//		//    |___|
//		//
//		rect2.set( -1.0f, 1.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		//  	  ___
//		//       | 1 |
//		//     __| __|		
//		//    | 2 |
//		//    |___|
//		//
//		rect2.set( -2.0f, 2.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//
//		//       ____________
//		//     	 |	  ___	|
//		//    	 |2  | 1 |  |
//		//   	 |   |___|  |
//		//  	 |__________|
//		//	
//		rect2.set( -5.0f, -5.0f, 10.0f, 10.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		
//
//		//            ___
//		//       ____|   |___
//		//     	 |	 |   |  |
//		//    	 |1  | 2 |  |
//		//   	 |   |   |  |
//		//  	 |___|   |__|
//		//           |___|	
//		rect2.set( 1.0f, -1.0f, 0.5f, 10.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		
//		
//		//   ___
//		//  | 2_|_
//		//  |_| 1 |
//		//    |___|
//		//
//		rect2.set( -1.0f, -1.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		//    ___
//		//   | 2 |
//		//   |___|___
//		//    	 | 1 |
//		//  	 |___|
//		//	
//		rect2.set( -2.0f, -2.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		
//		//  	  ___
//		//     __| 2 |
//		//    | 1 |__|
//		//    |___|
//		//
//		rect2.set( 1.0f, -1.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		//  	  ___
//		//       | 2 |
//		//     __| __|		
//		//    | 1 |
//		//    |___|
//		//
//		rect2.set( 2.0f, -2.0f, 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.intersect( rect2 ) );
//		Assert.assertTrue( rect2.intersect( rect1 ) );
//		
//	}
//	
//	@Test
//	public final void testPointsInside() {
//		
//		Rect rect1 = new Rect( 0.0f, 0.0f, 2.0f, 2.0f );
//		Vec2 point = new Vec2();
//		
//		point.set( 0.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) ); 
//		point.set( 0.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 1.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 1.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 1.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 0.0f, 1.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//
//		rect1.set( 0.5f, 0.5f, 1.0f, 1.0f );
//		
//		point.set( 0.0f, 0.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 2.0f, 0.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 0.0f, 2.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 2.0f, 2.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 1.0f, 0.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 2.0f, 1.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 1.0f, 2.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//		point.set( 0.0f, 1.0f );		
//		Assert.assertFalse( rect1.isInside(point) );
//
//
//		rect1.set( -10.0f, -10.f, 20.0f, 20.0f );
//
//		point.set( 0.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 0.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 1.0f, 0.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 2.0f, 1.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 1.0f, 2.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		point.set( 0.0f, 1.0f );		
//		Assert.assertTrue( rect1.isInside(point) );
//		
//	}
//	
//	@Test
//	public final void testDisjointRectsIntersection() {
//		Rect rect1 = new Rect( 0.0f, 0.0f, 2.0f, 2.0f );
//		Rect rect2 = new Rect( 3.0f, 0.0f, 2.0f, 2.0f );
//		
//		rect2.set( 0.0f, -3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//		
//		rect2.set( 3.0f, -3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( 3.0f, 0.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( 3.0f, 3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( 0.0f, -3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( -3.0f, 3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( -3.0f, 0.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		rect2.set( -3.0f, -3.0f, 2.0f, 2.0f );
//		Assert.assertFalse( rect1.intersect( rect2 ) );
//		Assert.assertFalse( rect2.intersect( rect1 ) );
//
//		
//		
//	}
}
