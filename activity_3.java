package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Department in an organisation
 */
public class activity_3 {

    private String name;

    private ArrayList<Integer> ID1 = new ArrayList<Integer>(); // Should be an integer instead, not string, mistmatched
                                                               // when we call AssignNew(String temp2, int ID)
    // private ArrayList<String> ID1 = new ArrayList<String>();

    private ArrayList<String> temp1 = new ArrayList<String>();

    private ArrayList<Integer> employee = new ArrayList<Integer>(); // should be Integer instead, not int
    // private ArrayList<int> employee = new ArrayList<int>();

    private int ID2;

    // private String name; Remove this, redundant data.

    public String getID() {
        return ID2;
    }

    public void setID(int ID) {
        this.ID2 = ID;
    }

    // See below for better refracting, removing redundant data, p=1.
    public boolean checkEmployee(int eID) {
        // Use an enhanced for loop to iterate through 'employee' list
        for (int i = 0; i < this.employee.size(); i++) {
            check = this.employee.get(i);
            if (check == eID) {
                // Directly prints and return true, if we find the employee.
                System.out.println("Employee exists");
                return true;
            }
        }
        // If the loop completes without finding a matching ID, indicate that the
        // employee doesn't exist
        System.out.println("Employee doesn't exists");
        return false;
    }

    public void assignNew(String temp2, int ID) {
        this.temp1.add(temp2);
        this.ID1.add(ID);
    }

}
