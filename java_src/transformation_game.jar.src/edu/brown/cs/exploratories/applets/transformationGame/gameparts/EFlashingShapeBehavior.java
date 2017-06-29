package edu.brown.cs.exploratories.applets.transformationGame.gameparts;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;

public class EFlashingShapeBehavior
  implements EBehavior, ActionListener
{
  private Vector shapes = new Vector();
  private Container parentContainer;
  private Timer timer = new Timer(this.DEFAULT_INTERVAL, this);
  private int DEFAULT_INTERVAL = 50;
  private boolean color1 = false;
  
  public void setParentContainer(Container paramContainer)
  {
    this.parentContainer = paramContainer;
  }
  
  public void addShape(EShape paramEShape)
  {
    this.shapes.add(paramEShape);
  }
  
  public void setFlashDelay(int paramInt)
  {
    this.timer.setDelay(paramInt);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    int i;
    EShape localEShape;
    if (this.color1)
    {
      for (i = 0; i < this.shapes.size(); i++)
      {
        localEShape = (EShape)this.shapes.elementAt(i);
        localEShape.setColorSpecial2();
      }
      this.color1 = false;
    }
    else
    {
      for (i = 0; i < this.shapes.size(); i++)
      {
        localEShape = (EShape)this.shapes.elementAt(i);
        localEShape.setColorSpecial();
      }
      this.color1 = true;
    }
    this.parentContainer.repaint();
  }
  
  public void execute()
  {
    this.timer.start();
  }
  
  public void reset()
  {
    this.timer.stop();
    for (int i = 0; i < this.shapes.size(); i++)
    {
      EShape localEShape = (EShape)this.shapes.elementAt(i);
      localEShape.setColorNormal();
    }
    this.parentContainer.repaint();
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/gameparts/EFlashingShapeBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */