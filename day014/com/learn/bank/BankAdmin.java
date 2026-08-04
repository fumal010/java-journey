package com.learn.bank;

public class BankAdmin {

   BankAccount account = new BankAccount("1234567890");
   
    void forceReset(BankAccount acc) {
        acc.auditReset();
    }
}