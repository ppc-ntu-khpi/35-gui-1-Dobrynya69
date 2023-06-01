package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.*;
import com.mybank.reporting.CustomerReport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {
    
    private final JEditorPane log;
    private final JButton show;
    private final JButton report;
    private final JComboBox clients;
    
    public SWINGDemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(450, 350));
        show = new JButton("Show");
        report = new JButton("Report");
        clients = new JComboBox();
        for (int i=0; i<Bank.getNumberOfCustomers();i++)
        {
            clients.addItem(Bank.getCustomer(i).getLastName()+", "+Bank.getCustomer(i).getFirstName());
        }
        
    }
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        cpane.add(report);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(log, BorderLayout.CENTER);
        
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());
                String accType = current.getAccount(0)instanceof CheckingAccount?"Checking":"Savings";                
                String custInfo="<br>&nbsp;<b><span style=\"font-size:2em;\">"+current.getLastName()+", "+
                        current.getFirstName()+"</span><br><hr>"+
                        "&nbsp;<b>Acc Type: </b>"+accType+
                        "<br>&nbsp;<b>Balance: <span style=\"color:red;\">$"+current.getAccount(0).getBalance()+"</span></b>";
                log.setText(custInfo);                
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String custInfo = "";
                custInfo += "<h1>CUSTOMERS REPORT</h1>";

                for(int cust_idx = 0; cust_idx < Bank.getNumberOfCustomers(); ++cust_idx) {
                    Customer customer = Bank.getCustomer(cust_idx);
                    custInfo += "<h5>Customer: " + customer.getLastName() + ", " + customer.getFirstName() + "</h5>";

                    for(int acct_idx = 0; acct_idx < customer.getNumberOfAccounts(); ++acct_idx) {
                        Account account = customer.getAccount(acct_idx);
                        String account_type = "";
                        if (account instanceof SavingsAccount) {
                            account_type = "Savings Account";
                        } else if (account instanceof CheckingAccount) {
                            account_type = "Checking Account";
                        } else {
                            account_type = "Unknown Account Type";
                        }

                        custInfo += "<span>" + account_type + ": current balance is " + account.getBalance() + "<span>";
                    }
                }
                log.setText(custInfo);
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    public static void main(String[] args) throws IOException {

        File currentClassFile = new File(URLDecoder.decode(SWINGDemo.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
        String classFileDirectory = currentClassFile.getParent();
        DataSource data = new DataSource(classFileDirectory + "\\35-gui-1-Dobrynya69\\test.dat");
        data.loadData();
        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
    }
    
}
