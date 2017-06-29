/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasisBezier2
/*    */   extends BasisFnc
/*    */ {
/*    */   public BasisBezier2()
/*    */   {
/* 14 */     super("3t(1-t)^2", Color.yellow);
/*    */   }
/*    */   
/*    */   public double tVal(double t) {
/* 18 */     return 3.0D * t * (1.0D - t) * (1.0D - t);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisBezier2.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */