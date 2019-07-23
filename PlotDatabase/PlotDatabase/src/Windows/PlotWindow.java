package Windows;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PlotWindow extends Application {

    /*
        Everything who has a "..2" at the end is for the second tab in the GUI.
        It become so because the fields cannot share the same memory.
     */

    private static SimpleStringProperty staticColumnName = new SimpleStringProperty("");
    private static TableView staticTableView;

    @FXML
    private TextField axsisXcolumnName;

    @FXML
    private TextField axsisYcolumnName;

    @FXML
    private TextField axsisYcolumnName2;

    @FXML
    private ToggleButton toggleSelectXaxis;

    @FXML
    private ToggleButton toggleSelectYaxis;

    @FXML
    private ToggleButton toggleSelectColumn;

    @FXML
    private Spinner<Integer> spinnerFromRow;

    @FXML
    private Spinner<Integer> spinnerToRow;

    @FXML
    private CheckBox gridONcheck;

    @FXML
    private TextField xLabelTextField;

    @FXML
    private TextField yLabelTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField legendTextField;

    @FXML
    private Spinner<Integer> spinnerFromRow2;

    @FXML
    private Spinner<Integer> spinnerToRow2;

    @FXML
    private CheckBox gridONcheck2;

    @FXML
    private TextField xLabelTextField2;

    @FXML
    private TextField yLabelTextField2;

    @FXML
    private TextField titleTextField2;

    @FXML
    private TextField legendTextField2;

    @FXML
    private ToggleGroup radioToggleGroup;

    @FXML
    private ToggleGroup radioToggleGroup2;

    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> listView2;

    private ArrayList<XYChart.Series<Number, Number>> dataPlotList = new ArrayList<>(); // For area/line/scatter
    private ArrayList<XYChart.Series<String, Number>> barDataPlotList = new ArrayList<>(); // For bar
    private ArrayList<XYChart.Series<String, Number>> histDataPlotList = new ArrayList<>(); // For hist
    private ArrayList<PieChart.Data> pieDataPlotList = new ArrayList<>(); // For pie

    @Override
    public void start(Stage plotStage) throws Exception {
        Parent plotParent = FXMLLoader.load(getClass().getResource("/SceneBuilderFXML/PlotWindow.fxml"));
        plotStage.setTitle("Plot Database");
        plotStage.setScene(new Scene(plotParent));
        plotStage.setResizable(false);
        plotStage.show();
    }


    @FXML
    public void initialize(){

        // Listener for the the toggle buttons so only ONE toggle button can be active of them two.
        toggleSelectXaxis.setSelected(false);
        toggleSelectYaxis.setSelected(false);
        toggleSelectXaxis.selectedProperty().addListener(e-> {if(toggleSelectXaxis.isSelected()){toggleSelectYaxis.setSelected(false);}});
        toggleSelectYaxis.selectedProperty().addListener(e->  {if(toggleSelectYaxis.isSelected()) toggleSelectXaxis.setSelected(false);});


        // Add listener for the staticColumnName - If the table view changes!
        staticColumnName.addListener(e -> {
            if(toggleSelectXaxis.isSelected()){
                axsisXcolumnName.setText(staticColumnName.getValue());
                toggleSelectXaxis.setSelected(false);
                reinitSpinner();
            }else if(toggleSelectYaxis.isSelected()){
                axsisYcolumnName.setText(staticColumnName.getValue());
                toggleSelectYaxis.setSelected(false);
                reinitSpinner();
            }else if(toggleSelectColumn.isSelected()){
                axsisYcolumnName2.setText(staticColumnName.getValue());
                toggleSelectColumn.setSelected(false);
                reinitSpinner();
            }
        });

        // Initial values
        spinnerFromRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1,1, 1));
        spinnerToRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1,1, 1));
        spinnerFromRow2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1,1, 1));
        spinnerToRow2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1,1, 1));
    }

    // "Setup" the spinner again!
    public void reinitSpinner(){
        int maxLimit = staticTableView.getItems().size();
        Integer currentFrom  = spinnerFromRow.getValue(); // Remember where we was!
        Integer currentTo = spinnerToRow.getValue(); // Remember where we was!
        spinnerFromRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxLimit,currentFrom, 1));
        spinnerToRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxLimit,currentTo, 1));

        currentFrom  = spinnerFromRow2.getValue(); // Remember where we was!
        currentTo = spinnerToRow2.getValue(); // Remember where we was!
        spinnerFromRow2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxLimit,currentFrom, 1));
        spinnerToRow2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxLimit,currentTo, 1));

    }

    public static void setColumnName(String columnName) {
        staticColumnName.get(); // Need to be here or else it won't work.
        staticColumnName.set(columnName);
    }

    public static void setTableView(TableView tableView){
        staticTableView = tableView;
    }

    @FXML
    public void creatChartButton2(){

        // Create the axis for numerical plots and, set the y-label and x-label (if you want)
        CategoryAxis xAxis_bar = new CategoryAxis();
        xAxis_bar.setLabel(xLabelTextField2.getText());

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(yLabelTextField2.getText());
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yLabelTextField2.getText());

        // Create a new scene to place the plot into
        Scene plotScene = null;

        try{
            // What type of chart do you want to plot?
            RadioButton selectedButton = (RadioButton) radioToggleGroup2.getSelectedToggle();
            if(selectedButton.getText().equals("Bar chart")){
                BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis_bar, yAxis);
                for(XYChart.Series<String, Number> dataPlot : barDataPlotList){
                    barChart.getData().add(dataPlot);
                }
                barChart.setTitle(titleTextField2.getText());
                barChart.setHorizontalGridLinesVisible(gridONcheck2.isSelected());
                barChart.setVerticalGridLinesVisible(gridONcheck2.isSelected());
                plotScene = new Scene(barChart, 500,500);
            }else if(selectedButton.getText().equals("Hist chart")){
                BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis_bar, yAxis);
                for(XYChart.Series<String, Number> dataPlot : histDataPlotList){
                    barChart.getData().add(dataPlot);
                }
                barChart.setTitle(titleTextField2.getText());
                barChart.setHorizontalGridLinesVisible(gridONcheck2.isSelected());
                barChart.setVerticalGridLinesVisible(gridONcheck2.isSelected());
                plotScene = new Scene(barChart, 500,500);
            }else if(selectedButton.getText().equals("Pie chart")) {
                PieChart pieChart = new PieChart();
                for(PieChart.Data dataPlot : pieDataPlotList){
                    pieChart.getData().add(dataPlot);
                }
                pieChart.setTitle(titleTextField2.getText());
                plotScene = new Scene(pieChart, 500,500);
            }

            // Plot now
            Stage plotStage = new Stage();
            plotStage.setTitle(" ");
            plotStage.setScene(plotScene);
            plotStage.show();
        }catch (IllegalArgumentException e){
            Alert wrongData = new Alert(Alert.AlertType.ERROR, "Wrong data for the plot!", ButtonType.OK);
            wrongData.show();
        }
    }

    @FXML
    public void creatChartButton(){

        // Create the axis for numerical plots and, set the y-label and x-label (if you want)
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xLabelTextField.getText());
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yLabelTextField.getText());

        // Create a new scene to place the plot into
        Scene plotScene = null;

        try{
            // What type of chart do you want to plot?
            RadioButton selectedButton = (RadioButton) radioToggleGroup.getSelectedToggle();
            if(selectedButton.getText().equals("Area chart")){
                AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
                for(XYChart.Series<Number, Number> dataPlot : dataPlotList){
                    areaChart.getData().add(dataPlot);
                }
                areaChart.setTitle(titleTextField.getText());
                areaChart.setHorizontalGridLinesVisible(gridONcheck.isSelected());
                areaChart.setVerticalGridLinesVisible(gridONcheck.isSelected());
                plotScene = new Scene(areaChart, 500,500);
            }else if(selectedButton.getText().equals("Line chart")){
                LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                for(XYChart.Series<Number, Number> dataPlot : dataPlotList){
                    lineChart.getData().add(dataPlot);
                }
                lineChart.setTitle(titleTextField.getText());
                lineChart.setHorizontalGridLinesVisible(gridONcheck.isSelected());
                lineChart.setVerticalGridLinesVisible(gridONcheck.isSelected());
                plotScene = new Scene(lineChart, 500,500);
            }else if(selectedButton.getText().equals("Scatter chart")) {
                ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
                for(XYChart.Series<Number, Number> dataPlot : dataPlotList){
                    scatterChart.getData().add(dataPlot);
                }
                scatterChart.setTitle(titleTextField.getText());
                scatterChart.setHorizontalGridLinesVisible(gridONcheck.isSelected());
                scatterChart.setVerticalGridLinesVisible(gridONcheck.isSelected());
                plotScene = new Scene(scatterChart, 500,500);
            }

            // Plot now
            Stage plotStage = new Stage();
            plotStage.setTitle(" ");
            plotStage.setScene(plotScene);
            plotStage.show();
        }catch (IllegalArgumentException e){
            Alert wrongData = new Alert(Alert.AlertType.ERROR, "Wrong data for the plot!", ButtonType.OK);
            wrongData.show();
        }

    }

    @FXML
    public void deleteChart(){
        // Delete element from list view
        String deteteElement = listView.getSelectionModel().getSelectedItem();
        listView.getItems().remove(deteteElement);

        // Remove the data from the dataPlotList too
        for(XYChart.Series<Number, Number> dataPlot : dataPlotList){
            if(dataPlot.getName().equals(deteteElement)){
                dataPlotList.remove(dataPlot);
                break;
            }
        }

    }

    @FXML
    public void deleteChart2(){
        // Delete element from list view
        String deteteElement = listView2.getSelectionModel().getSelectedItem();
        listView2.getItems().remove(deteteElement);

        // Remove the data from the barDataPlotList too
        for(XYChart.Series<String, Number> dataPlot : barDataPlotList){
            if(dataPlot.getName().equals(deteteElement)){
                barDataPlotList.remove(dataPlot);
                break;
            }
        }

        // Remove the data from the histDataPlotList too
        for(XYChart.Series<String, Number> dataPlot : histDataPlotList){
            if(dataPlot.getName().equals(deteteElement)){
                histDataPlotList.remove(dataPlot);
                break;
            }
        }

        // Remove the data from the pieDataPlotList too
        for(PieChart.Data pieChart : pieDataPlotList){
            if(pieChart.getName().contains(deteteElement)){ // Note that this is .contains() because of the legend is almost equal as list view
                pieDataPlotList.remove(pieChart);
                break;
            }
        }
    }

    @FXML
    public void saveChart2(){
        try{
            // Get one Y and X axis from the tableView
            ObservableList items =  staticTableView.getItems();
            ObservableList<TableColumn> columNames = staticTableView.getColumns();
            ArrayList<String> vectorY = getVector(columNames, items, axsisYcolumnName2.getText(), spinnerFromRow2.getValue() - 1, spinnerToRow2.getValue());

            // First convert!
            ArrayList<Double> vectorY_double = new ArrayList<>();
            ArrayList<Integer> vectorY_integer = new ArrayList<>();
            for(int i = 0; i < vectorY.size(); i++){
                vectorY_double.add(Double.valueOf(vectorY.get(i))); // To double
                vectorY_integer.add((int) Math.ceil(Double.valueOf(vectorY.get(i)))); // Round up to int
            }
            vectorY.clear(); // Free memory...ish

            // Create bar Series
            XYChart.Series<String, Number> barDataPlot = new XYChart.Series<String, Number>();
            double SUM = vectorY_double.stream().mapToDouble(f -> f.doubleValue()).sum(); // Sum all values
            barDataPlot.setName(legendTextField2.getText());
            String X = axsisYcolumnName2.getText();
            barDataPlot.getData().add(new XYChart.Data<String, Number>(X, SUM));

            // Create frequency Series
            XYChart.Series<String, Number> histDataplot = new XYChart.Series<String, Number>();
            Collections.sort(vectorY_integer); // Sorting from smallest to largest
            histDataplot.setName(legendTextField2.getText());
            // This will create the histogram
            for(int i = 0; i < vectorY_integer.size(); i++){
                int freq = Collections.frequency(vectorY_integer, vectorY_integer.get(i));
                histDataplot.getData().add(new XYChart.Data<String, Number>(vectorY_integer.get(i).toString(), freq));
            }

            // Create pie series
            PieChart.Data slice = new PieChart.Data(legendTextField2.getText() + " - " + SUM, SUM);

            // What should we store?
            RadioButton selectedButton = (RadioButton) radioToggleGroup2.getSelectedToggle();
            if(selectedButton.getText().equals("Bar chart")){
                barDataPlotList.add(barDataPlot);
            }else if(selectedButton.getText().equals("Hist chart")){
                histDataPlotList.add(histDataplot);
            }else if(selectedButton.getText().equals("Pie chart")) {
                pieDataPlotList.add(slice);
            }

            // Add - Same name as Legend
            listView2.getItems().add(legendTextField2.getText());

        }catch (NullPointerException e){
            Alert noTable = new Alert(Alert.AlertType.INFORMATION, "No table view to plot.", ButtonType.OK);
            noTable.show();
        }catch (IndexOutOfBoundsException e){
            Alert noTable = new Alert(Alert.AlertType.INFORMATION, "Index out of bounds.", ButtonType.OK);
            noTable.show();
        }
    }

    @FXML
    public void saveChart(){
        try{
            // Get one Y and X axis from the tableView
            ObservableList items =  staticTableView.getItems();
            ObservableList<TableColumn> columNames = staticTableView.getColumns();
            ArrayList<String> vectorX = getVector(columNames, items, axsisXcolumnName.getText(), spinnerFromRow.getValue() - 1, spinnerToRow.getValue());
            ArrayList<String> vectorY = getVector(columNames, items, axsisYcolumnName.getText(), spinnerFromRow.getValue() - 1, spinnerToRow.getValue());

            // Create the data and store the values into dataPlot
            XYChart.Series<Number, Number> dataPlot = new XYChart.Series<Number, Number>();
            dataPlot.setName(legendTextField.getText());
            for(int i = 0; i < vectorX.size(); i++){
                double X = Double.valueOf(vectorX.get(i));
                double Y = Double.valueOf(vectorY.get(i));
                dataPlot.getData().add(new XYChart.Data<Number, Number>(X, Y));
            }

            dataPlotList.add(dataPlot);
            listView.getItems().add(legendTextField.getText()); // Add - Same name as Legend

        }catch (NullPointerException e){
            Alert noTable = new Alert(Alert.AlertType.INFORMATION, "No table view to plot.", ButtonType.OK);
            noTable.show();
        }catch (IndexOutOfBoundsException e){
            Alert noTable = new Alert(Alert.AlertType.INFORMATION, "Index out of bounds.", ButtonType.OK);
            noTable.show();
        }

    }

    // Pick one value from tableView - Remove [ ] and split into ,
    private String splitList(ObservableList items, int rowNumber, int columnNumber){
        String row = items.get(rowNumber).toString();
        List<String> rowItem = Arrays.asList(row.split(","));
        String cellValue = rowItem.get(columnNumber).toString().replace("[", "").replace("]", "");
        return  cellValue;
    }

    // This line search in [2, 1,31, 2,1] [2,2,2 1,4] to pick out the correct column because it's a 2D Observable list.
    private ArrayList<String> getVector(ObservableList<TableColumn> columNames, ObservableList items, String columnPickName, int fromRow, int toRow){
        ArrayList<String> vector = new ArrayList<>();
        for(int columnNumber = 0; columnNumber < columNames.size(); columnNumber++){

            String name = columNames.get(columnNumber).getText();
            if(name.equals(columnPickName)){

                for(int rowNumber = fromRow; rowNumber < toRow; rowNumber++){
                    vector.add(splitList(items, rowNumber, columnNumber));
                }
            }
        }
        return vector;
    }
}
