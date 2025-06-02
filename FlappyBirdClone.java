import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBirdClone extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    int birdY = 250, velocity = 0, gravity = 1, score = 0;
    ArrayList<Rectangle> pipes = new ArrayList<>();
    Random rand = new Random();

    public FlappyBirdClone() {
        JFrame frame = new JFrame("Flappy Bird Java");
        frame.setSize(400, 600);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);
        timer = new Timer(20, this);
        timer.start();
        addPipe();
        addPipe();
    }

    void addPipe() {
        int height = 100 + rand.nextInt(200);
        pipes.add(new Rectangle(400, 0, 50, height)); // Top
        pipes.add(new Rectangle(400, height + 150, 50, 600)); // Bottom
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 400, 600);
        g.setColor(Color.orange);
        g.fillRect(0, 500, 400, 100);
        g.setColor(Color.green);
        g.fillRect(0, 500, 400, 20);
        g.setColor(Color.red);
        g.fillOval(100, birdY, 20, 20);
        g.setColor(Color.green);
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 30);
    }

    public void actionPerformed(ActionEvent e) {
        velocity += gravity;
        birdY += velocity;

        for (int i = 0; i < pipes.size(); i++) {
            Rectangle pipe = pipes.get(i);
            pipe.x -= 5;

            if (pipe.x + pipe.width < 0) {
                pipes.remove(pipe);
                if (pipe.y == 0) {
                    addPipe();
                    score++;
                }
            }

            if (pipe.intersects(new Rectangle(100, birdY, 20, 20))) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }

        if (birdY > 480 || birdY < 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
            System.exit(0);
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocity = -10;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new FlappyBirdClone();
    }
}
