/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

class ComponentColor {
    private String propertyName;
    private String defaultColorVariableName;
    private float saturationOffset = 0,  brightnessOffset = 0;
    private int alphaOffset = 0;

    ComponentColor(String propertyName,
            String defaultColorVariableName,
            float saturationOffset,
            float brightnessOffset,
            int alphaOffset) {
        this.propertyName = propertyName;
        this.defaultColorVariableName = defaultColorVariableName;
        this.saturationOffset = saturationOffset;
        this.brightnessOffset = brightnessOffset;
        this.alphaOffset = alphaOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComponentColor c = (ComponentColor) o;
        if (alphaOffset != c.alphaOffset) {
            return false;
        }
        if (Float.compare(saturationOffset, c.saturationOffset) != 0) {
            return false;
        }
        if (Float.compare(brightnessOffset, c.brightnessOffset) != 0) {
            return false;
        }
        if (defaultColorVariableName != null ? !defaultColorVariableName.equals(c.defaultColorVariableName) : c.defaultColorVariableName != null) {
            return false;
        }
        if (propertyName != null ? !propertyName.equals(c.propertyName) : c.propertyName != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.propertyName != null ? this.propertyName.hashCode() : 0);
        hash = 61 * hash + (this.defaultColorVariableName != null ? this.defaultColorVariableName.hashCode() : 0);
        hash = 61 * hash + Float.floatToIntBits(this.saturationOffset);
        hash = 61 * hash + Float.floatToIntBits(this.brightnessOffset);
        hash = 61 * hash + this.alphaOffset;
        return hash;
    }

    public void write(StringBuilder sb) {
        sb.append("                     getComponentColor(c, \"").
           append(propertyName).append("\", ").
           append(defaultColorVariableName).append(", ").
           append(saturationOffset).append("f, ").
           append(brightnessOffset).append("f, ").
           append(alphaOffset);
    }
}
