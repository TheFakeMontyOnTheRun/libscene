package br.odb.libscene.builders;

import org.w3c.dom.Node;

import br.odb.gameutils.math.Vec3;

public class Vec3Builder {
	
	public static final Vec3Builder v3b = new Vec3Builder();
	
	public static String toXML( Vec3 vec) {
		StringBuilder sb = new StringBuilder();
		
		sb.append( vec.x + "\n" );
		sb.append( vec.y + "\n" );
		sb.append( vec.z + "\n" );
		
		return sb.toString();
	}

	public Vec3 build(Node node) {
		String text = node.getTextContent();
		
		text = text.trim();
		
		return build( text );
	}
		
	public Vec3 build( String text ) {
		
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
