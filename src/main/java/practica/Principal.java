/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Principal
{
    public static void main(String[] args)
    {
        String nomColeccion = "miColeccion";
        String nomCorpus = "MED.ALL";
        
        // Leemos el Fichero que nos dan 'MED.ALL'
        try
        {
            //Separadora.leerFichero("MED.ALL");
            
            //Solr.consultarTodosDocumentos(nomColeccion);
            //Separadora.leerFicheroSeparado(nomCorpus);
            
            ArrayList<Integer> v_id = Separadora.obtenerIdentificadores(nomCorpus);
            ArrayList<String> v_textos = Separadora.obtenerTextos(nomCorpus);
            
            for(int i=0; i<v_id.size(); i++)
            {
                System.out.println("\nID: " + v_id.get(i));
                System.out.println("\nTexto: \n\n" + v_textos.get(i));
            }
        } catch (Exception e)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("\nError en -> 'Principal'");
        }
    }
    
}
