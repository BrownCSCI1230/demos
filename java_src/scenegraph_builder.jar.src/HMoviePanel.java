/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class HMoviePanel extends javax.swing.JPanel implements java.awt.event.ActionListener { private javax.swing.ImageIcon[] _images;
/*     */   private javax.swing.JButton _next;
/*     */   private javax.swing.JButton _prev;
/*     */   private javax.swing.JButton _play;
/*     */   private javax.swing.JButton _stop;
/*     */   private javax.swing.JLabel _picture;
/*     */   private int _numImages;
/*     */   private int _currentIndex;
/*     */   private javax.swing.Timer _timer;
/*     */   private javax.swing.JPanel _imagePanel;
/*  13 */   public static final java.awt.Color BACKGROUND = new java.awt.Color(205, 190, 180);
/*     */   
/*     */   public HMoviePanel() {
/*  16 */     setBackground(BACKGROUND);
/*  17 */     setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
/*     */     
/*  19 */     this._imagePanel = new javax.swing.JPanel();
/*  20 */     this._imagePanel.setBackground(BACKGROUND);
/*     */     
/*  22 */     this._images = new javax.swing.ImageIcon[1];
/*  23 */     this._images[0] = new javax.swing.ImageIcon();
/*     */     
/*     */ 
/*  26 */     this._currentIndex = 0;
/*  27 */     this._picture = new javax.swing.JLabel();
/*     */     
/*  29 */     this._picture.setBackground(java.awt.Color.red);
/*  30 */     this._picture.setAlignmentX(0.5F);
/*     */     
/*  32 */     this._play = new javax.swing.JButton("play");
/*  33 */     this._play.addActionListener(this);
/*  34 */     this._play.setBackground(BACKGROUND);
/*  35 */     this._stop = new javax.swing.JButton("stop");
/*  36 */     this._stop.addActionListener(this);
/*  37 */     this._stop.setBackground(BACKGROUND);
/*  38 */     this._next = new javax.swing.JButton("next");
/*  39 */     this._next.addActionListener(this);
/*  40 */     this._next.setBackground(BACKGROUND);
/*  41 */     this._prev = new javax.swing.JButton("prev");
/*  42 */     this._prev.addActionListener(this);
/*  43 */     this._prev.setBackground(BACKGROUND);
/*     */     
/*  45 */     this._timer = new javax.swing.Timer(500, this);
/*     */     
/*  47 */     javax.swing.JPanel localJPanel = new javax.swing.JPanel(false);
/*  48 */     localJPanel.setLayout(new java.awt.GridLayout(1, 4));
/*  49 */     localJPanel.setPreferredSize(new java.awt.Dimension(300, 25));
/*  50 */     localJPanel.setMaximumSize(new java.awt.Dimension(300, 25));
/*     */     
/*  52 */     localJPanel.add(this._prev);
/*  53 */     localJPanel.add(this._play);
/*  54 */     localJPanel.add(this._stop);
/*  55 */     localJPanel.add(this._next);
/*     */     
/*  57 */     setLayout(new javax.swing.BoxLayout(this, 1));
/*  58 */     this._imagePanel.setLayout(new javax.swing.BoxLayout(this._imagePanel, 1));
/*  59 */     this._imagePanel.add(this._picture);
/*  60 */     add(this._imagePanel);
/*  61 */     add(localJPanel);
/*     */   }
/*     */   
/*     */   public void changeMovie(java.awt.Image[] paramArrayOfImage, int paramInt) {
/*  65 */     this._images = new javax.swing.ImageIcon[paramInt];
/*  66 */     for (int i = 0; i < paramInt; i++) {
/*  67 */       this._images[i] = new javax.swing.ImageIcon(paramArrayOfImage[i]);
/*     */     }
/*  69 */     this._currentIndex = 0;
/*  70 */     this._picture.setIcon(this._images[this._currentIndex]);
/*  71 */     this._picture.revalidate();
/*  72 */     this._numImages = paramInt;
/*  73 */     revalidate();
/*     */   }
/*     */   
/*     */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*     */   {
/*  78 */     if (paramActionEvent.getSource() == this._next) {
/*  79 */       if (this._currentIndex < this._numImages - 1) {
/*  80 */         this._currentIndex += 1;
/*     */       } else {
/*  82 */         this._currentIndex = 0;
/*     */       }
/*     */     }
/*  85 */     if (paramActionEvent.getSource() == this._prev) {
/*  86 */       if (this._currentIndex > 0) {
/*  87 */         this._currentIndex -= 1;
/*     */       } else {
/*  89 */         this._currentIndex = (this._numImages - 1);
/*     */       }
/*     */     }
/*  92 */     if (paramActionEvent.getSource() == this._play) {
/*  93 */       this._timer.start();
/*     */     }
/*     */     
/*  96 */     if (paramActionEvent.getSource() == this._stop) {
/*  97 */       this._timer.stop();
/*     */     }
/*     */     
/* 100 */     if (paramActionEvent.getSource() == this._timer) {
/* 101 */       if (this._currentIndex < this._numImages - 1) {
/* 102 */         this._currentIndex += 1;
/*     */       } else
/* 104 */         this._currentIndex = 0;
/*     */     }
/* 106 */     this._picture.setIcon(this._images[this._currentIndex]);
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HMoviePanel.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */