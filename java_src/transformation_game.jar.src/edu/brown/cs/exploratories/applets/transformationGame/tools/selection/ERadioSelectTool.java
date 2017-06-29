package edu.brown.cs.exploratories.applets.transformationGame.tools.selection;

import edu.brown.cs.exploratories.applets.transformationGame.tools.ETool;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ERadioSelectTool
  implements ETool
{
  private ERadioSelectable currentSelection;
  private Vector listenerList = new Vector();
  
  public void addSelectionListener(ESelectionListener paramESelectionListener)
  {
    this.listenerList.add(paramESelectionListener);
  }
  
  public void setSelected(ERadioSelectable paramERadioSelectable)
  {
    if (this.currentSelection != null) {
      this.currentSelection.deselect();
    }
    paramERadioSelectable.select();
    this.currentSelection = paramERadioSelectable;
    notifyListeners(paramERadioSelectable);
  }
  
  public ERadioSelectable getSelected()
  {
    return this.currentSelection;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    ERadioSelectable localERadioSelectable = (ERadioSelectable)paramMouseEvent.getSource();
    if (localERadioSelectable.isRadioSelectable())
    {
      if (this.currentSelection != null) {
        this.currentSelection.deselect();
      }
      localERadioSelectable.select();
      this.currentSelection = localERadioSelectable;
      notifyListeners(localERadioSelectable);
    }
  }
  
  private void notifyListeners(ERadioSelectable paramERadioSelectable)
  {
    for (int i = 0; i < this.listenerList.size(); i++)
    {
      ESelectionListener localESelectionListener = (ESelectionListener)this.listenerList.elementAt(i);
      localESelectionListener.noteSelection(paramERadioSelectable);
    }
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/selection/ERadioSelectTool.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */