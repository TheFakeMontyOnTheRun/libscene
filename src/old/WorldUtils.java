package br.odb.libscene.old;

import java.util.ArrayList;

import br.odb.libstrip.Mesh;

/**
 * 
 * @author Daniel "Monty" Monteiro
 * 
 */
public class WorldUtils {

	public static void buildConvexHulls(final World world,
			final ArrayList<Mesh> mesh2) {
		Mesh mesh;
		Sector sector;

		for (int c = 0; c < mesh2.size(); ++c) {

			mesh = mesh2.get(c);

			if (mesh.faces.size() > 0) {

				sector = Sector.getConvexHull( World.snapLevel, mesh );

				if (!sector.isDegenerate()) {
					world.addSector(sector);
				}
			}
		}
	}
}
