package com.parking.parking.controller.utils;

import com.parking.parking.models.DbConfig;

import java.sql.SQLException;

public class Global{

    public static int[]  availableSlots() throws SQLException {
        DbConfig config = new DbConfig();
        String arr = config.executeQuery("SELECT JSON_ARRAY((SELECT COUNT(*) FROM lot WHERE slot_number LIKE 'C1%'),(SELECT COUNT(*) FROM lot WHERE slot_number LIKE 'F2%'),(SELECT COUNT(*) FROM lot WHERE slot_number LIKE 'F3%') ) AS counts_array;").toString();
//        String arr = "[[0, 0, 11]]";
        String inner = arr.substring(1, arr.length() - 1);
        inner = inner.replace("[", "").replace("]", "");
        String[] parts = inner.split(", ");
        int[] values = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            values[i] = Integer.parseInt(parts[i]);
        }
        return values;

    }
}
