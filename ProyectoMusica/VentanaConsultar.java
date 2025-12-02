import javax.swing.*;
import java.awt.*;

public class VentanaConsultar extends JFrame {

    public VentanaConsultar() {
        setTitle("Consulta General");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder texto = new StringBuilder();

        texto.append("=== ARTISTAS ===\n");
        for (String a : BaseDeDatos.artistas) {
            texto.append(a).append("\n");
        }

        texto.append("\n=== CANCIONES ===\n");
        for (String c : BaseDeDatos.canciones) {
            texto.append(c).append("\n");
        }

        area.setText(texto.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
    }
}




