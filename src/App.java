import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados
        var geradora = new StickGenerator();
        for (Map<String, String> filme : listaDeFilmes) {
            
            String urlImagem = filme.get("image"); 
            String titulo = filme.get("title"); 

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = titulo + ".png";

            geradora.cria(inputStream, nomeArquivo);


            //  Exibição no console

                System.out.println("\u001b[1m\u001b[37;1m\u001b[44;1mTítulo:\u001b[m " + filme.get("title"));
                System.out.println("\u001b[1m\u001b[37;1m\u001b[44;1mCapa:\u001b[m " + filme.get("image"));

                // System.out.println("\u001b[1m\u001b[37;1m\u001b[44;1mNota:\u001b[m " +
                // filme.get("imDbRating"));

                double classificacao = Double.parseDouble(filme.get("imDbRating"));
                int numeroEstrelinhas = (int) classificacao;
                for (int n = 1; n <= numeroEstrelinhas; n++) {
                    System.out.print("\u2B50");

                }
                
                System.out.println();

            //  Fim da execução no console
        }
    }
}
