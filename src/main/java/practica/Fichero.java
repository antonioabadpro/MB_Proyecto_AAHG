/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Fichero
{
    /**
     * Lee un fichero dado por parametro y lo muestra por pantalla
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus que queremos leer
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static void leerFichero(String rutaFichero) throws FileNotFoundException
    {
        String linea = null;
        Scanner sc = new Scanner(new File(rutaFichero));
        while(sc.hasNextLine())
        {
            linea = sc.nextLine();
            System.out.println(linea);
        }
    }
    
    /**
     * Lee un fichero dado por parametro y lo muestra con el formato original por pantalla
     * @param rutaFichero Es la ruta en la que se encuentra el fichero/corpus que queremos leer
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static void leerFicheroSeparado(String rutaFichero) throws FileNotFoundException
    {
        String linea = null;
        File f = new File(rutaFichero);
        Scanner sc = new Scanner(f);
        String v_linea_id[]; // Vector que contiene la linea del fichero dividida en 2 posiciones -> 0(Información NO relevante), 1(ID)
        Integer id;
        String texto = "";
        boolean leyendoTexto = false;
        
        while(sc.hasNextLine())
        {
            linea = sc.nextLine(); // Leemos la linea del Corpus
            
            if(linea.startsWith(".I")) // Si la linea que hemos leido empieza por ".I" significa que en la pos 1, tenemos el 'id' del documento
            {
                leyendoTexto = false;
                v_linea_id = linea.split("\\.I");
                id = Integer.valueOf(v_linea_id[1].trim());
                System.out.println("\nID: " + id);
                System.out.println("\nTexto: \n");
            }
            if(linea.startsWith(".W")) // Si la linea que hemos leido empieza por ".W" significa que en la siguiente linea comienza el texto
            {
                leyendoTexto = true;
            }
            else
            {
                if(leyendoTexto == true) // Si la linea NO empieza por ".W" y estamos leyendo el texto, mostramos la linea correspondiente
                {
                    linea = linea.replaceFirst("^\\s+", ""); // Eliminamos los espacios al inicio de cada linea
                    linea = linea.replaceAll(" {2,}", " "); // Reemplaza todos los espacios múltiples (2 o más) por un solo espacio
                    texto = linea;
                    System.out.println(texto);
                }
            }
        }
        sc.close();
    }
    
    /**
     * Escribe un Fichero .txt en la misma ruta del proyecto con los documentos resultantes de cada Consulta realizada en el metodo 'consultar5Palabras()'
     * @param v_resultado_consultas Array que contiene un array con los Documentos resultantes de cada Consulta realizada
     * @param rutaConsultas Es la ruta en la que se encuentra el fichero/corpus de Consultas que queremos leer (MED.QRY)
     */
    public static void escribirFicheroConsulta5Palabras(ArrayList<ArrayList<Documento>> v_resultado_consultas, String rutaConsultas)
    {
        String nomFichero = "consultas5Palabras.txt";
        
        try
        {
            FileWriter f=new FileWriter(nomFichero);
            
            ArrayList<String> texto_consulta = Separadora.obtener5PrimerasPalabras(rutaConsultas); // Para mostrar el texto de cada Consulta
            
            // Mostramos los Documentos resultado de cada Consulta realizada
            for (int i = 0; i < v_resultado_consultas.size(); i++)
            {
                f.write("\n\n\t\t\t********** Consulta " + (i + 1) + ": " + texto_consulta.get(i) + " **********");
                for (Documento d : v_resultado_consultas.get(i))
                {
                    f.write("\n\nDocumento " + d.getId() + ": ");
                    f.write("\n\n" + d.getTexto());
                }
            }
            
            // Escribimos el fin el fichero (EOF)
            f.write("EOF");
            f.close(); // Cerramos el fichero
            
        } catch (IOException ex)
        {
            System.out.println("Error al crear el Fichero con nombre '" + nomFichero + "'");
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Escribe un Fichero .txt en la misma ruta del proyecto con los documentos resultantes de cada Consulta realizada en el metodo 'consultarDocumentosRanking()'
     * @param v_listaDocumentos Es un array que contiene una Lista con los Documentos resultantes de cada Consulta realizada
     * @param rutaConsultas Es la ruta en la que se encuentra el fichero/corpus de Consultas que queremos leer (MED.QRY)
     */
    public static void escribirFicheroConsultas(ArrayList<SolrDocumentList> v_listaDocumentos, String rutaConsultas)
    {
        String nomFichero = "rankingConsultas.trec";
        int numConsulta=1;
        
        try
        {
            // Creamos el Fichero con su nombre
            File fichero=new File(nomFichero);
            
            // Como vamos a escribir en el fichero necesitamoss crear un objeto de la Clase 'FileWriter' para poder escribir en el fichero 'File'
            FileWriter fich=new FileWriter(nomFichero);
            
            // Como quiero escribir el fichero con el mismo formato con el que lo muestro por pantalla en el 'case 4' necesito un objeto de tipo 'PrintWriter'
            PrintWriter f=new PrintWriter(fich);
            
            // Escribimos la cabecera del Fichero
            f.printf("%-10s %-8s %-10s %-16s %-16s %-16s%n", "Consulta", "Q", "Documento", "Ranking", "Score", "EQUIPO");
            
            // Recorremos cada posicion del array que contiene las listas de Documentos
            for(SolrDocumentList listaDocumentos : v_listaDocumentos)
            {
                // Mostramos los Documentos resultado de cada Consulta realizada
                for (SolrDocument d : listaDocumentos)
                {
                    f.printf("%-10s ", numConsulta);
                    f.printf("%-8s ", "Q0");
                    f.printf("%-10s ", d.getFieldValue("id"));
                    f.printf("%-16s ", d.getFieldValue("ranking"));
                    f.printf("%-16f ", d.getFieldValue("score"));
                    f.printf("%-16s ", "AAHG");
                    f.write("\n");
                }
                numConsulta++;
            }
        
            // Escribimos el fin el fichero (EOF)
            f.write("EOF");
            f.close(); // Cerramos el fichero
            
            System.out.println("\nFichero " + nomFichero + " generado con exito");
            
        } catch (IOException ex)
        {
            System.out.println("Error al crear el Fichero con nombre '" + nomFichero + "'");
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
