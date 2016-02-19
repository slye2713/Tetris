package tetris2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

    JPanel buttonHolder, bottomContainer, copyrightPanel;
    JButton[] buttons = new JButton[3];
    String[] butText = {"Play Game", "Instructions", "Quit Game"};
    boolean fadeInComplete;

    public MainMenu() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(690, 500));
        initialize();
        setupCopyright();
        addButtons();
        add(bottomContainer, BorderLayout.SOUTH);
    }

    private void initialize() {
        buttonHolder = new JPanel(new GridLayout(1, 0, 10, 10));
        bottomContainer = new JPanel(new BorderLayout());
        copyrightPanel = new JPanel();
        fadeInComplete = false;
        
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(butText[i]);
        }
        bottomContainer.setOpaque(false);
        bottomContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void addButtons() {
        buttonHolder.setPreferredSize(new Dimension(400, 60));
        buttonHolder.setOpaque(false);
        for (JButton i : buttons) {
            i.setForeground(new Color(240, 240, 240));
            i.setBackground(new Color(10, 10, 10));
            i.setFont(new Font("Calibri", Font.PLAIN, 18));
            i.addMouseListener(new MouseHover());
            buttonHolder.add(i);
        }
        bottomContainer.add(buttonHolder, BorderLayout.CENTER);
        bottomContainer.add(Box.createRigidArea(new Dimension(75, 50)), BorderLayout.EAST);
        bottomContainer.add(Box.createRigidArea(new Dimension(75, 50)), BorderLayout.WEST);
    }
    
    private void setupCopyright() {
        JLabel label = new JLabel("Created by Shang Lin Ye, Li Jing Wu, and Stanley Wong");
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("Calibri", Font.PLAIN, 17));
        label.setHorizontalAlignment(JLabel.CENTER);
        copyrightPanel.setPreferredSize(new Dimension(690, 40));
        copyrightPanel.setOpaque(false);
        copyrightPanel.add(label);
        bottomContainer.add(copyrightPanel, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        Image image = null;
        
        try {
            image = ImageIO.read(new File("background.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 130));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(220, 220, 220));
        g.setFont(new Font("Calibri", Font.PLAIN, 110));
        g.drawString("TETRIS", (int) (getWidth() - g.getFontMetrics(g.getFont()).stringWidth("TETRIS")) / 2, 190);
    }
}
