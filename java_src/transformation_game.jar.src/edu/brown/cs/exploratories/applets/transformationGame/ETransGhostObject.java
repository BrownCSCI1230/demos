package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectTool;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectable;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ESelectionListener;

public class ETransGhostObject
  extends ETransGameObject
  implements ESelectionListener
{
  private boolean visibleFromPipeline = false;
  private boolean visibleFromToggle = true;
  
  public ETransGhostObject(ETransFactory paramETransFactory)
  {
    super(paramETransFactory);
    this.visibleImpl = this.factory.getGhostImpl();
    addMouseListener(this.transMan);
    addMouseMotionListener(this.transMan);
    ERadioSelectTool localERadioSelectTool = this.factory.getRadioSelectTool();
    localERadioSelectTool.addSelectionListener(this);
  }
  
  public void updateTransform()
  {
    this.myTransform = this.transMan.getSelectedCompositeTransform();
  }
  
  public void setVisibleFromPipeline(boolean paramBoolean)
  {
    this.visibleFromPipeline = paramBoolean;
    setVisible((this.visibleFromPipeline) && (this.visibleFromToggle));
  }
  
  public void setVisibleFromToggle(boolean paramBoolean)
  {
    this.visibleFromToggle = paramBoolean;
    setVisible((this.visibleFromPipeline) && (this.visibleFromToggle));
  }
  
  public void noteSelection(ERadioSelectable paramERadioSelectable)
  {
    try
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)paramERadioSelectable;
      setVisibleFromPipeline(true);
    }
    catch (ClassCastException localClassCastException)
    {
      setVisibleFromPipeline(false);
    }
    updateTransform();
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransGhostObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */