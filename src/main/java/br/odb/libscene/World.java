/**
 * @author Daniel Monteiro
 *
 */
package br.odb.libscene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.odb.gameworld.exceptions.InvalidSlotException;
import br.odb.libstrip.MeshFactory;
import br.odb.utils.Color;
import br.odb.utils.FileServerDelegate;
import br.odb.utils.Utils;

public class World implements Iterable<Sector> {

	public static int snapLevel;
	private Sector sector;
	private float[] vals = new float[8];
	private String cache[] = new String[8];
	protected ArrayList<Actor3D> actorList;
	private ArrayList<Sector> sectorList;

	public World(World world) {
		this();

		for (Sector s : world.getSectorList()) {
			addSector(s);
		}

		for (Actor3D a : world.getActorList()) {
			addActor(a);
		}
	}

	public ArrayList<Sector> getSectorList() {
		return sectorList;
	}

	public ArrayList<Actor3D> getActorList() {
		return actorList;
	}

	public void setSectorList(ArrayList<Sector> sectorList) {
		this.sectorList = sectorList;
	}

	public void addActor(Actor3D actor) {
		actorList.add(actor);
	}

	public Actor3D getActor(int c) {

		return actorList.get(c);
	}

	// public boolean canSee( Sector sector1, Sector sector2, boolean
	// testOnlyMasters ) {
	// boolean toReturn = false;
	// int size = sectorList.size();
	// Sector s;
	// Line3[] lines = Line3.makeLines( sector1.getPoints(), sector2.getPoints()
	// );
	// Line3 line;
	//
	// for ( int d = 0; d < lines.length; ++d ) {
	//
	// line = lines[ d ];
	//
	// if ( testOnlyMasters ) {
	//
	// for ( int c = 0; c < size; ++c ) {
	// s = sectorList.get( c );
	//
	// if ( testOnlyMasters && !s.isMaster() )
	// break;
	//
	// if ( sector.intersect( line ) )
	// toReturn = false;
	// }
	// }
	//
	// //precisa de ao menos uma linha de
	// vis������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������o
	// dispon������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������vel
	// if ( toReturn )
	// return true;
	// }
	//
	// //se chegou aqui,
	// ������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
	// porque falhou..
	// return toReturn;
	// }
	//
	// public boolean canSee( int sector1, int sector2, boolean testOnlyMasters
	// ) {
	// Sector sec1 = sectorList.get( sector1 );
	// Sector sec2 = sectorList.get( sector2 );
	// return canSee( sec1, sec2, testOnlyMasters );
	// }
	// ------------------------------------------------------------------------------------------------------------
	public int getTotalSectors() {
		return sectorList.size();
	}

	public int getTotalActors() {
		return actorList.size();
	}

	// ------------------------------------------------------------------------------------------------------------

	public Sector getSector(int index) {
		return sectorList.get(index);
	}

