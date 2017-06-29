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
/*    */ public class HTransformRotateNode
/*    */   extends HTransformNode
/*    */ {
/*    */   private float _angle;
/*    */   
/*    */   public HTransformRotateNode(HTree paramHTree, float paramFloat, HChooser paramHChooser)
/*    */   {
/* 24 */     super(paramHTree, paramHChooser);
/* 25 */     this._angle = paramFloat;
/* 26 */     setTransform(HMatrixOps.getRotationMatrix(this._angle));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void drawThisNode(Graphics paramGraphics)
/*    */   {
/* 33 */     super.drawThisNode(paramGraphics);
/*    */     
/*    */ 
/* 36 */     HPosition localHPosition = getPosition();
/* 37 */     paramGraphics.setColor(Color.darkGray);
/* 38 */     paramGraphics.setFont(new Font("SansSerif", 1, 12));
/* 39 */     paramGraphics.drawString("R", (int)localHPosition.x + 2, (int)localHPosition.y + 7);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean rotateActive()
/*    */   {
/* 45 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void updateTransform(HPosition paramHPosition)
/*    */   {
/* 51 */     this._angle = paramHPosition.x;
/* 52 */     setTransform(HMatrixOps.getRotationMatrix(this._angle));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HPosition getTransform()
/*    */   {
/* 59 */     return new HPosition(this._angle, 0.0F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getTransformValue()
/*    */   {
/* 66 */     return new String("r= " + this._angle * 180.0F / 3.141592653589793D);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HTransformRotateNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */