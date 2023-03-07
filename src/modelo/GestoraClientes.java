package modelo;

import clientesPackage.Clientes;
import clientesPackage.ObjectFactory;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Marcos Miranda
 */
public class GestoraClientes {

    ObjectFactory fabrica = new ObjectFactory();

    /**
     * metodo unmarshalizar
     * @param ficheroXML
     * @return
     * @throws JAXBException
     */
    public JAXBElement unmarshalizar(File ficheroXML) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("clientesPackage");
        Unmarshaller unmarshall = jaxbContext.createUnmarshaller();
        JAXBElement jaxbElement = unmarshall.unmarshal(new StreamSource(ficheroXML), Clientes.class);
        return jaxbElement;
    }
    
    
    

}
