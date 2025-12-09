package practica;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Antonio Abad
 */
public class Rag
{
    // URL de tu servidor local LM Studio
    private static final String API_URL = "http://127.0.0.1:1234/v1/chat/completions";
    //private static final String API_URL = "http://localhost:1234/v1/chat/completions";
    private static final String MODEL_ID = "google/gemma-3n-e4b"; // Modelo utilizado desde LM Studio

    public String generarRespuestaRAG(String preguntaUsuario, String contextoSolr)
    {
        try
        {
            // 1. Construimos el Prompt del Sistema incluyendo el escrito por el Usuario
            // Le decimos a la IA que use solo el contexto proporcionado
            String systemPrompt = "Eres un asistente útil. Responde a la pregunta basándote ÚNICAMENTE en el contexto proporcionado a continuación. Si la respuesta no está en el contexto, di: 'No lo se aún, estoy trabajando en ello'.";

            String finalPrompt = "Contexto:\n" + contextoSolr + "\n\nPregunta: " + preguntaUsuario;

            // 2. Creamos el JSON del cuerpo (Body)
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", finalPrompt);

            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", systemPrompt);

            JSONArray messages = new JSONArray();
            messages.put(systemMessage);
            messages.put(userMessage);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", MODEL_ID);
            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.3);
            requestBody.put("max_tokens", 300);

            // 3. Configuramos Cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1) // Si NO le pongo esta linea para la version NO se conecta con LM Studio
                    .connectTimeout(Duration.ofSeconds(60))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(300))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // 4. Enviamos y esperamos la respuesta de la IA
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                // Parsear la respuesta de LM Studio
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            }
            else
            {
                return "Error en LM Studio: " + response.statusCode();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Error conectando con la IA: " + e.getMessage();
        }
    }
};
