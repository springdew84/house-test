package com.cassey.house.base.ListSeq;

import java.util.*;
import java.util.stream.Collectors;

public class SetSeqTest {
    public static void main(String[] args) {
        List<Entity> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Entity entity = new Entity(i + 1000, i + 1000 + "_V");
            list.add(entity);
        }

        Set<Integer> set = list.stream().map(Entity::getValue).collect(Collectors.toSet());
        Map<Integer, Entity> voMap = new HashMap<>();
        set.forEach(i -> {
            voMap.put(i, new Entity(i, String.valueOf(i + 2000)));
        });

        List<Entity> list2 = set.stream().map(voMap::get).filter(Objects::nonNull).collect(Collectors.toList());
        list2.size();


        List<String> strList = new ArrayList<>();
        strList.add("ss1");
        strList.add("ss2");
        strList.set(1, "ss3");
        strList.size();
    }

    static class Entity {
        private Integer value;
        private String name;

        public Entity(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
