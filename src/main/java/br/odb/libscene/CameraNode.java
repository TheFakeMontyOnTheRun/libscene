package br.odb.libscene;

public class CameraNode extends SceneNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054020252855916205L;
	float angleXZ;
	
	public CameraNode(SceneNode other) {
		super(other);

		if ( other instanceof CameraNode ) {
			setFrom( (CameraNode) other );	
		}		
	}

	public void setFrom( CameraNode other ) {
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
}
