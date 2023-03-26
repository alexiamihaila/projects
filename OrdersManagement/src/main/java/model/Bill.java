package model;

import business.ClientBLL;
import business.ProductBLL;

import java.io.IOException;

public class Bill {

    private FileWriter f = new FileWriter();
    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();

    public void createBill(Order order) throws IOException {

        Client client = clientBLL.findClientById(order.getClient());
        Product product = productBLL.findProductById(order.getProduct());

        int price = product.getPrice() * order.getQuantity();

        String s = "Client id: " + client.getId() + "\nClient name: " + client.getNume() +
                "\nClient address: " + client.getAddress() + "\nClient email: " + client.getEmail()+
                "\nProduct id: " + product.getProdId() + "\nProduct name: " + product.getName() +
                "\n\nOrder quantity is: " + order.getQuantity() +
                "\nTOTAL price: " + price;

        f.writeInFile(s);

    }
}
