package br.odb.libscene;

import java.util.ArrayList;
import java.util.List;

import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class World extends Scene {

	public World(GroupSector built) {
		masterSector = built;
	}

	public World() {
		masterSector = new GroupSector("id");
	}
	
	private List<SpaceRegion> getAllRegionsAsList(GroupSector group) {

		ArrayList<SpaceRegion> toReturn = new ArrayList<SpaceRegion>();

		toReturn.addAll(group.getSons());

		for (SpaceRegion sr : group.getSons()) {
			if (sr instanceof GroupSector) {
				toReturn.addAll(getAllRegionsAsList((GroupSector) sr));
			}
		}

		return toReturn;
	}

	public List<SpaceRegion> getAllRegionsAsList() {
		return getAllRegionsAsList( masterSector );
	}

	public void checkForHardLinks() {
		List<SpaceRegion> sectors = getAllRegionsAsList(masterSector);

		for (SpaceRegion s1 : sectors) {

			if (s1 instanceof GroupSector) {
				continue;
			}

			for (SpaceRegion s2 : sectors) {

				if (s2 == s1)
					continue;

				if (s2 instanceof GroupSector) {
					continue;
				}

				checkLinksForSectors((Sector) s1, (Sector) s2);
			}
		}
	}

	/**
	 * @param s1
	 * @param s2
	 */
	private void checkLinksForSectors(Sector s1, Sector s2) {

		Vec3 pos2 = s2.getAbsolutePosition();

		int s2_x0 = Math.round(pos2.x);
		int s2_x1 = Math.round(pos2.x + s2.size.x);
		int s2_y0 = Math.round(pos2.y);
		int s2_y1 = Math.round(pos2.y + s2.size.y);
		int s2_z0 = Math.round(pos2.z);
		int s2_z1 = Math.round(pos2.z + s2.size.z);

		Vec3 pos1 = s1.getAbsolutePosition();

		int s1_x0 = Math.round(pos1.x);
		int s1_x1 = Math.round(pos1.x + s1.size.x);
		int s1_y0 = Math.round(pos1.y);
		int s1_y1 = Math.round(pos1.y + s1.size.y);
		int s1_z0 = Math.round(pos1.z);
		int s1_z1 = Math.round(pos1.z + s1.size.z);

		if (br.odb.utils.Utils.eqFloat(s1_x0, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {

			s1.connection.put(Direction.W, s2);
			s2.connection.put(Direction.E, s1);
		}

		if (br.odb.utils.Utils.eqFloat(s1_x1, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.E, s2);
			s2.connection.put(Direction.W, s1);
		}

		if (br.odb.utils.Utils.eqFloat(s1_y0, s2_y1)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)) {
			s1.connection.put(Direction.FLOOR, s2);
			s2.connection.put(Direction.CEILING, s1);

		}

		if (br.odb.utils.Utils.eqFloat(s1_y1, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)) {
			s1.connection.put(Direction.CEILING, s2);
			s2.connection.put(Direction.FLOOR, s1);

		}

		if (br.odb.utils.Utils.eqFloat(s1_z0, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.N, s2);
			s2.connection.put(Direction.S, s1);
		}

		if (br.odb.utils.Utils.eqFloat(s1_z1, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.S, s2);
			s2.connection.put(Direction.N, s1);
		}
	}

	public final GroupSector masterSector;
}
