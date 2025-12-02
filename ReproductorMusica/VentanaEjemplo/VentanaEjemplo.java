import javax.swing.*;
import java.awt.*;

public class VentanaEjemplo extends JFrame {

    public VentanaEjemplo() {
        // Configuración básica de la ventana
        setTitle("Ejemplo de JFrame con BorderLayout y GridLayout");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear los paneles
        JPanel panelSuperior = crearPanelSuperior();
        JPanel panelCentral = crearPanelCentral();

        // Agregar los paneles al JFrame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel etiqueta = new JLabel("Ingrese su nombre:");
        JTextField campoTexto = new JTextField(15);
        JButton botonSaludar = new JButton("Saludar");

        botonSaludar.addActionListener(e -> {
            String nombre = campoTexto.getText();
            JOptionPane.showMessageDialog(this, "¡Hola, " + nombre + "!");
        });

        panel.add(etiqueta);
        panel.add(campoTexto);
        panel.add(botonSaludar);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));  // 2 filas, 2 columnas con espacio de 10px

        JLabel etiquetaImagen = new JLabel("Imagen:");

        // Ruta absoluta a la imagen
        ImageIcon icono = new ImageIcon("C:\\Users\\Usuario\\Documents\\VentanaEjemplo\\lionel-messi-futbol_3840x2160_xtrafondos.com.jpg");

        // Verificar si la imagen se carga correctamente
        if (icono.getIconWidth() == -1) {
            System.out.println("¡Error al cargar la imagen!");
        } else {
            System.out.println("Imagen cargada correctamente.");
        }

        // Redimensionar la imagen si es demasiado grande
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Cambia el tamaño
        icono = new ImageIcon(imagenEscalada);
        JLabel imagen = new JLabel(icono);  // Crea un JLabel con la imagen

        JLabel etiquetaLista = new JLabel("Selecciona tu color favorito:");
        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo"};
        JComboBox<String> listaColores = new JComboBox<>(colores);

        // Acción para cambiar el color de fondo del panel según el color seleccionado
        listaColores.addActionListener(e -> {
            String colorSeleccionado = (String) listaColores.getSelectedItem();  // Obtiene el color seleccionado
            switch (colorSeleccionado) {
                case "Rojo":
                    panel.setBackground(Color.RED);  // Cambia el fondo a rojo
                    break;
                case "Verde":
                    panel.setBackground(Color.GREEN);  // Cambia el fondo a verde
                    break;
                case "Azul":
                    panel.setBackground(Color.BLUE);  // Cambia el fondo a azul
                    break;
                case "Amarillo":
                    panel.setBackground(Color.YELLOW);  // Cambia el fondo a amarillo
                    break;
            }
        });

        // Agregar los componentes al panel
        panel.add(etiquetaImagen);
        panel.add(imagen);  // Agrega la imagen
        panel.add(etiquetaLista);
        panel.add(listaColores);

        return panel;  // Retorna el panel con los componentes
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaEjemplo::new);  // Crear y mostrar la ventana
    }
}
