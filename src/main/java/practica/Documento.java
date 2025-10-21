/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

/**
 *
 * @author AAHG-PORTATIL
 */
public class Documento
{
    private int id;
    private String texto;

    public Documento(int id, String texto)
    {
        this.id = id;
        this.texto = texto;
    }

    public int getId()
    {
        return this.id;
    }
    
    public String getTexto()
    {
        return this.texto;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}
