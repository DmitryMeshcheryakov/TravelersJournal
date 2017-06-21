package by.insight.travelersjournal.presenters;


public interface ITravelPresenter extends IBasePresenter {
    void addTravel(String travelName, String travelDescriptions, String travelImagePath);

    void deleteTravel(int position);

    void deleteTravelById(String Id);

    void getTravelById(String id);

    void getAllTravels();
}
