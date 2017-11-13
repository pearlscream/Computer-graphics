package frames;

import javax.swing.*;

/**
 * Created by Dima on 22.10.2017.
 */
public class CartesianFrame extends JFrame {
    FigureBuilder panel;

    public CartesianFrame() {
        panel = new FigureBuilder();
        add(panel);
    }

    public void showUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Figure");
        setSize(700, 700);
        setVisible(true);
    }
}
