import java.util.ArrayList;

/**
* The Muncher is the user's character on the game board. Muncher keeps track of the lives that it currently has and also contains methods for
* other objects to calculate distance from it and to add score if it eats a correct number.
*/

public class Muncher extends Character implements Eat
   {
   private int lives;

   /**
    * Constructs a Muncher. The default number of lives is set to 3 and setStartLocation() is called.
    *
    * @param g reference to the Game class
    */
   public Muncher( Game g )
      {
      super( g );
      lives = 3;
      setStartLocation();
      } //end constructor Muncher

   /**
    * Sets starting location to a random location on the board. All spaces are possible starting locations except the outside edges.
    */
   public void setStartLocation()
      {
      setX( (int)( 1 + Math.random() * ( GameBoard.LENGTHX - 2 ) ) );
      setY( (int)( 1 + Math.random() * ( GameBoard.LENGTHY - 2 ) ) );
      } //end method setStartLocation   

   /**
    * Eats the number at the character's current location.
    */
   public void eat()
      {
      if( getGRef().getBoard().isValid( getY(), getX() ) )
         {
         if( getGRef().getBoard().isCorrect( getY(), getX() ) )
            {
            getGRef().addScore();
            getGRef().getBoard().deleteNum( getY(), getX() );
            } //end if
         else
            {
            getGRef().getBoard().deleteNum( getY(), getX() );
            getGRef().getMyGUI().displayIncorrectNumMsg();
            }

         getGRef().printStatus();
         }
      } //end method eat

   /**
    * Determines if the requested move for the Muncher is valid or not. A valid move is considered any move that sets the new location to a position
    * on the game board which exists. The Muncher cannot exit the board.
    *
    * @param direction direction of the requested move
    * @return true if move is valid and false if move is not valid
    */
   public boolean isValidMove( String direction )
      {
      boolean isValid = true;
      if( getGRef().isActive() == false )
         {
         isValid = false;
         }
      if( direction.equals( "right" ) && getX() == GameBoard.LENGTHX - 1 )
         {
         isValid = false;
         } //end if
      if( direction.equals( "up" ) && getY() == 0 )
         {
         isValid = false;
         } //end if
      if( direction.equals( "left" ) && getX() == 0 )
         {
         isValid = false;
         } //end if
      if( direction.equals( "down" ) && getY() == GameBoard.LENGTHY - 1 )
         {
         isValid = false;
         } //end if
      
      return isValid;
      } //end method isValidMove

   /**
    * Calculates the distance between the Muncher and a specified location. Distance is defined as the change in the X values plus the change in
    * the Y values of the two coordinate locations.
    *
    * @param y Y coordinate to calculate distance with
    * @param x X coordinate to calculate distance with
    * @return numerical distance between the coordinates and the Muncher as defined above
    */
   public int distanceFrom( int y, int x ) //coordinates of object to compare with
      {
      return( ( Math.abs( y - getY() ) ) + ( Math.abs( x - getX() ) ) );
      } //end method distanceFrom

   /**
    * Adds one life to the Muncher's life count.
    */
   public void addLife()
      {
      lives += 1;
      } //end method addLife

   /**
    * Removes one life from the Muncher's life count. If the Muncher has no more lives, the Game Over message is displayed which triggers the
    * endGame() process.
    */
   public void loseLife()
      {
      if( lives == 0 )
         {
         getGRef().getMyGUI().displayGameOverMsg();
         } //end if
      else
         {
         lives -= 1;
         } //end else
      } //end method loseLife

   /**
    * Returns the value of the current number of lives the Muncher possesses.
    *
    * @return value of the number of lives the Muncher possesses
    */
   public int getLives()
      {
      return lives;
      } //end method getLives

   /**
    * Returns unique String representation/identification of the Muncher for the console window.
    *
    * @return unique String representation of the Muncher
    */
   public String toString()
      {
      return "*M*";
      }
   } //end class Muncher