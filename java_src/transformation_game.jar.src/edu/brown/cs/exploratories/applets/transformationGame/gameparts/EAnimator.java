package edu.brown.cs.exploratories.applets.transformationGame.gameparts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;

public class EAnimator
  implements ActionListener
{
  private Timer javaTimer = new Timer(this.DEFAULT_INTERVAL, this);
  private int DEFAULT_INTERVAL = 100;
  private Vector list = new Vector();
  
  public void start()
  {
    this.javaTimer.start();
  }
  
  public void setInterval(int paramInt)
  {
    this.javaTimer.setDelay(paramInt);
  }
  
  public void addAnimatable(EAnimatable paramEAnimatable)
  {
    this.list.add(paramEAnimatable);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    for (int i = 0; i < this.list.size(); i++)
    {
      EAnimatable localEAnimatable = (EAnimatable)this.list.elementAt(i);
      localEAnimatable.update();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/gameparts/EAnimator.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */