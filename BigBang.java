import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class BigBang extends JPanel implements KeyListener{
    Tetris game = new Tetris();
    static JFrame window = new JFrame();
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);

    }

    public BigBang(){
        JFrame f = new JFrame();

        f.repaint();
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        game.tick();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000/60);
                        window.repaint();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();
    }

    public static void main(String[] args){
        BigBang a = new BigBang();
        window.setVisible(true);
        window.setTitle("Tetris");
        window.setSize(21*20, 42*20);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.add(a);
        window.addKeyListener(a);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                game.rotateLeft();
                break;
            case KeyEvent.VK_UP:
                game.rotateRight();
                break;
            case KeyEvent.VK_LEFT:
                game.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                game.moveRight();
                break;
            case KeyEvent.VK_SPACE:
                game.tick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}