package br.odb.libscene;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {

	public static String toXML(SpaceRegion region) {
		String toReturn = "";
		Color c;
		toReturn += "<id>\n";
		toReturn += region.id;
		toReturn += "</id>\n";

		if (region.description != null) {
			toReturn += "<description>\n";
			toReturn += region.description;
			toReturn += "</description>\n";
		}

		for (Direction d : region.colorForDirection.keySet()) {

			c = region.colorForDirection.get(d);

			toReturn += "<material>\n";

			toReturn += "<direction>\n";
			toReturn += d.simpleName;
			toReturn += "</direction>\n";

			toReturn += "<color>\n";
			toReturn += c.getHTMLColor();
			toReturn += "</color>\n";

			toReturn += "</material>\n";
		}

		toReturn += "<position>\n";
		toReturn += Vec3Builder.toXML(region.position);
		toReturn += "</position>\n";

		toReturn += "<size>\n";
		toReturn += Vec3Builder.toXML(region.size);
		toReturn += "</size>\n";

		return toReturn;
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
		region.position.set(p0);
		region.size.set(p1);
		region.description = description;
		return region;
	}
}
