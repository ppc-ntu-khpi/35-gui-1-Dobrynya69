## Результат. Інформація про користувача
<img src="/res1.png">

## Результат. Додана функція "Report"
<img src="/res2.png">

## Код. Метод main
``` java
public static void main(String[] args) throws IOException {

        File currentClassFile = new File(URLDecoder.decode(SWINGDemo.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
        String classFileDirectory = currentClassFile.getParent();
        DataSource data = new DataSource(classFileDirectory + "\\35-gui-1-Dobrynya69\\test.dat");
        data.loadData();
        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
    }
```

## Код. Доданий код в метод launchFrame
``` java
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
```
