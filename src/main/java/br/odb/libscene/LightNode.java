package br.odb.libscene;

import java.io.Serializable;

import br.odb.gameutils.Color;

public class LightNode extends SceneNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2433654222823676793L;
	public float intensity;
	public final Color color = new Color( 255, 255, 255 ); 

	public LightNode(SceneNode other) {
		super(other);
		
		if ( other instanceof LightNode ) {			
			setFrom( (LightNode) other );
		}
	}

	
	public void setFrom( SceneNode other ) {
		
		super.setFrom(other);
		
		if ( other instanceof LightNode ) {
			
			this.color.set( ((LightNode)other).color );
			this.intensity = ( (LightNode)other ).intensity;	
		}
	}
	
	public LightNode(String id) {
		super(id);
	
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (color.hashCode());
		result = prime * result + Float.floatToIntBits(intensity);
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof LightNode)) {
			return false;
		}
		LightNode other = (LightNode) obj;
		
		if (!color.equals(other.color)) {
			return false;
		}
		if (Float.floatToIntBits(intensity) != Float
				.floatToIntBits(other.intensity)) {
			return false;
		}
		return true;
	}


	public LightNode(SceneNode other, String newId) {
		super(other, newId);
		
		if ( other instanceof LightNode ) {			
			setFrom( (LightNode) other );
		}
	}
}
