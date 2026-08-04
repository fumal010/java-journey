package com.learn.app;

import com.learn.bank.*;

public class Day14Demo {

    public static void main(String[] args) {
        BankAccount acc = new BankAccount("1234567890");
        acc.deposit(1000);
        acc.withdraw(500);
//        acc.withdraw(0);
//        acc.withdraw(5000);
        System.out.println(acc.getBalance());
        System.out.println(acc.toString());
    }
}