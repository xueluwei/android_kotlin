//package com.example.lib;
//
//import java.util.Arrays;
//import java.util.Scanner;
//
//public class TowClass {
//    public static void main(String args[]){
//        int[] a = new int[1000];
//        for (int i = 0; i < 999; i++){
//            a[i] = -1;
//        }
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNext()) {
//            String next1 = scanner.nextLine();
//            String s = next1.split(":--")[1];
//            for (int i = 0; i < 999; i++){
//                if(a[i] == -1){
//                    a[i] = Integer.parseInt(s);
//                    System.out.println(a[i]);
//                    break;
//                }
//                if (a[i] == Integer.parseInt(s)){
//                    break;
//                }
//            }
//        }
//        for (int i = 0; i < 999; i++){
//            if(a[i] == -1 ){
//                break;
//            }
//            System.out.println(a[i]);
//        }
//    }
//}
