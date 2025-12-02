import java.io.*;
import java.util.*;

public class BaseDatos {

    public static Map<String, Artista> artistas = new LinkedHashMap<>();
    public static Map<String, Cancion> canciones = new LinkedHashMap<>();
    public static Map<String, ListaReproduccion> listas = new LinkedHashMap<>();

    private static final String ARCHIVO = "datos.txt";

    static {
        cargarDesdeArchivo();
    }

    // --------- Métodos para agregar ----------
    public static boolean agregarArtista(Artista a) {
        if (a == null || a.getNombre() == null || a.getNombre().trim().isEmpty()) return false;
        String key = a.getNombre().trim();
        if (artistas.containsKey(key)) return false;
        artistas.put(key, a);
        guardarEnArchivo();
        return true;
    }

    public static boolean agregarCancion(Cancion c) {
        if (c == null || c.getTitulo() == null || c.getTitulo().trim().isEmpty()) return false;
        String key = c.getTitulo().trim();
        if (canciones.containsKey(key)) return false;
        // comprobar que el artista existe
        if (c.getArtistaNombre() != null && !c.getArtistaNombre().isEmpty()) {
            if (!artistas.containsKey(c.getArtistaNombre())) return false;
        }
        canciones.put(key, c);
        guardarEnArchivo();
        return true;
    }

    public static boolean agregarLista(ListaReproduccion l) {
        if (l == null || l.getNombre() == null || l.getNombre().trim().isEmpty()) return false;
        String key = l.getNombre().trim();
        if (listas.containsKey(key)) return false;
        listas.put(key, l);
        guardarEnArchivo();
        return true;
    }

    public static boolean agregarCancionALista(String nombreLista, String tituloCancion) {
        if (!listas.containsKey(nombreLista) || !canciones.containsKey(tituloCancion)) return false;
        ListaReproduccion l = listas.get(nombreLista);
        l.agregarCancion(tituloCancion);
        guardarEnArchivo();
        return true;
    }

    // --------- Lectura/Escritura ----------
    private static synchronized void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            // Guardar listas (solo la definición)
            for (ListaReproduccion l : listas.values()) {
                bw.write(l.toSave());
                bw.newLine();
            }
            // Guardar artistas
            for (Artista a : artistas.values()) {
                bw.write(a.toSave());
                bw.newLine();
            }
            // Guardar canciones
            for (Cancion c : canciones.values()) {
                bw.write(c.toSave());
                bw.newLine();
            }
            // Guardar las relaciones lista-cancion
            for (ListaReproduccion l : listas.values()) {
                String bloques = l.toSaveCanciones();
                if (!bloques.isEmpty()) {
                    bw.write(bloques);
                }
            }
        } catch (IOException e) {
            System.err.println("Error guardando datos: " + e.getMessage());
        }
    }

    private static synchronized void cargarDesdeArchivo() {
        File f = new File(ARCHIVO);
        if (!f.exists()) return;
        // Leer todo y procesar por línea
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split("\\|", -1);
                String tipo = partes[0].trim().toUpperCase();
                switch (tipo) {
                    case "LISTA":
                        if (partes.length >= 2) {
                            String nombreLista = partes[1].trim();
                            if (!listas.containsKey(nombreLista)) {
                                listas.put(nombreLista, new ListaReproduccion(nombreLista));
                            }
                        }
                        break;
                    case "ARTISTA":
                        // ARTISTA|nombre|pais|genero
                        if (partes.length >= 4) {
                            String nombre = partes[1].trim();
                            String pais = partes[2].trim();
                            String genero = partes[3].trim();
                            if (!artistas.containsKey(nombre)) {
                                artistas.put(nombre, new Artista(nombre, pais, genero));
                            }
                        }
                        break;
                    case "CANCION":
                        // CANCION|titulo|artista|duracion
                        if (partes.length >= 4) {
                            String titulo = partes[1].trim();
                            String artistaNombre = partes[2].trim();
                            String duracion = partes[3].trim();
                            if (!canciones.containsKey(titulo)) {
                                canciones.put(titulo, new Cancion(titulo, artistaNombre, duracion));
                            }
                        }
                        break;
                    case "LISTA_CANCION":
                        // LISTA_CANCION|nombre_lista|titulo_cancion
                        if (partes.length >= 3) {
                            String nombreLista = partes[1].trim();
                            String tituloCanc = partes[2].trim();
                            // ensure list exists
                            if (!listas.containsKey(nombreLista)) {
                                listas.put(nombreLista, new ListaReproduccion(nombreLista));
                            }
                            ListaReproduccion l = listas.get(nombreLista);
                            l.agregarCancion(tituloCanc);
                        }
                        break;
                    default:
                        // línea desconocida -> ignorar
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando datos: " + e.getMessage());
        }
    }

    // Métodos utilitarios de consulta
    public static List<ListaReproduccion> obtenerListas() {
        return new ArrayList<>(listas.values());
    }

    public static List<Artista> obtenerArtistas() {
        return new ArrayList<>(artistas.values());
    }

    public static List<Cancion> obtenerCanciones() {
        return new ArrayList<>(canciones.values());
    }

    public static Artista buscarArtistaPorNombre(String nombre) {
        return artistas.get(nombre);
    }

    public static Cancion buscarCancionPorTitulo(String titulo) {
        return canciones.get(titulo);
    }

    public static ListaReproduccion buscarListaPorNombre(String nombre) {
        return listas.get(nombre);
    }
}

