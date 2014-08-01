/**
 * 
 */
package br.odb.libscene.builder;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.odb.libscene.SpaceRegion;

/**
 * @author monty
 *
 */
public interface SpatialDivisionBuilder {

	SpaceRegion build(XMLStreamReader streamReader)  throws NumberFormatException, IOException, XMLStreamException;

}
