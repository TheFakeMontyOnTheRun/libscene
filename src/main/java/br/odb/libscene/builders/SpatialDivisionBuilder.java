/**
 * 
 */
package br.odb.libscene.builders;

import org.w3c.dom.Node;

import br.odb.libscene.SceneNode;

/**
 * @author monty
 *
 */
public interface SpatialDivisionBuilder {

	SceneNode build( Node node );
	String toXML(SceneNode node);
}
