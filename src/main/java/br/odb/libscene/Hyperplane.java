/**
 * 
 */
package br.odb.libscene;

import br.odb.utils.math.Vec3;

/**
 * @author daniel
 * 
 */
public class Hyperplane {
	/**
	 * 
	 */
	private byte kind;
	private Vec3 v;
	private Sector generator;

	// ///////////////////////////

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public boolean equals(Hyperplane another) {

		if (another == null)
			return false;

		if (!v.equals(another.v))
			return false;

		if (kind != another.kind)
			return false;

		// the generator sector doesnt matter.

		return true;
	}

	@Override
	public boolean equals(Object obj) {

		return equals((Hyperplane) obj);
	}

	// ///////////////////////////////

	@Override
	public String toString() {
		return "hyperplane: " + v + " kind: " + kind + " generator id: "
				+ generator.getId();
	}

	public Hyperplane(byte kind, float n, Sector generator) {

		this.kind = kind;
		this.v = new Vec3(Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MAX_VALUE);
		this.generator = generator;

		switch (kind) {
		case Constants.FACE_N:
		case Constants.FACE_S:
			v.z = (n);
			break;
		case Constants.FACE_W:
		case Constants.FACE_E:
			v.x = (n);
			break;
		case Constants.FACE_FLOOR:
		case Constants.FACE_CEILING:
			v.y = (n);
			break;
		}

	}

	/**
	 * 
	 * @return
	 */
	public Vec3 getVector() {
		return v;
	}

	public Sector getGenerator() {
		return generator;
	}

}