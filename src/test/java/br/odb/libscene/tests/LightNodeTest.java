/**
 * 
 */
package br.odb.libscene.tests;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.LightNode;
import br.odb.libscene.SceneNode;

/**
 * @author monty
 *
 */
public class LightNodeTest {

	/**
	 * Test method for {@link br.odb.libscene.LightNode#hashCode()} and {@link br.odb.libscene.LightNode#equals(java.lang.Object)}.
	 */
	@Test
	public void testHashCodeAndEquals() {
		SceneNode baseNode = new SceneNode( "light0" );
		LightNode ln1 = new LightNode( baseNode );
		LightNode ln3 = null;
		ln1 = new LightNode( "light0" );
		LightNode ln2 = new LightNode( ln3 );
		ln2 = new LightNode( ln1 );
		
		Assert.assertEquals( ln1.hashCode(), ln2.hashCode() );
		Assert.assertEquals( ln1, ln2 );
		Assert.assertEquals( ln1, ln1 );

		ln2 = new LightNode( ln1, "different name" );
		
		Assert.assertFalse( ln1.equals( ln2 ) );
		Assert.assertFalse( ln1.hashCode() == ln2.hashCode() );
		
		ln2.setFrom( ln1 );
		ln2.id = ln1.id;
		ln2.intensity = 50.0f;
		
		Assert.assertFalse( ln1.hashCode() == ln2.hashCode() );
		Assert.assertFalse( ln1.equals( ln2 ) );
		
		ln2.setFrom( ln1 );
		ln2.id = ln1.id;
		ln2.color.multiply( 0.9f );
		
		Assert.assertFalse( ln1.hashCode() == ln2.hashCode() );
		Assert.assertFalse( ln1.equals( ln2 ) );
		
		ln2.setFrom( ln1 );
		ln2.id = ln1.id;
		
		Assert.assertEquals( ln1.hashCode(), ln2.hashCode() );
		Assert.assertEquals( ln1, ln2 );
		Assert.assertEquals( ln1, ln1 );
		
		SceneNode from = new SceneNode( "camModel" );
		ln1.setFrom( from );
		ln1.id = from.id;
		Assert.assertTrue( from.equals( ln1 ) );

		
		Assert.assertFalse( ln1.equals( null ) );
		Assert.assertFalse( ln1.equals( "Not a light node!" ) );
		Assert.assertFalse( ln1.equals( new SceneNode( ln1 ) ) );
	}
}
