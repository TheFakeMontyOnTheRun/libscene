package br.odb.libscene;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import br.odb.libstrip.Material;
import br.odb.libstrip.Mesh;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class GroupSector extends SpaceRegion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4269836844728197301L;
	
	GroupSector() {
		super();
	}
	
	public GroupSector( String id ) {
		super( id );		 
	}
	
	public GroupSector(SpaceRegion region ) {
		super( region );
		
		if ( region instanceof GroupSector ) {
			
			for ( Direction d : Direction.values() ) {
				if ( ((GroupSector)region).materials.get( d ) != null ) {
					materials.put( d, ((GroupSector)region).materials.get( d ) );
				}
			}
		}
	}
	
	public void removeChild( SpaceRegion child ) {
		child.localPosition.set( getAbsolutePosition() );
		sons.remove( child );
		child.parent = null;
	}
	

	public void addChild( SpaceRegion region ) {
		
		if ( region.parent instanceof GroupSector ) {
			( ( GroupSector) region.parent ).removeChild( region );
		}
		
		sons.add( region );
		
		region.localPosition.set( region.localPosition.sub( localPosition ) );
		region.parent = this;
	}
	
	public SpaceRegion pick( Vec3 v ) {
		
		SpaceRegion contained;
		
		if ( isInside( v ) ) {
			
			for ( SpaceRegion sr : sons ) {
				
				if ( sr.isInside(v) && sr instanceof GroupSector ) {
					
					contained = ((GroupSector)sr).pick( v );		
					
					if ( contained != null ) {
						
						return contained;
					}
				}
			}	 			
			
			return this;
		}
		
		return null;
	}
	
	public static GroupSector getConvexHull(int snapLevel, Mesh mesh) {

		GroupSector sector = new GroupSector(mesh.name);

		if (mesh.material != null) {

			for (Direction d : Direction.values()) {
				sector.materials.put(d, mesh.material );
				System.out
						.println("d: " + d + " m: " + mesh.material );
			}
		} else {
			System.out.println("Sector has no material for it's mesh");
		}

		if (mesh.points.size() < 1) {

			return sector;
		}

		// find the center point;
		Vec3 center = mesh.getCenter();

		// make the box stay at the center;

		sector.localPosition.x = (center.x);
		sector.localPosition.y = (center.y);
		sector.localPosition.z = (center.z);
		sector.size.x = (center.x);
		sector.size.y = (center.y);
		sector.size.z = (center.z);

		float x0 = center.x;
		float y0 = center.y;
		float z0 = center.z;
		float x1 = center.x;
		float y1 = center.y;
		float z1 = center.z;

		for (Vec3 p : mesh.points) {

			if (p.x < x0) {
				x0 = p.x;
			}

			if (p.y < y0) {
				y0 = p.y;
			}

			if (p.z < z0) {
				z0 = p.z;
			}

			if (p.x > x1) {
				x1 = p.x;
			}

			if (p.y > y1) {
				y1 = p.y;
			}

			if (p.z > z1) {
				z1 = p.z;
			}
		}

		sector.localPosition.x = x0;
		sector.localPosition.y = y0;
		sector.localPosition.z = z0;

		sector.size.x = x1 - x0;
		sector.size.y = y1 - y0;
		sector.size.z = z1 - z0;

		if (snapLevel > 0) {
			sector.localPosition.x = Math.round(sector.localPosition.x);
			sector.localPosition.y = Math.round(sector.localPosition.y);
			sector.localPosition.z = Math.round(sector.localPosition.z);
			sector.size.x = Math.round(sector.size.x);
			sector.size.y = Math.round(sector.size.y);
			sector.size.z = Math.round(sector.size.z);
		}

		return sector;
	}	
	
	public SpaceRegion getChild( String query ) {
		
		SpaceRegion descendant;
		
		for ( SpaceRegion child : sons ) {
			
			if ( child.id.equals( query ) ) {
				return child;
			}
			
			if ( child instanceof GroupSector ) {
				descendant = (( GroupSector) child ).getChild( query );
				
				if ( descendant != null ) {
					return descendant;
				}
			}
		}
		
		if ( id.equals( query  ) ) {
			return this;
		}
		
		return null;
	}
	
	
	
	public Set< SpaceRegion > getSons() {
		return sons;
	}
	
	public final Mesh mesh = new Mesh( "_mesh" );
	public final HashMap< Direction, Material > materials = new HashMap< Direction, Material >();
	public final Set< SpaceRegion > sons = new HashSet< SpaceRegion >();
	
}
