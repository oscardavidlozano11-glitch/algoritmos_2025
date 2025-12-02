import javax.swing.*;
import java.awt.*;

public class VentanaAgregarCancion extends JFrame {

    public VentanaAgregarCancion() {
        setTitle("Agregar Canción");
        setSize(350, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();

        JLabel lblArtista = new JLabel("Artista:");
        JTextField txtArtista = new JTextField();

        JLabel lblDuracion = new JLabel("Duración:");
        JTextField txtDuracion = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        add(lblTitulo); add(txtTitulo);
        add(lblArtista); add(txtArtista);
        add(lblDuracion); add(txtDuracion);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            BaseDeDatos.agregarCancion(
                txtTitulo.getText(),
                txtArtista.getText(),
                txtDuracion.getText()
            );

            JOptionPane.showMessageDialog(this, "Canción guardada.");
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
}



