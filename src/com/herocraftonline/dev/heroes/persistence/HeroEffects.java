package com.herocraftonline.dev.heroes.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HeroEffects {

    protected Map<String, Double> effects;
    protected ReadWriteLock effectLock;

    HeroEffects() {
        effects = new HashMap<String, Double>();
        this.effectLock = new ReentrantReadWriteLock(false);
    }

    public boolean hasEffect(String effect) {
        effectLock.readLock().lock();
        boolean has = effects.containsKey(effect);
        effectLock.readLock().unlock();
        return has;
    }
    
    public Double getEffect(String effect) {
        effectLock.readLock().lock();
        Double time = effects.get(effect);
        effectLock.readLock().unlock();
        return time;
    }
    
    public Double putEffect(String effect, Double time) {
        effectLock.writeLock().lock();
        Double oldTime = effects.put(effect, time);
        effectLock.writeLock().unlock();
        return oldTime;
    }
    
    public Double removeEffect(String effect) {
        effectLock.writeLock().lock();
        Double oldTime = effects.remove(effect);
        effectLock.writeLock().unlock();
        return oldTime;
    }
    
    void update(int interval)
    {
        effectLock.writeLock().lock();
        Set<Map.Entry<String, Double>> effectSet = effects.entrySet();
        for(Iterator<Map.Entry<String, Double>> iter = effectSet.iterator(); iter.hasNext();)
        {
            Map.Entry<String, Double> effect = iter.next();
            Double time = effect.getValue();
            time = time - interval;
            if(time.isNaN()||time <= 0)
            {
                iter.remove();
                continue;
            }
            effect.setValue(time);
        } 
        effectLock.writeLock().unlock();
        return;
    }
}