package homework;


import java.util.*;

public class CustomerService {

    private final NavigableMap<Customer, String> customerStringNavigableMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> data = customerStringNavigableMap.firstEntry();

        return getCustomerStringEntry(data);
    }

    private Map.Entry<Customer, String> getCustomerStringEntry(Map.Entry<Customer, String> data) {
        if (data != null) {
            Customer keyCustomer = data.getKey();
            Map.Entry<Customer, String> updatedData =
                    new AbstractMap.SimpleEntry<>(
                            new Customer(keyCustomer.getId(),keyCustomer.getName(),keyCustomer.getScores()), data.getValue());
            return updatedData;
        } else return null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> data = customerStringNavigableMap.higherEntry(customer);

        return getCustomerStringEntry(data);
    }

    public void add(Customer customer, String data) {
        customerStringNavigableMap.put(customer, data);
    }

}
