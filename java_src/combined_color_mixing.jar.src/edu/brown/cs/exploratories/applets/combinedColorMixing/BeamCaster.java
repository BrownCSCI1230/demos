package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.Behavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.CallbackBehavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Image;
import java.awt.Rectangle;

public class BeamCaster
  extends VisualObject
{
  private ObserverEye eye;
  private Image norm;
  private Behavior behavior;
  private LightCarrier[] objects;
  private Beam[] beam;
  private int numBeams;
  private int curBeam;
  private int state;
  private int step;
  private final int RESTING = 0;
  private final int ACTIVE = 1;
  private final int FINISHED = 2;
  
  public BeamCaster(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, LightCarrier[] paramArrayOfLightCarrier, int paramInt3)
  {
    super(paramObjectSpace);
    this.numBeams = (paramInt3 - 1);
    this.objects = new LightCarrier[paramInt3];
    System.arraycopy(paramArrayOfLightCarrier, 0, this.objects, 0, paramInt3);
    this.step = 0;
    this.curBeam = -1;
    this.x = paramInt1;
    this.y = paramInt2;
    this.beam = new Beam[this.numBeams];
    generateBeamPath();
    this.norm = this.space.loadImage("beamButton");
    this.space.finishLoading();
    this.target = new Rectangle(0, 0, this.norm.getWidth(this.space.getImageObserver()), this.norm.getHeight(this.space.getImageObserver()));
    this.eye = ((ObserverEye)this.objects[(paramInt3 - 1)]);
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if ((this.state == 1) || (this.state == 2)) {
      bool = true;
    } else {
      bool = super.contains(paramInt1, paramInt2);
    }
    return bool;
  }
  
  public void mouseClicked(int paramInt1, int paramInt2)
  {
    if ((this.state == 1) || (this.state == 2))
    {
      this.behavior.stopBehavior();
      this.state = 0;
      this.curBeam = -1;
      this.eye.hide();
    }
    else if (this.state == 0)
    {
      this.space.bringToFront(this);
      generateBeamPath();
      this.state = 1;
      this.behavior = new CallbackBehavior(this, 10000, 20);
      this.curBeam = 0;
    }
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.x = (paramInt1 - 47);
    generateBeamPath();
  }
  
  public void generateBeamPath()
  {
    this.beam[0] = new MergingBeam(((LightStrip)this.objects[0]).getColor1(), ((LightStrip)this.objects[0]).getColor2(), ((LightStrip)this.objects[0]).getEmissionPoint1(), ((LightStrip)this.objects[0]).getEmissionPoint2(), this.objects[1].getReceptionPoint(), 3);
    for (int i = 1; i < this.numBeams; i++) {
      this.beam[i] = new Beam(this.objects[i].getColor(), this.objects[i].getEmissionPoint(), this.objects[(i + 1)].getReceptionPoint(), 3);
    }
  }
  
  public void action(int paramInt)
  {
    if (this.state == 1)
    {
      if (this.beam[this.curBeam].update())
      {
        this.curBeam += 1;
        if (this.curBeam == this.numBeams)
        {
          this.state = 2;
          this.step = 0;
          this.curBeam -= 1;
          this.eye.show();
        }
        else
        {
          this.beam[this.curBeam] = new Beam(this.objects[this.curBeam].getColor(), this.objects[this.curBeam].getEmissionPoint(), this.objects[(this.curBeam + 1)].getReceptionPoint(), 3);
        }
      }
    }
    else if (this.state == 2)
    {
      this.step += 1;
      if (this.step > 70)
      {
        this.behavior.stopBehavior();
        this.state = 0;
        this.curBeam = -1;
        this.eye.hide();
      }
    }
  }
  
  public void draw()
  {
    this.space.drawImage(this.x, this.y, this.norm);
    for (int i = 0; i < this.curBeam + 1; i++) {
      this.beam[i].draw(this.space);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/BeamCaster.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */