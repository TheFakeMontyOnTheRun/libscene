package br.odb.libscene.old;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.odb.gameworld.Direction;
import br.odb.gameworld.exceptions.InvalidSlotException;
import br.odb.libstrip.Mesh;
import br.odb.utils.Color;
import br.odb.utils.Utils;
import br.odb.utils.math.Vec3;

/**
 * 
 * @author Daniel "Monty" Monteiro
 * 
 */
public class Sector {

	public void setDoorAt(Direction face) {		
		this.setDoorAt(face, this.getLink(face));		
	}
	
	public static Sector getConvexHull( int snapLevel, Mesh mesh ) {
		Sector sector = new Sector();

		sector.name = mesh.getName();
		sector.setIsMaster(true);

		if (mesh.material != null)
			for (int o = 0; o < 6; ++o)
				sector.setColor(new Color( mesh.material.getMainColor()), o);

		Vec3 center;

		if ( mesh.points.size() < 1)
			return sector;

		// find the center point;
		center = new Vec3( mesh.points.get(0));

		for (int c = 1; c < mesh.points.size(); ++c) {
			center.set(center.add( mesh.points.get(c)));
		}

		center.scale(1.0f / mesh.points.size());

		// make the box stay at the center;

		sector.setX0(center.x);
		sector.setY0(center.y);
		sector.setZ0(center.z);
		sector.setX1(center.x);
		sector.setY1(center.y);
		sector.setZ1(center.z);

		for (int d = 0; d < mesh.points.size(); ++d) {
			center = ( mesh.points.get(d));

			if (center.x < sector.getX0())
				sector.setX0(center.x);

			if (center.x > sector.getX1())
				sector.setX1(center.x);

			if (center.y < sector.getY0())
				sector.setY0(center.y);

			if (center.y > sector.getY1())
				sector.setY1(center.y);

			if (center.z < sector.getZ0())
				sector.setZ0(center.z);

			if (center.z > sector.getZ1())
				sector.setZ1(center.z);
		}

		if ( snapLevel > 0) {
			sector.setX0(snap(sector.getX0()));
			sector.setX1(snap(sector.getX1()));
			sector.setY0(snap(sector.getY0()));
			sector.setY1(snap(sector.getY1()));
			sector.setZ0(snap(sector.getZ0()));
			sector.setZ1(snap(sector.getZ1()));
		}
		return sector;
	}


	private static float snap(float f) {

		return Math.round( f );
	}

	public boolean pick(Vec3 vec) {
		float x = vec.x;
		float y = vec.y;
		float z = vec.z;

		boolean inside = false;

		inside = Float.isNaN(x)
				|| ((getX0() <= x || Utils.eqFloat(getX0(), x)) && x <= getX1() || Utils
						.eqFloat(getX1(), x));

		inside = inside
				&& (Float.isNaN(y) || ((getY0() <= y || Utils.eqFloat(getY0(),
						y)) && y <= getY1() || Utils.eqFloat(getY1(), y)));

		inside = inside
				&& (Float.isNaN(z) || ((getZ0() <= z || Utils.eqFloat(getZ0(),
						z)) && z <= getZ1() || Utils.eqFloat(getZ1(), z)));

		return inside;
	}

	public void resizeTo(Vec3 v) {
		x1 = v.x;
		y1 = v.y;
		z1 = v.z;

		if (x1 < x0) {
			x1 = x0;
		}

		if (y1 < y0) {
			y1 = y0;
		}

		if (z1 < z0) {
			z1 = z0;
		}
	}

	private String extraInformation;

	/**
	 * @return the extraInformation
	 */
	public String getExtraInformation() {
		return extraInformation;
	}

