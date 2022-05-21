package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerStringTreeMap;

    public CustomerService() {
        customerStringTreeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        var data = customerStringTreeMap.firstEntry();
        return new AbstractMap.SimpleEntry<>(new Customer(data.getKey()), data.getValue()) ; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var data = customerStringTreeMap.higherEntry(customer);
        if (data == null)
            return null;
        return new AbstractMap.SimpleEntry<>(new Customer(data.getKey()), data.getValue()); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customerStringTreeMap.put(customer, data);
    }

}
