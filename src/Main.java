import java.util.ArrayList;
import java.util.Map;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[]args){
        TransportVehicle truck = new Truck("T-998");
        TransportVehicle drone = new Drone("D-007");
        double weight = 10;

        GPSLocatable truckGPs = (GPSLocatable)truck;
        GPSLocatable droneGPs = (GPSLocatable)drone;
        System.out.printf("卡车[%s]当前位置:%s | 配送%.0fkg货物费用:%.1f元\n",truck.vehicleId,truckGPs.getLocation(),weight,truck.calculateCost(weight));
        System.out.printf("无人机[%s]当前位置:%s | 配送%.0fkg货物费用:%.1f元\n",drone.vehicleId,droneGPs.getLocation(),weight,drone.calculateCost(weight));


        StorageBox<Product> productBox = new StorageBox<>();
        StorageBox<TransportVehicle> deviceBox = new StorageBox<>();
        Product product = new Product("P001","机械键盘",299.0);
        TransportVehicle droneDevice = new Drone("D-007");
        productBox.storeItem(product);
        deviceBox.storeItem(droneDevice);
        Product getProduct = productBox.retrieveItem();
        TransportVehicle getDevice = deviceBox.retrieveItem();
        System.out.println("\n商品储物箱存取测试:成功取出商品 ->"+getProduct.getName());
        System.out.println("设备储物箱存取测试:成功取出设备 ->无人机["+getDevice.vehicleId+"]");

        MyArrayList<String>list=new
                MyArrayList<>();
        list.add("单号A");
        list.add("单号B");
        list.add("单号C");
        list.add("单号D");
        list.add("单号E");
        System.out.println("\n获取索引[2]的元素:"+list.get(2));
        list.remove(1);
        System.out.println("验证移位，此时索引[1]的元素变为了:"+list.get(1));

        LogisticsConfig a=LogisticsConfig.getInstance();
        LogisticsConfig b=LogisticsConfig.getInstance();
        System.out.println("单例测试:配置类实例A与实例B是否相同? ->"+(a==b));
        TransportVehicle vehicle = VehicleFactory.createVehicle("Drone");
        System.out.print("工厂调度:工厂成功分配 ->");
        double cost = vehicle.calculateCost(10);
        System.out.println("无人机["+vehicle.vehicleId+"],计费启动...");
        LogisticsOrder order = new LogisticsOrder.Builder("0D-999").sender("Alice").receiver("Bob").fragile(true).insureMoney(1000.0).build();
        System.out.println(order);

        PaymentService service = new PaymentService();
        try{
            service.doPay(-100,"T001");
        }catch (SystemMaintenanceException e){
            System.out.println("捕获包装后的异常:"+e.getMessage());
        }
        service.doPay(12000,"T002");
        try {
            service.doPay(500,"T003");
        }catch (SystemMaintenanceException e){
            System.out.println("捕获网络异常:"+e.getMessage());
        }
        service.doPay(2000,"T004");

        OrderAnalyzer analyzer = new OrderAnalyzer();
        analyzer.add(new Order("0001","Alice",150.0,Arrays.asList("电子","数码")));
        analyzer.add(new Order("0002","Bob",-50.0,Arrays.asList("服装")));
        analyzer.add(new Order("0003","Alice",200.0,Arrays.asList("图书","电子")));
        analyzer.add(new Order("0004","Charlie",200.0,Arrays.asList("生鲜")));
        analyzer.add(new Order("0005","Bob",300.0,Arrays.asList("服装","户外")));
        System.out.println("====原始订单====");
        for (Order o : analyzer.getOrderList()){
            System.out.println(o);
        }
        analyzer.removeInvalidOrders();
        System.out.println("\n====删除金额<=订单后====");
        for (Order o : analyzer.getOrderList()){
            System.out.println(o);
        }
        Set<String> tags = analyzer.getAllUniqueTags();
        System.out.println("\n====全部唯一标签===="+tags);
        Map<String,Double> spendMap = analyzer.getCustomerSpendMap();
        System.out.println("\n====客户消费总额===="+spendMap);
        List<Order> sortedByName = analyzer.sortOrdersByCustomerName();
        System.out.println("\n====按客户姓名升序====");
        for (Order o : sortedByName){
            System.out.println(o);
        }
        List<Order> defaultSort = new ArrayList<>(analyzer.getOrderList());
        Collections.sort(defaultSort);
        System.out.println("\n====默认排序(金额降序)====");
        for (Order o : defaultSort){
            System.out.println(o);
        }
    }
}