package tetris2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Instructions extends JPanel {

    JButton back;

    public Instructions() {
        super(new BorderLayout());
        setupCopyright();
        addButtons();
    }

    private void setupCopyright() {
        JPanel copyrightPanel = new JPanel();
        JLabel label = new JLabel("Created by Shang Lin Ye, Li Jing Wu, and Stanley Wong");

        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("Calibri", Font.PLAIN, 17));
        label.setHorizontalAlignment(JLabel.CENTER);
        copyrightPanel.setPreferredSize(new Dimension(690, 40));
        copyrightPanel.setOpaque(false);
        copyrightPanel.add(label);
        this.add(copyrightPanel, BorderLayout.SOUTH);
    }

    private void addButtons() {
        back = new JButton("Back");
        back.setPreferredSize(new Dimension(80, 60));
        back.setForeground(new Color(240, 240, 240));
        back.setBackground(new Color(10, 10, 10));
        back.setFont(new Font("Calibri", Font.PLAIN, 18));
        back.addMouseListener(new MouseHover());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(back, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        g.drawRect((getWidth() / 2) - 25, (getHeight() / 2) - 65, 50, 50);
        g.drawRect((getWidth() / 2) - 25, (getHeight() / 2) - 10, 50, 50);
        g.drawRect((getWidth() / 2) - 80, (getHeight() / 2) - 10, 50, 50);
        g.drawRect((getWidth() / 2) + 30, (getHeight() / 2) - 10, 50, 50);
        g.drawRect((getWidth() / 2) - 100, (getHeight() / 2) + 100, 200, 50);
        g.setFont(new Font("Calibri", Font.PLAIN, 80));
        g.drawString("INSTRUCTIONS", (int) (getWidth() - g.getFontMetrics(g.getFont()).stringWidth("INSTRUCTIONS")) / 2, 100);
        g.setFont(new Font("Calibri", Font.PLAIN, 40));
        g.drawString("W", (getWidth() / 2) - 18, (getHeight() / 2) - 26);
        g.drawString("A", (getWidth() / 2) - 11, (getHeight() / 2) + 29);
        g.drawString("S", (getWidth() / 2) - 66, (getHeight() / 2) + 29);
        g.drawString("D", (getWidth() / 2) + 44, (getHeight() / 2) + 29);
        g.setFont(new Font("Calibri", Font.PLAIN, 30));
        g.drawString("SPACE", (getWidth() / 2) - 38, (getHeight() / 2) + 134);
        g.setFont(new Font("Calibri", Font.PLAIN, 14));
        g.drawString("ROTATE", (getWidth() / 2) - 23, (getHeight() / 2) - 70);
        g.drawString("LEFT", (getWidth() / 2) - 110, (getHeight() / 2) + 21);
        g.drawString("RIGHT", (getWidth() / 2) + 85, (getHeight() / 2) + 20);
        g.drawString("DOWN", (getWidth() / 2) - 19, (getHeight() / 2) + 55);
        g.drawString("INSTA-FALL", (getWidth() / 2) - 30, (getHeight() / 2) + 164);
    }
}
