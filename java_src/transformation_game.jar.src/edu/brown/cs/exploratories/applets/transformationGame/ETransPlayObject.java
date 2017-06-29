package edu.brown.cs.exploratories.applets.transformationGame;

public class ETransPlayObject
  extends ETransGameObject
{
  public ETransPlayObject(ETransFactory paramETransFactory)
  {
    super(paramETransFactory);
    this.visibleImpl = this.factory.getPlayImpl();
  }
  
  public void updateTransform()
  {
    this.myTransform = this.transMan.getCurrentCompositeTransform();
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPlayObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */