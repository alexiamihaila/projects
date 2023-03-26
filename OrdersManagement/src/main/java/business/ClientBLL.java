package business;

import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    ClientDAO clientDAO;

    /**
     *
     */
    public ClientBLL(){
        clientDAO = new ClientDAO();
    }

    /**
     *
     * @return
     * @throws NoSuchFieldException
     */
    public List<Client> findAll() throws NoSuchFieldException{
        List<Client> clientList = clientDAO.findAll();
        if (clientList != null) {
            return clientList;
        }
        else throw new NoSuchFieldException();

    }

    /**
     *
     * @param id
     * @return
     */
    public Client findClientById(int id) {
        if (clientDAO.findById(id) == null)
            throw new NoSuchElementException("There's no client with this id");
        return clientDAO.findById(id);

    }

    /**
     *
     * @param client
     * @param id
     * @return
     */
    public Client insert(Client client, int id){
        return clientDAO.insert(client, id);
    }

    /**
     *
     * @param client
     * @param id
     * @return
     */
    public Client update(Client client, int id){

        return clientDAO.update(client, id);
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id){

        return clientDAO.delete(id);
    }

}