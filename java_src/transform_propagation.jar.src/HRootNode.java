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
/*     */ 
/*     */ public class HRootNode
/*     */   extends HNode
/*     */ {
/*     */   private HNode _child;
/*  21 */   public static final Color BACKGROUND = new Color(171, 130, 255);
/*     */   
/*     */   public HRootNode(HTree paramHTree, HChooser paramHChooser) {
/*  24 */     super(paramHTree, paramHChooser);
/*     */   }
/*     */   
/*     */   public void render(float[][] paramArrayOfFloat, boolean paramBoolean, HPosition paramHPosition)
/*     */   {
/*  29 */     if (this._child != null)
/*  30 */       this._child.render(paramArrayOfFloat, paramBoolean, paramHPosition);
/*     */   }
/*     */   
/*     */   public void drawTree() {
/*  34 */     if (this._child != null)
/*  35 */       this._child.drawTree();
/*  36 */     fireDrawNodeEvent();
/*     */   }
/*     */   
/*     */   public void drawLabels() {
/*  40 */     if (this._child != null)
/*  41 */       this._child.drawLabels();
/*     */   }
/*     */   
/*     */   public void setSelects(boolean paramBoolean1, boolean paramBoolean2) {
/*  45 */     boolean bool1 = false;
/*  46 */     if ((paramBoolean1) || (mousePressed())) {
/*  47 */       bool1 = true;
/*     */     }
/*  49 */     boolean bool2 = false;
/*  50 */     if ((paramBoolean2) || (mousePressed())) {
/*  51 */       bool2 = true;
/*     */     }
/*  53 */     if (this._child != null)
/*  54 */       this._child.setSelects(bool1, bool2);
/*     */   }
/*     */   
/*     */   public void drawLines() {
/*  58 */     if (this._child != null)
/*  59 */       this._child.drawLines();
/*     */   }
/*     */   
/*     */   public void animate(Thread paramThread, HNode paramHNode, float[][] paramArrayOfFloat) {
/*  63 */     animateAction(paramThread, paramHNode);
/*  64 */     if (this._child != null)
/*  65 */       this._child.animate(paramThread, this, paramArrayOfFloat);
/*  66 */     finishAnimateAction(paramThread);
/*     */   }
/*     */   
/*     */   public void getMatrix(Stack paramStack, boolean paramBoolean, float[][] paramArrayOfFloat, int paramInt, String paramString)
/*     */   {
/*  71 */     boolean bool = false;
/*  72 */     if ((paramBoolean) || (mousePressed()))
/*  73 */       bool = true;
/*  74 */     if (this._child != null)
/*  75 */       this._child.getMatrix(paramStack, bool, paramArrayOfFloat, paramInt, paramString.concat("M"));
/*     */   }
/*     */   
/*     */   public void addChild(HNode paramHNode) {
/*  79 */     this._child = paramHNode;
/*     */   }
/*     */   
/*     */   public boolean removeChild(HNode paramHNode) {
/*  83 */     if (this._child == paramHNode) {
/*  84 */       this._child = null;
/*  85 */       return true;
/*     */     }
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public String getString() {
/*  91 */     return "R";
/*     */   }
/*     */   
/*     */   public Color getColor() {
/*  95 */     return BACKGROUND;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  99 */     return 4;
/*     */   }
/*     */   
/*     */   public boolean canAddChild(HNode paramHNode) {
/* 103 */     if (this._child == null)
/* 104 */       return true;
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild() {
/* 109 */     if (this._child != null)
/* 110 */       return true;
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild(int paramInt) {
/* 115 */     if ((paramInt == 0) && (this._child != null))
/* 116 */       return true;
/* 117 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canBeDeleted() {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public HNode getChild(int paramInt) {
/* 125 */     if (paramInt == 0)
/* 126 */       return this._child;
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public Vector getChildren() {
/* 131 */     Vector localVector = new Vector();
/* 132 */     if (this._child != null)
/* 133 */       localVector.addElement(this._child);
/* 134 */     return localVector;
/*     */   }
/*     */   
/*     */   public int getIndex(HNode paramHNode) {
/* 138 */     if (this._child == paramHNode)
/* 139 */       return 0;
/* 140 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HRootNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */