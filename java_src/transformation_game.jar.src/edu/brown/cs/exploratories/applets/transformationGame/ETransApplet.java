package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Container;

public class ETransApplet
  extends Exploratory
  implements ETransConst
{
  private ETransBrain brain;
  private ETransGamePanel visualPanel = this.factory.getTransGamePanel();
  private ETransFactory factory = new ETransFactory();
  
  public ETransApplet()
  {
    getContentPane().add(this.visualPanel);
    this.brain = this.factory.getTransBrain();
    this.brain.startGame();
    super.setPackingLayout(false);
    super.setSize(500, 600);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, ETransApplet.class);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransApplet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */