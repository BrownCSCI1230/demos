/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleBasis1
/*    */   extends BasisFnc
/*    */ {
/*    */   public SimpleBasis1()
/*    */   {
/* 15 */     super("1.0-t", Color.magenta);
/*    */   }
/*    */   
/*    */   public double tVal(double t) {
/* 19 */     return 1.0D - t;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/SimpleBasis1.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */