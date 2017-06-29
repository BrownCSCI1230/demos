package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerThree
  extends CLManager
{
  static final int II3_GRAPH_WIDTH = 200;
  static final int II3_GRAPH_HEIGHT = 200;
  static final int II3_RESULT_HEIGHT = 100;
  static final int II3_RESULT_BAR_WIDTH = 30;
  static final int II3_RESULT_PATCH_WIDTH = 60;
  static final int II3_RESULT_PATCH_HEIGHT = 60;
  static final int II3_ACTIVE_LEFT = 0;
  static final int II3_ACTIVE_RIGHT = 1;
  static final int II3_RESULT_LEFT = 0;
  static final int II3_RESULT_RIGHT = 1;
  static final int II3_NUM_FILTERS = 3;
  static final int II3_MAX_RESULT_INDEX = 2;
  
  public CLManagerThree(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 200);
    this.active_array_[0] = new CLSpectrumGraph(200, 200, new GPPoint(50, 40), "Input", 1);
    this.active_array_[1] = new CLSpectrumGraph(200, 200, new GPPoint(300, 40), "Input", 1);
    this.inactive_array_[0] = new CLColorPatchResultGraph(3, 100, new GPPoint(80, 290), 30, 60, 60);
    this.inactive_array_[1] = new CLColorPatchResultGraph(3, 100, new GPPoint(330, 290), 30, 60, 60);
    for (int i = 0; i < 3; i++) {
      this.filter_array_[i] = new CLFilter(200, i);
    }
    for (int j = 0; j < 6; j++) {
      this.nonvisual_array_[j] = new CLGraph(200);
    }
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    for (int i = 0; i < 3; i++)
    {
      this.nonvisual_array_[(i + 3 * this.current_active_)].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[this.current_active_], this.filter_array_[i]);
      this.inactive_array_[this.current_active_].SetElement(i, this.nonvisual_array_[(i + 3 * this.current_active_)].GetSum() / this.filter_array_[i].GetSum());
    }
    this.inactive_array_[this.current_active_].DrawBarRange(this.da_, 0, 2);
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerThree.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */