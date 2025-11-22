package io.github.towerdefense.logic;

public class Util {
    public static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
