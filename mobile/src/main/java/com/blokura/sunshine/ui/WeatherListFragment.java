package com.blokura.sunshine.ui;

import com.blokura.sunshine.R;
import com.blokura.sunshine.core.models.WeatherForecast;
import com.blokura.sunshine.daggerutils.ForActivity;
import com.blokura.sunshine.data.ForecastDatabase;
import com.blokura.sunshine.data.sensors.LocationService;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherListFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private static final long LOCATION_TIMEOUT_SECONDS = 20;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ForecastAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private Subscription request;

    private CompositeSubscription compositeRequest;

    @InjectView(R.id.listview_forecast)
    RecyclerView weatherRecyclerView;

    List<String> forecastList;

    @Inject
    ForecastDatabase forecastDatabase;

    @Inject
    @ForActivity
    Context context;

    @Inject
    LocationManager locationManager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherListFragment newInstance(String param1, String param2) {
        WeatherListFragment fragment = new WeatherListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WeatherListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        compositeRequest = new CompositeSubscription();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_weather_list;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Use this setting to improve performance if you know that changes in context do not
        // change the layout size of the RecyclerView
        weatherRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        layoutManager = new LinearLayoutManager(context);
        weatherRecyclerView.setLayoutManager(layoutManager);

        if (forecastList == null) {
            forecastList = new ArrayList<>();
        }
        // Specify an adapter
        adapter = new ForecastAdapter(forecastList);
        weatherRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: get location
        Location location = new Location("Fake");
        location.setLatitude(40.416953);
        location.setLongitude(-3.705283);
        updateWeather();
    }

    private void updateWeather() {
        final LocationService locationService = new LocationService(locationManager);

        // Get current location
        final Observable fetchDataObservable = locationService.getLocation()
                .timeout(LOCATION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .flatMap(new Func1<Location, Observable<List<WeatherForecast>>>() {
                    @Override
                    public Observable<List<WeatherForecast>> call(final Location location) {
                        return forecastDatabase.fetchWeatherForecasts(location, "metric", 7);
                    }
                });
        compositeRequest.add(fetchDataObservable
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<WeatherForecast>>() {

            @Override
            public void onCompleted() {
                // Nothing
            }

            @Override
            public void onError(final Throwable e) {
                Toast.makeText(getActivity(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(final List<WeatherForecast> weatherForecasts) {
                forecastList = new ArrayList<>();
                for (WeatherForecast wf : weatherForecasts) {
                    forecastList.add(wf.toString());
                    adapter.addOrUpdateItem(wf.toString());
                }
            }
        }));


    }

    @Override
    public void onPause() {
        compositeRequest.unsubscribe();
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
