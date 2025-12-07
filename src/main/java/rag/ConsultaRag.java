package rag;

import practica.Rag;
import javax.swing.JTextArea;

/**
 *
 * @author Antonio Abad
 */
public class ConsultaRag extends javax.swing.SwingWorker<String, Void>
{
    private final String pregunta;
    private final String contexto;
    private final JTextArea jTextArea_respuestaIA;

    // Constructor limpio: Recibe solo los datos necesarios
    public ConsultaRag(String pregunta, String contexto, JTextArea jTextArea_respuestaIA)
    {
        this.pregunta = pregunta;
        this.contexto = contexto;
        this.jTextArea_respuestaIA = jTextArea_respuestaIA;
    }

    // 1. Esto lo hace el "Cocinero" (NO toca la interfaz visual)
    @Override
    protected String doInBackground() throws Exception
    {
        String respuesta = "";
        Rag aiService = new Rag();
        
        // Llamada lenta a la API
        respuesta = aiService.generarRespuestaRAG(pregunta, contexto);
        
        return respuesta;
    }

    // 2. Esto ocurre cuando termina, en el hilo del "Camarero" (AQUÍ sí tocamos la interfaz)
    @Override
    protected void done()
    {
        try
        {
            String respuestaIA = get(); // 'get()' recupera lo que devolvió doInBackground
            
            // Actualizamos el JTextArea de la interfaz
            this.jTextArea_respuestaIA.setText(respuestaIA);
            System.out.println("Texto Respuesta IA:\n\n" + respuestaIA);

        }
        catch (InterruptedException ex)
        {
            this.jTextArea_respuestaIA.setText("La búsqueda fue interrumpida.");
        }
        catch (java.util.concurrent.ExecutionException ex)
        {
            // ESTO es lo que te dirá qué pasó realmente
            this.jTextArea_respuestaIA.setText("Error interno: " + ex.getCause().getMessage());
            System.out.println("--- ERROR CRÍTICO EN WORKER ---");
        }
    }
}
