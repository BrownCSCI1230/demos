package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.ELevelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ETransControlPanel
  extends JPanel
  implements ETransConst, ActionListener
{
  private JButton nextButton;
  private JButton prevButton;
  private JTextField levelNumberField;
  private JCheckBox ghostBox;
  private JButton resetButton;
  private ETransFactory factory;
  private ELevelManager levelMan;
  
  public ETransControlPanel(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.levelMan = this.factory.getLevelManager();
    setSize(500, 100);
    this.nextButton = new JButton("Next");
    this.prevButton = new JButton("Prev");
    this.nextButton.addActionListener(this);
    this.prevButton.addActionListener(this);
    this.nextButton.setBackground(ETransConst.E_TRANS_NEXT_COLOR);
    this.prevButton.setBackground(ETransConst.E_TRANS_PREV_COLOR);
    this.levelNumberField = new JTextField(2);
    this.ghostBox = new JCheckBox("Ghost");
    this.ghostBox.addActionListener(this);
    this.ghostBox.setSelected(true);
    this.resetButton = new JButton("Reset");
    this.resetButton.setBackground(ETransConst.E_TRANS_RESET_COLOR);
    this.resetButton.addActionListener(this);
    add(this.prevButton);
    add(this.nextButton);
    add(this.ghostBox);
    add(this.resetButton);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.nextButton)
    {
      this.levelMan.loadNextLevel();
    }
    else if (paramActionEvent.getSource() == this.prevButton)
    {
      this.levelMan.loadPrevLevel();
    }
    else
    {
      Object localObject;
      if (paramActionEvent.getSource() == this.ghostBox)
      {
        localObject = this.factory.getGhostObject();
        if (this.ghostBox.isSelected()) {
          ((ETransGhostObject)localObject).setVisibleFromToggle(true);
        } else {
          ((ETransGhostObject)localObject).setVisibleFromToggle(false);
        }
      }
      else if (paramActionEvent.getSource() == this.resetButton)
      {
        localObject = this.factory.getPaletteArea();
        ((ETransPaletteArea)localObject).resetPieces();
      }
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransControlPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */