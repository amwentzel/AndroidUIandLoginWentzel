package sweng888.psu.edu.androiduiandlogin_wentzel;

import java.util.ArrayList;

public interface IPersistence<T> {

    public void insert(T t);

    public void delete(T t);

    public void edit (T t);

    public ArrayList<T> getDataFromDB();

}