	// ------------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return String.valueOf(sectorList.size()) + " sector(s) loaded";

	}

	// ------------------------------------------------------------------------------------------------------------
	public World() {
		sectorList = new ArrayList<Sector>();
		actorList = new ArrayList<Actor3D>();
	}

	// ------------------------------------------------------------------------------------------------------------
	public void internalize(String path, boolean fakeShading,
			FileServerDelegate server, MeshFactory factory) {

		String line;
		line = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					server.openAsInputStream(path)));
			line = br.readLine();

			while (line != null) {
				parseLine(line, actorList, fakeShading, server, factory);
				line = br.readLine();
			}

			System.out.println("loaded " + sectorList.size()
					+ " sector(s) with no issues to report");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------------------------------------------
	void fillWithStrings(String main, int num) {
		int index = 0;
		int tokens = 0;

		for (int c = 0; c < main.length(); ++c) {
			if (main.charAt(c) == ' ') {

				++tokens;

				if (tokens - 1 < num) {
					cache[tokens - 1] = main.substring(index, c);
					index = c + 1;
				}
			}
		}
		cache[tokens] = main.substring(index);
	}

	private void parseLine(String Sub, ArrayList<Actor3D> people,
			boolean fakeShading, FileServerDelegate server, MeshFactory factory) {
		try {

			for (int c = 0; c < 8; ++c) {
				cache[c] = null;
			}

			String[] result;
			// = Sub.split(" ");

			// result[0] contains s

			switch (Sub.charAt(0)) {
			case 'c':

				for (int c = 0; c < 3; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}

				// fillWithStrings( Sub, 5 );

				result = cache;

				sector.setColor(Color.getColorFromHTMLColor(result[2]),
						Integer.parseInt(result[1]), fakeShading);
				break;
			case 'p':

				for (int c = 0; c < 7; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}

				// fillWithStrings( Sub, 7 );

				result = cache;
				sector.setLinks(plusOneIfNotZero(Integer.parseInt(result[1])),
						plusOneIfNotZero(Integer.parseInt(result[2])),
						plusOneIfNotZero(Integer.parseInt(result[3])),
						plusOneIfNotZero(Integer.parseInt(result[4])),
						plusOneIfNotZero(Integer.parseInt(result[5])),
						plusOneIfNotZero(Integer.parseInt(result[6])));

				break;
			case 'l':
				for (int c = 0; c < 7; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}

				// fillWithStrings( Sub, 7 );

				result = cache;
				sector.setLinks(Integer.parseInt(result[1]),
						Integer.parseInt(result[2]),
						Integer.parseInt(result[3]),
						Integer.parseInt(result[4]),
						Integer.parseInt(result[5]),
						Integer.parseInt(result[6]));

				break;
			case 'n': {
				cache[0] = Utils.getSubToken(Sub, 1);
				Sector master;
				int n = Integer.parseInt(cache[0]);

				if (n > 0) {
					master = getSector(n);
					master.addSon(sector);

				}
			}
				break;
			case 'a': {
				for (int c = 0; c < 3; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}

				// fillWithStrings( Sub, 3 );

				result = cache;
				Actor3D actor = makeActorAt(Integer.parseInt(result[1]),
						Integer.parseInt(result[2]));
				actor.setId(people.size());
				people.add(actor);
			}
				break;

			case 'd':

				for (int c = 0; c < 3; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}
				result = cache;

				makeDoorAt(sector, Integer.parseInt(result[1]),
						Integer.parseInt(result[2]), null);
				break;
			case 't':
				for (int c = 0; c < 3; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}
				result = cache;

				if (sector.isMaster()) {
					applyDecalTo(server, sector, Integer.parseInt(result[1]),
							result[2]);
				}
				break;
			case 'i':
				sector.setExtraInformation(Sub.substring(2));
				break;
			case 'r':
				sector.name = Sub.substring(2);
				break;
			case 'o':
			case 'm':
			case 's': {
				for (int c = 0; c < 7; ++c) {
					cache[c] = Utils.getSubToken(Sub, c);
				}
				// fillWithStrings( Sub, 7 );

				result = cache;

				vals[0] = Float.parseFloat(result[1]);
				vals[1] = Float.parseFloat(result[2]);
				vals[2] = Float.parseFloat(result[3]);
				vals[3] = Float.parseFloat(result[4]);
				vals[4] = Float.parseFloat(result[5]);
				vals[5] = Float.parseFloat(result[6]);

				sector = makeSector(vals[0], vals[0] + vals[1], vals[2],
						vals[2] + vals[3], vals[4], vals[4] + vals[5], factory);
				sector.setIsMaster(Sub.charAt(0) == 'm');
				sector.setId(sectorList.size());

				sectorList.add(sector);

			}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyDecalTo(FileServerDelegate server, Sector sector,
			int face, String decalName) {
		sector.setDecalAt(face, decalName);
	}

	public void makeDoorAt(Sector origin, int face, int sectorId,
			String extraData) {
		origin.setDoorAt(face, sectorId);
	}

	public Sector makeSector(float x0, float x1, float y0, float y1, float z0,
			float z1, MeshFactory factory) {
		return new Sector(x0, x1, y0, y1, z0, z1);
	}

	// ------------------------------------------------------------------------------------------------------------
	public Actor3D makeActorAt(int sector, int candelas) {
		Actor3D actor = new Actor3D();
		actor.setCurrentSector(sector);
		actor.setEmissiveLightningIntensity(candelas);
		return actor;
	}

	private int plusOneIfNotZero(int Int) {
		if (Int != 0) {
			return Int;
		} else {
			return 0;
		}
	}

	public void addSector(Sector sector) {

		sector.setId(sectorList.size());
		sectorList.add(sector);
	}

	public void removeSector(Sector sector) {
		removeSector(sector, true);
	}

	public void removeSector(Sector sector, boolean rearrangeIds) {

		sectorList.remove(sector);

		if (rearrangeIds) {
			Sector another;
			int link;

			for (int c = 0; c < getTotalSectors(); ++c) {

				another = getSector(c);

				for (int d = 0; d < 6; ++d) {

					try {
						link = another.getLink(d);
					} catch (InvalidSlotException e) {
						continue;
					}

					if (link > sector.getId()) {
						another.setLink(d, link - 1);
					}
				}
			}

			for (int e = sector.getId(); e < getTotalSectors(); ++e) {
				getSector(e).setId(e - 1);
			}
		}
	}

	public void removeSector(int index) {
		Sector sector = getSector(index);
		sectorList.remove(sector);
	}

	public void removeSector(int index, boolean rearrangeIds) {
		Sector sector = getSector(index);
		removeSector(sector, rearrangeIds);
	}

	public boolean checkConsistency() {
		Sector s;

		for (int c = 1; c < getTotalSectors(); ++c) {

			s = getSector(c);

			if (s.getId() != c) {
				System.out
						.println("id de setor n������������������o consistente: "
								+ c + "!=" + s.getId());
				return false;
			}

			for (int d = 0; d < 6; ++d) {
				try {
					if (s.getLink(d) >= getTotalSectors() || s.getLink(d) < 0) {
						System.out
								.println("link para setor n������������������o existente: s["
										+ c
										+ "].link["
										+ d
										+ "]="
										+ s.getLink(d)
										+ " ( total = "
										+ getTotalSectors() + ")");
						return false;
					}
					if (s.getLink(d) == c) {
						System.out.println("setor linkando para si mesmo: s["
								+ c + "].link[" + d + "]=" + s.getLink(d));
						return false;
					}
				} catch (InvalidSlotException e) {
					System.out
							.println("setor causou exce������������������������������������o ao pedir link: s["
									+ c + "]");
					return false;
				}
			}
		}

		return true;
	}

	public boolean contains(Sector sector) {
		return this.sectorList.contains(sector);
	}

	public void addMasters(World worldBefore) {
		Sector master;

		for (int c = 1; c < worldBefore.getTotalSectors(); ++c) {

			if (worldBefore.getSector(c).isDegenerate()) {
				continue;
			}

			master = new Sector(worldBefore.getSector(c));
			master.setIsMaster(true);
			this.addSector(master);
		}
	}

	// public SVGGraphic toSVGPolygonList() {
	//
	// SVGGraphic toReturn = new SVGGraphic();
	//
	// ArrayList< ColoredPolygon > temporaryList = new
	// ArrayList<ColoredPolygon>();
	//
	// for (int c = 0; c < getTotalSectors(); ++c) {
	//
	// temporaryList.add( getSector(c).toSVGTopViewShape() );
	// }
	//
	// toReturn.fromArrayList( temporaryList );
	//
	// return toReturn;
	// }
	public void saveToDiskAsLevel(OutputStream os) {
		saveToDiskAsLevel(os, false);
	}

	public void saveToDiskAsLevel(OutputStream os, boolean writeSolids) {

		try {

			for (int c = 0; c < getTotalSectors(); ++c) {

				os.write((getSector(c).toString() + "\n").getBytes());
			}

			if (actorList != null) {
				for (int c = 0; c < actorList.size(); ++c) {
					os.write((actorList.get(c).toString()).getBytes());
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		System.out.println("wrote " + getTotalSectors() + " sectors to disk");
	}

	// public void saveToDiskAsLevel( final String filename ) {
	// saveToDiskAsLevel( filename, null );
	// }
	//
	// public void saveToDiskAsLevel( final String filename, final Vector
	// actors) {
	//
	//
	// int saved = 0;
	//
	// try {
	// final FileOutputStream fos = new FileOutputStream(filename);
	// final BufferedWriter bw = new BufferedWriter(
	// new OutputStreamWriter(fos));
	//
	// for (int c = 0; c < getTotalSectors(); ++c) {
	//
	// // if ( getSector(c).isMaster() )
	// // continue;
	//
	// ++saved;
	//
	// if (!(getSector(c).isSolid() && getSector(c)
	// .isDegenerate()))
	// bw.write(getSector(c).toString() + "\n");
	// else
	// System.out.println("sector " + c + ": not recorded");
	// }
	//
	// if (actors != null) {
	// for (int c = 0; c < actors.size(); ++c) {
	// bw.write(((Actor) actors.get(c)).toString());
	// }
	// }
	//
	// bw.close();
	//
	// } catch (final Exception e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println("wrote " + saved + " sectors to disk");
	// }
	public void addActor(Actor3D actor, int transientStartSector) {
	}

	public void addMastersBefore(World worldBefore) {
		Sector master;
		int num = worldBefore.getTotalSectors();

		for (int c = 0; c < getTotalSectors(); ++c) {
			getSector(c).setId(getSector(c).getId() + num);
			for (int d = 0; d < 6; ++d) {
				try {
					if (getSector(c).getLink(d) != 0) {
						getSector(c).setLink(d, getSector(c).getLink(d) + num);
					}
					// não
					// deveria existir, mas...
					if (getSector(c).getDoor(d) != null) {
						getSector(c).getDoor(d).setSector(
								getSector(c).getDoor(d).getSector() + num);
					}

				} catch (InvalidSlotException e) {
					// TODO Properly  report error, somehow.
					e.printStackTrace();
				}
			}
		}

		for (int c = worldBefore.getTotalSectors() - 1; c >= 0; --c) {

			if (worldBefore.getSector(c).isDegenerate()) {
				continue;
			}

			master = new Sector(worldBefore.getSector(c));
			master.setIsMaster(true);
			this.addSectorAtBegin(master);
		}

		// setor raiz
		master = new Sector();
		master.setIsMaster(true);
		this.addSectorAtBegin(master);

		for (int c = 0; c < getTotalSectors(); ++c) {
			System.out.println("id " + getSector(c).getId() + " se torna id = "
					+ c);

			getSector(c).setId(c);
		}
	}

	private void addSectorAtBegin(Sector master) {
		this.sectorList.add(0, master);
	}

	public void destroy() {

		if (sectorList != null) {
			for (Sector s : sectorList) {
				s.destroy();
			}
		}

		sector = null;

		if (actorList != null) {
			for (Actor3D a : actorList) {
				a.destroy();
			}
		}

		if (actorList != null) {
			actorList.clear();
		}

		actorList = null;

		if (sectorList != null) {
			sectorList.clear();
		}

		sectorList = null;
	}

	@Override
	public Iterator<Sector> iterator() {

		return sectorList.iterator();
	}

	public void addSectorAtIndex(Sector sector, int i) {
		sectorList.add(i, sector);
	}

	public int getTotalMasterSectors() {
		int total = 0;

		for (Sector s : sectorList) {
			if (s.isMaster()) {
				++total;
			}
		}

		return total;
	}

	public void reassignConnectionsWith(int id, int newId) {
		for (Sector s : this) {
			for (int c = 0; c < 6; ++c) {
				try {
					if (s.getLink(c) == id) {
						s.setLink(c, newId);
					}
				} catch (InvalidSlotException ex) {
					Logger.getLogger(World.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}
	}
}