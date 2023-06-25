package com.example.treasurehunt.ui.game;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.treasurehunt.R;
import com.example.treasurehunt.databinding.FragmentSlideshowBinding;
import com.google.android.material.slider.BaseOnChangeListener;

import java.util.Arrays;
import java.util.List;

public class gameFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    //private List<Float> horizontalPositions = Arrays.asList(0.498f, 0.7f, 0.362f, 0.423f, 0.185f, 0.362f, 0.085f, 0.656f, 0.75f, 0.795f, 0.224f, 0.324f);
    //private List<Float> verticalPositions = Arrays.asList(0.383f, 0.425f, 0.252f, 0.527f, 0.393f, 0.117f, 0.252f, 0.118f, 0.279f, 0.715f, 0.641f, 0.776f);
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Reference to the view model.
        gameViewModel gameViewModel =
                new ViewModelProvider(this).get(gameViewModel.class);
        gameViewModel.setContext(requireContext());
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ConstraintLayout buttonContainer = binding.buttonContainer;

        // The game start Button.
        Button gameButton = binding.textSlideshow;
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.resetGame();
            }
        });

        // Set the onCLick Listeners for the Buttons
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the index of the clicked button
                int buttonIndex = buttonContainer.indexOfChild(view);

                // Call the gameViewModel.playing() method with the button index
                gameViewModel.playing(buttonIndex);
            }
        };
        // Set for every Button
        for (int i = 1; i < buttonContainer.getChildCount(); i++) {
            buttonContainer.getChildAt(i).setEnabled(false);
            buttonContainer.getChildAt(i).setOnClickListener(buttonClickListener);
        }


        View.OnClickListener buttonStartListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the index of the clicked button
                int buttonIndex = buttonContainer.indexOfChild(view);

                // Call the gameViewModel.playing() method with the button index
                gameViewModel.playing(buttonIndex);
            }
        };


        // Change ImageButtons based on gameState.
        gameViewModel.getGameActive().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean active) {
                if (active) {
                    gameButton.setEnabled(true);
                    gameButton.setText(R.string.start_game);
                    for (int i = 1; i < buttonContainer.getChildCount(); i++) {
                        buttonContainer.getChildAt(i).setEnabled(false);
                    }
                    return;
                }
                // Reset the playing Buttons.
                for (int i = 1; i < buttonContainer.getChildCount(); i++) {
                    buttonContainer.getChildAt(i).setEnabled(true);
                    buttonContainer.getChildAt(i).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ic_button_art));
                }
                // Change Play Button.
                gameButton.setEnabled(false);
                gameButton.setText(R.string.game_started);
            }
        });
        // Change ImageButtons based on gameState.
        gameViewModel.getButtonBackground().observe(getViewLifecycleOwner(), new Observer<Pair>() {
            @Override
            public void onChanged(Pair background) {
                ImageButton button = (ImageButton) buttonContainer.getChildAt((Integer) background.first);
                button.setEnabled(false);
                button.setBackground(ResourcesCompat.getDrawable(getResources(), (Integer) background.second, null));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}