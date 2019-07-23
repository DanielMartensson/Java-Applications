package CoolFeatures;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class IniFile {

    private Ini ini;

    public IniFile() throws IOException {
        // Get file
        File file = new File("servers.ini");

        // If not exsist - Create new one
        if(!file.exists()){
            file.createNewFile();
        }

        // open as .ini file
        ini = new Ini(file);
    }

    // Search if key exist
    public boolean search(String key){
        for (String keys: ini.keySet()) {
            if(keys.equals(key)){
                return true;
            }
        }
        return false;
    }

    // Get section
    public Profile.Section getSection(String key){
        Profile.Section section = ini.get(key);
        return section;
    }

    // Collect all the keys
    public ArrayList keyList(){
        ArrayList listOfKeys = new ArrayList();
        listOfKeys.addAll(ini.keySet());
        return listOfKeys;
    }

    // Insert a new key
    public void inertKey(String SQL, String adress, String schema, String table, String userName, String password) throws  Exception{
        String key = schema + "-" + table; // This is going to display which server and table in Menu -> Recent

        // If the header is the same in the servers.ini file, then it will be overwritten
        Profile.Section section;
        if(search(key)){
            section = ini.get(key);
        }else{
            section = ini.add(key);
        }

        section.put("SQL", SQL);
        section.put("adress", adress);
        section.put("schema", schema);
        section.put("table", table);
        section.put("userName", userName);
        section.put("password", password);
        // Store
        ini.put(key, section);
        ini.store();
    }

}
