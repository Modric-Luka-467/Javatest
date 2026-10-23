public class PaymentProcessor {
    public void pay(double amount,String transactionId)throws InsufficientFundsException{
        if (amount<0){
            throw new IllegalArgumentException("支付金额不能为负数");
        }
        if (amount>100){
            throw new InsufficientFundsException("余额不足",transactionId);
        }
        if (amount==500){
            NullPointerException npe = new NullPointerException();
            throw new SystemMaintenanceException("网络连接超时",npe);
        }
        System.out.printf("交易[%s]支付成功,金额:[%f]%n",transactionId,amount);
    }
}
