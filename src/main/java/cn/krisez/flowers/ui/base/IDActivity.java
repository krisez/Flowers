package cn.krisez.flowers.ui.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.krisez.flowers.App;
import cn.krisez.flowers.R;

public class IDActivity extends AppCompatActivity {

    private ImageView zm;
    private ImageView bm;
    private ImageView data;
    private ProgressBar progressBar;
    private String[] uris = new String[3];

    private Uri u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);
        zm = (ImageView) findViewById(R.id.idcard_z);
        bm = (ImageView) findViewById(R.id.idcard_b);
        data = (ImageView) findViewById(R.id.idcard_data);
        progressBar = (ProgressBar) findViewById(R.id.ID_progress);

        zm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(IDActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IDActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    showDia(1);
                }
            }
        });

        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(IDActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IDActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    showDia(2);
                }
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(IDActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IDActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    showDia(3);
                }
            }
        });

        findViewById(R.id.apply_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(IDActivity.this)
                        .setTitle("审核")
                        .setMessage("确定申请援助么？我们将在1-3个工作日给予回复！")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (uris != null)
                                    Log.d("IDActivity", "onClick:" + uris[0] + uris[1]);
                                progressBar.setVisibility(View.VISIBLE);
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1500);
                            }
                        })
                        .setPositiveButton("取消", null).show();
            }
        });

        findViewById(R.id.cancel_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDia(final int who) {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                File file = new File(getExternalCacheDir(), "idcard" + who + ".jpg");
                                try {
                                    if (file.exists())
                                        file.delete();
                                    file.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (Build.VERSION.SDK_INT >= 24) {
                                    u = FileProvider.getUriForFile(IDActivity.this, "cn.cqupt.group.lawhelp", file);
                                } else u = Uri.fromFile(file);
                                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent1.putExtra(MediaStore.EXTRA_OUTPUT, u);
                                if (who == 1)
                                    startActivityForResult(intent1, 101);
                                else if(who == 2)startActivityForResult(intent1, 201);
                                else startActivityForResult(intent1, 301);

                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                if (who == 1)
                                    startActivityForResult(intent, 102);
                                else if(who == 2)startActivityForResult(intent, 202);
                                else startActivityForResult(intent, 302);
                                break;
                        }

                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                        zm.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 102:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,1);
                    } else {
                        handleImageBeforeKitKat(data,1);
                    }
                }
                break;
            case 201:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                        bm.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 202:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,2);
                    } else {
                        handleImageBeforeKitKat(data,2);
                    }
                }
                break;
            case 301:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                        this.data.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 302:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,3);
                    } else {
                        handleImageBeforeKitKat(data,3);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Log.d("ID", "onRequestPermissionsResult:" + "already putting!");
                else Toast.makeText(IDActivity.this, "未给权限,请给予", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    private void handleImageBeforeKitKat(Intent data, int who) {
        Uri uri = data.getData();
        String imgPath = uri.getPath();
        displayHead(imgPath, who);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, int who) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayHead(imagePath, who);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = App.getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayHead(String path, int who) {
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (who == 1) {
                zm.setImageBitmap(bitmap);
                uris[0] = path;
            } else if(who == 2){
                bm.setImageBitmap(bitmap);
                uris[1] = path;
            }else {
                data.setImageBitmap(bitmap);
                uris[2] = path;
            }
        } else {
            Toast.makeText(IDActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
        }
    }
}
