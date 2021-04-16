

package com.example.photoapp;

        import android.app.AlertDialog;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.RectF;
        import android.graphics.drawable.BitmapDrawable;
        import android.util.SparseArray;
        import android.widget.ImageView;

        import com.google.android.gms.vision.Frame;
        import com.google.android.gms.vision.face.Face;
        import com.google.android.gms.vision.face.FaceDetector;

        import org.junit.Test;

        import java.io.FileDescriptor;
        import java.text.*;
        import java.util.*;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertNotNull;
        import static org.junit.Assert.assertTrue;

public class UnitTest {
    @Test
    public void SearchTest() throws ParseException {

        String latitude = "49.13";
        String longitude = "-122.88";
        Date date1 = null;
        Date date2 = null;

        ArrayList<String> pathToFake = new ArrayList(Arrays.asList(
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210203_111111_49.13_-122.88_2509275942840573539.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210206_122222_49.13_-122.88_2509275942840573531.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210321_133333_49.13_-122.88_2509275942840573532.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210204_144444_31.30_11.85_2509275942840573537.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210502_155555_22.30_13.85_2509275942840573536.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210102_166666_49.13_-122.88_25092759424350573523.jpg"
        ));
        ArrayList<String> foundPhotos = findPhotos(pathToFake, date1, date2 , "" , latitude, longitude);

        ArrayList expectedList =  new ArrayList(Arrays.asList(
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210203_111111_49.13_-122.88_2509275942840573539.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210206_122222_49.13_-122.88_2509275942840573531.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210321_133333_49.13_-122.88_2509275942840573532.jpg",
                "Android/data/com.example.photogalleryapp/files/Pictures/_JPEG_20210102_166666_49.13_-122.88_25092759424350573523.jpg"
        ));

        assertEquals(expectedList, foundPhotos);
    }

    public ArrayList<String> findPhotos(ArrayList<String> photopaths ,Date startTimestamp, Date endTimestamp, String keywords, String latitude, String longitude) throws ParseException {
        ArrayList<String> photos = new ArrayList<String>();
        if (photopaths != null) {
            for (String f : photopaths) {
                String[] attr = f.split("_");
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date dat = format.parse(attr[2].substring(0, 4) + "" + attr[2].substring(4, 6) + "" + attr[2].substring(6, 8) + "" + attr[3].substring(0, 2) + "" + attr[3].substring(2, 4) + "" + attr[3].substring(4, 6));

                if (((startTimestamp == null && endTimestamp == null) || (dat.getTime() >= startTimestamp.getTime()
                        && dat.getTime() <= endTimestamp.getTime())
                ) && (keywords == "" || f.contains(keywords))
                        &&(latitude== "" || f.contains(latitude))
                        &&(longitude == "" || f.contains(longitude)))
                    photos.add(f);
            }
        }
        return photos;
    }

    public void FaceTest() throws ParseException {

        boolean test = faceDetect();

        assertTrue(test);

    }

    public boolean faceDetect() {

        FileDescriptor
        Bitmap myBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        //create face detector
        FaceDetector faceDetector = new
                FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        //detect faces
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        }

        return answer;
    }

}
