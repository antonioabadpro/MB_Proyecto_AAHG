/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Separadora
{
    /**
     * Extrae el identificador (.I) de cada documento del fichero/corpus
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus que queremos leer
     * @return Devuelve un Array con todos los identificadores del Fichero/Corpus dado por parametro
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static ArrayList<Integer> obtenerIdentificadores(String rutaFichero) throws FileNotFoundException
    {
        ArrayList<Integer> v_id = new ArrayList<Integer>();
        
        String linea = null;
        File f = new File(rutaFichero);
        Scanner sc = new Scanner(f);
        String v_linea_id[]; // Vector que contiene la linea del fichero dividida en 2 posiciones -> 0(Información NO relevante), 1(ID)
        Integer id;
        
        while(sc.hasNextLine())
        {
            linea = sc.nextLine(); // Leemos la linea del Corpus
            
            if(linea.startsWith(".I")) // Si la linea que hemos leido empieza por ".I" significa que en la pos 1, tenemos el 'id' del documento
            {
                v_linea_id = linea.split("\\.I");
                id = Integer.valueOf(v_linea_id[1].trim());
                v_id.add(id);
            }
        }
        sc.close();
        return v_id;
    }
    
    /**
     * Extrae el texto (.W) de cada documento del fichero/corpus
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus que queremos leer
     * @return Devuelve un Array con todos los textos de cada identificador del Fichero/Corpus dado por parametro
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static ArrayList<String> obtenerTextos(String rutaFichero) throws FileNotFoundException
    {
        ArrayList<String> v_textos = new ArrayList<>();
        String linea = null;
        File f = new File(rutaFichero);
        Scanner sc = new Scanner(f);
        boolean leyendoTexto = false;
        String textoActual = ""; // Cadena que acumula el texto completo del documento actual
        
        while (sc.hasNextLine())
        {
            linea = sc.nextLine();
            
            if (linea.startsWith(".I"))
            {
                // Si ya había un texto acumulado, lo guardamos
                if (textoActual.isEmpty() == false)
                {
                    v_textos.add(textoActual.trim());
                }
                
                // Limpiamos la cadena de texto para el siguiente documento
                textoActual = "";
                leyendoTexto = false;
            } 
            else
            {
                if (linea.startsWith(".W"))
                {
                    leyendoTexto = true;
                } 
                else
                {
                    if (leyendoTexto == true)
                    {
                        linea = linea.replaceFirst("^\\s+", ""); // Eliminamos los espacios al inicio de cada linea
                        linea = linea.replaceAll(" {2,}", " "); // Reemplaza todos los espacios múltiples (2 o más) por un solo espacio

                        textoActual = textoActual + linea;
                    }
                }   
            }  
        }
        
        // No olvidar el último documento
        if (textoActual.isEmpty() == false)
        {
            v_textos.add(textoActual.trim());
        }
        
        sc.close();
        return v_textos;
    }

}


