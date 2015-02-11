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

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((localPosition == null) ? 0 : localPosition.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SceneNode other = (SceneNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (localPosition == null) {
			if (other.localPosition != null)
				return false;
		} else if (!localPosition.equals(other.localPosition))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	SceneNode() {		
	}
	
	public SceneNode( SceneNode other ) {
		this( other.id );
		this.localPosition.set( other.localPosition );
		this.parent = other.parent;
	}
	
	public SceneNode( String id ) {
		this.id = id;
	}

	public SceneNode( SceneNode other, String newId) {
		this( newId );
		this.localPosition.set( other.localPosition );
	}
}
