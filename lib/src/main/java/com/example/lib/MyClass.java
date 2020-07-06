package com.example.lib;

import java.util.Scanner;

public class MyClass {

    public static void main(String args[]){
        new ReadServiceLanguages(ReadServiceLanguages.BASE_SRC_PATH,ReadServiceLanguages.BASE_DES_PATH,ReadServiceLanguages.languagesGradle).startCopyFile();
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()){
//            String next1 = scanner.nextLine();
//            if(next1.contains("    //VK")){
//                System.out.println(next1);
//                String a = ("\n    //huawei\n");
//                while (scanner.hasNext()) {
//                    String nextLine = scanner.nextLine();
//                    if(nextLine.contains("//-")){
//                        System.out.println(nextLine);
//                        a += "\n" + nextLine;
//                        break;
//                    }else {
//                        String buffer = nextLine.split("=")[0].replace("VK","HW");
//                        if (buffer.contains("private")){
//                            a += "\n" +  buffer + " = \"\";";
//                        }else {
//                            a += "\n";
//                        }
//                        System.out.println(nextLine);
//                    }
//                }
//                System.out.println(a);
//            }else if(next1.contains("new Vk")){
//                String buf = next1.replace("Vk", "Huawei").replace("VK_", "HW_");
//                System.out.println(buf);
//                System.out.println(next1);
//            }else if(next1.contains("Id vk")){
//                String buf = next1.replace("Vk", "Huawei").replace("vk", "huawei");
//                System.out.println(buf);
//                System.out.println(next1);
//            }else {
//                System.out.println(next1);
//            }

//        }
    }
}
