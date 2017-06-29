/*     */ package Bezier;
/*     */ 
/*     */ import java.awt.Choice;
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class Bezier extends java.applet.Applet implements Runnable
/*     */ {
/*     */   protected Choice m_mode;
/*     */   protected Choice m_splineType;
/*  11 */   protected String m_currSpline = new String("Bezier");
/*  12 */   protected java.awt.Checkbox m_prefTangents = new java.awt.Checkbox("Tangents");
/*  13 */   protected java.awt.Checkbox m_prefLabels = new java.awt.Checkbox("Labels");
/*  14 */   protected java.awt.Checkbox m_prefColor = new java.awt.Checkbox("Color");
/*  15 */   protected java.awt.Checkbox m_prefTPoints = new java.awt.Checkbox("T-points");
/*  16 */   protected java.awt.Checkbox m_prefHints = new java.awt.Checkbox("Hints");
/*     */   protected Choice m_tValue;
/*  18 */   protected EquationCanvas m_qX = new EquationCanvas();
/*  19 */   protected EquationCanvas m_qY = new EquationCanvas();
/*  20 */   protected java.awt.GridBagLayout m_layout = new java.awt.GridBagLayout();
/*  21 */   protected java.awt.GridBagConstraints m_gbc = new java.awt.GridBagConstraints();
/*  22 */   protected java.awt.Label m_status = new java.awt.Label();
/*     */   protected SplineCanvas m_canvas;
/*  24 */   protected java.awt.Button m_showMe = new java.awt.Button("Show Mathematics");
/*  25 */   protected String m_currMode = new String("Play Area");
/*     */   protected Spline m_answer;
/*  27 */   protected double m_t = 0.05D;
/*  28 */   protected boolean m_ansShown = false;
/*  29 */   protected int m_fact = -1;
/*     */   
/*  31 */   protected BasisFnc m_simBas1 = new SimpleBasis1();
/*  32 */   protected BasisFnc m_simBas2 = new SimpleBasis2();
/*  33 */   protected BasisFnc m_simBas3 = new SimpleBasis3();
/*  34 */   protected BasisFnc m_simBas4 = new SimpleBasis4();
/*  35 */   protected Spline m_simSpline = new SimpleSpline(this.m_qX, this.m_qY, this.m_simBas1, this.m_simBas2, this.m_simBas3, this.m_simBas4);
/*     */   
/*  37 */   protected BasisFnc m_bas1 = new BasisBezier1();
/*  38 */   protected BasisFnc m_bas2 = new BasisBezier2();
/*  39 */   protected BasisFnc m_bas3 = new BasisBezier3();
/*  40 */   protected BasisFnc m_bas4 = new BasisBezier4();
/*  41 */   protected Spline m_bezSpline = new BezierSpline(this.m_qX, this.m_qY, this.m_bas1, this.m_bas2, this.m_bas3, this.m_bas4);
/*  42 */   protected BasisPanel m_basis = new BasisPanel(this.m_bas1, this.m_bas2, this.m_bas3, this.m_bas4);
/*     */   
/*  44 */   protected Spline m_spline = this.m_bezSpline;
/*     */   
/*     */   public void init()
/*     */   {
/*  48 */     this.m_mode = new Choice();
/*  49 */     this.m_mode.addItem("Tutorial");
/*  50 */     this.m_mode.addItem("Play Area");
/*  51 */     this.m_mode.addItem("Exercises");
/*     */     
/*  53 */     this.m_splineType = new Choice();
/*  54 */     this.m_splineType.addItem("Bezier");
/*  55 */     this.m_splineType.addItem("Simple");
/*     */     
/*  57 */     this.m_prefTangents.setState(true);
/*  58 */     this.m_prefLabels.setState(true);
/*  59 */     this.m_prefColor.setState(true);
/*  60 */     this.m_prefTPoints.setState(true);
/*     */     
/*  62 */     this.m_tValue = new Choice();
/*  63 */     this.m_tValue.addItem("0.01");
/*  64 */     this.m_tValue.addItem("0.05");
/*  65 */     this.m_tValue.addItem("0.10");
/*  66 */     this.m_tValue.addItem("0.20");
/*  67 */     this.m_tValue.addItem("0.25");
/*  68 */     this.m_tValue.addItem("0.50");
/*     */     
/*  70 */     setLayout(this.m_layout);
/*     */     
/*  72 */     this.m_gbc.gridx = 0;
/*  73 */     this.m_gbc.gridy = 0;
/*  74 */     this.m_gbc.gridwidth = 1;
/*  75 */     this.m_gbc.gridheight = 1;
/*  76 */     this.m_gbc.weightx = 0.0D;
/*  77 */     this.m_gbc.weighty = 0.0D;
/*  78 */     this.m_gbc.fill = 2;
/*  79 */     this.m_gbc.anchor = 11;
/*  80 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/*  81 */     this.m_layout.setConstraints(this.m_mode, this.m_gbc);
/*  82 */     this.m_mode.select("Play Area");
/*  83 */     add(this.m_mode);
/*     */     
/*  85 */     this.m_gbc.gridx = 1;
/*  86 */     this.m_gbc.gridy = 0;
/*  87 */     this.m_gbc.gridwidth = 1;
/*  88 */     this.m_gbc.gridheight = 1;
/*  89 */     this.m_gbc.weightx = 0.0D;
/*  90 */     this.m_gbc.weighty = 0.0D;
/*  91 */     this.m_gbc.fill = 2;
/*  92 */     this.m_gbc.anchor = 11;
/*  93 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/*  94 */     this.m_layout.setConstraints(this.m_tValue, this.m_gbc);
/*  95 */     this.m_tValue.select("0.05");
/*  96 */     add(this.m_tValue);
/*     */     
/*  98 */     this.m_gbc.gridx = 3;
/*  99 */     this.m_gbc.gridy = 0;
/* 100 */     this.m_gbc.gridwidth = 1;
/* 101 */     this.m_gbc.gridheight = 1;
/* 102 */     this.m_gbc.weightx = 0.0D;
/* 103 */     this.m_gbc.weighty = 0.0D;
/* 104 */     this.m_gbc.fill = 2;
/* 105 */     this.m_gbc.anchor = 11;
/* 106 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 107 */     this.m_layout.setConstraints(this.m_prefTangents, this.m_gbc);
/* 108 */     add(this.m_prefTangents);
/*     */     
/* 110 */     this.m_gbc.gridx = 4;
/* 111 */     this.m_gbc.gridy = 0;
/* 112 */     this.m_gbc.gridwidth = 1;
/* 113 */     this.m_gbc.gridheight = 1;
/* 114 */     this.m_gbc.weightx = 0.0D;
/* 115 */     this.m_gbc.weighty = 0.0D;
/* 116 */     this.m_gbc.fill = 2;
/* 117 */     this.m_gbc.anchor = 11;
/* 118 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 119 */     this.m_layout.setConstraints(this.m_prefLabels, this.m_gbc);
/* 120 */     add(this.m_prefLabels);
/*     */     
/* 122 */     this.m_gbc.gridx = 5;
/* 123 */     this.m_gbc.gridy = 0;
/* 124 */     this.m_gbc.gridwidth = 1;
/* 125 */     this.m_gbc.gridheight = 1;
/* 126 */     this.m_gbc.weightx = 0.0D;
/* 127 */     this.m_gbc.weighty = 0.0D;
/* 128 */     this.m_gbc.fill = 2;
/* 129 */     this.m_gbc.anchor = 11;
/* 130 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 131 */     this.m_layout.setConstraints(this.m_prefColor, this.m_gbc);
/* 132 */     add(this.m_prefColor);
/*     */     
/* 134 */     this.m_gbc.gridx = 6;
/* 135 */     this.m_gbc.gridy = 0;
/* 136 */     this.m_gbc.gridwidth = 1;
/* 137 */     this.m_gbc.gridheight = 1;
/* 138 */     this.m_gbc.weightx = 0.0D;
/* 139 */     this.m_gbc.weighty = 0.0D;
/* 140 */     this.m_gbc.fill = 2;
/* 141 */     this.m_gbc.anchor = 11;
/* 142 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 143 */     this.m_layout.setConstraints(this.m_prefTPoints, this.m_gbc);
/* 144 */     add(this.m_prefTPoints);
/*     */     
/* 146 */     this.m_prefHints.disable();
/* 147 */     this.m_gbc.gridx = 7;
/* 148 */     this.m_gbc.gridy = 0;
/* 149 */     this.m_gbc.gridwidth = 1;
/* 150 */     this.m_gbc.gridheight = 1;
/* 151 */     this.m_gbc.weightx = 0.0D;
/* 152 */     this.m_gbc.weighty = 0.0D;
/* 153 */     this.m_gbc.fill = 2;
/* 154 */     this.m_gbc.anchor = 11;
/* 155 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 156 */     this.m_layout.setConstraints(this.m_prefHints, this.m_gbc);
/* 157 */     add(this.m_prefHints);
/*     */     
/* 159 */     this.m_gbc.gridx = 2;
/* 160 */     this.m_gbc.gridy = 0;
/* 161 */     this.m_gbc.gridwidth = 1;
/* 162 */     this.m_gbc.gridheight = 1;
/* 163 */     this.m_gbc.weightx = 0.0D;
/* 164 */     this.m_gbc.weighty = 0.0D;
/* 165 */     this.m_gbc.fill = 2;
/* 166 */     this.m_gbc.anchor = 11;
/* 167 */     this.m_gbc.insets = new java.awt.Insets(0, 5, 3, 5);
/* 168 */     this.m_layout.setConstraints(this.m_splineType, this.m_gbc);
/* 169 */     this.m_splineType.select("Bezier");
/* 170 */     add(this.m_splineType);
/*     */     
/* 172 */     this.m_gbc.gridx = 0;
/* 173 */     this.m_gbc.gridy = 1;
/* 174 */     this.m_gbc.gridwidth = 8;
/* 175 */     this.m_gbc.gridheight = 1;
/* 176 */     this.m_gbc.weightx = 100.0D;
/* 177 */     this.m_gbc.weighty = 0.0D;
/* 178 */     this.m_gbc.fill = 2;
/* 179 */     this.m_gbc.anchor = 11;
/* 180 */     this.m_layout.setConstraints(this.m_qX, this.m_gbc);
/* 181 */     add(this.m_qX);
/*     */     
/* 183 */     this.m_gbc.gridx = 0;
/* 184 */     this.m_gbc.gridy = 2;
/* 185 */     this.m_gbc.gridwidth = 8;
/* 186 */     this.m_gbc.gridheight = 1;
/* 187 */     this.m_gbc.weightx = 100.0D;
/* 188 */     this.m_gbc.weighty = 0.0D;
/* 189 */     this.m_gbc.fill = 2;
/* 190 */     this.m_gbc.anchor = 11;
/* 191 */     this.m_layout.setConstraints(this.m_qY, this.m_gbc);
/* 192 */     add(this.m_qY);
/*     */     
/* 194 */     this.m_showMe.setLabel("Show Mathematics");
/* 195 */     this.m_gbc.gridx = 0;
/* 196 */     this.m_gbc.gridy = 3;
/* 197 */     this.m_gbc.gridwidth = 1;
/* 198 */     this.m_gbc.gridheight = 1;
/* 199 */     this.m_gbc.weightx = 0.0D;
/* 200 */     this.m_gbc.weighty = 0.0D;
/* 201 */     this.m_gbc.fill = 2;
/* 202 */     this.m_gbc.anchor = 11;
/* 203 */     this.m_gbc.ipadx = 5;
/* 204 */     this.m_gbc.ipady = 5;
/* 205 */     this.m_gbc.insets = new java.awt.Insets(10, 5, 10, 5);
/* 206 */     this.m_layout.setConstraints(this.m_showMe, this.m_gbc);
/* 207 */     add(this.m_showMe);
/*     */     
/* 209 */     this.m_gbc.gridx = 1;
/* 210 */     this.m_gbc.gridy = 3;
/* 211 */     this.m_gbc.gridwidth = 7;
/* 212 */     this.m_gbc.gridheight = 1;
/* 213 */     this.m_gbc.weightx = 100.0D;
/* 214 */     this.m_gbc.weighty = 0.0D;
/* 215 */     this.m_gbc.fill = 2;
/* 216 */     this.m_gbc.anchor = 15;
/* 217 */     this.m_layout.setConstraints(this.m_status, this.m_gbc);
/* 218 */     this.m_status.setText("Drag the endpoints to adjust the spline.");
/* 219 */     add(this.m_status);
/*     */     
/* 221 */     this.m_canvas = new SplineCanvas(this.m_spline);
/* 222 */     this.m_gbc.gridx = 0;
/* 223 */     this.m_gbc.gridy = 4;
/* 224 */     this.m_gbc.gridwidth = 8;
/* 225 */     this.m_gbc.gridheight = 1;
/* 226 */     this.m_gbc.weightx = 100.0D;
/* 227 */     this.m_gbc.weighty = 100.0D;
/* 228 */     this.m_gbc.fill = 1;
/* 229 */     this.m_gbc.anchor = 11;
/* 230 */     this.m_layout.setConstraints(this.m_canvas, this.m_gbc);
/* 231 */     add(this.m_canvas);
/*     */     
/* 233 */     this.m_gbc.gridx = 8;
/* 234 */     this.m_gbc.gridy = 0;
/* 235 */     this.m_gbc.gridwidth = 1;
/* 236 */     this.m_gbc.gridheight = 5;
/* 237 */     this.m_gbc.weightx = 0.0D;
/* 238 */     this.m_gbc.weighty = 0.0D;
/* 239 */     this.m_gbc.fill = 1;
/* 240 */     this.m_gbc.anchor = 11;
/* 241 */     this.m_layout.setConstraints(this.m_basis, this.m_gbc);
/* 242 */     add(this.m_basis);
/*     */     
/* 244 */     resize(800, 600);
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     int i;
/* 249 */     if (this.m_fact == 0)
/*     */     {
/* 251 */       for (i = 0; i < 10; i++) {
/* 252 */         this.m_spline.m_p1.x -= 4;
/* 253 */         this.m_spline.m_p1.y += 4;
/* 254 */         this.m_canvas.repaint();
/* 255 */         this.m_spline.sleep(200);
/*     */       }
/* 257 */       for (i = 0; i < 10; i++) {
/* 258 */         this.m_spline.m_p1.x += 8;
/* 259 */         this.m_canvas.repaint();
/* 260 */         this.m_spline.sleep(200);
/*     */       }
/* 262 */       for (i = 0; i < 10; i++) {
/* 263 */         this.m_spline.m_p1.x -= 4;
/* 264 */         this.m_spline.m_p1.y -= 4;
/* 265 */         this.m_canvas.repaint();
/* 266 */         this.m_spline.sleep(200);
/*     */       }
/*     */     }
/* 269 */     else if (this.m_fact == 1)
/*     */     {
/* 271 */       for (i = 0; i < 10; i++) {
/* 272 */         this.m_spline.m_p2.y += 4;
/* 273 */         this.m_spline.m_p2.x -= 4;
/* 274 */         this.m_canvas.repaint();
/* 275 */         this.m_spline.sleep(200);
/*     */       }
/* 277 */       for (i = 0; i < 10; i++) {
/* 278 */         this.m_spline.m_p2.y -= 8;
/* 279 */         this.m_canvas.repaint();
/* 280 */         this.m_spline.sleep(200);
/*     */       }
/* 282 */       for (i = 0; i < 10; i++) {
/* 283 */         this.m_spline.m_p2.y += 4;
/* 284 */         this.m_spline.m_p2.x += 4;
/* 285 */         this.m_canvas.repaint();
/* 286 */         this.m_spline.sleep(200);
/*     */       }
/*     */     }
/* 289 */     else if (this.m_fact == 2)
/*     */     {
/* 291 */       for (i = 0; i < 10; i++) {
/* 292 */         this.m_spline.m_p3.x += 4;
/* 293 */         this.m_spline.m_p3.y -= 4;
/* 294 */         this.m_canvas.repaint();
/* 295 */         this.m_spline.sleep(200);
/*     */       }
/* 297 */       for (i = 0; i < 10; i++) {
/* 298 */         this.m_spline.m_p3.x -= 8;
/* 299 */         this.m_canvas.repaint();
/* 300 */         this.m_spline.sleep(200);
/*     */       }
/* 302 */       for (i = 0; i < 10; i++) {
/* 303 */         this.m_spline.m_p3.x += 4;
/* 304 */         this.m_spline.m_p3.y += 4;
/* 305 */         this.m_canvas.repaint();
/* 306 */         this.m_spline.sleep(200);
/*     */       }
/*     */     }
/* 309 */     else if (this.m_fact == 3)
/*     */     {
/* 311 */       for (i = 0; i < 10; i++) {
/* 312 */         this.m_spline.m_p4.y -= 4;
/* 313 */         this.m_spline.m_p4.x += 4;
/* 314 */         this.m_canvas.repaint();
/* 315 */         this.m_spline.sleep(200);
/*     */       }
/* 317 */       for (i = 0; i < 10; i++) {
/* 318 */         this.m_spline.m_p4.y += 8;
/* 319 */         this.m_canvas.repaint();
/* 320 */         this.m_spline.sleep(200);
/*     */       }
/* 322 */       for (i = 0; i < 10; i++) {
/* 323 */         this.m_spline.m_p4.y -= 4;
/* 324 */         this.m_spline.m_p4.x -= 4;
/* 325 */         this.m_canvas.repaint();
/* 326 */         this.m_spline.sleep(200);
/*     */       }
/*     */     }
/* 329 */     else if (this.m_fact == 4) {
/* 330 */       this.m_spline.m_p1.x = 200;
/* 331 */       this.m_spline.m_p1.y = 300;
/* 332 */       this.m_spline.m_p2.x = 400;
/* 333 */       this.m_spline.m_p2.y = 100;
/* 334 */       this.m_spline.m_p3.x = 100;
/* 335 */       this.m_spline.m_p3.y = 100;
/* 336 */       this.m_spline.m_p4.x = 300;
/* 337 */       this.m_spline.m_p4.y = 300;
/* 338 */       this.m_canvas.repaint();
/*     */     }
/* 340 */     else if (this.m_fact == 5) {
/* 341 */       this.m_spline.m_p1.x = 400;
/* 342 */       this.m_spline.m_p1.y = 100;
/* 343 */       this.m_spline.m_p2.x = 100;
/* 344 */       this.m_spline.m_p2.y = 150;
/* 345 */       this.m_spline.m_p3.x = 500;
/* 346 */       this.m_spline.m_p3.y = 250;
/* 347 */       this.m_spline.m_p4.x = 200;
/* 348 */       this.m_spline.m_p4.y = 300;
/* 349 */       this.m_canvas.repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean handleEvent(java.awt.Event paramEvent)
/*     */   {
/* 357 */     switch (paramEvent.id)
/*     */     {
/*     */     case 1001: 
/* 360 */       if (paramEvent.target == this.m_showMe) {
/* 361 */         if (this.m_currMode.equals("Tutorial")) {
/* 362 */           this.m_ansShown = true;
/* 363 */           Thread localThread; if (this.m_fact == 0) {
/* 364 */             localThread = new Thread(this);
/* 365 */             localThread.start();
/*     */           }
/* 367 */           else if (this.m_fact == 1) {
/* 368 */             localThread = new Thread(this);
/* 369 */             localThread.start();
/*     */           }
/* 371 */           else if (this.m_fact == 2) {
/* 372 */             localThread = new Thread(this);
/* 373 */             localThread.start();
/*     */           }
/* 375 */           else if (this.m_fact == 3) {
/* 376 */             localThread = new Thread(this);
/* 377 */             localThread.start();
/*     */           }
/* 379 */           else if (this.m_fact == 4) {
/* 380 */             localThread = new Thread(this);
/* 381 */             localThread.start();
/*     */           }
/*     */           else {
/* 384 */             localThread = new Thread(this);
/* 385 */             localThread.start();
/*     */           }
/*     */         }
/* 388 */         else if (this.m_currMode.equals("Play Area")) {
/* 389 */           this.m_spline.Draw(this.m_canvas.getGraphics(), true);
/* 390 */           this.m_basis.repaint();
/*     */         }
/* 392 */         else if ((this.m_currMode.equals("Exercises")) && 
/* 393 */           (this.m_answer != null) && (!this.m_ansShown)) {
/* 394 */           this.m_answer.flipShowLabels();
/* 395 */           this.m_answer.flipShowControlPoints();
/* 396 */           this.m_ansShown = true;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 401 */         this.m_canvas.repaint();
/*     */ 
/*     */       }
/* 404 */       else if (paramEvent.target == this.m_mode) {
/* 405 */         this.m_currMode = ((String)paramEvent.arg);
/* 406 */         if (this.m_currMode.equals("Tutorial")) {
/* 407 */           this.m_showMe.setLabel("Show Me How");
/*     */           
/* 409 */           this.m_fact += 1;
/* 410 */           if (this.m_fact > 5)
/* 411 */             this.m_fact = 0;
/* 412 */           if (this.m_fact == 0) {
/* 413 */             this.m_status.setText("Moving P1 affects the endpoint of the spline and the R1 tangent.");
/* 414 */           } else if (this.m_fact == 1) {
/* 415 */             this.m_status.setText("Moving P2 affects the R1 tangent but does not affect any endpoints.");
/* 416 */           } else if (this.m_fact == 2) {
/* 417 */             this.m_status.setText("Moving P3 affects the R2 tangent but does not affect any endpoints.");
/* 418 */           } else if (this.m_fact == 3) {
/* 419 */             this.m_status.setText("Moving P4 affects the endpoint of the spline and the R4 tangent.");
/* 420 */           } else if (this.m_fact == 4) {
/* 421 */             this.m_status.setText("The spline can form a closed loop.");
/*     */           } else
/* 423 */             this.m_status.setText("The spline can form an S-shape.");
/* 424 */           this.m_ansShown = false;
/*     */           
/* 426 */           this.m_canvas.removeOptional();
/* 427 */           this.m_answer = null;
/*     */         }
/* 429 */         else if (this.m_currMode.equals("Play Area")) {
/* 430 */           this.m_showMe.setLabel("Show Mathematics");
/* 431 */           this.m_status.setText("Drag the endpoints to adjust the spline.");
/* 432 */           this.m_canvas.removeOptional();
/* 433 */           this.m_answer = null;
/*     */         }
/*     */         else {
/* 436 */           this.m_showMe.setLabel("Show Answer");
/* 437 */           this.m_status.setText("Try to match your spline to the red one.");
/* 438 */           this.m_prefHints.enable();
/* 439 */           if (this.m_currSpline.equals("Bezier")) {
/* 440 */             this.m_answer = new BezierSpline();
/* 441 */           } else if (this.m_currSpline.equals("Simple")) {
/* 442 */             this.m_answer = new SimpleSpline();
/*     */           }
/* 444 */           this.m_answer.flipShowTangents();
/* 445 */           this.m_answer.flipShowLabels();
/* 446 */           this.m_answer.flipShowColor();
/* 447 */           this.m_answer.flipShowControlPoints();
/* 448 */           this.m_answer.flipShowTPoints();
/* 449 */           this.m_answer.setColors(java.awt.Color.red, new java.awt.Color(128, 0, 0));
/* 450 */           this.m_answer.setTValue(this.m_t);
/* 451 */           this.m_answer.randomize(this.m_canvas.size());
/* 452 */           this.m_canvas.setOptional(this.m_answer);
/* 453 */           this.m_ansShown = false;
/*     */         }
/*     */         
/* 456 */         this.m_canvas.repaint();
/*     */ 
/*     */       }
/* 459 */       else if (paramEvent.target == this.m_splineType) {
/* 460 */         this.m_currSpline = ((String)paramEvent.arg);
/* 461 */         if (this.m_currSpline.equals("Bezier")) {
/* 462 */           this.m_spline = this.m_bezSpline;
/* 463 */           this.m_basis.setBasisFncs(this.m_bas1, this.m_bas2, this.m_bas3, this.m_bas4);
/* 464 */           this.m_canvas.setSpline(this.m_spline);
/* 465 */           this.m_spline.updateEqn(-10, -10);
/* 466 */           this.m_canvas.repaint();
/* 467 */           this.m_basis.repaint();
/*     */         }
/* 469 */         else if (this.m_currSpline.equals("Simple")) {
/* 470 */           this.m_spline = this.m_simSpline;
/* 471 */           this.m_basis.setBasisFncs(this.m_simBas1, this.m_simBas2, this.m_simBas3, this.m_simBas4);
/* 472 */           this.m_canvas.setSpline(this.m_spline);
/* 473 */           this.m_spline.updateEqn(-10, -10);
/* 474 */           this.m_canvas.repaint();
/* 475 */           this.m_basis.repaint();
/*     */         }
/*     */         
/*     */       }
/* 479 */       else if (paramEvent.target == this.m_prefTangents) {
/* 480 */         this.m_bezSpline.flipShowTangents();
/* 481 */         this.m_simSpline.flipShowTangents();
/* 482 */         this.m_canvas.repaint();
/*     */ 
/*     */       }
/* 485 */       else if (paramEvent.target == this.m_prefLabels) {
/* 486 */         this.m_bezSpline.flipShowLabels();
/* 487 */         this.m_simSpline.flipShowLabels();
/* 488 */         this.m_canvas.repaint();
/*     */ 
/*     */       }
/* 491 */       else if (paramEvent.target == this.m_prefColor) {
/* 492 */         this.m_bezSpline.flipShowColor();
/* 493 */         this.m_simSpline.flipShowColor();
/* 494 */         this.m_canvas.repaint();
/*     */ 
/*     */       }
/* 497 */       else if (paramEvent.target == this.m_prefTPoints) {
/* 498 */         this.m_simSpline.flipShowTPoints();
/* 499 */         this.m_bezSpline.flipShowTPoints();
/* 500 */         this.m_bas1.flipShowTPoints();
/* 501 */         this.m_bas2.flipShowTPoints();
/* 502 */         this.m_bas3.flipShowTPoints();
/* 503 */         this.m_bas4.flipShowTPoints();
/* 504 */         this.m_simBas1.flipShowTPoints();
/* 505 */         this.m_simBas2.flipShowTPoints();
/* 506 */         this.m_simBas3.flipShowTPoints();
/* 507 */         this.m_simBas4.flipShowTPoints();
/* 508 */         this.m_canvas.repaint();
/* 509 */         this.m_basis.repaint();
/*     */ 
/*     */       }
/* 512 */       else if (paramEvent.target == this.m_prefHints) {
/* 513 */         this.m_canvas.flipHints();
/*     */ 
/*     */       }
/* 516 */       else if (paramEvent.target == this.m_tValue) {
/* 517 */         this.m_t = Double.valueOf((String)paramEvent.arg).doubleValue();
/* 518 */         this.m_bezSpline.setTValue(this.m_t);
/* 519 */         this.m_simSpline.setTValue(this.m_t);
/* 520 */         this.m_bas1.setTValue(this.m_t);
/* 521 */         this.m_bas2.setTValue(this.m_t);
/* 522 */         this.m_bas3.setTValue(this.m_t);
/* 523 */         this.m_bas4.setTValue(this.m_t);
/* 524 */         this.m_simBas1.setTValue(this.m_t);
/* 525 */         this.m_simBas2.setTValue(this.m_t);
/* 526 */         this.m_simBas3.setTValue(this.m_t);
/* 527 */         this.m_simBas4.setTValue(this.m_t);
/* 528 */         if (this.m_answer != null)
/* 529 */           this.m_answer.setTValue(this.m_t);
/* 530 */         this.m_canvas.repaint();
/* 531 */         this.m_basis.repaint();
/*     */       }
/*     */       
/* 534 */       return true;
/*     */     }
/*     */     
/*     */     
/* 538 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/Bezier.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */