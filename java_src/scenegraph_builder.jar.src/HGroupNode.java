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
/*     */ 
/*     */ 
/*     */ public class HGroupNode
/*     */   extends HNode
/*     */ {
/*  27 */   public HGroupNode(HTree paramHTree, HChooser paramHChooser) { super(paramHTree, paramHChooser); }
/*  28 */   private Vector _nodes = new Vector();
/*  29 */   private boolean _animating = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(float[][] paramArrayOfFloat, boolean paramBoolean, HPosition paramHPosition)
/*     */   {
/*  36 */     for (int i = 0; i < this._nodes.size(); i++)
/*     */     {
/*  38 */       ((HNode)this._nodes.elementAt(i)).render(paramArrayOfFloat, paramBoolean, paramHPosition);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawTree()
/*     */   {
/*  47 */     for (int i = 0; i < this._nodes.size(); i++)
/*     */     {
/*  49 */       ((HNode)this._nodes.elementAt(i)).drawTree();
/*     */     }
/*  51 */     fireDrawNodeEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAnimating(boolean paramBoolean)
/*     */   {
/*  58 */     this._animating = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSelects(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*     */     int i;
/*  65 */     if (!this._animating) {
/*  66 */       i = 0;
/*  67 */       if ((paramBoolean1) || (isSelected()))
/*  68 */         i = 1;
/*  69 */       setSelected(i);
/*     */       
/*  71 */       boolean bool = false;
/*  72 */       if ((paramBoolean2) || (mousePressed()))
/*  73 */         bool = true;
/*  74 */       for (int j = 0; j < this._nodes.size(); j++) {
/*  75 */         ((HNode)this._nodes.elementAt(j)).setSelects(i, bool);
/*     */       }
/*     */     } else {
/*  78 */       for (i = 0; i < this._nodes.size(); i++) {
/*  79 */         ((HNode)this._nodes.elementAt(i)).setSelects(false, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void getMatrix(Stack paramStack, boolean paramBoolean, float[][] paramArrayOfFloat, int paramInt, String paramString)
/*     */   {
/*  89 */     boolean bool = false;
/*  90 */     if ((paramBoolean) || (mousePressed()))
/*  91 */       bool = true;
/*  92 */     for (int i = 0; i < this._nodes.size(); i++) {
/*  93 */       ((HNode)this._nodes.elementAt(i)).getMatrix(paramStack, bool, paramArrayOfFloat, 
/*  94 */         i + 1, paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public void animate(Thread paramThread, HNode paramHNode, float[][] paramArrayOfFloat) {
/*  99 */     animateAction(paramThread, paramHNode);
/* 100 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 101 */       ((HNode)this._nodes.elementAt(i)).animate(paramThread, this, paramArrayOfFloat);
/* 102 */       if (i < this._nodes.size() + 1) {
/*     */         try {
/* 104 */           Thread.sleep(500L);
/*     */         } catch (InterruptedException localInterruptedException) {
/* 106 */           paramThread.interrupt();
/*     */         }
/*     */       }
/*     */     }
/* 110 */     finishAnimateAction(paramThread);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawLabels()
/*     */   {
/* 117 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 118 */       ((HNode)this._nodes.elementAt(i)).drawLabels();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void drawLines()
/*     */   {
/* 125 */     for (int i = 0; i < this._nodes.size(); i++)
/* 126 */       ((HNode)this._nodes.elementAt(i)).drawLines();
/* 127 */     fireDrawLineEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addChild(HNode paramHNode)
/*     */   {
/* 134 */     this._nodes.addElement(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean removeChild(HNode paramHNode)
/*     */   {
/* 141 */     return this._nodes.removeElement(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getString()
/*     */   {
/* 148 */     return "G";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 155 */     return new Color(25, 200, 25);
/*     */   }
/*     */   
/*     */   public int getType() {
/* 159 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean canAddChild(HNode paramHNode) {
/* 163 */     if (!this._nodes.contains(paramHNode))
/* 164 */       return true;
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild() {
/* 169 */     if (this._nodes.size() > 0)
/* 170 */       return true;
/* 171 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasChild(int paramInt) {
/* 175 */     if (paramInt < this._nodes.size())
/* 176 */       return true;
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   public HNode getChild(int paramInt) {
/* 181 */     if (hasChild(paramInt))
/* 182 */       return (HNode)this._nodes.elementAt(paramInt);
/* 183 */     return null;
/*     */   }
/*     */   
/*     */   public int getIndex(HNode paramHNode) {
/* 187 */     if (this._nodes.contains(paramHNode))
/* 188 */       return this._nodes.indexOf(paramHNode);
/* 189 */     return -1;
/*     */   }
/*     */   
/*     */   public Vector getChildren() {
/* 193 */     Vector localVector = new Vector();
/* 194 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 195 */       localVector.addElement(this._nodes.elementAt(i));
/*     */     }
/* 197 */     return localVector;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HGroupNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */