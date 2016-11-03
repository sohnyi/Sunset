package ziyi.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

/**
 * Created by Ziyi on 2016/11/3.
 */

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private TextView mSunY;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSkyView = view.findViewById(R.id.sky);
        mSunView = view.findViewById(R.id.sun);
        mSunY = (TextView)view.findViewById(R.id.sun_y);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBlueSkyColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.blue_sky);
            mSunsetSkyColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.sunset_sky);
            mNightSkyColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.night_sky);
        } else {
            Resources resources = getResources();
            mBlueSkyColor = resources.getColor(R.color.blue_sky);
            mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
            mNightSkyColor = resources.getColor(R.color.night_sky);

        }

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });

         return view;
    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();


        mSunY.setText("sunYStart=" + sunYStart + ", sunYEnd=" + sunYEnd);



        ObjectAnimator sunsetAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(5000);
        sunsetAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor, mNightSkyColor)
                .setDuration(5000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());



        AnimatorSet sunsetAnimatorSet = new AnimatorSet();
        sunsetAnimatorSet
                .play(sunsetAnimator)
                .with(sunsetSkyAnimator);
//        sunsetAnimatorSet.start();


        ObjectAnimator sunriseAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYEnd, sunYStart)
                .setDuration(5000);
        sunriseAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunriseSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor, mBlueSkyColor)
                .setDuration(5000);
        sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet sunriseAnimatorSet = new AnimatorSet();
        sunriseAnimatorSet
                .play(sunriseAnimator)
                .with(sunriseSkyAnimator)
                .after(sunsetAnimatorSet);

        sunriseAnimatorSet.start();
    }
}
