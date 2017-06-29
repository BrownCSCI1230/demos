package GP;

import java.awt.Color;
import java.io.PrintStream;

public class GPColor
{
  Color color_;
  String name_;
  
  public GPColor()
  {
    this(0, 0, 0);
  }
  
  public GPColor(GPColor paramGPColor)
  {
    this(paramGPColor.GetRed(), paramGPColor.GetGreen(), paramGPColor.GetBlue());
    this.name_ = paramGPColor.name_;
  }
  
  public GPColor(String paramString)
  {
    this.name_ = paramString;
    if (paramString.equalsIgnoreCase("Red"))
    {
      this.color_ = Color.red;
    }
    else if (paramString.equalsIgnoreCase("Orange"))
    {
      this.color_ = Color.orange;
    }
    else if (paramString.equalsIgnoreCase("Yellow"))
    {
      this.color_ = Color.yellow;
    }
    else if (paramString.equalsIgnoreCase("Green"))
    {
      this.color_ = Color.green;
    }
    else if (paramString.equalsIgnoreCase("Blue"))
    {
      this.color_ = Color.blue;
    }
    else if (paramString.equalsIgnoreCase("Purple"))
    {
      this.color_ = new Color(160, 32, 240);
    }
    else if (paramString.equalsIgnoreCase("Black"))
    {
      this.color_ = Color.black;
    }
    else if (paramString.equalsIgnoreCase("White"))
    {
      this.color_ = Color.white;
    }
    else if (paramString.equalsIgnoreCase("Light Gray"))
    {
      this.color_ = Color.lightGray;
    }
    else if (paramString.equalsIgnoreCase("Gray"))
    {
      this.color_ = Color.gray;
    }
    else if (paramString.equalsIgnoreCase("Dark Gray"))
    {
      this.color_ = Color.darkGray;
    }
    else
    {
      System.out.println("GPColor: Unrecognized name \"" + paramString + "\".");
      this.name_ = null;
    }
  }
  
  public GPColor(int paramInt1, int paramInt2, int paramInt3)
  {
    this.color_ = new Color(paramInt1, paramInt2, paramInt3);
    this.name_ = null;
  }
  
  public void Randomize()
  {
    this.color_ = new Color((int)Math.round(Math.random() * 255.0D), (int)Math.round(Math.random() * 255.0D), (int)Math.round(Math.random() * 255.0D));
  }
  
  public Color GetColor()
  {
    return new Color(this.color_.getRed(), this.color_.getGreen(), this.color_.getBlue());
  }
  
  public boolean Matches(GPColor paramGPColor)
  {
    return (paramGPColor.GetRed() == this.color_.getRed()) && (paramGPColor.GetGreen() == this.color_.getGreen()) && (paramGPColor.GetBlue() == this.color_.getBlue());
  }
  
  public String toString()
  {
    if (this.name_ != null) {
      return this.name_;
    }
    return "(" + this.color_.getRed() + "," + this.color_.getGreen() + "," + this.color_.getBlue() + ")";
  }
  
  public int GetRed()
  {
    return this.color_.getRed();
  }
  
  public int GetGreen()
  {
    return this.color_.getGreen();
  }
  
  public int GetBlue()
  {
    return this.color_.getBlue();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPColor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */