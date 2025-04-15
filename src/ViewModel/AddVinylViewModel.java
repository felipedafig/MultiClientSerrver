package ViewModel;

import Model.ClientModelManager;
import Model.Vinyl;
import javafx.beans.property.*;

import java.time.LocalDate;

public class AddVinylViewModel {

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty artist = new SimpleStringProperty();
    private final StringProperty year = new SimpleStringProperty();
    private final StringProperty message = new SimpleStringProperty();

    private final ClientModelManager model;

    public AddVinylViewModel(ClientModelManager model) {
        this.model = model;
    }

    public String addVinyl() {
        if (title.get() == null || title.get().isBlank() ||
                artist.get() == null || artist.get().isBlank() ||
                year.get() == null || year.get().isBlank()) {
            return "All fields must be filled.";
        }

        int releaseYear;
        try {
            releaseYear = Integer.parseInt(year.get());
            if (releaseYear < 1900 || releaseYear > LocalDate.now().getYear()) {
                return "Year must be between 1900 and " + LocalDate.now().getYear();
            }
        } catch (NumberFormatException e) {
            return "Year must be a valid number.";
        }

        Vinyl vinyl = new Vinyl(title.get(), artist.get(), releaseYear);
        model.addVinyl(vinyl, 69 );


        title.set(""); artist.set(""); year.set("");
        return null;
    }


    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public StringProperty messageProperty() {
        return message;
    }
}
