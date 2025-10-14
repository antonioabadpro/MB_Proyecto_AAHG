/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Principal
{
    public static void main(String[] args)
    {
        String coleccion_original = "miColeccion";
        String nomColeccion = "coleccionPrueba";
        String rutaCorpus = "MED.ALL";
        
// Leemos el Fichero que nos dan 'MED.ALL'
//        try
//        { 
//            ArrayList<Integer> v_id = Separadora.obtenerIdentificadores(nomCorpus);
//            ArrayList<String> v_textos = Separadora.obtenerTextos(nomCorpus);
//            
//            // Mostramos los documentos del Corpus
//            for(int i=0; i<v_id.size(); i++)
//            {
//                System.out.println("\nID: " + v_id.get(i));
//                System.out.println("\nTexto: \n\n" + v_textos.get(i));
//            }
//        } catch (Exception e)
//        {
//            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
//            System.out.println("\nError en main() -> 'Principal'");
//        }
        
        // Indexamos el Corpus en la Coleccion
        try
        {
            Solr.indexarDocumentos(rutaCorpus, nomColeccion);
            
        } catch (SolrServerException ex)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
