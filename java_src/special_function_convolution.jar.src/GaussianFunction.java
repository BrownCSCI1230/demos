/*    */ public class GaussianFunction implements Function
/*    */ {
/*    */   public double value(double paramDouble)
/*    */   {
/*  5 */     if ((paramDouble < -10.0D) || (paramDouble > 10.0D)) {
/*  6 */       return 0.0D;
/*    */     }
/*  8 */     double d = 0.25D;
/*    */     
/* 10 */     return 1.0D / (2.0D * Math.sqrt(3.141592653589793D * d)) * 
/* 11 */       Math.exp(-(paramDouble * paramDouble / (4.0D * d)));
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/GaussianFunction.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */