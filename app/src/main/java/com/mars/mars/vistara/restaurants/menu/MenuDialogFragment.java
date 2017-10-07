package com.mars.mars.vistara.restaurants.menu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.mars.vistara.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDialogFragment extends DialogFragment {

    View rootView;
    RecyclerView menuRecyclerView;
    MenuAdapter menuAdapter;
    List<Menu> menu;
    public MenuDialogFragment() {
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
        if (null != menuAdapter) {
            menuAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu_dialog, container, false);
        setUpMenuAdapter();
        menuAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void setUpMenuAdapter() {
        menuRecyclerView = rootView.findViewById(R.id.menuRecyclerView);
        menuAdapter = new MenuAdapter(menu, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        menuRecyclerView.setLayoutManager(mLayoutManager);
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        menuRecyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onStart() {
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
        super.onStart();
    }
}
