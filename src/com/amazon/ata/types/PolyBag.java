package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {
    private double mass;
    private BigDecimal volume;


    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     */
    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;


    }

    public BigDecimal getVolume() {
        return volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemHeight = item.getHeight();
        BigDecimal itemLength = item.getLength();
        BigDecimal itemWidth = item.getWidth();
        BigDecimal itemVolume = itemHeight.multiply(itemLength).multiply(itemWidth);
        return volume.compareTo(itemVolume) < 0;
    }

    @Override
    public BigDecimal getMass() {
        double v = 0.6;
        double vVolume = volume.doubleValue();
        mass = Math.ceil(Math.sqrt(vVolume) * v);

        return BigDecimal.valueOf(mass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PolyBag polyBag = (PolyBag) o;
        return Double.compare(polyBag.mass, mass) == 0 && volume.equals(polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mass, volume);
    }
}