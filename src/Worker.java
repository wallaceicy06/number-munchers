/**
* The Worker class is the container for the Worker troggle. It contains a unique move method that also contains code for the worker to change numbers
* at each space it lands on.
*/

public class Worker extends Troggle
   {
   /**
    * Constructs a Worker.
    */
   public Worker( Game g )
      {
      super( g );
      } //end constructor Worker

   /**
    * The automatic, unique process for the Workers to move which is controlled by the Worker's moveTimer. Workers move in a straight line for the 
    * first three moves and randomly thereafter. Each time the worker moves to a new location, it changes the number at that location by calling
    * the GameBoard's changeNum() method.
    */   
   public void autoMove()
      {
      if( getNumMoves() < 3 )
         {
         if( getEnterDirection() == 0 ) //which means it will enter the board from right to left
            {
            super.move( "left" );
            } //end if
         if( getEnterDirection() == 1 ) //which means it will enter the board from top to bottom
            {
            super.move( "down" );
            } //end if
         if( getEnterDirection() == 2 ) //which means it will enter the board from left to right
            {
            super.move( "right" );
            } //end if
         if( getEnterDirection() == 3 ) //which means it will enter the board from bottom to top
            {
            super.move( "up" );
            } //end if
         } //end if
      else
         {
         int randomDirection = (int)( Math.random() * 4 );
         if( randomDirection == 0 )
            {
            super.move( "right" );
            } //end if
         if( randomDirection == 1 )
            {
            super.move( "up" );
            } //end if
         if( randomDirection == 2 )
            {
            super.move( "left" );
            } //end if
         if( randomDirection == 3 )
            {
            super.move( "down" );
            } //end if
         }//end else
      
      if( isOnBoard() ) //if the troggle is on the game board
         {
         getGRef().getBoard().changeNum( getY(), getX() );
         } //end if

      addMove();
      } //end method autoMove
   
   /**
    * The Worker's unique string representing itself in the console window.
    *
    * @return string representing the Worker
    */
   public String toString()
      {
      return "*W*";
      }
   } //end class Worker