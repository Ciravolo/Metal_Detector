package it.unipi.iet.smp.smpfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unipi.iet.metal_detector.R;

public class ChartFragment extends Fragment {
    // factory
    public static ChartFragment newInstance() {
        ChartFragment f = new ChartFragment();
        return f;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_fragment, container, false);
    }
}
