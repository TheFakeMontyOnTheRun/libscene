package br.odb.libscene;

public class World extends Scene {
	
	public World(GroupSector built) {
		masterSector = built;
	}
	
	public World() {
		masterSector = new GroupSector();
	}

	public final GroupSector masterSector;
}
