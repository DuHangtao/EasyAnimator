package cs3500.animator.model.util;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class for storing static utility methods. Given its own package to assign a namespace to be
 * referenced.
 */
public interface Util {

  static String newLine() {
    return System.getProperty("line.separator");
  }

  /**
   * Gets a String describing the Color in RGB values each between 0 and 1.
   *
   * @param col the Color to describe
   * @return the String formatted as (R,G,B) where each value is between 0 and 1
   */
  static String colString(Color col) {
    float[] com = col.getColorComponents(null);
    return "(" + com[0] + "," + com[1] + "," + com[2] + ")";
  }

  /**
   * Gets a String describing the Color in RGB values each between 0 and 1.
   *
   * @param col the Color to describe
   * @return the String formatted as (R,G,B) where each value is between 0 and 1
   */
  static String colRGBString(Color col) {
    float[] com = col.getColorComponents(null);
    return "(" + (int) (com[0] * 255) + "," + (int) (com[1] * 255) + "," +
            (int) (com[2] * 255) + ")";
  }

  static Color stringToColor(String col) {
    /*Color[] colors = new Color[]{Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.gray, Color.green, Color.yellow, Color.lightGray,
            Color.magenta, Color.orange, Color.pink, Color.red, Color.white};*/
    Map<String, Color> colors = new TreeMap<>();
    colors.put("Black", Color.black);
    colors.put("Blue", Color.blue);
    colors.put("Cyan", Color.cyan);
    colors.put("DarkGray", Color.darkGray);
    colors.put("Gray", Color.gray);
    colors.put("Green", Color.green);
    colors.put("Yellow", Color.yellow);
    colors.put("LightGray", Color.lightGray);
    colors.put("Magenta", Color.magenta);
    colors.put("Orange", Color.orange);
    colors.put("Red", Color.red);
    colors.put("Pink", Color.pink);
    colors.put("White", Color.white);
    if (colors.containsKey(col)) {
      return colors.get(col);
    } else {
      return Color.white;
    }
  }
}
