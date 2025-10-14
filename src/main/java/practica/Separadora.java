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
    
}
