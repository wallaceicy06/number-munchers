import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
* The class GameBoard is the controller for the numbers currently on the board as well as the Safety Square. When a Game is
* created, the Game instantiates a GameBoard and the GameBoard object fills the board with the appropriate numbers according
* to the current game type, level, and game definitions. GameBoard also serves as the checker when a Character tries to eat
* a number on its current location. The GameBoard class also creates and updates the safety square's location.
*/

public class GameBoard
   {
   private Game gRef;

   private Integer[][] board;
   static final int LENGTHY = 5;
   static final int LENGTHX = 6;
   private int safetySquareY;
   private int safetySquareX;

   private ArrayList<Integer> correctNumsToFill;
   private ArrayList<Integer> incorrectNumsToFill;
   private int numForGame;
   private int numOfCorrect;
   private boolean[] primeIndex;

   private Timer safetySquareRefreshTimer;
   private TimerTask safetySquareRefreshTask;
  
   /**
    * Constructs a new GameBoard object.
    *
    * @param g reference to the GUI class
    */
   public GameBoard( Game g )
      {
      gRef = g;
      board = new Integer[ LENGTHY ][ LENGTHX ];
      indexPrimes();
      fillBoard();
      refreshSafetySquare();

      safetySquareRefreshTask = new TimerTask()
         { 
         public void run() 
            {
            if( gRef.isActive() )
               {
               refreshSafetySquare();
               } //end while
            } //end method run
         }; //end class TimerTask

      safetySquareRefreshTimer = new Timer();
      safetySquareRefreshTimer.schedule( safetySquareRefreshTask, 0, 10000 );

      } //end constructor GameBoard

   /**
    * Fills the game board with numbers. The method determines a selection of correct and incorrect answers and fills the 
    * correctNumsToFill and incorrectNumsToFill ArrayList variables accordingly. Random numbers are selected from these
    * ArrayList variables and filled into the board.
    */
   public void fillBoard()
      {
      int minNum = 0;
      int maxNum = 0;

      if( gRef.getGameType() == 0 ) //game type: multiples
         {
         if( gRef.getGameDefs().isInOrder() )
            {
            numForGame = gRef.getGameDefs().getMultiplesStart() + ( ( gRef.getLevel() - 1 ) % ( gRef.getGameDefs().getMultiplesEnd() - gRef.getGameDefs().getMultiplesStart() + 1 ) ); 
            } //end if
         else
            {
            numForGame = ( gRef.getGameDefs().getMultiplesStart() + (int)( Math.random() * ( gRef.getGameDefs().getMultiplesEnd() - gRef.getGameDefs().getMultiplesStart() ) ) );
            } //end else
         minNum = numForGame;
         maxNum = numForGame * gRef.getGameDefs().getMaxMultiple();
         } //end if

      if( gRef.getGameType() == 1 ) //game type: factors
         {
         if( gRef.getGameDefs().isInOrder() )
            {
            numForGame = gRef.getGameDefs().getFactorsStart() + ( ( gRef.getLevel() - 1 ) % ( gRef.getGameDefs().getFactorsEnd() - gRef.getGameDefs().getFactorsStart() + 1 ) ); 
            } //end if
         else
            {
            numForGame = ( gRef.getGameDefs().getFactorsStart() + (int)( Math.random() * ( gRef.getGameDefs().getFactorsEnd() - gRef.getGameDefs().getFactorsStart() ) ) );
            } //end else 
         minNum = 1;
         maxNum = numForGame;
         } //end if

      if( gRef.getGameType() == 2 ) //game type: primes
         {
         minNum = 1;
         
         int numCorrect = 0; //counts how many primes are correct
         for( int index = 2; numCorrect < gRef.getLevel(); index++ )
            {
            if( primeIndex[ index ] )
               {
               numCorrect++;
               } //end if
            maxNum = index;
            } //end for
         } //end if

      correctNumsToFill = new ArrayList<Integer>();
      incorrectNumsToFill = new ArrayList<Integer>();

      for( int index = minNum; index <= maxNum; index++ )
         {
         if( isCorrect( index ) )
            {
            correctNumsToFill.add( new Integer( index ) );
            } //end if
         else
            {
            incorrectNumsToFill.add( new Integer( index ) );
            } //end else
         } //end for

      numOfCorrect = 0;
      for( int indexY = 0; indexY < board.length; indexY++ )
         {
         for( int indexX = 0; indexX < board[0].length; indexX++ )
            {
            int randomCorrect = (int)( Math.random() * 2 );
            if( randomCorrect == 0 ) //enter correct num
               {
               board[ indexY ][ indexX ] = new Integer( correctNumsToFill.get( (int)( Math.random() * correctNumsToFill.size() ) ) );
               numOfCorrect++;
               } //end if
            if( randomCorrect == 1 ) //enter incorrect num
               {
               board[ indexY ][ indexX ] = new Integer( incorrectNumsToFill.get( (int)( Math.random() * incorrectNumsToFill.size() ) ) );
               } //end if
            } //end for
         } //end for
      } //end method fillBoard

   /**
    * Clears all the numbers currently in the game board.
    */
   public void clearBoard()
      {
      for( int indexY = 0; indexY < board.length; indexY++ )
         {
         for( int indexX = 0; indexX < board[0].length; indexX++ )
            {
            board[ indexY ][ indexX ] = null;
            } //end for
         } //end for
      } //end method clearBoard
   
   /**
    * Determines if the number in the game board at the passed location (x,y) is a correct answer to the current game type.
    * 
    * @param y Y coordinate of desired game board location to check
    * @param x X coordinate of desired game board location to check
    * @return true if the number is a correct answer and false if the number is incorrect
    */
   public boolean isCorrect( int y, int x )
      {
      boolean returnValue = false;

      if( gRef.getGameType() == 0 ) //game type: multiples
         {
         if( board[ y ][ x ] % numForGame == 0 )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      if( gRef.getGameType() == 1 ) //game type: factors
         {
         if( numForGame % board[ y ][ x ] == 0 )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      if( gRef.getGameType() == 2 ) //game type: primes
         {
         if( primeIndex[ (int) board[ y ][ x ] ] )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      return returnValue;
      } //end method isValid

   /**
    * Determines if the passed number is is a correct answer to the current game type.
    *
    * @param num number value of desired number to check
    * @return true if the number is a correct answer and false if the number is incorrect
    */
   public boolean isCorrect( int num )
      {
      boolean returnValue = false;

      if( gRef.getGameType() == 0 ) //game type: multiples
         {
         if( num % numForGame == 0 )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      if( gRef.getGameType() == 1 ) //game type: factors
         {
         if( numForGame % num == 0 )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      if( gRef.getGameType() == 2 ) //game type: primes
         {
         if( primeIndex[ num ] )
            {
            returnValue = true;
            } //end if
         else
            {
            returnValue = false;
            } //end else
         } //end if

      return returnValue;
      } //end method isValid

   /**
    * Indexes all possible correct and incorrect prime numbers and stores them to the boolean array primeIndex. The index
    * of this array represents the prime number and the boolean value corresponding to that index represents whether the prime
    * number is correct (true) or incorrect (false).
    */   
   public void indexPrimes()
      {
      primeIndex = new boolean[ gRef.getGameDefs().getPrimesEnd() + 1 ];
      primeIndex[ 0 ] = false;
      primeIndex[ 1 ] = false;
      primeIndex[ 2 ] = true;
      for( int index = 3; index < primeIndex.length; index++ )
         {
         if( index % 2 == 0 )
            {
            primeIndex[ index ] = false;
            } //end if
         else
            {
            primeIndex[ index ] = true;
            for( int counter = 3; counter < ( gRef.getGameDefs().getPrimesEnd() + 1 ) / 2 && counter < index && primeIndex[ index ] == true; counter += 2 )
               {
               if( index % counter == 0 )
                  {
                  primeIndex[ index ] = false;
                  } //end if
               } //end for
            } //end else
         } //end for
      } //end method indexPrimes

    /**
    * Determines if it is valid for a number at the passed game board location to be eaten. If the location contains a null 
    * Integer reference, the number is considered invalid.
    *
    * @param y Y coordinate of desired game board location to check
    * @param X X coordinate of desired game board location to check
    * @return true if the location contains a valid Integer reference and false if the location contains a null Integer reference
    */
   public boolean isValid( int y, int x )
      {
      if( board[ y ][ x ] == null )
         {
         return false;
         } //end if
      else
         {
         return true;
         }
      } //end method isValid

   /**
    * Determines if the passed game board location is a safety square.
    *
    * @param y Y coordinate of desired game board location to check
    * @param X X coordinate of desired game board location to check
    * @return true if the location is a safety square and false if the location is not a safety square
    */
   public boolean isSafetySquare( int y, int x )
      {
      if( safetySquareY == y && safetySquareX == x )
         {
         return true;
         } //end if
      else
         {
         return false;
         } //end else
      } //end method isSafetySquare

   /**
    * Changes the number on the game board at the passed location. The method also updates the number of correct answers
    * currently on the board accordingly.
    *
    * @param y Y coordinate of desired game board number to change
    * @param X X coordinate of desired game board number to change
    */
   public void changeNum( int y, int x )
      {
      if( board[ y ][ x ] != null )
         {
         if( isCorrect( y, x ) ) //if the old number was correct, numOfCorrect is decremented
            {
            numOfCorrect--;
            } //end if

         board[ y ][ x ] = null;
         } //end if
      
      int randomCorrect = (int)( Math.random() * 2 );
      if( randomCorrect == 0 ) //enter correct num
         {
         board[ y ][ x ] = new Integer( correctNumsToFill.get( (int)( Math.random() * correctNumsToFill.size() ) ) );
         numOfCorrect++;
         } //end if
      if( randomCorrect == 1 ) //enter incorrect num
         {
         board[ y ][ x ] = new Integer( incorrectNumsToFill.get( (int)( Math.random() * incorrectNumsToFill.size() ) ) );
         } //end if
      } //end method changeNum

   /**
    * Changes the current safety square to a new location on the game board.
    */   
   public void refreshSafetySquare()
      {
      int numAdjacentTroggles = -1;

      while( numAdjacentTroggles != 0 )
         {
         safetySquareY = (int)( Math.random() * LENGTHY );
         safetySquareX = (int)( Math.random() * LENGTHX );

         numAdjacentTroggles = 0;
         for( Troggle t: gRef.getTroggles() )
            {
            if( Math.abs( safetySquareX - t.getX() ) <= 1 && Math.abs( safetySquareY - t.getY() ) <= 1 )
               {
               numAdjacentTroggles++;
               } //end if
            } //end for
         } //end while
      

      if( gRef.isActive() )
         {
         gRef.printStatus();
         } //end if
      } //end method refreshSafetySquare

   /**
    * Returns the value of the Integer in the game board at the passed location.
    *
    * @param y Y coordinate of desired game board number to return
    * @param X X coordinate of desired game board number to return
    * @return Integer value of the passed location
    */
   public Integer getNum( int y, int x )
      {
      return board[ y ][ x ];
      } //end method getNum

    /**
    * Changes the number on the game board at the passed location. The method also updates the number of correct answers
    * currently on the board accordingly.
    *
    * @param y Y coordinate of desired game board number to change
    * @param X X coordinate of desired game board number to change
    */
   public void deleteNum( int y, int x )
      {
      if( isCorrect( y, x ) )
         {
         numOfCorrect--;
         } //end if

      board[ y ][ x ] = null;

      gRef.printStatus();

      if( numOfCorrect == 0 )
         {
         gRef.advanceLevel();
         } //end if
      } //end method deleteNum
   
   /**
    * Cancels the safety square referesh timer.
    */
   public void cancelSafetySquareRefreshTimer()
      {
      safetySquareRefreshTimer.cancel();
      } //end method cancelTInitializationTimer

   /**
    * Returns the value of the current game number. The game number is the number for which the correct answers are based on.
    * For example, in the "Multiples of 2" game, 2 would be the numForGame value.
    *
    * @return value of the current game number numForGame
    */
   public int getNumForGame()
      {
      return numForGame;
      } //end method getNumForGame

   /**
    * Returns the value of the safety square's current Y location.
    *
    * @return value of the current safety square's Y coordinate
    */
   public int getSafetySquareY()
      {
      return safetySquareY;
      } //end method getSafetySquareY

   /**
    * Returns the value of the safety square's current X location.
    *
    * @return value of the current safety square's X coordinate
    */
   public int getSafetySquareX()
      { 
      return safetySquareX;
      } //end method getSafetySquareX

   /**
    * Returns the value of the current number of correct answers present on the board.
    *
    * @return value of the current number of correct answers
    */
   public int getNumCorrect()
      {
      return numOfCorrect;
      } //end method getNumCorrect
   } //end class GameBoard