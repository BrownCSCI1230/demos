package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

public class Beam
{
  private Color color;
  private int startX;
  private int startY;
  private int endX;
  private int endY;
  private int width;
  private int xoff;
  private int yoff;
  private int step;
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private int iterations;
  private double curX;
  private double curY;
  private double dx;
  private double dy;
  private int[] polyx;
  private int[] polyy;
  private Polygon beam;
  
  public Beam(Color paramColor, Point paramPoint1, Point paramPoint2, int paramInt)
  {
    this.startX = paramPoint1.x;
    this.startY = paramPoint1.y;
    this.width = paramInt;
    this.endX = paramPoint2.x;
    this.endY = paramPoint2.y;
    this.dx = (this.endX - this.startX);
    this.dy = (this.endY - this.startY);
    double d1 = Math.sqrt(Math.pow(this.dx, 2.0D) + Math.pow(this.dy, 2.0D));
    double d2 = this.width / d1;
    this.xoff = ((int)(-this.dy * d2));
    this.yoff = ((int)(this.dx * d2));
    this.iterations = ((int)Math.ceil(d1 / 6.0D));
    this.dx /= this.iterations;
    this.dy /= this.iterations;
    this.step = 0;
    this.curX = this.startX;
    this.curY = this.startY;
    this.x1 = (this.startX + this.xoff);
    this.y1 = (this.startY + this.yoff);
    this.x2 = (this.startX - this.xoff);
    this.y2 = (this.startY - this.yoff);
    this.color = paramColor;
    this.polyx = new int[4];
    this.polyy = new int[4];
  }
  
  public Beam() {}
  
  public boolean update()
  {
    boolean bool = false;
    this.step += 1;
    this.curX += this.dx;
    this.curY += this.dy;
    int[] arrayOfInt1 = { this.x1, this.x2, (int)this.curX - this.xoff, (int)this.curX + this.xoff };
    int[] arrayOfInt2 = { this.y1, this.y2, (int)this.curY - this.yoff, (int)this.curY + this.yoff };
    System.arraycopy(arrayOfInt1, 0, this.polyx, 0, 4);
    System.arraycopy(arrayOfInt2, 0, this.polyy, 0, 4);
    this.beam = new Polygon(this.polyx, this.polyy, 4);
    if (this.step > this.iterations - 1) {
      bool = true;
    }
    return bool;
  }
  
  public void draw(ObjectSpace paramObjectSpace)
  {
    paramObjectSpace.setColor(this.color);
    if (this.beam != null) {
      paramObjectSpace.fillShape(this.beam);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/Beam.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */