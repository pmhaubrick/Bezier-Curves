package main;

import delegateUI.DisplayGUI;
import model.Model;


public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        new DisplayGUI(model);

    }

}
