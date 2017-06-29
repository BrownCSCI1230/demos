/*    */ import java.util.EventObject;
/*    */ import javax.swing.JSlider;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ import javax.swing.event.ChangeListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovableCTFunctionController
/*    */   extends EditableCTFunctionController
/*    */   implements ChangeListener
/*    */ {
/*    */   protected transient int x_offset;
/*    */   protected transient JSlider j_slider;
/*    */   
/*    */   public void mouseStart(int paramInt1, int paramInt2)
/*    */   {
/* 19 */     super.mouseStart(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */ 
/*    */   public void resize(int paramInt1, int paramInt2)
/*    */   {
/* 25 */     super.resize(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public void stateChanged(ChangeEvent paramChangeEvent)
/*    */   {
/* 30 */     JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
/* 31 */     int i = localJSlider.getValue();
/* 32 */     if (i == this.x_offset) {
/* 33 */       return;
/*    */     }
/* 35 */     this.x_offset = i;
/*    */     
/* 37 */     String str = "g(x";
/* 38 */     double d1 = this.function_window.getXStart();
/* 39 */     double d2 = this.function_window.getXEnd();
/* 40 */     int j = this.function_window.getChartWidth();
/* 41 */     double d3 = this.x_offset * (d2 - d1) / j;
/* 42 */     if (d3 < 0.0D) {
/* 43 */       str = str + "+";
/* 44 */       str = str + Math.abs(d3);
/*    */     }
/* 46 */     else if (d3 > 0.0D) {
/* 47 */       str = str + "-";
/* 48 */       str = str + d3;
/*    */     }
/*    */     
/* 51 */     str = str.substring(0, Math.min(9, str.length()));
/* 52 */     str = str + ") [Convolution Filter]";
/*    */     
/* 54 */     this.function_window.setTitle(str);
/*    */     
/* 56 */     double[] arrayOfDouble = new double[this.chart_width];
/* 57 */     int k; if (i < 0) {
/* 58 */       for (k = 0; k < this.chart_width + i; k++) {
/* 59 */         arrayOfDouble[k] = this.chart_values[(k - i)];
/*    */       }
/*    */       
/*    */     } else {
/* 63 */       for (k = 0; k < this.chart_width - i; k++) {
/* 64 */         arrayOfDouble[(k + i)] = this.chart_values[k];
/*    */       }
/*    */     }
/*    */     
/* 68 */     this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 73 */     if (this.imp_ctrl != null) {
/* 74 */       this.imp_ctrl.reveal(this.x_offset);
/*    */     }
/*    */   }
/*    */   
/*    */   public void resetPosition() {
/* 79 */     if (this.x_offset == 0) {
/* 80 */       return;
/*    */     }
/* 82 */     this.x_offset = 0;
/*    */     
/* 84 */     this.j_slider.setValue(0);
/* 85 */     this.function_window.setTitle("g(x)");
/*    */     
/* 87 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*    */   }
/*    */   
/*    */   public void setSlider(JSlider paramJSlider)
/*    */   {
/* 92 */     this.j_slider = paramJSlider;
/* 93 */     this.j_slider.addChangeListener(this);
/*    */   }
/*    */   
/* 96 */   public int getOffset() { return this.x_offset; }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/MovableCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */