package edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop;

import edu.brown.cs.exploratories.applets.transformationGame.tools.ETool;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class EDragDropTool
  implements ETool
{
  private EDragDropSuperParent parentArea;
  private Vector dropAreaList = new Vector();
  private EDragDropArea originalContainer;
  private boolean currentlyDragging = false;
  
  public void setParentArea(EDragDropSuperParent paramEDragDropSuperParent)
  {
    this.parentArea = paramEDragDropSuperParent;
  }
  
  public void addDropArea(EDragDropArea paramEDragDropArea)
  {
    this.dropAreaList.add(paramEDragDropArea);
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (!this.currentlyDragging) {
      return;
    }
    EDragDroppable localEDragDroppable = (EDragDroppable)paramMouseEvent.getSource();
    Point localPoint1 = paramMouseEvent.getPoint();
    localPoint1 = SwingUtilities.convertPoint((JComponent)localEDragDroppable, localPoint1, this.parentArea);
    Object localObject = null;
    for (int i = 0; i < this.dropAreaList.size(); i++)
    {
      EDragDropArea localEDragDropArea = (EDragDropArea)this.dropAreaList.elementAt(i);
      Point localPoint2 = SwingUtilities.convertPoint(this.parentArea, localPoint1, localEDragDropArea);
      if (localEDragDropArea.contains(localPoint2)) {
        localObject = localEDragDropArea;
      }
    }
    if (localObject == null) {
      localObject = this.originalContainer;
    }
    this.parentArea.removeDragDroppable(localEDragDroppable);
    ((EDragDropArea)localObject).dropIntoContainer(localEDragDroppable);
    this.parentArea.repaint();
    this.currentlyDragging = false;
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    EDragDroppable localEDragDroppable = (EDragDroppable)paramMouseEvent.getSource();
    if (!localEDragDroppable.isDraggable()) {
      return;
    }
    if (!this.currentlyDragging)
    {
      localPoint = paramMouseEvent.getPoint();
      localPoint = SwingUtilities.convertPoint((JComponent)localEDragDroppable, localPoint, this.parentArea);
      this.originalContainer = ((EDragDropArea)((JComponent)localEDragDroppable).getParent());
      this.originalContainer.dragOutOfContainer(localEDragDroppable);
      this.parentArea.addDragDroppable(localEDragDroppable);
      ((JComponent)localEDragDroppable).setLocation(localPoint);
      this.currentlyDragging = true;
    }
    Point localPoint = paramMouseEvent.getPoint();
    if (localEDragDroppable.isMoveableAt((int)localPoint.getX(), (int)localPoint.getY()))
    {
      localPoint = SwingUtilities.convertPoint((JComponent)paramMouseEvent.getSource(), localPoint, ((JComponent)paramMouseEvent.getSource()).getParent());
      ((JComponent)paramMouseEvent.getSource()).setLocation(localPoint);
    }
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/tools/dragAndDrop/EDragDropTool.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */