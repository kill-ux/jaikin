import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chaikin.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smooth Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null); // Center window
            frame.add(new Chaikin());
            frame.setVisible(true);
        });
    }
}
