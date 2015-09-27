/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.mcessence.essence.arguments.internal;

public abstract class Argument {

    protected String name;
    protected String error;
    protected boolean success = false;
    protected Object defaultValue;
    protected Object value;

    public Argument() {
        //-
    }

    public Argument(String name) {
        this.name = name;
    }

    public Argument(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Argument(String name, Object defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public abstract boolean parse(String input);

    public abstract String getDescription();

    public abstract Argument clone();

    public abstract Class getRawClass();


    public boolean isValid() {
        return success;
    }

    public boolean hasDefault() {
        return defaultValue != null;
    }

    public Object getDefault() {
        return defaultValue;
    }

    public void setDefault(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getValue() {
        return value;
    }

    public String getError() {
        return error;
    }

    public String getName() {
        return name;
    }

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }
}
