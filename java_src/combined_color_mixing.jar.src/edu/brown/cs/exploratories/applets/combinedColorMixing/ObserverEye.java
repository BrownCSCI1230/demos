package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.Behavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.CallbackBehavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class ObserverEye
  extends VisualObject
  implements LightCarrier
{
  private int goalColorIndex;
  private int state;
  private Rectangle visual;
  private Rectangle goal;
  private Color visualColor;
  private Color goalColor;
  private Color tempColor;
  private Image norm;
  private boolean highlightStatus;
  private boolean showSeenColor;
  private LightCarrier nextLink;
  private final int FADING_OUT = 0;
  private final int FADING_IN = 1;
  private final int NORMAL = 2;
  
  public ObserverEye(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2)
  {
    super(paramObjectSpace, paramInt1, paramInt2);
    paramObjectSpace.disableClickable(this);
    this.norm = paramObjectSpace.loadImage("eye");
    paramObjectSpace.finishLoading();
    this.target = new Rectangle(0, 0, this.norm.getWidth(paramObjectSpace.getImageObserver()), this.norm.getHeight(paramObjectSpace.getImageObserver()));
    this.visual = new Rectangle(this.x + 77, this.y + 3, 62, 34);
    this.goal = new Rectangle(this.x + 78, this.y + 68, 62, 34);
    this.visualColor = Color.white;
    this.state = 2;
    this.goalColorIndex = 3;
    getNewGoalColor();
    this.state = 1;
    addBehavior(new CallbackBehavior(this, 16, 40));
    paramObjectSpace.drawImageToBackground(this.x, this.y, this.norm);
    this.showSeenColor = false;
  }
  
  public void receiveLight(Color paramColor)
  {
    this.visualColor = paramColor;
    isSolutionFound();
    hide();
  }
  
  public boolean isSolutionFound()
  {
    boolean bool = false;
    if (this.visualColor.equals(this.goalColor))
    {
      this.state = 0;
      addBehavior(new CallbackBehavior(this, 16, 40));
      bool = true;
    }
    return bool;
  }
  
  public void getNewGoalColor()
  {
    for (int i = this.goalColorIndex; i == this.goalColorIndex; i = (int)(Math.random() * 6.0D)) {}
    switch (i)
    {
    case 0: 
      this.tempColor = Color.red;
      break;
    case 1: 
      this.tempColor = Color.green;
      break;
    case 2: 
      this.tempColor = Color.blue;
      break;
    case 3: 
      this.tempColor = Color.cyan;
      break;
    case 4: 
      this.tempColor = Color.yellow;
      break;
    case 5: 
      this.tempColor = Color.magenta;
    }
    this.goalColorIndex = i;
  }
  
  public void action(int paramInt)
  {
    double d = paramInt / 16.0D;
    if (this.state == 0)
    {
      d = 1.0D - d;
      this.goalColor = new Color((int)(d * this.tempColor.getRed()), (int)(d * this.tempColor.getGreen()), (int)(d * this.tempColor.getBlue()));
    }
    else if (this.state == 1)
    {
      this.goalColor = new Color((int)(d * this.tempColor.getRed()), (int)(d * this.tempColor.getGreen()), (int)(d * this.tempColor.getBlue()));
    }
  }
  
  public void behaviorFinished(Behavior paramBehavior)
  {
    if (this.state == 0)
    {
      getNewGoalColor();
      this.state = 1;
      addBehavior(new CallbackBehavior(this, 16, 40));
    }
    else if (this.state == 1)
    {
      this.goalColor = this.tempColor;
    }
  }
  
  public Point getReceptionPoint()
  {
    return new Point(this.x + 20, this.y + 40);
  }
  
  public Point getEmissionPoint()
  {
    return new Point(this.x, this.y);
  }
  
  public Color getColor()
  {
    return this.visualColor;
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
  
  public void hide()
  {
    this.showSeenColor = false;
  }
  
  public void show()
  {
    this.showSeenColor = true;
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = ((int)(this.x * paramDouble1));
    this.y = ((int)(this.y * paramDouble2));
    this.visual = new Rectangle(this.x + 77, this.y + 3, 62, 34);
    this.goal = new Rectangle(this.x + 78, this.y + 68, 62, 34);
    this.space.drawImageToBackground(this.x, this.y, this.norm);
  }
  
  public void draw()
  {
    if (this.showSeenColor)
    {
      this.graphics.setColor(this.visualColor);
      this.space.fillShape(this.visual);
    }
    else
    {
      this.graphics.setColor(Color.gray);
      this.space.fillShape(this.visual);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/ObserverEye.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */