/**
 * 
 */
package br.odb.libscene.builder;

import java.io.IOException;
import java.io.InputStream;

import br.odb.libscene.SpaceRegion;

/**
 * @author monty
 *
 */
public interface SpatialDivisionBuilder {

	SpaceRegion build(InputStream is) throws NumberFormatException, IOException;

}
