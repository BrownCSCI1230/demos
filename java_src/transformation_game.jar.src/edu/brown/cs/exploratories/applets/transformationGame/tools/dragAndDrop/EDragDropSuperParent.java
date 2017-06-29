package edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop;

import javax.swing.JComponent;

public abstract class EDragDropSuperParent
  extends JComponent
{
  public abstract void addDragDroppable(EDragDroppable paramEDragDroppable);
  
  public abstract void removeDragDroppable(EDragDroppable paramEDragDroppable);
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/dragAndDrop/EDragDropSuperParent.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */