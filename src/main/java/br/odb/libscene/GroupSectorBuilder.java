package br.odb.libscene;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public static String toXML(GroupSector gs) {
		StringBuilder sb = new StringBuilder();
		
		sb.append( SpaceRegionBuilder.toXML((SpaceRegion) gs) );

		for (SpaceRegion s : gs.getSons()) {

			if (s instanceof GroupSector) {
				sb.append( "\n<group>" );

				if (((GroupSector) s).mesh.faces.size() > 0) {

					sb.append( "\n<mesh>" );
					sb.append( ((GroupSector) s).mesh );
					sb.append( "\n</mesh>" );
				}

				sb.append( GroupSectorBuilder.toXML((GroupSector) s) );
				sb.append( "\n</group>" );
			} else if (s instanceof Sector) {
				sb.append( "\n<sector>" );
				sb.append( SectorBuilder.toXML( (Sector) s) );
				sb.append( "\n</sector>" );
			}
		}

		return sb.toString();
	}

	
	final static HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();
	
	static {
		builders.put("group", new GroupSectorBuilder());
		builders.put("sector", new SectorBuilder());
	}
	
	public SpaceRegion build(Node node) {

		SpaceRegion region = super.build(node);
		GroupSector masterSector = new GroupSector(region);

		
		SpatialDivisionBuilder builder;

		NodeList nodeLst;

		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					builder = builders.get(fstNode.getNodeName()); 
					
					if ( builder != null ) {
						masterSector.addChild(builder.build(fstNode));
					}
				}
			}
		}

		return masterSector;
	}

}
