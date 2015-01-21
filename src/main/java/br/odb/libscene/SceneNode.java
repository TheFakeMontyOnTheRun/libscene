package br.odb.libscene;

import java.io.Serializable;
import br.odb.utils.math.Vec3;

public class SceneNode implements Serializable {

    private static final long serialVersionUID = -2428831262655841986L;

	public String id;
	public final Vec3 localPosition = new Vec3();
	public SceneNode parent;

	public Vec3 getAbsolutePosition() {
		if (parent != null ) {
			return parent.getAbsolutePosition().add(localPosition);
		} else {
			return localPosition;
		}
	}

	public void setPositionFromGlobal(Vec3 pos) {
		localPosition.set(pos.sub(parent.getAbsolutePosition()));
	}

	
	SceneNode() {		
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
