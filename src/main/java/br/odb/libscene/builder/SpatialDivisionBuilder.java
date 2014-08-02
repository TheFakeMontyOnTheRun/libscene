/**
 * 
 */
package br.odb.libscene.builder;

import org.w3c.dom.Node;

import br.odb.libscene.SpaceRegion;

/**
 * @author monty
 *
 */
public interface SpatialDivisionBuilder {

	SpaceRegion build( Node node );

}
