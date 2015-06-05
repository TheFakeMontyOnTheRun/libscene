package br.odb.libscene.builders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.LightNode;
import br.odb.libscene.SceneNode;

public class LightNodeBuilder extends SceneNodeBuilder implements SpatialDivisionBuilder {

	public static final LightNodeBuilder lnb = new LightNodeBuilder();

	@Override
	public Class getSerializedClass() {
		return LightNode.class;
	}
	
	@Override
	public String getTagName() {
		return "light";
	}
	
	@Override
	public SceneNode build(Node node) {
		SceneNode sceneNode = super.build(node);
		LightNode light = new LightNode( sceneNode );
		
		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("intensity".equalsIgnoreCase(fstNode.getNodeName())) {
						light.intensity = Float.parseFloat( fstNode.getTextContent() );
					}
				}
			}
		}
		
		return light;
	}

	@Override
	public String toXML(SceneNode node) {
		StringBuilder sb = new StringBuilder();
		LightNode litNode = (LightNode) node;
		sb.append( super.toXML( node ) );
		sb.append( "<intensity>" );
		sb.append( litNode.intensity );
		sb.append( "</intensity>" );
		return sb.toString();
	}
}
