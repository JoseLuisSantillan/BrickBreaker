import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BrickBreaker extends PApplet {

/*
    Brick Breaker Game
    Proyecto Final  
    Jose Luis Santillan
    211496

*/


int rows;                                                //variable for number of rows
int columns;                                             //variable for number of bricks
int sizeOfArrayOfBricks;                                 //variable for size of arrayOfBricks
Brick[] arrayOfBricks;                                   //array of bricks
ArrayList <Ball> balls;
Platform platform;                                       //instance of platform class
//Ball ball ;                                            //instance of ball class
Timer timer;                                             //instance of timer class
PImage galaxyImage, winImage, loseImage, pauseImage;
SoundFile bounceAudio, gameOverAudio, winAudio;          //instance of SoundFile class
float centerX,centerY;                                   //variables for center X and Y
int score,numberOfBalls,totalScore;                      //variables for score and lives
PFont winFont, defaultFont, loseFont, menuFont;          //instances of fonts
int streak, auxStreak;                                   //variables for Streak
boolean isPlaying;
boolean canChange;
String nivel = "Por escoger" ;                            //variable for mainMenu
int s = 0;                                               //extra variable
int level = 1;                                               //variable for levels

public void setup(){
  
  background(0);
  centerX = width/2; 
  centerY = height/2;
  mainMenu();
  isPlaying = false;
  canChange = true;
  numberOfBalls = 3;      
  balls = new ArrayList<Ball>();
  platform = new Platform(centerX, height - 100);
  balls.add(new Ball(centerX, height - 200));
  timer = new Timer(180);
  streak = 0;
  auxStreak = 15;
  bounceAudio = new SoundFile(this, "contactmic_1.mp3");
  gameOverAudio =  new SoundFile(this, "gameOver.wav");
  winAudio = new SoundFile(this, "win.wav");

}

public void draw(){

  if((key == 'P' || key == 'p')){
    play();
  }else if(isPlaying == false && canChange == true){
    mainMenu();
    if((key == 'F' || key == 'f') && isPlaying == false && canChange == true){
      resetAll(5,240,10);      
      nivel = "Facil";
    }else if((key == 'M'|| key == 'm') && isPlaying == false && canChange == true){
      resetAll(3,180,15);
      nivel = "Medio";
    }else if((key == 'D' || key == 'd') && isPlaying == false && canChange == true){
      resetAll(2,150,20);
      nivel = "Dificil";
    }else if(key == '1' && isPlaying == false && canChange == true){
      level = 1;
    }else if (key == '2'  && isPlaying == false && canChange == true){
      level = 2;
    }else if (key == '3'  && isPlaying == false && canChange == true){
      level = 3;
    }else if ((key == 'V' || key == 'v') && isPlaying == false && canChange == true){
      mainMenu();
      resetAll(3,180,15);
      nivel =  "Medio";
    }
  }else {
    pauseScreen();
  }
  println(balls.size());
}







public void resetAll(int numberOfBalls_, int time_, int auxStreak_){
  /*Depending of the level, its builds an arrays of brick of different sizes*/
  if(level == 1){
    build(5,5);
  }else if(level == 2){
    build(8,8);
  }else if(level == 3){
    build(10,10);
  }
  
  
  /*Reset all the values*/
  score = 0;
  numberOfBalls = numberOfBalls_;
  totalScore = 0;
  for(int c = balls.size()-1; c >= 0; c--){
    Ball ball = balls.get(c);
    if (balls.size()!=1) {
        balls.remove(c);
    }
    ball.resetBall(centerX, height - 200);
  }
  timer.setTime(time_);
  streak = 0;
  auxStreak = auxStreak_;
  s = 0;
  
}

/*Function to build an array of bricks*/    
public void build(int rows_, int columns_){
    rows = rows_;
    columns = columns_;
    sizeOfArrayOfBricks = rows*columns;
    arrayOfBricks = new Brick[sizeOfArrayOfBricks];
    for (int i = 0; i < rows; i++){
      for (int j = 0; j< columns; j++){
        arrayOfBricks[i*rows + j] = new Brick((i+0.1f) * width/(rows), (j+1) * (height/24));
      }
    }
}

/*Moves the ball when mouse is clicked*/
public void mouseClicked(){
  for(int c = balls.size()-1; c >= 0; c--){
    Ball ball = balls.get(c);
    ball.isBallMoving = true;
  }
}

public void mainMenu(){
  galaxyImage = loadImage("galaxy.jpg");
  imageMode(CORNER);
  image(galaxyImage,0,0);
  textSize(42);
  textAlign(CENTER);
  menuFont = createFont("Algerian",38);                            //font when the player wins
  textFont(menuFont);
  fill(255);                                                       //color of font
  text("Bienvenido a Brick Breaker",centerX,100);   
  textSize(25);
  text("Escoja el nivel de dificultad", centerX, 200);
  textSize(25);
  text("Facil: F" , width-500, 300);
  textSize(25);
  text("Medio: M", width -300, 300);
  textSize(25);
  text("Dificil: D", width-100, 300);
  textSize(25);
  text("Modo de juego seteado: " + nivel, centerX, 350);
  textSize(25);
  text("Nivel: " + level, centerX, 450);
  textSize(25);
  text("Presione P para jugar", centerX, 550);
  
}

public void pauseScreen(){
  background(0);
  pauseImage = loadImage("pausa.jpg");
  imageMode(CENTER);
  image(pauseImage,300,300);
  textSize(42);
  textAlign(CENTER);
  menuFont = createFont("Algerian",38);                            //font when the player wins
  textFont(menuFont);
  fill(255);                                                       //color of font
  text("Juego pausado",centerX,100); 
  textSize(25);
  text("Presione P para reanudar", centerX, 550);
}

public void win(){
  winImage = loadImage("ganaste.jpg");
  winImage.resize(600,600);
  imageMode(CORNER);
  image(winImage,0,0);
  textSize(42);
  textAlign(CENTER);
  winFont = createFont("Algerian",42);                            //font when the player wins
  textFont(winFont);
  fill(255,0,0);                                                      //color of font
  text("Ganaste!",centerX,100);   
  textSize(25);
  fill(255);  
  text("Numero de vidas restantes: " + numberOfBalls, centerX, 200);
  textSize(25);
  text("Puntos obtenidos en juego: " + score, centerX, 300);
  textSize(25);
  text("Puntos totales: " + totalScore, centerX, 400);
  textSize(18);
  text("Presione v para volver a jugar",centerX, 500);
  for(int c = balls.size()-1; c >= 0; c--){
    Ball ball = balls.get(c);
    ball.x = -10000;                                                  //puts the ball outside the window
    ball.y = -10000;
    ball.drawBallStatic();
  }
  /*Condition for the audio to play once*/
  if(s == 0){
    winAudio.play();
    s++;
  }
  
  
}

public void lose(){
  loseImage = loadImage("perdiste.jpg");
  loseImage.resize(600,600);
  imageMode(CORNER);
  image(loseImage,0,0);
  textSize(42);
  textAlign(CENTER);
  loseFont = createFont("Algerian",42);                                      //font when the players loose 
  textFont(loseFont);
  fill(255,0,0);                                                                 //color of font
  text("Perdiste :(",centerX,100);   
  textSize(30);
  fill(255);
  text("Numero de vidas restantes: "  +numberOfBalls, centerX, 200);
  textSize(30);
  text("Puntos obtenidos en juego: " + score, centerX, 300);
  textSize(30);
  text("Puntos totales: " + totalScore, centerX, 400);
  textSize(18);
  text("Presione V para volver a jugar",centerX, 500);
  for(int c = balls.size()-1; c >= 0; c--){
    Ball ball = balls.get(c);
    ball.x = -10000;
    ball.y = -10000;
    ball.drawBallStatic();
  }
  /*Condition for the audio to play once*/
  if(s == 0){
    gameOverAudio.play();
    s++;
  }
  
}

public void play(){
  isPlaying = true;
  canChange = false;
  background(255);

  /*Run the timer*/
  timer.runTimer();
  
  /*Draw each brick of the array of bricks*/
  for(int i = 0; i < sizeOfArrayOfBricks; i++){
     arrayOfBricks[i].drawBrick();                        
  }
  
  /*Draw and moves the platform*/
  platform.drawPlatform();
  platform.movePlatform(mouseX);
  
  for(int c = balls.size()-1; c >= 0; c--){   //
    Ball ball = balls.get(c);
    /*Draw a ball and moves it in case of a mouseClick event*/
    
    ball.drawBallStatic();
    if(ball.isBallMoving == true){
      ball.moveBall();
    }
    
    
    for (int i = 0; i < sizeOfArrayOfBricks; i ++){
      /*Bottom of brick*/
      if ((ball.y - ball.diameter / 2 <= arrayOfBricks[i].y + arrayOfBricks[i].heightBrick) 
          &&  (ball.y - ball.diameter/2 >= arrayOfBricks[i].y) 
          && (ball.x >= arrayOfBricks[i].x) 
          && (ball.x <= arrayOfBricks[i].x + arrayOfBricks[i].widthBrick)  
          && (arrayOfBricks[i].hit == false)){
        ball.changeYPos();
        arrayOfBricks[i].brickGotHit();
        score += 100; 
        streak +=1;
      } 
  
      /*Top of brick*/
      if ((ball.y + ball.diameter / 2 >= arrayOfBricks[i].y) 
          && (ball.y - ball.diameter /2 <= arrayOfBricks[i].y + arrayOfBricks[i].heightBrick/2) 
          && (ball.x >= arrayOfBricks[i].x) 
          && (ball.x <= arrayOfBricks[i].x + arrayOfBricks[i].widthBrick) 
          && (arrayOfBricks[i].hit == false)){
        ball.changeYPos();
        arrayOfBricks[i].brickGotHit();
        score += 100;
        streak +=1;
      }
  
      /*Left of brick*/
      if ((ball.x + ball.diameter / 2 >= arrayOfBricks[i].x) 
          && (ball.x + ball.diameter/ 2 <= arrayOfBricks[i].x + arrayOfBricks[i].widthBrick / 2) 
          && (ball.y >= arrayOfBricks[i].y) 
          && (ball.y <= arrayOfBricks[i].y + arrayOfBricks[i].heightBrick)  
          && (arrayOfBricks[i].hit == false)){
        //ball.moveLeft();
        ball.bounce();
        arrayOfBricks[i].brickGotHit();
        score += 100;
        streak +=1;
      }
  
      /*Right of brick*/
      if ((ball.x - ball.diameter/2 <= arrayOfBricks[i].x + arrayOfBricks[i].widthBrick) 
          && (ball.x +ball.diameter / 2 >= arrayOfBricks[i].x + arrayOfBricks[i].widthBrick / 2) 
          && (ball.y >= arrayOfBricks[i].y) 
          && (ball.y <= arrayOfBricks[i].y + arrayOfBricks[i].heightBrick)  
          && (arrayOfBricks[i].hit == false)){
        //ball.moveRight();
        ball.bounce();
        arrayOfBricks[i].brickGotHit();
        score += 100;
        streak +=1;
      }
  
    }
    
    
    // Hits > 0.75 side of the platform
    if ((ball.y == platform.y) && (ball.x > platform.x + 3*(platform.widthPlatform/4)) && (ball.x <= platform.x + platform.widthPlatform)){
      ball.moveRightRight();
      ball.changeYPos();
      bounceAudio.play();
    }
    //Hits 0.5 a 0.75 side of platform
    if ((ball.y == platform.y) && (ball.x > platform.x + (platform.widthPlatform/2)) && (ball.x <= platform.x + 3*(platform.widthPlatform)/4)){
      ball.moveRight();
      ball.changeYPos();
      bounceAudio.play();
    }
    
    //Hits <0.25 side of the platform
    if ((ball.y == platform.y) && (ball.x > platform.x) && (ball.x <= platform.x + (platform.widthPlatform / 4))){
      ball.moveLeftLeft();
      ball.changeYPos();
      bounceAudio.play();
    }
    
    //Hits 0.25 a 0.5 side of the platform
    if ((ball.y == platform.y) && (ball.x > platform.x+ (platform.widthPlatform/4)) && (ball.x <= platform.x + (platform.widthPlatform / 2))){
      ball.moveLeft();
      ball.changeYPos();
      bounceAudio.play();
    }
    
    
    //Hits Right wall
    if (ball.x + ball.diameter / 2 >= width) ball.bounce();  //moveLeft
  
    //Hits Left wall
    if (ball.x - ball.diameter / 2 <= 0) ball.bounce();   //moveRight
  
    //Hits Ceiling
    if (ball.y - ball.diameter / 2 <= 0) ball.changeYPos();
    
    
    //If the ball goes outside the bottom part of the window, you lose one ball
    if(ball.y > height){
      /*Removes the balls only in case there exists more than one ball in the array*/
      if(balls.size()!=1){
        balls.remove(c);
      }
      ball.resetBall(centerX, height - 200);
      numberOfBalls -= 1;
      streak = 0;
    }
    
    
    //Streak                                        
    if((streak % auxStreak==0) && (streak!=0)){             
      numberOfBalls += 1;
      streak = 0;
      balls.add(new Ball(centerX, height - 400));     
      ball.isBallMoving = true;
      
    }
  
  }
  
  
  
  
  /*Info of points o number of balls left*/
  defaultFont = createFont("Times new roman",18);                               //font for score, time and number of balls
  textFont(defaultFont);
  textSize(18);
  textAlign(10, 20);
  text("Puntos: " + score, 10, 20);
  textSize(18);
  textAlign(width/2, 20);
  text("Time: "+timer.seconds, width/2-100, 20);
  textSize(18);
  textAlign(width/2, 20);
  text("Streak: "+streak+"/"+auxStreak, width/2+50, 20);
  textSize(18);
  textAlign(width-110, 20);
  text("No. Balls: " + numberOfBalls, width-110, 20);
 
 
 
  /*Condition to lose*/
  if(numberOfBalls <= 0 || timer.seconds < 0){
    lose();
    isPlaying = false;
    canChange = true;
    
  }
  
  /*Condition to win*/
  if(score >= sizeOfArrayOfBricks*100){           //if the score is more or equal to all the posible points, the player wins.
    totalScore = score + numberOfBalls * 50;                        //each ball left is 50 extra points to the total score.
    win();
    isPlaying = false;
    canChange = true;
    
  }

}
class Ball{
  /*Variables for a ball*/
  float x, y, x_velocity, y_velocity, diameter;           
  float r, g, b;
  boolean isBallMoving;
  /*Loads the image*/
  PImage img = loadImage("bola.png");
  
  Ball(float xPos, float yPos){
    x = xPos;
    y = yPos;
    x_velocity = 0;
    y_velocity = 4;                    //only starts with the "y" velocity
    diameter = 10;
    r = 255;                           
    g = 0;
    b = 0;
    isBallMoving = true;              //the ball is paused 
  }
  
  public void drawBallStatic(){
    fill(r, g, b);
    /*Sets the image and shows it*/
    imageMode(CENTER);
    image(img,x,y);
    //ellipse(x, y, diameter, diameter);      //draw a ball
    
  }
  
  /*Moves the ball*/
  public void moveBall(){                          
    x += x_velocity;
    y += y_velocity;
  }
  
  /*Change direction of the ball*/
  public void bounce(){          
    x_velocity *=-1;
  }
  
  public void moveLeft(){          
    x_velocity = -3;
  }
  
  public void moveRight(){
    x_velocity = 3;
  }
  
  public void moveRightRight(){
    x_velocity = 6;
  }
  
  public void moveLeftLeft(){
    x_velocity = -6;
  }
  
  public void changeYPos(){
    y_velocity *= -1;
  }
  
  /*Reset ball*/
  public void resetBall(float xPos, float yPos){
      x = xPos;
      y = yPos;
      x_velocity = 0;
      y_velocity = 4;
      isBallMoving = false;
  }
  
}

class Brick{
  /*Variables for a Brick*/
  float x, y;
  float widthBrick, heightBrick;
  float r,g,b;
  boolean hit;
  

  
  Brick(float xpos, float ypos){
    x = xpos;
    y = ypos;
    setColors();
    widthBrick = 48;
    heightBrick = 15;
    hit = false;
  }


  public void setColors(){
    r = random(0, 255);
    g = random(0, 255);
    b = random(0, 255);
  }
  
  /*Draw a Brick*/
  public void drawBrick(){
    noStroke();
    fill(r,g,b);
    rect(x, y, widthBrick, heightBrick);
  }

  public void brickGotHit(){
    hit = true;
    r = 255;
    g = 255;
    b = 255;
    rect(x, y, widthBrick, heightBrick);
  }

}

class Platform{
  /*Variables for a platform*/
  float x,y;
  float widthPlatform;
  float heightPlatform;
  float offset; 
  float r,g,b;
  /*Loads the image*/
  PImage img = loadImage("plataforma.png");
  
  Platform(float platformXPos, float platformYPos){
    x = platformXPos;
    y = platformYPos; 
    widthPlatform = 150;
    heightPlatform = 10;
    offset = widthPlatform/2;
    r = 255;
    g = 0;
    b = 0;
  }
  
  /*Draw a platform*/
  public void drawPlatform(){
    fill(r,g,b);
    /*Sets the image and show it*/
    imageMode(CORNER);
    image(img,x,y);
    //rect(x,y, widthPlatform, heightPlatform);
  }
  
  public void movePlatform(float xPos){
    x = xPos-offset;                                    //the offset is for the platform to be center with the mouse movement
    //rect(x,y, widthPlatform, heightPlatform);
  }
  
  
}
class Timer{
  /*Variables for a timer*/
  int countdown;
  int seconds;
  int startTime;
  
  /*Constructor*/
  Timer(int c){
    countdown = c;
    startTime = millis()/1000 + countdown;
  }
  
  /*Sets the time*/
  public void setTime(int c){
    countdown = c;
    startTime = millis()/1000 + countdown;
  }
  /*Runs the timer*/
  public void runTimer(){
    seconds = startTime - millis()/1000;
  }


}
  public void settings() {  size(600,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BrickBreaker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
