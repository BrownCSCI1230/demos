/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class HApplet
/*     */   extends JApplet
/*     */ {
/*     */   private HTree _tree;
/*     */   private HRobotContainer _robotContainer;
/*     */   private HControls _transformController;
/*     */   private HMatrixDisplay _display;
/*     */   private int _xo;
/*     */   private int _yo;
/*     */   private int _xLevel;
/*     */   private int _yLevel;
/*     */   private JSplitPane _entirePane;
/*     */   private JSplitPane _rightPane;
/*     */   private JPanel _left;
/*     */   private JPanel _right;
/*     */   private JPanel _top;
/*     */   private JPanel _bottom;
/*     */   
/*     */   public void init()
/*     */   {
/*  34 */     GridBagLayout localGridBagLayout = new GridBagLayout();
/*  35 */     GridBagConstraints localGridBagConstraints = new GridBagConstraints();
/*     */     
/*  37 */     this._entirePane = new JSplitPane(1);
/*  38 */     this._rightPane = new JSplitPane(0);
/*     */     
/*  40 */     this._left = new JPanel();
/*  41 */     this._left.setLayout(localGridBagLayout);
/*     */     
/*  43 */     this._right = new JPanel();
/*  44 */     this._right.setLayout(localGridBagLayout);
/*     */     
/*  46 */     this._top = new JPanel();
/*  47 */     this._top.setLayout(localGridBagLayout);
/*     */     
/*  49 */     this._bottom = new JPanel();
/*  50 */     this._bottom.setLayout(localGridBagLayout);
/*     */     
/*     */ 
/*  53 */     this._xo = 50;
/*  54 */     this._yo = 30;
/*  55 */     this._xLevel = 65;
/*  56 */     this._yLevel = 50;
/*     */     
/*     */ 
/*  59 */     this._tree = new HTree();
/*  60 */     this._robotContainer = new HRobotContainer(this._tree);
/*  61 */     this._tree.setRobotContainer(this._robotContainer);
/*  62 */     this._transformController = new HControls(this._robotContainer);
/*  63 */     this._tree.createRoot(this._transformController);
/*  64 */     this._display = new HMatrixDisplay();
/*  65 */     this._tree.setDisplay(this._display);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  70 */     getContentPane().setLayout(localGridBagLayout);
/*  71 */     addItem(this._bottom, this._display, localGridBagLayout, localGridBagConstraints, 3, 3, 5, 2, 1, 0.7D, 0.3D);
/*  72 */     addItem(this._left, this._robotContainer, localGridBagLayout, localGridBagConstraints, 0, 0, 3, 3, 1, 0.3D, 0.7D);
/*  73 */     addItem(this._top, this._tree, localGridBagLayout, localGridBagConstraints, 3, 0, 5, 3, 1, 0.7D, 0.7D);
/*  74 */     addItem(this._left, this._transformController, localGridBagLayout, localGridBagConstraints, 0, 3, 3, 2, 1, 0.3D, 0.3D);
/*     */     
/*  76 */     this._left.setMinimumSize(new Dimension(200, 0));
/*  77 */     this._left.setPreferredSize(new Dimension(200, 0));
/*  78 */     this._top.setMinimumSize(new Dimension(0, 250));
/*  79 */     this._top.setPreferredSize(new Dimension(0, 450));
/*     */     
/*  81 */     this._right.setLayout(new GridLayout(1, 1));
/*  82 */     this._right.add(this._rightPane);
/*     */     
/*  84 */     getContentPane().setLayout(new GridLayout(1, 1));
/*  85 */     getContentPane().add(this._entirePane);
/*     */     
/*  87 */     this._entirePane.setLeftComponent(this._left);
/*  88 */     this._entirePane.setRightComponent(this._right);
/*  89 */     this._entirePane.setDividerSize(15);
/*  90 */     this._entirePane.setOneTouchExpandable(true);
/*     */     
/*  92 */     this._rightPane.setTopComponent(this._top);
/*  93 */     this._rightPane.setBottomComponent(this._bottom);
/*  94 */     this._rightPane.setDividerSize(15);
/*  95 */     this._rightPane.setOneTouchExpandable(true);
/*     */     
/*  97 */     getContentPane().validate();
/*     */     
/*     */ 
/* 100 */     createRobot();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItem(Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 110 */     paramGridBagConstraints.gridx = paramInt1;
/* 111 */     paramGridBagConstraints.gridy = paramInt2;
/* 112 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 113 */     paramGridBagConstraints.gridheight = paramInt4;
/* 114 */     paramGridBagConstraints.weightx = paramDouble1;
/* 115 */     paramGridBagConstraints.weighty = paramDouble2;
/* 116 */     paramGridBagConstraints.fill = paramInt5;
/* 117 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 118 */     getContentPane().add(paramComponent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItem(Container paramContainer, Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 128 */     paramGridBagConstraints.gridx = paramInt1;
/* 129 */     paramGridBagConstraints.gridy = paramInt2;
/* 130 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 131 */     paramGridBagConstraints.gridheight = paramInt4;
/* 132 */     paramGridBagConstraints.weightx = paramDouble1;
/* 133 */     paramGridBagConstraints.weighty = paramDouble2;
/* 134 */     paramGridBagConstraints.fill = paramInt5;
/* 135 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 136 */     paramContainer.add(paramComponent);
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     try {
/* 141 */       UIManager.setLookAndFeel(
/* 142 */         UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {
/* 145 */       System.out.println(
/* 146 */         "Couldn't use the cross-platform look and feel: " + localException);
/*     */     }
/*     */     
/* 149 */     HApplet localHApplet = new HApplet();
/*     */     
/*     */ 
/* 152 */     localHApplet.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HShapeNode createShape(float[][] paramArrayOfFloat, int paramInt)
/*     */   {
/* 159 */     HRobotPart localHRobotPart = new HRobotPart(paramArrayOfFloat, paramInt);
/* 160 */     HShapeNode localHShapeNode = new HShapeNode(localHRobotPart, this._tree, this._transformController);
/* 161 */     localHShapeNode.addDrawListener(this._robotContainer);
/* 162 */     this._robotContainer.addPart(localHRobotPart);
/*     */     
/* 164 */     return localHShapeNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createRobot()
/*     */   {
/* 172 */     HGroupNode localHGroupNode1 = new HGroupNode(this._tree, this._transformController);
/* 173 */     localHGroupNode1.setDescriptor("Robot");
/*     */     
/* 175 */     HGroupNode localHGroupNode2 = new HGroupNode(this._tree, this._transformController);
/* 176 */     localHGroupNode2.setDescriptor("Head and Neck");
/*     */     
/* 178 */     HGroupNode localHGroupNode3 = new HGroupNode(this._tree, this._transformController);
/* 179 */     localHGroupNode3.setDescriptor("Leg and Foot");
/*     */     
/*     */ 
/* 182 */     HTransformTranslateNode localHTransformTranslateNode1 = new HTransformTranslateNode(this._tree, 0.0F, 0.0F, 
/* 183 */       this._transformController);
/*     */     
/* 185 */     HTransformTranslateNode localHTransformTranslateNode2 = new HTransformTranslateNode(this._tree, 0.0F, -100.0F, 
/* 186 */       this._transformController);
/*     */     
/* 188 */     HTransformRotateNode localHTransformRotateNode1 = new HTransformRotateNode(
/* 189 */       this._tree, 0.0F, this._transformController);
/*     */     
/* 191 */     HTransformTranslateNode localHTransformTranslateNode3 = new HTransformTranslateNode(this._tree, 0.0F, -25.0F, 
/* 192 */       this._transformController);
/*     */     
/* 194 */     HTransformTranslateNode localHTransformTranslateNode4 = new HTransformTranslateNode(this._tree, 0.0F, 13.0F, 
/* 195 */       this._transformController);
/*     */     
/* 197 */     HTransformTranslateNode localHTransformTranslateNode5 = new HTransformTranslateNode(this._tree, -37.0F, 125.0F, 
/* 198 */       this._transformController);
/* 199 */     HTransformTranslateNode localHTransformTranslateNode6 = new HTransformTranslateNode(this._tree, 37.0F, 125.0F, 
/* 200 */       this._transformController);
/*     */     
/* 202 */     HTransformTranslateNode localHTransformTranslateNode7 = new HTransformTranslateNode(this._tree, 0.0F, -25.0F, 
/* 203 */       this._transformController);
/*     */     
/* 205 */     HTransformTranslateNode localHTransformTranslateNode8 = new HTransformTranslateNode(this._tree, -7.0F, 5.0F, 
/* 206 */       this._transformController);
/*     */     
/* 208 */     HTransformScaleNode localHTransformScaleNode = new HTransformScaleNode(this._tree, 1.0F, 
/* 209 */       this._transformController);
/*     */     
/* 211 */     HTransformRotateNode localHTransformRotateNode2 = new HTransformRotateNode(this._tree, 
/* 212 */       1.0471976F, 
/* 213 */       this._transformController);
/*     */     
/* 215 */     HTransformTranslateNode localHTransformTranslateNode9 = new HTransformTranslateNode(
/* 216 */       this._tree, -82.0F, -33.0F, this._transformController);
/*     */     
/* 218 */     HTransformTranslateNode localHTransformTranslateNode10 = new HTransformTranslateNode(
/* 219 */       this._tree, 82.0F, -33.0F, this._transformController);
/*     */     
/* 221 */     HTransformRotateNode localHTransformRotateNode3 = new HTransformRotateNode(
/* 222 */       this._tree, 2.0943952F, this._transformController);
/*     */     
/* 224 */     HTransformTranslateNode localHTransformTranslateNode11 = new HTransformTranslateNode(
/* 225 */       this._tree, 0.0F, 0.0F, this._transformController);
/*     */     
/*     */ 
/* 228 */     HShapeNode localHShapeNode1 = createShape(HMatrixOps.getRect(100.0F, 150.0F), 4);
/* 229 */     localHShapeNode1.setPosition(this._xo + 2 * this._xLevel, this._yo + 3 * this._yLevel);
/* 230 */     localHShapeNode1.setDescriptor("Trunk");
/*     */     
/* 232 */     HShapeNode localHShapeNode2 = createShape(HMatrixOps.getRect(50.0F, 50.0F), 4);
/* 233 */     localHShapeNode2.setPosition(this._xo + 2 * this._xLevel, this._yo + 7 * this._yLevel);
/* 234 */     localHShapeNode2.setDescriptor("Head");
/*     */     
/* 236 */     HShapeNode localHShapeNode3 = createShape(HMatrixOps.getRect(20.0F, 25.0F), 4);
/* 237 */     localHShapeNode3.setPosition(this._xo + 4 * this._xLevel, this._yo + 7 * this._yLevel);
/* 238 */     localHShapeNode3.setDescriptor("Neck");
/*     */     
/* 240 */     HShapeNode localHShapeNode4 = createShape(HMatrixOps.getRect(25.0F, 50.0F), 4);
/* 241 */     localHShapeNode4.setPosition(this._xo + 5 * this._xLevel, this._yo + 7 * this._yLevel);
/* 242 */     localHShapeNode4.setDescriptor("leg");
/*     */     
/* 244 */     HShapeNode localHShapeNode5 = createShape(HMatrixOps.getRect(40.0F, 10.0F), 4);
/* 245 */     localHShapeNode5.setPosition(this._xo + 6 * this._xLevel, this._yo + 7 * this._yLevel);
/* 246 */     localHShapeNode5.setDescriptor("Foot");
/*     */     
/* 248 */     HShapeNode localHShapeNode6 = createShape(HMatrixOps.getRect(100.0F, 15.0F), 4);
/* 249 */     localHShapeNode6.setPosition(this._xo + 0 * this._xLevel, this._yo + 6 * this._yLevel);
/* 250 */     localHShapeNode6.setDescriptor("arm");
/*     */     
/*     */ 
/* 253 */     link(this._tree.getRoot(), localHTransformTranslateNode1);
/* 254 */     this._tree.getRoot().setPosition(this._xo + 3 * this._xLevel, this._yo);
/*     */     
/*     */ 
/* 257 */     link(localHTransformTranslateNode1, localHGroupNode1);
/* 258 */     localHTransformTranslateNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + this._yLevel);
/*     */     
/* 260 */     link(localHGroupNode1, localHShapeNode1);
/* 261 */     localHGroupNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + 2 * this._yLevel);
/*     */     
/* 263 */     link(localHGroupNode1, localHTransformTranslateNode2);
/*     */     
/* 265 */     link(localHGroupNode1, localHTransformTranslateNode5);
/*     */     
/* 267 */     link(localHGroupNode1, localHTransformTranslateNode6);
/*     */     
/* 269 */     link(localHTransformTranslateNode2, localHTransformRotateNode1);
/* 270 */     localHTransformTranslateNode2.setPosition(this._xo + 3 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 272 */     link(localHTransformRotateNode1, localHGroupNode2);
/* 273 */     localHTransformRotateNode1.setPosition(this._xo + 3 * this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 275 */     link(localHGroupNode2, localHTransformTranslateNode3);
/* 276 */     localHGroupNode2.setPosition(this._xo + 3 * this._xLevel, this._yo + 5 * this._yLevel);
/*     */     
/* 278 */     link(localHGroupNode2, localHTransformTranslateNode4);
/*     */     
/* 280 */     link(localHTransformTranslateNode3, localHShapeNode2);
/* 281 */     localHTransformTranslateNode3.setPosition(this._xo + 2 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 283 */     link(localHTransformTranslateNode4, localHShapeNode3);
/* 284 */     localHTransformTranslateNode4.setPosition(this._xo + 4 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 286 */     link(localHTransformTranslateNode5, localHTransformScaleNode);
/* 287 */     localHTransformTranslateNode5.setPosition(this._xo + 4 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 289 */     link(localHTransformTranslateNode6, localHTransformScaleNode);
/* 290 */     localHTransformTranslateNode6.setPosition(this._xo + 5 * this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 292 */     link(localHTransformScaleNode, localHGroupNode3);
/* 293 */     localHTransformScaleNode.setPosition(this._xo + 5.5F * this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 295 */     link(localHGroupNode3, localHTransformTranslateNode7);
/* 296 */     localHGroupNode3.setPosition((float)(this._xo + 5.5D * this._xLevel), this._yo + 5 * this._yLevel);
/*     */     
/* 298 */     link(localHGroupNode3, localHTransformTranslateNode8);
/*     */     
/* 300 */     link(localHTransformTranslateNode7, localHShapeNode4);
/* 301 */     localHTransformTranslateNode7.setPosition(this._xo + 5 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 303 */     link(localHTransformTranslateNode8, localHShapeNode5);
/* 304 */     localHTransformTranslateNode8.setPosition(this._xo + 6 * this._xLevel, this._yo + 6 * this._yLevel);
/*     */     
/* 306 */     link(localHGroupNode1, localHTransformTranslateNode9);
/* 307 */     localHTransformTranslateNode9.setPosition(this._xo + 0 * this._xLevel, this._yo + 2 * this._yLevel);
/*     */     
/* 309 */     link(localHTransformTranslateNode9, localHTransformRotateNode2);
/* 310 */     localHTransformRotateNode2.setPosition(this._xo + 0 * this._xLevel, this._yo + 3.0F * this._yLevel);
/*     */     
/* 312 */     link(localHGroupNode1, localHTransformTranslateNode10);
/* 313 */     localHTransformTranslateNode10.setPosition(this._xo + this._xLevel, this._yo + 3 * this._yLevel);
/*     */     
/* 315 */     link(localHTransformTranslateNode10, localHTransformRotateNode3);
/* 316 */     localHTransformRotateNode3.setPosition(this._xo + this._xLevel, this._yo + 4 * this._yLevel);
/*     */     
/* 318 */     link(localHTransformRotateNode2, localHTransformTranslateNode11);
/* 319 */     localHTransformTranslateNode11.setPosition(this._xo + 0 * this._xLevel, this._yo + 5 * this._yLevel);
/*     */     
/* 321 */     link(localHTransformRotateNode3, localHTransformTranslateNode11);
/*     */     
/* 323 */     link(localHTransformTranslateNode11, localHShapeNode6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void link(HNode paramHNode1, HNode paramHNode2)
/*     */   {
/* 330 */     paramHNode1.addChild(paramHNode2);
/* 331 */     paramHNode2.addParent(paramHNode1);
/*     */   }
/*     */   
/*     */   public void disablePause() {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HApplet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */