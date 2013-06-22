/**
* The Eat interface specifies that any Character who implements Eat must contain an eat method which allows the Character
* to eat numbers.
*/

public interface Eat
   {
   /**
    * Eats the number at the character's current location. The method differs depending on the character.
    */
   public abstract void eat();
   } //end interface Eat