/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static ArrayList<Integer> obtenerIdentificadoresCorpus(String rutaFichero) throws FileNotFoundException
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
    * Limpia una cadena de caracteres (String) eliminando los caracteres NO alfanuméricos
    * Reduce múltiples espacios a un solo espacio y los espacios iniciales y finales.
    * @param texto Texto original que queremos limpiar
    * @return Devuelve el Texto limpio y bien formateado
    */
   public static String limpiarTexto(String texto)
   {
       String textoLimpio="";
       
       if (texto == null)
       {
           textoLimpio="";
       }
       else
       {            
           // Sustituir los guiones por un espacio
           textoLimpio = texto.replaceAll(" - ", "");
            
           // Reemplaza los saltos de linea por espacios
           textoLimpio = texto.replaceAll("[\\r\\n]+", " ");
            
           // Eliminamos espacios duplicados que puedan haber quedado
           textoLimpio = texto.replaceAll("\\s+", " ");

           // Eliminamos espacios al inicio y al final
           textoLimpio = texto.trim();
       }
       return textoLimpio;
   }
    
    /**
     * Extrae el texto (.W) de cada documento del fichero/corpus
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus que queremos leer
     * @return Devuelve un Array con todos los textos de cada identificador del Fichero/Corpus dado por parametro
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static ArrayList<String> obtenerTextosCorpus(String rutaFichero) throws FileNotFoundException
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
                    v_textos.add(Separadora.limpiarTexto(textoActual.trim()));
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

                        textoActual = textoActual + linea + " ";
                    }
                }   
            }  
        }
        
        // No olvidar el último documento
        if (textoActual.isEmpty() == false)
        {
            v_textos.add(Separadora.limpiarTexto(textoActual.trim()));
        }
        
        sc.close();
        return v_textos;
    }
    
    /**
     * Extrae las 5 primeras palabras de cada documento/corpus
     * En caso de que el texto NO tenga 5 palabras, devuelve todas las palabras que tenga el texto
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus de Consultas que queremos leer
     * @return Devuelve un Array con todos las 5 primeras palabras de cada texto del Fichero/Corpus de Consultas dado por parametro
     */
    public static ArrayList<String> obtener5PrimerasPalabras(String rutaFichero)
    {
        ArrayList<String> v_palabras = new ArrayList<>(); // Vector que contiene las primeras 5 palabras de cada texto
        try
        {
            ArrayList<String> v_textos=obtenerTextosCorpus(rutaFichero);
            for(int i=0; i<v_textos.size(); i++)
            {
                int longitud_texto=5; // Numero de palabras que queremos recorrer en cada texto
                
                String[] v_palabras_separadas = v_textos.get(i).split("[\\s]+(?=[^\\-])"); // Separamos las palabras del texto que tengan +1 espacio entre ellas o tengan guiones
                String v_palabras_limpias="";
                
                if(v_palabras_separadas.length < 5) // Si el texto tiene menos de 5 palabras, recorremos todas las palabras del texto
                {
                    longitud_texto=v_palabras_separadas.length;
                }
                
                for(int j=0; j<longitud_texto; j++)
                {
                    String palabra_limpia = v_palabras_separadas[j].replaceAll("[^a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]", ""); // Eliminamos los caracteres de la palabra que NO sean una letra o un numero
                    v_palabras_limpias=v_palabras_limpias + " " + palabra_limpia;
                }
                // Añadimos las 5 palabras del texto 'i' al array 'v_palabras'
                v_palabras.add(v_palabras_limpias);
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Separadora.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v_palabras;
    }

}


