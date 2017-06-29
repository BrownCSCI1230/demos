package edu.brown.cs.exploratories.applets.transformationGame;

import java.util.Vector;

public class ETransLevel
  implements ETransConst
{
  protected int num;
  protected Vector availTrans;
  protected Vector solution;
  
  public ETransLevel(int paramInt)
  {
    this.num = paramInt;
    this.availTrans = new Vector();
    this.solution = new Vector();
  }
  
  public int getNum()
  {
    return this.num;
  }
  
  public void addAvailTrans(ETransform paramETransform)
  {
    this.availTrans.addElement(paramETransform);
  }
  
  public Vector getAvail()
  {
    return this.availTrans;
  }
  
  public void addSolution(ETransform paramETransform)
  {
    this.solution.addElement(paramETransform);
  }
  
  public Vector getSolution()
  {
    return this.solution;
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransLevel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */