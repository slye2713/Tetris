package tetris2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jaco.mp3.player.MP3Player;

public class ControlPanel extends JPanel implements Runnable, KeyListener {

    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private boolean run = false;
    private Block block = null;
    private BlockShape blockShape;
    private BlockShape temp;
    public int spec;
    private int shape;
    private BufferedImage image;
    private JLabel label;
    private Font font, font2;
    public int score = 0;
    public int countLines = 0;
    public int tempShape;
    int level = 1;
    private boolean gameOn = true;
    public int keyCodeEnd;
    private boolean pausing = false;

    public ControlPanel() {
        MP3Player player = new MP3Player();
        player.addToPlayList(new File("ariamusic.mp3"));
        player.setRepeat(true);
        player.setShuffle(true);
        player.play();
        
        Random random = new Random();
        shape = random.nextInt(7) + 1;
        tempShape = random.nextInt(7) + 1;
        label = new JLabel("hi");
        font = new Font("Plain", 33, 33);
        font2 = new Font("Plain", 15, 15);

        blockShape = new BlockShape(300, 0, 15, 15, shape, 1, objects);
        block = blockShape.blocks[0];
        objects.add(blockShape.blocks[0]);
        objects.add(blockShape.blocks[1]);
        objects.add(blockShape.blocks[2]);
        objects.add(blockShape.blocks[3]);
        temp = new BlockShape(560, 165, 15, 15, tempShape, 0, objects);
        objects.add(temp.blocks[0]);
        objects.add(temp.blocks[1]);
        objects.add(temp.blocks[2]);
        objects.add(temp.blocks[3]);

        this.addKeyListener(this);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0, true),
                "showKey");
        this.getActionMap().put("showKey",
                showKey);

        try {
            image = ImageIO.read(new File("grid.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setPreferredSize(new Dimension(690, 500));
        setBackground(Color.white);
        setFocusable(true);
    }

    public void start() {
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    public void stop() {
        run = false;
    }

    public void run() {
        Random random = new Random();
        if (gameOn) {
            while (run) {
                // Check for collision, draw objects and sleep
                for (GameObject i : objects) {
                    i.update(this);
                }
                if (block.ySpeed == 0.0) {
                    int filledLn = 0;
                    countLines = 0;
                    while ((filledLn = check()) > 0) {
                        destroy(filledLn, countLines);
                        countLines++;
                    }
                    if (gameOn) {
                        countLines = 0;
                        blockShape = new BlockShape(300, 0, 15, 15, tempShape, 1, objects);
                        block = blockShape.blocks[0];
                        objects.add(blockShape.blocks[0]);
                        objects.add(blockShape.blocks[1]);
                        objects.add(blockShape.blocks[2]);
                        objects.add(blockShape.blocks[3]);

                        for (int i = 0; i < 4; i++) {
                            objects.remove(temp.blocks[i]);
                        }
                        tempShape = random.nextInt(7) + 1;
                        temp = new BlockShape(560, 165, 15, 15, tempShape, 0, objects);
                        objects.add(temp.blocks[0]);
                        objects.add(temp.blocks[1]);
                        objects.add(temp.blocks[2]);
                        objects.add(temp.blocks[3]);
                    }
                }
                repaint();
                try {
                    int sleep = 64 / (int) (Math.pow(2, level - 1));
                    Thread.sleep(sleep);
                } catch (Exception e) {
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, this);
        g2.setFont(font);
        String text = "0000000";
        String temp = "" + score;

        if (temp.length() == 1) {
            text = "000000" + score;
            level = 1;
        } else if (temp.length() == 2) {
            text = "00000" + score;
            level = 1;
        } else if (temp.length() == 3) {
            text = "0000" + score;
            level = 2;
        } else if (temp.length() == 4) {
            text = "000" + score;
            level = 3;
        } else if (temp.length() == 5) {
            text = "00" + score;
            level = 4;
        } else if (temp.length() == 6) {
            text = "0" + score;
            level = 5;
        } else if (temp.length() == 7) {
            text = "" + score;
            level = 6;
        }
        String otherText = "Level: " + level;
        g2.drawString(text, 515, 75);
        g2.setFont(font2);
        g2.drawString(otherText, 550, 255);

        for (GameObject i : objects) {
            i.paintComponent(g2);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ControlPanel control = new ControlPanel();
        final Instructions instruct = new Instructions();
        final MainMenu mainMenu = new MainMenu();
        final JFrame frame = new JFrame("Tetris");

        frame.setContentPane(mainMenu);
        //control.start();

        for (int i = 0; i < mainMenu.buttons.length; i++) {
            mainMenu.buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (((JButton) e.getSource()).getText().equals("Play Game")) {
                        JFrame gameView = new JFrame();
                        gameView.setContentPane(control);
                        gameView.setSize(690, 500);
                        gameView.setResizable(false);
                        gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameView.setVisible(true);
                        control.start();
                        frame.dispose();
                    } else if (((JButton) e.getSource()).getText().equals("Instructions")) {
                        JFrame gameView = new JFrame();
                        gameView.setContentPane(instruct);
                        instruct.back.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JFrame gameView = new JFrame();
                                gameView.setContentPane(mainMenu);
                                gameView.setSize(690, 500);
                                gameView.setResizable(false);
                                gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                gameView.setVisible(true);
                                frame.dispose();
                            }
                        });
                        gameView.setSize(690, 500);
                        gameView.setResizable(false);
                        gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameView.setVisible(true);
                        frame.dispose();
                    } else if (((JButton) e.getSource()).getText().equals("Quit Game")) {
                        System.exit(0);
                    }
                }
            });
        }

        frame.setSize(690, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (gameOn) {
            block.keyCode = e.getKeyCode();
        } else {
            keyCodeEnd = e.getKeyCode();
            restartOptions();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (gameOn) {
            block.keyCode2 = e.getKeyCode();
        }
    }

    Action showKey = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {

        }
    };

    public int check() {
        int[] filled = new int[30];
        for (GameObject object : objects) {
            Block block = (Block) object;
            int lineNumber = Math.round((block.y) / 15);
            if (lineNumber < 0) {
                gameOver();
                break;
            }
            if (block.x < 500) {
                filled[lineNumber]++;
            }
            if (filled[lineNumber] == 10) {
                return lineNumber;
            }
        }
        return 0;
    }

    public void destroy(int line, int countLines) {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            Block block = (Block) it.next();
            int temp = Math.round((block.y) / 15);
            if (temp == line) {
                it.remove();
                if (countLines == 0) {
                    score += (4 * level);
                } else if (countLines == 1) {
                    score += (6 * level);
                } else if (countLines == 2) {
                    score += (20 * level);
                } else if (countLines == 3) {
                    score += (90 * level);
                }
            }
        }

        for (GameObject object : objects) {
            Block block = (Block) object;
            int lineNumber = Math.round((block.y) / 15);
            if (lineNumber < line) {
                block.y += 15;
            }
        }
    }

    public void gameOver() {
        gameOn = false;
        for (GameObject object : objects) {
            Block block = (Block) object;
            block.stillPlaying = false;
            if (block.x < 500) {
                level = 1;
                block.ySpeed = 10;
            }
        }
        try {
            image = ImageIO.read(new File("gridgameover.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        restartOptions();
    }

    public void restartOptions() {
        if (keyCodeEnd == KeyEvent.VK_R) {
            ControlPanel control = new ControlPanel();
            JFrame temp = new JFrame();
            temp.setContentPane(control);
            temp.setVisible(true);
            temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temp.setSize(690, 500);
            control.start();
        } else if (keyCodeEnd == KeyEvent.VK_E) {
            System.exit(0);
        }
    }
}
