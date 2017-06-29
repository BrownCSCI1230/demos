/*   */ public class BoxFunction implements Function
/*   */ {
/*   */   public double value(double paramDouble)
/*   */   {
/* 5 */     if ((paramDouble >= -0.5D) && (paramDouble <= 0.5D)) {
/* 6 */       return 1.0D;
/*   */     }
/* 8 */     return 0.0D;
/*   */   }
/*   */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/BoxFunction.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */