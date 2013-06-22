/**
* The GameDefs class stores definitions for a particular game difficulty type. The default set of game definitions are easy and
* advanced grades 3-7, and grade 8 and above.
*/

public class GameDefs
   {
   private String myName;

   private int multiplesStart;
   private int multiplesEnd;

   private int factorsStart;
   private int factorsEnd;

   private int primesStart;
   private int primesEnd;

   private int maxMultiple;
   private boolean inOrder;
   
   /**
    * Constructs a GameDefs object with the specified parameters.
    *
    * @param name name for the game definitions
    * @param mStart value for the lowest possible multiples game number
    * @param mEnd value for the highest possible multiples game number
    * @param fStart value for the lowest possible factors game number
    * @param fEnd value for the highest possible factors game number
    * @param pStart value for the lowest possible primes game number
    * @param pStart value for the highest possible primes game number
    * @param mMult value for the maximum multiple for the multiples game number
    * @param inO boolean value true if the game numbers appear in order or false if the game numbers appear randomly
    */
   public GameDefs( String name, int mStart, int mEnd, int fStart, int fEnd, int pStart, int pEnd, int mMult, boolean inO )
      {
      myName = name;
      multiplesStart = mStart;
      multiplesEnd = mEnd;
      factorsStart = fStart;
      factorsEnd = fEnd;
      primesStart = pStart;
      primesEnd = pEnd;
      maxMultiple = mMult;
      inOrder = inO;
      } //end constructor GameDefs

   /**
    * Returns the value of the lowest possible multiples game number.
    *
    * @return value of the lowest possible multiples game number
    */
   public int getMultiplesStart()
      {  
      return multiplesStart;
      } //end method getMultiplesStart

   /**
    * Returns the value of the highest possible multiples game number.
    *
    * @return value of the highest possible multiples game number
    */
   public int getMultiplesEnd()
      {  
      return multiplesEnd;
      } //end method getMultiplesEnd

   /**
    * Returns the value of the lowest possible factors game number.
    *
    * @return value of the lowest possible factors game number
    */
   public int getFactorsStart()
      {  
      return factorsStart;
      } //end method getFactorsStart   
  
   /**
    * Returns the value of the highest possible factors game number.
    *
    * @return value of the highest possible factors game number
    */
   public int getFactorsEnd()
      {  
      return factorsEnd;
      } //end method getFactorsEnd

   /**
    * Returns the value of the lowest possible primes game number.
    *
    * @return value of the lowest possible primes game number
    */
   public int getPrimesStart()
      {  
      return primesStart;
      } //end method getPrimesStart

   /**
    * Returns the value of the highest possible primes game number.
    *
    * @return value of the highest possible primes game number
    */
   public int getPrimesEnd()
      {  
      return primesEnd;
      } //end method getPrimesEnd

   /**
    * Returns the value of the maximum multiple for the multiples game number.
    *
    * @return value of the maximum multiple for the multiples game number
    */
   public int getMaxMultiple()
      {
      return maxMultiple;
      } //end method getMaxMultiple

   /**
    * Returns boolean value representing whether the game numbers appear in order or not.
    *
    * @return true if the game numbers appear in order or false if the game numbers appear randomly
    */
   public boolean isInOrder()
      {  
      return inOrder;
      } //end method getInOrder

   /**
    * Returns the name for the game definitions.
    *
    * @return name for the game definitions
    */
   public String toString()
      {
      return myName;
      } //end method toString
   
   } //end class GameDefs