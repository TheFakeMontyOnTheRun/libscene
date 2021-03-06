package br.odb.libscene.util;

import java.util.ArrayList;
import java.util.List;

import br.odb.libscene.GroupSector;
import br.odb.libscene.MeshNode;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.TriangleMesh;
import br.odb.libstrip.Material;
import br.odb.libstrip.builders.GeneralTriangleFactory;
import br.odb.gameutils.Color;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

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

	private void clearMeshesOn(GroupSector sector) {

		List<MeshNode> nodes = new ArrayList<MeshNode>();

		for (SceneNode sn : sector.getSons()) {
			if (sn instanceof MeshNode) {
				nodes.add((MeshNode) sn);
			}
		}

		for (MeshNode node : nodes) {
			sector.removeChild(node);
		}
	}

	public void generateSubSectorMeshForSector(GroupSector sector) {

		clearMeshesOn(sector);
		TriangleMesh mesh = new TriangleMesh("_mesh");
		MeshNode meshDataNode = new MeshNode("_mesh", mesh);
		sector.addChild(meshDataNode);
		GeneralTriangle[] isfs;
		boolean generated;

		for (Direction d : Direction.values()) {

			generated = false;

			// if (foreignLinksInDirection(d, sector) == 0) {
			//
			// isfs = generateQuadFor(d, (SpaceRegion) sector);
			//
			// if (isfs != null) {
			// for (GeneralTriangle isf : isfs) {
			// sector.mesh.faces.add(isf);
			// generated = true;
			// }
			// }
			// }

			for (SceneNode s : sector.getSons()) {

				if (s instanceof GroupSector) {
					generateSubSectorMeshForSector((GroupSector) s);
				} else if (!generated && s instanceof SpaceRegion) {

					if (((Sector) s).links[d.ordinal()] == null) {

						isfs = generateQuadFor(d, (SpaceRegion) s);

						if (isfs != null) {
							for (GeneralTriangle isf : isfs) {
								mesh.faces.add(isf);
							}
						}
					}
				}
			}
		}
	}

	private void generateMeshForSector(GroupSector sector) {

		for (Direction d : Direction.values()) {
			generateQuadFor(d, sector);
		}

		for (SceneNode sr : sector.getSons()) {
			if (sr instanceof GroupSector) {
				generateMeshForSector((GroupSector) sr);
			}
		}
	}

	public Material getColorForFace(Direction d, SpaceRegion sr) {
		if (sr instanceof GroupSector && ((GroupSector) sr).shades.containsKey(d)) {
			return ((GroupSector) sr).shades.get(d);

		} else {
			if (sr.parent instanceof SpaceRegion) {
				return getColorForFace(d, (SpaceRegion) sr.parent);
			} else {
				return Material.makeWithColor(new Color(255, 128, 128));
			}
		}
	}

	public GeneralTriangle[] generateQuadFor(Direction d, SpaceRegion sector) {

		GeneralTriangle[] toReturn = new GeneralTriangle[2];
		GeneralTriangle trig;
		Vec3 position = sector.getAbsolutePosition();
		Material c = getColorForFace(d, sector);

		Vec3 normalizationFactor;

		if (sector.parent instanceof SpaceRegion) {
			normalizationFactor = ((SpaceRegion) sector.parent).size;
		} else {
			normalizationFactor = sector.size;
		}

		switch (d) {
			case CEILING:
				trig = factory.makeTrig(
						position.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
				});

				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						position.x, position.y + sector.size.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
				});
				toReturn[1] = trig;

				break;
			case W:
				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x, position.y + sector.size.y, position.z + sector.size.z,
						position.x, position.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
				});

				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x, position.y + sector.size.y, position.z,
						position.x, position.y + sector.size.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
				});

				toReturn[1] = trig;

				break;
			case E:
				trig = factory.makeTrig(
						position.x + sector.size.x, position.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						position.x + sector.size.x, position.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
				});

				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x + sector.size.x, position.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.z + sector.size.z) / normalizationFactor.z, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
				});

				toReturn[1] = trig;

				break;
			case N:
				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y, position.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
				});

				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x, position.y + sector.size.y, position.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
				});

				toReturn[1] = trig;

				break;
			case S:
				trig = factory.makeTrig(
						position.x, position.y, position.z + sector.size.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						position.x + sector.size.x, position.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
				});

				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x, position.y, position.z + sector.size.z,
						position.x, position.y + sector.size.y, position.z + sector.size.z,
						position.x + sector.size.x, position.y + sector.size.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y) / normalizationFactor.y,
						(sector.localPosition.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, 1.0f - (sector.localPosition.y + sector.size.y) / normalizationFactor.y,
				});

				toReturn[1] = trig;

				break;
			case FLOOR:
			default:
				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x + sector.size.x, position.y, position.z,
						position.x + sector.size.x, position.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
				});


				toReturn[0] = trig;

				trig = factory.makeTrig(
						position.x, position.y, position.z,
						position.x + sector.size.x, position.y, position.z + sector.size.z,
						position.x, position.y, position.z + sector.size.z,
						c);

				trig.setTextureCoordinates(new float[]{
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z) / normalizationFactor.z,
						(sector.localPosition.x + sector.size.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
						(sector.localPosition.x) / normalizationFactor.x, (sector.localPosition.z + sector.size.z) / normalizationFactor.z,
				});

				toReturn[1] = trig;

				break;
		}

		for (GeneralTriangle gt : toReturn) {
			gt.flush();
			gt.hint = d;
		}

		return toReturn;
	}

	private MeshNode findMeshNodeForSector(GroupSector sector) {
		for (SceneNode sn : sector.sons) {
			if (sn instanceof MeshNode) {
				return (MeshNode) sn;
			}
		}

		return new MeshNode("_mesh", new TriangleMesh("_mesh"));
	}

	private void generateQuadFor(Direction d, GroupSector sector) {

		GeneralTriangle[] isfs = generateQuadFor(d, (SpaceRegion) sector);

		TriangleMesh mesh = findMeshNodeForSector(sector).mesh;

		if (isfs != null) {

			for (GeneralTriangle isf : isfs) {

				mesh.faces.add(isf);
			}
		}
	}
}
