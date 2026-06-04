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
    int score = 0;
    boolean isGameOver = false;
    float playerX;
    float playerY;
    float playerSpeed = 5;
    boolean movingLeft = false;
    boolean movingRight = false;
    
    ArrayList<Float> meteorX;
    ArrayList<Float> meteorY;

    ArrayList<Float> laserX;
    ArrayList<Float> laserY;

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

        laserX = new ArrayList<Float>();
        laserY = new ArrayList<Float>();
    }

    @Override
    public void draw() {
        background(0); // Plain black background

        if (isGameOver) {
            fill(255, 0, 0);
            textSize(50);
            textAlign(CENTER, CENTER);
            text("GAME OVER\nscore: " + score + "\nPress R to Restart", width / 2f, height / 2f);
            return; // Stop running the rest of the game
        }

        spawnMeteors();
        updatePlayer();
        updateMeteors();
        updateLasers();
        checkCollisions();

        drawEntities();
        
        // Draw basic score
        fill(255);
        textSize(20);
        textAlign(LEFT, BASELINE);
        text("Score: " + score, 20, 30);
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

    private void updateLasers() {
        for (int i = laserX.size() - 1; i >= 0; i--) {
            float currentY = laserY.get(i);
            currentY -= 10; // Move up
            laserY.set(i, currentY); 

            if (currentY < 0) {
                laserX.remove(i);
                laserY.remove(i);
            }
        }
    }

    private void spawnMeteors() {
        if (random(100) < 2) { // 2% chance to spawn per frame
            meteorX.add(random(30, width - 30));
            meteorY.add(-50f);
        }
    }
    private void checkCollisions() {
        for (int j = meteorX.size() - 1; j >= 0; j--) {
            float mX = meteorX.get(j);
            float mY = meteorY.get(j);
            float mSize = 40;

            // 1. Check if meteor hit PLAYER
            if (dist(playerX, playerY, mX, mY) < 20) {
                isGameOver = true;
            }
            // 2. Check if LASER hit meteor
            for (int i = laserX.size() - 1; i >= 0; i--) {
                float lX = laserX.get(i);
                float lY = laserY.get(i);

                if (dist(lX, lY, mX, mY) < mSize / 2f) { 
                    score += 10; // 10 points per hit
                    
                    // Remove both the laser and the meteor
                    laserX.remove(i);
                    laserY.remove(i);
                    meteorX.remove(j);
                    meteorY.remove(j);
                    break; 
                }
            }
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

        // Draw Lasers (Green Rectangles)
        fill(0, 255, 0);
        for (int i = 0; i < laserX.size(); i++) {
            rect(laserX.get(i) - 2, laserY.get(i), 4, 15);
        }
    }

    //Input Methods

    public void keyPressed() {
        if (keyCode == LEFT) {
            movingLeft = true;
        }
        if (keyCode == RIGHT) {
            movingRight = true;
        }
        if (key == ' ') {
            laserX.add(playerX);
            laserY.add(playerY - 20); 
        }

        if (isGameOver && (key == 'r' || key == 'R')) {
            isGameOver = false;
            score = 0; 
            playerX = width / 2f;
            
            laserX.clear(); laserY.clear();
            meteorX.clear(); meteorY.clear();
        }

    }
    public void keyReleased() {
        if (keyCode == LEFT) movingLeft = false;
        if (keyCode == RIGHT) movingRight = false;
    }
}