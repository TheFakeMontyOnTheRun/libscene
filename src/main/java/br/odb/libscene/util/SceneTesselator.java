package br.odb.libscene.util;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.Material;
import br.odb.libstrip.builders.GeneralTriangleFactory;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SceneTesselator {

	GeneralTriangleFactory factory;

	public SceneTesselator(GeneralTriangleFactory factory) {
		this.factory = factory;
	}

	public World generateSubSectorQuadsForWorld(World world) {

		generateSubSectorMeshForSector(world.masterSector);

		return world;
	}

	public World generateQuadsForWorld(World world) {

		generateMeshForSector(world.masterSector);

		return world;
	}

	public int foreignLinksInDirection(Direction d, GroupSector gs) {
		int links = 0;
		Sector son;

		for (SceneNode sr : gs.getSons()) {
			if (sr instanceof Sector) {
				son = (Sector) sr;

				if (son.links[d.ordinal()] != null && son.isParentEdgeAt(d)) {
					++links;
				}
			}
		}

		return links;
	}

	public void generateSubSectorMeshForSector(GroupSector sector) {
		sector.mesh.clear();
		GeneralTriangle[] isfs;
		boolean generated;

		System.out.println("generating mesh for " + sector.id);

		for (Direction d : Direction.values()) {

			generated = false;

			if (foreignLinksInDirection(d, sector) == 0) {

				isfs = generateQuadFor(d, (SpaceRegion) sector);

				if (isfs != null) {
					for (GeneralTriangle isf : isfs) {
						sector.mesh.faces.add(isf);
						generated = true;
					}
				}
			}

			for (SceneNode s : sector.getSons()) {

				if (s instanceof GroupSector) {
					generateSubSectorMeshForSector((GroupSector) s);
				} else if (!generated && s instanceof SpaceRegion ) {

					if (((Sector) s).links[d.ordinal()] == null) {

						isfs = generateQuadFor(d, (SpaceRegion )s);

						if (isfs != null) {
							for (GeneralTriangle isf : isfs) {
								sector.mesh.faces.add(isf);
							}
						}
					}
				} 
//				else {
//					
//					isfs = generateQuadFor(d, cubeForNode( s ) );
//
//					Color colour = new Color();
//					
//					if ( s instanceof LightNode ) {
//						colour.set( ((LightNode)s).color );
//					} else {
//						colour.set( 128, 128, 128 );
//					}
//					
//					if (isfs != null) {
//						for (IndexedSetFace isf : isfs) {
//							isf.setColor( colour );
//							sector.mesh.addFace(isf);
//						}
//					}
//				}
			}
		}
	}

	private SpaceRegion cubeForNode(SceneNode s) {
		
		SpaceRegion sr = new SpaceRegion("killme");
		sr.localPosition.set( s.getAbsolutePosition() );
		sr.size.set( 1.0f, 1.0f, 1.0f );
		 
		return sr;
	}

	private void generateMeshForSector(GroupSector sector) {
		sector.mesh.clear();

		for (Direction d : Direction.values()) {
			generateQuadFor(d, sector);
		}

		for (SceneNode sr : sector.getSons()) {
			if (sr instanceof GroupSector) {
				generateMeshForSector((GroupSector) sr);
			}
		}

	}

	public Material getColorForFace(SpaceRegion sr) {
		if (sr instanceof GroupSector
				&& ((GroupSector) sr).material != null) {
			return ((GroupSector) sr).material;
		} else {
			if (sr.parent instanceof SpaceRegion) {
				return getColorForFace((SpaceRegion) sr.parent);
			} else {
				return new Material( null, new Color(128, 128, 128), null , null, null );
			}
		}
	}

	private GeneralTriangle[] generateQuadFor(Direction d, SpaceRegion sector) {

		GeneralTriangle[] toReturn = new GeneralTriangle[2];
		GeneralTriangle trig;
		Vec3 position = sector.getAbsolutePosition();
		Material c = getColorForFace(sector);

		switch (d) {
		case FLOOR:
			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x + sector.size.x, position.y,
					position.z, position.x + sector.size.x, position.y,
					position.z + sector.size.z, c, null);

			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x + sector.size.x, position.y,
					position.z + sector.size.z, position.x, position.y,
					position.z + sector.size.z, c, null);

			toReturn[1] = trig;

			break;
		case CEILING:
			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y
					+ sector.size.y, position.z, position.x + sector.size.x,
					position.y + sector.size.y, position.z, position.x
							+ sector.size.x, position.y + sector.size.y,
					position.z + sector.size.z, c, null);

			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y
					+ sector.size.y, position.z, position.x + sector.size.x,
					position.y + sector.size.y, position.z + sector.size.z,
					position.x, position.y + sector.size.y, position.z
							+ sector.size.z, c, null);
			toReturn[1] = trig;

			break;
		case W:
			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x, position.y + sector.size.y,
					position.z + sector.size.z, position.x, position.y,
					position.z + sector.size.z, c, null);

			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x, position.y + sector.size.y,
					position.z, position.x, position.y + sector.size.y,
					position.z + sector.size.z, c, null);
			toReturn[1] = trig;

			break;
		case E:
			trig = (GeneralTriangle) factory.makeTrig(position.x
					+ sector.size.x, position.y, position.z, position.x
					+ sector.size.x, position.y + sector.size.y, position.z
					+ sector.size.z, position.x + sector.size.x, position.y,
					position.z + sector.size.z, c, null);
			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x
					+ sector.size.x, position.y, position.z, position.x
					+ sector.size.x, position.y + sector.size.y, position.z,
					position.x + sector.size.x, position.y + sector.size.y,
					position.z + sector.size.z, c, null);
			toReturn[1] = trig;

			break;
		case N:
			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x + sector.size.x, position.y
							+ sector.size.y, position.z, position.x
							+ sector.size.x, position.y, position.z, c, null);
			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z, position.x, position.y + sector.size.y,
					position.z, position.x + sector.size.x, position.y
							+ sector.size.y, position.z, c, null);
			toReturn[1] = trig;

			break;
		case S:
			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z + sector.size.z, position.x + sector.size.x,
					position.y + sector.size.y, position.z + sector.size.z,
					position.x + sector.size.x, position.y, position.z
							+ sector.size.z, c, null);
			toReturn[0] = trig;

			trig = (GeneralTriangle) factory.makeTrig(position.x, position.y,
					position.z + sector.size.z, position.x, position.y
							+ sector.size.y, position.z + sector.size.z,
					position.x + sector.size.x, position.y + sector.size.y,
					position.z + sector.size.z, c, null);
			toReturn[1] = trig;

			break;
		}
		
		for ( GeneralTriangle gt : toReturn ) {
			gt.flush();
			gt.hint = d;
		}

		return toReturn;
	}

	private void generateQuadFor(Direction d, GroupSector sector) {

		if (sector.material == null) {
			return;
		}

		GeneralTriangle[] isfs = generateQuadFor(d, (SpaceRegion) sector);

		if (isfs != null) {

			for (GeneralTriangle isf : isfs) {

				sector.mesh.faces.add(isf);
			}
		}
	}
}
