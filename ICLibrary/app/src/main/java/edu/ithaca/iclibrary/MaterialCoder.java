package edu.ithaca.iclibrary;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by Joseph on 4/26/2016.
 */
public class MaterialCoder {

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
        FileWriter saver = null;

        try{
            File saveDir = new File(con.getFilesDir(), appFolder);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File favs = new File(saveDir, favFileName);
            saver = new FileWriter(favs);
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
    public Material decode(JSONObject enc){
        Material mat = new Material();

        try{
            mat.setBibId(enc.getString("bibID"));
            mat.setBibText1(enc.getString("bibText1"));
            mat.setBibText2(enc.getString("bibText2"));
            mat.setBibText3(enc.getString("bibText3"));
            mat.setCallNumber(enc.getString("callNumber"));
            mat.setLocationName(enc.getString("locationName"));
            mat.setMfhdCount(enc.getInt("mfhdCount"));
            mat.setItemCount(enc.getInt("itemCount"));
            mat.setItemStatusCode(enc.getInt("itemStatusCode"));
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
    public List<Material> decode(List<JSONObject> listEnc){
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
    public List<JSONObject> unpack(String filePath){
        File favs = null;
        FileInputStream fis = null;
        String jsonStr = null;
        List listJSON = new ArrayList<JSONObject>();

        try{
            favs = new File(filePath);
            fis = new FileInputStream(favs);
        }catch(Exception e){
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        try{
            while((jsonStr = br.readLine()) != null){
                JSONObject json = (JSONObject) new JSONTokener(jsonStr).nextValue();
                listJSON.add(json);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return listJSON;
    }
}