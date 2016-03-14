package br.odb.libscene.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.TriangleMesh;
import br.odb.gameutils.math.Vec3;

public class GroupSectorTest {
	
	
	/**
	 * http://stackoverflow.com/questions/19699634/coverage-for-private-constructor-junit-emma
	 * Test method for {@link br.odb.libscene.GroupSector#GroupSector()}.
	 */
	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<GroupSector> constructor = GroupSector.class.getDeclaredConstructor();
      Assert.assertFalse(Modifier.isPublic(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }
	
	@Test
	public final void testEqualsAndHashcode() {
		GroupSector gs1 = new GroupSector( "test1" );
		SpaceRegion sr = new SpaceRegion( "simple" );
		GroupSector gs2 = new GroupSector( "test1" );
		
		Assert.assertFalse( gs1.equals( null ) );
		Assert.assertFalse( gs1.equals( "Not a group sector" ) );
		Assert.assertFalse( gs1.equals( new Sector( "test1" ) ) );
		
		Assert.assertEquals( gs1.hashCode(), gs2.hashCode() );
		Assert.assertEquals( gs1, gs2 );
		
		gs1 = new GroupSector( "test1" );
		gs2 = new GroupSector( "test2" );				
		
		Assert.assertFalse( gs1.equals( gs2 ) );
		Assert.assertFalse( gs1.hashCode() == gs2.hashCode() );

		gs1 = new GroupSector( "test1" );
		gs2 = new GroupSector( "test1" );
		gs2.addChild( sr );
		Assert.assertFalse( gs1.equals( gs2 ) );
		Assert.assertFalse( gs2.equals( gs1 ) );
		Assert.assertFalse( gs1.hashCode() == gs2.hashCode() );		
		
		gs1 = new GroupSector( "test1" );
		gs2 = new GroupSector( "test1" );		
	}
	
	@Test
	public final void testGetChildAndPick() {
		
		SceneNode sn = new SceneNode( "node" );
		GroupSector gs1 = new GroupSector( "test1" );
		GroupSector gs2 = new GroupSector( "test2" );
		SpaceRegion sr = new SpaceRegion( "leaf" );
		SpaceRegion sr2 = new SpaceRegion( "leaf" );
		gs2.addChild( sr );
		gs2.addChild( sr2 );
		gs1.addChild( gs2 );
		gs1.addChild( sn );
		
		Assert.assertEquals( sr, gs2.getChild( "leaf" ) );
		Assert.assertEquals( sr, gs1.getChild( "leaf" ) );
		Assert.assertEquals( gs2, gs1.getChild( "test2" ) );
		Assert.assertEquals( gs1, gs1.getChild( "test1" ) );
		Assert.assertNull( gs1.getChild( "not included" ) );
		Assert.assertNull( gs1.getChild( null ) );
		
		//Pick tests
		
		gs1 = new GroupSector( "test1" );
		gs2 = new GroupSector( "test2" );
		sr = new SpaceRegion( "leaf1" );
		sr2 = new SpaceRegion( "leaf2" );
		gs1.localPosition.set( 1.0f, 2.0f, 3.0f );
		gs2.localPosition.set( 4.0f, 5.0f, 6.0f );
		gs2.addChild( sr );
		gs2.addChild( sr2 );
		gs1.addChild( gs2 );
		gs1.addChild( sn );
		
		gs1.size.set( 255, 255, 255 );
		gs2.size.set( 100, 100, 100 );
		sr.setPositionFromGlobal( new Vec3( 20.0f, 30.0f, 40.0f ) );
		sr2.setPositionFromGlobal( new Vec3( 10.0f, 20.0f, 30.0f ) );
		
		Assert.assertEquals( sr, gs1.pick( new Vec3( 20.5f, 30.5f, 40.5f ) ) );
		Assert.assertEquals( sr2, gs1.pick( new Vec3( 10.5f, 20.5f, 30.5f ) ) );
		
		Assert.assertEquals( gs1, gs1.pick( new Vec3( 1.05f, 2.05f, 3.05f ) ) );
		
		Assert.assertEquals( gs2, gs1.pick( new Vec3( 99.0f, 99.0f, 99.0f ) ) );
		
		Assert.assertEquals( gs1, gs1.pick( new Vec3( 203.0f, 203.0f, 203.0f ) ) );
		
		Assert.assertNull( gs1.pick( new Vec3( Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE ) ) );
		
	}
	
	@Test
	public final void testTallHierarchy() {
		GroupSector gs1 = new GroupSector( "s1" );
		GroupSector gs2 = new GroupSector( "s2" );
		GroupSector gs3 = new GroupSector( "s3" );
		GroupSector gs4 = new GroupSector( "s4" );
		
		gs1.localPosition.set( 1.0f, 2.0f, 3.0f );
		gs2.localPosition.set( 0.0f, 0.0f, 0.0f );
		gs3.localPosition.set( 10.0f, 10.0f, 10.0f );
		gs4.localPosition.set( 21.0f, 32.0f, 43.0f );
		
		gs1.addChild( gs2 );
		gs2.addChild( gs3 );
		gs3.addChild( gs4 );
		
		Assert.assertEquals( new Vec3( -1.0f, -2.0f, -3.0f ), gs2.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 10 ), gs3.localPosition );
		Assert.assertEquals( new Vec3( 11, 22, 33 ), gs4.localPosition );
	}
	
