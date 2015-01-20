package br.odb.libscene;

import br.odb.libstrip.GeneralPolygon;
import br.odb.libstrip.IndexedSetFace;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SceneTesselator {

	public static World generateSubSectorQuadsForWorld(World world) {

		generateSubSectorMeshForSector( world.masterSector );

		for (SpaceRegion sr : world.masterSector.getSons()) {
			if (sr instanceof GroupSector) {
				generateSubSectorMeshForSector((GroupSector) sr);
			}
		}

		return world;
	}

	public static World generateQuadsForWorld(World world) {

		generateMeshForSector(world.masterSector);

		return world;
	}

	public static int foreignLinksInDirection(Direction d, GroupSector gs) {
		int links = 0;
		Sector son;

		for (SpaceRegion sr : gs.getSons()) {
			if (sr instanceof Sector) {
				son = (Sector) sr;

				if (son.connection.containsKey(d) && son.isParentEdgeAt( d ) ) {
					++links;
				}
			}
		}

		return links;
	}

	public static void generateSubSectorMeshForSector(GroupSector sector) {
		sector.mesh.clear();
		IndexedSetFace[] isfs;

		for (Direction d : Direction.values()) {

//			if (foreignLinksInDirection(d, sector) == 0) {
//
//				isfs = generateQuadFor(d, (SpaceRegion) sector);
//
//				if (isfs != null) {
//					for (IndexedSetFace isf : isfs) {
//						sector.mesh.addFace(isf);
//					}
//				}
//
//			} else {

				for (SpaceRegion s : sector.getSons()) {

					if (s instanceof GroupSector) {
						generateSubSectorMeshForSector((GroupSector) s);
					} else {
						if (!((Sector) s).connection.containsKey(d)) {

							isfs = generateQuadFor(d, s);

							if (isfs != null) {
								for (IndexedSetFace isf : isfs) {
									sector.mesh.addFace(isf);
								}
							}
						}
					}
				}
//			}
		}
	}

	private static void generateMeshForSector(GroupSector sector) {
		sector.mesh.clear();

		for (Direction d : Direction.values()) {
			generateQuadFor(d, sector);
		}

		for (SpaceRegion sr : sector.getSons()) {
			if (sr instanceof GroupSector) {
				generateMeshForSector((GroupSector) sr);
			}
		}

	}

	static Color getColorForFace(Direction d, SpaceRegion sr) {
		if ( sr instanceof GroupSector && ( (GroupSector) sr).materials.containsKey(d) ) {
			return ( (GroupSector) sr).materials.get(d).mainColor;
		} else {
			if (sr.parent instanceof GroupSector ) {
				return getColorForFace(d, (GroupSector)sr.parent);
			} else {
				return new Color(0, 0, 0);
			}
		}
	}

	private static GeneralPolygon[] generateQuadFor(Direction d,
			SpaceRegion sector) {

		GeneralPolygon[] toReturn = new GeneralPolygon[2];
		GeneralPolygon trig;

		Vec3 position = sector.getAbsolutePosition();

		switch (d) {
		case FLOOR:
			trig = new GeneralPolygon();
			trig.id = sector.id + "_" + d.simpleName;
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z + sector.size.z));

			trig = new GeneralPolygon();
			trig.id = sector.id + "_" + d.simpleName;
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z + sector.size.z));

			trig.addVertex(new Vec3(position.x, position.y, position.z
					+ sector.size.z));

			break;
		case CEILING:
			trig = new GeneralPolygon();
			trig.id = sector.id + "_" + d.simpleName;
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));

			trig = new GeneralPolygon();
			trig.id = sector.id + "_" + d.simpleName;
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z));

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));

			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z + sector.size.z));
			break;
		case W:
			trig = new GeneralPolygon();
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));
			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z + sector.size.z));
			trig.addVertex(new Vec3(position.x, position.y, position.z
					+ sector.size.z));

			trig = new GeneralPolygon();
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));
			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z));
			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z + sector.size.z));

			break;
		case E:
			trig = new GeneralPolygon();
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z + sector.size.z));

			trig = new GeneralPolygon();
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));
			break;
		case N:
			trig = new GeneralPolygon();
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z));

			trig = new GeneralPolygon();
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z));
			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z));
			break;
		case S:
			trig = new GeneralPolygon();
			toReturn[0] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z
					+ sector.size.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y,
					position.z + sector.size.z));

			trig = new GeneralPolygon();
			toReturn[1] = trig;

			trig.color.set(getColorForFace(d, sector));
			trig.color.r /= (d.ordinal() + 1);
			trig.color.g /= (d.ordinal() + 1);
			trig.color.b /= (d.ordinal() + 1);

			trig.addVertex(new Vec3(position.x, position.y, position.z
					+ sector.size.z));
			trig.addVertex(new Vec3(position.x, position.y + sector.size.y,
					position.z + sector.size.z));
			trig.addVertex(new Vec3(position.x + sector.size.x, position.y
					+ sector.size.y, position.z + sector.size.z));
			break;
		}

		return toReturn;
	}

	private static void generateQuadFor(Direction d, GroupSector sector) {

		if (sector.materials.get(d) == null) {
			return;
		}

		IndexedSetFace[] isfs = generateQuadFor(d, (SpaceRegion) sector);

		if (isfs != null) {

			for (IndexedSetFace isf : isfs) {

				sector.mesh.addFace(isf);
			}
		}

	}
}
