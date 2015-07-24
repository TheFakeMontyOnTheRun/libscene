package br.odb.libscene;

import java.io.Serializable;

public class CameraNode extends DirectedSceneNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054020252855916205L;
	
	public CameraNode(SceneNode other) {
		super(other);

		if ( other instanceof CameraNode ) {
			setFrom( (CameraNode) other );	
		}		
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
