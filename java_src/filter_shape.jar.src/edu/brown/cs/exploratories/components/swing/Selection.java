package edu.brown.cs.exploratories.components.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;

public class Selection
  extends JComponent
{
  private int handleSize = 6;
  private boolean handlesVisible = true;
  private Color selectionRectangleColor = Color.white;
  private Color selectionHandlesColor = Color.white;
  
  public Selection()
  {
    setLayout(null);
    setForeground(Color.white);
  }
  
  public int getHandleSize()
  {
    return this.handleSize;
  }
  
  public void setHandleSize(int paramInt)
  {
    this.handleSize = paramInt;
  }
  
  public boolean getHandlesVisible()
  {
    return this.handlesVisible;
  }
  
  public void setHandlesVisible(boolean paramBoolean)
  {
    this.handlesVisible = paramBoolean;
    repaint();
  }
  
  public Color getSelectionRectangleColor()
  {
    return this.selectionRectangleColor;
  }
  
  public void setSelectionRectangleColor(Color paramColor)
  {
    if (paramColor == null) {
      throw new NullPointerException();
    }
    this.selectionRectangleColor = paramColor;
    repaint();
  }
  
  public Color getSelectionHandlesColor()
  {
    return this.selectionHandlesColor;
  }
  
  public void setSelectionHandlesColor(Color paramColor)
  {
    if (paramColor == null) {
      throw new NullPointerException();
    }
    this.selectionHandlesColor = paramColor;
    repaint();
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    Insets localInsets = getInsets();
    double d1 = localInsets.top;
    double d2 = localInsets.left;
    double d3 = localInsets.bottom;
    double d4 = localInsets.right;
    double d5 = getHandleSize();
    double d6 = d5 / 2.0D;
    double d7 = getWidth();
    double d8 = getHeight();
    Color localColor = paramGraphics.getColor();
    paramGraphics.setColor(getSelectionRectangleColor());
    paramGraphics.drawRect((int)(d2 + d6), (int)(d1 + d6), (int)(d7 - d2 - d4 - d5), (int)(d8 - d1 - d3 - d5));
    if (getHandlesVisible())
    {
      paramGraphics.setColor(getSelectionHandlesColor());
      paramGraphics.fillRect((int)d2, (int)d1, (int)d5, (int)d5);
      paramGraphics.fillRect((int)d2, (int)(d8 / 2.0D - d6), (int)d5, (int)d5);
      paramGraphics.fillRect((int)d2, (int)(d8 - d1 - d5), (int)d5, (int)d5);
      paramGraphics.fillRect((int)(d7 / 2.0D - d6), (int)d1, (int)d5, (int)d5);
      paramGraphics.fillRect((int)(d7 / 2.0D - d6), (int)(d8 - d1 - d5), (int)d5, (int)d5);
      paramGraphics.fillRect((int)(d7 - d4 - d5), (int)d1, (int)d5, (int)d5);
      paramGraphics.fillRect((int)(d7 - d4 - d5), (int)(d8 / 2.0D - d6), (int)d5, (int)d5);
      paramGraphics.fillRect((int)(d7 - d4 - d5), (int)(d8 - d1 - d5), (int)d5, (int)d5);
    }
    paramGraphics.setColor(localColor);
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/Selection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */