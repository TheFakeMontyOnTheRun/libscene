package br.odb.libscene;

import java.util.ArrayList;

import br.odb.libstrip.Mesh;
import br.odb.utils.math.Vec3;

public class SceneObject3D {
	private ArrayList<Mesh> meshes;
	private Vec3 origin;
	private Vec3 dimensions;
	private long emissiveLightningIntensity;
	private boolean visible;

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

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public SceneObject3D() {
		visible = true;
		meshes = new ArrayList<Mesh>();
		origin = new Vec3();
		dimensions = new Vec3();
	}

	public SceneObject3D(SceneObject3D sceneObject) {

		meshes = new ArrayList<Mesh>();

		for (Mesh m : sceneObject.meshes) {
			meshes.add(m);
		}

		visible = sceneObject.isVisible();
		origin = new Vec3(sceneObject.origin);
		dimensions = new Vec3(sceneObject.dimensions);
		emissiveLightningIntensity = sceneObject.emissiveLightningIntensity;
	}

	public int getEmissiveLightningIntensity() {
		return (int) emissiveLightningIntensity;
	}

	public void setEmissiveLightningIntensity(int candelas) {
		emissiveLightningIntensity = candelas;
	}

	public void addCandelas(int candelas) {

		if (candelas >= 255) {
			emissiveLightningIntensity = 255;
			return;
		}

		if (candelas < 0) {
			return;
		}

		if ((emissiveLightningIntensity + candelas) > 255)
			emissiveLightningIntensity = 255;
		else
			emissiveLightningIntensity += candelas;
	}

	public void moveTo(Vec3 place) {
		Vec3 diff = place.sub(origin);
		move(diff);
		origin.copy(place);
	}

	public void move(Vec3 delta) {
		origin.set(origin.add(delta));
	}

	public void setPosition(Vec3 v) {
		this.origin.set(v);
	}

	public ArrayList<Mesh> getMeshes() {
		return meshes;
	}

	public void update() {

	}

	public void addMesh(Mesh mesh) {
		meshes.add(mesh);
	}

	public Vec3 getPosition() {
		return origin;
	}
}
