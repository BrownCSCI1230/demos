package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class ETransGamePanel
  extends JPanel
  implements ETransConst
{
  private ETransFactory factory;
  private ETransGraphicPanel graphicPanel;
  private ETransControlPanel controlPanel;
  
  public ETransGamePanel(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.graphicPanel = this.factory.getGraphicPanel();
    this.controlPanel = this.factory.getControlPanel();
    setLayout(new BorderLayout());
    add(this.controlPanel, "South");
    add(this.graphicPanel, "Center");
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransGamePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */