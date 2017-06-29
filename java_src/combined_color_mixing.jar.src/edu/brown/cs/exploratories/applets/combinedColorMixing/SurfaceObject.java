package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class SurfaceObject
  extends VisualObject
  implements LightCarrier
{
  private Color inColor;
  private Color outColor;
  private Color myColor;
  private Polygon surface;
  private Filter filter;
  private Image norm;
  private boolean highlightStatus;
  private LightCarrier nextLink;
  
  public SurfaceObject(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, LightCarrier paramLightCarrier)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    this.norm = paramObjectSpace.loadImage("surfaceObj");
    paramObjectSpace.finishLoading();
    this.nextLink = paramLightCarrier;
    this.target = new Rectangle(0, 0, this.norm.getWidth(paramObjectSpace.getImageObserver()), this.norm.getHeight(paramObjectSpace.getImageObserver()));
    int[] arrayOfInt1 = { this.x + 18, this.x + 52, this.x + 83, this.x + 56 };
    int[] arrayOfInt2 = { this.y + 10, this.y + 10, this.y + 83, this.y + 103 };
    this.surface = new Polygon(arrayOfInt1, arrayOfInt2, 4);
    this.myColor = Color.white;
    paramObjectSpace.drawImageToBackground(this.x, this.y, this.norm);
  }
  
  public void receiveLight(Color paramColor)
  {
    this.inColor = paramColor;
    updateColor();
  }
  
  public void changeColor(Color paramColor)
  {
    this.myColor = paramColor;
    updateColor();
  }
  
  public void updateColor()
  {
    this.outColor = new Color(Math.max(this.inColor.getRed() - (255 - this.myColor.getRed()), 0), Math.max(this.inColor.getGreen() - (255 - this.myColor.getGreen()), 0), Math.max(this.inColor.getBlue() - (255 - this.myColor.getBlue()), 0));
    this.nextLink.receiveLight(this.outColor);
  }
  
  public int getTargetX()
  {
    return this.x + 40;
  }
  
  public int getTargetY()
  {
    return this.y - 30;
  }
  
  public void mouseClicked(int paramInt1, int paramInt2) {}
  
  public Point getReceptionPoint()
  {
    return new Point(this.x + 50, this.y + 40);
  }
  
  public Point getEmissionPoint()
  {
    return new Point(this.x + 50, this.y + 40);
  }
  
  public Color getColor()
  {
    return this.outColor;
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = ((int)(this.x * paramDouble1));
    this.y = ((int)(this.y * paramDouble2));
    int[] arrayOfInt1 = { this.x + 18, this.x + 52, this.x + 83, this.x + 56 };
    int[] arrayOfInt2 = { this.y + 10, this.y + 10, this.y + 83, this.y + 103 };
    this.surface = new Polygon(arrayOfInt1, arrayOfInt2, 4);
    this.space.drawImageToBackground(this.x, this.y, this.norm);
  }
  
  public void draw()
  {
    this.graphics.setColor(this.myColor);
    this.space.fillShape(this.surface);
    if (this.myColor.equals(Color.black))
    {
      this.graphics.setColor(Color.gray);
      this.space.drawShape(this.surface);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/SurfaceObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */