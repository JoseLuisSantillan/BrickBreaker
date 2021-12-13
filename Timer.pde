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
  void setTime(int c){
    countdown = c;
    startTime = millis()/1000 + countdown;
  }
  /*Runs the timer*/
  void runTimer(){
    seconds = startTime - millis()/1000;
  }


}