	/**
	 * @param extraInformation
	 *            the extraInformation to set
	 */
	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}

	private boolean master;
	private int parent = 0;
	private String[] decalName = new String[6];
	private int id;
	public String name;
	/**
     *
     */
	private float x0;
	/**
     *
     */
	private float y0;
	/**
     *
     */
	private float z0;
	/**
     *
     */
	private float x1;
	/**
     *
     */
	private float y1;
	/**
     *
     */
	private float z1;
	/**
     *
     */
	private Mesh mesh;
	/**
     *
     */
	private int[] link = new int[6];
	private Color[] color = new Color[6];
	protected Door[] doors = new Door[6];
	private String mapLink;
	private ArrayList<Sector> sons;

	public static int getRight(int dir) {
		int toReturn = dir;

		if (toReturn < Constants.FACE_FLOOR) {
			toReturn = (toReturn + 1) % 4;
		}

		return toReturn;
	}

	public boolean isMaster() {
		return master;
	}

	public void setIsMaster(boolean master) {
		this.master = master;

		if (master) {
			sons = new ArrayList<Sector>();
		} else if (sons != null) {
			sons.clear();
			sons = null;
		}
	}

	public void addSon(Sector s) {

		s.setParent(id);

		if (sons == null) {

			sons = new ArrayList<Sector>();
		}

		sons.add(s);
	}

	public void setParent(int sector) {
		parent = sector;
	}

	public String getMapLink() {
		return mapLink;
	}

	public void setMapLink(String mapLink) {
		this.mapLink = mapLink;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
     *
     */
	public Sector(Mesh mesh) {
		this();
		this.mesh = mesh;
		Vec3 v;
		// /calculates the bounding box of the mesh
		for (int c = 0; c < this.mesh.points.size(); ++c) {
			v = this.mesh.points.get(c);

			if (x0 >= v.x) {
				x0 = v.x;
			}

			if (y0 >= v.y) {
				y0 = v.y;
			}

			if (z0 >= v.z) {
				z0 = v.z;
			}

			if (x1 <= v.x) {
				x1 = v.x;
			}

			if (y1 <= v.y) {
				y1 = v.y;
			}

			if (z1 <= v.z) {
				z1 = v.z;
			}
		}
	}

	/**
	 * 
	 * @param another
	 */
	public Sector(Sector another) {
		this();
		initFrom(another);
	}

	public void initFrom(Sector another) {

		set(another.x0, another.y0, another.z0, another.x1, another.y1,
				another.z1);
		parent = another.parent;
		id = another.id;
		mesh = another.mesh;
		mapLink = another.mapLink;

		if (another.getMesh() != null) {
			this.mesh = new Mesh(another.getMesh());
		}

		for ( Direction c : Direction.values()) {
			

			color[c.ordinal()] = new Color(another.color[c.ordinal()]);

			if (another.getDoor(c) != null) {
				setDoorAt(c, another.getDoor(c).getSector());
			}


			this.link[c.ordinal()] = another.getLink(c);

			decalName[c.ordinal()] = another.decalName[c.ordinal()];

		}

		if (another.isMaster()) {

			setIsMaster(true);

			Sector[] s = another.getSons();

			if (s != null) {
				for (int c = 0; c < s.length; ++c) {
					addSon(s[c]);
				}
			}
		}

	}

	public Sector[] getSons() {
		Sector[] toReturn = null;

		if (sons != null) {

			toReturn = new Sector[sons.size()];
			sons.toArray(toReturn);
		}

		return toReturn;
	}

	public Sector() {
		this(0, 0, 0, 0, 0, 0);
	}

	public Sector(float x0, float x1, float y0, float y1, float z0, float z1) {

		set(x0, y0, z0, x1, y1, z1);
		id = -1;
		parent = 0;
		// this.mesh = new Mesh( );
		link = new int[6];
		color = new Color[6];

		for (int c = 0; c < 6; ++c) {
			link[c] = Constants.NO_LINK;
			color[c] = new Color();
		}
	}

	/**
	 * 
	 * @return
	 */
	public Mesh getMesh() {
		return mesh;
	}

	/**
	 * "Oh...I see what you did there!" A recursive set function that will go as
	 * deep as needed changing variables until everything is in order...
	 * 
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param x1
	 * @param y1
	 * @param z1
	 */
	public void set(float x0, float y0, float z0, float x1, float y1, float z1) {

		if (x0 > x1) {
			set(x1, y0, z0, x0, y1, z1);
		} else if (y0 > y1) {
			set(x0, y1, z0, x1, y0, z1);
		} else if (z0 > z1) {
			set(x0, y0, z1, x1, y1, z0);
		} else {
			this.x0 = x0;
			this.y0 = y0;
			this.z0 = z0;
			this.x1 = x1;
			this.y1 = y1;
			this.z1 = z1;
		}
	}

	@Override
	public String toString() {

		String sString = "#sector" + this.id + "\n";

		if (name != null && name.length() > 0) {
			sString += "r " + name + "\n";
		}

		if (isMaster()) {
			sString += "m ";
		} else {
			sString += "s ";
		}

		if (World.snapLevel > 2) {
			sString += (int) getX0();
			sString += " ";
			sString += (int) getDX();
			sString += " ";
			sString += (int) getY0();
			sString += " ";
			sString += (int) getDY();
			sString += " ";
			sString += (int) getZ0();
			sString += " ";
			sString += (int) getDZ();
		} else {
			sString += getX0();
			sString += " ";
			sString += getDX();
			sString += " ";
			sString += getY0();
			sString += " ";
			sString += getDY();
			sString += " ";
			sString += getZ0();
			sString += " ";
			sString += getDZ();
		}

		sString += "\n";
		sString += "n " + parent;

		sString += "\n";
		sString += "p";

		for (int c = 0; c < 6; ++c) {
			sString += " " + link[c];
		}

		for (int c = 0; c < 6; ++c) {

			if (link[c] == Constants.NO_LINK /* || doors[ c ] != null */) {
				sString += "\nc " + c + " " + color[c].getHTMLColor();
			}
		}

		for (int c = 0; c < 6; ++c) {
			if (doors[c] != null) {
				sString += "\nd " + c + " " + doors[c].getSector();
			}
		}

		for (int c = 0; c < 6; ++c) {
			if (decalName[c] != null) {
				sString += "\nt " + c + " " + decalName[c];
			}
		}

		return sString;
	}

	public int getLink(Direction c) {

		return link[c.ordinal()];
	}

	/**
	 * Link uniterally two sectors through a face
	 * 
	 * @param f
	 *            Face to link
	 * @param s
	 *            Sector to link
	 */
	public void setLink(Direction f, int s) {
		link[f.ordinal()] = s;
	}

	public boolean isDegenerate() {

		return Utils.eqFloat(x0, x1) || Utils.eqFloat(y0, y1)
				|| Utils.eqFloat(z0, z1) || (x1 - x0 < 0.0f)
				|| (y1 - y0 < 0.0f) || (z1 - z0 < 0.0f);
	}

	// public boolean equals(Sector another) {
	//
	// if (another == null)
	// return false;
	//
	// boolean isEqual = true;
	//
	// isEqual = isEqual && coincidant(another);
	//
	// if (mesh != null)
	// isEqual = isEqual && mesh.equals(another.getMesh());
	// else
	// isEqual = isEqual && (another.getMesh() == null);
	//
	// try {
	// for (int c = 0; c < 6; ++c) {
	// isEqual = isEqual && (link[c] == another.getLink(c));
	// isEqual = isEqual && color[c].equals(another.color[c]);
	// isEqual = isEqual && solid == (another.solid);
	// isEqual = isEqual && id == (another.id);
	// }
	// } catch (InvalidSlotException e) {
	// return false;
	// }
	//
	// return isEqual;
	// }
	//
	// public boolean equals(Object another) {
	// if (another instanceof Sector) {
	// return equals((Sector) another);
	// } else {
	// return false;
	// }
	// }
	public boolean coincidant(Sector sector) {

		if (sector == null) {
			return false;
		}

		if (Utils.eqFloat(sector.x0, x0) && Utils.eqFloat(sector.y0, y0)
				&& Utils.eqFloat(sector.x1, x1) && Utils.eqFloat(sector.y1, y1)
				&& Utils.eqFloat(sector.z0, z0) && Utils.eqFloat(sector.z1, z1)) {
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(x0);
		result = prime * result + Float.floatToIntBits(x1);
		result = prime * result + Float.floatToIntBits(y0);
		result = prime * result + Float.floatToIntBits(y1);
		result = prime * result + Float.floatToIntBits(z0);
		result = prime * result + Float.floatToIntBits(z1);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Sector)) {
			return false;
		}
		Sector other = (Sector) obj;
		if (Float.floatToIntBits(x0) != Float.floatToIntBits(other.x0)) {
			return false;
		}
		if (Float.floatToIntBits(x1) != Float.floatToIntBits(other.x1)) {
			return false;
		}
		if (Float.floatToIntBits(y0) != Float.floatToIntBits(other.y0)) {
			return false;
		}
		if (Float.floatToIntBits(y1) != Float.floatToIntBits(other.y1)) {
			return false;
		}
		if (Float.floatToIntBits(z0) != Float.floatToIntBits(other.z0)) {
			return false;
		}
		if (Float.floatToIntBits(z1) != Float.floatToIntBits(other.z1)) {
			return false;
		}
		return true;
	}

	// ------------------------------------------------------------------------------------------------------------
	public void setColor(Color color, int index) {
		setColor(color, index, false);
	}

	public void setColor(Color newColor, int i, boolean fakeShading) {
		this.color[i] = newColor;

		if (!fakeShading || i == 4) {
			color[i].r=(color[i].r);
			color[i].g=(color[i].g);
			color[i].b=(color[i].b);
		} else if (i == 5) {
			color[i].r=((int) (color[i].r * 0.25f));
			color[i].g=((int) (color[i].g * 0.25f));
			color[i].b=((int) (color[i].b * 0.25f));
		} else {
			color[i].r=((int) (color[i].r * 0.75f * ((0.15f) + (i / 4.0f))));
			color[i].g=((int) (color[i].g * 0.75f * ((0.15f) + (i / 4.0f))));
			color[i].b=((int) (color[i].b * 0.75f * ((0.15f) + (i / 4.0f))));
		}

	}

	public boolean contains(Vec3 vec) {
		float x = vec.x;
		float y = vec.y;
		float z = vec.z;

		boolean inside = false;

		inside = ((getX0() <= x || Utils.eqFloat(getX0(), x)) && x <= getX1() || Utils
				.eqFloat(getX1(), x))
				&& ((getY0() <= y || Utils.eqFloat(getY0(), y)) && y <= getY1() || Utils
						.eqFloat(getY1(), y))
				&& ((getZ0() <= z || Utils.eqFloat(getZ0(), z)) && z <= getZ1() || Utils
						.eqFloat(getZ1(), z));

		return inside && !blockedByDoor(vec);
	}

	public boolean blockedByDoor(Vec3 vec) {

		float x = vec.x;
		float y = vec.y;
		float z = vec.z;

		for ( Direction d : Direction.values() ) {		
		
			if (doors[ d.ordinal() ] != null && !doors[ d.ordinal() ].isOpen()) {

				switch ( d ) {

				case CEILING:

					if (Utils.eqFloatTrunc10(getY1(), y)) {
						return true;
					}
					break;

				case FLOOR:
					if (Utils.eqFloatTrunc10(getY0(), y)) {
						return true;
					}
					break;

				case N:
					if (Utils.eqFloatTrunc10(getZ0(), z)) {
						return true;
					}
					break;

				case S:
					if (Utils.eqFloatTrunc10(getZ1(), z)) {
						return true;
					}
					break;

				case W:
					if (Utils.eqFloatTrunc10(getX0(), x)) {
						return true;
					}
					break;

				case E:
					if (Utils.eqFloatTrunc10(getX1(), x)) {
						return true;
					}
					break;

				}
			}
		}

		return false;
	}

	public boolean contains(Sector sector) {

		if (sector == null) {
			return false;
		}

		if (sector.x0 >= x0 && sector.x1 <= x1 && sector.y0 >= y0
				&& sector.y1 <= y1 && sector.z0 >= z0 && sector.z1 <= z1) {
			return true;
		}

		return false;
	}

	public float getDX() {
		return x1 - x0;
	}

	public float getDY() {
		return y1 - y0;
	}

	public float getDZ() {
		return z1 - z0;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getX0() {
		return x0;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getX1() {
		return x1;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getY0() {
		return y0;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getY1() {
		return y1;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getZ0() {
		return z0;
	}

	// ------------------------------------------------------------------------------------------------------------
	public float getZ1() {
		return z1;
	}

	public void setDX(float x) {

		if (x < 0.0f) {
			x = 0.0f;
		}

		x1 = x0 + x;
	}

	public void setDY(float y) {

		if (y < 0.0f) {
			y = 0.0f;
		}

		y1 = y0 + y;
	}

	public void setDZ(float z) {

		if (z < 0.0f) {
			z = 0.0f;
		}

		z1 = z0 + z;
	}

	public void setLinks(int l0, int l1, int l2, int l3, int l4, int l5) {
		setLink(Direction.N, l0);
		setLink(Direction.E, l1);
		setLink(Direction.S, l2);
		setLink(Direction.W, l3);
		setLink(Direction.FLOOR, l4);
		setLink(Direction.CEILING, l5);
	}

	public Color getColor(int d) {
		return color[d];
	}

	public void setX0(float x) {
		this.x0 = x;
	}

	public void moveTo(Vec3 target) {

		if (!target.isValid()) {
			return;
		}

		if (target.x == Float.MAX_VALUE || target.x == Float.MIN_VALUE) {
			return;
		}

		if (target.y == Float.MAX_VALUE || target.y == Float.MIN_VALUE) {
			return;
		}

		if (target.z == Float.MAX_VALUE || target.z == Float.MIN_VALUE) {
			return;
		}

		float dx = getDX();
		float dy = getDY();
		float dz = getDZ();

		x0 = target.x;
		y0 = target.y;
		z0 = target.z;

		x1 = x0 + dx;
		y1 = y0 + dy;
		z1 = z0 + dz;
	}

	public void setY0(float y) {
		y0 = y;
	}

	public void setZ0(float z) {
		z0 = z;
	}

	public void setX1(float x) {
		x1 = x;
	}

	public void setY1(float y) {
		y1 = y;
	}

	public void setZ1(float z) {
		z1 = z;
	}

	public Vec3 getCenter() {
		return new Vec3(getX0() + (getDX() / 2), getY0() + (getDY() / 2),
				getZ0() + (getDZ() / 2));
	}

	public Vec3 getCenterForEdge( Direction edge) {

		Vec3 center = getCenter();

		switch (edge) {

		case CEILING:

			center.y = getY1();
			break;

		case FLOOR:
			center.y = getY0();
			break;

		case N:
			center.z = getZ0();
			break;

		case S:
			center.z = getZ1();
			break;

		case W:
			center.x = getX0();
			break;

		case E:
			center.x = getX1();
			break;

		}

		return center;
	}

	public boolean touches(Vec3 v) {
		float x = v.x;
		float y = v.y;
		float z = v.z;

		boolean xIsIn = Float.isInfinite(x)
				|| (!Float.isInfinite(x) && x0 <= x && x <= x1);
		boolean yIsIn = Float.isInfinite(y)
				|| (!Float.isInfinite(y) && y0 <= y && y <= y1);
		boolean zIsIn = Float.isInfinite(z)
				|| (!Float.isInfinite(z) && z0 <= z && z <= z1);

		// System.out.println( "x:" + xIsIn + " y:" + yIsIn + " z:" + zIsIn );

		return xIsIn && yIsIn && zIsIn;
	}

	public boolean intersect(Sector s1) {

		if (s1 == null) {
			return false;
		}

		Vec3[] points = s1.getPoints();

		for (int c = 0; c < points.length; ++c) {

			if (contains(points[c])) {
				return true;
			}
		}

		points = getPoints();

		for (int c = 0; c < points.length; ++c) {

			if (s1.contains(points[c])) {
				return true;
			}
		}

		return false;
	}

	public Vec3[] getPoints() {

		Vec3[] toReturn = new Vec3[8];

		toReturn[0] = new Vec3(x0, y0, z0);
		toReturn[1] = new Vec3(x0, y0, z1);
		toReturn[2] = new Vec3(x0, y1, z0);
		toReturn[3] = new Vec3(x0, y1, z1);
		toReturn[4] = new Vec3(x1, y0, z0);
		toReturn[5] = new Vec3(x1, y0, z1);
		toReturn[6] = new Vec3(x1, y1, z0);
		toReturn[7] = new Vec3(x1, y1, z1);

		return toReturn;
	}

	public boolean isOpenAt(int slot) {
		return (doors[slot] == null) ? link[slot] != Constants.NO_LINK
				: doors[slot].isOpen();
	}

	public Door getDoor(Direction c) {

		return doors[c.ordinal()];
	}

	public void removeDoorAt(int slot) {
		doors[slot] = null;
	}

	public void onSectorEnteredBy(Actor3D actor) {
	}

	public void setDoorAt(Direction slot, int sector) {
		doors[slot.ordinal()] = new Door(sector);
	}

	public void onSectorLeftBy(Actor3D actor) {
	}

	public int[] getNeighbours() {
		return this.link;
	}

	public int getParent() {
		return parent;
	}

	// public String toSVGTopView(boolean randomColor) {
	//
	// return toSVGTopViewShape(randomColor).toString();
	// }
	//
	// public ColoredPolygon toSVGTopViewShape(boolean randomColor) {
	// ColoredPolygon toReturn = ColoredPolygon.makeRect("" + id, solid);
	//
	// float x;
	// float y;
	// float width;
	// float height;
	//
	// for (int c = 0; c < 6; ++c) {
	//
	// try {
	//
	// switch (c) {
	// case Constants.FACE_N:
	// x = getX0();
	// y = getZ0();
	// width = getDX();
	// height = 1.0f;
	// break;
	//
	// case Constants.FACE_S:
	// x = getX0();
	// y = getZ1();
	// width = getDX();
	// height = 1.0f;
	// break;
	//
	// case Constants.FACE_W:
	// x = getX0();
	// y = getZ0();
	// height = getDZ();
	// width = 1.0f;
	// break;
	//
	// case Constants.FACE_E:
	// x = getX1();
	// y = getZ0();
	// height = getDZ();
	// width = 1.0f;
	// break;
	//
	// case Constants.FACE_FLOOR:
	// x = getX1() - 5;
	// y = getZ1() - 5;
	// height = getDZ();
	// width = getDX();
	// break;
	//
	// case Constants.FACE_CEILING:
	// x = getX0();
	// y = getZ0();
	// height = getDZ();
	// width = getDX();
	// break;
	// default:
	// x = getX0();
	// y = getZ0();
	// height = 1.0f;
	// width = 1.0f;
	// }
	//
	// if (getLink(c) == Constants.NO_LINK) {
	// // toReturn += "\n<color face = '" + c + "' color = '" +
	// // getColor( c ).getARGBColor() + "' />" ;
	// // toReturn += "\n<rect style = 'fill:#000000;' x = '" + x +
	// // "' y = '" + y + "' width = '" + width +"' height = '" +
	// // height +"' />" ;
	// }
	//
	// if (getDoor(c) != null) {
	// // toReturn += "\n<door face = '" + c + "' link = '" +
	// // getLink( c ) + "' />" ;
	// // toReturn += "\n<rect style = 'fill:#FF0000;' x = '" + x +
	// // "' y = '" + y + "' width = '" + width +"' height = '" +
	// // height +"' />" ;
	// }
	//
	// ColoredPolygon.addPoint(new Vec2(x, y));
	// ColoredPolygon.setWidth(width);
	// ColoredPolygon.setHeight(height);
	// //
	// // toReturn += "\n<link face = '" + c + "' sector = '" +
	// // getLink( c ) + "' />" ;
	// } catch (InvalidSlotException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// return toReturn;
	// }
	//
	// public ColoredPolygon toSVGTopViewShape() {
	// return toSVGTopViewShape(true);
	// }
	public int getVisibleFacesCount() {
		int opaqueFaces = 0;
		
		for ( Direction c : Direction.values() ) {
			
			if (getLink(c) == Constants.NO_LINK) {
				++opaqueFaces;
			}
		}

		return opaqueFaces;
	}

	public void removeAllDoors() {
		for (int c = 0; c < 6; ++c) {
			this.doors[c] = null;
		}

	}

	// public boolean intersect(Line3 line) {
	//
	// GeneralPolygon[] quads = makeQuads();
	// Vec3 projection;
	// GeneralPolygon q;
	//
	// for ( int c = 0; c < quads.length; ++c ) {
	//
	// q = quads[ c ];
	// projection = q.getProjection( line );
	//
	// if ( q.contains( projection ) )
	// return true;
	// }
	//
	// return false;
	// }
	//
	//
	// private GeneralPolygon[] makeQuads() {
	//
	// GeneralPolygon[] toReturn = new GeneralPolygon[ 6 ];
	// GeneralPolygon face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 0 ] = face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 1 ] = face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 2 ] = face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 3 ] = face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 4 ] = face;
	//
	//
	// face = new GeneralPolygon();
	// toReturn[ 5 ] = face;
	//
	//
	//
	//
	// return toReturn;
	// }
	public int touchesInPoints(Sector sector) {

		int toReturn = 0;

		Vec3[] points = this.getPoints();

		for (int c = 0; c < points.length; ++c) {
			if (sector.touches(points[c])) {
				++toReturn;
			}
		}

		return toReturn;
	}

	public boolean touches(Sector sector) {

		Vec3[] points = this.getPoints();

		for (int c = 0; c < points.length; ++c) {
			if (sector.touches(points[c])) {
				return true;
			}
		}

		return false;
	}

	public String getDecalAt(int face) {

		return decalName[face];
	}

	public void setDecalAt(int face, String decalName) {

		this.decalName[face] = decalName;
	}

	public void destroy() {

		if (mesh != null) {
			mesh.destroy();
		}

		for (int c = 0; c < 6; ++c) {
			if (doors[c] != null) {
				doors[c].destroy();
			}
		}

		link = null;
		color = null;
		doors = null;

	}

	public void cleanUpLinks() {

		for (int c = 0; c < 6; ++c) {
			link[c] = 0;
		}
	}

	public void cleanUpMeshes() {

		mesh = null;
		for (int c = 0; c < 6; ++c) {
			decalName[c] = null;
		}
	}

	public ArrayList<Mesh> getMeshes() {
		return null;
	}

	public void moveBy(Vec3 minPoint) {
		moveTo(minPoint.add(new Vec3(x0, y0, z0)));
	}
}
