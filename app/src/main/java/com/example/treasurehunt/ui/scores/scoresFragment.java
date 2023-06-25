package com.example.treasurehunt.ui.scores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurehunt.ScoreItem;
import com.example.treasurehunt.databinding.FragmentGalleryBinding;
import com.example.treasurehunt.scoreListAdapter;

import java.util.ArrayList;

public class scoresFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scoresViewModel scoresViewModel =
                new ViewModelProvider(this).get(scoresViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        scoresViewModel.setContext(requireContext());

        RecyclerView recyclerView = binding.scoreList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> content = scoresViewModel.getContentasString();
        scoreListAdapter adapter = new scoreListAdapter(content);
        recyclerView.setAdapter(adapter);
        Button reset = binding.resetScores;
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoresViewModel.resetScores();
                adapter.removeAll();
            }
        });



        // MutableLiveData for score list.
        final TextView textView = binding.textGallery;
        scoresViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}