//package com.parking.parking.controller.utils;
//
//import java.io.*;
//
//public class PythonRunner {
//    public static void main(String[] args) throws IOException {
//        ProcessBuilder pb = new ProcessBuilder("python", "/home/martin/Desktop/Python/file.py");
//        Process p = pb.start();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//    }
//}
