package br.odb.libscene;

public abstract class Constants {

	public static final int FACE_N = 0;
	public static final int FACE_E = 1;
	public static final int FACE_S = 2;
	public static final int FACE_W = 3;
	public static final int FACE_FLOOR = 4;
	public static final int FACE_CEILING = 5;
	public static final int INSIDE = 100;
	public static final int NO_LINK = 0;
	
	public static int getLeft(int currentDirection) {
		int toReturn = currentDirection;

		if (toReturn < Constants.FACE_FLOOR) {

			--toReturn;

			while (toReturn < 0)
				toReturn += 4;

			while (toReturn >= 4)
				toReturn -= 4;
		}

		return toReturn;
	}

	public static int getRight(int dir) {
		int toReturn = dir;

		if (toReturn < Constants.FACE_FLOOR) {

			++toReturn;

			while (toReturn < 0)
				toReturn += 4;

			while (toReturn >= 4)
				toReturn -= 4;
		}

		return toReturn;
	}
}
