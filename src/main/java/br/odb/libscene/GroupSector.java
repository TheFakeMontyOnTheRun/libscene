package br.odb.libscene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.GeneralTriangleMesh;
import br.odb.libstrip.Material;
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
		
		if ( region instanceof GroupSector && ((GroupSector)region).material != null ) {
			material = ((GroupSector)region).material;
		}
	}
	
	public void removeChild( SceneNode child ) {
		
		child.localPosition.set( child.getAbsolutePosition() );		
		child.parent = null;
		
		sons.remove( child );		
	}
	

	public void addChild( SceneNode region ) {
		
		if ( sons.contains( region ) ) {
			return;
		}		
		
		if ( region.parent instanceof GroupSector ) {
			System.out.println( "removing " + region.id + " from " + region.parent.id );
			( ( GroupSector) region.parent ).removeChild( region );
		}

		region.localPosition.set( region.localPosition.sub( localPosition ) );
		region.parent = this;

        sons.add( region );
	}
	
	public SpaceRegion pick( Vec3 v ) {
		
		SpaceRegion contained;
		
		if ( isInside( v ) ) {
			
			for ( SceneNode sr : sons ) {
				
				if ( sr instanceof GroupSector && ( ( GroupSector )sr ).isInside(v) ) {
					
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
	
	public static GroupSector getConvexHull(int snapLevel, GeneralTriangleMesh mesh) {

		if (mesh.faces.size() < 1) {
			return new GroupSector(mesh.name);
		}
		
		GroupSector sector = new GroupSector(mesh.name );
		
		sector.material = mesh.faces.get( 0 ).material;

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

		
		//TOOOOO BIG! Must refactor
		for ( GeneralTriangle f : mesh.faces) {

			if (f.x0 < x0) {
				x0 = f.x0;
			}

			if (f.y0 < y0) {
				y0 = f.y0;
			}

			if (f.z0 < z0) {
				z0 = f.z0;
			}

			if (f.x0 > x1) {
				x1 = f.x0;
			}

			if (f.y0 > y1) {
				y1 = f.y0;
			}

			if (f.z0 > z1) {
				z1 = f.z0;
			}
			
			if (f.x1 < x0) {
				x0 = f.x1;
			}
			
			if (f.y1 < y0) {
				y0 = f.y1;
			}
			
			if (f.z1 < z0) {
				z0 = f.z1;
			}
			
			if (f.x1 > x1) {
				x1 = f.x1;
			}
			
			if (f.y1 > y1) {
				y1 = f.y1;
			}
			
			if (f.z1 > z1) {
				z1 = f.z1;
			}
			
			if (f.x2 < x0) {
				x0 = f.x2;
			}
			
			if (f.y2 < y0) {
				y0 = f.y2;
			}
			
			if (f.z2 < z0) {
				z0 = f.z2;
			}
			
			if (f.x2 > x1) {
				x1 = f.x2;
			}
			
			if (f.y2 > y1) {
				y1 = f.y2;
			}
			
			if (f.z2 > z1) {
				z1 = f.z2;
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
	
	public SceneNode getChild( String query ) {
		
		SceneNode descendant;
		
		for ( SceneNode child : sons ) {
			
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
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mesh == null) ? 0 : mesh.hashCode());
		result = prime * result + ((sons == null) ? 0 : sons.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupSector other = (GroupSector) obj;
		if (mesh == null) {
			if (other.mesh != null)
				return false;
		} else if (!mesh.equals(other.mesh))
			return false;
		if (sons == null) {
			if (other.sons != null)
				return false;
		} else if (!sons.equals(other.sons))
			return false;
		return true;
	}

	public Collection<SceneNode> getSons() {
		return sons;
	}
	
	public Material material;
	public final GeneralTriangleMesh mesh = new GeneralTriangleMesh( "_mesh" );
	public final List< SceneNode > sons = new ArrayList<>();
	
}
