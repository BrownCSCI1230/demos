package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.Resizeable;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

public class StorageBox
  implements Resizeable
{
  private int ySpacing;
  private int xSpacingLights;
  private int xSpacingPaints;
  private int xSpacingFilters;
  private int w;
  private int h;
  private ObjectSpace space;
  private int lightWidth;
  private int paintWidth;
  private int paintHeight;
  private int filterHeight;
  private int labelHeight;
  private int xOffsetLights;
  private int xOffsetPaints;
  private int xOffsetFilters;
  private int yOffsetLights;
  private int yOffsetPaints;
  private int yOffsetFilters;
  private Image lightLabel;
  private Image paintLabel;
  private Image filterLabel;
  private LightBulb[] lights;
  private Filter[] filters;
  private PaintCan[] paints;
  
  public StorageBox(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, FilterHolder paramFilterHolder, SurfaceObject paramSurfaceObject, Light paramLight)
  {
    this.space = paramObjectSpace;
    this.space.enableResizeable(this);
    this.lights = new LightBulb[6];
    this.paints = new PaintCan[8];
    this.filters = new Filter[6];
    this.lightWidth = 27;
    this.paintWidth = 36;
    this.labelHeight = 20;
    this.lightLabel = this.space.loadImage("lightsLabel");
    this.paintLabel = this.space.loadImage("paintsLabel");
    this.filterLabel = this.space.loadImage("filtersLabel");
    this.lights[0] = new LightBulb(this.space, paramLight, Color.red, "rl");
    this.lights[1] = new LightBulb(this.space, paramLight, Color.green, "gl");
    this.lights[2] = new LightBulb(this.space, paramLight, Color.blue, "bl");
    this.lights[3] = new LightBulb(this.space, paramLight, Color.cyan, "cl");
    this.lights[4] = new LightBulb(this.space, paramLight, Color.magenta, "ml");
    this.lights[5] = new LightBulb(this.space, paramLight, Color.yellow, "yl");
    this.paints[0] = new PaintCan(this.space, paramSurfaceObject, Color.red, "rp");
    this.paints[1] = new PaintCan(this.space, paramSurfaceObject, Color.cyan, "cp");
    this.paints[2] = new PaintCan(this.space, paramSurfaceObject, Color.green, "gp");
    this.paints[3] = new PaintCan(this.space, paramSurfaceObject, Color.magenta, "mp");
    this.paints[4] = new PaintCan(this.space, paramSurfaceObject, Color.blue, "bp");
    this.paints[5] = new PaintCan(this.space, paramSurfaceObject, Color.yellow, "yp");
    this.paints[6] = new PaintCan(this.space, paramSurfaceObject, Color.white, "wp");
    this.paints[7] = new PaintCan(this.space, paramSurfaceObject, Color.black, "kp");
    this.filters[0] = new Filter(this.space, paramFilterHolder, Color.red, "rf");
    this.filters[1] = new Filter(this.space, paramFilterHolder, Color.green, "gf");
    this.filters[2] = new Filter(this.space, paramFilterHolder, Color.blue, "bf");
    this.filters[3] = new Filter(this.space, paramFilterHolder, Color.cyan, "cf");
    this.filters[4] = new Filter(this.space, paramFilterHolder, Color.magenta, "mf");
    this.filters[5] = new Filter(this.space, paramFilterHolder, Color.yellow, "yf");
    resize(1.0D, 1.0D, paramInt1, paramInt2);
  }
  
  public void addBulb(LightBulb paramLightBulb, int paramInt)
  {
    this.lights[paramInt] = paramLightBulb;
  }
  
  public void organize()
  {
    int i = 0;
    for (int j = 0; j < 6; j++)
    {
      this.lights[j].setID(j);
      this.lights[j].setHome(this.xSpacingLights * (j % 3) + this.xOffsetLights, this.labelHeight + this.ySpacing * i + this.yOffsetLights - i * 8, this);
      this.space.bringToFront(this.lights[j]);
      if (j == 2) {
        i++;
      }
    }
    i++;
    for (int k = 0; k < 8; k++)
    {
      this.paints[k].setID(k);
      this.paints[k].setHome(this.xSpacingPaints * (k % 2) + this.xOffsetPaints, 2 * this.labelHeight + this.ySpacing * i + this.yOffsetPaints, this);
      this.space.bringToFront(this.paints[k]);
      if ((k == 1) || (k == 3) || (k == 5) || (k == 7)) {
        i++;
      }
    }
    for (int m = 0; m < 6; m++)
    {
      this.filters[m].setID(m);
      this.filters[m].setHome(this.xSpacingFilters * (m % 3) + this.xOffsetFilters, 4 * this.labelHeight + this.ySpacing * i + this.yOffsetFilters, this);
      this.space.bringToFront(this.filters[m]);
      if (m == 2) {
        i++;
      }
    }
  }
  
  public void sortLayering()
  {
    for (int i = 0; i < 6; i++) {
      this.space.bringForwardButPreserveTopObject(this.lights[i], 1);
    }
    for (int j = 0; j < 8; j++) {
      this.space.bringForwardButPreserveTopObject(this.paints[j], 1);
    }
    for (int k = 0; k < 6; k++) {
      this.space.bringForwardButPreserveTopObject(this.filters[k], 1);
    }
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.w = (paramInt1 / 6);
    this.h = paramInt2;
    this.ySpacing = ((this.h - 3 * this.labelHeight) / 9);
    this.xSpacingLights = (this.w / 3);
    this.xSpacingPaints = (this.w / 2);
    this.xSpacingFilters = (this.w / 3);
    this.xOffsetLights = ((this.xSpacingLights - this.lightWidth) / 2);
    this.xOffsetPaints = ((this.xSpacingPaints - this.paintWidth) / 2 - 5);
    this.xOffsetFilters = (this.xOffsetLights + 4);
    this.yOffsetLights = 0;
    this.yOffsetPaints = 0;
    this.yOffsetFilters = 0;
    if (this.xOffsetLights < 0) {
      this.xOffsetLights = 0;
    }
    if (this.xOffsetPaints < 0) {
      this.xOffsetPaints = 0;
    }
    if (this.xOffsetFilters < 0) {
      this.xOffsetFilters = 0;
    }
    if (this.yOffsetPaints < 0) {
      this.yOffsetPaints = 0;
    }
    if (this.yOffsetFilters < 0) {
      this.yOffsetFilters = 0;
    }
    organize();
    this.space.setColor(Color.white);
    this.space.drawShapeToBackground(new Rectangle(1, 0, this.w - 2, this.ySpacing * 2 + this.labelHeight - 1));
    this.space.drawShapeToBackground(new Rectangle(0, 1, this.w, this.ySpacing * 2 + this.labelHeight - 3));
    this.space.drawShapeToBackground(new Rectangle(1, this.ySpacing * 2 + this.labelHeight, this.w - 2, this.ySpacing * 4 + 2 * this.labelHeight - 1));
    this.space.drawShapeToBackground(new Rectangle(0, this.ySpacing * 2 + this.labelHeight + 1, this.w, this.ySpacing * 4 + 2 * this.labelHeight - 3));
    this.space.drawShapeToBackground(new Rectangle(0, this.ySpacing * 6 + 3 * this.labelHeight + 1, this.w, this.h - (this.ySpacing * 6 + 3 * this.labelHeight) - 3));
    this.space.drawShapeToBackground(new Rectangle(1, this.ySpacing * 6 + 3 * this.labelHeight, this.w - 2, this.h - (this.ySpacing * 6 + 3 * this.labelHeight) - 1));
    this.space.drawImageToBackground(0, 0, this.lightLabel);
    this.space.drawImageToBackground(0, this.ySpacing * 2 + this.labelHeight, this.paintLabel);
    this.space.drawImageToBackground(0, this.ySpacing * 6 + 3 * this.labelHeight, this.filterLabel);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/StorageBox.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */