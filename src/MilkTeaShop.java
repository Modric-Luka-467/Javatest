public class MilkTeaShop {
    static class BarCounter {
        private int count = 0;
        private final int MAX = 5;
        public synchronized void put(String staffName) throws InterruptedException{
            while (count >= MAX){
                wait();
            }
            count++;
            System.out.printf("[%s]制作了[奶茶],吧台当前数量:[%d]%n",staffName,count);
            notifyAll();
        }
        public synchronized void take(String customerName) throws InterruptedException{
            while (count <= 0){
                wait();
            }
            count--;
            System.out.printf("[%s]取走了[奶茶],吧台剩余数量:[%d]%n",customerName,count);
            notifyAll();
        }
    }
    static class Staff implements Runnable {
        private final BarCounter bar;
        private final String name;
        private int makeTotal = 10;
        public Staff(BarCounter bar,String name){
            this.bar = bar;
            this.name = name;
        }
        public void run(){
            try {
                while (makeTotal > 0){
                    bar.put(name);
                    makeTotal--;
                    Thread.sleep(300);
                }
                System.out.println("====店员 " + name + " 做完10杯奶茶，下班====");
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
    static class Customer implements Runnable {
        private final BarCounter bar;
        private final String name;
        private int drinkTotal = 10;
        public Customer(BarCounter bar,String name){
            this.bar = bar;
            this.name = name;
        }
        public void run() {
            try {
                while (drinkTotal > 0){
                    bar.take(name);
                    drinkTotal--;
                    Thread.sleep(500);
                }
                System.out.println("====顾客 " + name + " 喝完10杯奶茶，满足离开====");
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args){
        BarCounter bar = new BarCounter();
        Thread staff1 = new Thread(new Staff(bar,"店员A"));
        Thread staff2 = new Thread(new Staff(bar,"店员B"));
        Thread cus1 = new Thread(new Customer(bar,"顾客1"));
        Thread cus2 = new Thread(new Customer(bar,"顾客2"));
        staff1.start();
        staff2.start();
        cus1.start();
        cus2.start();
    }
}
