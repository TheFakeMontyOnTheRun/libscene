/**
 * 
 */
package br.odb.libscene;

import br.odb.libstrip.Mesh;

/**
 * @author monty
 * 
 */
public class Door {

	private boolean open;
	private int sector;
	private Mesh mesh;
	private Door linkedDoor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((linkedDoor == null) ? 0 : linkedDoor.hashCode());
		result = prime * result + ((mesh == null) ? 0 : mesh.hashCode());
		result = prime * result + sector;
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Door)) {
			return false;
		}
		Door other = (Door) obj;
		if (linkedDoor == null) {
			if (other.linkedDoor != null) {
				return false;
			}
		} else if (!linkedDoor.equals(other.linkedDoor)) {
			return false;
		}
		if (mesh == null) {
			if (other.mesh != null) {
				return false;
			}
		} else if (!mesh.equals(other.mesh)) {
			return false;
		}
		if (sector != other.sector) {
			return false;
		}
		return true;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	public Door(int sector) {
		this.sector = sector;
		open = false;
		mesh = null;
	}

	public void open() {
		openNonMutual();
		System.out.println("opening non-mutual");
		if (linkedDoor != null) {
			System.out.println("opening linked non-mutual");
			linkedDoor.openNonMutual();
		}

	}

	private void openNonMutual() {

		open = true;

		if (mesh != null)
			mesh.setVisibility(false);
	}

	public void close() {

		if (mesh != null)
			mesh.setVisibility(true);

		open = false;
	}

	/**
	 * @return the linkedDoor
	 */
	public Door getLinkedDoor() {
		return linkedDoor;
	}

	/**
	 * @param linkedDoor
	 *            the linkedDoor to set
	 */
	public void setLinkedDoor(Door linkedDoor) {
		this.linkedDoor = linkedDoor;
	}

	public void destroy() {

		mesh.destroy();
		linkedDoor.destroy();
	}
}