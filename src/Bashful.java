/**
* The Bashful class is the container for the Bashful troggle. It contains a unique move method.
*/

public class Bashful extends Troggle
   {
   /**
    * Constructs a Bashful.
    */
   public Bashful( Game g )
      {
      super( g );
      } //end constructor Bashful

   /**
    * The automatic, unique process for the Bashful to move which is controlled by the Bashful's moveTimer. Bashfuls move in a straight line for
    * the first three moves and randomly thereafter.
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

      addMove();
      } //end method autoMove

   /**
    * The Bashful's unique string representing itself in the console window.
    *
    * @return string representing the Bashful
    */
   public String toString()
      {
      return "*B*";
      } //end method toString
   
   } //end class Bashful