/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Random;

/**
 *
 * @author Nhu Quynh
 */
public class Helper {

    private static final String PREFIX = "HD";
    private static final int LENGTH = 5;

    public static String generateRandomMaHoaDon() {
        Random random = new Random();

        int number = random.nextInt((int) Math.pow(10, LENGTH));
        return PREFIX + String.format("%0" + LENGTH + "d", number);
    }

}
