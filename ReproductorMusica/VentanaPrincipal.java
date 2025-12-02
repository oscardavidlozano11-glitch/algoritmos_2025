import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Gestor de Colecciones de Música");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Panel izquierdo: Listas (WEST)
        JPanel panelListas = new JPanel(new GridLayout(5, 1, 6, 6));
        panelListas.setBorder(BorderFactory.createTitledBorder("Listas de reproducción"));
        JButton btnMostrarListas = new JButton("Mostrar listas");
        JButton btnCrearLista = new JButton("Crear lista nueva");
        JButton btnVerContenido = new JButton("Ver contenido de lista");
        JButton btnAgregarCancionALista = new JButton("Agregar canción a lista");
        JButton btnGuardarCambios = new JButton("Guardar cambios (archivo)");

        panelListas.add(btnMostrarListas);
        panelListas.add(btnCrearLista);
        panelListas.add(btnVerContenido);
        panelListas.add(btnAgregarCancionALista);
        panelListas.add(btnGuardarCambios);

        // Panel centro: Artistas (CENTER)
        JPanel panelArtistas = new JPanel(new GridLayout(5, 1, 6, 6));
        panelArtistas.setBorder(BorderFactory.createTitledBorder("Artistas"));
        JButton btnMostrarArtistas = new JButton("Mostrar artistas");
        JButton btnCrearArtista = new JButton("Crear artista");
        JButton btnBuscarArtista = new JButton("Buscar artista");
        JButton btnEliminarArtista = new JButton("Eliminar artista (opcional)");
        panelArtistas.add(btnMostrarArtistas);
        panelArtistas.add(btnCrearArtista);
        panelArtistas.add(btnBuscarArtista);
        panelArtistas.add(btnEliminarArtista);
        panelArtistas.add(new JLabel("")); // espacio

        // Panel inferior: Canciones (SOUTH)
        JPanel panelCanciones = new JPanel(new GridLayout(5, 1, 6, 6));
        panelCanciones.setBorder(BorderFactory.createTitledBorder("Canciones"));
        JButton btnMostrarCanciones = new JButton("Mostrar canciones");
        JButton btnCrearCancion = new JButton("Crear canción");
        JButton btnBuscarCancion = new JButton("Buscar canción");
        JButton btnEliminarCancion = new JButton("Eliminar canción (opcional)");
        JButton btnEditarCancion = new JButton("Editar canción (opcional)");

        panelCanciones.add(btnMostrarCanciones);
        panelCanciones.add(btnCrearCancion);
        panelCanciones.add(btnBuscarCancion);
        panelCanciones.add(btnEliminarCancion);
        panelCanciones.add(btnEditarCancion);

        // Añadir paneles al frame
        add(panelListas, BorderLayout.WEST);
        add(panelArtistas, BorderLayout.CENTER);
        add(panelCanciones, BorderLayout.SOUTH);

        // ---------- Acciones ----------
        btnMostrarListas.addActionListener(e -> mostrarListas());
        btnCrearLista.addActionListener(e -> crearLista());
        btnVerContenido.addActionListener(e -> verContenidoLista());
        btnAgregarCancionALista.addActionListener(e -> agregarCancionALista());
        btnGuardarCambios.addActionListener(e -> {
            // BaseDatos guarda automáticamente en cada operación, pero permitimos forzar
            JOptionPane.showMessageDialog(this, "Los datos se guardan automáticamente en datos.txt.");
        });

        btnMostrarArtistas.addActionListener(e -> mostrarArtistas());
        btnCrearArtista.addActionListener(e -> crearArtista());
        btnBuscarArtista.addActionListener(e -> buscarArtista());
        btnEliminarArtista.addActionListener(e -> eliminarArtista());

        btnMostrarCanciones.addActionListener(e -> mostrarCanciones());
        btnCrearCancion.addActionListener(e -> crearCancion());
        btnBuscarCancion.addActionListener(e -> buscarCancion());
        btnEliminarCancion.addActionListener(e -> eliminarCancion());
        btnEditarCancion.addActionListener(e -> editarCancion());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ---------- Implementación de acciones (diálogos sencillos) ----------
    private void mostrarListas() {
        List<ListaReproduccion> listas = BaseDatos.obtenerListas();
        StringBuilder sb = new StringBuilder();
        if (listas.isEmpty()) sb.append("No hay listas creadas.\n");
        else {
            for (ListaReproduccion l : listas) {
                sb.append(l.toString()).append("\n");
            }
        }
        mostrarEnVentana("Listas de reproducción", sb.toString());
    }

    private void crearLista() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre de la nueva lista:");
        if (nombre == null) return;
        nombre = nombre.trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ListaReproduccion l = new ListaReproduccion(nombre);
        boolean ok = BaseDatos.agregarLista(l);
        if (ok) JOptionPane.showMessageDialog(this, "Lista creada: " + nombre);
        else JOptionPane.showMessageDialog(this, "Ya existe una lista con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void verContenidoLista() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre de la lista a ver:");
        if (nombre == null) return;
        ListaReproduccion l = BaseDatos.buscarListaPorNombre(nombre.trim());
        if (l == null) {
            JOptionPane.showMessageDialog(this, "Lista no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Contenido de la lista: ").append(l.getNombre()).append("\n\n");
        if (l.getTitulosCanciones().isEmpty()) sb.append("(vacía)\n");
        else {
            for (String t : l.getTitulosCanciones()) {
                Cancion c = BaseDatos.buscarCancionPorTitulo(t);
                if (c != null) sb.append("- ").append(c.toString()).append("\n");
                else sb.append("- ").append(t).append(" (no se encontró objeto canción)\n");
            }
        }
        mostrarEnVentana("Contenido de lista: " + l.getNombre(), sb.toString());
    }

    private void agregarCancionALista() {
        String nombreLista = JOptionPane.showInputDialog(this, "Nombre de la lista:");
        if (nombreLista == null) return;
        ListaReproduccion l = BaseDatos.buscarListaPorNombre(nombreLista.trim());
        if (l == null) {
            JOptionPane.showMessageDialog(this, "Lista no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String titulo = JOptionPane.showInputDialog(this, "Título de la canción a agregar (debe existir):");
        if (titulo == null) return;
        Cancion c = BaseDatos.buscarCancionPorTitulo(titulo.trim());
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Canción no encontrada. Crea la canción primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean ok = BaseDatos.agregarCancionALista(l.getNombre(), c.getTitulo());
        if (ok) JOptionPane.showMessageDialog(this, "Canción añadida a la lista.");
        else JOptionPane.showMessageDialog(this, "No se pudo añadir la canción.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarArtistas() {
        List<Artista> lista = BaseDatos.obtenerArtistas();
        StringBuilder sb = new StringBuilder();
        if (lista.isEmpty()) sb.append("No hay artistas.\n");
        else {
            for (Artista a : lista) {
                sb.append(a.toString()).append("\n");
            }
        }
        mostrarEnVentana("Artistas", sb.toString());
    }

    private void crearArtista() {
        JTextField txtNombre = new JTextField();
        JTextField txtPais = new JTextField();
        JTextField txtGenero = new JTextField();
        Object[] msg = {
                "Nombre:", txtNombre,
                "País:", txtPais,
                "Género:", txtGenero
        };
        int option = JOptionPane.showConfirmDialog(this, msg, "Crear artista", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre requerido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Artista a = new Artista(nombre, txtPais.getText().trim(), txtGenero.getText().trim());
        boolean ok = BaseDatos.agregarArtista(a);
        if (ok) JOptionPane.showMessageDialog(this, "Artista creado.");
        else JOptionPane.showMessageDialog(this, "Ya existe artista con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void buscarArtista() {
        String q = JOptionPane.showInputDialog(this, "Nombre del artista a buscar:");
        if (q == null) return;
        Artista a = BaseDatos.buscarArtistaPorNombre(q.trim());
        if (a == null) JOptionPane.showMessageDialog(this, "Artista no encontrado.", "Info", JOptionPane.INFORMATION_MESSAGE);
        else mostrarEnVentana("Artista: " + a.getNombre(), a.toString());
    }

    private void eliminarArtista() {
        String q = JOptionPane.showInputDialog(this, "Nombre del artista a eliminar:");
        if (q == null) return;
        String nombre = q.trim();
        if (nombre.isEmpty()) return;
        // eliminación simple: quitar del mapa (nota: no elimina canciones asociadas)
        if (BaseDatos.artistas.containsKey(nombre)) {
            int resp = JOptionPane.showConfirmDialog(this, "Eliminar artista " + nombre + " ? (las canciones seguirán existiendo)", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                BaseDatos.artistas.remove(nombre);
                BaseDatos.guardarEnArchivo(); // guardado forzado
                JOptionPane.showMessageDialog(this, "Artista eliminado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Artista no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarCanciones() {
        List<Cancion> lista = BaseDatos.obtenerCanciones();
        StringBuilder sb = new StringBuilder();
        if (lista.isEmpty()) sb.append("No hay canciones.\n");
        else {
            for (Cancion c : lista) {
                sb.append(c.toString()).append("\n");
            }
        }
        mostrarEnVentana("Canciones", sb.toString());
    }

    private void crearCancion() {
        // Solicitar título, artista (debe existir), duración
        JTextField txtTitulo = new JTextField();
        JTextField txtArtista = new JTextField();
        JTextField txtDuracion = new JTextField();
        Object[] msg = {
                "Título:", txtTitulo,
                "Artista (nombre exacto):", txtArtista,
                "Duración (ej: 3:15):", txtDuracion
        };
        int option = JOptionPane.showConfirmDialog(this, msg, "Crear canción", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;
        String titulo = txtTitulo.getText().trim();
        String artistaNombre = txtArtista.getText().trim();
        String duracion = txtDuracion.getText().trim();

        if (titulo.isEmpty() || artistaNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título y artista son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // comprobar artista
        if (BaseDatos.buscarArtistaPorNombre(artistaNombre) == null) {
            JOptionPane.showMessageDialog(this, "Artista no existe. Crea el artista primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cancion c = new Cancion(titulo, artistaNombre, duracion);
        boolean ok = BaseDatos.agregarCancion(c);
        if (ok) JOptionPane.showMessageDialog(this, "Canción creada.");
        else JOptionPane.showMessageDialog(this, "Ya existe una canción con ese título.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void buscarCancion() {
        String q = JOptionPane.showInputDialog(this, "Título de la canción a buscar:");
        if (q == null) return;
        Cancion c = BaseDatos.buscarCancionPorTitulo(q.trim());
        if (c == null) JOptionPane.showMessageDialog(this, "Canción no encontrada.", "Info", JOptionPane.INFORMATION_MESSAGE);
        else mostrarEnVentana("Canción: " + c.getTitulo(), c.toString());
    }

    private void eliminarCancion() {
        String q = JOptionPane.showInputDialog(this, "Título de la canción a eliminar:");
        if (q == null) return;
        String titulo = q.trim();
        if (!BaseDatos.canciones.containsKey(titulo)) {
            JOptionPane.showMessageDialog(this, "No existe canción con ese título.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int resp = JOptionPane.showConfirmDialog(this, "Eliminar canción " + titulo + " ? (se eliminará la referencia de listas)", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            BaseDatos.canciones.remove(titulo);
            // también borramos referencias en listas
            for (ListaReproduccion l : BaseDatos.obtenerListas()) {
                l.getTitulosCanciones().remove(titulo);
            }
            BaseDatos.guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "Canción eliminada.");
        }
    }

    private void editarCancion() {
        String q = JOptionPane.showInputDialog(this, "Título de la canción a editar:");
        if (q == null) return;
        Cancion c = BaseDatos.buscarCancionPorTitulo(q.trim());
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Canción no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField txtTitulo = new JTextField(c.getTitulo());
        JTextField txtArtista = new JTextField(c.getArtistaNombre());
        JTextField txtDur = new JTextField(c.getDuracion());
        Object[] msg = {
                "Título:", txtTitulo,
                "Artista:", txtArtista,
                "Duración:", txtDur
        };
        int option = JOptionPane.showConfirmDialog(this, msg, "Editar canción", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;
        String nuevoTitulo = txtTitulo.getText().trim();
        String nuevoArtista = txtArtista.getText().trim();
        String nuevaDur = txtDur.getText().trim();
        // Validar artista
        if (BaseDatos.buscarArtistaPorNombre(nuevoArtista) == null) {
            JOptionPane.showMessageDialog(this, "Artista indicado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si cambia título, hay que actualizar key en mapa y en listas
        if (!nuevoTitulo.equals(c.getTitulo())) {
            if (BaseDatos.canciones.containsKey(nuevoTitulo)) {
                JOptionPane.showMessageDialog(this, "Ya existe otra canción con ese título.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // eliminar antigua y crear nueva entrada
            BaseDatos.canciones.remove(c.getTitulo());
            Cancion nueva = new Cancion(nuevoTitulo, nuevoArtista, nuevaDur);
            BaseDatos.canciones.put(nuevoTitulo, nueva);
            // actualizar referencias en listas
            for (ListaReproduccion l : BaseDatos.obtenerListas()) {
                List<String> titulos = l.getTitulosCanciones();
                for (int i = 0; i < titulos.size(); i++) {
                    if (titulos.get(i).equals(c.getTitulo())) titulos.set(i, nuevoTitulo);
                }
            }
        } else {
            // sólo actualizar datos (reemplazar objeto)
            Cancion nueva = new Cancion(nuevoTitulo, nuevoArtista, nuevaDur);
            BaseDatos.canciones.put(nuevoTitulo, nueva);
        }
        BaseDatos.guardarEnArchivo();
        JOptionPane.showMessageDialog(this, "Canción actualizada.");
    }

    // utilidad: mostrar texto en ventana con scroll
    private void mostrarEnVentana(String titulo, String texto) {
        JTextArea area = new JTextArea(20, 60);
        area.setText(texto);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}

