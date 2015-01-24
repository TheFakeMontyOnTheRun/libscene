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

import br.odb.libscene.SceneNode;
import br.odb.libscene.SpaceRegion;

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
