package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Color;
import java.awt.Graphics;

public class OvalSamplePoint
  extends AbstractSamplePointImpl
{
  private static final Color transparent = new Color(0, 0, 0, 0);
  private boolean boo;
  
  public OvalSamplePoint(int paramInt1, int paramInt2, Color paramColor)
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
    paramGraphics.setColor(getInsideMarkerColor());
    paramGraphics.drawOval(i - 2 - getWidth() / 2, j - 3 - getHeight() / 2, getWidth() + 4, getHeight() + 4);
    paramGraphics.setColor(getOutsideMarkerColor());
    paramGraphics.drawOval(i - 3 - getWidth() / 2, j - 3 - getHeight() / 2, getWidth() + 6, getHeight() + 6);
    paramGraphics.setColor(getMarkerFillColor());
    paramGraphics.fillOval(i - getWidth() / 2, j - getHeight() / 2, getWidth(), getHeight());
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/OvalSamplePoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */