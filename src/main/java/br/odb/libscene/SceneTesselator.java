package br.odb.libscene;

import br.odb.libstrip.GeneralPolygon;
import br.odb.libstrip.GeneralQuad;
import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.IndexedSetFace;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SceneTesselator {

	public static World generateQuadsForWorld(World world) {

		generateMeshForSector(world.masterSector);

		return world;
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

	private static void generateQuadFor(Direction d, GroupSector sector) {

		GeneralPolygon trig;

		if ( sector.colorForDirection.get(d) == null ) {
			return;
		}
		
		switch (d) {
		case FLOOR:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z + sector.size.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z + sector.size.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z + sector.size.z));

			break;
		case CEILING:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z + sector.size.z));
			break;
		case W:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z + sector.size.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z + sector.size.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z + sector.size.z));

			break;
		case E:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z + sector.size.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));
			break;
		case N:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z));
			break;
		case S:
			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z + sector.size.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y, sector.position.z + sector.size.z));

			trig = new GeneralPolygon();
			sector.mesh.addFace(trig);
			trig.color.set(sector.colorForDirection.get(d));
			trig.color.r /= ( d.ordinal() + 1 );
			trig.color.g /= ( d.ordinal() + 1 );
			trig.color.b /= ( d.ordinal() + 1 );

			trig.addVertex(new Vec3(sector.position.x, sector.position.y,
					sector.position.z + sector.size.z));
			trig.addVertex(new Vec3(sector.position.x, sector.position.y
					+ sector.size.y, sector.position.z + sector.size.z));
			trig.addVertex(new Vec3(sector.position.x + sector.size.x,
					sector.position.y + sector.size.y, sector.position.z
							+ sector.size.z));
			break;
		}
	}
}
