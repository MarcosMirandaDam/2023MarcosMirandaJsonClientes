package controlador;

import clientesPackage.Clientes;
import clientesPackage.ObjectFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import modelo.GestoraClientes;

/**
 *
 * @author Marcos Miranda
 */
public class MainClientes {

    public static void main(String[] args) {

        try {
            GestoraClientes gestora = new GestoraClientes();

            //archivo de entrada
            File ficheroXML = new File("clientes.xml");

            //unmarshalizamos
            JAXBElement jaxbElement = gestora.unmarshalizar(ficheroXML);

            ObjectFactory fabrica = new ObjectFactory();
            Clientes raizClientes = fabrica.createClientes();
            raizClientes = (Clientes) jaxbElement.getValue();
            
            
            
            
            
            
            

            //marshalizamos??
        } catch (JAXBException ex) {
            Logger.getLogger(MainClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
