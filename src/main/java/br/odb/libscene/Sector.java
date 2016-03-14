package br.odb.libscene;

import java.io.Serializable;

import br.odb.gameutils.Direction;
import br.odb.gameutils.Utils;

public class Sector extends SpaceRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9089005913720463010L;
	public final String[] links = new String[ 6 ];
	
	Sector() {
	}
	
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
		for ( int c = 0; c < Direction.values().length; ++c ) {
			this.links[ c ] = region.links[ c ];
		}
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

	    SpaceRegion parentRegion = (SpaceRegion)parent;

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
			if (Utils.eqFloat(localPosition.y, parentRegion.size.y - size.y)) {
				return true;
			}
			break;
		case W:
			if (Utils.eqFloat(localPosition.x, 0.0f)) {
				return true;
			}
			break;
		case E:
			if (Utils.eqFloat(localPosition.x, parentRegion.size.x - size.x)) {
				return true;
			}
			break;
		case N:
			if (Utils.eqFloat(localPosition.z, 0.0f)) {
				return true;
			}
			break;
		case S:
		default:
			if (Utils.eqFloat(localPosition.z, parentRegion.size.z - size.z)) {
				return true;
			}

			break;
		}

		return false;
	}
}
