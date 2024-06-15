package com.github.spaghettifying.elysium.util;

import com.github.spaghettifying.elysium.Elysium;
import com.github.spaghettifying.elysium.annotation.Listen;
import com.github.spaghettifying.elysium.annotation.Listeners;
import com.github.spaghettifying.elysium.hack.Hack;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Setter
public class TickByMixin {

    private static HashMap<Class<?>, ArrayList<Hack>> hacksPerMixin = new HashMap<>();

    static {
        Set<Class<?>> classesListeningSingle = new Reflections("com.github.spaghettifying.elysium.hack.impl").getTypesAnnotatedWith(Listen.class);
        for (Class<?> clazz : classesListeningSingle) {
            Listen annotation = clazz.getAnnotation(Listen.class);
            if (!hacksPerMixin.containsKey(annotation.value()))
                hacksPerMixin.put(annotation.value(), new ArrayList<>());

            ArrayList<Hack> list = hacksPerMixin.get(annotation.value());

            for (Hack hack : Elysium.getHackRegistry().getHacks().keySet())
                if (hack.getClass().equals(clazz))
                    list.add(hack);
        }

        Set<Class<?>> classesListeningMultiple = new Reflections("com.github.spaghettifying.elysium.hack.impl").getTypesAnnotatedWith(Listeners.class);
        for (Class<?> clazz : classesListeningMultiple) {
            Listeners listenersAnnotation = clazz.getAnnotation(Listeners.class);
            for (Listen listenAnnotation : listenersAnnotation.value()) {
                System.out.println(listenAnnotation.value());
                if (!hacksPerMixin.containsKey(listenAnnotation.value()))
                    hacksPerMixin.put(listenAnnotation.value(), new ArrayList<>());

                ArrayList<Hack> list = hacksPerMixin.get(listenAnnotation.value());

                for (Hack hack : Elysium.getHackRegistry().getHacks().keySet())
                    if (hack.getClass().equals(clazz))
                        list.add(hack);

                hacksPerMixin.put(listenAnnotation.value(), list);
            }
        }
    }

    public static void tick(Class<?> mixin, MinecraftClient client) {
        hacksPerMixin.get(mixin).forEach(hack -> hack.tick(client));
    }

}
