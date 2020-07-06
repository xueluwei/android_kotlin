//package com.example.lib;
//
//import java.math.BigDecimal;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.sql.DriverManager.println;
//
///**
// * Created by yh on 2020/1/6
// **/
//public class RunDpSp {
//    private static final int STANDARD_DEVICE_SIZE = 375;  //dp
//
//    private static final List<Integer> GENERATE_DP_ARRAY = new ArrayList<>();
//    private static final List<Integer> GENERATE_SP_ARRAY = new ArrayList<>();
//
//    private static final List<Integer> GENERATE_DEVICE_SIZE_ARRAY = new ArrayList<>();
//
//
//    public static void main(String[] args) {
//        GENERATE_DEVICE_SIZE_ARRAY.add(270);
//        GENERATE_DEVICE_SIZE_ARRAY.add(320);
//        GENERATE_DEVICE_SIZE_ARRAY.add(360);
//        GENERATE_DEVICE_SIZE_ARRAY.add(375);
//        GENERATE_DEVICE_SIZE_ARRAY.add(480);
//
//
////        for (int i = 100; i < 300; i++) {
////            GENERATE_DP_ARRAY.add(i);
////        }
////
////
////        for (int i = 0; i < 101; i++) {
////            GENERATE_SP_ARRAY.add(i);
////        }
//
//        GENERATE_DP_ARRAY.add(240);
//
//        for (Integer deviceSize:GENERATE_DEVICE_SIZE_ARRAY) {
//            float ratioFactor = 1.0f * deviceSize / STANDARD_DEVICE_SIZE;
//
//            System.out.print("------" + deviceSize + "dp------\n");
//            for (Integer dpSize : GENERATE_DP_ARRAY) {
//                System.out.println("<dimen name=\"dp_"+ dpSize +"\"> " + new BigDecimal(dpSize * ratioFactor).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() + "dp</dimen>");
//            }
//
//            System.out.print("------" + deviceSize + "sp------\n");
//            for (Integer spSize : GENERATE_SP_ARRAY) {
//                System.out.println("<dimen name=\"sp_" + spSize +"\"> " + new BigDecimal(spSize * ratioFactor).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() +"sp</dimen>");
//            }
//
//            System.out.print("\n\n\n");
//        }
//    }
//}
