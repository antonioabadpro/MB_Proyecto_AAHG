/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
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
       consulta.setRows(Integer.SIZE); // Hacemos que la consulta muestre todas las filas del documento
       consulta.setQuery("*:*");
       //query.setQuery("Apple");
       //query.addFilterQuery("cat:electronics");
       //query.setFields("id","price","merchant","cat","store");
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
        SolrClient Solr = new HttpSolrClient.Builder(rutaServidorSolr).build();   

        // Preparando el Documento Solr
        SolrInputDocument doc = new SolrInputDocument();   

        // Realizamos una consulta que elimina todos los documentos de la coleccion de Solr
        Solr.deleteByQuery("*");

        // Realizamos un commit
        Solr.commit(); 
    }
    
}
