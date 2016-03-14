package br.odb.libscene.builders;

import br.odb.liboldfart.SimpleWavefrontOBJLoader;
import br.odb.liboldfart.WavefrontMaterialLoader;
import br.odb.libscene.CameraNode;
import br.odb.libscene.MeshNode;
import br.odb.libscene.SceneNode;
import br.odb.libstrip.GeneralTriangle;
import br.odb.libstrip.TriangleMesh;
import br.odb.libstrip.builders.GeneralTriangleFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

		String geometryFilename = null;
		String materialFilename = null;

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			if (fstNode != null) {
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					if ("geometry".equalsIgnoreCase(fstNode.getNodeName())) {
						geometryFilename = fstNode.getTextContent();
					} else if ("material".equalsIgnoreCase(fstNode.getNodeName())) {
						materialFilename = fstNode.getTextContent();
					}
				}
			}
		}

		SimpleWavefrontOBJLoader loader = new SimpleWavefrontOBJLoader(new GeneralTriangleFactory());
		WavefrontMaterialLoader matLoader = new WavefrontMaterialLoader();
		List<TriangleMesh> meshes = new ArrayList<>();

		if ( geometryFilename != null ) {
			try {
				meshes.addAll( loader.loadMeshes(new FileInputStream( geometryFilename ), ( materialFilename != null ) ?
						matLoader.parseMaterials( new FileInputStream( materialFilename ) ) : null
				));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		for (TriangleMesh m : meshes) {
			meshNode.mesh.faces.addAll(m.faces);
			for (GeneralTriangle gt : m.faces ) {
				meshNode.materials.add( gt.material );
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
