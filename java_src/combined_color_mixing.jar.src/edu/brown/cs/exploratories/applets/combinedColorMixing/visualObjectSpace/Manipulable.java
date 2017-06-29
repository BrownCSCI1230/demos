package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public abstract interface Manipulable
{
  public abstract boolean contains(int paramInt1, int paramInt2);
  
  public abstract void mousePressed(int paramInt1, int paramInt2);
  
  public abstract void mouseDragged(int paramInt1, int paramInt2);
  
  public abstract void mouseReleased(int paramInt1, int paramInt2);
  
  public abstract int getX();
  
  public abstract int getY();
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/Manipulable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */