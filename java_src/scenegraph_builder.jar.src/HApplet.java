/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSplitPane;
/*     */ 
/*     */ public class HApplet extends JApplet implements java.awt.event.ComponentListener, java.awt.event.ActionListener, java.awt.event.MouseListener
/*     */ {
/*     */   private HTree _tree;
/*     */   private HRobotContainer _robotContainer;
/*     */   private HControls _transformController;
/*     */   private HMatrixDisplay _display;
/*     */   private HNodeTypeChooser _nodeSelector;
/*     */   private HToolPalette _palette;
/*     */   private int _xo;
/*     */   private int _yo;
/*     */   private int _xLevel;
/*     */   private int _yLevel;
/*     */   private javax.swing.JButton _animate;
/*     */   private javax.swing.JButton _pause;
/*     */   private Thread _animationThread;
/*     */   private JPanel _robotContainerAnimate;
/*     */   private HMatrixStacker _stacker;
/*     */   private boolean _paused;
/*     */   private JSplitPane _pane;
/*     */   private JSplitPane _horizontalPane;
/*     */   private JPanel _left;
/*     */   private JPanel _right;
/*     */   private JPanel _top;
/*     */   private JPanel _bottom;
/*     */   
/*     */   public void init()
/*     */   {
/*  41 */     GridBagLayout localGridBagLayout = new GridBagLayout();
/*  42 */     GridBagConstraints localGridBagConstraints = new GridBagConstraints();
/*     */     
/*     */ 
/*  45 */     this._xo = 50;
/*  46 */     this._yo = -20;
/*  47 */     this._xLevel = 65;
/*  48 */     this._yLevel = 50;
/*     */     
/*  50 */     this._pane = new JSplitPane(1);
/*  51 */     this._horizontalPane = new JSplitPane(0);
/*     */     
/*  53 */     this._left = new JPanel();
/*  54 */     this._left.setLayout(localGridBagLayout);
/*     */     
/*  56 */     this._right = new JPanel();
/*  57 */     this._right.setLayout(localGridBagLayout);
/*     */     
/*  59 */     this._top = new JPanel();
/*  60 */     this._top.setLayout(localGridBagLayout);
/*     */     
/*  62 */     this._bottom = new JPanel();
/*  63 */     this._bottom.setLayout(localGridBagLayout);
/*     */     
/*  65 */     JPanel localJPanel1 = new JPanel();
/*  66 */     localJPanel1.setLayout(new java.awt.GridLayout(1, 2));
/*  67 */     localJPanel1.setMaximumSize(new Dimension(32767, 20));
/*     */     
/*  69 */     this._animate = new javax.swing.JButton("Animate");
/*  70 */     this._animate.addActionListener(this);
/*  71 */     this._animate.setAlignmentX(0.5F);
/*  72 */     this._animate.setPreferredSize(new Dimension(0, 20));
/*  73 */     this._animate.setMaximumSize(new Dimension(32767, 20));
/*  74 */     this._animate.setBackground(HTree.BACKGROUND);
/*  75 */     localJPanel1.add(this._animate);
/*     */     
/*  77 */     this._pause = new javax.swing.JButton("Pause");
/*  78 */     this._pause.addActionListener(this);
/*  79 */     this._pause.setAlignmentX(0.5F);
/*  80 */     this._pause.setPreferredSize(new Dimension(0, 20));
/*  81 */     this._pause.setMaximumSize(new Dimension(32767, 20));
/*  82 */     this._pause.setBackground(HTree.BACKGROUND);
/*  83 */     this._pause.setEnabled(false);
/*  84 */     this._paused = false;
/*  85 */     localJPanel1.add(this._pause);
/*     */     
/*  87 */     getContentPane().setBackground(HTree.BACKGROUND);
/*     */     
/*     */ 
/*  90 */     this._tree = new HTree();
/*  91 */     this._tree.addMouseListener(this);
/*     */     
/*  93 */     this._robotContainer = new HRobotContainer(this._tree);
/*  94 */     this._tree.setRobotContainer(this._robotContainer);
/*  95 */     this._transformController = new HControls(this._robotContainer);
/*  96 */     this._tree.createRoot(this._transformController);
/*  97 */     this._display = new HMatrixDisplay();
/*  98 */     this._stacker = new HMatrixStacker(this);
/*  99 */     this._stacker.setSound(true);
/* 100 */     this._tree.setDisplay(this._display);
/* 101 */     this._tree.setStacker(this._stacker);
/*     */     
/* 103 */     this._nodeSelector = new HNodeTypeChooser(this._tree, this._transformController, 
/* 104 */       this._robotContainer);
/*     */     
/* 106 */     JPanel localJPanel2 = new JPanel();
/* 107 */     localJPanel2.setLayout(new javax.swing.BoxLayout(localJPanel2, 0));
/* 108 */     localJPanel2.add(this._tree);
/*     */     
/*     */ 
/*     */ 
/* 112 */     this._palette = new HToolPalette(this._nodeSelector);
/* 113 */     this._tree.setPalette(this._palette, this);
/*     */     
/* 115 */     this._nodeSelector.setPalette(this._palette);
/* 116 */     addComponentListener(this);
/*     */     
/* 118 */     this._robotContainerAnimate = new JPanel();
/* 119 */     this._robotContainerAnimate.setLayout(new javax.swing.BoxLayout(this._robotContainerAnimate, 
/* 120 */       1));
/* 121 */     this._robotContainerAnimate.add(this._robotContainer);
/*     */     
/*     */ 
/* 124 */     addItem(this._left, this._robotContainerAnimate, localGridBagLayout, localGridBagConstraints, 0, 0, 2, 6, 1, 1.0D, 0.65D);
/* 125 */     addItem(this._top, localJPanel2, localGridBagLayout, localGridBagConstraints, 2, 0, 3, 4, 1, 1.0D, 1.0D);
/* 126 */     addItem(this._top, this._nodeSelector, localGridBagLayout, localGridBagConstraints, 2, 4, 2, 2, 2, 1.0D, 0.0D);
/* 127 */     addItem(this._top, this._palette, localGridBagLayout, localGridBagConstraints, 4, 4, 1, 2, 0, 0.0D, 0.0D);
/* 128 */     addItem(this._left, this._transformController, localGridBagLayout, localGridBagConstraints, 0, 6, 2, 2, 1, 1.0D, 0.35D);
/* 129 */     addItem(this._bottom, this._display, localGridBagLayout, localGridBagConstraints, 2, 6, 3, 2, 1, 1.0D, 1.0D);
/*     */     
/* 131 */     this._left.setMinimumSize(new Dimension(200, 0));
/* 132 */     this._left.setPreferredSize(new Dimension(200, 0));
/* 133 */     this._top.setMinimumSize(new Dimension(0, 250));
/* 134 */     this._top.setPreferredSize(new Dimension(0, 400));
/*     */     
/* 136 */     this._pane.setLeftComponent(this._left);
/* 137 */     this._pane.setRightComponent(this._horizontalPane);
/*     */     
/* 139 */     this._horizontalPane.setTopComponent(this._top);
/* 140 */     this._horizontalPane.setBottomComponent(this._bottom);
/*     */     
/* 142 */     getContentPane().setLayout(new java.awt.GridLayout(1, 1));
/* 143 */     getContentPane().add(this._pane);
/*     */     
/* 145 */     this._pane.setOneTouchExpandable(true);
/* 146 */     this._pane.setDividerSize(15);
/*     */     
/* 148 */     this._horizontalPane.setOneTouchExpandable(true);
/* 149 */     this._horizontalPane.setDividerSize(15);
/*     */     
/* 151 */     this._pane.resetToPreferredSizes();
/* 152 */     this._horizontalPane.resetToPreferredSizes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItem(Container paramContainer, Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 162 */     paramGridBagConstraints.gridx = paramInt1;
/* 163 */     paramGridBagConstraints.gridy = paramInt2;
/* 164 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 165 */     paramGridBagConstraints.gridheight = paramInt4;
/* 166 */     paramGridBagConstraints.weightx = paramDouble1;
/* 167 */     paramGridBagConstraints.weighty = paramDouble2;
/* 168 */     paramGridBagConstraints.fill = paramInt5;
/* 169 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 170 */     paramComponent.addMouseListener(this);
/* 171 */     paramContainer.add(paramComponent);
/*     */   }
/*     */   
/*     */   public void addItem(Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 176 */     paramGridBagConstraints.gridx = paramInt1;
/* 177 */     paramGridBagConstraints.gridy = paramInt2;
/* 178 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 179 */     paramGridBagConstraints.gridheight = paramInt4;
/* 180 */     paramGridBagConstraints.weightx = paramDouble1;
/* 181 */     paramGridBagConstraints.weighty = paramDouble2;
/* 182 */     paramGridBagConstraints.fill = paramInt5;
/* 183 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 184 */     paramComponent.addMouseListener(this);
/* 185 */     getContentPane().add(paramComponent);
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     try {
/* 190 */       javax.swing.UIManager.setLookAndFeel(
/* 191 */         javax.swing.UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {
/* 194 */       System.out.println(
/* 195 */         "Couldn't use the cross-platform look and feel: " + localException);
/*     */     }
/*     */     
/* 198 */     HApplet localHApplet = new HApplet();
/*     */     
/*     */ 
/* 201 */     localHApplet.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HShapeNode createShape(float[][] paramArrayOfFloat, int paramInt)
/*     */   {
/* 208 */     HRobotPart localHRobotPart = new HRobotPart(paramArrayOfFloat, paramInt);
/* 209 */     HShapeNode localHShapeNode = new HShapeNode(localHRobotPart, this._tree, this._transformController);
/* 210 */     localHShapeNode.addDrawListener(this._robotContainer);
/* 211 */     this._robotContainer.addPart(localHRobotPart);
/*     */     
/* 213 */     return localHShapeNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createRobot()
/*     */   {
/* 221 */     HGroupNode localHGroupNode1 = new HGroupNode(this._tree, this._transformController);
/* 222 */     localHGroupNode1.setDescriptor("Robot");
/*     */     
/* 224 */     HGroupNode localHGroupNode2 = new HGroupNode(this._tree, this._transformController);
/* 225 */     localHGroupNode2.setDescriptor("Head and Neck");
/*     */     
/* 227 */     HGroupNode localHGroupNode3 = new HGroupNode(this._tree, this._transformController);
/* 228 */     localHGroupNode3.setDescriptor("Leg and Foot");
/*     */     
/*     */ 
/* 231 */     HTransformTranslateNode localHTransformTranslateNode1 = new HTransformTranslateNode(this._tree, 125.0F, 170.0F, 
/* 232 */       this._transformController);
/*     */     
/* 234 */     HTransformTranslateNode localHTransformTranslateNode2 = new HTransformTranslateNode(this._tree, 0.0F, -100.0F, 
/* 235 */       this._transformController);
/*     */     
/* 237 */     HTransformRotateNode localHTransformRotateNode1 = new HTransformRotateNode(
/* 238 */       this._tree, 0.0F, this._transformController);
/*     */     
/* 240 */     HTransformTranslateNode localHTransformTranslateNode3 = new HTransformTranslateNode(this._tree, 0.0F, -25.0F, 
/* 241 */       this._transformController);
/*     */     
/* 243 */     HTransformTranslateNode localHTransformTranslateNode4 = new HTransformTranslateNode(this._tree, 0.0F, 13.0F, 
/* 244 */       this._transformController);
/*     */     
/* 246 */     HTransformTranslateNode localHTransformTranslateNode5 = new HTransformTranslateNode(this._tree, -37.0F, 125.0F, 
/* 247 */       this._transformController);
/* 248 */     HTransformTranslateNode localHTransformTranslateNode6 = new HTransformTranslateNode(this._tree, 37.0F, 125.0F, 
/* 249 */       this._transformController);
/*     */     
/* 251 */     HTransformTranslateNode localHTransformTranslateNode7 = new HTransformTranslateNode(this._tree, 0.0F, -25.0F, 
/* 252 */       this._transformController);
/*     */     
/* 254 */     HTransformTranslateNode localHTransformTranslateNode8 = new HTransformTranslateNode(this._tree, -7.0F, 5.0F, 
/* 255 */       this._transformController);
/*     */     
/* 257 */     HTransformScaleNode localHTransformScaleNode = new HTransformScaleNode(this._tree, 1.0F, 
/* 258 */       this._transformController);
/*     */     
/* 260 */     HTransformRotateNode localHTransformRotateNode2 = new HTransformRotateNode(this._tree, 
/* 261 */       1.0471976F, 
/* 262 */       this._transformController);
/*     */     
/* 264 */     HTransformTranslateNode localHTransformTranslateNode9 = new HTransformTranslateNode(
/* 265 */       this._tree, -82.0F, -33.0F, this._transformController);
/*     */     
/* 267 */     HTransformTranslateNode localHTransformTranslateNode10 = new HTransformTranslateNode(
/* 268 */       this._tree, 82.0F, -33.0F, this._transformController);
/*     */     
/* 270 */     HTransformRotateNode localHTransformRotateNode3 = new HTransformRotateNode(
/* 271 */       this._tree, 2.0943952F, this._transformController);
/*     */     
/* 273 */     HTransformTranslateNode localHTransformTranslateNode11 = new HTransformTranslateNode(
/* 274 */       this._tree, 0.0F, 0.0F, this._transformController);
/*     */     
/*     */ 
/* 277 */     HShapeNode localHShapeNode1 = createShape(HMatrixOps.getRect(100.0F, 150.0F), 4);
/* 278 */     localHShapeNode1.setPosition(this._xo + 2 * this._xLevel, this._yo + 3 * this._yLevel);
/* 279 */     localHShapeNode1.setDescriptor("Trunk");
/*     */     
/* 281 */     HShapeNode localHShapeNode2 = createShape(HMatrixOps.getTriangle(25.0F, 10.0F), 3);
/* 282 */     localHShapeNode2.setPosition(this._xo + 2 * this._xLevel, this._yo + 7 * this._yLevel);
/* 283 */     localHShapeNode2.setDescriptor("Head");
/*     */     
/* 285 */     HShapeNode localHShapeNode3 = createShape(HMatrixOps.getRect(20.0F, 25.0F), 4);
/* 286 */     localHShapeNode3.setPosition(this._xo + 4 * this._xLevel, this._yo + 7 * this._yLevel);
/* 287 */     localHShapeNode3.setDescriptor("Neck");
/*     */     
/* 289 */     HShapeNode localHShapeNode4 = createShape(HMatrixOps.getRect(25.0F, 50.0F), 4);
/* 290 */     localHShapeNode4.setPosition(this._xo + 5 * this._xLevel, this._yo + 7 * this._yLevel);
/* 291 */     localHShapeNode4.setDescriptor("leg");
/*     */     
/* 293 */     HShapeNode localHShapeNode5 = createShape(HMatrixOps.getRect(40.0F, 10.0F), 4);
/* 294 */     localHShapeNode5.setPosition(this._xo + 6 * this._xLevel, this._yo + 7 * this._yLevel);
/* 295 */     localHShapeNode5.setDescriptor("Foot");
/*     */     
/* 297 */     HShapeNode localHShapeNode6 = createShape(HMatrixOps.getRect(100.0F, 15.0F), 4);
/* 298 */     localHShapeNode6.setPosition(this._xo + 0 * this._xLevel, this._yo + 6 * this._yLevel);
/* 299 */     localHShapeNode6.setDescriptor("arm");
/*     */     
/*     */ 
/* 302 */     link(this._tree.getRoot(), localHTransformTranslateNode1);
/*     */     
/*     */ 
/* 305 */     link(localHTransformTranslateNode1, localHGroupNode1);
/* 306 */     localHTransformTranslateNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + this._yLevel);
/*     */     
/* 308 */     link(localHGroupNode1, localHShapeNode1);
/* 309 */     localHGroupNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + 2 * this._yLevel);
/*     */     
/* 311 */     link(localHGroupNode1, localHTransformTranslateNode2);
/*     */     
/* 313 */     link(localHGroupNode1, localHTransformTranslateNode5);
/*     */     
/* 315 */     link(localHGroupNode1, localHTransformTranslateNode6);
/*     */     
/* 317 */     link(localHTransformTranslateNode2, localHTransformRotateNode1);
/* 318 */     localHTransformTranslateNode2.setPosition(this._xo + 3 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 320 */     link(localHTransformRotateNode1, localHGroupNode2);
/* 321 */     localHTransformRotateNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 323 */     link(localHGroupNode2, localHTransformTranslateNode3);
/* 324 */     localHGroupNode2.setPosition(this._xo + 3 * this._xLevel, this._yo + 5 * this._yLevel);
/*     */     
/* 326 */     link(localHGroupNode2, localHTransformTranslateNode4);
/*     */     
/* 328 */     link(localHTransformTranslateNode3, localHShapeNode2);
/* 329 */     localHTransformTranslateNode3.setPosition(this._xo + 2 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 331 */     link(localHTransformTranslateNode4, localHShapeNode3);
/* 332 */     localHTransformTranslateNode4.setPosition(this._xo + 4 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 334 */     link(localHTransformTranslateNode5, localHTransformScaleNode);
/* 335 */     localHTransformTranslateNode5.setPosition(this._xo + 4 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 337 */     link(localHTransformTranslateNode6, localHTransformScaleNode);
/* 338 */     localHTransformTranslateNode6.setPosition(this._xo + 5 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 340 */     link(localHTransformScaleNode, localHGroupNode3);
/* 341 */     localHTransformScaleNode.setPosition(this._xo + 5.5F * this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 343 */     link(localHGroupNode3, localHTransformTranslateNode7);
/* 344 */     localHGroupNode3.setPosition((float)(this._xo + 5.5D * this._xLevel), this._yo + 5 * this._yLevel);
/*     */     
/* 346 */     link(localHGroupNode3, localHTransformTranslateNode8);
/*     */     
/* 348 */     link(localHTransformTranslateNode7, localHShapeNode4);
/* 349 */     localHTransformTranslateNode7.setPosition(this._xo + 5 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 351 */     link(localHTransformTranslateNode8, localHShapeNode5);
/* 352 */     localHTransformTranslateNode8.setPosition(this._xo + 6 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 354 */     link(localHGroupNode1, localHTransformTranslateNode9);
/* 355 */     localHTransformTranslateNode9.setPosition(this._xo + 0 * this._xLevel, this._yo + 2 * this._yLevel);
/*     */     
/* 357 */     link(localHTransformTranslateNode9, localHTransformRotateNode2);
/* 358 */     localHTransformRotateNode2.setPosition(this._xo + 0 * this._xLevel, this._yo + 3.0F * this._yLevel);
/*     */     
/* 360 */     link(localHGroupNode1, localHTransformTranslateNode10);
/* 361 */     localHTransformTranslateNode10.setPosition(this._xo + this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 363 */     link(localHTransformTranslateNode10, localHTransformRotateNode3);
/* 364 */     localHTransformRotateNode3.setPosition(this._xo + this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 366 */     link(localHTransformRotateNode2, localHTransformTranslateNode11);
/* 367 */     localHTransformTranslateNode11.setPosition(this._xo + 0 * this._xLevel, this._yo + 5 * this._yLevel);
/*     */     
/* 369 */     link(localHTransformRotateNode3, localHTransformTranslateNode11);
/*     */     
/* 371 */     link(localHTransformTranslateNode11, localHShapeNode6);
/*     */   }
/*     */   
/*     */   public HToolPalette getPalette() {
/* 375 */     return this._palette;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void link(HNode paramHNode1, HNode paramHNode2)
/*     */   {
/* 382 */     paramHNode1.addChild(paramHNode2);
/* 383 */     paramHNode2.addParent(paramHNode1);
/*     */   }
/*     */   
/*     */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
/* 387 */     if (paramActionEvent.getSource() == this._animate) {
/* 388 */       if ((this._animationThread != null) && 
/* 389 */         (this._animationThread.isAlive())) {
/* 390 */         this._animationThread.interrupt();
/* 391 */         this._animationThread.stop();
/*     */       }
/* 393 */       this._pause.setEnabled(true);
/* 394 */       this._animationThread = new HTraversalThread(this._tree);
/* 395 */       this._animationThread.start();
/* 396 */       this._pause.setText("Pause");
/* 397 */       this._paused = false;
/*     */     }
/*     */     
/* 400 */     if (paramActionEvent.getSource() == this._pause) {
/* 401 */       if (this._paused) {
/* 402 */         if (this._animationThread != null)
/* 403 */           this._animationThread.resume();
/* 404 */         this._pause.setText("Pause");
/* 405 */         this._paused = false;
/*     */       } else {
/* 407 */         if (this._animationThread != null)
/* 408 */           this._animationThread.suspend();
/* 409 */         this._pause.setText("Resume");
/* 410 */         this._paused = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void stopAnimation() {
/* 416 */     if ((this._animationThread != null) && 
/* 417 */       (this._animationThread.isAlive())) {
/* 418 */       this._animationThread.interrupt();
/* 419 */       this._animationThread.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePause() {
/* 424 */     this._pause.setEnabled(false);
/* 425 */     this._pause.setText("Pause");
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 429 */     stopAnimation();
/*     */   }
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentResized(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HApplet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */