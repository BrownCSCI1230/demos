package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class FilterHolder
  extends VisualObject
  implements LightCarrier
{
  private Color inColor;
  private Color outColor;
  private Color myColor;
  private Filter filter;
  private Image norm;
  private boolean highlightStatus;
  private boolean ready;
  private LightCarrier nextLink;
  
  public FilterHolder(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, LightCarrier paramLightCarrier)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    paramObjectSpace.disableDrawable(this);
    this.norm = paramObjectSpace.loadImage("FilterHolder");
    paramObjectSpace.finishLoading();
    this.nextLink = paramLightCarrier;
    this.target = new Rectangle(0, 0, this.norm.getWidth(paramObjectSpace.getImageObserver()), this.norm.getHeight(paramObjectSpace.getImageObserver()));
    this.myColor = Color.white;
    paramObjectSpace.drawImageToBackground(this.x, this.y + 10, this.norm);
  }
  
  public void newFilterComing(Filter paramFilter)
  {
    if (this.filter != null) {
      this.filter.goHome();
    }
    this.filter = paramFilter;
  }
  
  public Filter getFilter()
  {
    return this.filter;
  }
  
  public void receiveLight(Color paramColor)
  {
    this.inColor = paramColor;
    updateColor();
  }
  
  public void holdFilter(Filter paramFilter)
  {
    this.filter = paramFilter;
    this.myColor = this.filter.getColor();
    updateColor();
  }
  
  public void removeFilter()
  {
    this.myColor = Color.white;
    this.filter = null;
    updateColor();
  }
  
  public void updateColor()
  {
    this.outColor = new Color(Math.max(this.inColor.getRed() - (255 - this.myColor.getRed()), 0), Math.max(this.inColor.getGreen() - (255 - this.myColor.getGreen()), 0), Math.max(this.inColor.getBlue() - (255 - this.myColor.getBlue()), 0));
    this.nextLink.receiveLight(this.outColor);
  }
  
  public Point getReceptionPoint()
  {
    Point localPoint;
    if (this.filter == null) {
      localPoint = new Point(this.x + 42, this.y + 40);
    } else {
      localPoint = new Point(this.x + 30, this.y + 40);
    }
    return localPoint;
  }
  
  public Point getEmissionPoint()
  {
    return new Point(this.x + 54, this.y + 40);
  }
  
  public Color getColor()
  {
    return this.outColor;
  }
  
  public int getTargetX()
  {
    return this.x + 23;
  }
  
  public int getTargetY()
  {
    return this.y + 23;
  }
  
  public void mouseClicked(int paramInt1, int paramInt2) {}
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = ((int)(this.x * paramDouble1));
    this.y = ((int)(this.y * paramDouble2));
    if (this.filter != null) {
      this.filter.setPosition(this.x + 23, this.y + 23);
    }
    this.space.drawImageToBackground(this.x, this.y + 10, this.norm);
  }
  
  public void draw()
  {
    this.space.drawImage(this.x, this.y, this.norm);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/FilterHolder.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */