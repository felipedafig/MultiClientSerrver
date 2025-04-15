package ViewModel;

import Model.ClientModelManager;

public class ViewModelFactory {

    private final ListViewModel listViewModel;
    private final AddVinylViewModel addVinylViewModel;

    public ViewModelFactory(ClientModelManager model) {
        this.listViewModel = new ListViewModel(model);
        this.addVinylViewModel = new AddVinylViewModel(model);
    }

    public ListViewModel getListViewModel() {
        return listViewModel;
    }

    public AddVinylViewModel getAddVinylViewModel() {
        return addVinylViewModel;
    }
}
