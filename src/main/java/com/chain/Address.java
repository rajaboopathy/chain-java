package com.chain;

import java.math.BigInteger;

public class Address {
    public String address;
    public Details total;
    public Details confirmed;

    public String getAddress() {
        return address;
    }

    public Details getTotal() {
        return total;
    }

    public Details getConfirmed() {
        return confirmed;
    }

    static public class Details {
        public BigInteger balance;
        public BigInteger sent;
        public BigInteger received;

        public BigInteger getBalance() {
            return balance;
        }

        public BigInteger getSent() {
            return sent;
        }

        public BigInteger getReceived() {
            return received;
        }
    }
}
