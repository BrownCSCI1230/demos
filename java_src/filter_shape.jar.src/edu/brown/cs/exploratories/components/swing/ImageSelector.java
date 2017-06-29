package edu.brown.cs.exploratories.components.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;

public class ImageSelector
  extends JComponent
{
  private ImageDisplayer imageDisplayer;
  private Selection selection;
  private SelectionListener selectionListener;
  private PropertyChangeListener imageDisplayerImagePropertyChangeListener = new PropertyChangeListener()
  {
    public void propertyChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent)
    {
      if ("image".equals(paramAnonymousPropertyChangeEvent.getPropertyName())) {
        ImageSelector.this.positionSelection();
      }
    }
  };
  
  public ImageSelector()
  {
    setLayout(null);
    setImageDisplayer(new CenteredImageDisplayer());
    setSelection(new Selection());
    setSelectionListener(new SelectionListener());
  }
  
  public BufferedImage getSelectedSubimage()
  {
    Rectangle localRectangle = this.imageDisplayer.calcImageRectangle(new Rectangle());
    int i = this.selection.getHandleSize();
    int j = this.selection.getX() - localRectangle.x;
    int k = this.selection.getY() - localRectangle.y;
    int m = this.selection.getWidth();
    int n = this.selection.getHeight();
    j += (int)(i / 2.0D);
    k += (int)(i / 2.0D);
    m -= i;
    n -= i;
    Image localImage = this.imageDisplayer.getImage();
    BufferedImage localBufferedImage = new BufferedImage(m, n, 2);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    localGraphics2D.drawImage(localImage, -j, -k, localRectangle.width, localRectangle.height, this);
    return localBufferedImage;
  }
  
  public ImageDisplayer getImageDisplayer()
  {
    return this.imageDisplayer;
  }
  
  public void setImageDisplayer(ImageDisplayer paramImageDisplayer)
  {
    if (this.imageDisplayer != null)
    {
      if (this.selection != null) {
        this.imageDisplayer.remove(this.selection);
      }
      this.imageDisplayer.removePropertyChangeListener(this.imageDisplayerImagePropertyChangeListener);
    }
    this.imageDisplayer = paramImageDisplayer;
    if (this.imageDisplayer != null) {
      add(this.imageDisplayer);
    }
    if (this.imageDisplayer != null)
    {
      if (this.selection != null) {
        this.imageDisplayer.add(this.selection);
      }
      this.imageDisplayer.addPropertyChangeListener(this.imageDisplayerImagePropertyChangeListener);
    }
    updateListenerDragBounds();
    positionSelection();
  }
  
  public Selection getSelection()
  {
    return this.selection;
  }
  
  public void setSelection(Selection paramSelection)
  {
    if ((this.imageDisplayer != null) && (this.selection != null)) {
      this.imageDisplayer.remove(this.selection);
    }
    this.selection = paramSelection;
    if ((this.imageDisplayer != null) && (this.selection != null)) {
      this.imageDisplayer.add(this.selection);
    }
    if (this.selectionListener != null) {
      this.selectionListener.setSelection(this.selection);
    }
    positionSelection();
  }
  
  private void positionSelection()
  {
    if ((this.imageDisplayer != null) && (this.selection != null))
    {
      Rectangle localRectangle = this.imageDisplayer.calcImageRectangle(new Rectangle());
      this.selection.setSize(4 * this.selection.getHandleSize(), 4 * this.selection.getHandleSize());
      this.selection.setLocation(localRectangle.x, localRectangle.y);
    }
  }
  
  private void positionSelection(Rectangle paramRectangle)
  {
    if ((this.imageDisplayer != null) && (this.selection != null))
    {
      Insets localInsets = this.imageDisplayer.getInsets();
      int i = (int)(this.selection.getHandleSize() / 2.0D);
      double d1 = this.selection.getX() - paramRectangle.x;
      double d2 = this.selection.getY() - paramRectangle.y;
      double d3 = this.selection.getWidth();
      double d4 = this.selection.getHeight();
      Rectangle localRectangle = this.imageDisplayer.calcImageRectangle(new Rectangle());
      double d5;
      double d6;
      double d7;
      double d8;
      if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
      {
        d5 = localRectangle.x;
        d6 = localRectangle.y;
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
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.imageDisplayer != null)
    {
      Rectangle localRectangle = this.imageDisplayer.calcImageRectangle(new Rectangle());
      Insets localInsets = getInsets();
      this.imageDisplayer.setBounds(localInsets.left, localInsets.top, paramInt3 - localInsets.left - localInsets.right, paramInt4 - localInsets.top - localInsets.bottom);
      positionSelection(localRectangle);
      updateListenerDragBounds();
    }
  }
  
  public void setBounds(Rectangle paramRectangle)
  {
    super.setBounds(paramRectangle);
    if (this.imageDisplayer != null)
    {
      Rectangle localRectangle1 = this.imageDisplayer.calcImageRectangle(new Rectangle());
      Insets localInsets = getInsets();
      Rectangle localRectangle2 = new Rectangle(localInsets.left, localInsets.left, paramRectangle.width - localInsets.left - localInsets.right, paramRectangle.height - localInsets.top - localInsets.bottom);
      this.imageDisplayer.setBounds(localRectangle2);
      positionSelection(localRectangle1);
      updateListenerDragBounds();
    }
  }
  
  public Dimension getPreferredSize()
  {
    Insets localInsets = getInsets();
    Dimension localDimension = null;
    if (this.imageDisplayer != null) {
      localDimension = this.imageDisplayer.getPreferredSize();
    } else {
      localDimension = new Dimension();
    }
    localDimension.width = (localDimension.width + localInsets.left + localInsets.right);
    localDimension.height = (localDimension.height + localInsets.top + localInsets.bottom);
    return localDimension;
  }
  
  private void updateListenerDragBounds()
  {
    if ((this.imageDisplayer != null) && (this.selectionListener != null) && (this.selection != null))
    {
      Rectangle localRectangle1 = this.imageDisplayer.calcImageRectangle(new Rectangle());
      Insets localInsets = this.imageDisplayer.getInsets();
      int i = (int)(this.selection.getHandleSize() / 2.0D);
      Rectangle localRectangle2 = new Rectangle();
      localRectangle2.x = Math.max(localInsets.left + localRectangle1.x - i, localInsets.left - i);
      localRectangle2.y = Math.max(localInsets.top + localRectangle1.y - i, localInsets.top - i);
      localRectangle2.width = Math.min(localRectangle1.width + this.selection.getHandleSize(), this.imageDisplayer.getWidth() - localInsets.left - localInsets.right + this.selection.getHandleSize());
      localRectangle2.height = Math.min(localRectangle1.height + this.selection.getHandleSize(), this.imageDisplayer.getHeight() - localInsets.top - localInsets.bottom + this.selection.getHandleSize());
      this.selectionListener.setDragBounds(localRectangle2);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/ImageSelector.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */