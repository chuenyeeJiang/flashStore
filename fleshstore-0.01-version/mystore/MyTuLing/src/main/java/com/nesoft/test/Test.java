package com.nesoft.test;

import com.nesoft.tulinguntil.TuLingUntil;

import java.util.Scanner;

public class Test {
    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
         while(true){
             String message = scanner.nextLine();
             String result = TuLingUntil.getResult(message);
             System.out.println(result);
         }
    }
}
