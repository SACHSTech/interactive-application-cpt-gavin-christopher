[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wPuP5asc)
# ICS3U CPT – Interactive Processing Project

This repository contains your ICS3U Culminating Performance Task (CPT).

<img width="800" height="595" alt="Image" src="https://github.com/user-attachments/assets/96ba781f-061c-4d1b-b2bf-e62a7dde7afd" />

## Space Defender
### Description
Space Defender is a 2D arcade-style shooting game built using Processing. The player controls a space ship at the bottom of the screen and must destroy falling meteors using lasers. Meteors vary in size and health, and the game becomes progressively more challenging as they spawn randomly and fall faster.
The game includes a scoring system, high score tracking across play sessions, and penalty mechanics for missed meteors. The game ends if the player is hit by a meteor or if the score drops below zero.
### How the user interacts with it
The user interacts with the game using the keyboard:
- __Left Arrow Key__ -> Move to the Left
- __Right Arrow Key__ -> Move to the Right
- __Space Bar__ -> Shoot Lasers Upward
- __R Key__ -> Restart the game after game over
Gameplay loop:
1. Meteors spawn from the top of the screen.
2. The player moves left/right to avoid them.
3. The player shoots lasers to destroy meteors.
4. Destroying meteors increases score based on difficulty.
5. Letting meteors hit the ground reduces score as a penalty.
6. The game ends if the ship is hit or score drops below 0.
### Known Limitations
- The High score gets reset when the program is closed
- No sound effects or background music.
- The Meteor Spawn rate is random so sometimes the wave of meteors can be impossible
- No start menu or pause menu.
### Attribution or External Assets
- No external images, sounds, or fonts were used.
- All game assets (shapes, visuals, logic) were created by the authors.
