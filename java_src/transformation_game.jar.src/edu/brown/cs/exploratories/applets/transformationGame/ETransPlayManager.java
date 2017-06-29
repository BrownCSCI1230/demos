package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EBehavior;
import java.awt.geom.AffineTransform;
import java.util.Vector;

public class ETransPlayManager
{
  private ETransFactory factory;
  private AffineTransform goalAT;
  private AffineTransform testAT;
  private ETransformComparator matcher;
  private Vector winBehaviors;
  
  public ETransPlayManager(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.matcher = this.factory.getTransformComparator();
    this.winBehaviors = new Vector();
  }
  
  public void addWinBehavior(EBehavior paramEBehavior)
  {
    this.winBehaviors.add(paramEBehavior);
  }
  
  public void userMoved()
  {
    if (winConditionTrue()) {
      activateWinBehaviors();
    }
  }
  
  private boolean winConditionTrue()
  {
    ETransformationManager localETransformationManager = this.factory.getTransformationManager();
    this.goalAT = localETransformationManager.getGoalCompositeTransform();
    this.testAT = localETransformationManager.getCurrentCompositeTransform();
    return this.matcher.transformsMatch(this.goalAT, this.testAT);
  }
  
  private void activateWinBehaviors()
  {
    for (int i = 0; i < this.winBehaviors.size(); i++)
    {
      EBehavior localEBehavior = (EBehavior)this.winBehaviors.elementAt(i);
      localEBehavior.execute();
    }
  }
  
  public void reset()
  {
    for (int i = 0; i < this.winBehaviors.size(); i++)
    {
      EBehavior localEBehavior = (EBehavior)this.winBehaviors.elementAt(i);
      localEBehavior.reset();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPlayManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */