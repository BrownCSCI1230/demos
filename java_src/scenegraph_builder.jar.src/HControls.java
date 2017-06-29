/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JSlider;
/*     */ 
/*     */ public class HControls extends javax.swing.JPanel implements javax.swing.event.ChangeListener, HChooser
/*     */ {
/*     */   private JSlider _translateX;
/*     */   private JSlider _translateY;
/*     */   private JSlider _scaleX;
/*     */   private JSlider _scaleY;
/*     */   private JSlider _rotate;
/*     */   private Color backColor;
/*     */   private HControllable _currentControl;
/*     */   private HRobotContainer _container;
/*     */   private JLabel _translateLabel;
/*     */   private JLabel _xLabel;
/*     */   private JLabel _yLabel;
/*     */   private JLabel _scaleLabel;
/*     */   private JLabel _scaleXLabel;
/*     */   private JLabel _scaleYLabel;
/*     */   private JLabel _rotateLabel;
/*     */   private Color _activeColor;
/*     */   private Color _disabledColor;
/*  29 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HControls(HRobotContainer paramHRobotContainer)
/*     */   {
/*  38 */     this._container = paramHRobotContainer;
/*  39 */     setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
/*  40 */     this.backColor = BACKGROUND;
/*  41 */     setBackground(this.backColor);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  46 */     this._disabledColor = new Color(200, 200, 200);
/*  47 */     this._activeColor = new Color(100, 100, 100);
/*     */     
/*     */ 
/*  50 */     setPreferredSize(getSize());
/*     */     
/*     */ 
/*  53 */     this._translateX = new JSlider(0, 65236, 300, 0);
/*  54 */     this._translateX.setBackground(this.backColor);
/*  55 */     this._translateX.addChangeListener(this);
/*  56 */     this._translateX.setEnabled(false);
/*  57 */     this._translateX.setMajorTickSpacing(100);
/*  58 */     this._translateX.setMinorTickSpacing(50);
/*  59 */     this._translateX.setPaintTicks(true);
/*     */     
/*  61 */     this._translateY = new JSlider(0, 65236, 300, 0);
/*  62 */     this._translateY.setBackground(this.backColor);
/*  63 */     this._translateY.addChangeListener(this);
/*  64 */     this._translateY.setEnabled(false);
/*  65 */     this._translateY.setMajorTickSpacing(100);
/*  66 */     this._translateY.setMinorTickSpacing(50);
/*  67 */     this._translateY.setPaintTicks(true);
/*     */     
/*  69 */     this._scaleX = new JSlider(0, 65236, 300, 0);
/*  70 */     this._scaleX.setBackground(this.backColor);
/*  71 */     this._scaleX.addChangeListener(this);
/*  72 */     this._scaleX.setEnabled(false);
/*  73 */     this._scaleX.setMajorTickSpacing(100);
/*  74 */     this._scaleX.setMinorTickSpacing(50);
/*  75 */     this._scaleX.setPaintTicks(true);
/*     */     
/*  77 */     this._scaleY = new JSlider(0, 65236, 300, 0);
/*  78 */     this._scaleY.setBackground(this.backColor);
/*  79 */     this._scaleY.addChangeListener(this);
/*  80 */     this._scaleY.setEnabled(false);
/*  81 */     this._scaleY.setMajorTickSpacing(100);
/*  82 */     this._scaleY.setMinorTickSpacing(50);
/*  83 */     this._scaleY.setPaintTicks(true);
/*     */     
/*  85 */     this._rotate = new JSlider(0, 65176, 360, 0);
/*  86 */     this._rotate.setBackground(this.backColor);
/*  87 */     this._rotate.addChangeListener(this);
/*  88 */     this._rotate.setEnabled(false);
/*  89 */     this._rotate.setMajorTickSpacing(180);
/*  90 */     this._rotate.setMinorTickSpacing(60);
/*  91 */     this._rotate.setPaintTicks(true);
/*     */     
/*     */ 
/*  94 */     this._translateLabel = new JLabel("Translate");
/*  95 */     this._xLabel = new JLabel("X");
/*  96 */     this._yLabel = new JLabel("Y");
/*  97 */     this._scaleLabel = new JLabel("Scale");
/*  98 */     this._scaleXLabel = new JLabel("X");
/*  99 */     this._scaleYLabel = new JLabel("Y");
/* 100 */     this._rotateLabel = new JLabel("Rotate");
/*     */     
/*     */ 
/* 103 */     Color localColor = this._disabledColor;
/* 104 */     this._translateLabel.setForeground(localColor);
/* 105 */     this._xLabel.setForeground(localColor);
/* 106 */     this._yLabel.setForeground(localColor);
/* 107 */     this._scaleLabel.setForeground(localColor);
/* 108 */     this._scaleXLabel.setForeground(localColor);
/* 109 */     this._scaleYLabel.setForeground(localColor);
/* 110 */     this._rotateLabel.setForeground(localColor);
/*     */     
/* 112 */     GridBagLayout localGridBagLayout = new GridBagLayout();
/* 113 */     GridBagConstraints localGridBagConstraints = new GridBagConstraints();
/*     */     
/* 115 */     setLayout(localGridBagLayout);
/*     */     
/*     */ 
/* 118 */     addItem(this._translateLabel, localGridBagLayout, localGridBagConstraints, 0, 0, 6, 1, 1, 0.125D, 1.0D);
/* 119 */     addItem(this._xLabel, localGridBagLayout, localGridBagConstraints, 1, 1, 1, 1, 3, 0.125D, 0.1D);
/* 120 */     addItem(this._yLabel, localGridBagLayout, localGridBagConstraints, 1, 2, 1, 1, 3, 0.125D, 0.1D);
/* 121 */     addItem(this._translateX, localGridBagLayout, localGridBagConstraints, 2, 1, 1, 1, 2, 0.125D, 0.5D);
/* 122 */     addItem(this._translateY, localGridBagLayout, localGridBagConstraints, 2, 2, 1, 1, 2, 0.125D, 0.5D);
/* 123 */     addItem(this._scaleLabel, localGridBagLayout, localGridBagConstraints, 0, 3, 6, 1, 1, 0.125D, 1.0D);
/* 124 */     addItem(this._scaleXLabel, localGridBagLayout, localGridBagConstraints, 1, 4, 1, 1, 3, 0.0D, 0.1D);
/* 125 */     addItem(this._scaleX, localGridBagLayout, localGridBagConstraints, 2, 4, 5, 1, 2, 0.125D, 0.9D);
/* 126 */     addItem(this._scaleYLabel, localGridBagLayout, localGridBagConstraints, 1, 5, 1, 1, 3, 0.0D, 0.1D);
/* 127 */     addItem(this._scaleY, localGridBagLayout, localGridBagConstraints, 2, 5, 5, 1, 2, 0.125D, 0.9D);
/* 128 */     addItem(this._rotateLabel, localGridBagLayout, localGridBagConstraints, 0, 6, 6, 1, 1, 0.125D, 1.0D);
/* 129 */     addItem(this._rotate, localGridBagLayout, localGridBagConstraints, 2, 7, 5, 1, 2, 0.125D, 0.9D);
/* 130 */     validate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItem(Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 140 */     paramGridBagConstraints.gridx = paramInt1;
/* 141 */     paramGridBagConstraints.gridy = paramInt2;
/* 142 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 143 */     paramGridBagConstraints.gridheight = paramInt4;
/* 144 */     paramGridBagConstraints.weightx = paramDouble2;
/* 145 */     paramGridBagConstraints.weighty = paramDouble1;
/* 146 */     paramGridBagConstraints.fill = paramInt5;
/* 147 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 148 */     add(paramComponent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setActive(HControllable paramHControllable)
/*     */   {
/* 155 */     this._currentControl = paramHControllable;
/*     */     
/*     */ 
/* 158 */     if (this._currentControl.translateActive()) {
/* 159 */       this._translateX.setValue((int)this._currentControl.getTransform().x);
/* 160 */       this._translateY.setValue((int)this._currentControl.getTransform().y);
/* 161 */       this._translateX.setEnabled(true);
/* 162 */       this._translateY.setEnabled(true);
/* 163 */       this._scaleX.setEnabled(false);
/* 164 */       this._scaleY.setEnabled(false);
/* 165 */       this._rotate.setEnabled(false);
/* 166 */       this._translateX.requestFocus();
/*     */       
/*     */ 
/* 169 */       this._translateLabel.setForeground(this._activeColor);
/* 170 */       this._xLabel.setForeground(this._activeColor);
/* 171 */       this._yLabel.setForeground(this._activeColor);
/* 172 */       this._scaleLabel.setForeground(this._disabledColor);
/* 173 */       this._scaleXLabel.setForeground(this._disabledColor);
/* 174 */       this._scaleXLabel.setForeground(this._disabledColor);
/* 175 */       this._rotateLabel.setForeground(this._disabledColor);
/*     */ 
/*     */     }
/* 178 */     else if (this._currentControl.scaleActive()) {
/* 179 */       this._scaleX.setValue((int)(this._currentControl.getTransform().x * 100.0F));
/* 180 */       this._scaleX.setEnabled(true);
/* 181 */       this._scaleY.setValue((int)(this._currentControl.getTransform().y * 100.0F));
/* 182 */       this._scaleY.setEnabled(true);
/* 183 */       this._rotate.setEnabled(false);
/* 184 */       this._translateX.setEnabled(false);
/* 185 */       this._translateY.setEnabled(false);
/* 186 */       this._scaleX.requestFocus();
/*     */       
/*     */ 
/* 189 */       this._translateLabel.setForeground(this._disabledColor);
/* 190 */       this._xLabel.setForeground(this._disabledColor);
/* 191 */       this._yLabel.setForeground(this._disabledColor);
/* 192 */       this._scaleLabel.setForeground(this._activeColor);
/* 193 */       this._scaleXLabel.setForeground(this._activeColor);
/* 194 */       this._scaleYLabel.setForeground(this._activeColor);
/* 195 */       this._rotateLabel.setForeground(this._disabledColor);
/*     */ 
/*     */     }
/* 198 */     else if (this._currentControl.rotateActive()) {
/* 199 */       this._rotate.setValue((int)(this._currentControl.getTransform().x * 180.0F / 3.141592653589793D));
/* 200 */       this._rotate.setEnabled(this._currentControl.rotateActive());
/* 201 */       this._scaleX.setEnabled(false);
/* 202 */       this._scaleY.setEnabled(false);
/* 203 */       this._rotate.setEnabled(true);
/* 204 */       this._translateX.setEnabled(false);
/* 205 */       this._translateY.setEnabled(false);
/* 206 */       this._rotate.requestFocus();
/*     */       
/*     */ 
/* 209 */       this._translateLabel.setForeground(this._disabledColor);
/* 210 */       this._xLabel.setForeground(this._disabledColor);
/* 211 */       this._yLabel.setForeground(this._disabledColor);
/* 212 */       this._scaleLabel.setForeground(this._disabledColor);
/* 213 */       this._scaleXLabel.setForeground(this._disabledColor);
/* 214 */       this._scaleYLabel.setForeground(this._disabledColor);
/* 215 */       this._rotateLabel.setForeground(this._activeColor);
/*     */     }
/*     */     else {
/* 218 */       this._scaleX.setEnabled(false);
/* 219 */       this._scaleY.setEnabled(false);
/* 220 */       this._rotate.setEnabled(false);
/* 221 */       this._translateX.setEnabled(false);
/* 222 */       this._translateY.setEnabled(false);
/*     */       
/* 224 */       this._translateLabel.setForeground(this._disabledColor);
/* 225 */       this._xLabel.setForeground(this._disabledColor);
/* 226 */       this._yLabel.setForeground(this._disabledColor);
/* 227 */       this._scaleLabel.setForeground(this._disabledColor);
/* 228 */       this._scaleXLabel.setForeground(this._disabledColor);
/* 229 */       this._scaleYLabel.setForeground(this._disabledColor);
/* 230 */       this._rotateLabel.setForeground(this._disabledColor);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 235 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void stateChanged(javax.swing.event.ChangeEvent paramChangeEvent)
/*     */   {
/* 242 */     JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
/*     */     
/*     */ 
/* 245 */     if (localJSlider == this._translateX) {
/* 246 */       this._currentControl.updateTransform(new HPosition(
/* 247 */         localJSlider.getValue(), 
/* 248 */         this._currentControl.getTransform().y));
/*     */ 
/*     */     }
/* 251 */     else if (localJSlider == this._translateY) {
/* 252 */       this._currentControl.updateTransform(new HPosition(
/* 253 */         this._currentControl.getTransform().x, 
/* 254 */         localJSlider.getValue()));
/*     */ 
/*     */     }
/* 257 */     else if (localJSlider == this._scaleX) {
/* 258 */       this._currentControl.updateTransform(new HPosition(
/* 259 */         localJSlider.getValue() / 100.0F, 
/* 260 */         this._currentControl.getTransform().y));
/*     */     }
/* 262 */     else if (localJSlider == this._scaleY) {
/* 263 */       this._currentControl.updateTransform(new HPosition(
/* 264 */         this._currentControl.getTransform().x, 
/* 265 */         localJSlider.getValue() / 100.0F));
/*     */ 
/*     */     }
/* 268 */     else if (localJSlider == this._rotate) {
/* 269 */       this._currentControl.updateTransform(new HPosition(
/* 270 */         (float)
/*     */         
/* 272 */         (localJSlider.getValue() * 3.141592653589793D / 180.0D), 0.0F));
/*     */     }
/* 274 */     this._container.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addMouseListener(java.awt.event.MouseListener paramMouseListener)
/*     */   {
/* 281 */     Component[] arrayOfComponent = getComponents();
/*     */     try {
/* 283 */       for (int i = 0;; i++) {
/* 284 */         arrayOfComponent[i].addMouseListener(paramMouseListener);
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 287 */       super.addMouseListener(paramMouseListener);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HControls.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */