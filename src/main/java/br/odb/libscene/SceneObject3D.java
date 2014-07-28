package br.odb.libscene;

import java.util.ArrayList;

import br.odb.libstrip.Mesh;
import br.odb.utils.math.Vec3;

public class SceneObject3D {
	public ArrayList<Mesh> meshes = new ArrayList< Mesh >();
	public final Vec3 origin = new Vec3();
	public final Vec3 dimensions = new Vec3();
	public long emissiveLightningIntensity = 0;
	public boolean visible = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dimensions == null) ? 0 : dimensions.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SceneObject3D)) {
			return false;
		}
		SceneObject3D other = (SceneObject3D) obj;
		if (dimensions == null) {
			if (other.dimensions != null) {
				return false;
			}
		} else if (!dimensions.equals(other.dimensions)) {
			return false;
		}
		if (origin == null) {
			if (other.origin != null) {
				return false;
			}
		} else if (!origin.equals(other.origin)) {
			return false;
		}
		return true;
	}

	public SceneObject3D() {
		visible = true;
	}

	public SceneObject3D(SceneObject3D sceneObject) {

		for (Mesh m : sceneObject.meshes) {
			meshes.add(m);
		}

		origin.set( sceneObject.origin );
		dimensions.set( sceneObject.dimensions );
		visible = sceneObject.visible;
		emissiveLightningIntensity = sceneObject.emissiveLightningIntensity;
	}

	public void move(Vec3 delta) {
		origin.set(origin.add(delta));
	}
}
