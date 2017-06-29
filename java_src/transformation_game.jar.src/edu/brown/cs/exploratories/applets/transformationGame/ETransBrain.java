package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EBehavior;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.ELevelManager;

public class ETransBrain
  implements ELevelManager
{
  private ELevelManager levelMan;
  private ETransFactory factory;
  
  public ETransBrain(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.levelMan = this.factory.getLevelManager();
  }
  
  public void startGame()
  {
    this.levelMan.loadLevel(1);
  }
  
  public void addWinBehavior(EBehavior paramEBehavior) {}
  
  public void userMoved() {}
  
  public void loadNextLevel()
  {
    this.levelMan.loadNextLevel();
  }
  
  public void loadPrevLevel()
  {
    this.levelMan.loadPrevLevel();
  }
  
  public void loadLevel(int paramInt)
  {
    this.levelMan.loadLevel(paramInt);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransBrain.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */