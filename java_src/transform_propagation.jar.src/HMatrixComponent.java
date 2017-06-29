/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GridLayout;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ public class HMatrixComponent
/*     */   extends JPanel
/*     */   implements HMatrixChangeListener
/*     */ {
/*     */   private JLabel[][] _labels;
/*     */   private Color _backColor;
/*     */   private int _sizeX;
/*     */   private int _sizeY;
/*     */   private int _tickSize;
/*     */   private static final double EPSILON = 5.0E-5D;
/*     */   private JPanel _grid;
/*     */   private JLabel _valueLabel;
/*     */   private float[][] _values;
/*  28 */   private static Font EQUALSFONT = new Font("SansSerif", 0, 22);
/*  29 */   private static Color LINECOLOR = new Color(30, 30, 30);
/*     */   
/*     */ 
/*     */   public HMatrixComponent(float[][] paramArrayOfFloat, Color paramColor, String paramString1, String paramString2)
/*     */   {
/*  34 */     this._values = paramArrayOfFloat;
/*  35 */     this._backColor = paramColor;
/*  36 */     this._labels = new JLabel[3][3];
/*     */     
/*  38 */     this._sizeX = 135;
/*  39 */     this._sizeY = 100;
/*  40 */     this._tickSize = 10;
/*     */     
/*  42 */     setLayout(new BoxLayout(this, 1));
/*     */     
/*     */ 
/*  45 */     this._grid = new JPanel();
/*  46 */     this._grid.setLayout(new GridLayout(3, 3));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  51 */     this._grid.setBackground(this._backColor);
/*     */     
/*     */ 
/*  54 */     setSize(new Dimension(this._sizeX, this._sizeY));
/*  55 */     setPreferredSize(getSize());
/*  56 */     setMaximumSize(getSize());
/*  57 */     setMinimumSize(getSize());
/*     */     
/*  59 */     setBackground(this._backColor);
/*  60 */     setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
/*     */     
/*     */ 
/*  63 */     for (int i = 0; i < 3; i++) {
/*  64 */       for (int j = 0; j < 3; j++) {
/*  65 */         if ((this._values[j][i] < 5.0E-5D) && (this._values[j][i] > -5.0E-5D))
/*  66 */           this._values[j][i] = 0.0F;
/*  67 */         this._labels[i][j] = new JLabel(Float.toString(this._values[j][i]));
/*  68 */         this._labels[i][j].setBackground(this._backColor);
/*  69 */         this._grid.add(this._labels[i][j]);
/*     */       }
/*     */     }
/*  72 */     this._grid.setBorder(BorderFactory.createLoweredBevelBorder());
/*  73 */     add(this._grid);
/*     */     
/*  75 */     if (!paramString1.equals(" "))
/*     */     {
/*  77 */       JLabel localJLabel = new JLabel(paramString1);
/*  78 */       localJLabel.setAlignmentX(0.5F);
/*  79 */       localJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
/*  80 */       add(localJLabel);
/*     */     }
/*  82 */     if (!paramString2.equals("")) {
/*  83 */       this._valueLabel = new JLabel(paramString2);
/*  84 */       this._valueLabel.setAlignmentX(0.5F);
/*  85 */       this._valueLabel.setBorder(BorderFactory.createLoweredBevelBorder());
/*  86 */       add(this._valueLabel);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void matrixChanged(HMatrixObject paramHMatrixObject)
/*     */   {
/*  97 */     this._values = paramHMatrixObject.getMatrix();
/*  98 */     for (int i = 0; i < 3; i++) {
/*  99 */       for (int j = 0; j < 3; j++) {
/* 100 */         if ((this._values[j][i] < 5.0E-5D) && (this._values[j][i] > -5.0E-5D))
/* 101 */           this._values[j][i] = 0.0F;
/* 102 */         this._labels[i][j].setText(Float.toString(this._values[j][i]));
/*     */       }
/*     */     }
/* 105 */     String str = paramHMatrixObject.getValues();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */     if (!str.equals("")) {
/* 112 */       this._valueLabel.setText(str);
/* 113 */       this._valueLabel.revalidate();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 122 */     super.paint(paramGraphics);
/* 123 */     paramGraphics.setColor(LINECOLOR);
/*     */     
/*     */ 
/* 126 */     paramGraphics.drawLine(0, 0, 0, this._sizeY);
/* 127 */     paramGraphics.drawLine(1, 0, 1, this._sizeY);
/*     */     
/*     */ 
/* 130 */     paramGraphics.drawLine(this._sizeX - 2, 0, this._sizeX - 2, this._sizeY);
/* 131 */     paramGraphics.drawLine(this._sizeX - 1, 0, this._sizeX - 1, this._sizeY);
/*     */     
/*     */ 
/* 134 */     paramGraphics.drawLine(0, 0, this._tickSize, 0);
/* 135 */     paramGraphics.drawLine(0, 1, this._tickSize, 1);
/* 136 */     paramGraphics.drawLine(0, this._sizeY - 1, 10, this._sizeY - 1);
/* 137 */     paramGraphics.drawLine(0, this._sizeY - 2, 10, this._sizeY - 2);
/*     */     
/*     */ 
/* 140 */     paramGraphics.drawLine(this._sizeX - 1, 0, this._sizeX - this._tickSize - 1, 0);
/* 141 */     paramGraphics.drawLine(this._sizeX - 1, 1, this._sizeX - this._tickSize - 1, 1);
/* 142 */     paramGraphics.drawLine(this._sizeX - 1, this._sizeY - 1, this._sizeX - this._tickSize - 1, this._sizeY - 1);
/* 143 */     paramGraphics.drawLine(this._sizeX - 1, this._sizeY - 2, this._sizeX - this._tickSize - 1, this._sizeY - 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JPanel getEquals(Color paramColor)
/*     */   {
/* 152 */     JPanel localJPanel = new JPanel();
/* 153 */     localJPanel.setLayout(new GridLayout(1, 1));
/* 154 */     localJPanel.setSize(new Dimension(25, 20));
/* 155 */     localJPanel.setPreferredSize(localJPanel.getSize());
/* 156 */     localJPanel.setMaximumSize(localJPanel.getSize());
/* 157 */     localJPanel.setBorder(BorderFactory.createRaisedBevelBorder());
/* 158 */     localJPanel.setBackground(HMatrixDisplay.BACKGROUND);
/*     */     
/* 160 */     JLabel localJLabel = new JLabel("=");
/* 161 */     localJLabel.setFont(EQUALSFONT);
/* 162 */     localJLabel.setAlignmentX(0.5F);
/* 163 */     localJLabel.setHorizontalAlignment(0);
/* 164 */     localJLabel.setVerticalAlignment(0);
/*     */     
/* 166 */     localJPanel.add(localJLabel);
/* 167 */     return localJPanel;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HMatrixComponent.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */