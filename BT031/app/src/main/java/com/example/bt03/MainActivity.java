package com.example.bt03;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private Switch switchBackground;
    private int[] backgrounds = {R.drawable.fit, R.drawable.hcmute1, R.drawable.hcmute2};
    private int currentIndex = 0;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_BACKGROUND_INDEX = "background_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        mainLayout = findViewById(R.id.main);
        switchBackground = findViewById(R.id.switchBackground);

        // Lấy giá trị index cũ từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int lastIndex = prefs.getInt(KEY_BACKGROUND_INDEX, -1);

        // Sinh index ngẫu nhiên nhưng tránh trùng với lần trước
        Random random = new Random();
        do {
            currentIndex = random.nextInt(backgrounds.length);
        } while (currentIndex == lastIndex); // Đảm bảo không lặp lại hình cũ

        // Cập nhật background mới
        mainLayout.setBackgroundResource(backgrounds[currentIndex]);

        // Lưu index mới vào SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_BACKGROUND_INDEX, currentIndex);
        editor.apply();

        // Xử lý sự kiện khi bấm vào Switch
        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentIndex = (currentIndex + 1) % backgrounds.length;
            mainLayout.setBackgroundResource(backgrounds[currentIndex]);

            // Lưu index mới sau khi thay đổi
            editor.putInt(KEY_BACKGROUND_INDEX, currentIndex);
            editor.apply();
        });
    }
}
