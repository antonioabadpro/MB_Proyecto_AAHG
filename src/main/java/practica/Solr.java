/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Solr
{
    /**
     * Muestra todos los documentos de la coleccion Solr dada por parametro
     * @param nomColeccion Nombre de la Coleccion Solr sobre la que queremos realizar una consulta
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la consulta en la coleccion de Solr
     */
    public static void consultarTodosDocumentos(String nomColeccion) throws SolrServerException, IOException
    {
       HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/" + nomColeccion).build();
       SolrQuery consulta = new SolrQuery();
       
       consulta.setRows(Integer.MAX_VALUE); // Hacemos que la consulta muestre todas las filas del documento
       consulta.setQuery("*:*");
       
       QueryResponse rsp = solr.query(consulta); // Devuelve la respuesta a la consulta que hemos realizado sobre la coleccion de la BD
       SolrDocumentList docs = rsp.getResults();
       for (int i = 0; i < docs.size(); ++i)
       {
           System.out.println(docs.get(i));
       }
    }
    
    /**
     * Elimina todos los documentos de la coleccion Solr dada por parametro
     * @param nomColeccion Nombre de la Coleccion Solr
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la eliminacion de la coleccion de Solr
     */
    public static void eliminarTodosDocumentos(String nomColeccion) throws SolrServerException, IOException
    {
        // Preparamos el Cliente Solr
        String rutaServidorSolr = "http://localhost:8983/Solr/" + nomColeccion; 
        SolrClient cliente = new HttpSolrClient.Builder(rutaServidorSolr).build();

        // Preparando el Documento Solr
        SolrInputDocument doc = new SolrInputDocument();   

        // Realizamos una consulta que elimina todos los documentos de la coleccion de Solr
        cliente.deleteByQuery("*");

        // Realizamos un commit
        cliente.commit(); 
    }
    
    /**
     * Indexa un conjunto de documentos provenientes de una coleccion origen en una coleccion destino
     * @param rutaFichero Nombre del Fichero/Corpus del que queremos extraer los datos para indexarlos
     * @param nomColeccion Nombre de la Coleccion Solr en la que queremos indexar los documentos
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la indexacion de la coleccion de Solr
     */
    public static void indexarDocumentos(String rutaFichero, String nomColeccion) throws SolrServerException, IOException
    {
        final SolrClient cliente = new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        ArrayList<Integer> v_id = new ArrayList<Integer>();
        ArrayList<String> v_textos = new ArrayList<String>();
        
        v_id = Separadora.obtenerIdentificadoresCorpus(rutaFichero);
        v_textos = Separadora.obtenerTextosCorpus(rutaFichero);
        
        // Indexamos los documentos del fichero/corpus
        for(int i=0; i<v_id.size(); i++)
        {
            final SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", v_id.get(i));
            doc.addField("texto", v_textos.get(i));
            
            final UpdateResponse updateResponse = cliente.add(nomColeccion, doc);
        }
        cliente.commit(nomColeccion); // Realizamos un commit
    }
    
    /**
     * Devuelve el resultado de todas las Consultas de las 5 primeras palabras del Fichero/Corpus introducido por parametro (MED.QRY)
     * @param nomColeccion Nombre de la Coleccion Solr
     * @param rutaConsultas Nombre del Fichero/Corpus sobre el que queremos hacer la consulta (MED.QRY)
     * @return Devuelve un array que contiene un array con los Documentos resultantes de cada Consulta realizada
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la indexacion de la coleccion de Solr
     */
    public static ArrayList<SolrDocumentList> consultar5Palabras(String nomColeccion, String rutaConsultas) throws SolrServerException, IOException
    {
       HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/" + nomColeccion).build();
       SolrQuery consulta = new SolrQuery();
       ArrayList<SolrDocumentList> v_resultado_consultas = new ArrayList<>(); // Creamos un array que contiene los documentos respuesta de cada Consulta
       
       consulta.setRows(Integer.MAX_VALUE); // Hacemos que la consulta muestre todas las filas del documento
       ArrayList<String> v_palabras = Separadora.obtener5PrimerasPalabras(rutaConsultas);
       
       // Realizamos la Consulta en cada Documento del Corpus
       for(String texto_consulta: v_palabras)
       {
           // Filtramos el texto de cada consulta para que Solr sepa que cada palabra del String debe ir en el campo 'texto'
           String queryEscapada = "texto:" + ClientUtils.escapeQueryChars(texto_consulta); // Elimina los caracteres innecesarios que hacen que NO me salgan los resultados para algunas consultas 
           consulta.setQuery(queryEscapada);
           QueryResponse rsp = solr.query(consulta); // Devuelve la respuesta a la consulta que hemos realizado sobre la coleccion de la BD
           SolrDocumentList docs = rsp.getResults();
           
           v_resultado_consultas.add(docs);
       }
       
       return v_resultado_consultas;
    }
    
    /**
     * Devuelve el resultado de todas las Consultas del Fichero/Corpus introducido por parametro (MED.QRY)
     * @param nomColeccion Nombre de la Coleccion Solr sobre la que queremos realizar una consulta
     * @param rutaConsultas Ruta del fichero en la que se encuentran los textos de las consultas que queremos realizar
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la consulta en la coleccion de Solr
     * @return Devuelve una Array que contiene listas con los Documentos resultantes de cada Consulta realizada
     */
    public static ArrayList<SolrDocumentList> consultarDocumentosRanking(String nomColeccion, String rutaConsultas) throws SolrServerException, IOException
    {
       HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/" + nomColeccion).build();
       SolrQuery consulta = new SolrQuery();
       ArrayList<SolrDocumentList> v_listaDocumentos=new ArrayList<>();
       
       consulta.setRows(Integer.MAX_VALUE); // Hacemos que la consulta muestre todas las filas del documento
       
       // Obtenemos las consultas que queremos realizar (consultas del fichero MED.QRY)
       ArrayList<String> v_palabras = Separadora.obtenerTextosCorpus(rutaConsultas);
       
       int contador=1;
       // Realizamos la Consulta en cada Documento del Corpus
       for(String texto_consulta : v_palabras)
       {
           // Filtramos el texto de cada consulta para que Solr sepa que cada palabra del String debe ir en el campo 'texto'
           String queryEscapada = "texto:" + ClientUtils.escapeQueryChars(texto_consulta); // Elimina los caracteres innecesarios que hacen que NO me salgan los resultados para algunas consultas 
           consulta.setQuery(queryEscapada);
           consulta.setFields("id", "score");
           QueryResponse rsp = solr.query(consulta); // Devuelve la respuesta a la consulta que hemos realizado sobre la coleccion de la BD
           SolrDocumentList docs = rsp.getResults();
           // Comprobacion por consola
           System.out.println("Consulta (" + contador + "): " + texto_consulta);
           contador++;
           
           v_listaDocumentos.add(docs);
       }
        
       return v_listaDocumentos;
    }
    
    /**
     * Devuelve el resultado de todas las Consultas del Fichero/Corpus introducido por parametro (MED.QRY)
     * @param nomColeccion Nombre de la Coleccion Solr sobre la que queremos realizar una consulta
     * @param rutaConsultas Ruta del fichero en la que se encuentran los textos de las consultas que queremos realizar
     * @throws SolrServerException Lanza una Excepcion en caso de que NO pueda conectarse con la coleccion de Solr
     * @throws IOException Lanza una Excepcion en caso de que NO pueda realizar la consulta en la coleccion de Solr
     * @return Devuelve una lista con los Documentos resultantes de la Consulta realizada
     */
    public static SolrDocumentList realizarConsultaVisual(String nomColeccion, String textoConsulta, String campoBusqueda) throws SolrServerException, IOException
    {
       HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/" + nomColeccion).build();
       SolrQuery consulta = new SolrQuery();
       
       consulta.setRows(Integer.MAX_VALUE); // Hacemos que la consulta muestre todas las filas del documento
       
       if (textoConsulta == null || textoConsulta.isEmpty())
       {
           consulta.setQuery(campoBusqueda + ":*");
       }
       else
       {
           // Filtramos el texto de cada consulta para que Solr sepa que cada palabra del String debe ir en el campo 'texto'
           String queryEscapada = campoBusqueda + ":" + ClientUtils.escapeQueryChars(textoConsulta); // Elimina los caracteres innecesarios que hacen que NO me salgan los resultados para algunas consultas 
           consulta.setQuery(queryEscapada);
       }
       
       System.out.println("textoConsulta: " + textoConsulta);
       System.out.println("campoBusqueda: " + campoBusqueda);

       QueryResponse rsp = solr.query(consulta); // Devuelve la respuesta a la consulta que hemos realizado sobre la coleccion de la BD
       SolrDocumentList docs = rsp.getResults();
       
       System.out.println("Listado de Documentos:\n");
       for(SolrDocument d: docs)
       {
           System.out.println("id: " + d.getFieldValue("id"));
       }
        
       return docs;
    }
    
}
