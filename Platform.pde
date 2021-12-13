
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
  void drawPlatform(){
    fill(r,g,b);
    /*Sets the image and show it*/
    imageMode(CORNER);
    image(img,x,y);
    //rect(x,y, widthPlatform, heightPlatform);
  }
  
  void movePlatform(float xPos){
    x = xPos-offset;                                    //the offset is for the platform to be center with the mouse movement
    //rect(x,y, widthPlatform, heightPlatform);
  }
  
  
}