	@Test
	public final void testConvexHull() {
		TriangleMesh mesh = new TriangleMesh( "test" );
		GeneralTriangle trig = new GeneralTriangle();
		
		trig.x0 = -2.0f;
		trig.y0 = -3.0f;
		trig.z0 = -6.0f;
		
		trig.x1 = 5.0f;
		trig.y1 = -8.0f;
		trig.z1 = -4.0f;

		trig.x2 = 8.0f;
		trig.y2 = 7.0f;
		trig.z2 = 9.0f;
		
		mesh.faces.add( trig );
		GroupSector gs = GroupSector.getConvexHull( mesh );
		
		Assert.assertEquals( new Vec3( -2.0f, -8.0f, -6.0f ), gs.localPosition );
		Assert.assertEquals( new Vec3( 10.0f, 15.0f, 15.0f ), gs.size );

		mesh.faces.clear();
		
		gs = GroupSector.getConvexHull( mesh );
		
		Assert.assertEquals( new Vec3(), gs.localPosition );
		Assert.assertEquals( new Vec3( 1.0f, 1.0f, 1.0f ), gs.size );

		gs = GroupSector.getConvexHull( null );
		
		Assert.assertEquals( new Vec3(), gs.localPosition );
		Assert.assertEquals( new Vec3( 1.0f, 1.0f, 1.0f ), gs.size );		
	}
	
	
	@Test 
	public final void testAddChild() {
		GroupSector gs1 = new GroupSector( "test1" );
		
		
		gs1.localPosition.set( 11.0f, 21.0f, -4.0f );
		gs1.size.set( 12, 12, 12 );
		
		GroupSector gs2 = new GroupSector( gs1 );
		SpaceRegion sr2 = new SpaceRegion( gs1, "test-sr" );
		
		Assert.assertEquals( gs1, gs2 );
		Assert.assertTrue( gs1 != gs2 );
		Assert.assertTrue( gs1.getSons() != gs2.getSons() );
		
		Assert.assertEquals( gs1.localPosition, sr2.localPosition );
		Assert.assertEquals( sr2.localPosition, gs1.localPosition );
		
		
		///////// SR2 is owned by GS1 //////////
		gs1.addChild( sr2 );
		gs1.addChild( sr2 );
		Assert.assertEquals(gs1, sr2.parent );
		Assert.assertNotNull( gs1.getChild( sr2.id ) );
		Assert.assertEquals( new Vec3( 0, 0, 0), sr2.localPosition );
		Assert.assertEquals( sr2.localPosition, new Vec3( 0, 0, 0) );
		///////// SR2 is owned by GS2 //////////
		gs2.addChild( sr2 );
		Assert.assertEquals(gs2, sr2.parent );
		
		Assert.assertTrue( gs2.getSons().contains( sr2 ) );
		Assert.assertFalse( gs1.getSons().contains( sr2 ) );
		
		Assert.assertNotNull( gs2.getChild( sr2.id ) );
		Assert.assertNull( gs1.getChild( sr2.id ) );
	}
}
