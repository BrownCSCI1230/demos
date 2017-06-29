package edu.brown.cs.exploratories.applets.transformationGame;

public class ETransGoalObject
  extends ETransGameObject
{
  public ETransGoalObject(ETransFactory paramETransFactory)
  {
    super(paramETransFactory);
    this.visibleImpl = this.factory.getGoalImpl();
  }
  
  public void updateTransform()
  {
    this.myTransform = this.transMan.getGoalCompositeTransform();
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransGoalObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */