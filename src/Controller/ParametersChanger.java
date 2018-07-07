package Controller;

import View.MainWindow;

import static Controller.DrawPanel.activeShape;

public class ParametersChanger {
    public static void coords(){
        MainWindow.textMinX.setText(String.valueOf((int)activeShape.lxc));
        MainWindow.textMaxX.setText(String.valueOf((int)activeShape.rxc));
        MainWindow.textMinY.setText(String.valueOf((int)activeShape.tyc));
        MainWindow.textMaxY.setText(String.valueOf((int)activeShape.byc));
    }

    public static void fill() {
        switch (MainWindow.fillBox.getSelectedIndex()){
            case 0:
                activeShape.options[5]="0";
                break;
            case 1:
                activeShape.options[5]="1";
                break;
            case 2:
                activeShape.options[5]="20";
                break;
            case 3:
                activeShape.options[5]="21";
                break;
            case 4:
                activeShape.options[5]="23";
                break;
            case 5:
                activeShape.options[5]="22";
                break;
            case 6:
                activeShape.options[5]="24";
                break;
        }
    }

    public static void border() {
        switch (MainWindow.borderBox.getSelectedIndex()){
            case 0:
                activeShape.options[0]="-";
                break;
            case 1:
                activeShape.options[0]="---";
                break;
            case 2:
                activeShape.options[0]="...";
                break;
        }
        activeShape.options[4]=String.valueOf(MainWindow.jSlider2.getValue());
    }

    public static void fillBoxChange(){
        switch (activeShape.options[5]) {
            case "0":
                MainWindow.fillBox.setSelectedIndex(0);
                break;
            case "1":
                MainWindow.fillBox.setSelectedIndex(1);
                break;
            case "20":
                MainWindow.fillBox.setSelectedIndex(2);
                break;
            case "21":
                MainWindow.fillBox.setSelectedIndex(3);
                break;
            case "23":
                MainWindow.fillBox.setSelectedIndex(4);
                break;
            case "22":
                MainWindow.fillBox.setSelectedIndex(5);
                break;
            case "24":
                MainWindow.fillBox.setSelectedIndex(6);
                break;
                        /*
                        case "3":
                            MainWindow.fillBox.setSelectedIndex(7);
                            break;
                            */
        }
    }

}
