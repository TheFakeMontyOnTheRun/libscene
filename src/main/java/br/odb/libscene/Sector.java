package br.odb.libscene;

import java.util.HashMap;

import br.odb.utils.Direction;
import br.odb.utils.Utils;

public class Sector extends SpaceRegion {

	public final HashMap<Direction, Sector> connection = new HashMap<Direction, Sector>();
	
	public Sector(String id) {
		super(id);
	}

	public Sector(SpaceRegion region) {
		super(region);

		if ( region instanceof Sector ) {
			copyLinksFrom((Sector) region);
		}
	}

	public Sector(SpaceRegion region, String newId) {
		super(region, newId);

		if ( region instanceof Sector ) {
			copyLinksFrom((Sector) region);
		}
	}

	public void copyLinksFrom(Sector region) {
		connection.putAll(region.connection);
	}

	public boolean isParentEdge() {

		if (parent == null) {
			return false;
		}

		for (Direction d : Direction.values()) {
			if (isParentEdgeAt(d)) {
				return true;
			}
		}

		return false;
	}

	public boolean isParentEdgeAt(Direction d) {

		if (parent == null) {
			return false;
		}

		switch (d) {
		case FLOOR:
			if (Utils.eqFloat(localPosition.y, 0.0f)) {
				return true;
			}
			break;
		case CEILING:
			if (Utils.eqFloat(localPosition.y, parent.size.y - size.y)) {
				return true;
			}
			break;
		case W:
			if (Utils.eqFloat(localPosition.x, 0.0f)) {
				return true;
			}
			break;
		case E:
			if (Utils.eqFloat(localPosition.x, parent.size.x - size.x)) {
				return true;
			}
			break;
		case N:
			if (Utils.eqFloat(localPosition.z, 0.0f)) {
				return true;
			}
			break;
		case S:
			if (Utils.eqFloat(localPosition.z, parent.size.z - size.z)) {
				return true;
			}

			break;
		}

		return false;
	}
}
