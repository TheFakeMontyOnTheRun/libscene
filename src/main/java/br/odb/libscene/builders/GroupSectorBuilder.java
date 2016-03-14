package br.odb.libscene.builders;

import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.CameraNode;
import br.odb.libscene.DecalNode;
import br.odb.libscene.GroupSector;
import br.odb.libscene.LightNode;
import br.odb.libscene.MeshNode;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libstrip.Material;
import br.odb.gameutils.Color;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public final static GroupSectorBuilder gsb = new GroupSectorBuilder();
	
	public String toXML(GroupSector gs) {

		StringBuilder sb = new StringBuilder();
		SpaceRegionBuilder srb = new SpaceRegionBuilder();
		sb.append( srb.toXML(gs));

		Material m;
		
		for ( Direction d : gs.shades.keySet() ) {
		
			m = gs.shades.get( d );
			
			sb.append( "<material dir='" + d.simpleName + "' " );
			
			if ( m.mainColor != null ) {
				sb.append( " color = '" + m.mainColor.getHTMLColor() + "' " );	
			}
			
			if ( m.texture != null ) {
				sb.append( " texture = '" + m.texture + "' " );	
			}

			sb.append( " />" );
		}

		for (SceneNode s : gs.getSons()) {

			if (s instanceof GroupSector) {
				sb.append("\n<group>");
				sb.append( gsb.toXML((GroupSector) s));
				sb.append("\n</group>");
			} else if ( s instanceof MeshNode ) {
				sb.append("\n<mesh>");
				sb.append(MeshNodeBuilder.msb.toXML(s));
				sb.append("\n</mesh>");
			} else if (s instanceof Sector) {
				sb.append("\n<sector>");
				sb.append(SectorBuilder.sb.toXML(s));
				sb.append("\n</sector>");
			} else if ( s instanceof LightNode ) {
				sb.append("\n<light>");
				sb.append(LightNodeBuilder.lnb.toXML(s));
				sb.append("\n</light>");
			} else if ( s instanceof CameraNode ) {
				sb.append("\n<camera>");
				sb.append(CameraNodeBuilder.cnb.toXML(s));
				sb.append("\n</camera>");
			} else if ( s instanceof DecalNode ) {
				sb.append("\n<decal>");
				sb.append(DecalNodeBuilder.cnb.toXML(s));
				sb.append("\n</decal>");
			}
		}

		return sb.toString();
	}

	final static HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();

	static {
		builders.put("group", new GroupSectorBuilder());
		builders.put("sector", new SectorBuilder());
		builders.put("light", new LightNodeBuilder());
		builders.put("camera", new CameraNodeBuilder());
		builders.put("decal", new CameraNodeBuilder());
		builders.put("mesh", new MeshNodeBuilder());
	}

	public SpaceRegion build(Node node) {

		SpaceRegion region = (SpaceRegion) super.build(node);
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
						
						SceneNode newNode = builder.build(fstNode);
						//XML files save their relative positions
						Vec3 posBefore = new Vec3( newNode.localPosition );
						masterSector.addChild( newNode );
						newNode.localPosition.set( posBefore );
						
					} else if ("material".equalsIgnoreCase(fstNode.getNodeName())) {
						readShade(masterSector, fstNode);
					}
				}
			}
		}

		return masterSector;
	}

	private void readShade(GroupSector gs, Node node) {
		Direction d = null;
		String direction = null;
		Color shade;
		String color = null;
		String texture = null;
		
		NamedNodeMap map = node.getAttributes();
		
		Node attrs;
		
		attrs = map.getNamedItem( "dir" );
		if ( attrs != null ) {
			direction = attrs.getNodeValue().trim();	
		}
		
		attrs = map.getNamedItem( "color" );
		if ( attrs != null ) {
			color = attrs.getNodeValue().trim();	
		}
				
		attrs = map.getNamedItem( "texture" );
		if ( attrs != null ) {
			texture = attrs.getNodeValue().trim();
		}

		if ( direction != null ) {
			d = Direction.getDirectionForSimpleName(direction);	
		}		

		if ( color == null ) {
			shade = new Color();
		} else {
			shade = Color.getColorFromHTMLColor( color );
		}

		Material m = Material.makeWithColorAndTexture( shade, texture );
		
		if (d != null) {
			gs.shades.put( d,  m );
		}
	}
}
