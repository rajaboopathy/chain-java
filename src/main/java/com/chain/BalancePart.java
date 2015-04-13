package com.chain;

import java.math.BigInteger;

public class BalancePart {
    public BigInteger balance;
    public BigInteger deposits;
    public BigInteger withdrawals;

    public BigInteger getBalance() {
        return balance;
    }

    public BigInteger getDeposits() {
        return deposits;
    }

    public BigInteger getWithdrawals() {
        return withdrawals;
    }
}
