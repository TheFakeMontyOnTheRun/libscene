package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SceneNode {
	public final String id;
	public final Vec3 position = new Vec3();

	public SceneNode( SceneNode other ) {
		this( other.id );
		this.position.set( other.position );
	}
	
	public SceneNode( String id ) {
		this.id = id;
	}
}
