package br.odb.libscene.builders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.DecalNode;
import br.odb.libscene.SceneNode;
import br.odb.utils.Direction;

public class DecalNodeBuilder extends SceneNodeBuilder implements
		SpatialDivisionBuilder {

	public static final DecalNodeBuilder cnb = new DecalNodeBuilder();

	@Override
	public Class getSerializedClass() {
		return DecalNode.class;
	}
	
	@Override
	public String getTagName() {
		return "decal";
	}
	
	@Override
	public SceneNode build(Node node) {
		SceneNode sceneNode = super.build(node);

		Direction dirName = null;
		String decalName = null;

		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("face".equalsIgnoreCase(fstNode.getNodeName())) {
						dirName = Direction.valueOf(fstNode.getTextContent());
					} else if ("res".equalsIgnoreCase(fstNode.getNodeName())) {
						decalName = fstNode.getTextContent();
					}
				}
			}
		}

		return new DecalNode(sceneNode, dirName, decalName);
	}

	@Override
	public String toXML(SceneNode node) {
		StringBuilder sb = new StringBuilder();
		DecalNode decalNode = (DecalNode) node;
		sb.append(SceneNodeBuilder.snb.toXML(node));

		sb.append("<face>");
		sb.append(decalNode.face.simpleName);
		sb.append("</face>");

		sb.append("<res>");
		sb.append(decalNode.decalName);
		sb.append("</res>");

		return sb.toString();
	}
}
