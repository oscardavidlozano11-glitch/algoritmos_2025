public class Cancion {
    private String titulo;
    private String artistaNombre; // referencia por nombre
    private String duracion;

    public Cancion(String titulo, String artistaNombre, String duracion) {
        this.titulo = titulo;
        this.artistaNombre = artistaNombre;
        this.duracion = duracion;
    }

    public String getTitulo() { return titulo; }
    public String getArtistaNombre() { return artistaNombre; }
    public String getDuracion() { return duracion; }

    @Override
    public String toString() {
        return titulo + " | " + artistaNombre + " | " + duracion;
    }

    public String toSave() {
        return "CANCION|" + escape(titulo) + "|" + escape(artistaNombre) + "|" + escape(duracion);
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("|", " ");
    }
}
