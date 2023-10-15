package jp.todolist.utils.beans;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ObjectMerger {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target,getNullProperties(source));
    }

    public static String[] getNullProperties(Object o){

        final BeanWrapper src = new BeanWrapperImpl(o);
        PropertyDescriptor[] propertyDescriptors  = src.getPropertyDescriptors();

        Set<String> emptyProperties = new HashSet<String>();

        for(PropertyDescriptor pd: propertyDescriptors){
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
                emptyProperties.add(pd.getName());
            }
        }

        var result = new String[emptyProperties.size()];

        return emptyProperties.toArray(result);

    }
    
}
