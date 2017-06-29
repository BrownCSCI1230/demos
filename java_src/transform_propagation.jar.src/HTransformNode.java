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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HTransformNode
/*     */   extends HNode
/*     */ {
/*  25 */   public static final Color BACKGROUND = new Color(205, 100, 100);
/*     */   
/*     */ 
/*  28 */   public HTransformNode(HTree paramHTree, HChooser paramHChooser) { super(paramHTree, paramHChooser); }
/*  29 */   private Vector _labels = new Vector();
/*  30 */   private Vector _matrixChangeListeners = new Vector();
/*  31 */   private int _size = 3;
/*     */   
/*     */   private float[][] _myTrans;
/*     */   private HNode _child;
/*     */   
/*     */   public void setTransform(float[][] paramArrayOfFloat)
/*     */   {
/*  38 */     this._myTrans = paramArrayOfFloat;
/*  39 */     fireMatrixChangeEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void render(float[][] paramArrayOfFloat, boolean paramBoolean, HPosition paramHPosition)
/*     */   {
/*  46 */     if (paramArrayOfFloat == null)
/*  47 */       paramArrayOfFloat = HMatrixOps.getIdentity(3);
/*  48 */     if (this._myTrans == null) {
/*  49 */       this._myTrans = HMatrixOps.getIdentity(3);
/*     */     }
/*  51 */     boolean bool = paramBoolean;
/*     */     
/*  53 */     if ((scaleActive()) || (rotateActive())) {
/*  54 */       bool = false;
/*  55 */       if ((isSelected()) || (paramBoolean)) {
/*  56 */         bool = true;
/*  57 */         if (!paramBoolean) {
/*  58 */           paramHPosition = new HPosition(paramArrayOfFloat[2][0], paramArrayOfFloat[2][1]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  63 */     float[][] arrayOfFloat = HMatrixOps.multiply(this._myTrans, paramArrayOfFloat, this._size, this._size);
/*     */     
/*  65 */     if (this._child != null) {
/*  66 */       this._child.render(arrayOfFloat, bool, paramHPosition);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void drawTree()
/*     */   {
/*  73 */     if (this._child != null)
/*  74 */       this._child.drawTree();
/*  75 */     fireDrawNodeEvent();
/*     */   }
/*     */   
/*     */   public void setSelects(boolean paramBoolean1, boolean paramBoolean2) {
/*  79 */     boolean bool1 = false;
/*  80 */     if ((paramBoolean1) || (mousePressed())) {
/*  81 */       bool1 = true;
/*     */     }
/*  83 */     boolean bool2 = false;
/*  84 */     if ((paramBoolean2) || (mousePressed())) {
/*  85 */       bool2 = true;
/*     */     }
/*  87 */     if (this._child != null) {
/*  88 */       this._child.setSelects(bool1, bool2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getMatrix(Stack paramStack, boolean paramBoolean, float[][] paramArrayOfFloat, int paramInt, String paramString)
/*     */   {
/*  96 */     boolean bool = false;
/*  97 */     if ((paramBoolean) || (mousePressed())) {
/*  98 */       bool = true;
/*     */     }
/* 100 */     if (paramArrayOfFloat == null)
/* 101 */       paramArrayOfFloat = HMatrixOps.getIdentity(3);
/* 102 */     if (this._myTrans == null) {
/* 103 */       this._myTrans = HMatrixOps.getIdentity(3);
/*     */     }
/* 105 */     paramString = paramString.concat("." + Integer.toString(paramInt));
/*     */     
/* 107 */     this._labels.addElement(paramString);
/*     */     
/* 109 */     float[][] arrayOfFloat = HMatrixOps.multiply(this._myTrans, paramArrayOfFloat, this._size, this._size);
/*     */     
/* 111 */     paramStack.push(new HMatrixObject(this._myTrans, mousePressed(), paramString, 
/* 112 */       getTransformValue(), this));
/*     */     
/* 114 */     if (this._child != null) {
/* 115 */       this._child.getMatrix(paramStack, bool, arrayOfFloat, 1, paramString);
/*     */     }
/* 117 */     setDescriptor(new String(""));
/* 118 */     paramStack.pop();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void animate(Thread paramThread, HNode paramHNode, float[][] paramArrayOfFloat)
/*     */   {
/* 125 */     animateAction(paramThread, paramHNode);
/* 126 */     firePushMatrixEvent(new HMatrixObject(this._myTrans, mousePressed(), 
/* 127 */       getFinalLabel(), 
/* 128 */       getTransformValue(), this));
/*     */     
/* 130 */     float[][] arrayOfFloat = HMatrixOps.multiply(this._myTrans, paramArrayOfFloat, this._size, this._size);
/* 131 */     if (this._child != null)
/* 132 */       this._child.animate(paramThread, this, arrayOfFloat);
/* 133 */     firePopMatrixEvent();
/* 134 */     finishAnimateAction(paramThread);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearLabels()
/*     */   {
/* 142 */     this._labels.removeAllElements();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reverseTraverse()
/*     */   {
/* 149 */     setDescriptor(getFinalLabel());
/* 150 */     super.reverseTraverse();
/*     */   }
/*     */   
/*     */   public void drawLabels() {
/* 154 */     setDescriptor(getFinalLabel());
/* 155 */     if (this._child != null) {
/* 156 */       this._child.drawLabels();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getConcatLabel()
/*     */   {
/* 163 */     String str = new String("");
/* 164 */     for (int i = 0; i < this._labels.size(); i++) {
/* 165 */       str = str.concat((String)this._labels.elementAt(i) + ",");
/*     */     }
/* 167 */     if (this._labels.size() > 0)
/* 168 */       str = str.substring(0, str.length() - 1);
/* 169 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFinalLabel()
/*     */   {
/* 177 */     if (this._labels.size() == 0)
/* 178 */       return new String(" ");
/* 179 */     if (this._labels.size() == 1) {
/* 180 */       return (String)this._labels.elementAt(0);
/*     */     }
/* 182 */     Vector localVector = new Vector();
/*     */     
/*     */ 
/* 185 */     String str2 = new Character(((String)this._labels.elementAt(0))
/* 186 */       .charAt(((String)this._labels
/* 187 */       .elementAt(0))
/* 188 */       .length() - 1)).toString();
/*     */     
/* 190 */     for (int i = 0; i < this._labels.size(); i++) {
/* 191 */       String str1 = (String)this._labels.elementAt(i);
/* 192 */       if (str1.length() - 2 >= 2)
/*     */       {
/* 194 */         localVector.addElement(str1.substring(2, str1.length() - 2));
/*     */       }
/*     */     }
/* 197 */     String str3 = new String("M.{");
/*     */     
/* 199 */     for (int j = 0; j < localVector.size(); j++) {
/* 200 */       str3 = str3.concat((String)localVector.elementAt(j) + "|");
/*     */     }
/*     */     
/* 203 */     str3 = str3.substring(0, str3.length() - 1);
/*     */     
/* 205 */     if ((localVector.size() > 0) && 
/* 206 */       (((String)localVector.elementAt(0)).length() > 0)) {
/* 207 */       str3 = str3.concat("}.");
/*     */     }
/* 209 */     str3 = str3.concat(str2);
/*     */     
/* 211 */     return str3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawLines()
/*     */   {
/* 219 */     if (this._child != null)
/* 220 */       this._child.drawLines();
/* 221 */     fireDrawLineEvent();
/*     */   }
/*     */   
/*     */   public void addMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {
/* 225 */     this._matrixChangeListeners.addElement(paramHMatrixChangeListener);
/*     */   }
/*     */   
/*     */   public void removeMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {
/* 229 */     this._matrixChangeListeners.removeElement(paramHMatrixChangeListener);
/*     */   }
/*     */   
/*     */   public void fireMatrixChangeEvent() {
/* 233 */     for (int i = 0; i < this._matrixChangeListeners.size(); i++)
/*     */     {
/* 235 */       ((HMatrixChangeListener)this._matrixChangeListeners.elementAt(i)).matrixChanged(new HMatrixObject(this._myTrans, mousePressed(), new String("hello"), 
/* 236 */         getTransformValue(), this));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addChild(HNode paramHNode)
/*     */   {
/* 244 */     this._child = paramHNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean removeChild(HNode paramHNode)
/*     */   {
/* 251 */     if (this._child == paramHNode) {
/* 252 */       this._child = null;
/* 253 */       return true;
/*     */     }
/* 255 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getString()
/*     */   {
/* 262 */     return "T";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 269 */     return BACKGROUND;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 273 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canAddChild(HNode paramHNode)
/*     */   {
/* 280 */     if (this._child == null)
/* 281 */       return true;
/* 282 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasChild()
/*     */   {
/* 289 */     if (this._child != null)
/* 290 */       return true;
/* 291 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild(int paramInt) {
/* 295 */     if ((paramInt == 0) && (this._child != null))
/* 296 */       return true;
/* 297 */     return false;
/*     */   }
/*     */   
/*     */   public String getTransformValue() {
/* 301 */     return new String("");
/*     */   }
/*     */   
/*     */   public HNode getChild(int paramInt) {
/* 305 */     if (paramInt == 0)
/* 306 */       return this._child;
/* 307 */     return null;
/*     */   }
/*     */   
/*     */   public Vector getChildren() {
/* 311 */     Vector localVector = new Vector();
/* 312 */     if (this._child != null)
/* 313 */       localVector.addElement(this._child);
/* 314 */     return localVector;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HTransformNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */