package homework;

public class demo {
    public static void main(String[] args) {
        //CustomerReverseOrder customerReverseOrder = new CustomerReverseOrder();
        CustomerService customerService11 = new CustomerService();
        customerService11.add(new Customer(1L,"test", 22L),"dataaaaa");
        customerService11.add(new Customer(2L,"test2", 23L),"dataaaaa2");
        customerService11.add(new Customer(2L,"test2", 2L),"dataaaaa2");
     //   System.out.println(customerService11.customerStringNavigableMap);
       // System.out.println(customerService11.getSmallest());
        System.out.println(customerService11.getNext(new Customer(1L,"test", 22L)));



    }
}
