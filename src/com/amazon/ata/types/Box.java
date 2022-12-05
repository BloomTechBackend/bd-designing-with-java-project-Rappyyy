package com.amazon.ata.types;

import java.math.BigDecimal;

public class Box extends Packaging {
    private Material material;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;

    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     * @param length   - the length of the package
     * @param width    - the width of the package
     * @param height   - the height of the package
     */
    // set length for this instance and other values too
    public Box(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
        this.material = super.getMaterial();
    }

    public BigDecimal getLength() {
        return length;
    }


    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    @Override
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }
    @Override
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);

        // For simplicity, we ignore overlapping flaps
        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
    }
}
