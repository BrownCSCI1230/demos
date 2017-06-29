/*     */ import java.util.EventObject;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovableDTFunctionController
/*     */   extends EditableDTFunctionController
/*     */   implements ChangeListener
/*     */ {
/*     */   protected transient int x_offset;
/*     */   protected transient JSlider j_slider;
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2)
/*     */   {
/*  19 */     super.resize(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void stateChanged(ChangeEvent paramChangeEvent)
/*     */   {
/*  24 */     JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
/*  25 */     int i = localJSlider.getValue();
/*  26 */     if (i == this.x_offset) {
/*  27 */       return;
/*     */     }
/*  29 */     this.x_offset = i;
/*     */     
/*  31 */     String str = "g(x";
/*  32 */     if (this.x_offset < 0.0D) {
/*  33 */       str = str + "+";
/*  34 */       str = str + Math.abs(this.x_offset);
/*     */     }
/*  36 */     else if (this.x_offset > 0.0D) {
/*  37 */       str = str + "-";
/*  38 */       str = str + this.x_offset;
/*     */     }
/*     */     
/*     */ 
/*  42 */     str = str + ") [Convolution Filter]";
/*     */     
/*  44 */     this.function_window.setTitle(str);
/*     */     int k;
/*  46 */     int m; if (i < 0) {
/*  47 */       j = Math.abs(i);
/*     */       
/*  49 */       for (k = 0; k < j; k++) {
/*  50 */         this.function_window.getDValue(k).setVisible(false);
/*     */       }
/*     */       
/*  53 */       for (m = j; m < 17; m++) {
/*  54 */         this.function_window.getDValue(m).setVisible(true);
/*     */       }
/*     */     }
/*     */     else {
/*  58 */       j = 8 - i + 8 + 1;
/*     */       
/*  60 */       for (k = 0; k < j; k++) {
/*  61 */         this.function_window.getDValue(k).setVisible(true);
/*     */       }
/*     */       
/*  64 */       for (m = j; m < 17; m++) {
/*  65 */         this.function_window.getDValue(m).setVisible(false);
/*     */       }
/*     */     }
/*     */     
/*  69 */     for (int j = 0; j < 17; j++) {
/*  70 */       this.function_window.getDValue(j).setX((int)((j + i) * (this.function_window.getChartWidth() / 16.0D)));
/*     */     }
/*     */     
/*  73 */     this.function_window.changeValues();
/*     */     
/*  75 */     if (this.prod_ctrl != null) {
/*  76 */       this.prod_ctrl.recompute();
/*     */     }
/*  78 */     if (this.conv_ctrl != null) {
/*  79 */       this.conv_ctrl.reveal(this.x_offset);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetPosition() {
/*  84 */     if (this.x_offset == 0) {
/*  85 */       return;
/*     */     }
/*  87 */     this.x_offset = 0;
/*     */     
/*  89 */     for (int i = 0; i < 17; i++) {
/*  90 */       this.function_window.getDValue(i).setX((int)(i * (this.function_window.getChartWidth() / 16.0D)));
/*     */     }
/*  92 */     this.function_window.changeValues();
/*     */     
/*  94 */     this.j_slider.setValue(0);
/*  95 */     this.function_window.setTitle("g(x)");
/*     */   }
/*     */   
/*     */   public void setSlider(JSlider paramJSlider)
/*     */   {
/* 100 */     this.j_slider = paramJSlider;
/* 101 */     this.j_slider.addChangeListener(this);
/*     */   }
/*     */   
/* 104 */   public int getOffset() { return this.x_offset; }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/MovableDTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */