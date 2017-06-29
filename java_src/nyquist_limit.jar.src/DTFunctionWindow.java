/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class DTFunctionWindow extends javax.swing.JLayeredPane implements java.io.Serializable, java.awt.event.ComponentListener, java.awt.event.MouseListener, java.awt.event.MouseMotionListener
/*     */ {
/*     */   public DTFunctionWindow()
/*     */   {
/*  16 */     setOpaque(true);
/*     */     
/*     */ 
/*     */ 
/*  20 */     setDoubleBuffered(true);
/*     */     
/*  22 */     this.border_ = javax.swing.BorderFactory.createTitledBorder(
/*  23 */       javax.swing.BorderFactory.createRaisedBevelBorder(), 
/*  24 */       "Function", 
/*  25 */       0, 
/*  26 */       3);
/*  27 */     setBorder(this.border_);
/*     */     
/*  29 */     addMouseListener(this);
/*  30 */     addMouseMotionListener(this);
/*  31 */     addComponentListener(this);
/*     */     
/*  33 */     this.prev_size = getSize();
/*     */     
/*  35 */     this.values_ = new DValue[this.num_values];
/*  36 */     for (int i = 0; i < this.num_values; i++) {
/*  37 */       this.values_[i] = new DValue(this, i, 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setController(DTFunctionController paramDTFunctionController)
/*     */   {
/*  43 */     this.function_controller = paramDTFunctionController;
/*  44 */     this.function_controller.setFunctionWindow(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paintComponent(Graphics paramGraphics)
/*     */   {
/*  57 */     createBacking(paramGraphics);
/*  58 */     createGraph(paramGraphics);
/*     */     
/*  60 */     Rectangle localRectangle = paramGraphics.getClipBounds();
/*  61 */     paramGraphics.drawImage(this.chart_img, 
/*  62 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  63 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  64 */       null);
/*     */     
/*  66 */     this.prev_size = getSize();
/*     */     
/*  68 */     this.redraw_backing = false;
/*  69 */     this.redraw_chart = false;
/*     */   }
/*     */   
/*     */   public void createGraph(Graphics paramGraphics)
/*     */   {
/*  74 */     if ((this.chart_img == null) || 
/*  75 */       (!this.prev_size.equals(getSize())) || 
/*  76 */       (this.redraw_backing))
/*     */     {
/*  78 */       if (this.chart_img == null) {
/*  79 */         realloc();
/*     */       }
/*  81 */       this.chart_img = createImage(getSize().width, getSize().height);
/*  82 */       this.chart_gfx = this.chart_img.getGraphics();
/*  83 */       this.chart_gfx.setFont(getFont());
/*     */       
/*  85 */       this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
/*     */       
/*  87 */       Insets localInsets = getInsets();
/*     */       int i;
/*     */       int j;
/*  90 */       int k; int m; int n; int i1; if (this.show_numbers) {
/*  91 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/*  92 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/*  93 */         k = localInsets.left + i + 4;
/*  94 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/*  95 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/*  96 */         n = m + 4;
/*  97 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 100 */         i = 1;
/* 101 */         j = 0;
/* 102 */         k = 6;
/* 103 */         m = 1;
/* 104 */         n = 6;
/* 105 */         i1 = 0;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 110 */       int i2 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 111 */       int i3 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 113 */       if ((i2 != this.chart_width) || (i3 != this.chart_height)) {
/* 114 */         realloc();
/*     */       }
/* 116 */       for (int i4 = 0; i4 < this.num_values; i4++) {
/* 117 */         this.values_[i4].draw(this.chart_gfx);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createBacking(Graphics paramGraphics)
/*     */   {
/* 135 */     if ((this.graphpaper_img == null) || 
/* 136 */       (!this.prev_size.equals(getSize())) || 
/* 137 */       (this.redraw_backing))
/*     */     {
/*     */ 
/*     */ 
/* 141 */       this.graphpaper_img = createImage(getSize().width, getSize().height);
/* 142 */       this.graphpaper_gfx = this.graphpaper_img.getGraphics();
/* 143 */       this.graphpaper_gfx.setFont(getFont());
/*     */       
/*     */ 
/* 146 */       Insets localInsets = getInsets();
/*     */       int i;
/* 148 */       int j; int k; int m; int n; int i1; if (this.show_numbers) {
/* 149 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 150 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 151 */         k = localInsets.left + i + 4;
/* 152 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 153 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 154 */         n = m + 4;
/* 155 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 158 */         i = 1;
/* 159 */         j = 0;
/* 160 */         k = 6;
/* 161 */         m = 1;
/* 162 */         n = 6;
/* 163 */         i1 = 0;
/*     */       }
/*     */       
/* 166 */       int i2 = k + 1;
/* 167 */       int i3 = localInsets.top + 1 + m;
/* 168 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 169 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 171 */       this.graphpaper_gfx.setColor(Color.lightGray);
/* 172 */       this.graphpaper_gfx.fillRect(0, 0, getSize().width, getSize().height);
/*     */       
/* 174 */       this.graphpaper_gfx.setColor(getForeground());
/* 175 */       this.graphpaper_gfx.drawRect(i2 - 1, i3 - 1, i4 + 2, i5 + 2);
/*     */       
/* 177 */       this.graphpaper_gfx.setColor(this.bg_color);
/* 178 */       this.graphpaper_gfx.fillRect(i2, i3, i4, i5);
/*     */       
/*     */ 
/* 181 */       double d1 = this.x_start;
/*     */       String str;
/*     */       int i6;
/* 184 */       for (int i7 = 0; i7 < this.x_divisions; i7++) {
/* 185 */         int i8 = (int)(i2 + i4 * i7 / (this.x_divisions - 1.0D));
/*     */         
/* 187 */         if (this.show_numbers) {
/* 188 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 189 */           str = new Double(d1).toString();
/* 190 */           if (d1 < 0.0D) {
/* 191 */             i6 = Math.min(5, str.length());
/*     */           } else
/* 193 */             i6 = Math.min(4, str.length());
/* 194 */           str = str.substring(0, i6);
/*     */           
/*     */ 
/* 197 */           this.graphpaper_gfx.drawString(str, i8 - j * str.length() / 2, i3 + i5 + i1 + 4);
/* 198 */           d1 += (this.x_end - this.x_start) / (this.x_divisions - 1.0D);
/*     */         }
/*     */         
/* 201 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 202 */         this.graphpaper_gfx.drawLine(i8, i3, i8, i3 + i5);
/*     */       }
/*     */       
/*     */ 
/* 206 */       double d2 = this.y_end;
/* 207 */       for (int i9 = 0; i9 < this.y_divisions; i9++) {
/* 208 */         i10 = (int)(i3 + i5 * i9 / (this.y_divisions - 1.0D));
/*     */         
/* 210 */         if (this.show_numbers) {
/* 211 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 212 */           str = new Double(d2).toString();
/* 213 */           i6 = Math.min(4, str.length());
/* 214 */           str = str.substring(0, i6);
/*     */           
/* 216 */           this.graphpaper_gfx.drawString(str, localInsets.left + 2, i10 + 2);
/* 217 */           d2 -= (this.y_end - this.y_start) / (this.y_divisions - 1.0D);
/*     */         }
/*     */         
/* 220 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 221 */         this.graphpaper_gfx.drawLine(i2, i10, i2 + i4, i10);
/*     */       }
/*     */       
/*     */ 
/* 225 */       this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 226 */       int i10 = (int)(-this.x_start / (this.x_end - this.x_start) * i4) + i2;
/* 227 */       if ((i10 >= i2) && (i10 <= i2 + i4)) {
/* 228 */         this.graphpaper_gfx.drawLine(i10, i3, i10, i3 + i5);
/*     */       }
/* 230 */       int i11 = (int)(i3 + i5 - -this.y_start / (this.y_end - this.y_start) * i5);
/* 231 */       if ((i11 >= i3) && (i11 <= i3 + i5)) {
/* 232 */         this.graphpaper_gfx.drawLine(i2, i11, i2 + i4, i11);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void realloc()
/*     */   {
/* 239 */     Insets localInsets = getInsets();
/*     */     int i;
/* 241 */     int j; int k; int m; if ((this.graphpaper_gfx != null) && (this.show_numbers)) {
/* 242 */       i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 243 */       j = localInsets.left + i + 4;
/*     */       
/* 245 */       k = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 246 */         this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 247 */       m = k + 4;
/*     */     }
/*     */     else {
/* 250 */       i = 1;
/* 251 */       j = 6;
/*     */       
/* 253 */       k = 1;
/* 254 */       m = 6;
/*     */     }
/*     */     
/* 257 */     this.chart_x = (j + 1);
/* 258 */     this.chart_y = (localInsets.top + 1 + k);
/* 259 */     this.chart_width = (getSize().width - localInsets.right - j - 2 - i / 2);
/* 260 */     this.chart_height = (getSize().height - (localInsets.top + localInsets.bottom + m + 2) - k);
/*     */     
/* 262 */     for (int n = 0; n < this.num_values; n++) {
/* 263 */       this.values_[n].setX((int)(n * (this.chart_width / (this.num_values - 1.0D))));
/*     */     }
/*     */   }
/*     */   
/*     */   public void changeValues()
/*     */   {
/* 269 */     if ((this.graphpaper_gfx == null) || (this.chart_gfx == null)) {
/* 270 */       System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
/* 271 */       return;
/*     */     }
/*     */     
/*     */ 
/* 275 */     this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
/*     */     
/* 277 */     for (int i = 0; i < this.num_values; i++) {
/* 278 */       this.values_[i].draw(this.chart_gfx);
/*     */     }
/*     */     
/* 281 */     repaint();
/*     */   }
/*     */   
/*     */   public void setValues(int paramInt, double[] paramArrayOfDouble)
/*     */   {
/* 286 */     this.num_values = paramInt;
/* 287 */     this.values_ = new DValue[this.num_values];
/* 288 */     for (int i = 0; i < this.num_values; i++) {
/* 289 */       this.values_[i] = new DValue(this, (int)(i * (this.chart_width / (this.num_values - 1.0D))), 
/* 290 */         paramArrayOfDouble[i]);
/*     */     }
/*     */     
/* 293 */     changeValues();
/*     */   }
/*     */   
/*     */   public DValue getDValue(int paramInt)
/*     */   {
/* 298 */     return this.values_[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */   public int XPixToCht(int paramInt)
/*     */   {
/* 304 */     return paramInt - this.chart_x;
/*     */   }
/*     */   
/*     */   public int XChtToPix(int paramInt)
/*     */   {
/* 309 */     return paramInt + this.chart_x;
/*     */   }
/*     */   
/*     */   public double YPixToCht(int paramInt)
/*     */   {
/* 314 */     return (this.chart_y + this.chart_height - paramInt) * (this.y_end - this.y_start) / this.chart_height + this.y_start;
/*     */   }
/*     */   
/*     */   public int YChtToPix(double paramDouble)
/*     */   {
/* 319 */     return (int)(this.chart_y + this.chart_height - (paramDouble - this.y_start) * (this.chart_height / (this.y_end - this.y_start)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 338 */     DValue localDValue = null;
/*     */     
/* 340 */     for (int i = 0; i < this.num_values; i++) {
/* 341 */       if (this.values_[i].intersects(paramMouseEvent)) {
/* 342 */         localDValue = this.values_[i];
/*     */       }
/*     */     }
/*     */     
/* 346 */     if (localDValue != null) {
/* 347 */       this.drag_value = localDValue;
/* 348 */       this.function_controller.beginUpdate(localDValue);
/* 349 */       localDValue.beginDrag(paramMouseEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 355 */     if (this.drag_value != null) {
/* 356 */       this.drag_value.endDrag();
/* 357 */       this.drag_value = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent)
/*     */   {
/* 363 */     boolean bool = false;
/* 364 */     for (int i = 0; i < this.num_values; i++) {
/* 365 */       if (this.values_[i].intersects(paramMouseEvent)) {
/* 366 */         bool = true;
/*     */       }
/*     */     }
/*     */     
/* 370 */     if ((bool) || (this.was_foo)) {
/* 371 */       changeValues();
/*     */     }
/* 373 */     this.was_foo = bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */   public void valueUpdate()
/*     */   {
/* 383 */     this.function_controller.valueUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */   public void componentResized(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */ 
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/* 396 */   public Dimension getMinimumSize() { return new Dimension(150, 150); }
/* 397 */   public Dimension getPreferredSize() { return new Dimension(600, 300); }
/*     */   
/* 399 */   public int getChartHeight() { return this.chart_height; }
/* 400 */   public int getChartWidth() { return this.chart_width; }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/* 404 */     super.setEnabled(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String paramString)
/*     */   {
/* 412 */     this.border_.setTitle(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 418 */     return this.border_.getTitle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setForeground(Color paramColor)
/*     */   {
/* 428 */     this.redraw_backing = true;
/* 429 */     super.setForeground(paramColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(java.awt.Font paramFont)
/*     */   {
/* 440 */     this.redraw_backing = true;
/* 441 */     super.setFont(paramFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphBackgroundColor()
/*     */   {
/* 450 */     return this.bg_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphBackgroundColor(Color paramColor)
/*     */   {
/* 459 */     this.redraw_backing = true;
/* 460 */     this.bg_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphColor()
/*     */   {
/* 469 */     return this.graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphColor(Color paramColor)
/*     */   {
/* 478 */     this.redraw_backing = true;
/* 479 */     this.graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getActiveGraphColor()
/*     */   {
/* 488 */     return this.active_graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActiveGraphColor(Color paramColor)
/*     */   {
/* 497 */     this.redraw_backing = true;
/* 498 */     this.active_graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getPrimaryChartColor()
/*     */   {
/* 506 */     return this.primary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrimaryChartColor(Color paramColor)
/*     */   {
/* 514 */     this.redraw_backing = true;
/* 515 */     this.primary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getSecondaryChartColor()
/*     */   {
/* 523 */     return this.secondary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSecondaryChartColor(Color paramColor)
/*     */   {
/* 531 */     this.redraw_backing = true;
/* 532 */     this.secondary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getXDivisions()
/*     */   {
/* 540 */     return this.x_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXDivisions(int paramInt)
/*     */   {
/* 548 */     this.redraw_backing = true;
/* 549 */     this.x_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getYDivisions()
/*     */   {
/* 557 */     return this.y_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYDivisions(int paramInt)
/*     */   {
/* 565 */     this.redraw_backing = true;
/* 566 */     this.y_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXStart()
/*     */   {
/* 574 */     return this.x_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXStart(double paramDouble)
/*     */   {
/* 582 */     this.redraw_backing = true;
/* 583 */     this.x_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXEnd()
/*     */   {
/* 591 */     return this.x_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXEnd(double paramDouble)
/*     */   {
/* 599 */     this.redraw_backing = true;
/* 600 */     this.x_end = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYStart()
/*     */   {
/* 608 */     return this.y_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYStart(double paramDouble)
/*     */   {
/* 616 */     this.redraw_backing = true;
/* 617 */     this.y_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYEnd()
/*     */   {
/* 625 */     return this.y_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYEnd(double paramDouble)
/*     */   {
/* 633 */     this.redraw_backing = true;
/* 634 */     this.y_end = paramDouble;
/*     */   }
/*     */   
/* 637 */   protected boolean show_numbers = true;
/* 638 */   protected Color bg_color = Color.white;
/* 639 */   protected Color graph_color = Color.darkGray;
/* 640 */   protected Color active_graph_color = Color.blue;
/* 641 */   protected Color primary_cht_color = Color.black;
/* 642 */   protected Color secondary_cht_color = Color.lightGray;
/* 643 */   protected int x_divisions = 9;
/* 644 */   protected int y_divisions = 5;
/*     */   protected double x_start;
/* 646 */   protected double x_end = 1.0D;
/* 647 */   protected double y_start = -1.2D;
/* 648 */   protected double y_end = 1.2D;
/*     */   
/*     */   protected transient DValue[] values_;
/*     */   
/*     */   protected transient DValue drag_value;
/*     */   protected transient javax.swing.border.TitledBorder border_;
/*     */   protected transient Dimension prev_size;
/*     */   protected transient java.awt.Image graphpaper_img;
/*     */   protected transient Graphics graphpaper_gfx;
/*     */   protected transient java.awt.Image chart_img;
/*     */   protected transient Graphics chart_gfx;
/* 659 */   protected transient boolean redraw_backing = true;
/* 660 */   protected transient boolean redraw_chart = true;
/*     */   protected transient int chart_x;
/* 662 */   protected transient int chart_y; protected transient int chart_width; protected transient int chart_height; protected transient boolean was_foo = false;
/*     */   
/*     */   protected transient DTFunctionController function_controller;
/*     */   
/* 666 */   protected transient int num_values = 32;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/DTFunctionWindow.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */