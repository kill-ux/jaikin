package chaikin;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.util.List;

public class Chaikin extends JPanel {

    private List<Point> points = new ArrayList<>();
    private List<Point> last_points = new ArrayList<>();
    private boolean draw = false;
    private int steps = 0;

    public Chaikin() {
        super.setBackground(Color.BLACK);
        setFocusable(true);

        Timer timer = new Timer(1000, e -> {
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());
                points.add(e.getPoint());
                System.out.println(points);
                repaint();

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {
                        System.out.println("Hello from Enter");
                        if (points.size() >= 2 && !draw) {
                            draw = true;
                            last_points = points;
                            repaint();
                            timer.start();
                        }
                    }
                    case KeyEvent.VK_DELETE -> {
                        points.clear();
                        last_points.clear();
                        draw = false;
                        steps = 0;
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
            new_positions.add(start);
            new_positions.add(end);
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
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(Color.YELLOW);

        for (Point point : points) {
            g2d.drawOval(point.x - 3, point.y - 3, 6, 6);
        }

        if (draw) {

            if (steps == 7) {
                steps = 0;
                last_points = points;
            }

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