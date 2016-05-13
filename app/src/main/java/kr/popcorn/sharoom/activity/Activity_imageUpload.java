package kr.popcorn.sharoom.activity;

/**
 * Created by Administrator on 2016-04-03.
 */
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;

        import cz.msebera.android.httpclient.Header;
        import kr.popcorn.sharoom.R;

/**
 * Created by Administrator on 2016-02-07.
 */
public class Activity_imageUpload extends Activity {

    private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
    private static final int REQ_CODE_PICK_IMAGE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imageupload); // 항상 제공되는
        // activity_layout.xml을 복사해서
        // 만듦
        ImageView btn = (ImageView) findViewById(R.id.iv_upload);
        btn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");              // 모든 이미지
                intent.putExtra("crop", "true");        // Crop기능 활성화
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
                intent.putExtra("outputFormat",         // 포맷방식
                        Bitmap.CompressFormat.JPEG.toString());

                startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                // REQ_CODE_PICK_IMAGE == requestCode
                Log.d("kisang1", ""+android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
        });

    }
    private Uri getTempUri() {
        Log.d("kisang2", ""+Uri.fromFile(getTempFile()));

        return Uri.fromFile(getTempFile());
    }

    public static void postImage(String ImageLink){
        RequestParams params = new RequestParams();
        try {
            params.put("id","111");
            params.put("file", new File(ImageLink));
            params.put("path", "aaa");
            System.out.println("kisang10 : " + ImageLink );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("sibalbalblabl_file");
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://14.63.227.200/image/save1.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("statusCode "+statusCode);//statusCode 200
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("sibalbalblabl_onFailure");
            }
        });
    }
    /** 외장메모리에 임시 이미지 파일을 생성하여 그 파일의 경로를 반환  */
    private File getTempFile() {
        if (isSDCARDMOUNTED()) {
            File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                    TEMP_PHOTO_FILE);
            Log.d("kisang3", ""+Environment.getExternalStorageDirectory()+","+TEMP_PHOTO_FILE);

            try {
                f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
            } catch (IOException e) {
            }

            return f;
        } else
            return null;
    }

    /** SD카드가 마운트 되어 있는지 확인 */
    private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;

        return false;
    }

    /** 다시 액티비티로 복귀하였을때 이미지를 셋팅 */
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);

        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (imageData != null) {
                        String filePath = ""+Environment.getExternalStorageDirectory();
                        Log.d("kisang4", ""+Environment.getExternalStorageDirectory());

                        filePath += "/temp.jpg";
                        postImage(filePath);
                        System.out.println("filepath" + filePath); // logCat으로 경로확인.

                        Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                        // temp.jpg파일을 Bitmap으로 디코딩한다.

                        ImageView _image = (ImageView) findViewById(R.id.iv_upload);
                        _image.setImageBitmap(selectedImage);
                        // temp.jpg파일을 이미지뷰에 씌운다.
                    }
                }
                break;
        }
    }
}
