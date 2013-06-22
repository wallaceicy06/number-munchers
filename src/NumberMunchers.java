import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

/**
* The Driver class sets up the program for execution. It loads the necessary font files, creates a GUI, and passes control
* of the program to the GUI object.
*/

public class NumberMunchers
   {
   public static String compileVersion;
   public static String fileLocation;

   /**
    * Runs the program by setting the initial parameters and creating a GUI.
    *
    * @param args Initial arguments
    */
   public static void main( String[] args ) throws Exception
      {
      if( args.length == 0 )
         {
         compileVersion = "JAR";
         } //end if
      else
         {
         compileVersion = new String( args[0] );
         } //end else
      fileLocation = new String();
      if( compileVersion.equals( "JAR" ) )
         {
         fileLocation += "res/";
         } //end if
      if( compileVersion.equals( "APP" ) )
         {
         fileLocation += "";
         } //end if

      File fontFile = new File( fileLocation + "Fixedsys500c.ttf");
      FileInputStream in = new FileInputStream( fontFile );
      Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);

      GUI myGUI = new GUI( dynamicFont );
      myGUI.go();
      } //end method main
   } //end class Driver