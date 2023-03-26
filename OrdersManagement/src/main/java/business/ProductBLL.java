package business;

import dao.ProductDao;
import model.Client;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    /**
     *
     */
    private ProductDao productDAO;

    /**
     *
     */
    public ProductBLL() {
        productDAO = new ProductDao();
    }

    /**
     *
     * @param id
     * @return
     */
    public Product findProductById(int id) {
        if (productDAO.findById(id) == null)
            throw new NoSuchElementException("There's no product with this id");
        return productDAO.findById(id);

    }

    /**
     *
     * @return
     */
    public List<Product> findAll(){

        return productDAO.findAll();
    }

    /**
     *
     * @param product
     * @param id
     * @return
     */
    public Product insert(Product product, int id){
        return productDAO.insert(product, id);
    }

    /**
     *
     * @param product
     * @param id
     * @return
     */
    public Product update(Product product, int id){
        return productDAO.update(product,id);
    }

    /**
     *
     * @param product
     * @param id
     * @return
     */
    public boolean delete(Product product, int id){
        return productDAO.delete(id);
    }


}
