import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
* The GUI class is the program's hub for the objects that the user interacts with. Once the Driver instantiates a GUI, the GUI
* and by extension the user has control over the game. The GUI creates a game, displays the windows, prints the current game status,
* calls methods if a KeyEvent or WindowEvent occurs, etc.
*/

public class GUI implements Runnable, KeyListener, ActionListener, WindowListener
   {
   private GameDrawPanel gameDrawPanel;
   private MenuDrawPanel menuDrawPanel;
   private Game myGame;
   private JFrame gameFrame;
   private JFrame menuFrame;
   private JPanel menuPanel;
   private JButton startButton;
   private JButton helpButton;
   private JLabel gameTypeLabel;
   private JComboBox gameTypeBox;
   private JLabel difficultyLabel;
   private JComboBox difficultyBox;
   private Color backgroundColor;

   private Thread guiThread;

   private Font myFont16;
   private Font myFont24;
   private Font myFont30;
   private FontMetrics fontMetrics16;
   private FontMetrics fontMetrics24;
   private FontMetrics fontMetrics30;

   private Image gameLogo;
   private Image muncherImage;
   private Image reggieImage;
   private Image bashfulImage;
   private Image helperImage;
   private Image workerImage;
   private Image smartieImage;

   private boolean gameOverMsgDisplayed;
   private boolean incorrectNumMsgDisplayed;
   private boolean troggleEatMsgDisplayed;
   private boolean gamePausedMsgDisplayed;

   private GameDefs gr3E;
   private GameDefs gr3A;
   private GameDefs gr4E;
   private GameDefs gr4A;
   private GameDefs gr5E;
   private GameDefs gr5A;
   private GameDefs gr6E;
   private GameDefs gr6A;
   private GameDefs gr7E;
   private GameDefs gr7A;
   private GameDefs gr8;
   private GameDefs[] gameDefs;

   /**
    * Constructs a GUI. Fonts are derived from the font passed by the driver, images are loaded and assigned to variables, the default game
    * definitions are initialized, and all display messages are set to false.
    *
    * @param dFont font file loaded by the Driver class
    */
   public GUI( Font dFont )
      {
      myFont16 = dFont.deriveFont( 16f );
      myFont24 = dFont.deriveFont( 24f );
      myFont30 = dFont.deriveFont( 30f ); 

      gameLogo = null;
      muncherImage = null;
      reggieImage = null;
      bashfulImage = null;
      helperImage = null;
      workerImage = null;
      smartieImage = null;

      try
         {
         gameLogo = ImageIO.read(new File( NumberMunchers.fileLocation + "GameLogo.png" ));
         muncherImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Muncher.png" ));
         reggieImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Reggie.png" ));
         bashfulImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Bashful.png" ));
         helperImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Helper.png" ));
         workerImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Worker.png" ));
         smartieImage = ImageIO.read(new File( NumberMunchers.fileLocation + "Smartie.png" ));
         } //end try
      catch( IOException e )
         {
         } //end catch

      backgroundColor = new Color( 9, 15, 102 );

      GameDefs gr3E = new GameDefs( "3rd grade easy",      2, 5, 3, 5, 2, 25, 12, true );
      GameDefs gr3A = new GameDefs( "3rd grade advanced",  2, 5, 3, 25, 2, 25, 12, true );
      GameDefs gr4E = new GameDefs( "4th grade easy",      2, 9, 3, 25, 2, 50, 12, true );
      GameDefs gr4A = new GameDefs( "4th grade advanced",  2, 9, 3, 64, 2, 50, 12, false );
      GameDefs gr5E = new GameDefs( "5th grade easy",      2, 9, 3, 64, 2, 50, 12, false );
      GameDefs gr5A = new GameDefs( "5th grade advanced",  2, 11, 3, 81, 2, 50, 12, false );
      GameDefs gr6E = new GameDefs( "6th grade easy",      2, 11, 3, 81, 2, 50, 12, false );
      GameDefs gr6A = new GameDefs( "6th grade advanced",  2, 12, 3, 99, 2, 99, 12, false );
      GameDefs gr7E = new GameDefs( "7th grade easy",      2, 12, 3, 99, 2, 99, 12, false );
      GameDefs gr7A = new GameDefs( "7th grade advanced",  2, 20, 3, 99, 2, 199, 12, false );
      GameDefs gr8  = new GameDefs( "8th grade and above", 2, 20, 3, 99, 2, 199, 12, false );

      gameDefs = new GameDefs[ 11 ];
      gameDefs[0] = gr3E;
      gameDefs[1] = gr3A;
      gameDefs[2] = gr4E;
      gameDefs[3] = gr4A;
      gameDefs[4] = gr5E;
      gameDefs[5] = gr5A;
      gameDefs[6] = gr6E;
      gameDefs[7] = gr6A;
      gameDefs[8] = gr7E;
      gameDefs[9] = gr7A;
      gameDefs[10] = gr8;

      gameOverMsgDisplayed = false;
      incorrectNumMsgDisplayed = false;
      troggleEatMsgDisplayed = false;  
      gamePausedMsgDisplayed = false;
      } //end constructor GUI

   /**
    * Runs the GUI. Two JFrames are created, one for the splash window and another for the game window. A drawPanel for the game is added to
    * the game's JFrame. 2 JComboBoxes and 2 JButtons are added to the splash window. The only window initially set to visible is the splash window.
    */
   public void go()
      {
      menuFrame = new JFrame( "Number Munchers" );
      menuFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      menuFrame.setLayout( null );
      menuFrame.setSize( 300, 300 );
      menuFrame.setResizable( false );
      menuFrame.setFocusable( true );

      menuPanel = new JPanel();
      menuPanel.setLayout( null );
      menuPanel.setLocation( 0, 101 );
      menuPanel.setSize( 300, 200 );
      menuFrame.getContentPane().add( menuPanel );

      menuDrawPanel = new MenuDrawPanel();
      menuDrawPanel.setSize( 300, 100 );
      menuDrawPanel.setLocation( 0, 0 );
      menuFrame.getContentPane().add( menuDrawPanel );

      startButton = new JButton( "play game" );
      menuPanel.add( startButton );
      startButton.setSize( startButton.getPreferredSize() );
      startButton.setLocation( ( menuFrame.getWidth() - startButton.getWidth() ) / 2 , 90 );
      startButton.addActionListener( this );

      helpButton = new JButton( "how to play" );
      menuPanel.add( helpButton );
      helpButton.setSize( helpButton.getPreferredSize() );
      helpButton.setLocation( ( menuFrame.getWidth() - helpButton.getWidth() ) / 2, 130 );
      helpButton.addActionListener( this );

      String[] difficultyTypes = new String[ 11 ];
      difficultyBox = new JComboBox( gameDefs );
      menuPanel.add( difficultyBox );
      difficultyBox.setSize( difficultyBox.getPreferredSize() );
      difficultyBox.setLocation( ( menuFrame.getWidth() - difficultyBox.getWidth() ) / 2 , 50 );

      String[] gameTypes = new String[ 3 ];
      gameTypes[0] = new String( "multiples" );
      gameTypes[1] = new String( "factors" );
      gameTypes[2] = new String( "primes" );
      gameTypeBox = new JComboBox( gameTypes );
      menuPanel.add( gameTypeBox );
      gameTypeBox.setSize( new Dimension( difficultyBox.getWidth(), (int) gameTypeBox.getPreferredSize().getHeight() ) );
      gameTypeBox.setLocation( ( menuFrame.getWidth() - gameTypeBox.getWidth() ) / 2 , 10 );
      
      menuFrame.setVisible( true );

      gameFrame = new JFrame( "Number Munchers" );
      gameFrame.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
      gameFrame.setSize( 640, 422 );
      gameFrame.setResizable( false );
      gameFrame.addWindowListener( this );

      gameDrawPanel = new GameDrawPanel();
      gameFrame.getContentPane().add( gameDrawPanel, BorderLayout.CENTER );
      } //end method go

   /**
    * Constructs and runs the game according to the parameters specified in the JComboBoxes from the splash window.
    *
    * @param gType int representing the game type ( 0 = multiples, 1 = factors, 2 = primes )
    * @param gDefs reference to the definitions for the difficulty level specified in the difficultyBox JComboBox
    */
   public void startGame( int gType, GameDefs gDefs )
      {
      boolean isActive = false;
      try
         {
         isActive = myGame.isActive();
         } //end try
      catch( NullPointerException e )
         {
         } //end catch
      
      if( !isActive )
         {
         myGame = new Game( gType, gDefs, this );

         myGame.runGame();

         guiThread = new Thread( (Runnable) this );
         guiThread.start();

         gameDrawPanel.setFocusable( true );
         gameFrame.setVisible( true );
         } //end if
      } //end method startGame
  
   /**
    * Updates the gameDrawPanel by calling the repaint() method.
    */
   public void update()
      {
      try
         {
         gameDrawPanel.repaint();
         } //end try
      catch ( Exception e )
         {
         } //end catch
      } //end method update
   
   /**
    * Specifies the method to be run in the GUI thread. The GUI has a thread designed to listen to the keyboard input from the user and
    * call the KeyPressed(), KeyReleased(), and KeyTyped() methods when a key event occurs.
    */
   public void run()
      {
      gameDrawPanel.addKeyListener( this );
      } //end method run

   /**
    * Displays the game over message by setting the message boolean to true and calling printStatus in myGame. The game is set to inactive.
    */
   public void displayGameOverMsg()
      {
      myGame.setActive( false );
      gameOverMsgDisplayed = true;
      myGame.printStatus();
      } //end method displayGameOverMsg

   /**
    * Displays the incorrect number message by setting the message boolean to true and calling printStatus in myGame. The game is set to inactive.
    */
   public void displayIncorrectNumMsg()
      {
      myGame.setActive( false );
      incorrectNumMsgDisplayed = true;
      myGame.printStatus();
      } //end method displayIncorrectNumMsg

   /**
    * Displays the game over message by setting the message boolean to true and calling printStatus in myGame. The game is set to inactive.
    */
   public void displayTroggleEatMsg()
      {
      myGame.setActive( false );
      troggleEatMsgDisplayed = true;
      myGame.printStatus();
      } //end method display troggleEatMsg

   /**
    * Displays the game paused message by setting the message boolean to true and calling printStatus in myGame. The game is set to inactive.
    */
   public void pauseGame()
      {
      myGame.setActive( false );
      gamePausedMsgDisplayed = true;
      myGame.printStatus();
      } //end method pauseGame

   /**
    * Hides the Game JFrame by setting its visibility boolean to false.
    */
   public void hideGameFrame()
      {
      gameFrame.setVisible( false );
      } //end method hideGameFrame

   /**
    * This method is called whenever a button in the splash window is pressed. If the "play game" button is pressed, the startGame() method
    * is called according to the parameters specified by the user in the JComboBoxes. If the "how to play" button is pressed, the application
    * runs a terminal command to open the file "howtoplay.pdf" located within the application's resources folder.
    *
    * @param event ActionEvent object reference
    */
   public void actionPerformed( ActionEvent event )
      {
      if( event.getActionCommand().equals( "play game" ) )
         {
         int gType = -1;
         if( gameTypeBox.getSelectedItem().equals( "multiples" ) )
            {
            gType = 0;
            } //end if
         if( gameTypeBox.getSelectedItem().equals( "factors" ) )
            {
            gType = 1;
            } //end if
         if( gameTypeBox.getSelectedItem().equals( "primes" ) )
            {
            gType = 2;
            } //end if
         startGame( gType, (GameDefs) difficultyBox.getSelectedItem() );
         } //end if
      if( event.getActionCommand().equals( "how to play" ) )
         {
         try
            {
            Desktop.getDesktop().open(new File(NumberMunchers.fileLocation + "howtoplay.pdf"));
            } //end try
         catch( Exception e )
            {
            //this method left blank intentionally
            } //end catch
         } //end if
      } //end method actionPerformed

   /**
    * Method left blank intentionally. Required implementation from KeyListener interface. 
    *
    * @param e KeyEvent object reference
    */
   public void keyPressed( KeyEvent e )
      {
      //this method left blank intentionally
      } //end method keyPressed

   /**
    * This method is called whenever a key is released on the keyboard. If the game is currently active, the keys pressed are interpreted
    * as character moves ( up, down, left, right, space/eat, esc/pause ). If the game is currently inactive, the only key registered is the
    * space bar to close a currently displayed message. When a message is closed, the corresponding action is performed according to the
    * message. For example, for the gameOverMessageDisplayed messsage, the process for ending the game is started after the message is closed.
    *
    * @param e KeyEvent object reference
    */
   public void keyReleased( KeyEvent e )
      {
      if( myGame.isActive() )
         {
         if( e.getKeyCode() == KeyEvent.VK_DOWN )
            {
            myGame.getMuncher().move( "down" ); 
            } //end if
         if( e.getKeyCode() == KeyEvent.VK_UP )
            {
            myGame.getMuncher().move( "up" );
            } //end if
         if( e.getKeyCode() == KeyEvent.VK_RIGHT )
            {
            myGame.getMuncher().move( "right" ); 
            } //end if
         if( e.getKeyCode() == KeyEvent.VK_LEFT )
            {
            myGame.getMuncher().move( "left" ); 
            } //end if
         if( e.getKeyCode() == KeyEvent.VK_SPACE )
            {
            myGame.getMuncher().eat();
            } //end if
         if( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            {
            pauseGame();
            } //end if
         } //end if
      else
         {
         if( gameOverMsgDisplayed )
            {
            if( e.getKeyCode() == KeyEvent.VK_SPACE )
               {
               gameOverMsgDisplayed = false;
               myGame.setActive( true );
               myGame.printStatus();
               myGame.endGame();
               } //end if
            } //end if
         if( incorrectNumMsgDisplayed )
            {
            if( e.getKeyCode() == KeyEvent.VK_SPACE )
               {
               incorrectNumMsgDisplayed = false;
               myGame.setActive( true );
               myGame.getMuncher().loseLife();
               myGame.printStatus();
               } //end if
            } //end if
         if( troggleEatMsgDisplayed )
            {
            if( e.getKeyCode() == KeyEvent.VK_SPACE )
               {
               troggleEatMsgDisplayed = false;
               
               if( myGame.getMuncher().getLives() > 0 )
                  {
                  for( Troggle t: myGame.getTroggles() )
                     {
                     if( t.getX() == myGame.getMuncher().getX() && t.getY() == myGame.getMuncher().getY() )
                        {
                        t.autoMove();
                        } //end if
                     } //end for
                  myGame.setActive( true );
                  } //end if

               myGame.resetMuncherRecentlyEaten();
               myGame.getMuncher().loseLife();

               myGame.printStatus();
               } //end if
            } //end if
         if( gamePausedMsgDisplayed )
            {
            if( e.getKeyCode() == KeyEvent.VK_SPACE )
               {
               gamePausedMsgDisplayed = false;
               myGame.setActive( true );
               myGame.printStatus();
               } //end if
            } //end if
         } //end if
      } //end method keyReleased
   
   /**
    * Method left blank intentionally. Required implementation from KeyListener interface. 
    *
    * @param e KeyEvent object reference
    */
   public void keyTyped( KeyEvent e )
      {
      //this method left blank intentionally
      } //end methodKeyTyped

   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowActivated( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowActivated

   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowClosed( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowClosed

   /**
    * This method is called whenever a window is closed in the application. This specifically applies to the Game JFrame. When a user closes
    * the Game window, the process for ending the game is executed.
    *
    * @param e WindowEvent object reference
    */
   public void windowClosing( WindowEvent e )
      {
      myGame.endGame();
      myGame.printStatus();
      myGame = null;
      gameOverMsgDisplayed = false;
      incorrectNumMsgDisplayed = false;
      troggleEatMsgDisplayed = false;  
      gamePausedMsgDisplayed = false;
      } //end method windowClosing

   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowDeactivated( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowDeactivated

   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowDeiconified( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowDeiconified

   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowIconified( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowIconified
  
   /**
    * Method left blank intentionally. Required implementation from WindowListener interface. 
    *
    * @param e WindowEvent object reference
    */
   public void windowOpened( WindowEvent e )
      {
      //this method left blank intentionally
      } //end method windowOpened

   /**
    * Cancels the GUI Thread.
    */
   public void stopGUIThread()
      {
      gameDrawPanel.removeKeyListener( this );
      guiThread.interrupt();
      guiThread = null;
      } //end method stopGUIThread

   /**
   * The inner class MenuDrawPanel is the panel added to the MenuFrame where the game logo is painted.
   */

   class MenuDrawPanel extends JPanel
      {
      /**
       * Paint method for the drawPanel in the splash window. It draws the "Number Munchers/A Game By Sean Harger" logo.
       *
       * @param g Graphics object reference
       */
      public void paintComponent( Graphics g )
         {
         g.drawImage( gameLogo, 0, 0, null );
         } //end method paintComponent
      } //end class MenuDrawPanel

   /**
   * The inner class GameDrawPanel is the panel added to the GameFrame where the current game status is printed.
   */

   class GameDrawPanel extends JPanel
      {
      /**
       * Paint method for the drawPanel in the game window. It draws the current state of the game including the current numbers, Muncher,
       * Troggles, safety square, current game type, current game number, number of lives, the current score, etc.
       *
       * @param g Graphics object reference
       */
      public void paintComponent( Graphics g )
         {     
         g.setFont( myFont16 );
         fontMetrics16 = g.getFontMetrics();
         
         g.setFont( myFont30 );
         fontMetrics30 = g.getFontMetrics();

         g.setFont( myFont24 );
         fontMetrics24 = g.getFontMetrics();
         
         //draws background
         g.setColor( backgroundColor );
         g.fillRect( 0, 0, 640, 400);

         //draws board outline
         g.setColor(Color.MAGENTA);
         g.fillRect( 37, 49, 586, 2 ); //top large
         g.fillRect( 41, 53, 578, 2 ); //top small
         g.fillRect( 37, 51, 2, 306 ); //left large
         g.fillRect( 41, 55, 2, 298 ); //left small
         g.fillRect( 37, 357, 586, 2 ); //bottom large
         g.fillRect( 41, 353, 578, 2 ); //bottom small
         g.fillRect( 621, 51, 2, 306 ); //right large
         g.fillRect( 617, 55, 2, 298 ); //right small
      
         //draws gridlines
         g.fillRect( 137, 55, 2, 298 ); //1st vertical
         g.fillRect( 233, 55, 2, 298 ); //2nd vertical
         g.fillRect( 329, 55, 2, 298 ); //3rd vertical
         g.fillRect( 425, 55, 2, 298 ); //4th vertical
         g.fillRect( 521, 55, 2, 298 ); //5th vertical

         g.fillRect( 43, 113, 574, 2 ); //1st horizontal
         g.fillRect( 43, 173, 574, 2 ); //2nd horizontal
         g.fillRect( 43, 233, 574, 2 ); //3rd horizontal
         g.fillRect( 43, 293, 574, 2 ); //4th horizontal
      
         g.setColor( Color.WHITE );
         g.drawString( "Level: " + myGame.getLevel(), 17, 31 );
         
         String gameTitle = new String();
         if( myGame.getGameType() == 0 ) //multiples
            {
            gameTitle += "Multiples of " + myGame.getBoard().getNumForGame();
            } //end if
         if( myGame.getGameType() == 1 ) //factors
            {
            gameTitle += "Factors of " + myGame.getBoard().getNumForGame();
            } //end if
         if( myGame.getGameType() == 2 )
            {
            gameTitle += "Prime Numbers";
            } //end if

         g.setColor( Color.WHITE );
         g.drawString( "Score: " + myGame.getScore(), 17, 386 );

         g.setColor( Color.WHITE );
         g.drawString( "Lives: " + myGame.getMuncher().getLives(), 520, 386 );

         //calculates center for title
         int centerX = gameFrame.getWidth()/2;
         Rectangle stringBounds = fontMetrics24.getStringBounds( gameTitle, g).getBounds();
         int textX = centerX - stringBounds.width/2;

         g.setColor( Color.WHITE );
         g.drawString( gameTitle, textX, 31 );

         int currentX = 43;
         int currentY = 91;
         for( int indexY = 0; indexY < GameBoard.LENGTHY; indexY++ )
            {
            for( int indexX = 0; indexX < GameBoard.LENGTHX; indexX++ )
               {
               if( myGame.getBoard().getNum( indexY, indexX ) != null )
                  {
                  centerX = 94/2;
                  stringBounds = fontMetrics24.getStringBounds( myGame.getBoard().getNum( indexY, indexX ).toString(), g ).getBounds();
                  textX = centerX - stringBounds.width/2;
   
                  g.setColor( Color.WHITE );
                  g.drawString( myGame.getBoard().getNum( indexY, indexX ).toString(), currentX + textX, currentY );
  
                  } //end if

               if( myGame.getMuncher().getY() == indexY && myGame.getMuncher().getX() == indexX )
                  {
                  g.drawImage( muncherImage, currentX, currentY - 36, null );
                  } //end if

               for( Troggle t : myGame.getTroggles() )
                  {
                  if( t.getY() == indexY && t.getX() == indexX )
                     {
                     if( t instanceof Reggie )
                        {
                        g.drawImage( reggieImage, currentX, currentY - 36, null );
                        } //end if
                     if( t instanceof Bashful )
                        {
                        g.drawImage( bashfulImage, currentX, currentY - 36, null );
                        } //end if
                     if( t instanceof Helper )
                        {
                        g.drawImage( helperImage, currentX, currentY - 36, null );
                        } //end if
                     if( t instanceof Worker )
                        {
                        g.drawImage( workerImage, currentX, currentY - 36, null );
                        } //end if
                     if( t instanceof Smartie )
                        {
                        g.drawImage( smartieImage, currentX, currentY - 36, null );
                        } //end if
                     } //end if
                  } //end for

               if( myGame.getBoard().isSafetySquare( indexY, indexX ) )
                  {
                  g.setColor( Color.WHITE );

                  g.fillRect( currentX, currentY - 36, 18, 2 ); //top left corner
                  g.fillRect( currentX, currentY - 34, 2, 16 ); //top left corner
                  
                  g.fillRect( currentX + 76, currentY - 36, 18, 2 ); //top right corner
                  g.fillRect( currentX + 92, currentY - 34, 2, 16 ); //top right corner

                  g.fillRect( currentX, currentY + 20, 18, 2 ); //bottom left corner
                  g.fillRect( currentX, currentY + 4, 2, 16 ); //bottom left corner
      
                  g.fillRect( currentX + 76, currentY + 20, 18, 2 ); //bottom right corner
                  g.fillRect( currentX + 92, currentY + 4, 2, 16 ); //bottom right corner
                  } //end if

               currentX += 96;
               } //end for
            currentX = 43;
            currentY += 60;
            } //end for

         if( gameOverMsgDisplayed || incorrectNumMsgDisplayed || troggleEatMsgDisplayed || gamePausedMsgDisplayed )
            {
            g.setColor( Color.BLUE );
            g.fillRect( 41, 173, 578, 62 );

            g.setColor( Color.WHITE );
            centerX = 640/2;
            if( gameOverMsgDisplayed )
               {
               g.setFont( myFont24 );
               stringBounds = fontMetrics24.getStringBounds( "Game Over.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Game Over.", textX, 201 );

               g.setFont( myFont16 );
               stringBounds = fontMetrics16.getStringBounds( "Press space to continue.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Press space to continue.", textX, 221 );
               } //end if
            if( incorrectNumMsgDisplayed )
               {
               g.setFont( myFont24 );
               stringBounds = fontMetrics24.getStringBounds( "Oops! You ate the wrong number.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Oops! You ate the wrong number.", textX, 201  );

               g.setFont( myFont16 );
               stringBounds = fontMetrics16.getStringBounds( "Press space to continue.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Press space to continue.", textX, 221 );
               } //end if
            if( troggleEatMsgDisplayed )
               {
               g.setFont( myFont24 );
               stringBounds = fontMetrics24.getStringBounds( "You were eaten by a troggle.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "You were eaten by a troggle.", textX, 201  );

               g.setFont( myFont16 );
               stringBounds = fontMetrics16.getStringBounds( "Press space to continue.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Press space to continue.", textX, 221 );
               } //end if
            if( gamePausedMsgDisplayed )
               {
               g.setFont( myFont24 );
               stringBounds = fontMetrics24.getStringBounds( "Game paused.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Game paused.", textX, 201  );

               g.setFont( myFont16 );
               stringBounds = fontMetrics16.getStringBounds( "Press space to continue.", g ).getBounds();
               textX = centerX - stringBounds.width/2;
               g.drawString( "Press space to continue.", textX, 221 );
               } //end if
            } //end if
         } //end method paintComponent
      } //end class GameDrawPanel
   } //end class GUI