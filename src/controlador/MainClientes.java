package controlador;

import clientesPackage.Clientes;
import clientesPackage.ObjectFactory;
import clientesPackage.TipoDireccion;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
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
            // archivo de salida			
            File ficheroXMLsalida = new File("clientesSalida.xml");			
 

            //unmarshalizamos
            JAXBElement jaxbElement = gestora.unmarshalizar(ficheroXML);

            ObjectFactory fabrica = new ObjectFactory();
            Clientes raizClientes = fabrica.createClientes();
            raizClientes = (Clientes) jaxbElement.getValue();
            
            //CREAR UNA DIRECCION
            JsonObject direccion1=gestora.crearDireccion(new TipoDireccion("la vega","50",3,"A",33940,"El Entrego"));
            
            
            
            
            
            
            
            // creamos fichreo Json/actualizamos
            gestora.crearArchivoJason(object, nombreArchivoSalida);
            //marshalizamos
            gestora.marshalizar(jaxbElement, ficheroXMLsalida);
            
            
        } catch (JAXBException ex) {
            Logger.getLogger(MainClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
