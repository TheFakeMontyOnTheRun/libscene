package br.odb.libscene;

import br.odb.utils.Direction;

public class DecalNode extends SceneNode {

	public final Direction face; 
	public final String decalName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5045527531145794254L;

	public DecalNode( Direction d, String decalName ) {
		super( d.simpleName + "_" + decalName );
		
		this.face = d;
		this.decalName = decalName;
	}

	public DecalNode(String id, Direction d, String decalName ) {

		super(id);
		
		this.face = d;
		this.decalName = decalName;

	}

	public DecalNode(SceneNode sceneNode, Direction dir, String decalName) {
		super( sceneNode );
		
		this.decalName = decalName;
		this.face = dir;
	}
}
