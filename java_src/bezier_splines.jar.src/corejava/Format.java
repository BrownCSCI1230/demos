/*     */ package corejava;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ public class Format
/*     */ {
/*     */   private int width;
/*     */   private int precision;
/*     */   private String pre;
/*     */   private String post;
/*     */   private boolean leading_zeroes;
/*     */   private boolean show_plus;
/*     */   private boolean alternate;
/*     */   private boolean show_space;
/*     */   private boolean left_align;
/*     */   private char fmt;
/*     */   
/*     */   public Format(String paramString)
/*     */   {
/*  72 */     this.width = 0;
/*  73 */     this.precision = -1;
/*  74 */     this.pre = "";
/*  75 */     this.post = "";
/*  76 */     this.leading_zeroes = false;
/*  77 */     this.show_plus = false;
/*  78 */     this.alternate = false;
/*  79 */     this.show_space = false;
/*  80 */     this.left_align = false;
/*  81 */     this.fmt = ' ';
/*     */     
/*     */ 
/*  84 */     int i = paramString.length();
/*  85 */     int j = 0;
/*     */     
/*     */ 
/*  88 */     int k = 0;
/*     */     
/*  90 */     while (j == 0) {
/*  91 */       if (k >= i) { j = 5;
/*  92 */       } else if (paramString.charAt(k) == '%') {
/*  93 */         if (k < i - 1) {
/*  94 */           if (paramString.charAt(k + 1) == '%') {
/*  95 */             this.pre += '%';
/*  96 */             k++;
/*     */           }
/*     */           else {
/*  99 */             j = 1;
/*     */           }
/* 101 */         } else throw new IllegalArgumentException();
/*     */       }
/*     */       else
/* 104 */         this.pre += paramString.charAt(k);
/* 105 */       k++;
/*     */     }
/* 107 */     while (j == 1) {
/* 108 */       if (k >= i) { j = 5;
/* 109 */       } else if (paramString.charAt(k) == ' ') { this.show_space = true;
/* 110 */       } else if (paramString.charAt(k) == '-') { this.left_align = true;
/* 111 */       } else if (paramString.charAt(k) == '+') { this.show_plus = true;
/* 112 */       } else if (paramString.charAt(k) == '0') { this.leading_zeroes = true;
/* 113 */       } else if (paramString.charAt(k) == '#') { this.alternate = true;
/* 114 */       } else { j = 2;k--; }
/* 115 */       k++;
/*     */     }
/* 117 */     while (j == 2)
/* 118 */       if (k >= i) { j = 5;
/* 119 */       } else if ((paramString.charAt(k) >= '0') && (paramString.charAt(k) <= '9')) {
/* 120 */         this.width = (this.width * 10 + paramString.charAt(k) - 48);
/* 121 */         k++;
/*     */       }
/* 123 */       else if (paramString.charAt(k) == '.') {
/* 124 */         j = 3;
/* 125 */         this.precision = 0;
/* 126 */         k++;
/*     */       }
/*     */       else {
/* 129 */         j = 4;
/*     */       }
/* 131 */     while (j == 3)
/* 132 */       if (k >= i) { j = 5;
/* 133 */       } else if ((paramString.charAt(k) >= '0') && (paramString.charAt(k) <= '9')) {
/* 134 */         this.precision = (this.precision * 10 + paramString.charAt(k) - 48);
/* 135 */         k++;
/*     */       }
/*     */       else {
/* 138 */         j = 4;
/*     */       }
/* 140 */     if (j == 4) {
/* 141 */       if (k >= i) j = 5; else
/* 142 */         this.fmt = paramString.charAt(k);
/* 143 */       k++;
/*     */     }
/* 145 */     if (k < i) {
/* 146 */       this.post = paramString.substring(k, i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void print(PrintStream paramPrintStream, String paramString, double paramDouble)
/*     */   {
/* 157 */     paramPrintStream.print(new Format(paramString).form(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void print(PrintStream paramPrintStream, String paramString, long paramLong)
/*     */   {
/* 167 */     paramPrintStream.print(new Format(paramString).form(paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void print(PrintStream paramPrintStream, String paramString, char paramChar)
/*     */   {
/* 178 */     paramPrintStream.print(new Format(paramString).form(paramChar));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void print(PrintStream paramPrintStream, String paramString1, String paramString2)
/*     */   {
/* 188 */     paramPrintStream.print(new Format(paramString1).form(paramString2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int atoi(String paramString)
/*     */   {
/* 198 */     return (int)atol(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long atol(String paramString)
/*     */   {
/* 208 */     int i = 0;
/*     */     
/* 210 */     while ((i < paramString.length()) && (Character.isSpace(paramString.charAt(i)))) i++;
/* 211 */     if ((i < paramString.length()) && (paramString.charAt(i) == '0')) {
/* 212 */       if ((i + 1 < paramString.length()) && ((paramString.charAt(i + 1) == 'x') || (paramString.charAt(i + 1) == 'X')))
/* 213 */         return parseLong(paramString.substring(i + 2), 16);
/* 214 */       return parseLong(paramString, 8);
/*     */     }
/* 216 */     return parseLong(paramString, 10);
/*     */   }
/*     */   
/*     */   private static long parseLong(String paramString, int paramInt) {
/* 220 */     int i = 0;
/* 221 */     int j = 1;
/* 222 */     long l = 0L;
/*     */     
/* 224 */     while ((i < paramString.length()) && (Character.isSpace(paramString.charAt(i)))) i++;
/* 225 */     if ((i < paramString.length()) && (paramString.charAt(i) == '-')) { j = -1;i++;
/* 226 */     } else if ((i < paramString.length()) && (paramString.charAt(i) == '+')) { i++; }
/* 227 */     while (i < paramString.length()) {
/* 228 */       int k = paramString.charAt(i);
/* 229 */       if ((k >= 48) && (k < 48 + paramInt)) {
/* 230 */         l = l * paramInt + k - 48L;
/* 231 */       } else if ((k >= 65) && (k < 65 + paramInt - 10)) {
/* 232 */         l = l * paramInt + k - 65L + 10L;
/* 233 */       } else if ((k >= 97) && (k < 97 + paramInt - 10)) {
/* 234 */         l = l * paramInt + k - 97L + 10L;
/*     */       } else
/* 236 */         return l * j;
/* 237 */       i++;
/*     */     }
/* 239 */     return l * j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double atof(String paramString)
/*     */   {
/* 248 */     int i = 0;
/* 249 */     int j = 1;
/* 250 */     double d1 = 0.0D;
/*     */     
/* 252 */     double d2 = 1.0D;
/* 253 */     int k = 0;
/*     */     
/* 255 */     while ((i < paramString.length()) && (Character.isSpace(paramString.charAt(i)))) i++;
/* 256 */     if ((i < paramString.length()) && (paramString.charAt(i) == '-')) { j = -1;i++;
/* 257 */     } else if ((i < paramString.length()) && (paramString.charAt(i) == '+')) { i++; }
/* 258 */     while (i < paramString.length()) {
/* 259 */       int m = paramString.charAt(i);
/* 260 */       if ((m >= 48) && (m <= 57)) {
/* 261 */         if (k == 0) {
/* 262 */           d1 = d1 * 10.0D + m - 48.0D;
/* 263 */         } else if (k == 1) {
/* 264 */           d2 /= 10.0D;
/* 265 */           d1 += d2 * (m - 48);
/*     */         }
/*     */       }
/* 268 */       else if (m == 46) {
/* 269 */         if (k == 0) k = 1; else
/* 270 */           return j * d1;
/*     */       } else {
/* 272 */         if ((m == 101) || (m == 69)) {
/* 273 */           long l = (int)parseLong(paramString.substring(i + 1), 10);
/* 274 */           return j * d1 * Math.pow(10.0D, l);
/*     */         }
/* 276 */         return j * d1; }
/* 277 */       i++;
/*     */     }
/* 279 */     return j * d1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String form(double paramDouble)
/*     */   {
/* 291 */     if (this.precision < 0) this.precision = 6;
/* 292 */     int i = 1;
/* 293 */     if (paramDouble < 0.0D) { paramDouble = -paramDouble;i = -1; }
/* 294 */     String str; if (this.fmt == 'f') {
/* 295 */       str = fixed_format(paramDouble);
/* 296 */     } else if ((this.fmt == 'e') || (this.fmt == 'E') || (this.fmt == 'g') || (this.fmt == 'G'))
/* 297 */       str = exp_format(paramDouble); else {
/* 298 */       throw new IllegalArgumentException();
/*     */     }
/* 300 */     return pad(sign(i, str));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String form(long paramLong)
/*     */   {
/* 311 */     int i = 0;
/* 312 */     String str; if ((this.fmt == 'd') || (this.fmt == 'i')) {
/* 313 */       i = 1;
/* 314 */       if (paramLong < 0L) { paramLong = -paramLong;i = -1; }
/* 315 */       str = "" + paramLong;
/*     */     }
/* 317 */     else if (this.fmt == 'o') {
/* 318 */       str = convert(paramLong, 3, 7, "01234567");
/* 319 */     } else if (this.fmt == 'x') {
/* 320 */       str = convert(paramLong, 4, 15, "0123456789abcdef");
/* 321 */     } else if (this.fmt == 'X') {
/* 322 */       str = convert(paramLong, 4, 15, "0123456789ABCDEF");
/* 323 */     } else { throw new IllegalArgumentException();
/*     */     }
/* 325 */     return pad(sign(i, str));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String form(char paramChar)
/*     */   {
/* 335 */     if (this.fmt != 'c') {
/* 336 */       throw new IllegalArgumentException();
/*     */     }
/* 338 */     String str = "" + paramChar;
/* 339 */     return pad(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String form(String paramString)
/*     */   {
/* 349 */     if (this.fmt != 's')
/* 350 */       throw new IllegalArgumentException();
/* 351 */     if (this.precision >= 0) paramString = paramString.substring(0, this.precision);
/* 352 */     return pad(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/* 361 */     double d1 = 1.23456789012D;
/* 362 */     double d2 = 123.0D;
/* 363 */     double d3 = 1.2345E30D;
/* 364 */     double d4 = 1.02D;
/* 365 */     double d5 = 1.234E-5D;
/* 366 */     int i = 51966;
/* 367 */     print(System.out, "x = |%f|\n", d1);
/* 368 */     print(System.out, "u = |%20f|\n", d5);
/* 369 */     print(System.out, "x = |% .5f|\n", d1);
/* 370 */     print(System.out, "w = |%20.5f|\n", d4);
/* 371 */     print(System.out, "x = |%020.5f|\n", d1);
/* 372 */     print(System.out, "x = |%+20.5f|\n", d1);
/* 373 */     print(System.out, "x = |%+020.5f|\n", d1);
/* 374 */     print(System.out, "x = |% 020.5f|\n", d1);
/* 375 */     print(System.out, "y = |%#+20.5f|\n", d2);
/* 376 */     print(System.out, "y = |%-+20.5f|\n", d2);
/* 377 */     print(System.out, "z = |%20.5f|\n", d3);
/*     */     
/* 379 */     print(System.out, "x = |%e|\n", d1);
/* 380 */     print(System.out, "u = |%20e|\n", d5);
/* 381 */     print(System.out, "x = |% .5e|\n", d1);
/* 382 */     print(System.out, "w = |%20.5e|\n", d4);
/* 383 */     print(System.out, "x = |%020.5e|\n", d1);
/* 384 */     print(System.out, "x = |%+20.5e|\n", d1);
/* 385 */     print(System.out, "x = |%+020.5e|\n", d1);
/* 386 */     print(System.out, "x = |% 020.5e|\n", d1);
/* 387 */     print(System.out, "y = |%#+20.5e|\n", d2);
/* 388 */     print(System.out, "y = |%-+20.5e|\n", d2);
/*     */     
/* 390 */     print(System.out, "x = |%g|\n", d1);
/* 391 */     print(System.out, "z = |%g|\n", d3);
/* 392 */     print(System.out, "w = |%g|\n", d4);
/* 393 */     print(System.out, "u = |%g|\n", d5);
/* 394 */     print(System.out, "y = |%.2g|\n", d2);
/* 395 */     print(System.out, "y = |%#.2g|\n", d2);
/*     */     
/* 397 */     print(System.out, "d = |%d|\n", i);
/* 398 */     print(System.out, "d = |%20d|\n", i);
/* 399 */     print(System.out, "d = |%020d|\n", i);
/* 400 */     print(System.out, "d = |%+20d|\n", i);
/* 401 */     print(System.out, "d = |% 020d|\n", i);
/* 402 */     print(System.out, "d = |%-20d|\n", i);
/* 403 */     print(System.out, "d = |%20.8d|\n", i);
/* 404 */     print(System.out, "d = |%x|\n", i);
/* 405 */     print(System.out, "d = |%20X|\n", i);
/* 406 */     print(System.out, "d = |%#20x|\n", i);
/* 407 */     print(System.out, "d = |%020X|\n", i);
/* 408 */     print(System.out, "d = |%20.8x|\n", i);
/* 409 */     print(System.out, "d = |%o|\n", i);
/* 410 */     print(System.out, "d = |%020o|\n", i);
/* 411 */     print(System.out, "d = |%#20o|\n", i);
/* 412 */     print(System.out, "d = |%#020o|\n", i);
/* 413 */     print(System.out, "d = |%20.12o|\n", i);
/*     */     
/* 415 */     print(System.out, "s = |%-20s|\n", "Hello");
/* 416 */     print(System.out, "s = |%-20c|\n", '!');
/*     */   }
/*     */   
/*     */   private static String repeat(char paramChar, int paramInt)
/*     */   {
/* 421 */     if (paramInt <= 0) return "";
/* 422 */     StringBuffer localStringBuffer = new StringBuffer(paramInt);
/* 423 */     for (int i = 0; i < paramInt; i++) localStringBuffer.append(paramChar);
/* 424 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */   private static String convert(long paramLong, int paramInt1, int paramInt2, String paramString) {
/* 428 */     if (paramLong == 0L) return "0";
/* 429 */     String str = "";
/* 430 */     while (paramLong != 0L) {
/* 431 */       str = paramString.charAt((int)(paramLong & paramInt2)) + str;
/* 432 */       paramLong >>>= paramInt1;
/*     */     }
/* 434 */     return str;
/*     */   }
/*     */   
/*     */   private String pad(String paramString) {
/* 438 */     String str = repeat(' ', this.width - paramString.length());
/* 439 */     if (this.left_align) return this.pre + paramString + str + this.post;
/* 440 */     return this.pre + str + paramString + this.post;
/*     */   }
/*     */   
/*     */   private String sign(int paramInt, String paramString) {
/* 444 */     String str = "";
/* 445 */     if (paramInt < 0) { str = "-";
/* 446 */     } else if (paramInt > 0) {
/* 447 */       if (this.show_plus) { str = "+";
/* 448 */       } else if (this.show_space) { str = " ";
/*     */       }
/*     */     }
/* 451 */     else if ((this.fmt == 'o') && (this.alternate) && (paramString.length() > 0) && (paramString.charAt(0) != '0')) { str = "0";
/* 452 */     } else if ((this.fmt == 'x') && (this.alternate)) { str = "0x";
/* 453 */     } else if ((this.fmt == 'X') && (this.alternate)) { str = "0X";
/*     */     }
/* 455 */     int i = 0;
/* 456 */     if (this.leading_zeroes) {
/* 457 */       i = this.width;
/* 458 */     } else if (((this.fmt == 'd') || (this.fmt == 'i') || (this.fmt == 'x') || (this.fmt == 'X') || (this.fmt == 'o')) && 
/* 459 */       (this.precision > 0)) { i = this.precision;
/*     */     }
/* 461 */     return str + repeat('0', i - str.length() - paramString.length()) + paramString;
/*     */   }
/*     */   
/*     */   private String fixed_format(double paramDouble)
/*     */   {
/* 466 */     String str = "";
/*     */     
/* 468 */     if (paramDouble > 9.223372036854776E18D) { return exp_format(paramDouble);
/*     */     }
/* 470 */     long l = (this.precision == 0 ? paramDouble + 0.5D : paramDouble);
/* 471 */     str = str + l;
/*     */     
/* 473 */     double d = paramDouble - l;
/* 474 */     if ((d >= 1.0D) || (d < 0.0D)) { return exp_format(paramDouble);
/*     */     }
/* 476 */     return str + frac_part(d);
/*     */   }
/*     */   
/*     */   private String frac_part(double paramDouble)
/*     */   {
/* 481 */     String str1 = "";
/* 482 */     if (this.precision > 0) {
/* 483 */       double d = 1.0D;
/* 484 */       String str2 = "";
/* 485 */       for (int j = 1; (j <= this.precision) && (d <= 9.223372036854776E18D); j++) {
/* 486 */         d *= 10.0D;
/* 487 */         str2 = str2 + "0";
/*     */       }
/* 489 */       long l = (d * paramDouble + 0.5D);
/*     */       
/* 491 */       str1 = str2 + l;
/* 492 */       str1 = str1.substring(str1.length() - this.precision, str1.length());
/*     */     }
/*     */     
/*     */ 
/* 496 */     if ((this.precision > 0) || (this.alternate)) str1 = "." + str1;
/* 497 */     if (((this.fmt == 'G') || (this.fmt == 'g')) && (!this.alternate))
/*     */     {
/* 499 */       int i = str1.length() - 1;
/* 500 */       while ((i >= 0) && (str1.charAt(i) == '0')) i--;
/* 501 */       if ((i >= 0) && (str1.charAt(i) == '.')) i--;
/* 502 */       str1 = str1.substring(0, i + 1);
/*     */     }
/* 504 */     return str1;
/*     */   }
/*     */   
/*     */   private String exp_format(double paramDouble) {
/* 508 */     String str1 = "";
/* 509 */     int i = 0;
/* 510 */     double d1 = paramDouble;
/* 511 */     double d2 = 1.0D;
/* 512 */     for (; d1 > 10.0D; d1 /= 10.0D) { i++;d2 /= 10.0D; }
/* 513 */     for (; d1 < 1.0D; d1 *= 10.0D) { i--;d2 *= 10.0D; }
/* 514 */     if (((this.fmt == 'g') || (this.fmt == 'G')) && (i >= -4) && (i < this.precision)) {
/* 515 */       return fixed_format(paramDouble);
/*     */     }
/* 517 */     paramDouble *= d2;
/* 518 */     str1 = str1 + fixed_format(paramDouble);
/*     */     
/* 520 */     if ((this.fmt == 'e') || (this.fmt == 'g')) {
/* 521 */       str1 = str1 + "e";
/*     */     } else {
/* 523 */       str1 = str1 + "E";
/*     */     }
/* 525 */     String str2 = "000";
/* 526 */     if (i >= 0) {
/* 527 */       str1 = str1 + "+";
/* 528 */       str2 = str2 + i;
/*     */     }
/*     */     else {
/* 531 */       str1 = str1 + "-";
/* 532 */       str2 = str2 + -i;
/*     */     }
/*     */     
/* 535 */     return str1 + str2.substring(str2.length() - 3, str2.length());
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/corejava/Format.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */