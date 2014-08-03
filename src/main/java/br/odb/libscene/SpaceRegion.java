package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SpaceRegion {
	
	public SpaceRegion() {
		
	}

	public SpaceRegion(SpaceRegion region) {
		p0.set( region.p0 );
		p1.set( region.p1 );
		description = region.description;
	}
	
	public final Vec3 p0 = new Vec3();
	public final Vec3 p1 = new Vec3();
	public String description;
}
