/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Separadora
{
    /**
     * Lee un fichero dado por parametro y lo muestra por pantalla
     * @param rutaFichero Es la ruta en la que se encuentra el fichero que queremos leer
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
     * @param rutaFichero Es la ruta en la que se encuentra el fichero que queremos leer
     * @throws FileNotFoundException Lanza una excepcion en caso de que NO se encuentre la ruta del fichero introducida por parametro
     */
    public static void leerFicheroSeparado(String rutaFichero) throws FileNotFoundException
    {
        String linea = null;
        File f = new File(rutaFichero);
        Scanner sc = new Scanner(f);
        String v_linea_id[]; // Vector que contiene la linea del fichero dividida en 2 posiciones -> 0(Información NO relevante), 1(ID)
        String v_linea_texto[]; // Vector que contiene la linea del fichero dividida en 2 posiciones -> 0(Información NO relevante), 1(Texto)
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
                System.out.println("ID:\n" + id);
            }
            if(linea.startsWith(".W")) // Si la linea que hemos leido empieza por ".I" significa que en la pos 1, tenemos el 'texto' del documento
            {
                leyendoTexto = true;
            }
            else
            {
                if(leyendoTexto = true)
                {
                    System.out.println(linea);
                }
            }
        }
        sc.close();
    }
}


