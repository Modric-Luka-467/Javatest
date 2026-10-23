import java.util.*;
import java.util.stream.Collectors;
public class OrderAnalyzer {
    private List<Order> orderList = new ArrayList<>();
    public void add(Order order){
        orderList.add(order);
    }
    public void removeInvalidOrders(){
        orderList.removeIf(order ->order.getAmount()<=0);
    }
    public Set<String> getAllUniqueTags(){
        Set<String> tagSet = new HashSet<>();
        for(Order o : orderList){
            tagSet.addAll(o.getTags());
        }
        return tagSet;
    }
    public Map<String,Double> getCustomerSpendMap(){
        Map<String,Double> map = new HashMap<>();
        for(Order o : orderList){
            String name = o.getCustomerName();
            double money = o.getAmount();
            map.put(name,map.getOrDefault(name,0.0)+money);
        }
        return map;
    }
    public List<Order> sortOrdersByCustomerName(){
        List<Order> newList = new ArrayList<>(orderList);
        newList.sort(Comparator.comparing(Order::getCustomerName));
        return newList;
    }
    public List<Order> getOrderList(){
        return orderList;
    }
}
