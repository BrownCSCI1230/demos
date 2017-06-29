/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasisBezier4
/*    */   extends BasisFnc
/*    */ {
/*    */   public BasisBezier4()
/*    */   {
/* 14 */     super("t^3", Color.green);
/*    */   }
/*    */   
/*    */   public double tVal(double t) {
/* 18 */     return t * t * t;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisBezier4.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */