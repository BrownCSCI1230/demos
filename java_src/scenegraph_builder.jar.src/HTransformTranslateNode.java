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
/*    */ public class HTransformTranslateNode
/*    */   extends HTransformNode
/*    */ {
/*    */   private float _x;
/*    */   private float _y;
/*    */   
/*    */   public HTransformTranslateNode(HTree paramHTree, float paramFloat1, float paramFloat2, HChooser paramHChooser)
/*    */   {
/* 23 */     super(paramHTree, paramHChooser);
/* 24 */     this._x = paramFloat1;
/* 25 */     this._y = paramFloat2;
/* 26 */     setTransform(HMatrixOps.getTranslationMatrix((int)this._x, (int)this._y));
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
/* 39 */     paramGraphics.drawString("T", (int)localHPosition.x + 2, (int)localHPosition.y + 7);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean translateActive()
/*    */   {
/* 45 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void updateTransform(HPosition paramHPosition)
/*    */   {
/* 51 */     this._x = paramHPosition.x;
/* 52 */     this._y = paramHPosition.y;
/* 53 */     setTransform(HMatrixOps.getTranslationMatrix((int)this._x, (int)this._y));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HPosition getTransform()
/*    */   {
/* 60 */     return new HPosition(this._x, this._y);
/*    */   }
/*    */   
/*    */   public String getTransformValue() {
/* 64 */     return new String("tx= " + this._x + " ty=" + this._y);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HTransformTranslateNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */