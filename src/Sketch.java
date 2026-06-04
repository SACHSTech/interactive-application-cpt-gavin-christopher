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

    ArrayList<Float> meteorX;
    ArrayList<Float> meteorY;

    @Override
    public void settings() {
        size(800, 600); 
    }

    @Override
    public void setup() {
        playerX = width / 2f;
        playerY = height - 50;

        meteorX = new ArrayList<Float>();
        meteorY = new ArrayList<Float>();

    }

    @Override
    public void draw() {
        background(0); // Plain black background
        spawnMeteors();
        updatePlayer();
        updateMeteors();
        
        drawEntities();
        
    }

    //Core Mechanics (Update Methods)

    private void updatePlayer() {
        if (movingLeft && playerX > 20) {
            playerX -= playerSpeed;
        }
        if (movingRight && playerX < width - 20) {
            playerX += playerSpeed;
        }
    }

    private void updateMeteors() {
        for (int i = meteorX.size() - 1; i >= 0; i--) {
            float currentY = meteorY.get(i);
            currentY += 3; // Move down (constant speed)
            meteorY.set(i, currentY); 

            // Despawn if it falls off the screen
            if (currentY > height + 50) {
                meteorX.remove(i);
                meteorY.remove(i);
            }
        }
    }

    private void spawnMeteors() {
        if (random(100) < 2) { // 2% chance to spawn per frame
            meteorX.add(random(30, width - 30));
            meteorY.add(-50f);
        }
    }

    //Basic Drawing

    private void drawEntities() {
        // Draw Player (White Triangle)
        fill(255); 
        triangle(playerX, playerY - 20, playerX - 20, playerY + 20, playerX + 20, playerY + 20);
        
        // Draw Meteors (Red Circles)
        fill(255, 50, 50);
        for (int i = 0; i < meteorX.size(); i++) {
            circle(meteorX.get(i), meteorY.get(i), 40);
        }

    }

    //Input Methods

    public void keyPressed() {
        if (keyCode == LEFT) movingLeft = true;
        if (keyCode == RIGHT) movingRight = true;
    }
    public void keyReleased() {
        if (keyCode == LEFT) movingLeft = false;
        if (keyCode == RIGHT) movingRight = false;
    }
}
