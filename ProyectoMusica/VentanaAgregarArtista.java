import javax.swing.*;
import java.awt.*;

public class VentanaAgregarArtista extends JFrame {

    public VentanaAgregarArtista() {
        setTitle("Agregar Artista");
        setSize(350, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblGenero = new JLabel("Género:");
        JTextField txtGenero = new JTextField();

        JLabel lblPais = new JLabel("País:");
        JTextField txtPais = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        add(lblNombre); add(txtNombre);
        add(lblGenero); add(txtGenero);
        add(lblPais); add(txtPais);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            BaseDeDatos.agregarArtista(
                txtNombre.getText(),
                txtGenero.getText(),
                txtPais.getText()
            );

            JOptionPane.showMessageDialog(this, "Artista guardado.");
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
}




