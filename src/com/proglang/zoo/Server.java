package com.proglang.zoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int A = constants.A;
    private static final int Z = constants.Z;
    private static final int numLetters = constants.numLetters;
    private static Account[] accounts;
    private static ExecutorService mExecutor;

    /**
     * Print out a summary of all the accounts
     */
    private static void dumpAccounts() {
        // output values:
        for (int i = A; i <= Z; i++) {
            System.out.print("    ");
            if (i < 10) System.out.print("0");
            System.out.print(i + " ");
            System.out.print(new Character((char) (i + 'A')) + ": ");
            accounts[i].print();
            System.out.print(" (");
            accounts[i].printMod();
            System.out.print(")\n");
        }
    }

    /**
     * Read in the transactions from the given
     * argument file. Execute the transactions
     * in parallel then print the account
     * summary.
     */
    public static void main (String args[])
        throws IOException {
        accounts = new Account[numLetters];
        for (int i = A; i <= Z; i++) {
            accounts[i] = new Account(Z-i);
        }
        
        mExecutor = Executors.newFixedThreadPool(4);

        // read transactions from input file
        String line;
        BufferedReader input =
            new BufferedReader(new FileReader(args[0]));

        while ((line = input.readLine()) != null) {
            Worker w = new Worker(accounts, line);
            mExecutor.execute(w);
        }
        mExecutor.shutdown();
        input.close();
        while (!mExecutor.isTerminated()) {
        }
    	System.out.println("final values:");
    	dumpAccounts();
    }
}