package View;

import Model.ClientModelManager;
import Model.Vinyl;
import ViewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewController implements PropertyChangeListener
{
    private ViewModel viewModel;

    @FXML
    private TableView<Vinyl> tableView;
    @FXML
    private TableColumn<Vinyl, String> title;
    @FXML
    private TableColumn<Vinyl, Integer> year;
    @FXML
    private TableColumn<Vinyl, String> artist;
    @FXML
    private TableColumn<Vinyl, String> state;

    public ViewController(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void initialize()
    {
        tableView.setItems(viewModel.getVinyls());
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        year.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        viewModel.getModel().addPropertyChangeListener("VinylUpdated", this);
    }

    @FXML
    private void borrow() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && viewModel != null) {
            viewModel.borrowVinyl(selected, 69);
        }
    }

    @FXML
    private void reserve() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && viewModel != null) {
            viewModel.reserveVinyl(selected, 69);
        }
    }

    @FXML
    private void remove() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && viewModel != null) {
            viewModel.remove(selected);
        }
    }

    @FXML
    private void returnVinyl() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && viewModel != null) {
            viewModel.returnVinyl(selected, 69);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("ViewController received VinylUpdated event, refreshing TableView");
        tableView.refresh();
    }

    public ClientModelManager getModel() {
        return viewModel.getModel();
    }
}