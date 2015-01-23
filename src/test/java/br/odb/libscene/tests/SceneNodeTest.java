package br.odb.libscene.tests;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.SceneNode;

public class SceneNodeTest {
	
	
	@Test
	public void testSimpleSceneNodeSerialization() {

		SceneNode node = new SceneNode( "test" );		
		SceneNode node2 = new SceneNode( "test" );
		SceneNode nodeParentNull = new SceneNode( "test" );
		SceneNode nodeCompletelyDifferent = new SceneNode( "other" );

		
		SceneNode parent = new SceneNode( "parent" );

		node.localPosition.set( 1.0f, 2.0f, 3.0f );
		node2.localPosition.set( 1.0f, 2.0f, 3.0f );
		
		node.parent = parent;
		node2.parent = parent;
		
		SceneNode node3 = new SceneNode( node );
		
		Assert.assertEquals( node, node2 );
		Assert.assertEquals( node, node3 );
		Assert.assertEquals( node, node );
		Assert.assertNotSame( node, parent );
		Assert.assertNotSame( node, nodeParentNull );
		Assert.assertNotSame( node, nodeCompletelyDifferent );
		Assert.assertNotSame( node, null );
		Assert.assertNotSame( node, "Not even a node!" );
	}
}
