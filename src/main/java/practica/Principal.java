/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.FileNotFoundException;
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
    /**
     * Primera prueba realizada para la v0.1
     */
    public static void pruebaLecturaCorpus()
    {
        String rutaCorpus = "MED.ALL";

        // Leemos el Fichero que nos dan 'MED.ALL'
        try
        { 
            ArrayList<Integer> v_id = Separadora.obtenerIdentificadoresCorpus(rutaCorpus);
            ArrayList<String> v_textos = Separadora.obtenerTextosCorpus(rutaCorpus);
            
            // Mostramos los documentos del Corpus
            for(int i=0; i<v_id.size(); i++)
            {
                System.out.println("\nID: " + v_id.get(i));
                System.out.println("\nTexto: \n\n" + v_textos.get(i));
            }
        } catch (Exception e)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Prueba realizada para comprobar que la v0.1 funciona correctamente
     */
    public static void pruebaV01()
    {
        String nomColeccion = "coleccionPrueba";
        String rutaCorpus = "MED.ALL";
        
        // Indexamos el Corpus en la Coleccion -> v0.1
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
    
    /**
     * Primera prueba realizada para la v0.2
     */
    public static void pruebaLecturaPalabrasConsultas()
    {
        String rutaConsultas = "MED.QRY";
        
        try
        {
            ArrayList<String> v_palabras=Separadora.obtener5PrimerasPalabras(rutaConsultas);
            for(int i=0; i<v_palabras.size(); i++)
            {
                System.out.println("\nConsulta " + (i+1) + ": ");
                System.out.println(v_palabras.get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args)
    {
        String coleccion_original = "miColeccion";
        String nomColeccion = "coleccionPrueba";
        String rutaCorpus = "MED.ALL";
        String rutaConsultas = "MED.QRY";
        
        Principal.pruebaLecturaPalabrasConsultas();
        
    }
    
}
