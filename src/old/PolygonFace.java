package br.odb.libscene.old;

import java.util.ArrayList;

public class PolygonFace {
	public ArrayList<Integer> indexes;

	public PolygonFace() {
		indexes = new ArrayList<Integer>();
	}

	public PolygonFace(int[] ints) {
		indexes = new ArrayList<Integer>();

		for (int c = 0; c < ints.length; ++c) {
			addIndex(ints[c]);
		}
	}

	public PolygonFace(PolygonFace poly) {

		indexes = new ArrayList<Integer>();

		for (Integer i : poly.indexes) {
			indexes.add(i);
		}
	}

	public boolean equals(PolygonFace another) {
		boolean isEqual = true;
		int size = indexes.size();

		for (int c = 0; c < size; ++c) {
			isEqual = isEqual && indexes.get(c).equals(another.getIndex(c));
		}

		return isEqual;
	}

	private Integer getIndex(int c) {
		return indexes.get(c);
	}

	@Override
	public boolean equals(Object another) {
		if (another instanceof PolygonFace) {
			return equals((PolygonFace) another);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return toString().hashCode();
	}

	@Override
	public String toString() {
		String toReturn = "";

		for (Integer i : indexes) {
			toReturn += i.intValue() + " ";
		}

		return toReturn;
	}

	public void addIndex(int i) {
		indexes.add(Integer.valueOf(i));
	}

	public PolygonFace makeCopy() {
		return new PolygonFace(this);
	}
}
