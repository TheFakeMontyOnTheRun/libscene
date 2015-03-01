package br.odb.libscene;

import java.io.Serializable;

public class CameraNode extends SceneNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054020252855916205L;
	public float angleXZ;
	
	public CameraNode(SceneNode other) {
		super(other);

		if ( other instanceof CameraNode ) {
			setFrom( (CameraNode) other );	
		}		
	}

	public void setFrom( CameraNode other ) {
		super.setFrom( other );
		this.angleXZ = other.angleXZ;
	}
	
	public CameraNode(String id) {
		super(id);
	}

	public CameraNode( SceneNode other, String newId) {
		super(other, newId);

		if ( other instanceof CameraNode ) {
			setFrom( (CameraNode) other );	
		}	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(angleXZ);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof CameraNode)) {
			return false;
		}
		CameraNode other = (CameraNode) obj;
		if (Float.floatToIntBits(angleXZ) != Float
				.floatToIntBits(other.angleXZ)) {
			return false;
		}
		return true;
	}
	
	
}
