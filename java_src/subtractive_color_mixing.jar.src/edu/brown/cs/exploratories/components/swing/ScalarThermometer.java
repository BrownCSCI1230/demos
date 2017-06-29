package edu.brown.cs.exploratories.components.swing;

import edu.brown.cs.exploratories.components.test.TestFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ScalarThermometer
  extends JComponent
{
  public static final String PROP_VALUE = "value";
  public static final String PROP_MIN_VISIBLE_VALUE = "minVisibleValue";
  public static final String PROP_MAX_VISIBLE_VALUE = "maxVisibleValue";
  public static final String PROP_ORIENTATION = "orientation";
  public static final String PROP_MERCURY_COLOR = "mercuryColor";
  public static final double DEFAULT_VALUE = 0.0D;
  public static final double DEFAULT_MIN_VISIBLE_VALUE = 0.0D;
  public static final double DEFAULT_MAX_VISIBLE_VALUE = 10.0D;
  public static final Orientation DEFAULT_ORIENTATION = Orientation.VERTICAL;
  public static final Color DEFAULT_MERCURY_COLOR = Color.black;
  private double value;
  private double minVisibleValue;
  private double maxVisibleValue;
  private Orientation orientation;
  private Color mercuryColor;
  private Rectangle tubeRect = new Rectangle();
  private Rectangle mercuryRect = new Rectangle();
  
  public ScalarThermometer()
  {
    setValue(0.0D);
    setMinVisibleValue(0.0D);
    setMaxVisibleValue(10.0D);
    setOrientation(DEFAULT_ORIENTATION);
    setMercuryColor(DEFAULT_MERCURY_COLOR);
  }
  
  public double getValue()
  {
    return this.value;
  }
  
  public void setValue(double paramDouble)
  {
    double d = this.value;
    try
    {
      fireVetoableChange("value", new Double(d), new Double(paramDouble));
      this.value = paramDouble;
      repaint();
      firePropertyChange("value", d, paramDouble);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public double getMinVisibleValue()
  {
    return this.minVisibleValue;
  }
  
  public void setMinVisibleValue(double paramDouble)
  {
    double d = this.minVisibleValue;
    if (paramDouble > this.maxVisibleValue) {
      paramDouble = this.maxVisibleValue;
    }
    try
    {
      fireVetoableChange("minVisibleValue", new Double(d), new Double(paramDouble));
      this.minVisibleValue = paramDouble;
      firePropertyChange("minVisibleValue", d, paramDouble);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public double getMaxVisibleValue()
  {
    return this.maxVisibleValue;
  }
  
  public void setMaxVisibleValue(double paramDouble)
  {
    double d = this.maxVisibleValue;
    if (paramDouble < this.minVisibleValue) {
      paramDouble = this.minVisibleValue;
    }
    try
    {
      fireVetoableChange("maxVisibleValue", new Double(d), new Double(paramDouble));
      this.maxVisibleValue = paramDouble;
      firePropertyChange("maxVisibleValue", d, paramDouble);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public Orientation getOrientation()
  {
    return this.orientation;
  }
  
  public void setOrientation(Orientation paramOrientation)
  {
    Orientation localOrientation = this.orientation;
    if (localOrientation != paramOrientation) {
      try
      {
        fireVetoableChange("orientation", localOrientation, paramOrientation);
        this.orientation = paramOrientation;
        revalidate();
        firePropertyChange("orientation", localOrientation, paramOrientation);
      }
      catch (PropertyVetoException localPropertyVetoException) {}
    }
  }
  
  public Color getMercuryColor()
  {
    return this.mercuryColor;
  }
  
  public void setMercuryColor(Color paramColor)
  {
    Color localColor = this.mercuryColor;
    if (localColor != paramColor) {
      try
      {
        fireVetoableChange("mercuryColor", localColor, paramColor);
        this.mercuryColor = paramColor;
        firePropertyChange("mercuryColor", localColor, paramColor);
        repaint();
      }
      catch (PropertyVetoException localPropertyVetoException) {}
    }
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    calculateGeometry();
    Color localColor = paramGraphics.getColor();
    if (isEnabled())
    {
      paramGraphics.setColor(getMercuryColor());
      paramGraphics.fillRect(this.mercuryRect.x, this.mercuryRect.y, this.mercuryRect.width, this.mercuryRect.height);
      paramGraphics.setColor(getForeground());
      paramGraphics.drawRect(this.tubeRect.x, this.tubeRect.y, this.tubeRect.width, this.tubeRect.height);
    }
    else
    {
      paramGraphics.setColor(getBackground().darker());
      paramGraphics.drawRect(this.tubeRect.x, this.tubeRect.y, this.tubeRect.width, this.tubeRect.height);
    }
    paramGraphics.setColor(localColor);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    ScalarThermometer localScalarThermometer = new ScalarThermometer();
    localScalarThermometer.setMinVisibleValue(-5.0D);
    localScalarThermometer.setMaxVisibleValue(20.0D);
    localScalarThermometer.setValue(5.0D);
    localScalarThermometer.setOrientation(Orientation.VERTICAL);
    localScalarThermometer.setForeground(Color.red);
    localScalarThermometer.setMercuryColor(Color.green);
    TestFrame localTestFrame1 = new TestFrame(localScalarThermometer);
    JButton localJButton1 = new JButton("Add 1");
    localJButton1.addActionListener(new ActionListener()
    {
      private final ScalarThermometer val$therm;
      
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        this.val$therm.setValue(this.val$therm.getValue() + 0.5D);
      }
    });
    JButton localJButton2 = new JButton("Sub 1");
    localJButton2.addActionListener(new ActionListener()
    {
      private final ScalarThermometer val$therm;
      
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        this.val$therm.setValue(this.val$therm.getValue() - 1.0D);
      }
    });
    JPanel localJPanel = new JPanel();
    localJPanel.setLayout(new BoxLayout(localJPanel, 0));
    localJPanel.add(localJButton1);
    localJPanel.add(localJButton2);
    TestFrame localTestFrame2 = new TestFrame(localJPanel);
  }
  
  private void calculateGeometry()
  {
    Insets localInsets = getInsets();
    this.tubeRect.x = localInsets.left;
    this.tubeRect.y = localInsets.top;
    this.tubeRect.width = (getWidth() - localInsets.left - localInsets.right - 1);
    this.tubeRect.height = (getHeight() - localInsets.top - localInsets.bottom - 1);
    this.mercuryRect = new Rectangle();
    double d1 = getValue();
    double d2 = getMinVisibleValue();
    double d3 = getMaxVisibleValue();
    double d4;
    double d5;
    double d6;
    if (getOrientation() == Orientation.HORIZONTAL)
    {
      if (d1 < 0.0D)
      {
        d4 = d1;
        d5 = 0.0D;
      }
      else if (d1 > 0.0D)
      {
        d4 = 0.0D;
        d5 = d1;
      }
      else
      {
        return;
      }
      if ((d4 > d5) || (d4 > d3) || (d5 < d2)) {
        return;
      }
      if (d4 < d2)
      {
        this.mercuryRect.x = this.tubeRect.x;
      }
      else
      {
        d6 = (d4 - d2) / (d3 - d2);
        this.mercuryRect.x = ((int)(this.mercuryRect.x + d6 * this.tubeRect.width));
      }
      if (d5 > d3)
      {
        this.mercuryRect.width = (this.tubeRect.width - (this.mercuryRect.x - this.tubeRect.x));
      }
      else
      {
        d6 = (d5 - d2) / (d3 - d2);
        this.mercuryRect.width = ((int)(this.tubeRect.x + d6 * this.tubeRect.width - this.mercuryRect.x));
      }
      this.mercuryRect.y = this.tubeRect.y;
      this.mercuryRect.height = this.tubeRect.height;
    }
    else if (getOrientation() == Orientation.VERTICAL)
    {
      if (d1 < 0.0D)
      {
        d4 = 0.0D;
        d5 = d1;
      }
      else if (d1 > 0.0D)
      {
        d4 = d1;
        d5 = 0.0D;
      }
      else
      {
        return;
      }
      if ((d4 < d5) || (d4 < d2) || (d5 > d3)) {
        return;
      }
      if (d4 > d3)
      {
        this.mercuryRect.y = this.tubeRect.y;
      }
      else
      {
        d6 = (d3 - d4) / (d3 - d2);
        this.mercuryRect.y = ((int)(this.mercuryRect.y + d6 * this.tubeRect.height));
      }
      if (d5 < d2)
      {
        this.mercuryRect.height = (this.tubeRect.height - (this.mercuryRect.y - this.tubeRect.y));
      }
      else
      {
        d6 = (d3 - d5) / (d3 - d2);
        this.mercuryRect.height = ((int)(this.tubeRect.y + d6 * this.tubeRect.height - this.mercuryRect.y));
      }
      this.mercuryRect.x = this.tubeRect.x;
      this.mercuryRect.width = this.tubeRect.width;
    }
  }
  
  public static class Orientation
  {
    public static final Orientation HORIZONTAL = new Orientation("horizontal");
    public static final Orientation VERTICAL = new Orientation("vertical");
    private final String name;
    
    private Orientation(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/subtractive_color_mixing.jar!/edu/brown/cs/exploratories/components/swing/ScalarThermometer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */