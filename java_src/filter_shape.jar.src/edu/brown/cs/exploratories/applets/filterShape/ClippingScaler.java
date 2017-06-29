package edu.brown.cs.exploratories.applets.filterShape;

import edu.brown.cs.exploratories.components.swing.Selection;
import edu.brown.cs.exploratories.components.swing.SelectionListener;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class ClippingScaler
  extends JComponent
{
  private Selection selection;
  private SelectionListener selectionListener;
  
  public ClippingScaler()
  {
    setLayout(null);
    setSelection(new ResizableImageClipping());
    setSelectionListener(new SelectionListener());
  }
  
  public Selection getSelection()
  {
    return this.selection;
  }
  
  public void setSelection(Selection paramSelection)
  {
    if (this.selection != null) {
      remove(this.selection);
    }
    this.selection = paramSelection;
    if (this.selection != null) {
      add(this.selection);
    }
    if (this.selectionListener != null) {
      this.selectionListener.setSelection(this.selection);
    }
    positionSelection();
  }
  
  private void positionSelection()
  {
    if (this.selection != null)
    {
      Insets localInsets = getInsets();
      this.selection.setSize(4 * this.selection.getHandleSize(), 4 * this.selection.getHandleSize());
      this.selection.setLocation(localInsets.left, localInsets.top);
    }
  }
  
  private void positionSelection(Rectangle paramRectangle)
  {
    if (this.selection != null)
    {
      Insets localInsets = getInsets();
      int i = (int)(this.selection.getHandleSize() / 2.0D);
      double d1 = this.selection.getX() - paramRectangle.x;
      double d2 = this.selection.getY() - paramRectangle.y;
      double d3 = this.selection.getWidth();
      double d4 = this.selection.getHeight();
      Rectangle localRectangle = getBounds();
      double d5;
      double d6;
      double d7;
      double d8;
      if ((paramRectangle.width <= 0) || (paramRectangle.height <= 0))
      {
        d5 = localInsets.left;
        d6 = localInsets.top;
        d7 = 4 * this.selection.getHandleSize();
        d8 = 4 * this.selection.getHandleSize();
      }
      else
      {
        double d9 = d1 / paramRectangle.width;
        double d10 = d2 / paramRectangle.height;
        double d11 = d3 / paramRectangle.width;
        double d12 = d4 / paramRectangle.height;
        d5 = d9 * localRectangle.width + localRectangle.x;
        d6 = d10 * localRectangle.height + localRectangle.y;
        d7 = Math.max(d11 * localRectangle.width, 3 * this.selection.getHandleSize());
        d8 = Math.max(d12 * localRectangle.height, 3 * this.selection.getHandleSize());
      }
      this.selection.setSize((int)d7, (int)d8);
      this.selection.setLocation((int)d5, (int)d6);
    }
  }
  
  public SelectionListener getSelectionListener()
  {
    return this.selectionListener;
  }
  
  public void setSelectionListener(SelectionListener paramSelectionListener)
  {
    if (this.selectionListener != null)
    {
      this.selectionListener.setDragBounds(null);
      this.selectionListener.setSelection(null);
    }
    this.selectionListener = paramSelectionListener;
    if ((this.selectionListener != null) && (this.selection != null)) {
      this.selectionListener.setSelection(this.selection);
    }
    updateListenerDragBounds();
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Rectangle localRectangle = getBounds();
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    positionSelection(localRectangle);
    updateListenerDragBounds();
  }
  
  public void setBounds(Rectangle paramRectangle)
  {
    Rectangle localRectangle = getBounds();
    super.setBounds(paramRectangle);
    positionSelection(localRectangle);
    updateListenerDragBounds();
  }
  
  private void updateListenerDragBounds()
  {
    if ((this.selectionListener != null) && (this.selection != null))
    {
      Insets localInsets = getInsets();
      int i = (int)(this.selection.getHandleSize() / 2.0D);
      Rectangle localRectangle = new Rectangle();
      localRectangle.x = localInsets.left;
      localRectangle.y = localInsets.top;
      localRectangle.width = (getWidth() - localInsets.left - localInsets.right);
      localRectangle.height = (getHeight() - localInsets.top - localInsets.bottom);
      this.selectionListener.setDragBounds(localRectangle);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/ClippingScaler.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */