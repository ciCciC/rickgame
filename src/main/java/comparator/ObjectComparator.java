package comparator;

import physics.interfaces.DepthOfField;

import java.util.Comparator;

public class ObjectComparator implements Comparator<DepthOfField> {

    @Override
    public int compare(DepthOfField o1, DepthOfField o2) {
        if (o1.getDepth() == o2.getDepth()){
            return 0;
        }
        return o1.getDepth() < o2.getDepth() ? -1 : 1;
    }

}
