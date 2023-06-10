/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package stack9;

/**
 *
 * @author zahra
 */
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

class editAcct {

    public static void main(String[] args) {

        editAccount user = new editAccount("Azalea");

        // display options
        System.out.println("Edit Account:");
        System.out.println("1) Display job history");
        System.out.println("2) Add current job");
        System.out.println("3) Remove current job");
        System.out.println("4) Clear job history");
        System.out.println("5) Display hobbies");
        System.out.println("6) Add a new hobby");
        System.out.println("7) Remove a hobby");
        System.out.println("8) Clear hobbies");
        System.out.println("9) Update your current relationship status");
        System.out.println("10) Exit");

        user.addStatus();

        Scanner sc = new Scanner(System.in);
        int editWhat = sc.nextInt();
        sc.nextLine();

        while (editWhat != 10) {
            switch (editWhat) {
                // for jobs
                case 1: 
                    user.displayJobs();
                    break;

                case 2: 
                    System.out.println("Enter your current job: ");
                    String newJob = sc.nextLine();
                    user.addCurrentJob(newJob);
                    break;
                
                case 3:
                    user.removeCurrentJob();
                    break;

                case 4:
                    user.clearAllJobs();
                    break;

                // for hobbies
                case 5:
                    user.displayHobbies();
                    break;

                case 6: 
                    System.out.println("Enter your new hobby: ");
                    String newHobby = sc.nextLine();
                    user.addNewHobby(newHobby);
                    break;

                case 7: 
                    System.out.println("No. of hobby to be removed: ");
                    int indexRemoveHobby = sc.nextInt();
                    sc.nextLine();
                    user.removeHobby(indexRemoveHobby);
                    break;
                
                case 8: 
                    user.clearAllHobbies();
                    break;

                case 9: 
                    user.displayAllStatus();
                    System.out.println("Choose status: ");
                    int indexStatus = sc.nextInt();
                    sc.nextLine();
                    user.chooseStatus(indexStatus);
                    break;

                default:
                    break;
            }
            editWhat = sc.nextInt();
            sc.nextLine();
        }
        sc.close();

    }

    public static class editAccount {
        // declare variables
        private Stack<String> jobStack = new Stack<>();
        private ArrayList<String> hobbyList = new ArrayList<>();
        private ArrayList<String> relayStatus = new ArrayList<>();

        // constructor
        public editAccount(String username) {
        }

        // job history methods

        public void displayJobs() {
            if (jobStack.isEmpty() == true)
                System.out.println("No job history");
            else
                System.out.println("Jobs: " + jobStack);
        }

        public void viewCurrentJob() {
            jobStack.peek();
        }

        public void addCurrentJob(String newJob) {
            jobStack.push(newJob);
        }

        public void removeCurrentJob() {
            jobStack.pop();
        }

        public void clearAllJobs() {
            while (jobStack.isEmpty() == false) {
                jobStack.pop();
            }
        }

        // hobbies methods
        public void displayHobbies() {
            if (hobbyList.isEmpty() == true)
                System.out.println("There are no hobbies");
            else
                System.out.println("Hobbies: " + hobbyList);
        }

        public void addNewHobby(String newHobby) {
            hobbyList.add(newHobby);
        }

        public void removeHobby(int index) {
            hobbyList.remove(index + 1);
        }

        public void clearAllHobbies() {
            hobbyList.clear();
        }

        // relationship status
        public void addStatus() {
            relayStatus.add("Single");
            relayStatus.add("In a relationship");
            relayStatus.add("Engaged");
            relayStatus.add("Married");
            relayStatus.add("It's complicated");
            relayStatus.add("Widowed");
        }

        public void displayAllStatus() {
            System.out.println("Statuses: " + relayStatus);
        }

        public void chooseStatus(int index) {
            relayStatus.get(index + 1);
        }
    }
}
