/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class EditableDTFunctionController
/*    */   implements DTFunctionController, Serializable, PropertyChangeListener
/*    */ {
/*    */   protected transient DValue cur_dv;
/*    */   protected transient DTFunctionWindow function_window;
/*    */   protected transient ProductDTFunctionController prod_ctrl;
/*    */   protected transient ConvDTFunctionController conv_ctrl;
/*    */   
/*    */   public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
/*    */   {
/* 16 */     this.function_window = paramDTFunctionWindow;
/*    */   }
/*    */   
/*    */   public void clear()
/*    */   {
/* 21 */     ConvolutionSlide.__instance.resetPositions();
/* 22 */     for (int i = 0; i < 17; i++) {
/* 23 */       this.function_window.getDValue(i).setValue(0.0D);
/*    */     }
/* 25 */     this.function_window.changeValues();
/* 26 */     this.prod_ctrl.recompute();
/* 27 */     this.conv_ctrl.recompute();
/*    */   }
/*    */   
/*    */   public void normalize()
/*    */   {
/* 32 */     ConvolutionSlide.__instance.resetPositions();
/* 33 */     double d = 0.0D;
/* 34 */     for (int i = 0; i < 17; i++) {
/* 35 */       d += this.function_window.getDValue(i).getValue();
/*    */     }
/*    */     
/* 38 */     if (Math.abs(d) > 0.0D) {
/* 39 */       for (int j = 0; j < 17; j++) {
/* 40 */         this.function_window.getDValue(j).setValue(this.function_window.getDValue(j).getValue() / d);
/*    */       }
/*    */     }
/*    */     
/* 44 */     this.function_window.changeValues();
/* 45 */     this.prod_ctrl.recompute();
/* 46 */     this.conv_ctrl.recompute();
/*    */   }
/*    */   
/*    */   public DValue getDValue(int paramInt)
/*    */   {
/* 51 */     return this.function_window.getDValue(paramInt);
/*    */   }
/*    */   
/*    */   public void setProductController(ProductDTFunctionController paramProductDTFunctionController)
/*    */   {
/* 56 */     this.prod_ctrl = paramProductDTFunctionController;
/*    */   }
/*    */   
/*    */   public void setConvController(ConvDTFunctionController paramConvDTFunctionController)
/*    */   {
/* 61 */     this.conv_ctrl = paramConvDTFunctionController;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */   
/*    */ 
/*    */   public void beginUpdate(DValue paramDValue)
/*    */   {
/* 71 */     ConvolutionSlide.__instance.resetPositions();
/* 72 */     this.prod_ctrl.beginUpdate(paramDValue);
/* 73 */     this.conv_ctrl.beginUpdate(paramDValue);
/* 74 */     this.cur_dv = paramDValue;
/*    */   }
/*    */   
/*    */   public void valueUpdate()
/*    */   {
/* 79 */     this.prod_ctrl.recompute();
/* 80 */     this.conv_ctrl.recompute();
/*    */   }
/*    */   
/*    */   public void endUpdate() {}
/*    */   
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/EditableDTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */