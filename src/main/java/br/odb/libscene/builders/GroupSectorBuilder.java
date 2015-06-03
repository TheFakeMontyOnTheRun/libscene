package br.odb.libscene.builders;

import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.CameraNode;
import br.odb.libscene.DecalNode;
import br.odb.libscene.GroupSector;
import br.odb.libscene.LightNode;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libstrip.Material;
import br.odb.utils.Color;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public class GroupSectorBuilder extends SpaceRegionBuilder {

	public final static GroupSectorBuilder gsb = new GroupSectorBuilder();
	
	public String toXML(GroupSector gs) {

		Color c;
		StringBuilder sb = new StringBuilder();
		SpaceRegionBuilder srb = new SpaceRegionBuilder();
		sb.append( srb.toXML((SpaceRegion) gs));

		Material m;
		
		if ( gs.material != null ) {

			c = gs.material.mainColor;

			if (c != null) {
				sb.append("<defaultmaterial \n");

				m = gs.material;
				
				if ( m.mainColor != null ) {
					sb.append( " color = '" + m.mainColor.getHTMLColor() + "' " );	
				}
				
				if ( m.texture != null ) {
					sb.append( " texture = '" + m.texture + "' " );	
				}
				
				if ( m.vertexShader != null ) {
					sb.append( " vertshader = '" + m.vertexShader + "' " );	
				}
				
				if ( m.fragmentShader != null ) {
					sb.append( " fragshader = '" + m.fragmentShader + "' " );
				}
				
				sb.append( " />" );
			}
		}
		
		
		
		for ( Direction d : gs.shades.keySet() ) {
		
			m = gs.shades.get( d );
			
			sb.append( "<material dir='" + d.simpleName + "' " );
			
			if ( m.mainColor != null ) {
				sb.append( " color = '" + m.mainColor.getHTMLColor() + "' " );	
			}
			
			if ( m.texture != null ) {
				sb.append( " texture = '" + m.texture + "' " );	
			}
			
			if ( m.vertexShader != null ) {
				sb.append( " vertshader = '" + m.vertexShader + "' " );	
			}
			
			if ( m.fragmentShader != null ) {
				sb.append( " fragshader = '" + m.fragmentShader + "' " );
			}
			
			sb.append( " />" );
		}

		for (SceneNode s : gs.getSons()) {

			if (s instanceof GroupSector) {
				sb.append("\n<group>");

				if (((GroupSector) s).mesh.faces.size() > 0) {

					sb.append("\n<mesh>");
					sb.append(((GroupSector) s).mesh);
					sb.append("\n</mesh>");
				}
				
				sb.append( gsb.toXML((GroupSector) s));
				sb.append("\n</group>");
			} else if (s instanceof Sector) {
				sb.append("\n<sector>");
				sb.append(SectorBuilder.toXML((Sector) s));
				sb.append("\n</sector>");
			} else if ( s instanceof LightNode ) {
				sb.append("\n<light>");
				sb.append(LightNodeBuilder.lnb.toXML((LightNode) s));
				sb.append("\n</light>");
			} else if ( s instanceof CameraNode ) {
				sb.append("\n<camera>");
				sb.append(CameraNodeBuilder.cnb.toXML((CameraNode) s));
				sb.append("\n</camera>");
			} else if ( s instanceof DecalNode ) {
				sb.append("\n<decal>");
				sb.append(DecalNodeBuilder.cnb.toXML((DecalNode) s));
				sb.append("\n</decal>");
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
		Material material = new Material( null, c, null, null, null);
		region.material = material;
		System.out.println( "region " + region.id + " got colour " + c );
	}

	final static HashMap<String, SpatialDivisionBuilder> builders = new HashMap<String, SpatialDivisionBuilder>();

	static {
		builders.put("group", new GroupSectorBuilder());
		builders.put("sector", new SectorBuilder());
		builders.put("light", new LightNodeBuilder());
		builders.put("camera", new CameraNodeBuilder());
		builders.put("decal", new CameraNodeBuilder());
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
						
					} else if ("defaultmaterial".equalsIgnoreCase(fstNode.getNodeName())) {
						readShade(masterSector, fstNode);
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
		String id = "";
		String vertShader = null;
		String fragShader = null;
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
			id += texture;
		}

		attrs = map.getNamedItem( "vertshader" );
		if ( attrs != null ) {
			vertShader = attrs.getNodeValue().trim();	
		}

		attrs = map.getNamedItem( "fragshader" );
		if ( attrs != null ) {
			fragShader = attrs.getNodeValue().trim();	
		}
		
		if ( direction != null ) {
			d = Direction.getDirectionForSimpleName(direction);	
		}		

		if ( color == null ) {
			shade = new Color();
		} else {
			shade = Color.getColorFromHTMLColor( color );
			id += shade.getHTMLColor();
		}

		Material m = new Material( id, shade, texture, vertShader, fragShader  );
		
		if (d != null) {
			gs.shades.put( d,  m );
		} else {
			gs.material = m;
		}
	}

}
