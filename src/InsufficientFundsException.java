public class InsufficientFundsException extends Exception{
    private String transactionId;
    public InsufficientFundsException(String message,String transactionId){
        super(message);
        this.transactionId = transactionId;
    }
    public String getTransactionId(){
        return transactionId;
    }
}
