package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SceneNode {
	public String id;
	public final Vec3 localPosition = new Vec3();
	public SpaceRegion parent;
	
	public SceneNode() {		
	}
	
	public SceneNode( SceneNode other ) {
		this( other.id );
		this.localPosition.set( other.localPosition );
	}
	
	public SceneNode( String id ) {
		this.id = id;
	}

	public SceneNode(SpaceRegion other, String newId) {
		this( newId );
		this.localPosition.set( other.localPosition );
	}
}
