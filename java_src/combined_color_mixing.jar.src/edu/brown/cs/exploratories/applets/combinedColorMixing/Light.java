package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.StaticForegroundObject;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Light
  extends VisualObject
  implements LightCarrier
{
  private Color myColor;
  private LightBulb bulb;
  private Image norm;
  private boolean highlightStatus;
  private LightCarrier nextLink;
  private StaticForegroundObject lightFront;
  
  public Light(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, LightCarrier paramLightCarrier)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    paramObjectSpace.disableDrawable(this);
    paramObjectSpace.disableClickable(this);
    this.norm = paramObjectSpace.loadImage("light");
    this.nextLink = paramLightCarrier;
    setTarget(this.norm);
    this.myColor = Color.black;
    paramObjectSpace.drawImageToBackground(this.x, this.y, this.norm);
    this.lightFront = new StaticForegroundObject(paramObjectSpace, this.x, this.y, "lightFront");
  }
  
  public Light(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    paramObjectSpace.disableDrawable(this);
    this.norm = paramObjectSpace.loadImage("light");
    paramObjectSpace.finishLoading();
    this.target = new Rectangle(0, 0, this.norm.getWidth(paramObjectSpace.getImageObserver()), this.norm.getHeight(paramObjectSpace.getImageObserver()));
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.target.contains(paramInt1, paramInt2);
  }
  
  public LightBulb getBulb()
  {
    return this.bulb;
  }
  
  public void newBulbComing(LightBulb paramLightBulb)
  {
    if (this.bulb != null) {
      this.bulb.goHome();
    }
    this.bulb = paramLightBulb;
    this.myColor = this.bulb.getColor();
  }
  
  public void removeBulb(LightBulb paramLightBulb)
  {
    this.bulb = null;
    this.myColor = Color.black;
    this.nextLink.receiveLight(this.myColor);
  }
  
  public void setBulb()
  {
    this.nextLink.receiveLight(this.myColor);
  }
  
  public int getTargetX()
  {
    return this.x + 7;
  }
  
  public int getTargetY()
  {
    return this.y + 26;
  }
  
  public void mouseClicked(int paramInt1, int paramInt2) {}
  
  public void receiveLight(Color paramColor) {}
  
  public Point getReceptionPoint()
  {
    return new Point(this.x, this.y);
  }
  
  public Point getEmissionPoint()
  {
    return new Point(this.x + 7, this.y + 46);
  }
  
  public Color getColor()
  {
    return this.myColor;
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = ((int)(this.x * paramDouble1));
    this.y = ((int)(this.y * paramDouble2));
    this.lightFront.setPosition(this.x, this.y);
    if (this.bulb != null) {
      this.bulb.setPosition(this.x + 7, this.y + 26);
    }
    this.space.drawImageToBackground(this.x, this.y, this.norm);
  }
  
  public void draw() {}
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/Light.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */