package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SceneNode {
	public final String id;
	public final Vec3 localPosition = new Vec3();

	public SceneNode( SceneNode other ) {
		this( other.id );
		this.localPosition.set( other.localPosition );
	}
	
	public SceneNode( String id ) {
		this.id = id;
	}
}
