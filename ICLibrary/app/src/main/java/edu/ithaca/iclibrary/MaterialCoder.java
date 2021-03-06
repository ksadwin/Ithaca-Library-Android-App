package edu.ithaca.iclibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by Joseph on 4/26/2016.
 * Processes the Material object for the purposes of saving them to Favorites. Saves lists of
 * Materials to a .txt file on the device's internal storage with JSON strings. Unpacks the
 * save file and converts it to a list of Materials.
 */
public class MaterialCoder {
    private static final String TAG = "MaterialCoder";
    private static String appFolder = "ICLibrary";
    private static String favFileName = "Favorites.txt";
    private static Context con;

    /**
     * Constructs a new MaterialCoder with a Context to direct it to a save file location.
     * @param context
     */
    public MaterialCoder(Context context){
        con = context;
    }

    /**
     * Creates JSONObject from Material mat.
     * @param mat
     * @return
     */
    public static JSONObject encode(Material mat){

        JSONObject jsonMat = new JSONObject();

        try{
            jsonMat.put("bibID", mat.getBibId());
            jsonMat.put("bibText1",mat.getBibText1());
            jsonMat.put("bibText2", mat.getBibText2());
            jsonMat.put("bibText3", mat.getBibText3());
            jsonMat.put("callNumber", mat.getCallNumber());
            jsonMat.put("locationName", mat.getLocationName());
            jsonMat.put("mfhdCount", mat.getMfhdCount());
            jsonMat.put("itemCount", mat.getItemCount());
            jsonMat.put("itemStatusCode",mat.getItemStatusCode());
            jsonMat.put("isbn", mat.getIsbn());

        }catch(JSONException e){
            e.printStackTrace();
        }

        return jsonMat;
    }

    /**
     * Returns a list of JSONObjects containing the data of Materials
     * in Material List listMat.
     * @param listMat
     * @return
     */
    public static List<JSONObject> encode(List<Material> listMat){
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (Material mat : listMat) {
            JSONObject place = encode(mat);
            jsons.add(place);
        }

        return jsons;
    }

    /**
     * Saves a JSON string of mat to the savePath.txt file in the directory
     * of path.
     * @param mat
     */
    public static void saveMat(Material mat){
        String lineToWrite = encode(mat).toString();
        Log.d("Encode", lineToWrite);
        FileWriter saver = null;

        try{
            File saveDir = new File(con.getFilesDir(), appFolder);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File favs = new File(saveDir, favFileName);
            saver = new FileWriter(favs, true);
            saver.append(lineToWrite+"\n");
            saver.flush();
            saver.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Writes the data of listMat to a save file in a location determined
     * by the MaterialCoder's Context. Data is encoded as JSONStrings.
     * @param listMat
     */
    public static void saveMats(List<Material> listMat){
        for(Material mat: listMat){
            saveMat(mat);
        }
    }

    /**
     * Returns the MaterialCoder's Context's save path for files, plus
     * the folder name for the ICLibrary app and the name of the
     * text file in which Favorites are saved (i.e. ..../ICLibrary/Favorites.txt).
     * @return
     */
    public String getFileDirectoryPath(){
        return con.getFilesDir() + "/" + appFolder + "/" + favFileName;
    }

    /**
     * Returns a Material made from data stored in enc.
     * @param enc
     * @return
     */
    public static Material decode(JSONObject enc){
        Material mat = new Material();

        try{
            mat.setBibId(enc.getString("bibID"));

        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setBibText1(enc.getString("bibText1"));

        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setBibText2(enc.getString("bibText2"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setBibText3(enc.getString("bibText3"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setCallNumber(enc.getString("callNumber"));

        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setLocationName(enc.getString("locationName"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setMfhdCount(enc.getInt("mfhdCount"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setItemCount(enc.getInt("itemCount"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setItemStatusCode(enc.getInt("itemStatusCode"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            mat.setIsbn(enc.getString("isbn"));
        }catch(JSONException e){
            e.printStackTrace();
        }

        return mat;
    }

    /**
     * Returns a List of Materials made from data stored in listEnc.
     * @param listEnc
     * @return
     */
    public static List<Material> decode(List<JSONObject> listEnc){
        ArrayList<Material> listMat = new ArrayList<Material>();

        for(JSONObject json: listEnc){
            Material mat = decode(json);
            listMat.add(mat);
        }

        return listMat;
    }

    /**
     * Returns a list of JSONObjects from the file at filePath.
     * The JSONObjects contain data for Materials.
     * @param filePath
     * @return
     */
    public static List<JSONObject> unpack(String filePath){
        String jsonStr;
        List listJSON = new ArrayList<JSONObject>();

        try {
            File favs = new File(filePath);
            FileInputStream fis = new FileInputStream(favs);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((jsonStr = br.readLine()) != null) {
                JSONObject json = (JSONObject) new JSONTokener(jsonStr).nextValue();
                listJSON.add(json);
                Log.d("Decode", json.toString());
            }
            br.close();
            isr.close();
            fis.close();
        }catch(Exception e){
                e.printStackTrace();
            }

        return listJSON;
    }

        /**
     * Removes all instances of mat from the Favorites text file.
     * @param mat
     */
    public void remove(Material mat){
        JSONObject matJSON = encode(mat);
        String toRemove = matJSON.toString();
        Log.d(TAG, toRemove);

        try{
            File favs = new File(getFileDirectoryPath());
            FileInputStream fis = new FileInputStream(favs);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String temp;
            String rewrite = "";

            while((temp = br.readLine())!=null){
                Log.d(TAG, temp);
                if(temp.equals(toRemove)){
                    continue;
                }
                rewrite+=temp+"\n";
            }

            Log.d(TAG, rewrite);

            br.close();
            isr.close();
            fis.close();

            FileWriter saver = new FileWriter(favs);
            Log.d("Rewrite",rewrite);
            saver.write(rewrite);
            saver.flush();
            saver.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
