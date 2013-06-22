/**
* The Helper class is the container for the Helper troggle. It contains a unique move method and also implements Eat so it can eat numbers
* on the game board.
*/

public class Helper extends Troggle implements Eat
   {
   /**
    * Constructs a Reggie.
    */
   public Helper( Game g )
      {
      super( g );
      } //end constructor Helper
   
   /**
    * The automatic, unique process for the Helper to move which is controlled by the Helper's moveTimer. Helpers move in a straight line for
    * the first three moves and randomly thereafter. If a helper lands on a game board location with a correct answer, it eats it.
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
         if( getGRef().getBoard().isValid( getY(), getX() ) ) //if the number at that location is valid, check to see if it is correct
            {
            if( getGRef().getBoard().isCorrect( getY(), getX() ) ) //check to see if the answer is correct
               {
               eat();
               } //end if 
            } //end if
         } //end if

      addMove();
      } //end method autoMove

   /**
    * Eats the number at the Helper's current location.
    */
   public void eat()
      {
      getGRef().getBoard().deleteNum( getY(), getX() );
      } //end method eat

   /**
    * The Helper's unique string representing itself in the console window.
    *
    * @return string representing the Helper
    */
   public String toString()
      {
      return "*H*";
      }
   } //end class Helper