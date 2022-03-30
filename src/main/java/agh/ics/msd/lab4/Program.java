package agh.ics.msd.lab4;
import javax.swing.*;

public class Program extends JFrame {

    private static final long serialVersionUID = 1L;
    private GUI gof;

    public Program() {
        setTitle("Sound simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gof = new GUI(this);
        gof.initialize(this.getContentPane());
        this.setSize(Config.SIZE_X, Config.SIZE_Y);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Program();
    }
}
