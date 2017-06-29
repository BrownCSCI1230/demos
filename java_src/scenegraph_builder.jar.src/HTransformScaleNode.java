/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
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
/*    */ public class HTransformScaleNode
/*    */   extends HTransformNode
/*    */ {
/*    */   private float _scaleXFactor;
/*    */   private float _scaleYFactor;
/*    */   
/*    */   public HTransformScaleNode(HTree paramHTree, float paramFloat, HChooser paramHChooser)
/*    */   {
/* 25 */     super(paramHTree, paramHChooser);
/* 26 */     this._scaleXFactor = paramFloat;
/* 27 */     this._scaleYFactor = paramFloat;
/* 28 */     setTransform(HMatrixOps.getScaleMatrix(this._scaleXFactor, this._scaleYFactor));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void drawThisNode(Graphics paramGraphics)
/*    */   {
/* 35 */     super.drawThisNode(paramGraphics);
/*    */     
/*    */ 
/* 38 */     HPosition localHPosition = getPosition();
/* 39 */     paramGraphics.setColor(Color.darkGray);
/* 40 */     paramGraphics.setFont(new Font("SansSerif", 1, 12));
/* 41 */     paramGraphics.drawString("S", (int)localHPosition.x + 2, (int)localHPosition.y + 9);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean scaleActive()
/*    */   {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void updateTransform(HPosition paramHPosition)
/*    */   {
/* 53 */     this._scaleXFactor = paramHPosition.x;
/* 54 */     this._scaleYFactor = paramHPosition.y;
/* 55 */     setTransform(HMatrixOps.getScaleMatrix(this._scaleXFactor, this._scaleYFactor));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HPosition getTransform()
/*    */   {
/* 62 */     return new HPosition(this._scaleXFactor, this._scaleYFactor);
/*    */   }
/*    */   
/*    */   public String getTransformValue() {
/* 66 */     return new String("sx= " + this._scaleXFactor + " sy=" + this._scaleYFactor);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HTransformScaleNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */