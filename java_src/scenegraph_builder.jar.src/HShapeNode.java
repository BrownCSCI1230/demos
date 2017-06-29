/*     */ import java.awt.Color;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HShapeNode
/*     */   extends HNode
/*     */ {
/*     */   private HRobotPart _part;
/*     */   private Vector _drawListeners;
/*     */   private Vector _matrixChangeListeners;
/*     */   private HTree _tree;
/*     */   private float[][] _transform;
/*     */   private boolean _animating;
/*  25 */   public static final Color BACKGROUND = new Color(95, 148, 247);
/*     */   
/*     */   public HShapeNode(HRobotPart paramHRobotPart, HTree paramHTree, HChooser paramHChooser) {
/*  28 */     super(paramHTree, paramHChooser);
/*  29 */     this._part = paramHRobotPart;
/*  30 */     this._drawListeners = new Vector();
/*  31 */     this._matrixChangeListeners = new Vector();
/*  32 */     this._tree = paramHTree;
/*  33 */     this._animating = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void render(float[][] paramArrayOfFloat, boolean paramBoolean, HPosition paramHPosition)
/*     */   {
/*  40 */     this._transform = paramArrayOfFloat;
/*  41 */     if (this._part != null) {
/*  42 */       this._part.draw(paramArrayOfFloat);
/*  43 */       for (int i = 0; i < this._drawListeners.size(); i++)
/*     */       {
/*  45 */         ((HDrawListener)this._drawListeners.elementAt(i)).draw(new HDrawEvent(this._part, paramBoolean, paramHPosition)); }
/*     */     }
/*  47 */     fireMatrixChangeEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawTree()
/*     */   {
/*  54 */     fireDrawNodeEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAnimating(boolean paramBoolean)
/*     */   {
/*  61 */     this._animating = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setSelects(boolean paramBoolean1, boolean paramBoolean2) {
/*  65 */     if (!this._animating) {
/*  66 */       boolean bool1 = false;
/*  67 */       if ((paramBoolean1) || (isSelected())) {
/*  68 */         bool1 = true;
/*     */       }
/*  70 */       boolean bool2 = false;
/*  71 */       if ((paramBoolean2) || (mousePressed()))
/*  72 */         bool2 = true;
/*  73 */       this._part.setSelected(bool2);
/*  74 */       setSelected(bool1);
/*     */     } else {
/*  76 */       this._part.setSelected(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void animate(Thread paramThread, HNode paramHNode, float[][] paramArrayOfFloat)
/*     */   {
/*  82 */     if (this._part != null) {
/*  83 */       this._part.draw(paramArrayOfFloat);
/*  84 */       for (int i = 0; i < this._drawListeners.size(); i++)
/*     */       {
/*  86 */         ((HDrawListener)this._drawListeners.elementAt(i)).drawImmediately(new HDrawEvent(this._part, false, new HPosition(0.0F, 0.0F))); }
/*     */     }
/*  88 */     animateAction(paramThread, paramHNode);
/*  89 */     finishAnimateAction(paramThread);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void getMatrix(Stack paramStack, boolean paramBoolean, float[][] paramArrayOfFloat, int paramInt, String paramString)
/*     */   {
/*  97 */     int i = 0;
/*  98 */     if ((paramBoolean) || (mousePressed())) {
/*  99 */       i = 1;
/*     */     }
/* 101 */     if (i != 0) {
/* 102 */       Vector localVector = new Vector();
/* 103 */       for (int j = 0; j < paramStack.size(); j++)
/* 104 */         localVector.addElement(paramStack.elementAt(j));
/* 105 */       localVector.insertElementAt(new HMatrixObject(paramArrayOfFloat, false, 
/* 106 */         getDescriptor(), new String(""), this), 0);
/* 107 */       fireDrawMatrixEvent(new HDrawMatrixEvent(localVector));
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawLines()
/*     */   {
/* 113 */     fireDrawLineEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeChild(HNode paramHNode)
/*     */   {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public void addMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {
/* 125 */     this._matrixChangeListeners.addElement(paramHMatrixChangeListener);
/*     */   }
/*     */   
/*     */   public void removeMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {
/* 129 */     this._matrixChangeListeners.removeElement(paramHMatrixChangeListener);
/*     */   }
/*     */   
/*     */   public void fireMatrixChangeEvent() {
/* 133 */     for (int i = 0; i < this._matrixChangeListeners.size(); i++)
/*     */     {
/* 135 */       ((HMatrixChangeListener)this._matrixChangeListeners.elementAt(i)).matrixChanged(new HMatrixObject(this._transform, mousePressed(), new String("hello"), 
/* 136 */         new String(""), this));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addDrawListener(HDrawListener paramHDrawListener)
/*     */   {
/* 144 */     this._drawListeners.addElement(paramHDrawListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeDrawListener(HDrawListener paramHDrawListener)
/*     */   {
/* 151 */     this._drawListeners.removeElement(paramHDrawListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getString()
/*     */   {
/* 158 */     return "S";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 165 */     return BACKGROUND;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 169 */     return 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canAddChild(HNode paramHNode)
/*     */   {
/* 176 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HRobotPart getPart()
/*     */   {
/* 183 */     return this._part;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasChild()
/*     */   {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild(int paramInt) {
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public HNode getChild(int paramInt) {
/* 198 */     return null;
/*     */   }
/*     */   
/*     */   public int getChild(HNode paramHNode) {
/* 202 */     return -1;
/*     */   }
/*     */   
/*     */   public Vector getChildren() {
/* 206 */     Vector localVector = new Vector();
/* 207 */     return localVector;
/*     */   }
/*     */   
/*     */   public void addChild(HNode paramHNode) {}
/*     */   
/*     */   public void drawLabels() {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HShapeNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */