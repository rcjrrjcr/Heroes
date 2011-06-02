package com.nijikokun.register.payment.methods;

import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.BankAccount;
import com.iConomy.system.Holdings;
import com.iConomy.util.Constants;
import com.nijikokun.register.payment.Method;

public class iCo5 implements Method {
    private iConomy iConomy;

    @Override
    public iConomy getPlugin() {
        return this.iConomy;
    }

    @Override
    public String getName() {
        return "iConomy";
    }

    @Override
    public String getVersion() {
        return "5";
    }

    @Override
    @SuppressWarnings("static-access")
    public String format(double amount) {
        return this.iConomy.format(amount);
    }

    @Override
    public boolean hasBanks() {
        return Constants.Banking;
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean hasBank(String bank) {
        return (!hasBanks()) ? false : this.iConomy.Banks.exists(bank);
    }

    @Override
    @SuppressWarnings("static-access")
    public boolean hasAccount(String name) {
        return this.iConomy.hasAccount(name);
    }

    @Override
    @SuppressWarnings("static-access")
    public boolean hasBankAccount(String bank, String name) {
        return (!hasBank(bank)) ? false : this.iConomy.getBank(bank).hasAccount(name);
    }

    @Override
    @SuppressWarnings("static-access")
    public MethodAccount getAccount(String name) {
        return new iCoAccount(this.iConomy.getAccount(name));
    }

    @Override
    @SuppressWarnings("static-access")
    public MethodBankAccount getBankAccount(String bank, String name) {
        return new iCoBankAccount(this.iConomy.getBank(bank).getAccount(name));
    }

    @Override
    public boolean isCompatible(Plugin plugin) {
        return plugin.getDescription().getName().equalsIgnoreCase("iconomy") && plugin.getClass().getName().equals("com.iConomy.iConomy") && plugin instanceof iConomy;
    }

    @Override
    public void setPlugin(Plugin plugin) {
        iConomy = (iConomy) plugin;
    }

    public class iCoAccount implements MethodAccount {
        private Account account;
        private Holdings holdings;

        public iCoAccount(Account account) {
            this.account = account;
            this.holdings = account.getHoldings();
        }

        public Account getiCoAccount() {
            return account;
        }

        @Override
        public double balance() {
            return this.holdings.balance();
        }

        @Override
        public boolean set(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.set(amount);
            return true;
        }

        @Override
        public boolean add(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.add(amount);
            return true;
        }

        @Override
        public boolean subtract(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.subtract(amount);
            return true;
        }

        @Override
        public boolean multiply(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.multiply(amount);
            return true;
        }

        @Override
        public boolean divide(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.divide(amount);
            return true;
        }

        @Override
        public boolean hasEnough(double amount) {
            return this.holdings.hasEnough(amount);
        }

        @Override
        public boolean hasOver(double amount) {
            return this.holdings.hasOver(amount);
        }

        @Override
        public boolean hasUnder(double amount) {
            return this.holdings.hasUnder(amount);
        }

        @Override
        public boolean isNegative() {
            return this.holdings.isNegative();
        }

        @Override
        public boolean remove() {
            if (this.account == null)
                return false;
            this.account.remove();
            return true;
        }
    }

    public class iCoBankAccount implements MethodBankAccount {
        private BankAccount account;
        private Holdings holdings;

        public iCoBankAccount(BankAccount account) {
            this.account = account;
            this.holdings = account.getHoldings();
        }

        public BankAccount getiCoBankAccount() {
            return account;
        }

        @Override
        public String getBankName() {
            return this.account.getBankName();
        }

        @Override
        public int getBankId() {
            return this.account.getBankId();
        }

        @Override
        public double balance() {
            return this.holdings.balance();
        }

        @Override
        public boolean set(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.set(amount);
            return true;
        }

        @Override
        public boolean add(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.add(amount);
            return true;
        }

        @Override
        public boolean subtract(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.subtract(amount);
            return true;
        }

        @Override
        public boolean multiply(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.multiply(amount);
            return true;
        }

        @Override
        public boolean divide(double amount) {
            if (this.holdings == null)
                return false;
            this.holdings.divide(amount);
            return true;
        }

        @Override
        public boolean hasEnough(double amount) {
            return this.holdings.hasEnough(amount);
        }

        @Override
        public boolean hasOver(double amount) {
            return this.holdings.hasOver(amount);
        }

        @Override
        public boolean hasUnder(double amount) {
            return this.holdings.hasUnder(amount);
        }

        @Override
        public boolean isNegative() {
            return this.holdings.isNegative();
        }

        @Override
        public boolean remove() {
            if (this.account == null)
                return false;
            this.account.remove();
            return true;
        }
    }
}
