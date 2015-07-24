package br.odb.libscene;

public class DirectedSceneNode extends SceneNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2960473844694828046L;
	public float angleXZ;
	public float angleYZ;
	
	public DirectedSceneNode(SceneNode other) {
		super( other );
		
		if ( other instanceof DirectedSceneNode ) {
			setFrom( (DirectedSceneNode) other );	
		}		
	}
	
	public void setFrom( DirectedSceneNode other ) {
		super.setFrom( other );
		this.angleXZ = other.angleXZ;
		this.angleYZ = other.angleYZ;
	}
	
	public DirectedSceneNode(String id) {
		super(id);
	}

	public DirectedSceneNode( SceneNode other, String newId) {
		super(other, newId);

		if ( other instanceof DirectedSceneNode ) {
			setFrom( (DirectedSceneNode) other );	
		}	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(angleXZ);
		result = prime * result + Float.floatToIntBits(angleYZ);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectedSceneNode other = (DirectedSceneNode) obj;
		if (Float.floatToIntBits(angleXZ) != Float
				.floatToIntBits(other.angleXZ))
			return false;
		if (Float.floatToIntBits(angleYZ) != Float
				.floatToIntBits(other.angleYZ))
			return false;
		return true;
	}	
}
