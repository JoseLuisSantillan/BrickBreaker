
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


  void setColors(){
    r = random(0, 255);
    g = random(0, 255);
    b = random(0, 255);
  }
  
  /*Draw a Brick*/
  void drawBrick(){
    noStroke();
    fill(r,g,b);
    rect(x, y, widthBrick, heightBrick);
  }

  void brickGotHit(){
    hit = true;
    r = 255;
    g = 255;
    b = 255;
    rect(x, y, widthBrick, heightBrick);
  }

}
