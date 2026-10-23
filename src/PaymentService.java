public class PaymentService {
    public boolean doPay(double amount,String transactionId){
        PaymentProcessor processor = new PaymentProcessor();
        try {
            processor.pay(amount,transactionId);
            return true;
        }catch (InsufficientFundsException e){
            System.out.printf("余额不足,流水号为[%s]%n",e.getTransactionId());
            return false;
        }catch (IllegalArgumentException e){
            throw new SystemMaintenanceException(e.getMessage(),e);
        }catch (Exception e){
            System.out.printf("发生未知异常:[%s]%n",e.getMessage());
            return false;
        }finally {
            System.out.println("执行资源清理任务...");
        }
    }
}
