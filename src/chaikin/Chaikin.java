package chaikin;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.util.List;

public class Chaikin extends JPanel {

    private List<Point> points = new ArrayList<>();
    private List<Point> last_points = new ArrayList<>();
    private boolean draw;
    private int steps;
    private boolean err;

    public Chaikin() {
        super.setBackground(Color.BLACK);
        setFocusable(true);

        Timer timer = new Timer(1000, e -> {
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!draw) {
                    points.add(e.getPoint());
                    err = false;
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {
                        if (points.size() >= 2 && !draw) {
                            draw = true;
                            last_points = points;
                            repaint();
                            timer.start();
                            err = false;
                        } else if (points.size() == 0) {
                            err = true;
                            repaint();
                        }
                    }
                    case KeyEvent.VK_DELETE -> {
                        points.clear();
                        last_points.clear();
                        draw = false;
                        steps = 0;
                        err = false;
                        repaint();
                        timer.stop();
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        System.out.println("GOODBYE FROM ..|-_-|.. ");
                        System.exit(0);
                    }
                }
            }
        });

    }

    private List<Point> chaikinAlgo(List<Point> array) {
        int len = array.size() - 1;
        List<Point> new_positions = new ArrayList<>(array.size() * 2);
        new_positions.add(array.get(0));
        for (int i = 0; i < len; i++) {
            Point p1 = array.get(i);
            Point p2 = array.get(i + 1);
            Point start = new Point(
                    (int) ((p1.x * 0.75) + (p2.x * 0.25)),
                    (int) ((p1.y * 0.75) + (p2.y * 0.25)));

            Point end = new Point(
                    (int) ((p1.x * 0.25) + (p2.x * 0.75)),
                    (int) ((p1.y * 0.25) + (p2.y * 0.75)));
            if (i != 0) {
                new_positions.add(start);
            }
            if (i + 1 != len) {
                new_positions.add(end);
            }

        }
        new_positions.add(array.get(array.size() - 1));
        return new_positions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (steps == 7) {
            steps = 0;
            last_points = points;
        }

        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        String INSTRUCTIONS_DOUBLE = String.format("""
                ╔════════════════════════╗
                ║ Instructions:          ║
                ╠════════════════════════╣
                ║ Start Animation: Enter ║
                ║ Reset: Delete          ║
                ║ Exit: Escape           ║
                ║ Step: %d                ║
                ╚════════════════════════╝
                """, steps + 1);
        String[] lines = INSTRUCTIONS_DOUBLE.split("\n");

        for (int i = 1; i <= lines.length; i++) {
            g2d.drawString(lines[i - 1], 20, 15 * i);
        }

        if (err) {
            g2d.setColor(Color.ORANGE);
            String str = "||=> ERROR : pleas draw points before click Enter. <=||";
            g2d.drawString(str, 20, getHeight() - 10);
        }

        g2d.setColor(Color.WHITE);
        for (Point point : points) {
            g2d.drawOval(point.x - 3, point.y - 3, 6, 6);
        }

        if (draw) {
            for (int i = 0; i < last_points.size() - 1; i++) {
                Point p1 = last_points.get(i);
                Point p2 = last_points.get(i + 1);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            if (last_points.size() > 2) {
                steps++;
                last_points = chaikinAlgo(last_points);
            }
        }
    }

}