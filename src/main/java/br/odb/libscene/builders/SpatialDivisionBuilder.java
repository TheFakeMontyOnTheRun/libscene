/**
 * 
 */
package br.odb.libscene.builders;

import org.w3c.dom.Node;

import br.odb.libscene.SpaceRegion;

/**
 * @author monty
 *
 */
public interface SpatialDivisionBuilder {

	SpaceRegion build( Node node );

}
