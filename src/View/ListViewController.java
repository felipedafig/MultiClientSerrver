package View;

import Model.ClientModelManager;
import Model.Vinyl;
import ViewModel.ListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ListViewController implements PropertyChangeListener {

    @FXML private TableView<Vinyl> tableView;
    @FXML private TableColumn<Vinyl, String> title;
    @FXML private TableColumn<Vinyl, Integer> year;
    @FXML private TableColumn<Vinyl, String> artist;
    @FXML private TableColumn<Vinyl, String> state;

    private ViewHandler viewHandler;
    private ListViewModel listViewModel;

    public void init(ViewHandler viewHandler, ListViewModel listViewModel) {
        this.viewHandler = viewHandler;
        this.listViewModel = listViewModel;

        tableView.setItems(listViewModel.getVinyls());

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        year.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));

        listViewModel.getModel().addPropertyChangeListener("VinylUpdated", this);
    }

    @FXML
    private void borrow() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listViewModel.borrowVinyl(selected, 69);
        }
    }

    @FXML
    private void reserve() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listViewModel.reserveVinyl(selected, 69);
        }
    }

    @FXML
    private void remove() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listViewModel.remove(selected);
        }
    }

    @FXML
    private void returnVinyl() {
        Vinyl selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listViewModel.returnVinyl(selected, 69);
        }
    }

    @FXML
    private void addVinyl() {
        viewHandler.openView("AddVinyl");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("ListViewController received VinylUpdated event, refreshing TableView");
        tableView.refresh();
    }

    public ClientModelManager getModel() {
        return listViewModel.getModel();
    }
}
