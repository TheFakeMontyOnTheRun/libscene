package br.odb.libscene;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.SpaceRegionBuilder;
import br.odb.utils.Color;
import br.odb.utils.Direction;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public static String toXML( GroupSector gs ) {
		
		String toReturn = "";
		toReturn += SpaceRegionBuilder.toXML( ( SpaceRegion )gs );
		
		for ( SpaceRegion s : gs.getSons() ) {
			
			
			if ( s instanceof GroupSector ) {
				toReturn += "\n<group>";
				
				toReturn += "\n<mesh>";
				toReturn += ( (GroupSector) s ).mesh;
				toReturn += "\n</mesh>";
				
				
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


	private String direction;
	private String colour;
	
	
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
						masterSector.addChild(builder.build(fstNode));
					}
				}
			}
		}

		return masterSector;
	}


	
}
