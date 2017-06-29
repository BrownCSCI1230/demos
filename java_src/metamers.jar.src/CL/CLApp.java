package CL;

import GP.GPApplet;
import GP.GPColor;
import GP.GPColumn;
import GP.GPDrawingArea;
import GP.GPPoint;

public class CLApp
  extends GPApplet
  implements CLMouseController
{
  final int WIDTH_ = 550;
  final int HEIGHT_ = 450;
  final int NUMILLUSTRATIONS_ = 6;
  GPColumn main_frame_;
  GPDrawingArea da_;
  CLMouseInteractor mouse_;
  CLManager manager_;
  
  public void Init()
  {
    Setup();
  }
  
  public void Setup()
  {
    String str = GetParameter("ILLUSTRATION");
    if (str == null) {
      str = GetParameter("ARG1");
    }
    if (str != null)
    {
      this.main_frame_ = new GPColumn(this);
      this.da_ = new GPDrawingArea(this.main_frame_, 550, 450);
      this.mouse_ = new CLMouseInteractor(this);
      this.da_.AddInteractor(this.mouse_);
      this.da_.Clear(new GPColor("black"));
      int i = Integer.valueOf(str).intValue();
      CreateIllustration(i);
      this.manager_.DrawAll();
    }
    else
    {
      throw new Error("Syntax: CLApplet [illustration]");
    }
  }
  
  public String GetAppletDescription()
  {
    return "Spectrum v1.0, by Jeff Beall, Adam Doppelt, and John F. Hughes.";
  }
  
  public String[][] GetParameterDescription()
  {
    String[][] arrayOfString = { { "ILLUSTRATION", "1-7", "the illustration number" } };
    return arrayOfString;
  }
  
  public void CreateIllustration(int paramInt)
  {
    if (paramInt == 1) {
      this.manager_ = new CLManagerOne(this.da_);
    } else if (paramInt == 2) {
      this.manager_ = new CLManagerTwo(this.da_);
    } else if (paramInt == 3) {
      this.manager_ = new CLManagerThree(this.da_);
    } else if (paramInt == 4) {
      this.manager_ = new CLManagerFour(this.da_);
    } else if (paramInt == 5) {
      this.manager_ = new CLManagerFive(this.da_);
    } else if (paramInt == 6) {
      this.manager_ = new CLManagerSix(this.da_);
    } else if (paramInt == 7) {
      this.manager_ = new CLManagerSeven(this.da_);
    } else {
      this.manager_ = new CLManagerOne(this.da_);
    }
  }
  
  public void SwitchIllustration(int paramInt) {}
  
  public void ButtonDown(GPPoint paramGPPoint, int paramInt)
  {
    this.manager_.ButtonDown(paramGPPoint, paramInt);
  }
  
  public void ButtonMotion(GPPoint paramGPPoint, int paramInt)
  {
    this.manager_.ButtonMotion(paramGPPoint, paramInt);
  }
  
  public void ButtonUp(GPPoint paramGPPoint, int paramInt)
  {
    this.manager_.ButtonUp(paramGPPoint, paramInt);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    CLApp localCLApp = new CLApp();
    localCLApp.SetupParameters(paramArrayOfString);
    localCLApp.Attach();
    localCLApp.Init();
    localCLApp.Run();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLApp.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */