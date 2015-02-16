package br.odb.libscene.builders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.SceneNode;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.math.Vec3;

public class SpaceRegionBuilder extends SceneNodeBuilder implements
		SpatialDivisionBuilder {

	public String toXML(SceneNode region) {
		StringBuilder sb = new StringBuilder();
		sb.append(SceneNodeBuilder.snb.toXML(region));

		sb.append("<size>\n");
		sb.append(Vec3Builder.toXML(((SpaceRegion) region).size));
		sb.append("</size>\n");

		return sb.toString();
	}

	public SceneNode build(Node node) {

		SceneNode sn = super.build(node);

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

					if (nodeName.equalsIgnoreCase("size")) {
						p1.set(builder.build(fstNode));
					}
				}
			} else {
				nodeName = null;
			}
		}

		SpaceRegion region = new SpaceRegion(sn.id);
		region.localPosition.set(sn.localPosition);
		region.size.set(p1);
		return region;
	}
}
