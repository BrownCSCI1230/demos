package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerSix
  extends CLManager
{
  static final int II6_GRAPH_WIDTH = 125;
  static final int II6_GRAPH_HEIGHT = 80;
  static final int II6_RESULT_HEIGHT = 80;
  static final int II6_RESULT_BAR_WIDTH = 20;
  static final int II6_RESULT_PATCH_WIDTH = 50;
  static final int II6_RESULT_PATCH_HEIGHT = 50;
  static final int II6_ACTIVE_LIGHT = 0;
  static final int II6_ACTIVE_REFLECT = 1;
  static final int II6_PRODUCT = 0;
  static final int II6_RESULT_LIGHT = 1;
  static final int II6_RESULT_REFLECT = 2;
  static final int II6_RESULT_PRODUCT = 3;
  static final int II6_RESULT_RGB_PRODUCT = 4;
  static final int II6_LIGHT_OFFSET = 0;
  static final int II6_REFLECT_OFFSET = 3;
  static final int II6_PRODUCT_OFFSET = 6;
  static final int II6_NUM_FILTERS = 3;
  static final int II6_MAX_RESULT_INDEX = 2;
  static final int X_1 = 40;
  static final int X_2 = 190;
  static final int X_3 = 390;
  static final int Y_1 = 45;
  static final int Y_2 = 175;
  static final int Y_3 = 300;
  
  public CLManagerSix(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 125);
    this.active_array_[0] = new CLVisualGraph(125, 80, new GPPoint(190, 45), "Incoming Light", 1);
    this.active_array_[1] = new CLSpectrumGraph(125, 80, new GPPoint(190, 175), "Reflectance", 1);
    this.inactive_array_[0] = new CLSpectrumGraph(125, 80, new GPPoint(190, 300), "Product", 0);
    this.inactive_array_[1] = new CLColorPatchResultGraph(3, 80, new GPPoint(390, 45), 20, 50, 50);
    this.inactive_array_[2] = new CLColorPatchResultGraph(3, 80, new GPPoint(390, 175), 20, 50, 50);
    this.inactive_array_[3] = new CLColorPatchResultGraph(3, 80, new GPPoint(40, 300), 20, 50, 50);
    this.inactive_array_[4] = new CLColorPatchResultGraph(3, 80, new GPPoint(390, 300), 20, 50, 50);
    for (int i = 0; i < 3; i++) {
      this.filter_array_[i] = new CLFilter(125, i);
    }
    for (int j = 0; j < 9; j++) {
      this.nonvisual_array_[j] = new CLGraph(125);
    }
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    this.inactive_array_[0].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[0], this.active_array_[1]);
    int i;
    int j;
    if (this.current_active_ == 0)
    {
      i = 0;
      j = 1;
    }
    else
    {
      i = 3;
      j = 2;
    }
    for (int k = 0; k < 3; k++)
    {
      this.nonvisual_array_[(k + i)].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[this.current_active_], this.filter_array_[k]);
      this.inactive_array_[j].SetElement(k, this.nonvisual_array_[(k + i)].GetSum() / this.filter_array_[k].GetSum());
    }
    this.inactive_array_[j].DrawBarRange(this.da_, 0, 2);
    for (int m = 0; m < 3; m++)
    {
      this.nonvisual_array_[(m + 6)].MultiplyRange(this.old_index_, this.current_index_, this.inactive_array_[0], this.filter_array_[m]);
      this.inactive_array_[3].SetElement(m, this.nonvisual_array_[(m + 6)].GetSum() / this.filter_array_[m].GetSum());
    }
    this.inactive_array_[3].DrawBarRange(this.da_, 0, 2);
    this.inactive_array_[4].Multiply(this.inactive_array_[1], this.inactive_array_[2]);
    this.inactive_array_[4].DrawBarRange(this.da_, 0, 2);
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
    this.inactive_array_[0].DrawBarRange(this.da_, this.old_index_, this.current_index_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerSix.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */