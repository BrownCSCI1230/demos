package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public abstract interface Highlightable
{
  public abstract boolean contains(int paramInt1, int paramInt2);
  
  public abstract void highlight(int paramInt1, int paramInt2);
  
  public abstract void highlightOff();
  
  public abstract int getX();
  
  public abstract int getY();
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/Highlightable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */