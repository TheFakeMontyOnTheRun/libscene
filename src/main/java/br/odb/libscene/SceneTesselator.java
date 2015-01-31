package br.odb.libscene;

import br.odb.libstrip.GeneralPolygon;
import br.odb.libstrip.IndexedSetFace;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SceneTesselator {

	public static World generateSubSectorQuadsForWorld(World world) {

		generateSubSectorMeshForSector(world.masterSector);

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

				if (son.links[d.ordinal()] != null && son.isParentEdgeAt(d)) {
					++links;
				}
			}
		}

		return links;
	}

	public static void generateSubSectorMeshForSector(GroupSector sector) {
		sector.mesh.clear();
		IndexedSetFace[] isfs;
		boolean generated;

		System.out.println("generating mesh for " + sector.id);

		for (Direction d : Direction.values()) {

			generated = false;

			if (foreignLinksInDirection(d, sector) == 0) {

				isfs = generateQuadFor(d, (SpaceRegion) sector);

				if (isfs != null) {
					for (IndexedSetFace isf : isfs) {
						sector.mesh.addFace(isf);
						generated = true;
					}
				}
			}

			for (SpaceRegion s : sector.getSons()) {

				if (s instanceof GroupSector) {
					generateSubSectorMeshForSector((GroupSector) s);
				} else if (!generated) {

					if (((Sector) s).links[d.ordinal()] == null) {

						isfs = generateQuadFor(d, s);

						if (isfs != null) {
							for (IndexedSetFace isf : isfs) {
								sector.mesh.addFace(isf);
							}
						}
					}
				}
			}
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

	public static Color getColorForFace(SpaceRegion sr) {
		if (sr instanceof GroupSector
				&& ((GroupSector) sr).mesh.material != null) {
			return ((GroupSector) sr).mesh.material.mainColor;
		} else {
			if (sr.parent instanceof SpaceRegion) {
				return getColorForFace((SpaceRegion) sr.parent);
			} else {
				return new Color(128, 128, 128);
			}
		}
	}

	private static GeneralPolygon[] generateQuadFor(Direction d,
			SpaceRegion sector) {

		GeneralPolygon[] toReturn = new GeneralPolygon[2];
		GeneralPolygon trig;

		Vec3 position = sector.getAbsolutePosition();
		Color c = getColorForFace(sector);

		switch (d) {
		case FLOOR:
			trig = new GeneralPolygon();
			trig.id = sector.id + "_" + d.simpleName;
			toReturn[0] = trig;

			trig.color.set(c);
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

			trig.color.set( c );
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

			trig.color.set(c);
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

		if (sector.mesh.material == null) {
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
