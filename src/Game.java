import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
* The Game class is the main hub for everything that interacts with the Game. The Game class calls methods to create
* a gameBoard and add a Muncher and Troggles to the game. It keeps track of the current score and the current level.
* It serves as a hub in the sense that each object created by Game is given a game reference variable (gRef) that can 
* refer back to this object so that all objects can call methods in the Game class or in any object for which the Game 
* class instantiates. 
*/

public class Game
   {
   private GUI myGUI;
   private GameBoard myBoard;
   private GameDefs gameDefs;

   private Muncher myMuncher;
   private ArrayList<Troggle> myTroggles;

   private int gameType; //number which corresponds to a specific game type
   private boolean isActive;

   private int score;
   private int level;

   private boolean muncherRecentlyEaten;

   private Timer tInitializationTimer;
   private TimerTask tInitializationTask;

   /**
    * Constructs a game.
    * 
    * @param gType int representing the game type ( 0 = multiples, 1 = factors, 2 = primes )
    * @param gDefs reference to the definitions for the difficulty specified by the user in the splash screen
    * @param g reference to the GUI class
    */

   public Game( int gType, GameDefs gDefs, GUI g )
      {
      score = 0;
      level = 1;
      gameType = gType;
      gameDefs = gDefs;
      myGUI = g;

      myTroggles = new ArrayList<Troggle>();
      //this parameter for gRef variable to point back to this class
      myBoard = new GameBoard( this );
      myMuncher = new Muncher( this );
      } //end constructor Game

   /**
    * Checks each troggle to see if a muncher is currently present at its location. If a muncher is present, the eatMuncher()
    * method is called for that troggle. To prevent ConcurrentModificationException errors from being thrown, each time the
    * eatMuncher() method is called, a boolean variable muncherRecentlyEaten is set to true.
    */

   public void checkBoard()
      {
      for( Troggle t: myTroggles )
         {
         if( t.isMuncherPresent() )
            {
            if( !muncherRecentlyEaten )
               {
               t.eatMuncher();
               muncherRecentlyEaten = true;
               } //end if
            } //end if
         } //end for
      } //end method checkBoard

   /**
    * Returns the muncherRecentlyEaten boolean variable.
    */
   public boolean getMuncherRecentlyEaten()
      {
      return muncherRecentlyEaten;
      } //end method getMuncherRecentlyEaten
   
   /**
    * Resets the muncherRecentlyEaten boolean variable.
    */
   public void resetMuncherRecentlyEaten()
      {
      muncherRecentlyEaten = false;
      } //end method setMuncherRecentlyEaten

   /**
    * Starts the constructed game.
    */
   public void runGame()
      {  
      isActive = true; //set the game status to active
      muncherRecentlyEaten = false;

      startTInitializationTimer();

      printStatus();
      } //end method runGame

   /**
    * Returns the isActive boolean variable.
    */
   public boolean isActive()
      {
      return isActive;
      } //end method isActive

   /**
    * Sets the isActive boolean variable.
    *
    * @param a boolean of the desired value to be set to isActive
    */
   public void setActive( boolean a )
      {
      isActive = a;
      } //end method setActive

   /**
    * Adds a random troggle to the game board. The variety of troggles possible depends on the current level, with only weak
    * troggles possible on the lower levels and smart troggles possible on the higher levels. (level 1: Reggies only; 
    * levels 2-3: Reggies, Bashfuls; levels 4-6: Reggies, Bashfuls, Helpers; levels 7-9: Reggies, Bashfuls, Helpers, Workers; 
    * levels 10 and above: Reggies, Bashfuls, Helpers, Workers, Smarties
    */
   public void addTroggle()
      {
      int possibleTroggles = 0;

      if( level == 1 )
         {
         possibleTroggles = 1;
         } //end if
      else if( level > 1 && level <= 3 )
         {
         possibleTroggles = 2;
         } //end if
      else if( level > 3 && level <= 6 )
         {
         possibleTroggles = 3;
         } //end if
      else if( level > 6 && level <= 9 )
         {
         possibleTroggles = 4;
         } //end if
      else
         {
         possibleTroggles = 5;
         } //end else

      int randomTroggle = (int)( Math.random() * possibleTroggles );

      if( randomTroggle == 0 )
         {
         myTroggles.add( new Reggie( this ) );
         } //end if
      if( randomTroggle == 1 )
         {
         myTroggles.add( new Bashful( this ) );
         } //end if
      if( randomTroggle == 2 )
         {
         myTroggles.add( new Helper( this ) );
         } //end if
      if( randomTroggle == 3 )
         {
         myTroggles.add( new Worker( this ) );
         } //end if
      if( randomTroggle == 4 )
         {
         myTroggles.add( new Smartie( this ) );
         } //end if
      } //end method addTroggle
   
   /**
    * Deletes the specified troggle from the ArrayList myTroggles and effectively removes it from the game board.
    * 
    * @param t troggle that is desired to be deleted
    */
   public void deleteTroggle( Troggle t )
      {
      myTroggles.remove( t );
      printStatus();
      } //end method deleteTroggle

   /**
    * Deletes all of the troggles in the ArrayList myTroggles and effectively removes them all from the game board.
    */
   public void deleteAllTroggles()
      {
      for( Troggle t: myTroggles)
         {
         t.cancelTimer();
         } //end for
      myTroggles.clear();
      } //end method deleteAllTroggles

   /**
    * Adds the appropriate score if a correct answer is eaten. The amount of the score depends on the level.
    */
   public void addScore()
      {
      if( level >= 1 && level <= 3 )
         {
         score += 5;
         } //end if
      if( level >= 4 && level <= 6 )
         {
         score += 10;
         } //end if
      if( level >= 7 && level <= 9 )
         {
         score += 25;
         } //end if
      if( level >= 10 )
         {
         score += 50;
         } //end if
      } //end addScore

   /**
    * Ends the game, clears the board, cancels all timers and threads, and returns user to splash screen.
    */
   public void endGame()
      {
      isActive = false;
      myBoard.clearBoard();   
      myBoard.cancelSafetySquareRefreshTimer();
      cancelTInitializationTimer();
      myGUI.stopGUIThread();
      myGUI.hideGameFrame();
      deleteAllTroggles();
      printStatus();
      } //end method endLevel

   /**
    * Prepares the game board for the next level. It cancels the troggle initialization timer, clears the board, assigns
    * the muncher a new start location, refills the board, and restarts the troggle initialization timer.
    */
   public void advanceLevel()
      {
      isActive = false;
      deleteAllTroggles();
      cancelTInitializationTimer();
      myBoard.clearBoard();

      level++;
      myMuncher.setStartLocation();
      myBoard.fillBoard();
      isActive = true;
      startTInitializationTimer();

      printStatus(); 
      } //end method advanceLevel

   /**
    * Initializes and runs a new troggle initialization timer. This timer is responsible for adding a new troggle to the
    * game board until the maximum amount of troggles for that level has been reached. ( levels 1-3: 1 troggle; levels
    * 4-6: 2 troggles; levels 7 and above: 3 troggles )
    */
   public void startTInitializationTimer()
      {
      tInitializationTask = new TimerTask()
         {
         public void run()
            {
            if( isActive )
               {
               if( level > 0 && level <= 3 )
                  {
                  if( myTroggles.size() < 1 )
                     {
                     addTroggle();
                     } //end if
                  } //end if
               else if( level > 3 && level <= 6 )
                  {
                  if( myTroggles.size() < 2 )
                     {
                     addTroggle();
                     } //end if
                  } //end if
               else
                  {
                  if( myTroggles.size() < 3 ) 
                     {
                     addTroggle();
                     } //end if
                  } //end if
               } //end else
            } //end method run
         }; //end class TimerTask

      tInitializationTimer = new Timer();
      tInitializationTimer.schedule( tInitializationTask, 3000, 2000 );
      } //end method startTInitializationTimer
   
   /**
    * Cancels the troggle initialization timer and sets the timer and its task references to null.
    */
   public void cancelTInitializationTimer()
      {
      tInitializationTimer.cancel();
      tInitializationTimer = null;
      tInitializationTask = null;
      } //end method cancelTInitializationTimer

   /**
    * Returns a reference to the GameBoard object.
    * 
    * @return reference to the GameBoard object myBoard
    */
   public GameBoard getBoard()
      {
      return myBoard;
      } //end method getBoard

   /**
    * Returns a reference to the GUI object.
    *
    * @return reference to the GUI object myGUI
    */
   public GUI getMyGUI()
      {
      return myGUI;
      } //end method getMyGUI

   /**
    * Returns a reference to the ArrayList of troggles.
    *
    * @return reference to the ArrayList myTroggles.
    */
   public ArrayList<Troggle> getTroggles()
      {
      return myTroggles;
      } //end method getTroggles

    /**
    * Returns value of the int of the current game type. ( 0 = multiples, 1 = factors, 2 = primes )
    *
    * @return int value of the current game type variable gameType
    */
   public int getGameType()
      {
      return gameType;
      } //end method getGameType

   /**
    * Returns a reference to the game definitions object.
    *
    * @return reference to the game definitions object gameDefs
    */
   public GameDefs getGameDefs()
      {
      return gameDefs;
      } //end method getGameDifficulty

   /**
    * Returns value of the int of the current level.
    *
    * @return int value of the current level
    */
   public int getLevel()
      {
      return level;
      } //end method getLevel

   /**
    * Returns value of the int of the current score.
    *
    * @return int value of the current score
    */
   public int getScore()   
      {
      return score;
      } //end method getScore

   /**
    * Returns reference to Muncher object.
    *
    * @return reference to Muncher object myMuncher
    */
   public Muncher getMuncher()
      {
      return myMuncher;
      } //end method getMuncher

   /**
    * Prints current status of game in the console window and calls the GUI method update() to repaint the user
    * interface being seen by the game player.
    */
   public void printStatus()
      {
      for( int indexY = 0; indexY < GameBoard.LENGTHY; indexY++ )
         {
         for( int indexX = 0; indexX < GameBoard.LENGTHX; indexX++ )
            {
            boolean printed = false;
            for( Troggle t : myTroggles )
               {
               if( t.getY() == indexY && t.getX() == indexX )
                  {
                  System.out.print( t + "\t" );
                  printed = true;
                  } //end if
               } //end for
            if( !printed && myMuncher.getY() == indexY && myMuncher.getX() == indexX )
               {
               if( myBoard.isSafetySquare( indexY, indexX ) )
                  {
                  System.out.print( "[" );
                  } //end if
               System.out.print( myMuncher );
               if( myBoard.isSafetySquare( indexY, indexX ) )
                  {
                  System.out.print( "]" );
                  } //end if
               System.out.print( "\t" );

               printed = true;
               } //end if
            
            if( !printed )
               {
               if( myBoard.isSafetySquare( indexY, indexX ) )
                  {
                  System.out.print( "[" );
                  } //end if
               System.out.print( myBoard.getNum( indexY, indexX ) );
               if( myBoard.isSafetySquare( indexY, indexX ) )
                  {
                  System.out.print( "]" );
                  } //end if
               System.out.print( "\t" );
               } //end if
            } //end for
         System.out.println( "\n\n\n" );
         } //end for

      String gameTitle = new String();
      if( gameType == 0 ) //multiples
         {
         gameTitle += "Multiples of " + myBoard.getNumForGame();
         } //end if
      if( gameType == 1 ) //factors
         {
         gameTitle += "Factors of " + myBoard.getNumForGame();
         } //end if
      if( gameType == 2 )
         {
         gameTitle += "Prime Numbers";
         } //end if
      System.out.println( gameTitle );
      System.out.println( "Score: " + score );
      System.out.println( "Lives: " + myMuncher.getLives() );
      System.out.println( "Num Correct: " + myBoard.getNumCorrect() );
      System.out.println( "Game is active: " + isActive );
      System.out.print( "Troggles: " );
      for( Troggle t : myTroggles )
         {
         System.out.print( t );
         } //end for
      System.out.println( "\n\n\n" );

      myGUI.update();
      } //end method printStatus
   } //end class Game