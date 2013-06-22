/**
* The Character class is the superclass for all moving objects on the game board. It contains variables for the Character's location as well as a
* move method. Each inherited character must implement a toString method and an isValidMove method.
*/

public abstract class Character
   {
   private Game gRef;

   private int locationY;
   private int locationX;

   /**
    * Constructs a character. In the basic character class, the character's gRef variable is assigned to the game class reference.
    *
    * @param g reference to the Game class
    */
   public Character( Game g )
      {
      gRef = g;
      } //end constructor Character

   /**
    * Sets the location of the character on the board.
    *
    * @param y value of new Y coordinate
    * @param x value of new X coordinate
    */
   public void setLocation( int y, int x )
      {
      locationY = y;
      locationX = x;
      } //end method setLocation

   /**
    * Returns the value of the character's current X coordinate.
    *
    * @return value of the character's current X coordinate
    */
   public int getX()
      {
      return locationX;
      } //end method getX

   /**
    * Returns the value of the character's current Y coordinate.
    *
    * @return value of the character's current Y coordinate
    */
   public int getY()
      {
      return locationY;
      } //end method getY

   /**
    * Sets the character's X coordinate.
    *
    * @param x value of new X coordinate
    */
   public void setX( int x )
      {
      locationX = x;
      } //end method setX

   /**
    * Sets the character's Y coordinate.
    *
    * @param y value of new Y coordinate
    */
   public void setY( int y )
      {
      locationY = y;
      } //end method setY

   /**
    * Returns reference to the Game class.
    *
    * @return reference to the Game class
    */
   public Game getGRef()
      {
      return gRef;
      } //end method getGRef      

   /**
    * Determines which direction the character wants to move and adjusts its X and Y coordinates appropriately.
    *
    * @param direction String rerpesenting the desired direction to move ( right, up, left, down )
    */
   public void move( String direction )
      {
      if( isValidMove( direction ) )
         {
         if( direction.equals( "right" ) )
            {
            setX( getX() + 1 );
            } //end if
         if( direction.equals( "up" ) )
            {
            setY( getY() - 1 );
            } //end if
         if( direction.equals( "left" ) )
            {
            setX( getX() - 1 );
            } //end if
         if( direction.equals( "down" ) )
            {
            setY( getY() + 1 );            
            } //end if
         gRef.checkBoard();
         gRef.printStatus();
         } //end if
      } //end method move

   /**
    * Gives the character a unique string representing itself in the console window.
    *
    * @return string representing the character's type
    */
   public abstract String toString();

   /**
    * Determines whether the move desired by the character is valid or not based on the character's specific moving capabilities.
    *
    * @return true if move is valid and false if move is not valid
    */
   public abstract boolean isValidMove( String direction );
   } //end abstract class Character