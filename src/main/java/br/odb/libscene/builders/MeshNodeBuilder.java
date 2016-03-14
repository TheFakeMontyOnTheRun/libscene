package br.odb.libscene.builders;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.liboldfart.WavefrontMaterialLoader;
import br.odb.liboldfart.WavefrontOBJLoader;
import br.odb.libscene.CameraNode;
import br.odb.libscene.MeshNode;
import br.odb.libscene.SceneNode;
import br.odb.libstrip.TriangleMesh;
import br.odb.libstrip.Material;
import br.odb.libstrip.builders.GeneralTriangleFactory;

public class MeshNodeBuilder extends SceneNodeBuilder implements SpatialDivisionBuilder {
	public static final MeshNodeBuilder msb = new MeshNodeBuilder();

	@Override
	public String getTagName() {
		return "mesh";
	}

	@Override
	public Class getSerializedClass() {
		return CameraNode.class;
	}

	@Override
	public SceneNode build(Node node) {
		SceneNode sceneNode = super.build(node);
		TriangleMesh mesh = new TriangleMesh("_mesh");
		MeshNode meshNode = new MeshNode(sceneNode, mesh);
		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("geometry".equalsIgnoreCase(fstNode.getNodeName())) {
						
						WavefrontOBJLoader loader = new WavefrontOBJLoader( new GeneralTriangleFactory() );
						List<Material> materials = new ArrayList<>();
						
						for ( Material m : meshNode.materials ) {
							materials.add( m );	
						}
						
						List<TriangleMesh> meshes = loader.loadMeshes( new ByteArrayInputStream( fstNode.getTextContent().getBytes() ), materials );
						
						for ( TriangleMesh m : meshes ) {
							meshNode.mesh.faces.addAll( m.faces );	
						}
					}
					
					if ("material".equalsIgnoreCase(fstNode.getNodeName())) {
						WavefrontMaterialLoader matLoader = new WavefrontMaterialLoader();
						meshNode.materials.addAll( matLoader.parseMaterials( new ByteArrayInputStream( fstNode.getTextContent().getBytes() )));
					}					
				}
			}
		}

		return meshNode;
	}

	@Override
	public String toXML(SceneNode node) {
		StringBuilder sb = new StringBuilder();
		MeshNode meshNode = (MeshNode) node;
		sb.append(SceneNodeBuilder.snb.toXML(node));
//		sb.append( "<angle>" );
//		sb.append( camNode.angleXZ );
//		sb.append( "</angle>" );
		return sb.toString();
	}

}
