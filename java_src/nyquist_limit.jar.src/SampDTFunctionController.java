/*    */ 
/*    */ public class SampDTFunctionController
/*    */   implements DTFunctionController
/*    */ {
/*    */   protected transient DTFunctionWindow function_window;
/*    */   
/*    */   public void updateSamples(int paramInt, double[] paramArrayOfDouble)
/*    */   {
/*  9 */     this.function_window.setValues(paramInt, paramArrayOfDouble);
/*    */   }
/*    */   
/*    */   public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
/*    */   {
/* 14 */     this.function_window = paramDTFunctionWindow;
/*    */   }
/*    */   
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void beginUpdate(DValue paramDValue) {}
/*    */   
/*    */   public void valueUpdate() {}
/*    */   
/*    */   public void endUpdate() {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/SampDTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */