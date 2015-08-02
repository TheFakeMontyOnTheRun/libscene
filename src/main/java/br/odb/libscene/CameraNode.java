package br.odb.libscene;

import java.io.Serializable;

public class CameraNode extends DirectedSceneNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054020252855916205L;

	public CameraNode(SceneNode other) {
		super(other);

		if (other instanceof CameraNode) {
			setFrom((CameraNode) other);
		}
	}

	public void onLeft() {
		angleXZ -= 10;
	}

	public void onRight() {
		angleXZ += 10;
	}

	public void onWalkForward() {
		localPosition.x += 10 * Math.sin(angleXZ * (Math.PI / 180.0f));
		localPosition.z -= 10 * Math.cos(angleXZ * (Math.PI / 180.0f));
	}

	public void onWalkBack() {
		localPosition.x -= 10 * Math.sin(angleXZ * (Math.PI / 180.0f));
		localPosition.z += 10 * Math.cos(angleXZ * (Math.PI / 180.0f));
	}

	public void onMoveDown() {
		localPosition.y -= 10;
	}
	
	public void onMoveUp() {
		localPosition.y += 10;
	}
	
	public void onStrafeLeft() {
        localPosition.x += 10 * Math.sin((angleXZ - 90)* (Math.PI / 180.0f));
        localPosition.z -= 10 * Math.cos((angleXZ - 90) * (Math.PI / 180.0f));
	}
	
	public void onStrafeRight() {
        localPosition.x -= 10 * Math.sin((angleXZ - 90)* (Math.PI / 180.0f));
        localPosition.z += 10 * Math.cos((angleXZ - 90) * (Math.PI / 180.0f));
	}
	
	public CameraNode(String id) {
		super(id);
	}

	public CameraNode(SceneNode other, String newId) {
		super(other, newId);

		if (other instanceof CameraNode) {
			setFrom((CameraNode) other);
		}
	}
}
