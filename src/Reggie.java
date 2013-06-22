/**
* The Reggie class is the container for the Reggie troggle. It contains a unique move method.
*/

public class Reggie extends Troggle
   {
   /**
    * Constructs a Reggie.
    */
   public Reggie( Game g )
      {
      super( g );
      } //end constructor Reggie
   
   /**
    * The automatic, unique process for the Reggies to move which is controlled by the Reggie's moveTimer. Reggies move in a straight line across
    * the game board starting from their starting location and in the direction of their enterDirection as determined in the Troggle constructor.
    */
   public void autoMove()
      {
      if( getEnterDirection() == 0 ) //which means it will move across the board from right to left
         {
         super.move( "left" );
         } //end if
      if( getEnterDirection() == 1 ) //which means it will move across the board from top to bottom
         {
         super.move( "down" );
         } //end if
      if( getEnterDirection() == 2 ) //which means it will move across the board from left to right
         {
         super.move( "right" );
         } //end if
      if( getEnterDirection() == 3 ) //which means it will move across the board from bottom to top
         {
         super.move( "up" );
         } //end if

      addMove();
      } //end method autoMove  
   
   /**
    * The Reggie's unique string representing itself in the console window.
    *
    * @return string representing the Reggie
    */
   public String toString()
      {
      return "*R*";
      }
   } //end class Reggie