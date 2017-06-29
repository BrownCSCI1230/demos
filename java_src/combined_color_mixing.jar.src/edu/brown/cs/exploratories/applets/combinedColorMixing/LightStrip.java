package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.StaticForegroundObject;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class LightStrip
  extends Light
{
  private Light[] lights;
  private Color collectiveColor;
  private LightCarrier nextLink;
  private StaticForegroundObject halo;
  private int lightWidth;
  private int lightHeight;
  private int targWidth;
  private int numLights;
  private int curLight;
  private int spacing;
  
  public LightStrip(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, LightCarrier paramLightCarrier, int paramInt3, int paramInt4)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    paramObjectSpace.disableDrawable(this);
    this.nextLink = paramLightCarrier;
    this.lightWidth = 45;
    this.lightHeight = 50;
    this.collectiveColor = Color.black;
    this.nextLink.receiveLight(this.collectiveColor);
    this.curLight = 0;
    this.numLights = paramInt3;
    this.lights = new Light[this.numLights];
    for (int i = 0; i < this.numLights; i++)
    {
      this.lights[i] = new Light(paramObjectSpace, paramInt1 + i * paramInt4, paramInt2, this);
      paramObjectSpace.disableResizeable(this.lights[i]);
    }
    this.targWidth = (this.lights[(this.numLights - 1)].getX() - this.x);
    this.target = new Rectangle(0, 0, this.targWidth + this.lightWidth, this.lightHeight);
    Rectangle localRectangle = new Rectangle(this.x, this.y, this.targWidth + this.lightWidth, this.lightHeight);
    this.halo = new StaticForegroundObject(paramObjectSpace, this.x, this.y, "lightHalo");
  }
  
  public LightBulb getBulb()
  {
    return this.lights[this.curLight].getBulb();
  }
  
  public void newBulbComing(LightBulb paramLightBulb)
  {
    this.lights[this.curLight].newBulbComing(paramLightBulb);
  }
  
  public void removeBulb(LightBulb paramLightBulb)
  {
    for (int i = 0; i < this.numLights; i++) {
      if (this.lights[i].getBulb() == paramLightBulb) {
        this.lights[i].removeBulb(paramLightBulb);
      }
    }
    setBulb();
  }
  
  public void setBulb()
  {
    int i = 0;
    int j = 0;
    int k = 0;
    for (int m = 0; m < this.numLights; m++)
    {
      i += this.lights[m].getColor().getRed();
      j += this.lights[m].getColor().getGreen();
      k += this.lights[m].getColor().getBlue();
    }
    if (i > 255) {
      i = 255;
    }
    if (j > 255) {
      j = 255;
    }
    if (k > 255) {
      k = 255;
    }
    this.collectiveColor = new Color(i, j, k);
    this.nextLink.receiveLight(this.collectiveColor);
  }
  
  public int getTargetX()
  {
    return this.lights[this.curLight].getTargetX();
  }
  
  public int getTargetY()
  {
    return this.lights[this.curLight].getTargetY();
  }
  
  public void mouseClicked(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.numLights; i++) {
      if (this.lights[i].contains(paramInt1 + this.x - this.lights[i].getX(), paramInt2 + this.y - this.lights[i].getY()))
      {
        this.halo.setPosition(this.lights[i].getX(), this.lights[i].getY());
        this.curLight = i;
      }
    }
  }
  
  public void receiveLight(Color paramColor) {}
  
  public Point getReceptionPoint()
  {
    return this.lights[this.curLight].getReceptionPoint();
  }
  
  public Point getEmissionPoint1()
  {
    return this.lights[0].getEmissionPoint();
  }
  
  public Point getEmissionPoint2()
  {
    return this.lights[1].getEmissionPoint();
  }
  
  public Color getColor1()
  {
    return this.lights[0].getColor();
  }
  
  public Color getColor2()
  {
    return this.lights[1].getColor();
  }
  
  public Color getColor()
  {
    return this.collectiveColor;
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = ((int)(this.x * paramDouble1));
    this.y = ((int)(this.y * paramDouble2));
    this.targWidth = ((int)(this.targWidth * paramDouble1));
    this.target = new Rectangle(0, 0, this.targWidth + this.lightWidth, this.lightHeight);
    for (int i = 0; i < this.numLights; i++) {
      this.lights[i].resize(paramDouble1, paramDouble2, paramInt1, paramInt2);
    }
    this.halo.setPosition(this.lights[this.curLight].getX(), this.lights[this.curLight].getY());
  }
  
  public void draw() {}
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/LightStrip.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */