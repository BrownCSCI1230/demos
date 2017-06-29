/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class CTFunctionWindow extends JLayeredPane implements java.io.Serializable, java.awt.event.ComponentListener, java.awt.event.MouseListener, java.awt.event.MouseMotionListener
/*     */ {
/*     */   public CTFunctionWindow()
/*     */   {
/*  24 */     setOpaque(true);
/*     */     
/*     */ 
/*     */ 
/*  28 */     setDoubleBuffered(true);
/*     */     
/*  30 */     this.border_ = BorderFactory.createTitledBorder(
/*  31 */       BorderFactory.createRaisedBevelBorder(), 
/*  32 */       "Function", 
/*  33 */       0, 
/*  34 */       3);
/*  35 */     setBorder(this.border_);
/*     */     
/*  37 */     addMouseListener(this);
/*  38 */     addComponentListener(this);
/*     */     
/*  40 */     this.prev_size = getSize();
/*     */   }
/*     */   
/*     */   public void setController(CTFunctionController paramCTFunctionController)
/*     */   {
/*  45 */     this.function_controller = paramCTFunctionController;
/*  46 */     this.function_controller.setFunctionWindow(this);
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
/*  59 */     createBacking(paramGraphics);
/*  60 */     createGraph(paramGraphics);
/*     */     
/*  62 */     Rectangle localRectangle = paramGraphics.getClipBounds();
/*  63 */     paramGraphics.drawImage(this.chart_img, 
/*  64 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  65 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  66 */       null);
/*     */     
/*  68 */     this.prev_size = getSize();
/*     */     
/*  70 */     if (this.show_markers) {
/*  71 */       paramGraphics.setColor(Color.red);
/*  72 */       double d1 = this.chart_height / (this.y_end - this.y_start);
/*  73 */       int i = (int)(-this.y_start * d1);
/*  74 */       double d2 = this.chart_width / (this.num_samples - 1.0D);
/*  75 */       for (int j = 0; j < this.num_samples; j++) {
/*  76 */         int k = (int)(j * d2);
/*  77 */         if (this.chart_width == k) {
/*  78 */           k--;
/*  79 */         } else if (k == 0)
/*  80 */           k++;
/*  81 */         int m = (int)((this.graph_values[k] - this.y_start) * d1);
/*  82 */         paramGraphics.drawLine(k + this.chart_x, this.chart_y + this.chart_height - i, 
/*  83 */           k + this.chart_x, this.chart_y + this.chart_height - m);
/*  84 */         paramGraphics.drawOval(k + this.chart_x - 2, this.chart_y + this.chart_height - m - 1, 3, 3);
/*     */       }
/*     */     }
/*     */     
/*  88 */     this.redraw_backing = false;
/*  89 */     this.active_changed = false;
/*     */   }
/*     */   
/*     */   public void createGraph(Graphics paramGraphics)
/*     */   {
/*  94 */     if ((this.chart_img == null) || 
/*  95 */       (!this.prev_size.equals(getSize())) || 
/*  96 */       (this.redraw_backing) || (this.active_changed))
/*     */     {
/*  98 */       if (this.chart_img == null) {
/*  99 */         realloc();
/*     */       }
/* 101 */       this.chart_img = createImage(getSize().width, getSize().height);
/* 102 */       this.chart_gfx = this.chart_img.getGraphics();
/* 103 */       this.chart_gfx.setFont(getFont());
/*     */       
/* 105 */       this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.gray, null);
/*     */       
/* 107 */       Insets localInsets = getInsets();
/*     */       int i;
/*     */       int j;
/* 110 */       int k; int m; int n; int i1; if (this.show_numbers) {
/* 111 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 112 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 113 */         k = localInsets.left + i + 4;
/* 114 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 115 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 116 */         n = m + 4;
/* 117 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 120 */         i = 1;
/* 121 */         j = 0;
/* 122 */         k = 6;
/* 123 */         m = 1;
/* 124 */         n = 6;
/* 125 */         i1 = 0;
/*     */       }
/*     */       
/* 128 */       int i2 = k + 1;
/* 129 */       int i3 = localInsets.top + 1 + m;
/* 130 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 131 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 133 */       if ((i4 != this.chart_width) || (i5 != this.chart_height)) {
/* 134 */         realloc();
/*     */       }
/* 136 */       if (this.is_active) {
/* 137 */         this.chart_gfx.setColor(this.active_graph_color);
/*     */       } else {
/* 139 */         this.chart_gfx.setColor(this.graph_color);
/*     */       }
/*     */       
/* 142 */       double d = i5 / (this.y_end - this.y_start);
/*     */       int i8;
/*     */       int i6;
/* 145 */       if (this.graph_filled) {
/* 146 */         i8 = (int)(-this.y_start * d);
/* 147 */         if (i8 < 0)
/* 148 */           i8 = 0;
/* 149 */         if (i8 > i5) {
/* 150 */           i8 = i5;
/*     */         }
/* 152 */         for (int i9 = 0; i9 < i4; i9++) {
/* 153 */           i6 = (int)((this.graph_values[i9] - this.y_start) * d);
/* 154 */           this.chart_gfx.drawLine(i9 + i2, i3 + i5 - i8, i9 + i2, i3 + i5 - i6);
/*     */         }
/*     */       }
/*     */       else {
/* 158 */         int i7 = (int)((this.graph_values[0] - this.y_start) * d);
/* 159 */         for (i8 = 0; i8 < i4 - 1; i8++) {
/* 160 */           i6 = i7;
/* 161 */           i7 = (int)((this.graph_values[(i8 + 1)] - this.y_start) * d);
/* 162 */           this.chart_gfx.drawLine(i8 + i2, i3 + i5 - i6, i8 + i2 + 1, i3 + i5 - i7);
/*     */         }
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
/*     */   public void createBacking(Graphics paramGraphics)
/*     */   {
/* 180 */     if ((this.graphpaper_img == null) || 
/* 181 */       (!this.prev_size.equals(getSize())) || 
/* 182 */       (this.redraw_backing))
/*     */     {
/*     */ 
/*     */ 
/* 186 */       this.graphpaper_img = createImage(getSize().width, getSize().height);
/* 187 */       this.graphpaper_gfx = this.graphpaper_img.getGraphics();
/* 188 */       this.graphpaper_gfx.setFont(getFont());
/*     */       
/*     */ 
/* 191 */       Insets localInsets = getInsets();
/*     */       int i;
/* 193 */       int j; int k; int m; int n; int i1; if (this.show_numbers) {
/* 194 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 195 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 196 */         k = localInsets.left + i + 4;
/* 197 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 198 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 199 */         n = m + 4;
/* 200 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 203 */         i = 1;
/* 204 */         j = 0;
/* 205 */         k = 6;
/* 206 */         m = 1;
/* 207 */         n = 6;
/* 208 */         i1 = 0;
/*     */       }
/*     */       
/* 211 */       int i2 = k + 1;
/* 212 */       int i3 = localInsets.top + 1 + m;
/* 213 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 214 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 216 */       this.graphpaper_gfx.setColor(Color.lightGray);
/* 217 */       this.graphpaper_gfx.fillRect(0, 0, getSize().width, getSize().height);
/*     */       
/* 219 */       this.graphpaper_gfx.setColor(getForeground());
/* 220 */       this.graphpaper_gfx.drawRect(i2 - 1, i3 - 1, i4 + 2, i5 + 2);
/*     */       
/* 222 */       this.graphpaper_gfx.setColor(this.bg_color);
/* 223 */       this.graphpaper_gfx.fillRect(i2, i3, i4, i5);
/*     */       
/*     */ 
/* 226 */       double d1 = this.x_start;
/*     */       String str;
/*     */       int i6;
/* 229 */       for (int i7 = 0; i7 < this.x_divisions; i7++) {
/* 230 */         int i8 = (int)(i2 + i4 * i7 / (this.x_divisions - 1.0D));
/*     */         
/* 232 */         if (this.show_numbers) {
/* 233 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 234 */           str = new Double(d1).toString();
/* 235 */           if (d1 < 0.0D) {
/* 236 */             i6 = Math.min(5, str.length());
/*     */           } else
/* 238 */             i6 = Math.min(4, str.length());
/* 239 */           str = str.substring(0, i6);
/*     */           
/*     */ 
/* 242 */           this.graphpaper_gfx.drawString(str, i8 - j * str.length() / 2, i3 + i5 + i1 + 2);
/* 243 */           d1 += (this.x_end - this.x_start) / (this.x_divisions - 1.0D);
/*     */         }
/*     */         
/* 246 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 247 */         this.graphpaper_gfx.drawLine(i8, i3, i8, i3 + i5);
/*     */       }
/*     */       
/*     */ 
/* 251 */       double d2 = this.y_end;
/* 252 */       for (int i9 = 0; i9 < this.y_divisions; i9++) {
/* 253 */         i10 = (int)(i3 + i5 * i9 / (this.y_divisions - 1.0D));
/*     */         
/* 255 */         if (this.show_numbers) {
/* 256 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 257 */           str = new Double(d2).toString();
/* 258 */           i6 = Math.min(4, str.length());
/* 259 */           str = str.substring(0, i6);
/*     */           
/* 261 */           this.graphpaper_gfx.drawString(str, localInsets.left + 2, i10 + 2);
/* 262 */           d2 -= (this.y_end - this.y_start) / (this.y_divisions - 1.0D);
/*     */         }
/*     */         
/* 265 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 266 */         this.graphpaper_gfx.drawLine(i2, i10, i2 + i4, i10);
/*     */       }
/*     */       
/*     */ 
/* 270 */       this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 271 */       int i10 = (int)(-this.x_start / (this.x_end - this.x_start) * i4) + i2;
/* 272 */       if ((i10 >= i2) && (i10 <= i2 + i4)) {
/* 273 */         this.graphpaper_gfx.drawLine(i10, i3, i10, i3 + i5);
/*     */       }
/* 275 */       int i11 = (int)(i3 + i5 - -this.y_start / (this.y_end - this.y_start) * i5);
/* 276 */       if ((i11 >= i3) && (i11 <= i3 + i5)) {
/* 277 */         this.graphpaper_gfx.drawLine(i2, i11, i2 + i4, i11);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void changeValues(int paramInt1, int paramInt2, double[] paramArrayOfDouble) {
/* 283 */     if ((this.graphpaper_gfx == null) || (this.chart_gfx == null)) {
/* 284 */       System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
/* 285 */       return;
/*     */     }
/*     */     
/*     */ 
/* 289 */     int i = paramInt1;
/* 290 */     if (i < 0) {
/* 291 */       i = 0;
/*     */     }
/* 293 */     int j = paramInt1 + paramInt2;
/* 294 */     if (j > this.chart_width) {
/* 295 */       j = this.chart_width;
/*     */     }
/* 297 */     this.chart_gfx.drawImage(this.graphpaper_img, 
/* 298 */       i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, 
/* 299 */       i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, 
/* 300 */       null);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 307 */     if (this.is_active) {
/* 308 */       this.chart_gfx.setColor(this.active_graph_color);
/*     */     } else {
/* 310 */       this.chart_gfx.setColor(this.graph_color);
/*     */     }
/*     */     
/* 313 */     double d = this.chart_height / (this.y_end - this.y_start);
/*     */     try { int n;
/*     */       int i2;
/*     */       int i1;
/* 317 */       int k; if (this.graph_filled) {
/* 318 */         n = (int)(-this.y_start * d);
/* 319 */         if (n < 0)
/* 320 */           n = 0;
/* 321 */         if (n > this.chart_height) {
/* 322 */           n = this.chart_height;
/*     */         }
/*     */         
/* 325 */         for (i2 = 0; i2 < paramInt2; i2++) {
/* 326 */           this.graph_values[(i2 + paramInt1)] = paramArrayOfDouble[i2];
/* 327 */           i1 = i2 + paramInt1;
/* 328 */           this.graph_values[i1] = paramArrayOfDouble[i2];
/* 329 */           if (this.graph_values[i1] > this.y_end)
/* 330 */             this.graph_values[i1] = this.y_end;
/* 331 */           if (this.graph_values[i1] < this.y_start)
/* 332 */             this.graph_values[i1] = this.y_start;
/* 333 */           k = (int)((this.graph_values[i1] - this.y_start) * d);
/*     */           
/* 335 */           this.chart_gfx.drawLine(i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - n, 
/* 336 */             i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - k);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 341 */         for (i1 = 0; i1 < paramInt2; i1++) {
/* 342 */           n = i1 + paramInt1;
/* 343 */           this.graph_values[n] = paramArrayOfDouble[i1];
/* 344 */           if (this.graph_values[n] > this.y_end)
/* 345 */             this.graph_values[n] = this.y_end;
/* 346 */           if (this.graph_values[n] < this.y_start) {
/* 347 */             this.graph_values[n] = this.y_start;
/*     */           }
/*     */         }
/* 350 */         i2 = paramInt1 + paramInt2 - 1;
/* 351 */         int m = (int)((this.graph_values[paramInt1] - this.y_start) * d);
/* 352 */         if (paramInt1 == i2) {
/* 353 */           this.chart_gfx.drawLine(paramInt1 + this.chart_x, this.chart_y + this.chart_height - m, 
/* 354 */             paramInt1 + this.chart_x, this.chart_y + this.chart_height - m);
/*     */         }
/*     */         else {
/* 357 */           for (int i3 = paramInt1; i3 < i2; i3++) {
/* 358 */             k = m;
/* 359 */             m = (int)((this.graph_values[(i3 + 1)] - this.y_start) * d);
/* 360 */             this.chart_gfx.drawLine(i3 + this.chart_x, this.chart_y + this.chart_height - k, 
/* 361 */               i3 + this.chart_x + 1, this.chart_y + this.chart_height - m);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 367 */       System.err.println("Internal error: Function Change Event arry out of bounds.");
/*     */     }
/*     */     
/* 370 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void realloc()
/*     */   {
/* 380 */     Insets localInsets = getInsets();
/*     */     int i;
/* 382 */     int j; int k; int m; if ((this.graphpaper_gfx != null) && (this.show_numbers)) {
/* 383 */       i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 384 */       j = localInsets.left + i + 4;
/*     */       
/* 386 */       k = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 387 */         this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 388 */       m = k + 4;
/*     */     }
/*     */     else {
/* 391 */       i = 1;
/* 392 */       j = 6;
/*     */       
/* 394 */       k = 1;
/* 395 */       m = 6;
/*     */     }
/*     */     
/* 398 */     this.chart_x = (j + 1);
/* 399 */     this.chart_y = (localInsets.top + 1 + k);
/* 400 */     this.chart_width = (getSize().width - localInsets.right - j - 2 - i / 2);
/* 401 */     this.chart_height = (getSize().height - (localInsets.top + localInsets.bottom + m + 2) - k);
/*     */     
/*     */ 
/* 404 */     if (this.chart_width > 0) {
/* 405 */       this.graph_values = new double[this.chart_width];
/*     */       
/* 407 */       this.function_controller.resize(this.chart_width, this.chart_height);
/* 408 */       Nyquist.__instance.fixApplet();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setNumSamples(int paramInt)
/*     */   {
/* 414 */     this.num_samples = paramInt;
/* 415 */     repaint();
/*     */   }
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent)
/*     */   {
/* 420 */     Nyquist.__instance.resetPositions();
/*     */     
/* 422 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 423 */     if ((i < 0) || (i >= this.chart_width)) {
/* 424 */       return;
/*     */     }
/* 426 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 427 */     if (j < 0)
/* 428 */       j = 0;
/* 429 */     if (j > this.chart_height) {
/* 430 */       j = this.chart_height;
/*     */     }
/* 432 */     this.function_controller.mouseStart(i, j);
/* 433 */     this.function_controller.mouseStop();
/*     */   }
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent)
/*     */   {
/* 438 */     this.is_active = true;
/* 439 */     this.active_changed = true;
/* 440 */     repaint();
/*     */   }
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent)
/*     */   {
/* 445 */     this.is_active = false;
/* 446 */     this.active_changed = true;
/* 447 */     repaint();
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 452 */     Nyquist.__instance.resetPositions();
/*     */     
/* 454 */     addMouseMotionListener(this);
/*     */     
/* 456 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 457 */     if ((i < 0) || (i >= this.chart_width)) {
/* 458 */       return;
/*     */     }
/* 460 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 461 */     if (j < 0)
/* 462 */       j = 0;
/* 463 */     if (j > this.chart_height) {
/* 464 */       j = this.chart_height;
/*     */     }
/* 466 */     this.function_controller.mouseStart(i, j);
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 471 */     this.function_controller.mouseStop();
/*     */     
/* 473 */     removeMouseMotionListener(this);
/*     */     
/* 475 */     if (!this.graph_filled)
/* 476 */       this.active_changed = true;
/* 477 */     repaint();
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/* 482 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 483 */     if (i < 0) {
/* 484 */       i = 0;
/* 485 */     } else if (i >= this.chart_width - 1) {
/* 486 */       i = this.chart_width - 2;
/*     */     }
/* 488 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 489 */     if (j < 0)
/* 490 */       j = 0;
/* 491 */     if (j > this.chart_height) {
/* 492 */       j = this.chart_height;
/*     */     }
/* 494 */     this.function_controller.mouseDrag(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent paramMouseEvent)
/*     */   {
/* 501 */     mouseDragged(paramMouseEvent);
/*     */   }
/*     */   
/*     */   public void componentResized(ComponentEvent paramComponentEvent)
/*     */   {
/* 506 */     realloc();
/*     */     
/* 508 */     this.function_controller.resize(this.chart_width, this.chart_height);
/*     */   }
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/* 517 */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) { this.change_support.addPropertyChangeListener(paramPropertyChangeListener); }
/*     */   
/*     */ 
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 522 */     this.change_support.removePropertyChangeListener(paramPropertyChangeListener);
/*     */   }
/*     */   
/* 525 */   public Dimension getMinimumSize() { return new Dimension(150, 150); }
/* 526 */   public Dimension getPreferredSize() { return new Dimension(600, 300); }
/*     */   
/* 528 */   public int getChartHeight() { return this.chart_height; }
/* 529 */   public int getChartWidth() { return this.chart_width; }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/* 533 */     super.setEnabled(paramBoolean);
/*     */     
/* 535 */     if (paramBoolean == false) {
/* 536 */       removeMouseListener(this);
/* 537 */       this.is_active = false;
/* 538 */       this.active_changed = true;
/* 539 */       repaint();
/*     */     }
/*     */     else {
/* 542 */       addMouseListener(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String paramString)
/*     */   {
/* 551 */     this.border_.setTitle(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 557 */     return this.border_.getTitle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getGraphFilled()
/*     */   {
/* 564 */     return this.graph_filled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphFilled(boolean paramBoolean)
/*     */   {
/* 572 */     this.redraw_backing = true;
/* 573 */     this.graph_filled = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setForeground(Color paramColor)
/*     */   {
/* 584 */     this.redraw_backing = true;
/* 585 */     super.setForeground(paramColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(Font paramFont)
/*     */   {
/* 596 */     this.redraw_backing = true;
/* 597 */     super.setFont(paramFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphBackgroundColor()
/*     */   {
/* 606 */     return this.bg_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphBackgroundColor(Color paramColor)
/*     */   {
/* 615 */     this.redraw_backing = true;
/* 616 */     this.bg_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphColor()
/*     */   {
/* 625 */     return this.graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphColor(Color paramColor)
/*     */   {
/* 634 */     this.redraw_backing = true;
/* 635 */     this.graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getActiveGraphColor()
/*     */   {
/* 644 */     return this.active_graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActiveGraphColor(Color paramColor)
/*     */   {
/* 653 */     this.redraw_backing = true;
/* 654 */     this.active_graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getPrimaryChartColor()
/*     */   {
/* 662 */     return this.primary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrimaryChartColor(Color paramColor)
/*     */   {
/* 670 */     this.redraw_backing = true;
/* 671 */     this.primary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getSecondaryChartColor()
/*     */   {
/* 679 */     return this.secondary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSecondaryChartColor(Color paramColor)
/*     */   {
/* 687 */     this.redraw_backing = true;
/* 688 */     this.secondary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getXDivisions()
/*     */   {
/* 696 */     return this.x_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXDivisions(int paramInt)
/*     */   {
/* 704 */     this.redraw_backing = true;
/* 705 */     this.x_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getYDivisions()
/*     */   {
/* 713 */     return this.y_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYDivisions(int paramInt)
/*     */   {
/* 721 */     this.redraw_backing = true;
/* 722 */     this.y_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXStart()
/*     */   {
/* 730 */     return this.x_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXStart(double paramDouble)
/*     */   {
/* 738 */     this.redraw_backing = true;
/* 739 */     this.x_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXEnd()
/*     */   {
/* 747 */     return this.x_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXEnd(double paramDouble)
/*     */   {
/* 755 */     this.redraw_backing = true;
/* 756 */     this.x_end = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYStart()
/*     */   {
/* 764 */     return this.y_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYStart(double paramDouble)
/*     */   {
/* 772 */     this.redraw_backing = true;
/* 773 */     this.y_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYEnd()
/*     */   {
/* 781 */     return this.y_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYEnd(double paramDouble)
/*     */   {
/* 789 */     this.redraw_backing = true;
/* 790 */     this.y_end = paramDouble;
/*     */   }
/*     */   
/*     */   public void setMarkers(boolean paramBoolean)
/*     */   {
/* 795 */     this.show_markers = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/* 799 */   protected boolean graph_filled = false;
/* 800 */   protected boolean show_numbers = true;
/* 801 */   protected boolean show_markers = false;
/* 802 */   protected Color bg_color = Color.white;
/* 803 */   protected Color graph_color = Color.darkGray;
/* 804 */   protected Color active_graph_color = Color.blue;
/* 805 */   protected Color primary_cht_color = Color.black;
/* 806 */   protected Color secondary_cht_color = Color.lightGray;
/* 807 */   protected int x_divisions = 9;
/* 808 */   protected int y_divisions = 5;
/*     */   protected double x_start;
/* 810 */   protected double x_end = 1.0D;
/* 811 */   protected double y_start = -1.2D;
/* 812 */   protected double y_end = 1.2D;
/* 813 */   protected int num_samples = 32;
/*     */   
/*     */   protected transient double[] graph_values;
/*     */   protected transient TitledBorder border_;
/*     */   protected transient Dimension prev_size;
/*     */   protected transient Image graphpaper_img;
/*     */   protected transient Graphics graphpaper_gfx;
/*     */   protected transient Image chart_img;
/*     */   protected transient Graphics chart_gfx;
/* 822 */   protected transient boolean redraw_backing = true;
/*     */   protected transient boolean is_active;
/*     */   protected transient boolean active_changed;
/*     */   protected transient int chart_x;
/* 826 */   protected transient int chart_y; protected transient int chart_width; protected transient int chart_height; protected transient PropertyChangeSupport change_support = new PropertyChangeSupport(this);
/*     */   protected transient CTFunctionController function_controller;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/CTFunctionWindow.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */