/*   */ public class TriangleFunction implements Function
/*   */ {
/*   */   public double value(double paramDouble)
/*   */   {
/* 5 */     if ((paramDouble >= -1.0D) && (paramDouble <= 1.0D)) {
/* 6 */       return 1.0D - Math.abs(paramDouble);
/*   */     }
/* 8 */     return 0.0D;
/*   */   }
/*   */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/TriangleFunction.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */