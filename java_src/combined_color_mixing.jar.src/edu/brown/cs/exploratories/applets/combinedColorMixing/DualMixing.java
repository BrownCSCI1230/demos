package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Dimension;

public class DualMixing
  extends Exploratory
{
  private BeamCaster caster;
  private FilterHolder filterHolder;
  private Light light;
  private SurfaceObject object;
  private ObserverEye eye;
  private StorageBox box;
  private StorageBox x2;
  private StorageBox x3;
  private ObjectSpace space;
  private int width;
  private int height;
  
  public DualMixing()
  {
    setSize(800, 600);
    this.width = getSize().width;
    this.height = getSize().height;
    this.space = new ObjectSpace(this, this.width / 2, this.height / 2);
    this.space.setImageIndex("/edu/brown/cs/exploratories/applets/combinedColorMixing/images/", "dualmixing.dat");
    this.eye = new ObserverEye(this.space, 70, 40);
    this.filterHolder = new FilterHolder(this.space, 45, 40, this.eye);
    this.object = new SurfaceObject(this.space, 22, 40, this.filterHolder);
    this.light = new LightStrip(this.space, 65, 0, this.object, 2, 10);
    this.box = new StorageBox(this.space, this.width, this.height, this.filterHolder, this.object, this.light);
    LightCarrier[] arrayOfLightCarrier = { this.light, this.object, this.filterHolder, this.eye };
    this.caster = new BeamCaster(this.space, this.width - 47, 0, arrayOfLightCarrier, 4);
    this.space.mainloop();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, DualMixing.class);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/DualMixing.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */