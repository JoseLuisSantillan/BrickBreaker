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
  
  void drawBallStatic(){
    fill(r, g, b);
    /*Sets the image and shows it*/
    imageMode(CENTER);
    image(img,x,y);
    //ellipse(x, y, diameter, diameter);      //draw a ball
    
  }
  
  /*Moves the ball*/
  void moveBall(){                          
    x += x_velocity;
    y += y_velocity;
  }
  
  /*Change direction of the ball*/
  void bounce(){          
    x_velocity *=-1;
  }
  
  void moveLeft(){          
    x_velocity = -3;
  }
  
  void moveRight(){
    x_velocity = 3;
  }
  
  void moveRightRight(){
    x_velocity = 6;
  }
  
  void moveLeftLeft(){
    x_velocity = -6;
  }
  
  void changeYPos(){
    y_velocity *= -1;
  }
  
  /*Reset ball*/
  void resetBall(float xPos, float yPos){
      x = xPos;
      y = yPos;
      x_velocity = 0;
      y_velocity = 4;
      isBallMoving = false;
  }
  
}
