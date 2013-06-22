import java.util.Timer;
import java.util.TimerTask;

/**
* The Troggle class is the superclass for all Troggles on the game board. Troggles are unique from characters in that they all contain an
* autoMove mehtod which must be implemented. Also, they contain a specific isValidMove method that allows them to go outside the boundaries of the
* game board but forbids them from entering safety squares. They also contain an eatMuncher method which is called by the checkBoard method in
* the Game class. 
*/

public abstract class Troggle extends Character
   {
   private int enterDirection;
   private int numMoves;

   private Timer moveTimer;
   private TimerTask moveTask;

   /**
    * Constructs a Troggle. The Troggle is given a random enter direction from which a random enter location is determined. The moveTimer is
    * also initialized with the Troggle's autoMove() method and is set at a variable interval depending on the level.
    */
   public Troggle( Game g )
      {
      super( g );
      numMoves = 0;      

      int yEnterLoc = 0; //have to initialize or else error thrown at compile
      int xEnterLoc = 0; //have to initialize or else error thrown at compile

      enterDirection = (int)( Math.random() * 4 ); //assigns a random entering direction for the Reggie to enter FROM
      if( enterDirection == 0 || enterDirection == 2 ) //which means it enters from the Y axis
         {
         yEnterLoc = (int)( Math.random() * GameBoard.LENGTHY ); //sets Y value to random location in domain of Y axis
         if( enterDirection == 0 ) //which means it enters on Y axis from the right
            {
            xEnterLoc = GameBoard.LENGTHX; //sets X value to the furthest column to the right
            } //end if
         if( enterDirection == 2 ) //which means it enters on Y axis from the left
            {
            xEnterLoc = -1; //sets X to the first column on the left
            } //end if
         } //end if 

      if( enterDirection == 1 || enterDirection == 3 ) //which means it enters from the X axis
         {
         xEnterLoc = (int)( Math.random() * GameBoard.LENGTHX ); //sets X value to random location in domain of X axis
         if( enterDirection == 1 ) //which means it enters on X axis from the top
            {
            yEnterLoc = -1; //sets X value to the first row on the top
            } //end if
         if( enterDirection == 3 ) //which means it enters on X axis from the bottom
            {
            yEnterLoc = GameBoard.LENGTHY; //sets X to the furthest row from the top
            } //end if
         } //end if 

      /*ERROR CHECKING*/// System.out.println( "enter location: (y,x): (" + yEnterLoc + "," + xEnterLoc + ")" );
      /*ERROR CHECKING*/// System.out.println( "enter direction: " + enterDirection );
      setLocation( yEnterLoc, xEnterLoc );

      moveTask = new TimerTask()
         { 
         public void run() 
            {
            if( getGRef().isActive() )
               {
               autoMove();
               } //end while
            } //end method run
         }; //end class TimerTask

      int troggleMoveDelay;
      
      if( getGRef().getLevel() > 0 && getGRef().getLevel() <= 3 )
         {
         troggleMoveDelay = 5000;
         } //end if
      else if( getGRef().getLevel() > 3 && getGRef().getLevel() <= 6 )
         {
         troggleMoveDelay = 4000;
         } //end if
      else if( getGRef().getLevel() > 6 && getGRef().getLevel() <= 9 )
         {
         troggleMoveDelay = 3000;
         } //end if  
      else if( getGRef().getLevel() > 9 && getGRef().getLevel() <= 12 )
         {
         troggleMoveDelay = 2000;
         } //end if
      else
         {
         troggleMoveDelay = 1000;
         } //end if

      moveTimer = new Timer();
      moveTimer.schedule( moveTask, 0, troggleMoveDelay );
      } //end constructor Troggle

   /**
    * Returns the numerical representation of the Troggle's enter direction. ( 0 = right, 1 = up, 2 = left, 3 = down )
    *
    * @return value of the numerical representation of the Troggle's enter direction
    */
   public int getEnterDirection()
      {
      return enterDirection;
      } //end method getEnterDirection

   /**
    * Determines whether the Troggle is currently present on the visible GameBoard.
    *
    * @return true if Troggle is on the visible GameBoard and false if it is not
    */
   public boolean isOnBoard()
      {
      if( getY() >= 0 && getY() < GameBoard.LENGTHY && getX() >= 0 && getX() < GameBoard.LENGTHX )
         {
         return true;
         } //end if
      else
         {
         return false;
         } //end else
      } //end method isOnBoard

   /**
    * Determines whether the Muncher is currently present at the Troggle's current game board location.
    *
    * @return true if the Muncher is present and false if it is not
    */
   public boolean isMuncherPresent()
      {
      if( getGRef().getMuncher().getY() == getY() && getGRef().getMuncher().getX() == getX() )
         {
         return true;
         } //end if
      else
         {
         return false;
         } //end else
      } //end method isMuncherPresent

   /**
    * Displays the GUI eat message which triggers the process for decremening the number of lives of the muncher and/or ending the game.
    */
   public void eatMuncher()
      {
      getGRef().getMyGUI().displayTroggleEatMsg();
      } //end method eatMuncher

   /**
    * Determines if the requested Troggle move is valid. A valid Troggle move is defined as any move on the visible game board and one additional
    * unit of distance in every direction off of the game board that does not enter a current safety square. If the Troggle exits the game board
    * after having been on the board for at least three moves, the deleteTroggle() method is called to remove the Troggle from the game.
    *
    * @param direction direction of the requested move
    * @return true if move is valid and false if move is not valid
    */
   public boolean isValidMove( String direction )
      {
      boolean isValid = true;

      if( numMoves > 3 && !isOnBoard() )
         {
         isValid = false;
         getGRef().deleteTroggle( this );
         } //end if

      if( direction.equals( "right" ) )
         {
         if( getGRef().getBoard().isSafetySquare( getY(), getX() + 1 ) )
            {
            isValid = false;
            } //end if
         } //end if
      if( direction.equals( "up" ) )
         {
         if( getGRef().getBoard().isSafetySquare( getY() - 1, getX() ) )
            {
            isValid = false;
            } //end if
         } //end if
      if( direction.equals( "left" ) )
         {
         if( getGRef().getBoard().isSafetySquare( getY(), getX() - 1 ) )
            {
            isValid = false;
            } //end if
         } //end if
      if( direction.equals( "down" ) )
         {
         if( getGRef().getBoard().isSafetySquare( getY() + 1, getX() ) )
            {
            isValid = false;
            } //end if
         } //end if
      
      return isValid;
      } //end method isValidMove

   /**
    * Adds a move to the Troggle's current numMoves counter variable.
    */
   public void addMove()
      {
      numMoves++;
      } //end method addMove

   /**
    * Returns the value of the Troggle's current number of moves represented by the numMoves variable.
    *
    * @return value of the Troggle's current number of moves
    */
   public int getNumMoves()
      {
      return numMoves;
      } //end method getNumMoves

   /**
    * The automatic, unique process for the Troggle to move which is controlled by the Troggle's moveTimer.
    */
   public abstract void autoMove();

   /**
    * Cancels the moveTask timer.
    */
   public void cancelTimer()
      {
      moveTask.cancel();
      moveTimer.cancel();
      moveTimer.purge();
      } //end method cancelTimer
   }