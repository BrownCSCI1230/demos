package edu.brown.cs.exploratories.applets.colorMixing;

import edu.brown.cs.exploratories.components.swing.ScalarThermometer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.NumberFormat;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdditiveMixingContentPanel
  extends JPanel
{
  public static final int DEFAULT_THERMOMETER_VALUE = 0;
  public static final Color RED = new Color(255, 0, 0, 255);
  public static final Color GREEN = new Color(0, 255, 0, 255);
  public static final Color BLUE = new Color(0, 0, 255, 255);
  private static final String RESOURCES_PATH = "additiveColorMixingResources";
  private Composite additiveMixingComposite = AdditiveMixingComposite.SINGLETON;
  private static final Color DEFAULT_FOREGROUND_COLOR = new Color(255, 0, 0, 128);
  private JLabel red05Label;
  private JPanel thermometerPanel;
  private JLabel blue10Label;
  private JLabel blueLabel;
  private JLabel blue00Label;
  private JLabel green10Label;
  private JLabel greenLabel;
  private JLabel green00Label;
  private JPanel controlsPanel;
  private JToggleButton blueButton;
  private ScalarThermometer blueThermometer;
  private ButtonGroup colorSelectorButtonGroup;
  private JLabel red10Label;
  private JToggleButton redButton;
  private JLabel redLabel;
  private ScalarThermometer greenThermometer;
  private JLabel red00Label;
  private JSlider alphaSlider;
  private PaintCanvas paintCanvas;
  private JToggleButton greenButton;
  private ScalarThermometer redThermometer;
  private JLabel blue05Label;
  private JTextField alphaTextField;
  private JLabel green05Label;
  private JPanel canvasPanel;
  private JPanel alphaPanel;
  private JPanel colorSelectorPanel;
  private JToolBar jToolBar1;
  
  public AdditiveMixingContentPanel()
  {
    Class localClass = getClass();
    initComponents();
    this.redButton.setSelected(true);
    this.alphaSlider.setValue(128);
  }
  
  private void initComponents()
  {
    this.colorSelectorButtonGroup = new ButtonGroup();
    this.canvasPanel = new JPanel();
    this.paintCanvas = new PaintCanvas();
    this.controlsPanel = new JPanel();
    this.thermometerPanel = new JPanel();
    this.red00Label = new JLabel();
    this.red05Label = new JLabel();
    this.red10Label = new JLabel();
    this.green00Label = new JLabel();
    this.green05Label = new JLabel();
    this.green10Label = new JLabel();
    this.blue00Label = new JLabel();
    this.blue05Label = new JLabel();
    this.blue10Label = new JLabel();
    this.redThermometer = new ScalarThermometer();
    this.greenThermometer = new ScalarThermometer();
    this.blueThermometer = new ScalarThermometer();
    this.redLabel = new JLabel();
    this.greenLabel = new JLabel();
    this.blueLabel = new JLabel();
    this.colorSelectorPanel = new JPanel();
    this.redButton = new JToggleButton();
    this.greenButton = new JToggleButton();
    this.blueButton = new JToggleButton();
    this.alphaPanel = new JPanel();
    this.alphaSlider = new JSlider();
    this.alphaTextField = new JTextField();
    this.jToolBar1 = new JToolBar();
    setLayout(new BorderLayout());
    setBackground((Color)UIManager.getDefaults().get("Panel.background"));
    this.canvasPanel.setLayout(new BorderLayout());
    this.canvasPanel.setBorder(new TitledBorder("Canvas"));
    this.paintCanvas.setBackground(new Color(0, 0, 0));
    this.paintCanvas.setBorder(new SoftBevelBorder(1));
    this.paintCanvas.setForeground(DEFAULT_FOREGROUND_COLOR);
    this.paintCanvas.setToolTipText("Draw on this canvas to see how color mix additively.");
    this.paintCanvas.setComposite(this.additiveMixingComposite);
    this.paintCanvas.setOpaque(true);
    this.paintCanvas.addMouseListener(new MouseAdapter()
    {
      public void mouseExited(MouseEvent paramAnonymousMouseEvent)
      {
        AdditiveMixingContentPanel.this.paintCanvasMouseExited(paramAnonymousMouseEvent);
      }
    });
    this.paintCanvas.addMouseMotionListener(new MouseMotionAdapter()
    {
      public void mouseMoved(MouseEvent paramAnonymousMouseEvent)
      {
        AdditiveMixingContentPanel.this.paintCanvasMouseMoved(paramAnonymousMouseEvent);
      }
    });
    this.canvasPanel.add(this.paintCanvas, "Center");
    add(this.canvasPanel, "Center");
    this.controlsPanel.setLayout(new GridBagLayout());
    this.thermometerPanel.setLayout(new GridBagLayout());
    this.thermometerPanel.setBorder(new TitledBorder("Color Components"));
    this.thermometerPanel.setMinimumSize(new Dimension(100, 78));
    this.thermometerPanel.setOpaque(false);
    this.red00Label.setText("0.0");
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.red00Label, localGridBagConstraints);
    this.red05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.red05Label, localGridBagConstraints);
    this.red10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.red10Label, localGridBagConstraints);
    this.green00Label.setText("0.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.green00Label, localGridBagConstraints);
    this.green05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.green05Label, localGridBagConstraints);
    this.green10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.green10Label, localGridBagConstraints);
    this.blue00Label.setText("0.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.blue00Label, localGridBagConstraints);
    this.blue05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.blue05Label, localGridBagConstraints);
    this.blue10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.blue10Label, localGridBagConstraints);
    this.redThermometer.setMaxVisibleValue(255.0D);
    this.redThermometer.setMaximumSize(new Dimension(80, 32767));
    this.redThermometer.setMercuryColor(new Color(255, 0, 0));
    this.redThermometer.setMinimumSize(new Dimension(10, 50));
    this.redThermometer.setPreferredSize(new Dimension(20, 0));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 0, 10);
    this.thermometerPanel.add(this.redThermometer, localGridBagConstraints);
    this.greenThermometer.setMaxVisibleValue(255.0D);
    this.greenThermometer.setMaximumSize(new Dimension(80, 32767));
    this.greenThermometer.setMercuryColor(new Color(0, 255, 0));
    this.greenThermometer.setMinimumSize(new Dimension(10, 50));
    this.greenThermometer.setPreferredSize(new Dimension(20, 0));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 3;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 0, 10);
    this.thermometerPanel.add(this.greenThermometer, localGridBagConstraints);
    this.blueThermometer.setMaxVisibleValue(255.0D);
    this.blueThermometer.setMaximumSize(new Dimension(80, 32767));
    this.blueThermometer.setMercuryColor(new Color(0, 0, 255));
    this.blueThermometer.setMinimumSize(new Dimension(10, 50));
    this.blueThermometer.setPreferredSize(new Dimension(20, 0));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 5;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 0, 10);
    this.thermometerPanel.add(this.blueThermometer, localGridBagConstraints);
    this.redLabel.setHorizontalAlignment(0);
    this.redLabel.setText("R");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.redLabel, localGridBagConstraints);
    this.greenLabel.setHorizontalAlignment(0);
    this.greenLabel.setText("G");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 3;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.greenLabel, localGridBagConstraints);
    this.blueLabel.setHorizontalAlignment(0);
    this.blueLabel.setText("B");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 5;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.blueLabel, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.controlsPanel.add(this.thermometerPanel, localGridBagConstraints);
    this.colorSelectorPanel.setLayout(new GridBagLayout());
    this.colorSelectorPanel.setBorder(new TitledBorder("Color Selector"));
    this.redButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/redBulbOff.gif")));
    this.redButton.setSelected(true);
    this.redButton.setToolTipText("Red");
    this.colorSelectorButtonGroup.add(this.redButton);
    this.redButton.setHorizontalTextPosition(0);
    this.redButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/redBulbOn.gif")));
    this.redButton.setVerticalTextPosition(3);
    this.redButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        AdditiveMixingContentPanel.this.redButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    this.colorSelectorPanel.add(this.redButton, localGridBagConstraints);
    this.greenButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/greenBulbOff.gif")));
    this.greenButton.setToolTipText("Green");
    this.colorSelectorButtonGroup.add(this.greenButton);
    this.greenButton.setHorizontalTextPosition(0);
    this.greenButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/greenBulbOn.gif")));
    this.greenButton.setVerticalTextPosition(3);
    this.greenButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        AdditiveMixingContentPanel.this.greenButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    this.colorSelectorPanel.add(this.greenButton, localGridBagConstraints);
    this.blueButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/blueBulbOff.gif")));
    this.blueButton.setToolTipText("Blue");
    this.colorSelectorButtonGroup.add(this.blueButton);
    this.blueButton.setHorizontalTextPosition(0);
    this.blueButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/additiveColorMixingResources/blueBulbOn.gif")));
    this.blueButton.setVerticalTextPosition(3);
    this.blueButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        AdditiveMixingContentPanel.this.blueButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 0;
    this.colorSelectorPanel.add(this.blueButton, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 1;
    this.controlsPanel.add(this.colorSelectorPanel, localGridBagConstraints);
    this.alphaPanel.setLayout(new GridBagLayout());
    this.alphaPanel.setBorder(new TitledBorder("Alpha"));
    this.alphaSlider.setMajorTickSpacing(128);
    this.alphaSlider.setMaximum(256);
    this.alphaSlider.setMinimum(64);
    this.alphaSlider.setMinorTickSpacing(64);
    this.alphaSlider.setPaintLabels(true);
    this.alphaSlider.setPaintTicks(true);
    this.alphaSlider.setSnapToTicks(true);
    this.alphaSlider.setValue(128);
    Hashtable localHashtable = new Hashtable();
    localHashtable.put(new Integer(0), new JLabel("0"));
    localHashtable.put(new Integer(64), new JLabel(".25"));
    localHashtable.put(new Integer(128), new JLabel(".5"));
    localHashtable.put(new Integer(192), new JLabel(".75"));
    localHashtable.put(new Integer(256), new JLabel("1.0"));
    this.alphaSlider.setLabelTable(localHashtable);
    this.alphaSlider.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent paramAnonymousChangeEvent)
      {
        AdditiveMixingContentPanel.this.alphaSliderStateChanged(paramAnonymousChangeEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.anchor = 18;
    this.alphaPanel.add(this.alphaSlider, localGridBagConstraints);
    this.alphaTextField.setColumns(3);
    this.alphaTextField.setEditable(false);
    this.alphaTextField.setHorizontalAlignment(4);
    this.alphaTextField.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.anchor = 18;
    this.alphaPanel.add(this.alphaTextField, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 1;
    this.controlsPanel.add(this.alphaPanel, localGridBagConstraints);
    add(this.controlsPanel, "East");
    this.jToolBar1.setRollover(true);
    add(this.jToolBar1, "North");
  }
  
  private void blueButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(BLUE.getRed(), BLUE.getGreen(), BLUE.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void greenButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(GREEN.getRed(), GREEN.getGreen(), GREEN.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void redButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(RED.getRed(), RED.getGreen(), RED.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void alphaSliderStateChanged(ChangeEvent paramChangeEvent)
  {
    NumberFormat localNumberFormat = NumberFormat.getInstance();
    localNumberFormat.setMaximumFractionDigits(2);
    localNumberFormat.setMinimumFractionDigits(2);
    localNumberFormat.setMinimumIntegerDigits(1);
    this.alphaTextField.setText(localNumberFormat.format(this.alphaSlider.getValue() / 256.0D));
    int i = Math.min(this.alphaSlider.getValue(), 255);
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(localColor.getRed(), localColor.getGreen(), localColor.getBlue(), i);
    this.paintCanvas.setForeground(localColor);
  }
  
  private void paintCanvasMouseExited(MouseEvent paramMouseEvent)
  {
    this.redThermometer.setValue(0.0D);
    this.greenThermometer.setValue(0.0D);
    this.blueThermometer.setValue(0.0D);
  }
  
  private void paintCanvasMouseMoved(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    int[] arrayOfInt = new int[3];
    arrayOfInt = this.paintCanvas.getPixel(localPoint.x, localPoint.y, arrayOfInt);
    if (arrayOfInt != null)
    {
      int i = 0;
      int j = 1;
      int k = 2;
      this.redThermometer.setValue(arrayOfInt[i]);
      this.greenThermometer.setValue(arrayOfInt[j]);
      this.blueThermometer.setValue(arrayOfInt[k]);
    }
  }
  
  public PaintCanvas getPaintCanvas()
  {
    return this.paintCanvas;
  }
}


/* Location:              /Users/masonbartle/Downloads/additive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/AdditiveMixingContentPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */