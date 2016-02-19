package tetris2;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class BlockShape {

    public Block[] blocks = new Block[4];
    int shapeType;
    int x;
    int y;
    int ySpeed;
    boolean passed = false;
    boolean blockPassed = false;
    boolean canLeftMove = false;
    boolean canRightMove = false;
    boolean allowJump = true;
    Image image;
    Color color;

    public BlockShape(int x, int y, int width, int height, int shapeType, int ySpeed, ArrayList<GameObject> objects) {
        this.shapeType = shapeType;
        this.x = x;
        this.y = y;
        this.ySpeed = ySpeed;
        switch (shapeType) {
            case 1:
                color = Color.yellow;
                image = Toolkit.getDefaultToolkit().getImage("blocks/yellow.jpg");
                blocks[0] = new Block(x, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x + 15, y, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x + 15, y + 15, width, height, color, ySpeed, objects, this);
                break;
            case 2:
                color = Color.cyan;
                image = Toolkit.getDefaultToolkit().getImage("blocks/cyan.jpg");
                blocks[0] = new Block(x, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x, y + 30, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x, y + 45, width, height, color, ySpeed, objects, this);
                break;
            case 3:
                color = Color.green;
                image = Toolkit.getDefaultToolkit().getImage("blocks/green.jpg");
                blocks[0] = new Block(x, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x + 15, y + 15, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x + 15, y + 30, width, height, color, ySpeed, objects, this);
                break;
            case 4:
                color = Color.red;
                image = Toolkit.getDefaultToolkit().getImage("blocks/red.jpg");
                blocks[0] = new Block(x + 15, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x + 15, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x, y + 30, width, height, color, ySpeed, objects, this);
                break;
            case 5:
                color = Color.orange;
                image = Toolkit.getDefaultToolkit().getImage("blocks/orange.jpg");
                blocks[0] = new Block(x, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x, y + 30, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x + 15, y + 30, width, height, color, ySpeed, objects, this);
                break;
            case 6:
                color = Color.blue;
                image = Toolkit.getDefaultToolkit().getImage("blocks/blue.jpg");
                blocks[0] = new Block(x + 15, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x + 15, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x + 15, y + 30, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x, y + 30, width, height, color, ySpeed, objects, this);
                break;
            case 7:
                color = Color.magenta;
                image = Toolkit.getDefaultToolkit().getImage("blocks/purple.jpg");
                blocks[0] = new Block(x, y, width, height, color, ySpeed, objects, this);
                blocks[1] = new Block(x, y + 15, width, height, color, ySpeed, objects, this);
                blocks[2] = new Block(x + 15, y + 15, width, height, color, ySpeed, objects, this);
                blocks[3] = new Block(x, y + 30, width, height, color, ySpeed, objects, this);
                break;
        }
    }

    public void flip(int blockType) {
        switch (blockType) {
            case 1:
                break;
            case 2:
                if (blocks[0].x == blocks[1].x) {
                    if (blocks[0].x > 300 && blocks[0].x < 410 && leftTest() && rightTest()) {
                        blocks[0].x -= 15;
                        blocks[2].x += 15;
                        blocks[3].x += 30;
                        blocks[0].y += 15;
                        blocks[2].y -= 15;
                        blocks[3].y -= 30;
                    }

                } else {
                    if (blocks[0].y < 420) {
                        blocks[0].x += 15;
                        blocks[2].x -= 15;
                        blocks[3].x -= 30;
                        blocks[0].y -= 15;
                        blocks[2].y += 15;
                        blocks[3].y += 30;
                    }
                }
                break;
            case 3:
                if (blocks[1].y == blocks[2].y) {
                    if (blocks[0].x >= 300 && blocks[0].x < 410 && leftTest() && rightTest()) {
                        blocks[0].x += 30;
                        blocks[0].y += 15;
                        blocks[1].x += 15;
                        blocks[2].y += 15;
                        blocks[3].x -= 15;
                    }

                } else {
                    blocks[0].x -= 30;
                    blocks[0].y -= 15;
                    blocks[1].x -= 15;
                    blocks[2].y -= 15;
                    blocks[3].x += 15;
                }
                break;
            case 4:
                if (blocks[1].y == blocks[2].y) {
                    if (blocks[0].x > 300 && blocks[0].x < 425 && leftTest() && rightTest()) {
                        blocks[0].x -= 15;
                        blocks[0].y += 15;
                        blocks[2].x += 15;
                        blocks[2].y += 15;
                        blocks[3].x += 30;
                    }
                } else {
                    blocks[0].x += 15;
                    blocks[0].y -= 15;
                    blocks[2].x -= 15;
                    blocks[2].y -= 15;
                    blocks[3].x -= 30;
                }
                break;
            case 5:
                if (blocks[0].y == blocks[2].y - 30 ) {
                    if (blocks[3].x < 425 && rightTest()) {
                        blocks[0].x += 30;
                        blocks[1].x += 15;
                        blocks[1].y -= 15;
                        blocks[2].y -= 30;
                        blocks[3].y -= 15;
                        blocks[3].x -= 15;
                    }
                } else if (blocks[0].x - 30 == blocks[2].x) {
                    blocks[0].y += 30;
                    blocks[1].x += 15;
                    blocks[1].y += 15;
                    blocks[2].x += 30;
                    blocks[3].y -= 15;
                    blocks[3].x += 15;
                } else if (blocks[0].y - 30 == blocks[2].y) {
                    if (blocks[0].x > 320 && leftTest()) {
                        blocks[0].x -= 30;
                        blocks[1].x -= 15;
                        blocks[1].y += 15;
                        blocks[2].y += 30;
                        blocks[3].y += 15;
                        blocks[3].x += 15;
                    }
                } else if (blocks[0].x == blocks[2].x - 30) {
                    blocks[0].y -= 30;
                    blocks[1].x -= 15;
                    blocks[1].y -= 15;
                    blocks[2].x -= 30;
                    blocks[3].y += 15;
                    blocks[3].x -= 15;
                }
                break;
            case 6:
                if (blocks[0].y == blocks[2].y - 30) {
                    if (blocks[0].x < 425 && rightTest()) {
                        blocks[0].x += 15;
                        blocks[0].y += 30;
                        blocks[1].y += 15;
                        blocks[2].x -= 15;
                        blocks[3].y -= 15;
                    }
                } else if (blocks[0].x - 30 == blocks[2].x) {
                    blocks[0].x -= 30;
                    blocks[1].x -= 15;
                    blocks[1].y -= 15;
                    blocks[2].y -= 30;
                    blocks[3].y -= 15;
                    blocks[3].x += 15;
                } else if (blocks[0].y - 30 == blocks[2].y) {
                    if (blocks[3].x < 425 && rightTest()) {
                        blocks[0].y -= 30;
                        blocks[1].x += 15;
                        blocks[1].y -= 15;
                        blocks[2].x += 30;
                        blocks[3].y += 15;
                        blocks[3].x += 15;
                    }
                } else if (blocks[0].x == blocks[2].x - 30) {
                    blocks[0].x += 15;
                    blocks[1].y += 15;
                    blocks[2].y += 30;
                    blocks[2].x -= 15;
                    blocks[3].y += 15;
                    blocks[3].x -= 30;
                }
                break;
            case 7:
                if (blocks[0].y == blocks[3].y - 30) {
                    if (blocks[2].x < 425 && rightTest()) {
                        blocks[0].x += 30;
                        blocks[1].y -= 15;
                        blocks[1].x += 15;
                        blocks[3].y -= 30;
                    }
                } else if (blocks[0].x - 30 == blocks[3].x) {
                    blocks[0].y += 30;
                    blocks[1].x += 15;
                    blocks[1].y += 15;
                    blocks[3].x += 30;
                } else if (blocks[0].y - 30 == blocks[3].y) {
                    if (blocks[0].x > 315 && leftTest()) {
                        blocks[0].x -= 30;
                        blocks[1].y += 15;
                        blocks[1].x -= 15;
                        blocks[3].y += 30;
                    }
                } else if (blocks[0].x == blocks[3].x - 30) {
                    blocks[0].y -= 30;
                    blocks[1].x -= 15;
                    blocks[1].y -= 15;
                    blocks[3].x -= 30;
                }
                break;
        }
    }

    public boolean leftTest() {
        int[] yPosition = new int[4];
        for (int i = 0; i < yPosition.length; i++) {
            yPosition[i] = Math.round((blocks[i].y) / 15);
        }

        for (GameObject stillBlocks : blocks[0].objects) {
            Block temp = (Block) stillBlocks;
            if (temp.ySpeed == 0) {
                for (int i = 0; i < yPosition.length; i++) {
                    if (yPosition[i] == Math.round((temp.y) / 15) && blocks[i].x == temp.x + 15) {
                        return false;
                    }
                }
            }    
        }
        return true;
    }
    
    public boolean rightTest() {
        int[] yPosition = new int[4];
        for (int i = 0; i < yPosition.length; i++) {
            yPosition[i] = Math.round((blocks[i].y) / 15);
        }

        for (GameObject stillBlocks : blocks[0].objects) {
            Block temp = (Block) stillBlocks;
            if (temp.ySpeed == 0) {
                for (int i = 0; i < yPosition.length; i++) {
                    if (yPosition[i] == Math.round((temp.y) / 15) && blocks[i].x == temp.x - 15) {
                        return false;
                    }
                }
            }    
        }
        return true;
    }
}
