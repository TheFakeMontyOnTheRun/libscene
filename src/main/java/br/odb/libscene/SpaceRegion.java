package br.odb.libscene;

import br.odb.gameutils.math.Vec3;

public class SpaceRegion extends SceneNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1591752162392334619L;

	public final Vec3 size = new Vec3(1.0f, 1.0f, 1.0f);

	SpaceRegion() {
	}

	public Vec3 getLocalCenter() {

		Vec3 halfSize = new Vec3(size);
		halfSize.scale(0.5f);

		return localPosition.add(halfSize);
	}

	public Vec3 getAbsoluteCenter() {

		Vec3 halfSize = new Vec3(size);
		halfSize.scale(0.5f);
		return getAbsolutePosition().add(halfSize);
	}

	public SpaceRegion(String id) {
		super(id);
	}

	public SpaceRegion(String id, Vec3 size) {
		super(id);
		this.size.set(size);
	}

	public SpaceRegion(SpaceRegion region) {
		super(region);

		size.set(region.size);
	}

	public SpaceRegion(SpaceRegion region, String newId) {
		super(region, newId);
	}

	public boolean isDegenerate() {
		return !size.isValid() || (size.x <= 0.0f) || (size.y <= 0.0f)
				|| (size.z <= 0.0f);
	}

	public boolean coincidant(SpaceRegion another) {

		Vec3 pos = getAbsolutePosition();
		Vec3 otherPos = another.getAbsolutePosition();

		return pos.equals(otherPos) && size.equals(another.size);
	}

	public boolean intersects(SpaceRegion another) {
		return this.intersectsWith(another)
				|| (another != null ? another.intersectsWith(this) : false);
	}

	private boolean intersectsWith(SpaceRegion another) {

		if (another == null) {
			return false;
		}

		Vec3 pos = getAbsolutePosition();
		Vec3 otherPos = another.getAbsolutePosition();

		if (this.equals(another)) {
			return true;
		}

		Vec3 p0 = pos;
		Vec3 p1 = pos.add(size);
		Vec3 anotherP0 = otherPos;
		Vec3 anotherP1 = otherPos.add(another.size);

		if (p0.z <= anotherP0.z && anotherP1.z <= p1.z) {
			if (p0.y <= anotherP0.y && anotherP1.y <= p1.y) {
				if ((p0.x <= anotherP0.x && anotherP1.x <= p1.x)) {
					return true;
				}
			}
		}

		if ((p0.x <= anotherP0.x && anotherP0.x <= p1.x)
				|| (p0.x <= anotherP1.x && anotherP1.x <= p1.x)) {

			if ((p0.y <= anotherP0.y && anotherP0.y <= p1.y)
					|| (p0.y <= anotherP1.y && anotherP1.y <= p1.y)) {

				if ((p0.z <= anotherP0.z && anotherP0.z <= p1.z)
						|| (p0.z <= anotherP1.z && anotherP1.z <= p1.z)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isInside(Vec3 v) {

		Vec3 position = getAbsolutePosition();

		return ((position.x <= v.x) && (v.x <= (position.x + size.x)))
				&& ((position.y <= v.y) && (v.y <= (position.y + size.y)))
				&& ((position.z <= v.z) && (v.z <= (position.z + size.z)));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (size.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof SpaceRegion))
			return false;
		SpaceRegion other = (SpaceRegion) obj;
		if (!size.equals(other.size))
			return false;
		return true;
	}

}
