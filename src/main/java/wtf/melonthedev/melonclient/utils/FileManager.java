package wtf.melonthedev.melonclient.utils;

import com.google.gson.Gson;

import java.io.*;

public class FileManager {

    private static final Gson gson = new Gson();
    private static final File ROOT_DIR = new File("MelonClient");
    private static final File MODS_DIR = new File(ROOT_DIR, "Mods");


    public static void init() {
        if (!ROOT_DIR.exists()) {ROOT_DIR.mkdirs();}
        if (!MODS_DIR.exists()) {MODS_DIR.mkdirs();}
    }

    public static Gson getGson() {
        return gson;
    }

    public static File getRootDir() {
        return ROOT_DIR;
    }

    public static File getModsDir() {
        return MODS_DIR;
    }

    public static boolean writeJsonToFile(File file, Object o) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gson.toJson(o).getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> T readFromJson(File file, Class<T> c) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            inputStreamReader.close();
            fileInputStream.close();
            return gson.fromJson(builder.toString(), c);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
