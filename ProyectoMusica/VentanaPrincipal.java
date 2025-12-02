import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Ventana Principal");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnArtista = new JButton("Agregar artista");
        JButton btnCancion = new JButton("Agregar canción");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnBuscar = new JButton("Buscar");

        add(btnArtista);
        add(btnCancion);
        add(btnConsultar);
        add(btnBuscar);

        btnArtista.addActionListener(e -> new VentanaAgregarArtista());
        btnCancion.addActionListener(e -> new VentanaAgregarCancion());
        btnConsultar.addActionListener(e -> new VentanaConsultar());
        btnBuscar.addActionListener(e -> new VentanaBusqueda());

        setVisible(true);
        setLocationRelativeTo(null);
    }
}



