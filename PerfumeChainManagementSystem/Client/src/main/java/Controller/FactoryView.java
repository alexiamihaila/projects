package Controller;

import View.AdminView;
import View.EmployeeView;
import View.ManagerView;
import View.ViewType;

public class FactoryView {

    private String language;
    private int id;

    public FactoryView(String language){
        this.language = language;
    }

    public ViewType getView(String viewType){
        if(viewType == null){
            return null;
        }
        if(viewType.equalsIgnoreCase("ADMIN")){
            return new AdminView(language);

        } else if(viewType.equalsIgnoreCase("MANAGER")){
            return new ManagerView(language);

        } else if(viewType.equalsIgnoreCase("EMPLOYEE")){
            return new EmployeeView(id,language);
        }

        return null;
    }

    public void setId(int id){
        this.id = id;
    }

}
