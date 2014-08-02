package br.odb.libscene.builder;

import org.w3c.dom.Node;

import br.odb.utils.math.Vec3;

public class Vec3Builder {

	public Vec3 build(Node node) {
		String text = node.getTextContent();
		
		text = text.trim();
		
		
		for ( String s : new String[] { "\n", "\t", "\r" } ) {
			text = text.replace( s, " " );
		}
		
		String textBefore = null;
		
		while ( !text.equals( textBefore ) ) {
			textBefore = new String( text );
			text = text.replace( "  ", " " );
		}

		String[] tokens = text.split( " " );
		
		Vec3 toReturn = new Vec3();
		
		toReturn.x = Float.parseFloat( tokens[ 0 ] );
		toReturn.y = Float.parseFloat( tokens[ 1 ] );
		toReturn.z = Float.parseFloat( tokens[ 2 ] );
		
		return toReturn;
	}

}
