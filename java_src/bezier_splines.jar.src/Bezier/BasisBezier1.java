/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasisBezier1
/*    */   extends BasisFnc
/*    */ {
/*    */   public BasisBezier1()
/*    */   {
/* 14 */     super("(1-t)^3", Color.magenta);
/*    */   }
/*    */   
/*    */   public double tVal(double t) {
/* 18 */     return (1.0D - t) * (1.0D - t) * (1.0D - t);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisBezier1.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */