package br.odb.libscene;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libstrip.Material;
import br.odb.utils.Color;
import br.odb.utils.Direction;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public static String toXML(GroupSector gs) {

		Color c;
		StringBuilder sb = new StringBuilder();

		sb.append(SpaceRegionBuilder.toXML((SpaceRegion) gs));

		if ( gs.material != null ) {

			c = gs.material.mainColor;

			if (c != null) {
				sb.append("<material>\n");

				sb.append("<color>\n");
				sb.append(c.getHTMLColor());
				sb.append("\n</color>\n");

				sb.append("</material>\n");
			}
		}

		for (SpaceRegion s : gs.getSons()) {

			if (s instanceof GroupSector) {
				sb.append("\n<group>");

				if (((GroupSector) s).mesh.faces.size() > 0) {

					sb.append("\n<mesh>");
					sb.append(((GroupSector) s).mesh);
					sb.append("\n</mesh>");
				}

				sb.append(GroupSectorBuilder.toXML((GroupSector) s));
				sb.append("\n</group>");
			} else if (s instanceof Sector) {
				sb.append("\n<sector>");
				sb.append(SectorBuilder.toXML((Sector) s));
				sb.append("\n</sector>");
			}
		}

		return sb.toString();
	}

	private void readMaterial(GroupSector region, Node node) {
		NodeList nodeLst;
		nodeLst = node.getChildNodes();

		String colour = "";

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					if ("color".equalsIgnoreCase(fstNode.getNodeName())) {
						colour = fstNode.getTextContent().trim();
					}
				}
			}
		}

		Color c = Color.getColorFromHTMLColor(colour);
		Material material = new Material(c, null, null, null);
		region.material = material;
	}

	final static HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();

	static {
		builders.put("group", new GroupSectorBuilder());
		builders.put("sector", new SectorBuilder());
	}

	public SpaceRegion build(Node node) {

		SpaceRegion region = super.build(node);
		GroupSector masterSector = new GroupSector(region);

		SpatialDivisionBuilder builder;

		NodeList nodeLst;

		nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode != null) {

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					builder = builders.get(fstNode.getNodeName());

					if (builder != null) {
						masterSector.addChild(builder.build(fstNode));
					} else if ("material".equalsIgnoreCase(fstNode.getNodeName())) {
						readMaterial(masterSector, fstNode);
					}
				}
			}
		}

		return masterSector;
	}

}
