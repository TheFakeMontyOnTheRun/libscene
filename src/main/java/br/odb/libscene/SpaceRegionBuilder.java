package br.odb.libscene;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {

	public static String toXML(SpaceRegion region) {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "<id>\n" );
		sb.append(  region.id );
		sb.append(  "</id>\n" );

		if (region.description != null && region.description.trim().length() > 0 ) {
			sb.append(  "<description>\n" );
			sb.append(  region.description );
			sb.append(  "</description>\n" );
		}

		sb.append(  "<position>\n" );
		sb.append(  Vec3Builder.toXML(region.localPosition) );
		sb.append(  "</position>\n" );

		sb.append(  "<size>\n" );
		sb.append(  Vec3Builder.toXML(region.size) );
		sb.append(  "</size>\n" );

		return sb.toString();
	}


	
	public SpaceRegion build(Node node) {

		String description = "";
		String id = "";
		Vec3 p0 = new Vec3();
		Vec3 p1 = new Vec3();

		Vec3Builder builder = new Vec3Builder();

		NodeList nodeLst;
		nodeLst = node.getChildNodes();
		String nodeName;
		
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode != null) {

				nodeName = fstNode.getNodeName();
				
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ( nodeName.equalsIgnoreCase("size")) {
						p1.set(builder.build(fstNode));
					} else if ( nodeName.equalsIgnoreCase("id")) {
						id = fstNode.getTextContent().trim();
					} else if ( nodeName.equalsIgnoreCase(
							"position")) {
						p0.set(builder.build(fstNode));
					} else if ( nodeName.equalsIgnoreCase(
							"description")) {
						description = fstNode.getTextContent().trim();
					}
				}
			} else {
				nodeName = null;
			}
		}
		
		SpaceRegion region = new SpaceRegion(id);

		
		region.localPosition.set(p0);
		region.size.set(p1);
		region.description = description;
		return region;
	}
}
