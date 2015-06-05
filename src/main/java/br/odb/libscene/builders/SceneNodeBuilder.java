package br.odb.libscene.builders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.SceneNode;
import br.odb.utils.math.Vec3;

public class SceneNodeBuilder implements SpatialDivisionBuilder {

	public static final SceneNodeBuilder snb = new SceneNodeBuilder();

	@Override
	public String getTagName() {
		return "node";
	}
	
	@Override
	public SceneNode build(Node node) {
		
		String id = "";
		Vec3 pos = new Vec3();

		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("id".equalsIgnoreCase(fstNode.getNodeName())) {
						id = fstNode.getTextContent();
					} else if ( "position".equalsIgnoreCase(fstNode.getNodeName()) ) {
						pos.set( Vec3Builder.v3b.build( fstNode ) );
					}
				}
			}
		}
		
		SceneNode sceneNode = new SceneNode( id );
		sceneNode.localPosition.set( pos );
		return sceneNode;

	}

	@Override
	public String toXML(SceneNode node) {
		
		StringBuilder sb = new StringBuilder();
		sb.append( "<id>" + node.id + "</id>\n" );
		sb.append(  "<position>\n" );
		sb.append(  Vec3Builder.toXML( node.localPosition) );
		sb.append(  "</position>\n" );		
		
		return sb.toString();
	}

	@Override
	public Class getSerializedClass() {
		return SceneNode.class;
	}
}
