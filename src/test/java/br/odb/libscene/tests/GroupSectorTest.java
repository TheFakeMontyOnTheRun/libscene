package br.odb.libscene.tests;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class GroupSectorTest {
	
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
