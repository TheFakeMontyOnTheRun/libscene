package br.odb.libscene.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.Direction;

public class SerializationTest {
	
	Object serializeAndDesserialize( Serializable s ) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput output;
		output = new ObjectOutputStream( baos );
		output.writeObject( s );
		InputStream is = new ByteArrayInputStream( baos.toByteArray() );
		ObjectInput input = new ObjectInputStream( is );
		return input.readObject();
	}
	
	@Test
	public void testSimpleSectorSerialization() {
		Sector s1 = new Sector( "sector1" );
		Sector s2 = new Sector( "sector2" );
		GroupSector gs = new GroupSector( "parent" );
		
		s1.localPosition.set( 1.0f, 2.0f, 3.0f );
		s1.size.set( 4.0f, 5.0f, 6.0f );
		gs.addChild( s1 );

		s2.localPosition.set( 5.0f, 2.0f, 3.0f );
		s2.size.set( 4.0f, 5.0f, 6.0f );
		gs.addChild( s2 );
		
		s1.links[ Direction.E.ordinal() ] = s2.id;
		s2.links[ Direction.W.ordinal() ] = s1.id;
		
		try {
			Sector s3 = (Sector) serializeAndDesserialize( s1 );
			Sector s4 = (Sector) serializeAndDesserialize( s2 );
			Assert.assertEquals( s1.links[ Direction.E.ordinal() ], s3.links[ Direction.E.ordinal() ] );
			Assert.assertEquals( s2.links[ Direction.W.ordinal() ], s4.links[ Direction.W.ordinal() ] );
			Assert.assertEquals( s2, s4 );
			Assert.assertEquals( s2, s4 );
	
			
			GroupSector gs2 = (GroupSector) serializeAndDesserialize( gs );
			Assert.assertEquals( gs, gs2 );
			
			for ( SpaceRegion node : gs2.getSons() ) {
				Assert.assertTrue( gs2 == node.parent );
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
	
	@Test
	public void testSimpleSpaceRegionSerialization() {
		SpaceRegion sr1 = new SpaceRegion( "sr1" );
		SceneNode parent = new SceneNode( "parent" );
		
		sr1.parent = parent;
		sr1.localPosition.set( 1.0f, 2.0f, 3.0f );
		sr1.size.set( 4.0f, 5.0f, 6.0f );
		
		try {
			SpaceRegion sr2 = (SpaceRegion) serializeAndDesserialize( sr1 );
			Assert.assertEquals( sr1, sr2 );
			Assert.assertTrue( sr1 != sr2 );
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSimpleSceneNodeSerialization() {

		SceneNode node = new SceneNode( "test" );
		SceneNode parent = new SceneNode( "parent" );
		
		node.localPosition.set( 1.0f, 2.0f, 3.0f );
		node.parent = parent;
		try {
			SceneNode node2 = (SceneNode) serializeAndDesserialize( node );
			Assert.assertEquals( node, node2 );
			Assert.assertTrue( node != node2 );
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
