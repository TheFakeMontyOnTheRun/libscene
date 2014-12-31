package br.odb.libscene;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {

	public static String toXML(SpaceRegion region) {
		StringBuilder sb = new StringBuilder();
		
		Color c;
		sb.append( "<id>\n" );
		sb.append(  region.id );
		sb.append(  "</id>\n" );

		if (region.description != null && region.description.trim().length() > 0 ) {
			sb.append(  "<description>\n" );
			sb.append(  region.description );
			sb.append(  "</description>\n" );
		}

		for (Direction d : region.colorForDirection.keySet()) {

			c = region.colorForDirection.get(d);
			
			if ( c != null ) {
				sb.append(  "<material>\n" );

				sb.append(  "<direction>\n" );
				sb.append(  d.simpleName );
				sb.append(  "</direction>\n" );

				sb.append(  "<color>\n" );
				sb.append(  c.getHTMLColor() );
				sb.append(  "</color>\n" );

				sb.append(  "</material>\n" );				
			}
		}

		sb.append(  "<position>\n" );
		sb.append(  Vec3Builder.toXML(region.localPosition) );
		sb.append(  "</position>\n" );

		sb.append(  "<size>\n" );
		sb.append(  Vec3Builder.toXML(region.size) );
		sb.append(  "</size>\n" );

		return sb.toString();
	}

	private void readMaterial( SpaceRegion region, Node node) {
		NodeList nodeLst;
		nodeLst = node.getChildNodes();
		
		String direction = "";
		String colour = "";
		
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE ) {
					
					if ( "direction".equalsIgnoreCase( fstNode.getNodeName() ) ) {
						direction = fstNode.getTextContent().trim();
					} else if ( "color".equalsIgnoreCase( fstNode.getNodeName() ) ) {
						colour = fstNode.getTextContent().trim();
					}
				}
			}
		}
		
		region.colorForDirection.put( Direction.getDirectionForSimpleName( direction ), Color.getColorFromHTMLColor( colour ) );
	}
	
	public SpaceRegion build(Node node) {

		String description = "";
		String id = "";
		Vec3 p0 = new Vec3();
		Vec3 p1 = new Vec3();

		Vec3Builder builder = new Vec3Builder();

		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if (fstNode.getNodeName().equalsIgnoreCase("size")) {
						p1.set(builder.build(fstNode));
					} else if (fstNode.getNodeName().equalsIgnoreCase("id")) {
						id = fstNode.getTextContent().trim();
					} else if (fstNode.getNodeName().equalsIgnoreCase(
							"position")) {
						p0.set(builder.build(fstNode));
					} else if (fstNode.getNodeName().equalsIgnoreCase(
							"description")) {
						description = fstNode.getTextContent().trim();
					}
				}
			}
		}

		SpaceRegion region = new SpaceRegion(id);
		
		
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ( "material".equalsIgnoreCase( fstNode.getNodeName() ) ) {
						readMaterial( region, fstNode );
					}
				}
			}
		}
		
		region.localPosition.set(p0);
		region.size.set(p1);
		region.description = description;
		return region;
	}
}
