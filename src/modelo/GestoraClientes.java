package modelo;

import clientesPackage.Clientes;
import clientesPackage.Clientes.Cliente;
import clientesPackage.ObjectFactory;
import clientesPackage.TipoDireccion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
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

    /**
     * metodo que crea un JsonObject de tipo direccion
     * @param direccion
     * @return JsonObject
     */
    public JsonObject crearDireccion(TipoDireccion direccion) {
        JsonObject jSonDireccion = Json.createObjectBuilder()
                .add("calle", direccion.getCalle())
                .add("numero", direccion.getNumero())
                .add("piso", direccion.getPiso())
                .add("escalera", direccion.getEscalera())
                .add("cp", direccion.getCp())
                .add("ciudad", direccion.getCiudad())
                .build();

        return jSonDireccion;

    }

    /**
     * metodo que devuelve un JsonArray con los apellidos
     *
     * @param apellidos
     * @return
     */
    private JsonArray crearApellidos(List<String> apellidos) {
        JsonArray jsonArrayApellidos = Json.createArrayBuilder()
                .add(apellidos.get(0) + " " + apellidos.get(1))
                .build();
        return jsonArrayApellidos;
    }

    /**
     * 2-Crear cliente.Crea un JSONObjectBuilder.Pasar una lista de direcciones
     * y el resto de los datos del cliente.
     *
     * @param cliente
     * @return JsonObject 
     */
    public JsonObject crearCliente(Cliente cliente) {
        JsonObject jsonCliente = Json.createObjectBuilder()
                .add("apellido", crearApellidos(cliente.getApellido()))
                .add("direccion", JsonArrayDirecciones(cliente.getDireccion()))
                .add("nombre", cliente.getNombre())
                .add("telefono", cliente.getTelefono())
                .build();

        return jsonCliente;

    }

    /**
     * metodo privado que pasa un array de direcciones a JsonArray pasándole
     * como parámetro una lista de direcciones del metodo crear direccion
     *
     * @param direcciones
     * @return
     */
    private JsonArray JsonArrayDirecciones(List<TipoDireccion> direcciones) {
        JsonArrayBuilder jsonArrayDirecciones = Json.createArrayBuilder();                       //creamos con la fabrica el array

        for (TipoDireccion direccion : direcciones) {
            jsonArrayDirecciones.add(crearDireccion(direccion));                //añadimos las direcciones correspondientes
        }
        return jsonArrayDirecciones.build();

    }

    /**
     * 3-Crear clientes.Creando un JsonArrayBuilder.Pasar una lista de clientes
     *
     * @param clientes
     * @return JsonArray de clientes
     */
    public JsonArray crearClientes(List<Cliente> clientes) {
        JsonArrayBuilder jsonArrayClientes = Json.createArrayBuilder();                   //creamos fabrica. JsonArrayBuilder
        for (Cliente cliente : clientes) {
            jsonArrayClientes.add(crearCliente(cliente));

        }
        return jsonArrayClientes.build();                                                   //retornamos clientes

    }

    /**
     * Método que devuelve un JsonObject de tipo Cliente
     * @param clientes lista de tipo Clientes.Cliente
     * @return JsonObject
     */
    private JsonObject jsonObjectCliente(List<Cliente> clientes) {
        JsonObject jsonObjectCliente = Json.createObjectBuilder()
                .add("cliente", crearClientes(clientes))
                .build();
        return jsonObjectCliente;
    }

    /**
     * Método que devuelve un JsonObject de tipo Clientes (Para que nos de el
     * json con todos los elementos root e hijos)
     *
     * @param clientes lista de tipo clientes
     * @return JsonObject Clientes
     */
    public JsonObject jsonObjectClientes(List<Cliente> clientes) {
        JsonObject jsonObjectClientes = Json.createObjectBuilder()
                .add("clientes", jsonObjectCliente(clientes))
                .build();
        return jsonObjectClientes;
    }

    
    /**
     * metodo para crear y actualizar un archivo Json, parametrizado
     *
     * @param jsonObject
     * @param nombreArchivoSalida
     * @return
     * @throws java.io.IOException
     */
    public boolean escribirArchivoJson(JsonObject jsonObject, String nombreArchivoSalida) throws IOException {
        boolean creadoActualizado = false;
        FileWriter ficheroSalida = new FileWriter(nombreArchivoSalida);
        JsonWriter jsonWriter = Json.createWriter(ficheroSalida);
        jsonWriter.writeObject(jsonObject);                                     //escribimos el objeto completo
        ficheroSalida.flush();
        ficheroSalida.close();

        creadoActualizado = true;
        return creadoActualizado;
    }

    
    /**
     * lee un archivo json que recibe y devuelve un JsonArray
     *
     * @param nombreArchivo
     * @return JsonObject 
     * @throws FileNotFoundException
     */
    public JsonObject leerArchivoJson(String nombreArchivo) throws FileNotFoundException {
        FileReader fr = new FileReader(nombreArchivo);
        JsonReader jr = Json.createReader(fr);
        JsonObject readObject = jr.readObject();
        return readObject;

    }
    
    public void generalXML(String archivoJson,String salidaArchivoXML) throws FileNotFoundException{
    Clientes clientes = fabrica.createClientes();                   //creamos fabrica
    FileReader entrada = new FileReader(archivoJson);               //archivo json para leerlo
    JsonReader jsonReader = Json.createReader(entrada);
    JsonArray json = jsonReader.readArray();                   // objeto reader , para leer el array
    for (int i = 0; i < json.size(); i++) {                    //recorremos el array
    List<String> listaApellidos=new ArrayList<>();
    JsonArray jsonArrayApellidos = json.getJsonObject(i).getJsonArray("apellido");
    for(int j=0;j <listaApellidos.size();j++){
    listaApellidos.add(jsonArrayApellidos.getJsonObject(j).toString());
    }
    
    List<TipoDireccion> listaDirecciones=new ArrayList<>();
    JsonArray jsonArrayDirecciones = json.getJsonObject(i).getJsonArray("direcciones");
    for(int k=0;k<listaDirecciones.size();k++){
    listaDirecciones.add((TipoDireccion) jsonArrayDirecciones.getJsonObject(k));
    }
    String nombre=json.getString(i, "nombre");
    String telefono=json.getString(i, "telefono");
    
    //creamos el cliente 
    Cliente cliente=new Cliente();
    cliente.setNombre(nombre);
    cliente.setTelefono(telefono);
    clientes.getCliente().add(cliente);
    }
    
    
    
    }
    
    
}
