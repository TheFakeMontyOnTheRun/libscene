package br.odb.libscene.builder;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {

	public static String toXML( SpaceRegion region ) {
		String toReturn = "";

		if ( region.description != null ) {
			toReturn += "<description>\n";
			toReturn += region.description;
			toReturn += "</description>\n";
		}
		
		toReturn += "<position>\n";
		toReturn += Vec3Builder.toXML( region.p0 );
		toReturn += "</position>\n";
		
		toReturn += "<size>\n";
		toReturn += Vec3Builder.toXML( region.p1 );
		toReturn += "</size>\n";
		
		return toReturn;
	}
	
	public SpaceRegion build( Node node ) {

		SpaceRegion region = new SpaceRegion();

		Vec3Builder builder = new Vec3Builder();

		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE ) {

					
					if (fstNode.getNodeName().equalsIgnoreCase("size")) {
						region.p1.set( builder.build( fstNode ) );
					} else if (fstNode.getNodeName().equalsIgnoreCase("position")) {
						region.p0.set( builder.build( fstNode ) );
					} else if (fstNode.getNodeName().equalsIgnoreCase("description")) {
						region.description = fstNode.getTextContent().trim();
					}

				}
			}
		}
		return region;
	}
}
