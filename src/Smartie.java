/**
* The Smartie class is the container for the Smartie troggle. It contains a unique move method for following the Muncher around the game board.
*/

public class Smartie extends Troggle
   {
   /**
    * Constructs a Smartie.
    */
   public Smartie( Game g )
      {
      super( g );
      } //end constructor Smartie

   /**
    * The automatic, unique process for the Smarties to move which is controlled by the Smartie's moveTimer. Smarties move in a straight line for the
    * first three moves and in the direction of the Muncher thereafer. The Smartie determines the best possible move to get closer to the Muncher by
    * choosing the move with the least possible distance from the Muncher. This is determined by calling the distanceFrom() method in the muncher class.
    * If the Muncher is currently present on a safety square, the Smartie is essentially blind and has no idea where the Muncher is an moves randomly
    * around the board.
    */
   public void autoMove()
      {
      boolean smartMoving = true;

      if( getGRef().getMuncher().getX() == getGRef().getBoard().getSafetySquareX() && getGRef().getMuncher().getY() == getGRef().getBoard().getSafetySquareY() )
         {
         smartMoving = false;
         } //end if
      if( getGRef().getMuncherRecentlyEaten() )
         {
         smartMoving = false;
         } //end if

      if( getNumMoves() < 3 ) //makes entrance in straight line
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
      else if( smartMoving ) //smart moving
         {
         int originalDistance = getGRef().getMuncher().distanceFrom( getY(), getX() );
   
         int[] possibleMoves = new int[4];
         possibleMoves[ 0 ] = getGRef().getMuncher().distanceFrom( getY(), getX() + 1 ); //right
         possibleMoves[ 1 ] = getGRef().getMuncher().distanceFrom( getY() - 1, getX() ); //up
         possibleMoves[ 2 ] = getGRef().getMuncher().distanceFrom( getY(), getX() - 1 ); //left
         possibleMoves[ 3 ] = getGRef().getMuncher().distanceFrom( getY() + 1, getX() ); //down
   
         int bestMove = 0; //defaults to zero (right), will be changed below if false
   
         for( int index = 1; index < possibleMoves.length; index++ )
            {
            if( possibleMoves[ index ] < possibleMoves[ bestMove ] )
               {
               bestMove = index;
               } //end if
            } //end for
         
         if( bestMove == 0 ) 
            {
            super.move( "right" );
            } //end if
         if( bestMove == 1 ) 
            {
            super.move( "up" );
            } //end if
         if( bestMove == 2 ) 
            {
            super.move( "left" );
            } //end if
         if( bestMove == 3 ) 
            {
            super.move( "down" );
            } //end if
         } //end if
      else //random moving
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
         } //end else

      addMove();
      } //end method autoMove

   /**
    * The Smartie's unique string representing itself in the console window.
    *
    * @return string representing the Smartie
    */
   public String toString()
      {
      return "*S*";
      }
   } //end class Smartie