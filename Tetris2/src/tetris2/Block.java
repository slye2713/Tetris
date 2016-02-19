package tetris2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.util.ArrayList;

public class Block extends GameObject {

    protected double ySpeed;
    private Color color;
    private Rectangle2D rectangle;
    public int keyCode;
    public int keyCode2;
    protected ArrayList<GameObject> objects;
    private BlockShape blockShape;
    protected boolean stillPlaying = true;

    public Block(int x, int y, int width, int height, Color color, int ySpeed, ArrayList<GameObject> objects, BlockShape blockShape) {
        super(x, y, width, height, color);
        this.color = color;
        this.ySpeed = ySpeed;
        rectangle = new Rectangle2D.Double(x, y, width, height);
        this.objects = objects;
        this.blockShape = blockShape;
    }

    public Rectangle2D getPlatform() {
        return rectangle;
    }

    public void update(ControlPanel panel) {
        if (y + 15 >= 452 && stillPlaying) {
            int yChange = 0;
            if (!blockShape.passed) {
                yChange = y - 435;
                blockShape.passed = true;
            }
            for (int i = 0; i <= 3; i++) {
                this.blockShape.blocks[i].ySpeed = 0;
                //this.blockShape.blocks[i].y -= yChange;
                int temp = this.blockShape.blocks[i].y - yChange;
                int round = (Math.round(temp / 15)) * 15;
                this.blockShape.blocks[i].y = round;
            }
        } else {
            y += ySpeed;
        }
    }

    public void paintComponent(Graphics2D g2) {
        detect();
        if (keyCode != 0) {
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if (detect3() && ySpeed != 0) {
                    if (canLeft()) {
                        this.setX(this.getX() - 15);
                        for (int i = 1; i <= 3; i++) {
                            this.blockShape.blocks[i].setX(this.blockShape.blocks[i].getX() - 15);
                        }
                    }
                }
                keyCode = -1;
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if (detect4() && ySpeed != 0) {
                    if (canRight()) {
                        this.setX(this.getX() + 15);
                        for (int i = 1; i <= 3; i++) {
                            this.blockShape.blocks[i].setX(this.blockShape.blocks[i].getX() + 15);
                        }
                    }
                }
                keyCode = -1;
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if (this.blockShape.allowJump) {
                    detect2();
                }
                if (ySpeed != 0) {
                    if (blockShape.allowJump && groupJump()) {
                        y += 15;
                        for (int i = 1; i <= 3; i++) {
                            this.blockShape.blocks[i].y += 15;
                        }
                    }
                }
                keyCode = -1;
            }
        }

        if (keyCode2 != 0) {
            if (keyCode2 == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                blockShape.flip(blockShape.shapeType);
                keyCode2 = 0;
            } else if (keyCode2 == KeyEvent.VK_SPACE) {
                instantDrop();
                keyCode2 = 0;
            } 
        }
        rectangle.setFrame(x, y, width, height);
        g2.setColor(color);
        g2.fill(rectangle);
        Image img = blockShape.image;
        g2.drawImage(img, x, y, null);

