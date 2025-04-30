package xyz.wyan.extraitems.Utils;


import java.util.List;
import java.util.Map;

public class Experience {
    /**
     * Get the total experience of a player at a given level and progress.
     *
     * @param level   The player's level.
     * @param progress The player's progress towards the next level (0.0 to 1.0).
     * @return The total experience of the player.
     */
    public static int getTotalExperience(int level, float progress){
        return (int) (getTotalExperienceToLevel(level) + progress * getExperienceToNextLevel(level));
    }

    public static List<Float> getExperience(int exp){
        int level = getLevelFromExperience(exp);
        float progress = getProgressFromExperience(exp, level);
        return List.of((float) level, progress);

    }

    private static int getTotalExperienceToLevel(int level){
        if(level < 17){
            return (int) (Math.pow(level, 2) + 6*level);
        }else if (level<32){
            return (int) (2.5*Math.pow(level, 2) - 40.5*level + 360);
        }else{
            return (int) (4.5*Math.pow(level, 2) - 162.5*level + 2220);
        }
    }
    private static int getExperienceToNextLevel(int level){
        if(level < 16){
            return 2*level+7;
        }else if (level<31) {
            return 5*level-38;
        }else{
            return 9*level-158;
        }
    }

    public static int getLevelFromExperience(int exp) {
        if (exp < 353) {
            return (int) (Math.sqrt(exp + 9) - 3);
        } else if (exp < 1507) {
            return (int) (8.1 + Math.sqrt(0.4 * (exp - (7839.0 / 40))));
        } else {
            return (int) ((325.0 / 18) + Math.sqrt((2.0 / 9) * (exp - (54215.0 / 72))));
        }
    }
    public static float getProgressFromExperience(int exp, int level) {
        int totalExp = getTotalExperienceToLevel(level);
        int expToNextLevel = getExperienceToNextLevel(level);
        return ((float) exp - totalExp) / expToNextLevel;
    }
}
