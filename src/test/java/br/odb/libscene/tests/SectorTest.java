/**
 * 
 */
package br.odb.libscene.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.gameutils.Direction;

/**
 * @author monty
 *
 */
public class SectorTest {

	/**
	 * http://stackoverflow.com/questions/19699634/coverage-for-private-constructor-junit-emma
	 * Test method for {@link br.odb.libscene.Sector#Sector()}.
	 */
	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<Sector> constructor = Sector.class.getDeclaredConstructor();
      Assert.assertFalse(Modifier.isPublic(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }
	
	/**
	 * Test method for {@link br.odb.libscene.Sector#Sector(java.lang.String)}.
	 */
	@Test
	public void testSectorString() {
		Sector sector = new Sector( "test" );
		
		Assert.assertNotNull( sector.links );
	}

	/**
	 * Test method for {@link br.odb.libscene.Sector#Sector(br.odb.libscene.SpaceRegion)}.
	 */
	@Test
	public void testSectorSpaceRegion() {
		Sector sector = new Sector( new SpaceRegion( "test" ) );
		
		Assert.assertEquals( "test", sector.id );
		
		Sector sector2 = new Sector( "test2" );
		
		for ( Direction d : Direction.values() ) {
			sector2.links[ d.ordinal() ] = d.simpleName;
		}
		
		sector = new Sector( sector2 );
		
		Assert.assertEquals( "test2", sector.id );
		
		for ( Direction d : Direction.values() ) {
			
			Assert.assertEquals( sector2.links[ d.ordinal() ], d.simpleName );
		}
	}

	/**
	 * Test method for {@link br.odb.libscene.Sector#Sector(br.odb.libscene.SpaceRegion, java.lang.String)}.
	 */
	@Test
	public void testSectorSpaceRegionString() {
		Sector sector = new Sector( new SpaceRegion( "test" ), "not-test" );
		
		Assert.assertEquals( "not-test", sector.id );

		Sector sector2 = new Sector( "test2" );
		
		for ( Direction d : Direction.values() ) {
			sector2.links[ d.ordinal() ] = d.simpleName;
		}
		
		sector = new Sector( sector2, "not-test2" );
		
		Assert.assertEquals( "not-test2", sector.id );
		
		for ( Direction d : Direction.values() ) {
			
			Assert.assertEquals( sector2.links[ d.ordinal() ], d.simpleName );
		}		
	}

	/**
	 * Test method for {@link br.odb.libscene.Sector#isParentEdge()}.
	 */
	@Test
	public void testIsParentEdge() {
			Sector s1 = new Sector( "s1" );			
			Sector s2 = new Sector( "s2" );
			
			s2.localPosition.set( 1.0f, 1.0f, 1.0f );
			s2.size.set( 5.0f, 5.0f, 5.0f );

			Assert.assertFalse( s1.isParentEdge() );
			Assert.assertFalse( s1.isParentEdgeAt( Direction.N ) );
			
			s1.parent = s2;
			s1.size.set( 1.0f, 1.0f, 1.0f );
			
			s1.localPosition.set( 0.0f, 1.0f, 2.0f );
			Assert.assertTrue( s1.isParentEdge() );
			
			s1.localPosition.set( 1.0f, 0.0f, 2.0f );
			Assert.assertTrue( s1.isParentEdge() );

			s1.localPosition.set( 1.0f, 2.0f, 0.0f );
			Assert.assertTrue( s1.isParentEdge() );

			s1.localPosition.set( 4.0f, 1.0f, 2.0f );
			Assert.assertTrue( s1.isParentEdge() );
			
			s1.localPosition.set( 1.0f, 4.0f, 2.0f );
			Assert.assertTrue( s1.isParentEdge() );

			s1.localPosition.set( 1.0f, 2.0f, 4.0f );
			Assert.assertTrue( s1.isParentEdge() );

			s1.localPosition.set( 2.0f, 2.0f, 2.0f );
			Assert.assertFalse( s1.isParentEdge() );
			

			s1.localPosition.set( -0.5f, 1.0f, 2.0f );
			Assert.assertFalse( s1.isParentEdge() );
			
			s1.localPosition.set( 1.0f, -0.5f, 2.0f );
			Assert.assertFalse( s1.isParentEdge() );

			s1.localPosition.set( 1.0f, 2.0f, -0.5f );
			Assert.assertFalse( s1.isParentEdge() );

			s1.localPosition.set( 4.5f, 1.0f, 2.0f );
			Assert.assertFalse( s1.isParentEdge() );
			
			s1.localPosition.set( 1.0f, 4.5f, 2.0f );
			Assert.assertFalse( s1.isParentEdge() );

			s1.localPosition.set( 1.0f, 2.0f, 4.5f );
			Assert.assertFalse( s1.isParentEdge() );			
			
	}
}
