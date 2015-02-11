package br.odb.libscene;

public class LightNode extends SceneNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2433654222823676793L;
	public float intensity;

	public LightNode(SceneNode other) {
		super(other);
		
		if ( other instanceof LightNode ) {			
			setFrom( (LightNode) other );
		}
	}

	
	public void setFrom( LightNode other ) {
		this.intensity = ( (LightNode)other ).intensity;	
	}
	
	public LightNode(String id) {
		super(id);
	
	}

	public LightNode(SceneNode other, String newId) {
		super(other, newId);
		
		if ( other instanceof LightNode ) {			
			setFrom( (LightNode) other );
		}
	}
}
