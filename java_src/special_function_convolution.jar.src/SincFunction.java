/*   */ public class SincFunction implements Function
/*   */ {
/*   */   public double value(double paramDouble)
/*   */   {
/* 5 */     if (Math.abs(paramDouble) < 1.0E-4D) {
/* 6 */       return 1.0D;
/*   */     }
/* 8 */     return Math.sin(3.141592653589793D * paramDouble) / (3.141592653589793D * paramDouble);
/*   */   }
/*   */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/SincFunction.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */