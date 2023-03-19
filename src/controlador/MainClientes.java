package controlador;

import clientesPackage.Clientes;
import clientesPackage.Clientes.Cliente;
import clientesPackage.ObjectFactory;
import clientesPackage.TipoDireccion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
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

            // lista de clientes
            List<Cliente> listaClientes = raizClientes.getCliente();
            //creamos el objejo Json 
            JsonObject jsonObjectClientes = gestora.jsonObjectClientes(listaClientes);
            //se lo pasamos como parámetro tal y como creamos en el método, junto con el nombre de archivo deseado
            gestora.escribirArchivoJson(jsonObjectClientes, "pruebasJson.json");

            // leer el archivo Json creado o varios
            System.out.println(gestora.leerArchivoJson("pruebasJson.json"));

            //generar xml
            gestora.generalXML("pruebasJson.json", "clientesSalidaNuevo.xml");
            
            //marshalizamos
            gestora.marshalizar((JAXBElement) gestora.leerArchivoJson("pruebasJson.json"), ficheroXMLsalida);

        } catch (JAXBException | IOException ex) {
            Logger.getLogger(MainClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
