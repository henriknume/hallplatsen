package ex.nme.hallplatsen.mock;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import ex.nme.hallplatsen.R;

/**
 * Created by nm2 on 2017-06-01.
 */

public class MockedApi {

    private Context mContext;

    public MockedApi(Context context){
        this.mContext = context;
    }

    private String readFile(String resName) throws IOException {

        int id = mContext.getResources().getIdentifier(resName, null, null);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try(InputStream is = mContext.getResources().openRawResource(id)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }
        return writer.toString();
    }

    public String getLocationByName(String name) {
        try {
            String content = readFile("location_" + name);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
