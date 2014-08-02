package br.odb.libscene.builder;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {

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
					}
				}
			}
		}
		return region;
	}
}
