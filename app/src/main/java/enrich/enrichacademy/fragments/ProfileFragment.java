package enrich.enrichacademy.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.RegisterProfileActivity;
import enrich.enrichacademy.activities.RouteActivity;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;

/**
 * Created by Admin on 05-Mar-17.
 */

public class ProfileFragment extends Fragment {

    TextView name, dob, email;
    RadioButton male, female;
    Button logout;

    UserModel userModel;

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(ProfileFragment.this.getActivity(), Constants.KEY_USER_MODEL, ""), UserModel.class);

        name = (TextView) rootView.findViewById(R.id.profile_user_name);
        dob = (TextView) rootView.findViewById(R.id.profile_user_dob);
        email = (TextView) rootView.findViewById(R.id.profile_user_email);
        male = (RadioButton) rootView.findViewById(R.id.profile_user_gender_male);
        female = (RadioButton) rootView.findViewById(R.id.profile_user_gender_female);
        logout = (Button) rootView.findViewById(R.id.profile_signout_btn);

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Quicksand-Bold.otf");
        male.setTypeface(typeface);
        female.setTypeface(typeface);

        male.setClickable(false);
        female.setClickable(false);
        male.setEnabled(false);
        female.setEnabled(false);

        name.setText(userModel.Name);
        dob.setText(userModel.DateOfBirth);
        email.setText(userModel.EmailAddress);
        if (userModel.Gender.equals("Male"))
            male.setChecked(true);
        else
            female.setChecked(true);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceStore.deleteValue(ProfileFragment.this.getActivity(), Constants.KEY_USER_MODEL);

                Intent intent = new Intent(ProfileFragment.this.getActivity(), RouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
}
