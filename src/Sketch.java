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
    int highScore = 0;
    boolean isGameOver = false;
    float playerX;
    float playerY;
    float playerSpeed = 6;
    boolean movingLeft = false;
    boolean movingRight = false;
    
    ArrayList<Float> meteorX;
    ArrayList<Float> meteorY;
    ArrayList<Integer> meteorHP;
    ArrayList<Integer> meteorMaxHP;
    ArrayList<Float> meteorSize;
    ArrayList<Float> meteorSpeed;

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

        meteorHP = new ArrayList<Integer>();
        meteorMaxHP = new ArrayList<Integer>();
        meteorSize = new ArrayList<Float>();
        meteorSpeed = new ArrayList<Float>();
    }

    @Override
    public void draw() {
        background(0); // Plain black background

        if (isGameOver) {
            fill(255, 0, 0);
            textSize(50);
            textAlign(CENTER, CENTER);
            text("GAME OVER\nScore: " + score + "\nHigh Score: " + highScore + "\nPress R to Restart", width / 2f, height / 2f);
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
        text("High Score: " + highScore, 20, 60);

        textSize(14);
        fill(255, 255, 255, 150); 
        textAlign(LEFT, BASELINE);
        text("Controls: LEFT/RIGHT Arrows to move  |  SPACEBAR to shoot", 20, height - 10);

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
            float currentX = meteorX.get(i);
            float speed = meteorSpeed.get(i);
            
            currentY += speed; 
            meteorY.set(i, currentY); 

            int hp = meteorHP.get(i);
            float size = meteorSize.get(i);

            if (hp == 3) {
                fill(180, 60, 60);
            }else if (hp == 2){
                fill(180, 130, 60); 
            }else{
                fill(120); 
            }                  
            
            circle(currentX, currentY, size);
            
            fill(0, 0, 0, 60); 
            circle(currentX - size/4, currentY - size/4, size/3f);
            circle(currentX + size/5, currentY + size/5, size/4f);

            // PENALTY LOGIC: Meteor hits the ground
            if (currentY > height + (size / 2)) {
                // Lose 10 points for every tier of HP the meteor had
                score -= (meteorMaxHP.get(i) * 10); 
                removeMeteor(i);
                if (score > highScore){
                    highScore = score;
                }
                // Check if score dropped below 0
                if (score < 0) {
                    score = 0;
                    isGameOver = true;
                }
            }
        }
    }

    private void updateLasers() {
        for (int i = laserX.size() - 1; i >= 0; i--) {
            float currentY = laserY.get(i);
            currentY -= 10; // Move up
            laserY.set(i, currentY); 

            fill(0, 255, 0);
            rect(laserX.get(i) - 2, currentY, 4, 15, 5); 

            if (currentY < 0) {
                removeLaser(i);
            }

        }
    }

    private void spawnMeteors() {
        if (random(100) < 2) { // 2% chance to spawn per frame
            meteorX.add(random(30, width - 30));
            meteorY.add(-50f);

            int randomHP = (int) random(1, 4);
            meteorHP.add(randomHP);
            meteorMaxHP.add(randomHP);
            
            if (randomHP == 3) {
                meteorSize.add(90f);
                meteorSpeed.add(1.5f);
            } else if (randomHP == 2) {
                meteorSize.add(55f);
                meteorSpeed.add(2.5f);
            } else {
                meteorSize.add(35f);
                meteorSpeed.add(4f);
            }
        }
    }

    private void removeLaser(int index) {
        laserX.remove(index);
        laserY.remove(index);
    }

    private void removeMeteor(int index) {
        meteorX.remove(index);
        meteorY.remove(index);
        meteorHP.remove(index);
        meteorMaxHP.remove(index);
        meteorSize.remove(index);
        meteorSpeed.remove(index);
    }

    private void checkCollisions() {
        for (int j = meteorX.size() - 1; j >= 0; j--) {
            float mX = meteorX.get(j);
            float mY = meteorY.get(j);
            float mSize = meteorSize.get(j);

            // 1. Check if meteor hit PLAYER
            float distToPlayer = dist(playerX, playerY, mX, mY);
            if (distToPlayer < (mSize / 2f + 15)) { 
                isGameOver = true;
            }

            // 2. Check if LASER hit meteor
            for (int i = laserX.size() - 1; i >= 0; i--) {
                float lX = laserX.get(i);
                float lY = laserY.get(i);

                float distance = dist(lX, lY, mX, mY);

                if (distance < mSize / 2f) { 
                    int currentHP = meteorHP.get(j);
                    meteorHP.set(j, currentHP - 1); 
                    removeLaser(i); 

                    if (meteorHP.get(j) <= 0) {
                        score += (meteorMaxHP.get(j) * 10); 
                    removeMeteor(j);
                        if (score > highScore){
                            highScore = score;
                        }
                    }
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
            meteorHP.clear(); meteorMaxHP.clear();
            meteorSize.clear(); meteorSpeed.clear();

        }

    }
    public void keyReleased() {
        if (keyCode == LEFT) movingLeft = false;
        if (keyCode == RIGHT) movingRight = false;
    }
}