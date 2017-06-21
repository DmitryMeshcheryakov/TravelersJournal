package by.insight.travelersjournal.presenters.impl;

import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.presenters.ITravelPresenter;
import by.insight.travelersjournal.realm.ITravelRepository;
import by.insight.travelersjournal.realm.TravelRepository;
import by.insight.travelersjournal.view.activity.TravelsActivity;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;
import io.realm.RealmResults;


public class TravelPresenter implements ITravelPresenter {
    private RecyclerViewTravelFragment mRecyclerViewTravelFragment;
    private TravelsActivity mTravelsActivity;

    private ITravelRepository mRepository;


    private ITravelRepository.OnGetAllTravelCallback getAllTravelCallback;
    private ITravelRepository.OnAddTravelCallback addTravelCallback;
    private ITravelRepository.OnGetTravelByIdCallback getSpecialTravelCallback;
    private ITravelRepository.OnDeleteTravelCallback deleteTravelCallback;

    public TravelPresenter(RecyclerViewTravelFragment view) {
        this.mRecyclerViewTravelFragment = view;
        mRepository = new TravelRepository();
    }
    public TravelPresenter(TravelsActivity view)
    {
        this.mTravelsActivity = view;
        mRepository = new TravelRepository();
    }

    @Override
    public void getAllTravels() {
        mRepository.getAllTravels(getAllTravelCallback);
    }

    @Override
    public void addTravel(String travelName, String travelDescription, String travelImagePath) {
        Travel travel = new Travel(travelName, travelDescription, travelImagePath);
        mRepository.addTravel(travel, addTravelCallback);
    }

    @Override
    public void getTravelById(String id) {
        mRepository.getTravelById(id, getSpecialTravelCallback);
    }

    @Override
    public void deleteTravel(int position) {
        mRepository.deleteTravelByPosition(position, deleteTravelCallback);
    }

    @Override
    public void deleteTravelById(String Id) {
        mRepository.deleteTravelById(Id, deleteTravelCallback);
    }

    @Override
    public void subscribeCallbacks() {
        getAllTravelCallback = new ITravelRepository.OnGetAllTravelCallback() {
            @Override
            public void onSuccess(RealmResults<Travel> travel) {
                mRecyclerViewTravelFragment.showTravels(travel);
            }

            @Override
            public void onError(String message) {
                mRecyclerViewTravelFragment.showMessage(message);
            }
        };
        addTravelCallback = new ITravelRepository.OnAddTravelCallback() {
            @Override
            public void onSuccess() {
                mRecyclerViewTravelFragment.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                mRecyclerViewTravelFragment.showMessage(message);
            }
        };
        getSpecialTravelCallback = new ITravelRepository.OnGetTravelByIdCallback() {
            @Override
            public void onSuccess(Travel travel) {

            }

            @Override
            public void onError(String message) {
                mRecyclerViewTravelFragment.showMessage(message);
            }
        };
        deleteTravelCallback = new ITravelRepository.OnDeleteTravelCallback() {
            @Override
            public void onSuccess() {
                mRecyclerViewTravelFragment.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                mRecyclerViewTravelFragment.showMessage(message);
            }
        };

    }

    @Override
    public void unSubscribeCallbacks() {
        getAllTravelCallback = null;
        addTravelCallback = null;
        getSpecialTravelCallback = null;
        deleteTravelCallback = null;
    }
}
