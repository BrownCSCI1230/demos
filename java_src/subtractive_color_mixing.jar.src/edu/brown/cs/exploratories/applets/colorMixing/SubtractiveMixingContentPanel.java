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
import javax.swing.JRadioButton;
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

public class SubtractiveMixingContentPanel
  extends JPanel
{
  public static final int DEFAULT_THERMOMETER_VALUE = 0;
  public static final Color CYAN = new Color(100, 255, 255, 255);
  public static final Color MAGENTA = new Color(255, 100, 255, 255);
  public static final Color YELLOW = new Color(255, 255, 100, 255);
  public static final Color BLACK = new Color(0, 0, 0, 255);
  private static final String RESOURCES_PATH = "subtractiveColorMixingResources";
  private Composite subtractiveMixingComposite = SubtractiveMixingComposite.SINGLETON;
  private static final Color DEFAULT_FOREGROUND_COLOR = new Color(100, 255, 255, 128);
  private JRadioButton cmyRadioButton;
  private JPanel thermometerPanel;
  private ScalarThermometer magentaThermometer;
  private JToggleButton magentaButton;
  private JLabel yellow05Label;
  private JLabel black10Label;
  private JToggleButton cyanButton;
  private JLabel blackLabel;
  private JLabel black00Label;
  private ButtonGroup cmykSelectorButtonGroup;
  private JRadioButton cmykRadioButton;
  private JPanel controlsPanel;
  private JLabel cyan05Label;
  private ButtonGroup colorSelectorButtonGroup;
  private ScalarThermometer blackThermometer;
  private JLabel magenta05Label;
  private JLabel yellowLabel;
  private JLabel yellow00Label;
  private JSlider alphaSlider;
  private PaintCanvas paintCanvas;
  private ScalarThermometer yellowThermometer;
  private JLabel yello10Label;
  private JLabel cyan10Label;
  private JToggleButton blackButton;
  private JLabel cyanLabel;
  private JLabel cyan00Label;
  private JLabel black05Label;
  private JTextField alphaTextField;
  private JLabel magenta10Label;
  private JPanel canvasPanel;
  private JLabel magentaLabel;
  private JLabel magenta00Label;
  private JPanel alphaPanel;
  private JToggleButton yellowButton;
  private ScalarThermometer cyanThermometer;
  private JPanel colorSelectorPanel;
  private JToolBar jToolBar1;
  
  public SubtractiveMixingContentPanel()
  {
    initComponents();
    this.cyanButton.setSelected(true);
    this.alphaSlider.setValue(128);
  }
  
  private void initComponents()
  {
    this.cmykSelectorButtonGroup = new ButtonGroup();
    this.colorSelectorButtonGroup = new ButtonGroup();
    this.canvasPanel = new JPanel();
    this.paintCanvas = new PaintCanvas();
    this.controlsPanel = new JPanel();
    this.thermometerPanel = new JPanel();
    this.cyan00Label = new JLabel();
    this.cyan05Label = new JLabel();
    this.cyan10Label = new JLabel();
    this.magenta00Label = new JLabel();
    this.magenta05Label = new JLabel();
    this.magenta10Label = new JLabel();
    this.yellow00Label = new JLabel();
    this.yellow05Label = new JLabel();
    this.yello10Label = new JLabel();
    this.black00Label = new JLabel();
    this.black05Label = new JLabel();
    this.black10Label = new JLabel();
    this.cyanLabel = new JLabel();
    this.magentaLabel = new JLabel();
    this.yellowLabel = new JLabel();
    this.blackLabel = new JLabel();
    this.cmyRadioButton = new JRadioButton();
    this.cmykRadioButton = new JRadioButton();
    this.cyanThermometer = new ScalarThermometer();
    this.magentaThermometer = new ScalarThermometer();
    this.yellowThermometer = new ScalarThermometer();
    this.blackThermometer = new ScalarThermometer();
    this.colorSelectorPanel = new JPanel();
    this.cyanButton = new JToggleButton();
    this.magentaButton = new JToggleButton();
    this.yellowButton = new JToggleButton();
    this.blackButton = new JToggleButton();
    this.alphaPanel = new JPanel();
    this.alphaSlider = new JSlider();
    this.alphaTextField = new JTextField();
    this.jToolBar1 = new JToolBar();
    setLayout(new BorderLayout());
    setBackground((Color)UIManager.getDefaults().get("Panel.background"));
    this.canvasPanel.setLayout(new BorderLayout());
    this.canvasPanel.setBorder(new TitledBorder("Canvas"));
    this.paintCanvas.setBorder(new SoftBevelBorder(1));
    this.paintCanvas.setForeground(DEFAULT_FOREGROUND_COLOR);
    this.paintCanvas.setComposite(this.subtractiveMixingComposite);
    this.paintCanvas.addMouseListener(new MouseAdapter()
    {
      public void mouseExited(MouseEvent paramAnonymousMouseEvent)
      {
        SubtractiveMixingContentPanel.this.paintCanvasMouseExited(paramAnonymousMouseEvent);
      }
    });
    this.paintCanvas.addMouseMotionListener(new MouseMotionAdapter()
    {
      public void mouseMoved(MouseEvent paramAnonymousMouseEvent)
      {
        SubtractiveMixingContentPanel.this.paintCanvasMouseMoved(paramAnonymousMouseEvent);
      }
    });
    this.canvasPanel.add(this.paintCanvas, "Center");
    add(this.canvasPanel, "Center");
    this.controlsPanel.setLayout(new GridBagLayout());
    this.thermometerPanel.setLayout(new GridBagLayout());
    this.thermometerPanel.setBorder(new TitledBorder("Color Components"));
    this.thermometerPanel.setMinimumSize(new Dimension(100, 78));
    this.thermometerPanel.setOpaque(false);
    this.cyan00Label.setText("0.0");
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.cyan00Label, localGridBagConstraints);
    this.cyan05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.cyan05Label, localGridBagConstraints);
    this.cyan10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 2, 5, 2);
    this.thermometerPanel.add(this.cyan10Label, localGridBagConstraints);
    this.magenta00Label.setText("0.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.magenta00Label, localGridBagConstraints);
    this.magenta05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.magenta05Label, localGridBagConstraints);
    this.magenta10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.magenta10Label, localGridBagConstraints);
    this.yellow00Label.setText("0.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.yellow00Label, localGridBagConstraints);
    this.yellow05Label.setText("0.5");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.yellow05Label, localGridBagConstraints);
    this.yello10Label.setText("1.0");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.yello10Label, localGridBagConstraints);
    this.black00Label.setText("0.0");
    this.black00Label.setEnabled(false);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 6;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 15;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.black00Label, localGridBagConstraints);
    this.black05Label.setText("0.5");
    this.black05Label.setEnabled(false);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 6;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.black05Label, localGridBagConstraints);
    this.black10Label.setText("1.0");
    this.black10Label.setEnabled(false);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 6;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 11;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 2);
    this.thermometerPanel.add(this.black10Label, localGridBagConstraints);
    this.cyanLabel.setHorizontalAlignment(0);
    this.cyanLabel.setText("C");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.cyanLabel, localGridBagConstraints);
    this.magentaLabel.setHorizontalAlignment(0);
    this.magentaLabel.setText("M");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 3;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.magentaLabel, localGridBagConstraints);
    this.yellowLabel.setHorizontalAlignment(0);
    this.yellowLabel.setText("Y");
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 5;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 10);
    this.thermometerPanel.add(this.yellowLabel, localGridBagConstraints);
    this.blackLabel.setHorizontalAlignment(0);
    this.blackLabel.setText("K");
    this.blackLabel.setEnabled(false);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 7;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 2);
    this.thermometerPanel.add(this.blackLabel, localGridBagConstraints);
    this.cmyRadioButton.setSelected(true);
    this.cmyRadioButton.setText("CMY");
    this.cmykSelectorButtonGroup.add(this.cmyRadioButton);
    this.cmyRadioButton.setHorizontalAlignment(2);
    this.cmyRadioButton.setOpaque(false);
    this.cmyRadioButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.cmyRadioButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 4;
    localGridBagConstraints.gridwidth = 4;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(10, 5, 0, 0);
    this.thermometerPanel.add(this.cmyRadioButton, localGridBagConstraints);
    this.cmykRadioButton.setText("CMYK");
    this.cmykSelectorButtonGroup.add(this.cmykRadioButton);
    this.cmykRadioButton.setHorizontalAlignment(2);
    this.cmykRadioButton.setOpaque(false);
    this.cmykRadioButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.cmykRadioButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 4;
    localGridBagConstraints.gridwidth = 4;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.insets = new Insets(10, 0, 0, 5);
    this.thermometerPanel.add(this.cmykRadioButton, localGridBagConstraints);
    this.cyanThermometer.setMercuryColor(new Color(100, 255, 255));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.insets = new Insets(5, 5, 5, 5);
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.thermometerPanel.add(this.cyanThermometer, localGridBagConstraints);
    this.magentaThermometer.setMercuryColor(new Color(255, 100, 255));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 3;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.insets = new Insets(5, 5, 5, 5);
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.thermometerPanel.add(this.magentaThermometer, localGridBagConstraints);
    this.yellowThermometer.setMercuryColor(new Color(255, 255, 100));
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 5;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.insets = new Insets(5, 5, 5, 5);
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.thermometerPanel.add(this.yellowThermometer, localGridBagConstraints);
    this.blackThermometer.setEnabled(false);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 7;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.insets = new Insets(5, 5, 5, 5);
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.thermometerPanel.add(this.blackThermometer, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.controlsPanel.add(this.thermometerPanel, localGridBagConstraints);
    this.colorSelectorPanel.setLayout(new GridBagLayout());
    this.colorSelectorPanel.setBorder(new TitledBorder("Color Selector"));
    this.cyanButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/cyanBottleClosed.gif")));
    this.cyanButton.setSelected(true);
    this.cyanButton.setToolTipText("Cyan");
    this.colorSelectorButtonGroup.add(this.cyanButton);
    this.cyanButton.setHorizontalTextPosition(0);
    this.cyanButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/cyanBottleOpen.gif")));
    this.cyanButton.setVerticalTextPosition(3);
    this.cyanButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.cyanButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    this.colorSelectorPanel.add(this.cyanButton, localGridBagConstraints);
    this.magentaButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/magentaBottleClosed.gif")));
    this.magentaButton.setToolTipText("Magenta");
    this.colorSelectorButtonGroup.add(this.magentaButton);
    this.magentaButton.setHorizontalTextPosition(0);
    this.magentaButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/magentaBottleOpen.gif")));
    this.magentaButton.setVerticalTextPosition(3);
    this.magentaButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.magentaButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    this.colorSelectorPanel.add(this.magentaButton, localGridBagConstraints);
    this.yellowButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/yellowBottleClosed.gif")));
    this.yellowButton.setToolTipText("Yellow");
    this.colorSelectorButtonGroup.add(this.yellowButton);
    this.yellowButton.setHorizontalTextPosition(0);
    this.yellowButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/yellowBottleOpen.gif")));
    this.yellowButton.setVerticalTextPosition(3);
    this.yellowButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.yellowButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    this.colorSelectorPanel.add(this.yellowButton, localGridBagConstraints);
    this.blackButton.setIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/blackBottleClosed.gif")));
    this.blackButton.setToolTipText("Black");
    this.colorSelectorButtonGroup.add(this.blackButton);
    this.blackButton.setHorizontalTextPosition(0);
    this.blackButton.setSelectedIcon(new ImageIcon(getClass().getResource("/edu/brown/cs/exploratories/applets/colorMixing/subtractiveColorMixingResources/blackBottleOpen.gif")));
    this.blackButton.setVerticalTextPosition(3);
    this.blackButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        SubtractiveMixingContentPanel.this.blackButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    this.colorSelectorPanel.add(this.blackButton, localGridBagConstraints);
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
        SubtractiveMixingContentPanel.this.alphaSliderStateChanged(paramAnonymousChangeEvent);
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
  
  private void blackButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(BLACK.getRed(), BLACK.getGreen(), BLACK.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void yellowButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(YELLOW.getRed(), YELLOW.getGreen(), YELLOW.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void magentaButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(MAGENTA.getRed(), MAGENTA.getGreen(), MAGENTA.getBlue(), localColor.getAlpha());
    this.paintCanvas.setForeground(localColor);
  }
  
  private void cyanButtonActionPerformed(ActionEvent paramActionEvent)
  {
    Color localColor = this.paintCanvas.getForeground();
    localColor = new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), localColor.getAlpha());
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
    this.cyanThermometer.setValue(0.0D);
    this.magentaThermometer.setValue(0.0D);
    this.yellowThermometer.setValue(0.0D);
    this.blackThermometer.setValue(0.0D);
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
      int m = 255 - arrayOfInt[i];
      int n = 255 - arrayOfInt[j];
      int i1 = 255 - arrayOfInt[k];
      int i2 = 0;
      if (this.cmykRadioButton.isSelected())
      {
        i2 = Math.min(m, Math.min(n, i1));
        m -= i2;
        n -= i2;
        i1 -= i2;
      }
      this.cyanThermometer.setValue(m);
      this.magentaThermometer.setValue(n);
      this.yellowThermometer.setValue(i1);
      this.blackThermometer.setValue(i2);
    }
  }
  
  private void cmykRadioButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.black00Label.setEnabled(true);
    this.black05Label.setEnabled(true);
    this.black10Label.setEnabled(true);
    this.blackLabel.setEnabled(true);
    this.blackThermometer.setEnabled(true);
  }
  
  private void cmyRadioButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.black00Label.setEnabled(false);
    this.black05Label.setEnabled(false);
    this.black10Label.setEnabled(false);
    this.blackLabel.setEnabled(false);
    this.blackThermometer.setEnabled(false);
  }
  
  public PaintCanvas getPaintCanvas()
  {
    return this.paintCanvas;
  }
}


/* Location:              /Users/masonbartle/Downloads/subtractive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/SubtractiveMixingContentPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */