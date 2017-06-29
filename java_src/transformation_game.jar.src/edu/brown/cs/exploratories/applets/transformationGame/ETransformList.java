package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.geom.AffineTransform;
import java.util.Vector;

public class ETransformList
  implements ETransConst
{
  private Vector list = new Vector();
  
  public void update(int paramInt)
  {
    for (int i = 0; i < this.list.size(); i++) {
      ((ETransform)this.list.elementAt(i)).update(paramInt);
    }
  }
  
  public void clear()
  {
    this.list.clear();
  }
  
  public AffineTransform getCompositeTransform()
  {
    AffineTransform localAffineTransform = new AffineTransform();
    for (int i = this.list.size() - 1; i >= 0; i--)
    {
      ETransform localETransform = (ETransform)this.list.elementAt(i);
      localAffineTransform.concatenate(localETransform.getAffineTransform());
    }
    return localAffineTransform;
  }
  
  public AffineTransform getPartialCompositeTransform(ETransform paramETransform)
  {
    AffineTransform localAffineTransform = new AffineTransform();
    if (paramETransform.getType() == 99) {
      return localAffineTransform;
    }
    for (int i = this.list.indexOf(paramETransform); i >= 0; i--)
    {
      ETransform localETransform = (ETransform)this.list.elementAt(i);
      localAffineTransform.concatenate(localETransform.getAffineTransform());
    }
    return localAffineTransform;
  }
  
  public void addTransform(ETransform paramETransform)
  {
    this.list.add(paramETransform);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransformList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */