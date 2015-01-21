package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SpaceRegion extends SceneNode {

	public final Vec3 size = new Vec3(1.0f, 1.0f, 1.0f);
	public String description;

	SpaceRegion() {
	}

	public Vec3 getLocalCenter() {
		
		Vec3 toReturn = localPosition.add( size );
		toReturn.scale( 0.5f );
		
		return toReturn;		
	}
	
	public Vec3 getAbsoluteCenter() {
		
		Vec3 halfSize = new Vec3( size );
		halfSize.scale( 0.5f );
		return getAbsolutePosition().add( halfSize );		
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
		description = region.description;
	}

	public SpaceRegion(SpaceRegion region, String newId) {
		super(region, newId);
	}

	public boolean isDegenerate() {
		return (size.x <= 0.0f) || (size.y <= 0.0f) || (size.z <= 0.0f);
	}

	public boolean coincidant(SpaceRegion another) {
		
		Vec3 pos = getAbsolutePosition();
		Vec3 otherPos = another.getAbsolutePosition();
		
		return pos.equals( otherPos ) && size.equals( another.size );
	}
	
	public boolean intersects(SpaceRegion another) {

		if (another == null) {
			return false;
		}

		Vec3 pos = getAbsolutePosition();
		Vec3 otherPos = another.getAbsolutePosition();

		if (this == another || this.equals(another)) {
			return true;
		}

		Vec3 p0 = pos;
		Vec3 p1 = pos.add(size);
		Vec3 anotherP0 = otherPos;
		Vec3 anotherP1 = otherPos.add(size);

		if (contains(anotherP0.x, anotherP0.y, anotherP0.z)) {
			return true;
		}

		if (contains(anotherP0.x, anotherP0.y, anotherP1.z)) {
			return true;
		}

		if (contains(anotherP0.x, anotherP1.y, anotherP0.z)) {
			return true;
		}

		if (contains(anotherP0.x, anotherP1.y, anotherP1.z)) {
			return true;
		}

		if (contains(anotherP1.x, anotherP0.y, anotherP0.z)) {
			return true;
		}

		if (contains(anotherP1.x, anotherP0.y, anotherP1.z)) {
			return true;
		}

		if (contains(anotherP1.x, anotherP1.y, anotherP0.z)) {
			return true;
		}

		if (contains(anotherP1.x, anotherP1.y, anotherP1.z)) {
			return true;
		}

		if (anotherP0.x >= p0.x
				&& anotherP1.x <= p1.x
				&& anotherP0.y <= p0.y
				&& anotherP1.y >= p1.y
				&& ((p0.z <= anotherP0.z && anotherP0.z <= p1.z) || (p0.z <= anotherP1.z && anotherP1.z <= p1.z))) {
			return true;
		}

		return false;
	}

	public boolean contains(float x, float y, float z) {
		return contains(new Vec3(x, y, z));
	}

	public boolean contains(Vec3 v) {

		Vec3 position = getAbsolutePosition();

		return ((position.x <= v.x) && (v.x <= (position.x + size.x)))
				&& ((position.y <= v.y) && (v.y <= (position.y + size.y)))
				&& ((position.z <= v.z) && (v.z <= (position.z + size.z)));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		SpaceRegion other = (SpaceRegion) obj;

		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (localPosition == null) {
			if (other.localPosition != null)
				return false;
		} else if (!localPosition.equals(other.localPosition))
			return false;
		return true;
	}

}
