package br.odb.libscene.builders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.CameraNode;
import br.odb.libscene.SceneNode;

public class CameraNodeBuilder extends SceneNodeBuilder implements SpatialDivisionBuilder {

	public static final CameraNodeBuilder cnb = new CameraNodeBuilder();
	
	@Override
	public String getTagName() {
		return "camera";
	}
	
	@Override
	public Class getSerializedClass() {
		return CameraNode.class;
	}	
	
	@Override
	public SceneNode build(Node node) {
		SceneNode sceneNode = super.build(node);
		CameraNode cam = new CameraNode( sceneNode );

		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("angle".equalsIgnoreCase(fstNode.getNodeName())) {
						cam.angleXZ = Float.parseFloat( fstNode.getTextContent() );
					}
				}
			}
		}

		return cam;
	}

	@Override
	public String toXML(SceneNode node) {
		StringBuilder sb = new StringBuilder();
		CameraNode camNode = (CameraNode) node;
		sb.append( SceneNodeBuilder.snb.toXML( node ) );
		sb.append( "<angle>" );
		sb.append( camNode.angleXZ );
		sb.append( "</angle>" );
		return sb.toString();
	}
}
