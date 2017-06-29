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
/*     */ public class HMatrixOps
/*     */ {
/*     */   public static float[][] multiply(float[][] paramArrayOfFloat1, float[][] paramArrayOfFloat2, int paramInt1, int paramInt2)
/*     */   {
/*  30 */     float[][] arrayOfFloat = new float[paramInt1][paramInt2];
/*     */     
/*  32 */     for (int i = 0; i < paramInt1; i++)
/*     */     {
/*  34 */       for (int j = 0; j < paramInt2; j++) {
/*  35 */         float f = 0.0F;
/*     */         
/*  37 */         for (int k = 0; k < paramInt2; k++) {
/*  38 */           f += paramArrayOfFloat1[i][k] * paramArrayOfFloat2[k][j];
/*     */         }
/*  40 */         arrayOfFloat[i][j] = f;
/*     */       } }
/*  42 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getIdentity(int paramInt)
/*     */   {
/*  49 */     float[][] arrayOfFloat = new float[paramInt][paramInt];
/*  50 */     for (int i = 0; i < paramInt; i++)
/*  51 */       arrayOfFloat[i][i] = 1.0F;
/*  52 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getTranslationMatrix(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/*  59 */     float[][] arrayOfFloat = new float[paramInt][paramInt];
/*  60 */     for (int i = 0; i < paramInt; i++) {
/*  61 */       arrayOfFloat[(paramInt - 1)][i] = paramArrayOfFloat[i];
/*  62 */       arrayOfFloat[i][i] = 1.0F;
/*     */     }
/*  64 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getTranslationMatrix(int paramInt1, int paramInt2)
/*     */   {
/*  71 */     float[] arrayOfFloat = new float[3];
/*  72 */     arrayOfFloat[0] = paramInt1;
/*  73 */     arrayOfFloat[1] = paramInt2;
/*  74 */     arrayOfFloat[2] = 1.0F;
/*  75 */     return getTranslationMatrix(arrayOfFloat, 3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getRotationMatrix(float paramFloat)
/*     */   {
/*  82 */     float[][] arrayOfFloat = new float[3][3];
/*  83 */     arrayOfFloat[0][0] = ((float)Math.cos(paramFloat));
/*  84 */     arrayOfFloat[1][0] = ((float)Math.sin(paramFloat));
/*  85 */     arrayOfFloat[2][0] = 0.0F;
/*  86 */     arrayOfFloat[0][1] = ((float)-Math.sin(paramFloat));
/*  87 */     arrayOfFloat[1][1] = ((float)Math.cos(paramFloat));
/*  88 */     arrayOfFloat[2][1] = 0.0F;
/*  89 */     arrayOfFloat[0][2] = 0.0F;
/*  90 */     arrayOfFloat[1][2] = 0.0F;
/*  91 */     arrayOfFloat[2][2] = 1.0F;
/*     */     
/*  93 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getScaleMatrix(float paramFloat)
/*     */   {
/* 100 */     float[][] arrayOfFloat = new float[3][3];
/* 101 */     arrayOfFloat[0][0] = paramFloat;
/* 102 */     arrayOfFloat[1][0] = 0.0F;
/* 103 */     arrayOfFloat[2][0] = 0.0F;
/* 104 */     arrayOfFloat[0][1] = 0.0F;
/* 105 */     arrayOfFloat[1][1] = paramFloat;
/* 106 */     arrayOfFloat[2][1] = 0.0F;
/* 107 */     arrayOfFloat[0][2] = 0.0F;
/* 108 */     arrayOfFloat[1][2] = 0.0F;
/* 109 */     arrayOfFloat[2][2] = 1.0F;
/*     */     
/* 111 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getScaleMatrix(float paramFloat1, float paramFloat2)
/*     */   {
/* 118 */     float[][] arrayOfFloat = new float[3][3];
/* 119 */     arrayOfFloat[0][0] = paramFloat1;
/* 120 */     arrayOfFloat[1][0] = 0.0F;
/* 121 */     arrayOfFloat[2][0] = 0.0F;
/* 122 */     arrayOfFloat[0][1] = 0.0F;
/* 123 */     arrayOfFloat[1][1] = paramFloat2;
/* 124 */     arrayOfFloat[2][1] = 0.0F;
/* 125 */     arrayOfFloat[0][2] = 0.0F;
/* 126 */     arrayOfFloat[1][2] = 0.0F;
/* 127 */     arrayOfFloat[2][2] = 1.0F;
/*     */     
/* 129 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */   public static float[][] getHouse()
/*     */   {
/* 135 */     float[] arrayOfFloat1 = new float[13];
/* 136 */     float[] arrayOfFloat2 = new float[13];
/* 137 */     arrayOfFloat1[0] = 25.0F;
/* 138 */     arrayOfFloat2[0] = 0.0F;
/* 139 */     arrayOfFloat1[1] = 25.0F;
/* 140 */     arrayOfFloat2[1] = -45.0F;
/* 141 */     arrayOfFloat1[2] = 30.0F;
/* 142 */     arrayOfFloat2[2] = -45.0F;
/* 143 */     arrayOfFloat1[3] = 25.0F;
/* 144 */     arrayOfFloat2[3] = -50.0F;
/* 145 */     arrayOfFloat1[4] = 25.0F;
/* 146 */     arrayOfFloat2[4] = -75.0F;
/* 147 */     arrayOfFloat1[5] = 13.0F;
/* 148 */     arrayOfFloat2[5] = -75.0F;
/* 149 */     arrayOfFloat1[6] = 13.0F;
/* 150 */     arrayOfFloat2[6] = -63.0F;
/* 151 */     arrayOfFloat1[7] = 0.0F;
/* 152 */     arrayOfFloat2[7] = -75.0F;
/* 153 */     arrayOfFloat1[8] = -25.0F;
/* 154 */     arrayOfFloat2[8] = -50.0F;
/* 155 */     arrayOfFloat1[9] = -30.0F;
/* 156 */     arrayOfFloat2[9] = -45.0F;
/* 157 */     arrayOfFloat1[10] = -25.0F;
/* 158 */     arrayOfFloat2[10] = -45.0F;
/* 159 */     arrayOfFloat1[11] = -25.0F;
/* 160 */     arrayOfFloat2[11] = 0.0F;
/* 161 */     arrayOfFloat1[12] = 0.0F;
/* 162 */     arrayOfFloat2[12] = 0.0F;
/*     */     
/* 164 */     float[][] arrayOfFloat = new float[13][2];
/* 165 */     for (int i = 0; i < 13; i++) {
/* 166 */       arrayOfFloat[i][0] = arrayOfFloat1[i];
/* 167 */       arrayOfFloat[i][1] = arrayOfFloat2[i];
/*     */     }
/* 169 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getCircle(float paramFloat, int paramInt)
/*     */   {
/* 176 */     float[][] arrayOfFloat = new float[paramInt][3];
/* 177 */     for (int i = 0; i < paramInt; i++) {
/* 178 */       arrayOfFloat[i][0] = ((float)(paramFloat * Math.cos(i * (6.283185307179586D / paramInt))));
/* 179 */       arrayOfFloat[i][1] = ((float)(paramFloat * Math.sin(i * (6.283185307179586D / paramInt))));
/*     */     }
/* 181 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getTriangle(float paramFloat1, float paramFloat2)
/*     */   {
/* 188 */     float[][] arrayOfFloat = new float[3][2];
/* 189 */     arrayOfFloat[0][0] = 0.0F;
/* 190 */     arrayOfFloat[0][1] = (paramFloat2 / 2.0F);
/* 191 */     arrayOfFloat[1][0] = (-paramFloat1 / 2.0F);
/* 192 */     arrayOfFloat[1][1] = (-paramFloat2 / 2.0F);
/* 193 */     arrayOfFloat[2][0] = (paramFloat1 / 2.0F);
/* 194 */     arrayOfFloat[2][1] = (-paramFloat2 / 2.0F);
/* 195 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void printMatrix(float[][] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 203 */     for (int i = 0; i < paramInt2; i++) {
/* 204 */       for (int j = 0; j < paramInt1; j++)
/* 205 */         System.out.print(paramArrayOfFloat[j][i] + " ");
/* 206 */       System.out.print("\n");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float[][] getRect(float paramFloat1, float paramFloat2)
/*     */   {
/* 214 */     float[][] arrayOfFloat = new float[4][2];
/* 215 */     arrayOfFloat[0][0] = (-paramFloat1 / 2.0F);
/* 216 */     arrayOfFloat[0][1] = (-paramFloat2 / 2.0F);
/* 217 */     arrayOfFloat[1][0] = (paramFloat1 / 2.0F);
/* 218 */     arrayOfFloat[1][1] = (-paramFloat2 / 2.0F);
/* 219 */     arrayOfFloat[2][0] = (paramFloat1 / 2.0F);
/* 220 */     arrayOfFloat[2][1] = (paramFloat2 / 2.0F);
/* 221 */     arrayOfFloat[3][0] = (-paramFloat1 / 2.0F);
/* 222 */     arrayOfFloat[3][1] = (paramFloat2 / 2.0F);
/*     */     
/* 224 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static float length(float paramFloat1, float paramFloat2)
/*     */   {
/* 231 */     return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HMatrixOps.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */