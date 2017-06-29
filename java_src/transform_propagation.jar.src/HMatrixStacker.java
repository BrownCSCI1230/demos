/*     */ import java.applet.Applet;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HMatrixStacker
/*     */   extends JPanel
/*     */   implements HDrawMatrixListener, ComponentListener
/*     */ {
/*     */   private Stack _matrixStack;
/*     */   private boolean _componentResized;
/*     */   private int _oldWidth;
/*     */   private int _oldHeight;
/*     */   private int _top;
/*     */   private Applet _applet;
/*     */   private boolean _sound;
/*     */   private boolean _pushing;
/*  29 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */   public HMatrixStacker(Applet paramApplet) {
/*  32 */     this._applet = paramApplet;
/*     */     
/*  34 */     this._sound = false;
/*  35 */     this._pushing = false;
/*     */     
/*  37 */     setBackground(BACKGROUND);
/*     */     
/*  39 */     setLayout(null);
/*     */     
/*  41 */     addComponentListener(this);
/*     */     
/*  43 */     this._componentResized = true;
/*  44 */     this._matrixStack = new Stack();
/*  45 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*  46 */     setPreferredSize(new Dimension(140, 50));
/*  47 */     setMaximumSize(new Dimension(140, 32767));
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
/*     */ 
/*     */   public void startMatrixDisplay()
/*     */   {
/*  64 */     removeAll();
/*  65 */     this._matrixStack.removeAllElements();
/*  66 */     repaint();
/*  67 */     paintComponents(getGraphics());
/*     */   }
/*     */   
/*     */   public void endMatrixDisplay() {
/*  71 */     removeAll();
/*  72 */     this._matrixStack.removeAllElements();
/*  73 */     repaint();
/*  74 */     paintComponents(getGraphics());
/*     */   }
/*     */   
/*     */   public void pushMatrix(HMatrixObject paramHMatrixObject) {
/*  78 */     this._pushing = true;
/*  79 */     HMatrixComponent localHMatrixComponent = new HMatrixComponent(paramHMatrixObject.getMatrix(), 
/*  80 */       BACKGROUND, 
/*  81 */       paramHMatrixObject.getLabel(), 
/*  82 */       paramHMatrixObject.getValues());
/*  83 */     JPanel localJPanel = new JPanel();
/*  84 */     localJPanel.setSize(new Dimension(137, 90));
/*  85 */     localJPanel.setBackground(BACKGROUND);
/*  86 */     localJPanel.add(localHMatrixComponent);
/*  87 */     localJPanel.setLayout(null);
/*  88 */     localJPanel.setLocation(65336, -120);
/*  89 */     add(localJPanel);
/*  90 */     localJPanel.update(localJPanel.getGraphics());
/*  91 */     localJPanel.paintComponents(localJPanel.getGraphics());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  96 */     this._top = (getSize().height - (this._matrixStack.size() + 1) * 90 - 2);
/*     */     
/*  98 */     if (this._sound) {
/*  99 */       this._applet.play(this._applet.getCodeBase(), "zipboingNew");
/*     */     }
/*     */     
/*     */ 
/* 103 */     double d = 6.0D;
/* 104 */     for (int i = -90; i < this._top; i += 5)
/*     */     {
/* 106 */       localJPanel.setLocation(2, i);
/* 107 */       repaint();
/*     */       try {
/* 109 */         Thread.sleep((int)d);
/*     */       } catch (InterruptedException localInterruptedException) {
/* 111 */         Thread.currentThread().interrupt();
/* 112 */         Thread.currentThread().stop();
/*     */       }
/*     */     }
/*     */     
/* 116 */     localJPanel.setLocation(2, getSize().height - (this._matrixStack.size() + 1) * 90 - 2);
/* 117 */     repaint();
/* 118 */     this._matrixStack.push(localJPanel);
/* 119 */     this._pushing = false;
/*     */   }
/*     */   
/*     */   public void popMatrix() {
/* 123 */     JPanel localJPanel = (JPanel)this._matrixStack.pop();
/*     */     
/* 125 */     this._top = (getSize().height - (this._matrixStack.size() + 1) * 90 - 2);
/*     */     
/* 127 */     if (this._sound) {
/* 128 */       this._applet.play(this._applet.getCodeBase(), "pop3");
/*     */     }
/* 130 */     for (int i = this._top; i > -100; i -= 5) {
/* 131 */       localJPanel.setLocation(2, i);
/* 132 */       repaint();
/*     */       try {
/* 134 */         Thread.sleep(6L);
/*     */       } catch (InterruptedException localInterruptedException) {
/* 136 */         Thread.currentThread().interrupt();
/* 137 */         Thread.currentThread().stop();
/*     */       }
/*     */     }
/* 140 */     remove(localJPanel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void componentResized(ComponentEvent paramComponentEvent)
/*     */   {
/* 147 */     if (this._matrixStack.size() > 0)
/*     */     {
/* 149 */       for (int i = 0; i < this._matrixStack.size(); i++) {
/* 150 */         JPanel localJPanel = (JPanel)this._matrixStack.elementAt(i);
/* 151 */         localJPanel.setLocation(2, getSize().height - (i + 1) * 90);
/*     */       }
/* 153 */       repaint();
/* 154 */       paintComponents(getGraphics());
/*     */     }
/*     */     
/* 157 */     if (!this._pushing)
/*     */     {
/*     */ 
/*     */ 
/* 161 */       this._top = (getSize().height - this._matrixStack.size() * 90 - 2);
/*     */     } else
/* 163 */       this._top = (getSize().height - (this._matrixStack.size() + 1) * 90 - 2);
/*     */   }
/*     */   
/*     */   public void setSound(boolean paramBoolean) {
/* 167 */     this._sound = paramBoolean;
/*     */   }
/*     */   
/*     */   public void clearMatrices() {}
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void drawMatrix(HDrawMatrixEvent paramHDrawMatrixEvent) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HMatrixStacker.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */