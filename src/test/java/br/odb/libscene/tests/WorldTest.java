/**
 * 
 */
package br.odb.libscene.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.World;
import br.odb.gameutils.Direction;

/**
 * @author monty
 *
 */
public class WorldTest {

	/**
	 * Test method for
	 * {@link br.odb.libscene.World#World(br.odb.libscene.GroupSector)} and
	 * {@link br.odb.libscene.World#World()}.
	 */
	@Test
	public void testWorldGroupSector() {
		GroupSector gs = new GroupSector("id");
		World world = new World(gs);

		assertEquals(gs, world.masterSector);

		world = new World();
		gs = new GroupSector("id");

		assertEquals(gs, world.masterSector);
	}

	/**
	 * Test method for {@link br.odb.libscene.World#getAllRegionsAsList()}.
	 */
	@Test
	public void testGetAllRegionsAsList() {
		GroupSector gs = new GroupSector("id");
		World world = new World(gs);

		SceneNode sr1 = new SceneNode("sr1");
		SceneNode sr2 = new SceneNode("sr2");
		SceneNode sr3 = new SceneNode("sr4");
		GroupSector gs2 = new GroupSector("gs2");
		SceneNode sr4 = new SceneNode("sr5");
		SceneNode sr5 = new SceneNode("sr6");

		gs2.addChild(sr4);
		gs2.addChild(sr5);

		gs.addChild(sr1);
		gs.addChild(sr2);
		gs.addChild(sr3);
		gs.addChild(gs2);

		// Yeah, no master sector!
		SceneNode[] expectedOrder = new SceneNode[] { sr1, sr2, sr3, gs2, sr4,
				sr5 };
		List<SceneNode> actualOrder = world.getAllRegionsAsList();

		assertEquals(expectedOrder.length, actualOrder.size());

		for (int c = 0; c < expectedOrder.length; ++c) {
			assertEquals(expectedOrder[c], actualOrder.get(c));
		}

	}

	/**
	 * Test method for {@link br.odb.libscene.World#checkForHardLinks()}.
	 */
	@Test
	public void testCheckForHardLinks() {

		World world = new World();

		GroupSector gs1 = new GroupSector("gs1");
		GroupSector gs2 = new GroupSector("gs2");

		Sector sr1 = new Sector("sr1");
		Sector sr2 = new Sector("sr2");
		Sector sr3 = new Sector("sr3");
		Sector sr4 = new Sector("sr4");

		Sector sr5 = new Sector("sr5");
		Sector sr6 = new Sector("sr6");
		Sector sr7 = new Sector("sr7");
		Sector sr8 = new Sector("sr8");
		
		Sector sr1b = new Sector("sr1b");
		Sector sr2b = new Sector("sr2b");
		Sector sr3b = new Sector("sr3b");
		Sector sr4b = new Sector("sr4b");

		Sector sr5b = new Sector("sr5b");
		Sector sr6b = new Sector("sr6b");
		Sector sr7b = new Sector("sr7b");
		Sector sr8b = new Sector("sr8b");
		

		gs2.addChild(sr5);
		gs2.addChild(sr6);
		gs2.addChild(sr7);
		gs2.addChild(sr8);

		gs1.addChild(sr1);
		gs1.addChild(sr2);
		gs1.addChild(sr3);
		gs1.addChild(sr4);
		
		gs2.addChild(sr5b);
		gs2.addChild(sr6b);
		gs2.addChild(sr7b);
		gs2.addChild(sr8b);

		gs1.addChild(sr1b);
		gs1.addChild(sr2b);
		gs1.addChild(sr3b);
		gs1.addChild(sr4b);
		

		world.masterSector.addChild(gs1);
		world.masterSector.addChild(gs2);

		sr2.localPosition.set(0.0f, 0.0f, 1.0f);
		sr3.localPosition.set(0.0f, 1.0f, 0.0f);
		sr4.localPosition.set(1.0f, 0.0f, 0.0f);

		sr5.localPosition.set(0.0f, 0.0f, -2.0f);
		sr6.localPosition.set(0.0f, -1.0f, -1.0f);
		sr7.localPosition.set(-1.0f, 0.0f, -1.0f);
		sr8.localPosition.set(0.0f, 0.0f, -1.0f);
		
		
		sr1b.localPosition.set(0.0f, 5.0f, 5.0f);
		sr2b.localPosition.set(5.0f, 0.0f, 5.0f);
		sr3b.localPosition.set(5.0f, 5.0f, 0.0f);
		sr4b.localPosition.set(5.0f, 5.0f, 5.0f);

		sr5b.localPosition.set(0.0f, -5.0f, -5.0f);
		sr6b.localPosition.set(-5.0f, 0.0f, -5.0f);
		sr7b.localPosition.set(-5.0f, -5.0f, 0.0f);
		sr8b.localPosition.set(-5.0f, -5.0f, -5.0f);
		

		world.checkForHardLinks();

		
		for ( int c = 0; c < Direction.values().length; ++c ) {
			assertNull( sr1b.links[ c ] );
			assertNull( sr2b.links[ c ] );
			assertNull( sr3b.links[ c ] );
			assertNull( sr4b.links[ c ] );
			assertNull( sr5b.links[ c ] );
			assertNull( sr6b.links[ c ] );
			assertNull( sr7b.links[ c ] );
			assertNull( sr8b.links[ c ] );
		}
		
		assertEquals("sr2", sr1.links[Direction.S.ordinal()]);
		assertEquals("sr3", sr1.links[Direction.CEILING.ordinal()]);
		assertEquals("sr4", sr1.links[Direction.E.ordinal()]);

		assertEquals("sr1", sr2.links[Direction.N.ordinal()]);
		assertEquals("sr1", sr3.links[Direction.FLOOR.ordinal()]);
		assertEquals("sr1", sr4.links[Direction.W.ordinal()]);

		assertEquals("sr5", sr8.links[Direction.N.ordinal()]);
		assertEquals("sr6", sr8.links[Direction.FLOOR.ordinal()]);
		assertEquals("sr7", sr8.links[Direction.W.ordinal()]);

		assertEquals("sr8", sr5.links[Direction.S.ordinal()]);
		assertEquals("sr8", sr6.links[Direction.CEILING.ordinal()]);
		assertEquals("sr8", sr7.links[Direction.E.ordinal()]);

		assertEquals("sr8", sr1.links[Direction.N.ordinal()]);
		assertEquals("sr1", sr8.links[Direction.S.ordinal()]);
	}
}
