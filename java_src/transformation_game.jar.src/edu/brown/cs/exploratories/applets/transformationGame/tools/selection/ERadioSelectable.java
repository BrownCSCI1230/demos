package edu.brown.cs.exploratories.applets.transformationGame.tools.selection;

import edu.brown.cs.exploratories.applets.transformationGame.tools.EToolable;

public abstract interface ERadioSelectable
  extends EToolable
{
  public abstract boolean isRadioSelectable();
  
  public abstract void setRadioSelectable(boolean paramBoolean);
  
  public abstract void select();
  
  public abstract void deselect();
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/selection/ERadioSelectable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */