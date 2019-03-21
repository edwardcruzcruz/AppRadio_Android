package com.innovasystem.appradio.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovasystem.appradio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConcursosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class ConcursosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentsAdapter adapter;
    public ConcursosFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConcursosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConcursosFragment newInstance() {
        ConcursosFragment fragment = new ConcursosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_concursos, container, false);
        View root= inflater.inflate(R.layout.fragment_concursos, container, false);
        ViewPager vpager= root.findViewById(R.id.vpager_concursos);
        TabLayout tabLayout= root.findViewById(R.id.tablayout_concursos);
        adapter= new FragmentsAdapter(getChildFragmentManager());
        setupViewPager(vpager);
        tabLayout.setupWithViewPager(vpager);

        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ConcursosFragment.FragmentsAdapter(getChildFragmentManager());
        adapter.addFragment(new ConcursoActivosFragment(),"Activos");
        adapter.addFragment(new ConcursoFinalizadosFragment(),"Finalizados");
        viewPager.setAdapter(adapter);
    }
    static class FragmentsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public FragmentsAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String name) {
            mFragments.add(fragment);
            mFragmentTitles.add(name);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
