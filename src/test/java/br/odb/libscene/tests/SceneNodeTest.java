package br.odb.libscene.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.SceneNode;
import br.odb.utils.math.Vec3;

public class SceneNodeTest {

	//http://stackoverflow.com/questions/19699634/coverage-for-private-constructor-junit-emma
	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<SceneNode> constructor = SceneNode.class.getDeclaredConstructor();
      Assert.assertFalse(Modifier.isPublic(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }
	
	@Test
	public void testPositions() {
		SceneNode node = new SceneNode("test");
		SceneNode node2 = new SceneNode("test2");
		
		SceneNode parent = null;
		
		SceneNode node3;
		
		node3 = new SceneNode( parent );
		
		Assert.assertEquals(new Vec3(),
				node3.getAbsolutePosition());


		node3 = new SceneNode( parent, "node3" );
		
		Assert.assertEquals(new Vec3(),
				node3.getAbsolutePosition());

		node3.localPosition.set( 1.0f, 2.0f, 3.0f );
		
		node3 = new SceneNode( node, "node3" );
		
		Assert.assertEquals(new Vec3(),
				node3.getAbsolutePosition());

		
		parent = new SceneNode("parent");

		parent.localPosition.set(1.0f, 1.0f, 1.0f);

		node.localPosition.set(1.0f, 2.0f, 3.0f);

		node2.localPosition.set(10.0f, 20.0f, 30.0f);
		
		node.parent = parent;

		Assert.assertEquals(new Vec3(2.0f, 3.0f, 4.0f),
				node.getAbsolutePosition());

		node.setPositionFromGlobal( new Vec3( 0.0f, 0.0f, 0.0f ) );
		
		node2.setFrom( node );

		Assert.assertEquals(new Vec3( 0.0f, 0.0f, 0.0f),
				node2.localPosition);

		
		Assert.assertEquals(new Vec3(),
				node.getAbsolutePosition());

		Assert.assertEquals(new Vec3( -1.0f, -1.0f, -1.0f),
				node.localPosition);
		
		node.setFrom( null );
		
		Assert.assertEquals(new Vec3( -1.0f, -1.0f, -1.0f),
				node.localPosition);


	}

	@Test
	public void testSimpleSceneNodeSerialization() {

		SceneNode node = new SceneNode("test");
		SceneNode node2 = new SceneNode("test");
		SceneNode nodeParentNull = new SceneNode("test");
		SceneNode nodeCompletelyDifferent = new SceneNode("other");

		SceneNode parent = new SceneNode("parent");

		node.localPosition.set(1.0f, 2.0f, 3.0f);
		node2.localPosition.set(1.0f, 2.0f, 3.0f);

		node.parent = parent;
		node2.parent = parent;

		SceneNode node3 = new SceneNode(node);

		Assert.assertEquals(node, node2);
		Assert.assertEquals(node, node3);
		Assert.assertEquals(node, node);
	
		Assert.assertFalse( node.equals( parent ) );
		Assert.assertFalse( node.equals( nodeParentNull ) );
		Assert.assertFalse( node.equals( nodeCompletelyDifferent ) );

		Assert.assertFalse( parent.equals( node ) );
		Assert.assertFalse( nodeParentNull.equals( node ) );
		Assert.assertFalse( nodeCompletelyDifferent.equals( node ) );

		
		Assert.assertFalse( node.equals( null ) );
		Assert.assertFalse(node.equals( "Not even a node!") );
		
		
		
		Assert.assertEquals(node.hashCode(), node2.hashCode());
		Assert.assertEquals(node.hashCode(), node3.hashCode());
	
		Assert.assertFalse( node.hashCode() == parent.hashCode() );
		Assert.assertFalse( node.hashCode() == nodeParentNull.hashCode() );
		Assert.assertFalse( node.hashCode() == nodeCompletelyDifferent.hashCode() );
	}
}
