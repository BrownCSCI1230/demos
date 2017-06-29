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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HMatrixObject
/*    */ {
/*    */   private float[][] _matrix;
/*    */   private boolean _highlight;
/*    */   private String _label;
/*    */   private String _values;
/*    */   private HNode _node;
/*    */   
/*    */   public HMatrixObject(float[][] paramArrayOfFloat, boolean paramBoolean, String paramString1, String paramString2, HNode paramHNode)
/*    */   {
/* 29 */     this._matrix = paramArrayOfFloat;
/* 30 */     this._highlight = paramBoolean;
/* 31 */     this._label = paramString1;
/* 32 */     this._values = paramString2;
/* 33 */     this._node = paramHNode;
/*    */   }
/*    */   
/*    */   public float[][] getMatrix() {
/* 37 */     return this._matrix;
/*    */   }
/*    */   
/*    */   public boolean getHighlight() {
/* 41 */     return this._highlight;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 45 */     return this._label;
/*    */   }
/*    */   
/*    */   public String getValues() {
/* 49 */     return this._values;
/*    */   }
/*    */   
/*    */   public HNode getNode() {
/* 53 */     return this._node;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HMatrixObject.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */