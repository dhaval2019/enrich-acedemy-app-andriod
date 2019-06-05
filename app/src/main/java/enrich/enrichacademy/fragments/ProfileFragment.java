package enrich.enrichacademy.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.UUID;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.LoginActivity;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import enrich.enrichacademy.view.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 05-Mar-17.
 */

public class ProfileFragment extends Fragment {

    public static final int RESULT_LOAD_IMAGE = 908;
    private static final int CAMERA_REQUEST = 1888;
    TextView name, dob, email;
    RadioButton male, female;
    Button logout;
    ImageView uploadProfileImage;
    CircleImageView imageView;


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

        name = rootView.findViewById(R.id.profile_user_name);
        dob = rootView.findViewById(R.id.profile_user_dob);
        email = rootView.findViewById(R.id.profile_user_email);
        male = rootView.findViewById(R.id.profile_user_gender_male);
        female = rootView.findViewById(R.id.profile_user_gender_female);
        logout = rootView.findViewById(R.id.profile_signout_btn);
        uploadProfileImage = rootView.findViewById(R.id.upload_profile_image);
        imageView = rootView.findViewById(R.id.profile_image);

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Quicksand-Bold.otf");
        male.setTypeface(typeface);
        female.setTypeface(typeface);

        male.setClickable(false);
        female.setClickable(false);
        male.setEnabled(false);
        female.setEnabled(false);

        String url = userModel.getImage();

        Picasso.with(ProfileFragment.this.getActivity()).load(url).placeholder(R.drawable.profile_default).into(imageView);

        name.setText(userModel.Name);
        dob.setText(userModel.DateOfBirth);
        email.setText(userModel.EmailAddress);
        if (userModel.Gender == 1)
            male.setChecked(true);
        else
            female.setChecked(true);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceStore.deleteValue(ProfileFragment.this.getActivity(), Constants.KEY_USER_MODEL);
                Intent intent = new Intent(ProfileFragment.this.getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        uploadProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        return rootView;
    }

    public void showImageDialog() {
        final Dialog dialog = new Dialog(this.getActivity());
        dialog.setContentView(R.layout.image_selector_dialog);

        TextView gallery = dialog.findViewById(R.id.gallery);
        TextView camera = dialog.findViewById(R.id.camera);

        // if button is clicked, close the custom dialog
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                dialog.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            uploadPhoto(picturePath);
            cursor.close();
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveCameraImage(photo);
        }
    }

    private void saveCameraImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/profile_images");
        myDir.mkdirs();
        String fname = UUID.randomUUID().toString() + ".jpeg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        EnrichUtils.log(file.getAbsolutePath());
        uploadPhoto(file.getAbsolutePath());
    }

    public void uploadPhoto(String path) {
        File file = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse(path), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String userIdStr = EnrichUtils.getUser(this.getActivity()).getId();
        RequestBody userId = RequestBody.create(okhttp3.MultipartBody.FORM, userIdStr);

        Call<SingleResponseModel<String>> call = EnrichUtils.getRetrofitForEnrich().uploadProfileImage(userId, body);
        call.enqueue(new Callback<SingleResponseModel<String>>() {
            @Override
            public void onResponse(Call<SingleResponseModel<String>> call, Response<SingleResponseModel<String>> response) {
                EnrichUtils.log(response.body().data);
                if (response.body().status == 0) {
                    Glide.with(ProfileFragment.this.getActivity()).load(response.body().data).into(imageView);
                } else {
                    Toast.makeText(ProfileFragment.this.getActivity(), "" + response.body().data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleResponseModel<String>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
            }
        });
    }
}
