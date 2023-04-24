package mz.org.fgh.mentoring.util;

import java.util.ArrayList;

/**
 * Created by TAMELE, Volóide on 4/24/23.
 */
public class Utilities {

    public static boolean listHasElements(ArrayList<?> list){
        return list != null && !list.isEmpty() && list.size() > 0;
    }
}