        g2.draw(rectangle);

    }

    public void collision(GameObject two) {
        Block twoBlock = (Block) two;
        int yChange = 0;
        if (this.y + 15 > twoBlock.y && this.y < twoBlock.y + 15 && this.x == twoBlock.x) {
            this.ySpeed = 0;
            if (!blockShape.blockPassed) {
                yChange = y - (twoBlock.y - 15);
                blockShape.passed = true;
            }
            for (int i = 0; i <= 3; i++) {
                this.blockShape.blocks[i].ySpeed = 0;
                this.blockShape.blocks[i].y -= yChange;
            }
        }

    }

    public boolean canJump(GameObject two) {
        Block twoBlock = (Block) two;
        for (int i = 0; i < 4; i++) {
            Block temp = blockShape.blocks[i];
            if (temp.y >= twoBlock.y - 30 && temp.x == twoBlock.x) {
                return false;
            }
        }
        return true;

    }

    public boolean leftMove(GameObject two) {
        Block twoBlock = (Block) two;
        if (this.x - 15 == two.x && this.y > two.y - 15 && this.y < two.y + 15) {
            return false;
        } else {
            boolean isPossible = true;
            for (int i = 1; i <= 3; i++) {

                Block temp = this.blockShape.blocks[i];
                if (temp.x - 15 == two.x && temp.y > two.y - 15 && temp.y < two.y + 15) {
                    isPossible = false;
                }
            }
            return isPossible;
        }
    }

    public boolean rightMove(GameObject two) {
        Block twoBlock = (Block) two;
        if (this.x + 15 == two.x && this.y > two.y - 15 && this.y < two.y + 15) {
            return false;
        } else {
            boolean isPossible = true;
            for (int i = 1; i <= 3; i++) {
                Block temp = this.blockShape.blocks[i];
                if (temp.x + 15 == two.x && temp.y > two.y - 15 && temp.y < two.y + 15) {
                    isPossible = false;
                }
            }
            return isPossible;
        }
    }

    public void detect() {
        if (ySpeed > 0) {
            for (GameObject stillBlocks : objects) {
                Block temp = (Block) stillBlocks;
                if (!this.equals(temp) && temp.ySpeed == 0 && !this.blockShape.equals(temp.blockShape)) {
                    collision(stillBlocks);
                }
            }
        }
    }

    public void detect2() {
        if (ySpeed > 0) {
            for (GameObject stillBlocks : objects) {
                Block temp = (Block) stillBlocks;
                if (!this.equals(temp) && temp.ySpeed == 0 && !this.blockShape.equals(temp.blockShape)) {
                    if (!canJump(stillBlocks)) {
                        this.blockShape.allowJump = canJump(stillBlocks);
                    }
                }
            }
        }
    }

    public boolean detect3() {
        boolean allowed = true;
        if (ySpeed > 0) {
            for (GameObject stillBlocks : objects) {
                Block temp = (Block) stillBlocks;
                if (!this.equals(temp) && temp.ySpeed == 0 && !this.blockShape.equals(temp.blockShape)) {
                    if (!leftMove(stillBlocks)) {
                        allowed = leftMove(stillBlocks);
                    }
                }
            }
        }
        return allowed;
    }

    public boolean detect4() {
        boolean allowed = true;
        if (ySpeed > 0) {
            for (GameObject stillBlocks : objects) {
                Block temp = (Block) stillBlocks;
                if (!this.equals(temp) && temp.ySpeed == 0 && !this.blockShape.equals(temp.blockShape)) {
                    if (!rightMove(stillBlocks)) {
                        allowed = rightMove(stillBlocks);
                    }
                }
            }
        }
        return allowed;
    }

    public boolean canLeft() {
        boolean move = true;
        for (int i = 0; i <= 3; i++) {
            Block temp = this.blockShape.blocks[i];
            if (temp.x == 300) {
                move = false;
            }
        }
        return move;
    }

    public boolean canRight() {
        boolean move = true;
        for (int i = 0; i <= 3; i++) {
            Block temp = this.blockShape.blocks[i];
            if (temp.x >= 435) {
                move = false;
            }
        }
        return move;
    }

    public boolean groupJump() {
        boolean jump = true;
        for (int i = 0; i <= 3; i++) {
            Block temp = this.blockShape.blocks[i];
            if (temp.y >= 420) {
                jump = false;
            }
        }
        return jump;
    }

    public void instantDrop() {
        int[] currentPosition = new int[4];
        for (int i = 0; i < 4; i++) {
            currentPosition[i] = blockShape.blocks[i].x;
        }
        int position = -1;
        int highestBlock = 10000;
        if (ySpeed > 0) {
            for (GameObject stillBlocks : objects) {
                Block temp = (Block) stillBlocks;
                if (temp.x < 500 && temp.ySpeed == 0) {
                    for (int i = 0; i < 4; i++) {
                        if (temp.x == currentPosition[i]) {
                            if (temp.y - blockShape.blocks[i].y < highestBlock && temp.y > blockShape.blocks[i].y) {
                                highestBlock = temp.y - blockShape.blocks[i].y;
                                position = i;
                            }
                        }
                    }
                }
            }
        }
        if (position != -1) {
            for (int i = 0; i < 4; i++) {
                this.blockShape.blocks[i].ySpeed = 0;
                this.blockShape.blocks[i].y += highestBlock - 15;
            }
        } else {
            int lowest = -1;
            for (int i = 0; i < 4; i++) {
                if (blockShape.blocks[i].y > lowest) {
                    lowest = blockShape.blocks[i].y;
                }
            }
            int yDrop = 435 - lowest;
            for (int i = 0; i < 4; i++) {
                this.blockShape.blocks[i].ySpeed = 0;
                this.blockShape.blocks[i].y += yDrop;
            }
        }
    }
}
