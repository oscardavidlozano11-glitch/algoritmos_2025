public class Artista {
    private String nombre;
    private String pais;
    private String genero;

    public Artista(String nombre, String pais, String genero) {
        this.nombre = nombre;
        this.pais = pais;
        this.genero = genero;
    }

    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public String getGenero() { return genero; }

    @Override
    public String toString() {
        return nombre + " | " + genero + " | " + pais;
    }

    public String toSave() {
        return "ARTISTA|" + escape(nombre) + "|" + escape(pais) + "|" + escape(genero);
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("|", " ");
    }
}

