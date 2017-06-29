package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Color;
import java.awt.Graphics;

public class RectangleSamplePoint
  extends AbstractSamplePointImpl
{
  private static final Color transparent = new Color(0, 0, 0, 0);
  private boolean boo;
  
  public RectangleSamplePoint(int paramInt1, int paramInt2, Color paramColor)
  {
    super(paramInt1, paramInt2, paramColor);
    setX(paramInt1);
    setY(paramInt2);
    setBorderVisible(false);
  }
  
  public boolean getBorderVisible()
  {
    return this.boo;
  }
  
  public void setBorderVisible(boolean paramBoolean)
  {
    this.boo = paramBoolean;
    if (this.boo == true)
    {
      setOutsideMarkerColor(Color.black);
      setInsideMarkerColor(Color.white);
    }
    else
    {
      setOutsideMarkerColor(transparent);
      setInsideMarkerColor(transparent);
    }
  }
  
  public void paint(Graphics paramGraphics)
  {
    int i = getX();
    int j = getY();
    paramGraphics.setColor(getOutsideMarkerColor());
    paramGraphics.drawRect(i - 2, j - 2, getWidth() + 4, getHeight() + 4);
    paramGraphics.setColor(getInsideMarkerColor());
    paramGraphics.drawRect(i - 1, j - 1, getWidth() + 2, getHeight() + 2);
    paramGraphics.setColor(getMarkerFillColor());
    paramGraphics.fillRect(i, j, getWidth(), getHeight());
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/RectangleSamplePoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */