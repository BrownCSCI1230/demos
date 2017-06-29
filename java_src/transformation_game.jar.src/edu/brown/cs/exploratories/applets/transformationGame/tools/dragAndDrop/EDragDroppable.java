package edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop;

import edu.brown.cs.exploratories.applets.transformationGame.tools.EToolable;

public abstract interface EDragDroppable
  extends EToolable
{
  public abstract boolean isMoveableAt(int paramInt1, int paramInt2);
  
  public abstract boolean isDraggable();
  
  public abstract boolean isDroppableAt(int paramInt1, int paramInt2);
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/dragAndDrop/EDragDroppable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */