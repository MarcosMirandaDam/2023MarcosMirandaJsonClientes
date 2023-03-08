package modelo;

import clientesPackage.Clientes;
import clientesPackage.ObjectFactory;
import clientesPackage.TipoDireccion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
     *
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

    /**
     * metodo marshalizar
     *
     * @param jaxbElement
     * @param ficheroXMLsalida
     * @throws JAXBException
     */
    public void marshalizar(JAXBElement jaxbElement, File ficheroXMLsalida) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("clientesPackage");  //<-----nombre del paquete creado
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, ficheroXMLsalida);
    }

    //Apartado 1: Crear un JSON con datos usuario. 
    /**
     * 1: Crear un JSON con datos usuario. Crear dirección. Crea un
     * JSONObjectBuilder . Se pasa como parámetro el objeto dirección.
     *
     * @param tipoDireccion
     * @return
     */
    public JsonObject crearDireccion(TipoDireccion tipoDireccion) {
        JsonObject direccion = Json.createObjectBuilder()
                .add("calle", tipoDireccion.getCalle())
                .add("numero", tipoDireccion.getNumero())
                .add("piso", tipoDireccion.getPiso())
                .add("escalera", tipoDireccion.getEscalera())
                .add("cp", tipoDireccion.getCp())
                .add("ciudad", tipoDireccion.getCiudad())
                .build();

        return direccion;

    }

    /**
     * 2-Crear cliente.Crea un JSONObjectBuilder.Pasar una lista de direcciones
     * y el resto de los datos del cliente.
     *
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param telefono
     * @param direcciones
     * @return
     */
    public JsonObject crearCliente(String nombre, String apellido1, String apellido2, String telefono, List<JsonObject> direcciones) {
        JsonObject cliente = Json.createObjectBuilder()
                .add("apellido", Json.createArrayBuilder() //creamos array de apellidos
                        .add(apellido1)
                        .add(apellido2))
                .add("direccion", crearJsonArrayDirecciones(direcciones))
                .add("nombre", nombre)
                .add("telefono", telefono)
                .build();

        return cliente;

    }

    /**
     * metodo privado que pasa un array de direcciones a JsonArray pasándole
     * como parámetro una lista de direcciones del metodo crear direccion
     *
     * @param direcciones
     * @return
     */
    private JsonArray crearJsonArrayDirecciones(List<JsonObject> direcciones) {
        JsonArrayBuilder fabrica = Json.createArrayBuilder();                       //creamos con la fabrica el array
        JsonArray arrayDirecciones = null;                                          //JsonArray para las direcciones

        for (JsonValue direccion : arrayDirecciones) {
            fabrica.add(direccion);
        }

        arrayDirecciones = fabrica.build();                                           //build() del arrayDirecciones
        return arrayDirecciones;

    }

    /**
     * 3-Crear clientes.Creando un JsonArrayBuilder.Pasar una lista de clientes
     *
     * @param clientes
     * @return
     */
    public JsonArray crearClientes(List<JsonObject> clientes) {
        JsonArrayBuilder fabrica = Json.createArrayBuilder();                   //creamos fabrica. JsonArrayBuilder
        JsonArray arrayClientes = null;                                         //creamos el arrayClientes

        for (JsonValue cliente : arrayClientes) {                               //recorremos y añadimos
            fabrica.add(cliente);

        }
        arrayClientes = fabrica.build();                                        //pegamos
        return arrayClientes;                                                   //retornamos clientes

    }
    
    //APARTADO 2. CREAR UN JSON CON DATOS XML. que hemos obtenido con los metodos anteriores
    
    /**
     * metodo para crear y actualizar un archivo Json, parametrizado
     * @param object
     * @param nombreArchivoSalida
     * @return 
     */
    public boolean crearArchivoJason(JsonObject object,String nombreArchivoSalida) throws IOException{
        boolean creadoActualizado=false;
        FileWriter ficheroSalida = new FileWriter(nombreArchivoSalida);
        JsonWriter createWriter = Json.createWriter(ficheroSalida);
        createWriter.writeArray((JsonArray) object);                                     //escribimos el array completo
        ficheroSalida.flush();                                                           //hace CAST JsonArray
        ficheroSalida.close();

        creadoActualizado = true;
        return creadoActualizado;
    }

}
