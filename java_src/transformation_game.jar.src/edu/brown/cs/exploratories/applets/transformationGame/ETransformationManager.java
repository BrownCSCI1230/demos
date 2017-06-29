package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EAnimatable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public abstract interface ETransformationManager
  extends EAnimatable, MouseListener, MouseMotionListener
{
  public abstract void setGoalTransformList(ETransformList paramETransformList);
  
  public abstract void setCurrentTransformList(ETransformList paramETransformList);
  
  public abstract void setSelectedPiece(ETransPalettePiece paramETransPalettePiece);
  
  public abstract void clearCurrentTransformList();
  
  public abstract AffineTransform getCurrentCompositeTransform();
  
  public abstract AffineTransform getSelectedCompositeTransform();
  
  public abstract AffineTransform getGoalCompositeTransform();
  
  public abstract void update();
  
  public abstract void mouseClicked(MouseEvent paramMouseEvent);
  
  public abstract void mouseEntered(MouseEvent paramMouseEvent);
  
  public abstract void mouseExited(MouseEvent paramMouseEvent);
  
  public abstract void mousePressed(MouseEvent paramMouseEvent);
  
  public abstract void mouseReleased(MouseEvent paramMouseEvent);
  
  public abstract void mouseDragged(MouseEvent paramMouseEvent);
  
  public abstract void mouseMoved(MouseEvent paramMouseEvent);
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransformationManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */