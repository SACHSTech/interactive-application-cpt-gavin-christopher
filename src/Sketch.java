import processing.core.PApplet;
import java.util.ArrayList;

/**
 * Destroy astroids using lasers from a space ship and track score
 * @author Gavin Wong and Christopher Ho
 */

/**
 * Space Defender - High Score & Penalty Update
 * - Player loses points if meteors hit the ground
 * - Game over if the ship is hit OR if score drops below 0
 * - Tracks the highest score across multiple playthroughs
 */

public class Sketch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    // CORE VARIABLES
    float playerX;
    float playerY;
    float playerSpeed = 5;
    boolean movingLeft = false;
    boolean movingRight = false;

    
    @Override
    public void settings() {
        size(800, 600); 
    }

    @Override
    public void setup() {
        playerX = width / 2f;
        playerY = height - 50;
    }

    @Override
    public void draw() {
        background(0); // Plain black background
        updatePlayer();
        drawEntities();
    }

    /** Additional helper methods below */

    private void updatePlayer() {
        if (movingLeft && playerX > 20) playerX -= playerSpeed;
        if (movingRight && playerX < width - 20) playerX += playerSpeed;
    }
    private void drawEntities() {
        // Draw Player (White Triangle)
        fill(255); 
        triangle(playerX, playerY - 20, playerX - 20, playerY + 20, playerX + 20, playerY + 20);
    }
    public void keyPressed() {
        if (keyCode == LEFT) movingLeft = true;
        if (keyCode == RIGHT) movingRight = true;
    }
    public void keyReleased() {
        if (keyCode == LEFT) movingLeft = false;
        if (keyCode == RIGHT) movingRight = false;
    }
}
