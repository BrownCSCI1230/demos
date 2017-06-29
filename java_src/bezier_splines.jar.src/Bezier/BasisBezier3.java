/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasisBezier3
/*    */   extends BasisFnc
/*    */ {
/*    */   public BasisBezier3()
/*    */   {
/* 14 */     super("3t^2(1-t)", Color.cyan);
/*    */   }
/*    */   
/*    */   public double tVal(double t) {
/* 18 */     return 3.0D * t * t * (1.0D - t);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisBezier3.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */