package business;

import model.*;

import java.io.File;
import java.io.IOException;

public class OrderBLL {
    private OrderDAO orderDAO;

    /**
     *
     * @return
     */

    public List<Order> findAll(){

        return orderDAO.findAll();
    }

    /**
     *
     * @param order
     * @param id
     * @return
     */
    public Order insert(Order order, int id){
        return orderDAO.insert(order, id);
    }

    /**
     *
     * @param order
     * @param id
     * @return
     */
    public Order update(Order order, int id){
        return orderDAO.update(order,id);
    }

    /**
     *
     * @param order
     * @param id
     * @return
     */
    public boolean delete(Order order, int id){
        return orderDAO.delete(id);
    }

}
