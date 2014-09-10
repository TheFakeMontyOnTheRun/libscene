package br.odb.libscene;

import br.odb.utils.math.Vec3;

public class SpaceRegion extends SceneNode {

	public final Vec3 size = new Vec3();
	public String description;

	public SpaceRegion( String id ) {
		super( id );
	}
	
	public SpaceRegion(SpaceRegion region ) {
		super( region );
		
		size.set( region.size );
		description = region.description;
	}
}
