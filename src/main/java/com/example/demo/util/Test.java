package com.example.demo.util;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        if(s.hasNext()){
            int n = s.nextInt();
            System.out.println("k = "+test(n));
        }
    }
    public static int test(int n){
        int i=1 ,j =0 , k= 1;
        while (true){
            if(i == n){
                break;
            }
            if (j == 0){
                j = k;
                k=k+1;
            }else if(j>0){
                j--;
            }
            i++;
        }
        return  k;
    }
}
