package br.odb.libscene.builders;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.Direction;

public class SectorBuilder extends SpaceRegionBuilder {

	@Override
	public SpaceRegion build(Node node) {

		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		SpaceRegion region = (SpaceRegion) super.build(node);

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
		
		Direction d = null;
		String direction = null;
		
		String link = null;
		
		NamedNodeMap map = node.getAttributes();
		
		Node attrs;
		
		attrs = map.getNamedItem( "dir" );
		direction = attrs.getNodeValue().trim();
		attrs = map.getNamedItem( "link" );
		link = attrs.getNodeValue().trim();		

		if ( direction != null ) {
			d = Direction.getDirectionForSimpleName(direction);	
		}		

		if (d != null) {
			if ( link == null ) {
				link = sector.id;
			}
			
			sector.links[d.ordinal()] = link;
		}
	}

	public static String toXML(Sector s) {

		StringBuilder sb = new StringBuilder();
		SpaceRegionBuilder srb = new SpaceRegionBuilder();
		
		sb.append( srb.toXML((SpaceRegion) s));

		Direction[] values = Direction.values();

		int shouldBeSix = values.length;

		for (int c = 0; c < shouldBeSix; ++c) {

			if (s.links[c] == null) {
				continue;
			}

			sb.append("<connection dir ='" );
			sb.append(values[c].simpleName);
			sb.append( "' link ='" );
			sb.append( s.links[ c ] );
			sb.append( "'/>\n" );
		}

		return sb.toString();
	}
}
