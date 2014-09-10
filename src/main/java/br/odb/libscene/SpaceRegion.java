package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SpaceRegion {
	
	public SpaceRegion( String id ) {
		this.id = id;
	}

	public SpaceRegion(SpaceRegion region, String id ) {
		this.id = id;
		position.set( region.position );
		size.set( region.size );
		description = region.description;
	}
	
	public final String id;
	public final Vec3 position = new Vec3();
	public final Vec3 size = new Vec3();
	public String description;
}
