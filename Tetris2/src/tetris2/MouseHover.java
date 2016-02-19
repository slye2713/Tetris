package tetris2;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MouseHover extends MouseAdapter implements MouseListener {

    public void mouseEntered(MouseEvent e) {
        ((JButton) e.getSource()).setForeground(new Color(10, 10, 10));
        ((JButton) e.getSource()).setBackground(new Color(240, 240, 240));
    }

    public void mouseExited(MouseEvent e) {
        ((JButton) e.getSource()).setForeground(new Color(240, 240, 240));
        ((JButton) e.getSource()).setBackground(new Color(10, 10, 10));
    }
}
