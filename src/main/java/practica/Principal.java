/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

import java.io.FileNotFoundException;
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
        // Leemos el Fichero que nos dan 'MED.ALL'
        try
        {
            Separadora.leerFichero("MED.ALL");
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\nError al leer el fichero -> 'Principal'");
        }
    }
    
}
