import java.util.ArrayList;
import java.util.List;

public class ListaReproduccion {
    private String nombre;
    private List<String> titulosCanciones; // se almacenan títulos (clave para buscar objeto Cancion)

    public ListaReproduccion(String nombre) {
        this.nombre = nombre;
        this.titulosCanciones = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void agregarCancion(String titulo) {
        if (titulo != null && !titulo.isEmpty() && !titulosCanciones.contains(titulo)) {
            titulosCanciones.add(titulo);
        }
    }

    public List<String> getTitulosCanciones() {
        return titulosCanciones;
    }

    @Override
    public String toString() {
        return "Lista: " + nombre + " | " + titulosCanciones.size() + " canciones";
    }

    public String toSave() {
        return "LISTA|" + escape(nombre);
    }

    public String toSaveCanciones() {
        StringBuilder sb = new StringBuilder();
        for (String t : titulosCanciones) {
            sb.append("LISTA_CANCION|").append(escape(nombre)).append("|").append(escape(t)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("|", " ");
    }
}
