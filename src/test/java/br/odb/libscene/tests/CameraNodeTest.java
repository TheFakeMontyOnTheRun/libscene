/**
 * 
 */
package br.odb.libscene.tests;

import junit.framework.Assert;

import org.junit.Test;

import br.odb.libscene.CameraNode;
import br.odb.libscene.SceneNode;
import br.odb.gameutils.math.Vec3;

/**
 * @author monty
 *
 */
public class CameraNodeTest {

	/**
	 * Test method for {@link br.odb.libscene.CameraNode#hashCode()} and {@link br.odb.libscene.CameraNode#equals(java.lang.Object)}.
	 */
	@Test
	public void testHashCodeAndEquals() {
		SceneNode parent = new SceneNode( "parent" );
		CameraNode cn1 = new CameraNode( "cam0" );
		CameraNode cn2 = new CameraNode( parent, "cam0" );
		
		
		Assert.assertEquals( cn1, cn1 );
		Assert.assertEquals( cn1, cn2 );
		Assert.assertEquals( cn1.hashCode(), cn2.hashCode() );

		cn1.parent = parent;
		
		Assert.assertEquals( cn1, cn2 );
		Assert.assertEquals( cn1.hashCode(), cn2.hashCode() );
		
		cn1.angleXZ = 90.0f;
		
		Assert.assertFalse( cn1.equals( cn2 ) );
		Assert.assertFalse( cn1.hashCode() == cn2.hashCode() );
		
		cn1 = new CameraNode( "cam1" );
		
		Assert.assertFalse( cn1.equals( cn2 ) );
		Assert.assertFalse( cn1.hashCode() == cn2.hashCode() );
		
		cn1 = new CameraNode( "cam0" );
		cn1.localPosition.set( new Vec3(1.0f, 2.0f, 3.0f ) );
		Assert.assertFalse( cn1.equals( cn2 ) );
		Assert.assertFalse( cn1.hashCode() == cn2.hashCode() );
		
		
		cn1 = new CameraNode( "cam0" );
		Assert.assertFalse( cn1.equals( null ) );
		Assert.assertFalse( cn1.equals( new SceneNode( "cam0" ) ) );
		Assert.assertFalse( cn1.equals( "Not a camera node!" ) );
	}

	/**
	 * Test method for {@link br.odb.libscene.CameraNode#setFrom(br.odb.libscene.CameraNode)}.
	 */
	@Test
	public void testSetFrom() {
		SceneNode parent = new SceneNode( "parent" );
		SceneNode parent2 = new SceneNode( "parent" );
		CameraNode cn1 = new CameraNode( "cam0" );
		CameraNode cn2 = new CameraNode( parent, "cam0" );
		cn1.parent = parent;
		cn2.parent = parent2;
		parent.localPosition.set( 1.0f, 2.0f, 3.0f );
		parent2.localPosition.set( 4.0f, 5.0f, 6.0f );
		
		cn2.localPosition.set( 1.0f, 1.0f, 1.0f );
		cn1.setFrom( cn2 );
		
		Assert.assertEquals( new Vec3( 4.0f, 4.0f, 4.0f ), cn1.localPosition );
		
		cn1 = new CameraNode( cn2, "cam0"); 
		Assert.assertEquals( new Vec3( 5.0f, 6.0f, 7.0f ), cn1.localPosition );
		
		cn1 = new CameraNode( cn2 ); 
		Assert.assertEquals( new Vec3( 5.0f, 6.0f, 7.0f ), cn1.localPosition );

		
		cn2 = null;
		
		cn1 = new CameraNode( cn2 ); 
		Assert.assertEquals( new Vec3(), cn1.localPosition );
		
	}
}
