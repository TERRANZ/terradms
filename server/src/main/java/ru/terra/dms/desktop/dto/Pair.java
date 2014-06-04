package ru.terra.dms.desktop.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 04.06.14
 * Time: 14:54
 */
@XmlRootElement
public class Pair<K, V> {
    public K key;
    public V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

