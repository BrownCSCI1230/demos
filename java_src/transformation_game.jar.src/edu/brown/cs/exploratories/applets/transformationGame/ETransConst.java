package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.Color;

public abstract interface ETransConst
{
  public static final int E_TRANS_APPLET_SIZE_X = 500;
  public static final int E_TRANS_APPLET_SIZE_Y = 600;
  public static final int E_TRANS_CONTROL_PANEL_SIZE_X = 500;
  public static final int E_TRANS_CONTROL_PANEL_SIZE_Y = 100;
  public static final int E_TRANS_PIPELINE_AREA_SIZE_X = 500;
  public static final int E_TRANS_PIPELINE_AREA_SIZE_Y = 60;
  public static final int E_TRANS_PALETTE_AREA_SIZE_X = 100;
  public static final int E_TRANS_PALETTE_AREA_SIZE_Y = 440;
  public static final int E_TRANS_PLAY_AREA_SIZE_X = 400;
  public static final int E_TRANS_PLAY_AREA_SIZE_Y = 440;
  public static final int E_TRANS_LEVEL_TEXT_X_CUSHION = 10;
  public static final int E_TRANS_LEVEL_TEXT_Y_CUSHION = 20;
  public static final int E_TRANS_GRAPHIC_AREA_SIZE_X = 500;
  public static final int E_TRANS_GRAPHIC_AREA_SIZE_Y = 500;
  public static final float E_TRANS_AXES_THICKNESS = 2.0F;
  public static final int E_TRANS_AXES_BORDER_SIZE = 20;
  public static final int E_TRANS_GAME_OBJECT_SIZE_X = 40;
  public static final int E_TRANS_GAME_OBJECT_SIZE_Y = 60;
  public static final int E_TRANS_PALETTE_PIECE_SIZE_X = 80;
  public static final int E_TRANS_PALETTE_PIECE_SIZE_Y = 40;
  public static final int E_TRANS_PALETTE_PIECE_CUSHION_X = 10;
  public static final int E_TRANS_PALETTE_PIECE_CUSHION_Y = 10;
  public static final int E_TRANS_PALETTE_PIECE_VAL_TEXT_CUSHION = 3;
  public static final int E_TRANS_ANCHOR_POINT_X = 450;
  public static final int E_TRANS_ANCHOR_POINT_Y = 30;
  public static final int E_TRANS_ANCHOR_DIAMETER = 28;
  public static final int E_TRANS_GAME_OBJECT_DRAG_GRAVITY = 12;
  public static final Color E_TRANS_BROWN = new Color(165, 42, 42);
  public static final Color E_TRANS_PERRIWINKLE = new Color(191, 62, 255);
  public static final Color E_TRANS_LAVENDAR = new Color(205, 150, 205);
  public static final Color E_TRANS_LIGHT_BLUE = new Color(154, 192, 205);
  public static final Color E_TRANS_LIGHT_GREEN = new Color(143, 188, 143);
  public static final Color E_TRANS_LIGHT_YELLOW = new Color(238, 232, 170);
  public static final Color E_TRANS_PREV_COLOR = E_TRANS_LIGHT_GREEN;
  public static final Color E_TRANS_NEXT_COLOR = E_TRANS_LIGHT_GREEN;
  public static final Color E_TRANS_RESET_COLOR = E_TRANS_LIGHT_GREEN;
  public static final Color E_TRANS_PLAY_AREA_COLOR = Color.gray;
  public static final Color E_TRANS_AXES_COLOR = Color.black;
  public static final Color E_TRANS_HOUSE_COLOR = Color.pink;
  public static final Color E_TRANS_PALETTE_AREA_COLOR = E_TRANS_LIGHT_BLUE;
  public static final Color E_TRANS_PIPELINE_AREA_COLOR = E_TRANS_LAVENDAR;
  public static final Color E_TRANS_PLAY_OBJECT_COLOR_1 = Color.red;
  public static final Color E_TRANS_PLAY_OBJECT_COLOR_2 = Color.blue;
  public static final Color E_TRANS_PLAY_OBJECT_COLOR_3 = Color.green;
  public static final Color E_TRANS_GOAL_OBJECT_COLOR_1 = new Color(100, 0, 0);
  public static final Color E_TRANS_GOAL_OBJECT_COLOR_2 = new Color(0, 0, 100);
  public static final Color E_TRANS_GOAL_OBJECT_COLOR_3 = new Color(0, 100, 0);
  public static final Color E_TRANS_GHOST_OBJECT_COLOR_1 = new Color(255, 255, 255);
  public static final Color E_TRANS_GHOST_OBJECT_COLOR_2 = new Color(0, 0, 0);
  public static final Color E_TRANS_GHOST_OBJECT_COLOR_3 = new Color(100, 100, 100);
  public static final Color E_TRANS_PALETTE_PIECE_OUTLINE_COLOR = E_TRANS_BROWN;
  public static final Color E_TRANS_PALETTE_PIECE_FILL_COLOR = Color.pink;
  public static final Color E_TRANS_PALETTE_PIECE_FILL_COLOR_SPECIAL = E_TRANS_LIGHT_YELLOW;
  public static final Color E_TRANS_PALETTE_PIECE_TEXT_COLOR = Color.black;
  public static final Color E_TRANS_ANCHOR_COLOR = Color.white;
  public static final Color E_TRANS_ANCHOR_COLOR_SPECIAL = Color.yellow;
  public static final Color E_TRANS_ANCHOR_OUTLINE_COLOR = E_TRANS_BROWN;
  public static final Color E_TRANS_LEVEL_NUM_TEXT_COLOR = Color.white;
  public static final Color E_TRANS_AXES_LABEL_COLOR = Color.white;
  public static final Color E_TRANS_HOUSE_WIN_COLOR_1 = new Color(255, 100, 100);
  public static final Color E_TRANS_HOUSE_WIN_COLOR_2 = new Color(100, 255, 100);
  public static final Color E_TRANS_HOUSE_WIN_COLOR_3 = new Color(100, 100, 255);
  public static final int IT_NO_MOVE = 1;
  public static final int IT_TRANSLATE = 2;
  public static final int IT_X_TRANSLATE = 3;
  public static final int IT_Y_TRANSLATE = 4;
  public static final int IT_SCALE = 5;
  public static final int IT_X_SCALE = 6;
  public static final int IT_Y_SCALE = 7;
  public static final int IT_ROTATE = 8;
  public static final int IT_TRANSLATE_VARY = 9;
  public static final int IT_SCALE_VARY = 10;
  public static final int IT_ROTATE_VARY = 11;
  public static final int IT_X_TRANSLATE_VARY = 12;
  public static final int IT_Y_TRANSLATE_VARY = 13;
  public static final int IT_X_SCALE_VARY = 14;
  public static final int IT_Y_SCALE_VARY = 15;
  public static final int IT_NONE = 99;
  public static final int E_TRANS_NUM_ANIMATION_TICKS = 50;
  public static final int E_TRANS_HALF_ANIMATION_TICKS = 25;
  public static final int E_TRANS_VARY_FACTOR = 25;
  public static final double E_TRANS_GENERAL_EPSILON = 0.08D;
  public static final double E_TRANS_TRANSLATION_EPSILON = 3.0D;
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransConst.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */