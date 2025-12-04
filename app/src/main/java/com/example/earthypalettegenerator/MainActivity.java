package com.example.earthypalettegenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.earthypalettegenerator.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<View> colorViews = Arrays.asList(
                binding.color1,
                binding.color2,
                binding.color3,
                binding.color4,
                binding.color5
        );

        binding.generateButton.setOnClickListener(v -> refreshPalette(colorViews));

        refreshPalette(colorViews);
    }

    private void refreshPalette(List<View> colorViews) {

        List<TextView> labels = Arrays.asList(
                binding.label1,
                binding.label2,
                binding.label3,
                binding.label4,
                binding.label5
        );

        for (int i = 0; i < colorViews.size(); i++) {

            View colorBlock = colorViews.get(i);
            TextView label = labels.get(i);

            int newColor = generateEarthyColor();
            String hex = String.format("#%06X", (0xFFFFFF & newColor));

            // Set background color
            colorBlock.setBackgroundColor(newColor);

            // Set the label text
            label.setText(hex);

            // Allow clicking either the color or text to copy hex code
            colorBlock.setOnClickListener(v -> copyHexToClipboard(newColor));
            label.setOnClickListener(v -> copyHexToClipboard(newColor));
        }
    }

    private int generateEarthyColor() {
        float hue = 20f + random.nextFloat() * (90f - 20f); // Earthy hue range
        float saturation = 0.20f + random.nextFloat() * (0.60f - 0.20f);
        float lightness = 0.30f + random.nextFloat() * (0.70f - 0.30f);

        return Color.HSVToColor(new float[]{hue, saturation, lightness});
    }

    private void copyHexToClipboard(int color) {
        String hex = String.format("#%06X", (0xFFFFFF & color));

        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText("color", hex);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "Copied " + hex, Toast.LENGTH_SHORT).show();
    }
}
