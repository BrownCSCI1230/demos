/*    */ import java.awt.Polygon;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HRobotPart
/*    */ {
/*    */   private float[][] _points;
/*    */   private float[][] _transformation;
/*    */   private int _numPoints;
/*    */   private int _highlightIndex;
/*    */   private boolean _selected;
/*    */   private Vector _highlightedFlags;
/*    */   private float[][] _finalShape;
/*    */   
/*    */   public HRobotPart(float[][] paramArrayOfFloat, int paramInt)
/*    */   {
/* 29 */     this._points = new float[paramInt][3];
/* 30 */     this._selected = false;
/* 31 */     this._numPoints = paramInt;
/* 32 */     this._highlightedFlags = new Vector();
/* 33 */     this._highlightIndex = 0;
/* 34 */     this._finalShape = new float[paramInt][3];
/*    */     
/*    */ 
/* 37 */     for (int i = 0; i < this._numPoints; i++)
/* 38 */       for (j = 0; j < 2; j++)
/* 39 */         this._points[i][j] = paramArrayOfFloat[i][j];
/* 40 */     for (int j = 0; j < paramInt; j++) {
/* 41 */       this._points[j][2] = 1.0F;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Polygon getShape()
/*    */   {
/* 49 */     int[] arrayOfInt1 = new int[this._numPoints];
/* 50 */     int[] arrayOfInt2 = new int[this._numPoints];
/* 51 */     for (int i = 0; i < this._numPoints; i++) {
/* 52 */       arrayOfInt1[i] = ((int)this._finalShape[i][0]);
/* 53 */       arrayOfInt2[i] = ((int)this._finalShape[i][1]);
/*    */     }
/*    */     
/* 56 */     return new Polygon(arrayOfInt1, arrayOfInt2, this._numPoints);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void draw(float[][] paramArrayOfFloat)
/*    */   {
/* 63 */     this._transformation = paramArrayOfFloat;
/* 64 */     this._finalShape = HMatrixOps.multiply(this._points, this._transformation, this._numPoints, 3);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setSelected(boolean paramBoolean)
/*    */   {
/* 71 */     Boolean localBoolean = new Boolean(paramBoolean);
/* 72 */     this._highlightedFlags.addElement(localBoolean);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isSelected()
/*    */   {
/* 80 */     boolean bool = ((Boolean)this._highlightedFlags.elementAt(this._highlightIndex))
/* 81 */       .booleanValue();
/* 82 */     this._highlightIndex += 1;
/* 83 */     return bool;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void clearHighlight()
/*    */   {
/* 90 */     this._highlightIndex = 0;
/* 91 */     this._highlightedFlags.removeAllElements();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void resetIndex()
/*    */   {
/* 99 */     this._highlightIndex = 0;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HRobotPart.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */