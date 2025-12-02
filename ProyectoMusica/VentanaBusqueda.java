import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaBusqueda extends JFrame {

    public VentanaBusqueda() {
        setTitle("Buscar");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JPanel arriba = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        arriba.add(txtBuscar);
        arriba.add(btnBuscar);

        JTextArea resultados = new JTextArea();
        resultados.setEditable(false);

        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(resultados), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String texto = txtBuscar.getText();
            ArrayList<String> lista = BaseDeDatos.buscar(texto);

            resultados.setText("");

            if (lista.isEmpty()) {
                resultados.setText("No se encontraron resultados");
                return;
            }

            for (String item : lista) {
                resultados.append(item + "\n");
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
}
