package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import java.awt.Color;
import java.awt.Point;

public class MergingBeam
  extends Beam
{
  private Beam[] beams = new Beam[2];
  private boolean[] beamActiveFlag;
  
  public MergingBeam(Color paramColor1, Color paramColor2, Point paramPoint1, Point paramPoint2, Point paramPoint3, int paramInt)
  {
    this.beams[0] = new Beam(paramColor1, paramPoint1, paramPoint3, paramInt);
    this.beams[1] = new Beam(paramColor2, paramPoint2, paramPoint3, paramInt);
    this.beamActiveFlag = new boolean[2];
    this.beamActiveFlag[0] = false;
    this.beamActiveFlag[1] = false;
  }
  
  public boolean update()
  {
    if (this.beamActiveFlag[0] == 0) {
      this.beamActiveFlag[0] = this.beams[0].update();
    }
    if (this.beamActiveFlag[1] == 0) {
      this.beamActiveFlag[1] = this.beams[1].update();
    }
    boolean bool = false;
    if ((this.beamActiveFlag[0] != 0) && (this.beamActiveFlag[1] != 0)) {
      bool = true;
    }
    return bool;
  }
  
  public void draw(ObjectSpace paramObjectSpace)
  {
    this.beams[0].draw(paramObjectSpace);
    this.beams[1].draw(paramObjectSpace);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/MergingBeam.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */