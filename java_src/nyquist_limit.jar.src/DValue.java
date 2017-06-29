/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ public class DValue extends java.awt.event.MouseMotionAdapter
/*     */ {
/*     */   protected transient DTFunctionWindow function_window;
/*     */   
/*     */   public DValue(DTFunctionWindow paramDTFunctionWindow, int paramInt, double paramDouble)
/*     */   {
/*   9 */     this.x_ = paramInt;
/*  10 */     this.value_ = paramDouble;
/*  11 */     this.function_window = paramDTFunctionWindow;
/*     */   }
/*     */   
/*     */   public void draw(java.awt.Graphics paramGraphics)
/*     */   {
/*  16 */     int i = this.function_window.XChtToPix(this.x_);
/*  17 */     int j = this.function_window.YChtToPix(this.value_);
/*     */     
/*  19 */     paramGraphics.setColor(this.bar_color);
/*  20 */     paramGraphics.drawLine(i, this.function_window.YChtToPix(0.0D), 
/*  21 */       i, j);
/*     */     
/*  23 */     if (this.mouse_over) {
/*  24 */       paramGraphics.setColor(this.active_box_color);
/*     */     }
/*     */     else {
/*  27 */       paramGraphics.setColor(this.box_color);
/*     */     }
/*     */     
/*  30 */     paramGraphics.fillRect(i - 3, j - 3, 
/*  31 */       6, 6);
/*     */   }
/*     */   
/*     */   public void beginDrag(MouseEvent paramMouseEvent)
/*     */   {
/*  36 */     int i = this.function_window.getChartWidth();
/*  37 */     this.function_window.getChartHeight();
/*     */     
/*  39 */     this.last_x = this.function_window.XPixToCht(paramMouseEvent.getX());
/*  40 */     this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
/*     */     
/*  42 */     this.h_min = 0;
/*  43 */     this.h_max = i;
/*  44 */     this.v_min = this.function_window.getYStart();
/*  45 */     this.v_max = this.function_window.getYEnd();
/*     */     
/*  47 */     this.function_window.addMouseMotionListener(this);
/*     */   }
/*     */   
/*     */   public void endDrag()
/*     */   {
/*  52 */     this.function_window.removeMouseMotionListener(this);
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/*  57 */     if (this.horiz_enable) {
/*  58 */       int i = this.function_window.XPixToCht(paramMouseEvent.getX());
/*  59 */       int j = i - this.last_x;
/*  60 */       if ((i + j > this.h_min) && 
/*  61 */         (i + j < this.h_max)) {
/*  62 */         this.x_ += j;
/*  63 */         this.last_x = i;
/*     */       }
/*  65 */       else if (i + j <= this.h_min) {
/*  66 */         this.x_ = this.h_min;
/*  67 */         this.last_x = 0;
/*     */       }
/*     */       else {
/*  70 */         this.x_ = this.h_max;
/*  71 */         this.last_x = this.function_window.getChartWidth();
/*     */       }
/*     */     }
/*     */     
/*  75 */     if (this.vert_enable) {
/*  76 */       double d1 = this.function_window.YPixToCht(paramMouseEvent.getY());
/*  77 */       double d2 = d1 - this.last_y;
/*  78 */       if ((d1 + d2 > this.v_min) && 
/*  79 */         (d1 + d2 < this.v_max)) {
/*  80 */         this.value_ += d2;
/*  81 */         this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
/*     */       }
/*  83 */       else if (d1 + d2 <= this.v_min) {
/*  84 */         this.value_ = this.v_min;
/*  85 */         this.last_y = this.function_window.getYStart();
/*     */       }
/*     */       else {
/*  88 */         this.value_ = this.v_max;
/*  89 */         this.last_y = this.function_window.getYEnd();
/*     */       }
/*     */     }
/*     */     
/*  93 */     this.function_window.changeValues();
/*  94 */     this.function_window.valueUpdate();
/*     */   }
/*     */   
/*     */   public boolean intersects(MouseEvent paramMouseEvent)
/*     */   {
/*  99 */     int i = this.function_window.XChtToPix(this.x_);
/* 100 */     int j = this.function_window.YChtToPix(this.value_);
/*     */     
/* 102 */     this.mouse_over = ((Math.abs(paramMouseEvent.getX() - i) <= 3) && 
/* 103 */       (Math.abs(paramMouseEvent.getY() - j) <= 3));
/*     */     
/* 105 */     return this.mouse_over;
/*     */   }
/*     */   
/* 108 */   public int getX() { return this.x_; }
/* 109 */   public void setX(int paramInt) { this.x_ = paramInt; }
/*     */   
/* 111 */   public double getValue() { return this.value_; }
/* 112 */   public void setValue(double paramDouble) { this.value_ = paramDouble; }
/*     */   
/* 114 */   public void setHorizontalEnable(boolean paramBoolean) { this.horiz_enable = paramBoolean; }
/* 115 */   public void setVerticalEnable(boolean paramBoolean) { this.vert_enable = paramBoolean; }
/* 116 */   public void setColor(java.awt.Color paramColor) { this.bar_color = paramColor; }
/* 117 */   public void setBoxColor(java.awt.Color paramColor) { this.box_color = paramColor; }
/*     */   
/*     */ 
/*     */ 
/* 121 */   protected transient boolean mouse_over = false;
/*     */   
/*     */   protected transient int last_x;
/*     */   protected transient double last_y;
/*     */   protected int x_;
/*     */   protected double value_;
/* 127 */   protected boolean horiz_enable = false;
/* 128 */   protected boolean vert_enable = true;
/*     */   protected int h_min;
/*     */   protected int h_max;
/* 131 */   protected double v_min; protected double v_max; protected java.awt.Color bar_color = java.awt.Color.black;
/* 132 */   protected java.awt.Color box_color = java.awt.Color.darkGray;
/* 133 */   protected java.awt.Color active_box_color = java.awt.Color.blue;
/*     */   public static final int BOX_WIDTH = 3;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/DValue.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */