package br.odb.libscene.builder;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public static String toXML( GroupSector gs ) {
		
		String toReturn = "";
		toReturn += SpaceRegionBuilder.toXML( ( SpaceRegion )gs );
		
		for ( SpaceRegion s : gs.sons ) {
			
			
			if ( s instanceof GroupSector ) {
				toReturn += "\n<group>";
				toReturn += GroupSectorBuilder.toXML( (GroupSector) s );
				toReturn += "\n</group>";
			} else {
				toReturn += "\n<sector>";
				toReturn += SectorBuilder.toXML( s );
				toReturn += "\n</sector>";
			}
		}
		
		return toReturn;
	}
	
	
	public SpaceRegion build(Node node) {

		SpaceRegion region = super.build(node);
		GroupSector masterSector = new GroupSector(region);

		HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();
		builders.put("group", new GroupSectorBuilder());
		builders.put("sector", new SectorBuilder());
		SpatialDivisionBuilder builder;
		
		
		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE ) {

					if ( builders.containsKey( fstNode.getNodeName() ) ) {
						
						builder = builders.get(fstNode.getNodeName());
						masterSector.sons.add(builder.build(fstNode));
					}
				}
			}
		}

		return masterSector;
	}
}
