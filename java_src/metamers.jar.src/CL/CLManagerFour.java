package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerFour
  extends CLManager
{
  static final int II4_GRAPH_WIDTH = 450;
  static final int II4_GRAPH_HEIGHT = 100;
  static final int II4_RESULT_HEIGHT = 100;
  static final int II4_RESULT_BAR_WIDTH = 50;
  static final int II4_RESULT_PATCH_WIDTH = 100;
  static final int II4_RESULT_PATCH_HEIGHT = 100;
  static final int II4_ACTIVE_TOP = 0;
  static final int II4_ACTIVE_BOTTOM = 1;
  static final int II4_PRODUCT = 0;
  
  public CLManagerFour(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 450);
    this.active_array_[0] = new CLVisualGraph(450, 100, new GPPoint(50, 25), "Incoming Light", 1);
    this.active_array_[1] = new CLSpectrumGraph(450, 100, new GPPoint(50, 165), "Reflectance", 1);
    this.inactive_array_[0] = new CLSpectrumGraph(450, 100, new GPPoint(50, 300), "Product", 0);
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    this.inactive_array_[0].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[0], this.active_array_[1]);
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
    this.inactive_array_[0].DrawBarRange(this.da_, this.old_index_, this.current_index_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerFour.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */