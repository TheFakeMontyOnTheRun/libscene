package br.odb.libscene;

import java.util.HashSet;
import java.util.Set;

import br.odb.libstrip.Material;
import br.odb.libstrip.TriangleMesh;

public class MeshNode extends SceneNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7809743031900918271L;
	public final TriangleMesh mesh;
	public Set<Material> materials = new HashSet< Material >();

	public MeshNode(SceneNode other, TriangleMesh mesh ) {
		super(other);
		this.mesh = mesh;
	}

	public MeshNode(String id, TriangleMesh mesh) {
		super(id);
		this.mesh = mesh;
	}

	public MeshNode(SceneNode other, String newId) {
		super(other, newId);
		if ( other instanceof MeshNode ) {
			this.mesh = ((MeshNode)other).mesh;
		} else {
			this.mesh = new TriangleMesh( newId + "_mesh" );
		}
	}
}
