package br.odb.libscene.builder;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public SpaceRegion build(Node node) {

		SpaceRegion region = super.build(node);
		GroupSector masterSector = new GroupSector(region);

		HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();
		builders.put("place", new GroupSectorBuilder());
		builders.put("location", new SectorBuilder());
		SpatialDivisionBuilder builder;
		
		
		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE ) {

					if ( builders.containsKey( fstNode.getNodeName() ) ) {
						
						builder = builders.get(fstNode.getNodeName());
						masterSector.sons.add(builder.build(node));
					}
				}
			}
		}

		return masterSector;
	}
}
