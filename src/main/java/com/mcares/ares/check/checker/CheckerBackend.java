package com.mcares.ares.check.checker;

import com.mcares.ares.configuration.AbstractSerializer;
import com.mcares.ares.configuration.annotations.ConfigSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckerBackend {

    public Map<String, Object> getRawValues() {

        Map<String, Object> values = new HashMap<>();

        Class c = this.getClass();

        List<Field> fields = new ArrayList<>();

        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }

        c = this.getClass().getSuperclass();


        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }
        c = c.getSuperclass();


        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }


        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(CheckerData.class)) {
                CheckerData data = f.getAnnotation(CheckerData.class);
                String key = data.value();
                if(key.equals("")) {
                    key = f.getName();
                }

                try {
                    Object value = f.get(this);
                    try{
                        if (f.isAnnotationPresent(ConfigSerializer.class)) {
                            ConfigSerializer serializer = f.getAnnotation(ConfigSerializer.class);
                            AbstractSerializer as = (AbstractSerializer) serializer.serializer().newInstance();
                            value = as.toString(value);
                        }
                    }
                    catch (InstantiationException ex) {
                        ex.printStackTrace();
                    }

                    values.put(key, value);

                }
                catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return values;
    }

    public void putRawValues(Checker checker, Map<String, Object> values) {
        Class c = this.getClass();

        List<Field> fields = new ArrayList<>();

        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }

        c = this.getClass().getSuperclass();


        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }
        c = c.getSuperclass();


        for(Field f : c.getDeclaredFields()){
            fields.add(f);
        }


        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(CheckerData.class)) {
                CheckerData data = f.getAnnotation(CheckerData.class);
                String key = data.value();
                if(key.equals("")) {
                    key = f.getName();
                }
                if (values.containsKey(key)) {
                    f.setAccessible(true);
                    if (!f.isAnnotationPresent(ConfigSerializer.class)) {
                        try {
                            if(f.getClass().isInstance(checker)){
                                f.set(checker, values.get(key));
                            }
                            else{
                                f.set(this,values.get(key));
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            AbstractSerializer serializer = (AbstractSerializer) f.getAnnotation(ConfigSerializer.class).serializer().newInstance();
                            if(f.getClass().isInstance(checker)){
                                f.set(checker, serializer.fromString(values.get(key)));
                            }
                            else{
                                f.set(this, serializer.fromString(values.get(key)));
                            }
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
