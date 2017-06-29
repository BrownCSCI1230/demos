package edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop;

import javax.swing.JComponent;

public abstract class EDragDropArea
  extends JComponent
{
  public abstract void dragOutOfContainer(EDragDroppable paramEDragDroppable);
  
  public abstract void dropIntoContainer(EDragDroppable paramEDragDroppable);
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/dragAndDrop/EDragDropArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */