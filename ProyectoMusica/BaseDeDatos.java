import java.util.ArrayList;

public class BaseDeDatos {

    public static ArrayList<String> artistas = new ArrayList<>();
    public static ArrayList<String> canciones = new ArrayList<>();

    public static void agregarArtista(String nombre, String genero, String pais) {
        artistas.add("Nombre: " + nombre + " | Género: " + genero + " | País: " + pais);
    }

    public static void agregarCancion(String titulo, String artista, String duracion) {
        canciones.add("Título: " + titulo + " | Artista: " + artista + " | Duración: " + duracion);
    }

    public static ArrayList<String> buscar(String texto) {
        ArrayList<String> resultados = new ArrayList<>();

        for (String a : artistas) {
            if (a.toLowerCase().contains(texto.toLowerCase())) {
                resultados.add("[ARTISTA] " + a);
            }
        }

        for (String c : canciones) {
            if (c.toLowerCase().contains(texto.toLowerCase())) {
                resultados.add("[CANCIÓN] " + c);
            }
        }

        return resultados;
    }
}
