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
