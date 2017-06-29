/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EditableIFunctionController
/*    */   implements IFunctionController, Serializable, PropertyChangeListener
/*    */ {
/*    */   protected transient DValue cur_dv;
/*    */   protected transient IFunctionWindow function_window;
/*    */   protected transient ImpulseCTFunctionController imp_ctrl;
/*    */   
/*    */   public void setFunctionWindow(IFunctionWindow paramIFunctionWindow)
/*    */   {
/* 16 */     this.function_window = paramIFunctionWindow;
/*    */   }
/*    */   
/*    */   public void clear()
/*    */   {
/* 21 */     this.function_window.getDValue().setValue(0.0D);
/*    */     
/* 23 */     this.function_window.changeValues();
/* 24 */     this.imp_ctrl.recompute();
/*    */   }
/*    */   
/*    */   public DValue getDValue()
/*    */   {
/* 29 */     return this.function_window.getDValue();
/*    */   }
/*    */   
/*    */   public void setImpulseController(ImpulseCTFunctionController paramImpulseCTFunctionController)
/*    */   {
/* 34 */     this.imp_ctrl = paramImpulseCTFunctionController;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void beginUpdate(DValue paramDValue)
/*    */   {
/* 45 */     this.cur_dv = paramDValue;
/*    */   }
/*    */   
/*    */   public void valueUpdate()
/*    */   {
/* 50 */     this.imp_ctrl.recompute();
/*    */   }
/*    */   
/*    */   public void endUpdate() {}
/*    */   
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/EditableIFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */