package manager;

import enums.FolderType;

public class Resource {

    public static String getResourcePath(String folder, String filename){
        return "./assets/"+folder+"/"+filename;
    }

    public static String getResourcePath(FolderType folder, String filename){
        return "./assets/"+folder+"/"+filename;
    }
}
