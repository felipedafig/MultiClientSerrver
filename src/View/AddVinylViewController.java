package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ViewModel.AddVinylViewModel;

public class AddVinylViewController {

    @FXML private TextField title;
    @FXML private TextField artist;
    @FXML private TextField year;
    @FXML private Label message;

    private ViewHandler viewHandler;
    private AddVinylViewModel viewModel;


    public void init(ViewHandler viewHandler, AddVinylViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        title.textProperty().bindBidirectional(viewModel.titleProperty());
        artist.textProperty().bindBidirectional(viewModel.artistProperty());
        year.textProperty().bindBidirectional(viewModel.yearProperty());
        message.textProperty().bind(viewModel.messageProperty());
    }


    @FXML
    private void onAdd() {
        String result = viewModel.addVinyl();

        if (result == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Vinyl added successfully!");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Vinyl Data");
            alert.setContentText(result);
            alert.showAndWait();
        }
    }

    @FXML
    private void onGoToOverview() {
        viewHandler.openView("VinylList");
    }
}
