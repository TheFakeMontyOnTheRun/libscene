package br.odb.libscene;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.utils.Color;
import br.odb.utils.Direction;

public class SectorBuilder extends SpaceRegionBuilder {

	@Override
	public SpaceRegion build(Node node) {

		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		SpaceRegion region = super.build(node);

		Sector sector = new Sector(region);

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					if ("connection".equalsIgnoreCase(fstNode.getNodeName())) {
						readLink(sector, fstNode);
					}
				}
			}
		}

		return sector;
	}

	private void readLink(Sector sector, Node node) {
//		NodeList nodeLst;
//		nodeLst = node.getChildNodes();
//
		String direction = "";
//		String colour = "";

//		for (int s = 0; s < nodeLst.getLength(); s++) {
//
//			Node fstNode = nodeLst.item(s);
//
//			if (fstNode != null) {
//
//				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					// if ( "direction".equalsIgnoreCase( fstNode.getNodeName()
					// ) ) {
					 direction = node.getTextContent().trim();
					// } else if ( "sector".equalsIgnoreCase(
					// fstNode.getNodeName() ) ) {
					// colour = fstNode.getTextContent().trim();
					// }
//				}
//			}
//		}
		
		System.out.println( "connecting " + direction + " for " + sector.id );

		sector.connection.put( Direction.getDirectionForSimpleName( direction ), sector );
		// region.colorForDirection.put( Direction.getDirectionForSimpleName(
		// direction ), Color.getColorFromHTMLColor( colour ) );

	}

	public static String toXML(Sector s) {
		
		StringBuilder sb = new StringBuilder();


		sb.append( SpaceRegionBuilder.toXML((SpaceRegion) s) );

		// Sector s2;

		for (Direction d : s.connection.keySet()) {

			
			// s2 = s.connection.get(d);

			// if (s2 != null) {
			sb.append( "<connection>\n" );

//			sb.append( "<direction>\n" );
			sb.append( d.simpleName );
//			sb.append( "</direction>\n" );

			// toReturn += "<sector>\n";
			// toReturn += s.id;
			// toReturn += "</sector>\n";

			sb.append( "</connection>\n " );
			// }
		}

		return sb.toString();
	}
}
